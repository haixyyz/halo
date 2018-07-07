package org.xujin.halo.domain;

import lombok.Setter;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ConfigurationBuilder;
import org.springframework.stereotype.Component;
import org.xujin.halo.annotation.domian.DomainAbility;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 采集系统的能力图
 * @author xujin
 *
 */
@Component
public class AbilityCollection {
	
    @Setter
    private List<String> packages = new ArrayList<>();
    
    /**
     * 添加扫描范围
     * @param pkg
     */
    public void addPackage(String pkg){
    	packages.add(pkg);
    }

    /**
     * 收集能力图
     */
    public AbilityGraph collectAbility(){
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .forPackages(packages.stream().toArray(String[]::new))
                .addScanners(new MethodAnnotationsScanner(), new SubTypesScanner()));
        final AbilityGraph g = new AbilityGraph();
        //扫描领域能力
        Set<Method> methods = reflections.getMethodsAnnotatedWith(DomainAbility.class);
        graphMethod(g, methods);
        //扫描领域实体
        Set<Class<? extends Entity>> entities = reflections.getSubTypesOf(Entity.class);
        graphEntity(g, entities);
        //扫描领域服务
        Set<Class<? extends ServiceI>> services = reflections.getSubTypesOf(ServiceI.class);
        graphService(g, services);
        return g;
    }
    private void graphMethod(final AbilityGraph g, Set<Method> methods) {
        if(methods != null){
            methods.forEach(m -> {
                String ability = m.getName();
                g.attribute.add(ability + AbilityGraph.POINT);//能力用点表示
                Class<?> clazz = m.getDeclaringClass();
                String domain = clazz.getSimpleName();
                if(Entity.class.isAssignableFrom(clazz)){
                    g.attribute.add(domain + AbilityGraph.BOX);//实体用矩形表示
                }
                if(ValueObject.class.isAssignableFrom(clazz)){
                    g.attribute.add(domain + AbilityGraph.DOTBOX);//值用虚线矩形表示
                }
                //当前Entity或ValueObject拥有哪些Ability
                g.edges.add(AbilityGraph.Edge.of(ability, domain).type(AbilityGraph.Edge.TYPE.normal).direct(AbilityGraph.Edge.DIR.back));//拥有哪些能力用实心箭头
                //方法的参数依赖哪些Entity或ValueObject
                addDependencyEdge(g, m);
                //方法聚合了哪些实体
                methodAggregate(g, m);
            });
        }
    }
    private void graphEntity(final AbilityGraph g, Set<Class<? extends Entity>> entitys) {
		if(entitys != null){
			for (Class<? extends Entity> entity : entitys){
				//领域实体的继承关系 
				Class<?> superClass = entity.getSuperclass();
				if(superClass != null && Entity.class.isAssignableFrom(superClass) && superClass != Entity.class){
					g.attribute.add(entity.getSimpleName() + AbilityGraph.BOX);//实体用矩形表示
					g.attribute.add(superClass.getSimpleName() + AbilityGraph.BOX);//父类实体用矩形表示
					g.edges.add(AbilityGraph.Edge.of(entity.getSimpleName(), superClass.getSimpleName()).type(AbilityGraph.Edge.TYPE.empty));//继承关系用空心箭头
				}
                //领域实体的聚合关系
                Field[] fields = entity.getDeclaredFields();
                for (Field f : fields) {
                	//通过属性的依赖确定聚合关系
                    fieldAggregate(g, f);
				}
            };
        }
	}
    private void graphService(final AbilityGraph g, Set<Class<? extends ServiceI>> services) {
        if(services != null){
            for (Class<? extends ServiceI> service : services){
                if(!service.isInterface()){
                    continue;
                }
                //领域服务间的继承关系
                Class<?> superClass = service.getSuperclass();
                if(superClass != null && ServiceI.class.isAssignableFrom(superClass) && superClass != ServiceI.class){
                    g.edges.add(AbilityGraph.Edge.of(service.getSimpleName(), superClass.getSimpleName()).type(AbilityGraph.Edge.TYPE.empty));//继承关系用空心箭头
                }
                //领域服务的方法都是领域的能力
                Method[] methods = service.getMethods();
                for (Method m : methods) {
                    String ability = m.getName();
                    g.attribute.add(ability + AbilityGraph.POINT);//能力用点表示
                    g.edges.add(AbilityGraph.Edge.of(ability, service.getSimpleName()).type(AbilityGraph.Edge.TYPE.normal).direct(AbilityGraph.Edge.DIR.back));//服务有哪些能力用实心箭头
                    //方法的参数体现领域服务与领域Entity或ValueObject的依赖
                    addDependencyEdge(g, m);
                }

            };
        }
    }

    /*
     * 通过属性的依赖来体现聚合关系
     */
    private void fieldAggregate(final AbilityGraph g, Field f) {
        Class<?> rootClass = f.getDeclaringClass();
        String rootDomain = rootClass.getSimpleName();
        g.attribute.add(rootDomain + AbilityGraph.BOX);//根实体用矩形表示
        //属性的类型
        Type fieldType = f.getGenericType();
        addAggregateEdge(g, fieldType, rootClass);
    }
	/*
	 * 通过方法的依赖来体现聚合关系
	 */
	private void methodAggregate(final AbilityGraph g, Method m) {
		Class<?> rootClass = m.getDeclaringClass();
        String rootDomain = rootClass.getSimpleName();
        g.attribute.add(rootDomain + AbilityGraph.BOX);//根实体用矩形表示
//        //方法参数类型
//        Type[] paramTypes = m.getGenericParameterTypes();
//        for (Type paramType : paramTypes) {
//            addAggregateEdge(g, paramType, rootClass);
//        }
        //方法返回值类型
		Type returnType = m.getGenericReturnType();
        addAggregateEdge(g, returnType, rootClass);

	}
    /*
     * 添加依赖边
     */
    private void addDependencyEdge(final AbilityGraph g, Method m) {
        String ability = m.getName();
        //方法参数依赖
        Type[] paramTypes = m.getGenericParameterTypes();
        for (Type p : paramTypes) {
            List<String> typeList = getClassT(p.getTypeName());
            for (String className : typeList) {
                try {
                    Class<?> pClass = Class.forName(className);
                    String domain = pClass.getSimpleName();
                    if(Entity.class.isAssignableFrom(pClass)){
                        g.attribute.add(domain + AbilityGraph.BOX);//实体用矩形表示
                        g.edges.add(AbilityGraph.Edge.of(ability, domain).type(AbilityGraph.Edge.TYPE.normal).dottedLine());//参数依赖用虚线表示
                    }
                    if(ValueObject.class.isAssignableFrom(pClass)){
                        g.attribute.add(domain + AbilityGraph.DOTBOX);//值用虚线矩形表示
                        g.edges.add(AbilityGraph.Edge.of(ability, domain).type(AbilityGraph.Edge.TYPE.normal).dottedLine());//参数依赖用虚线表示
                    }
                } catch (Exception e1) {}
            }
        }
    }
	/*
     * 添加聚合边
	 */
    private void addAggregateEdge(AbilityGraph g, Type domainType, Class<?> rootDomainClass) {
        String rootDomain = rootDomainClass.getSimpleName();
        List<String> typeList = getClassT(domainType.getTypeName());
        for (String className : typeList) {
            try {
                Class<?> domainClass = Class.forName(className);
                String domain = domainClass.getSimpleName();
                if(Entity.class.isAssignableFrom(domainClass)){
                    g.attribute.add(domain + AbilityGraph.BOX);//参数是实体，用矩形表示
                    //如果有聚合关系，但是属性没有依赖，则把聚合关系飘红
                    if(!isDependencyType(domainType, rootDomainClass.getDeclaredFields())){
                        g.edges.add(AbilityGraph.Edge.of(domain, rootDomain).type(AbilityGraph.Edge.TYPE.diamond).color(AbilityGraph.Edge.COLOR.red));
                    }else {
                        g.edges.add(AbilityGraph.Edge.of(domain, rootDomain).type(AbilityGraph.Edge.TYPE.diamond));//聚合关系用菱形表示
                    }
                }
                if(ValueObject.class.isAssignableFrom(domainClass)){
                    g.attribute.add(domain + AbilityGraph.DOTBOX);//参数是值对象，用虚线矩形表示
                    //如果有聚合关系，但是属性没有依赖，则把聚合关系飘红
                    if(!isDependencyType(domainType, rootDomainClass.getDeclaredFields())){
                        g.edges.add(AbilityGraph.Edge.of(domain, rootDomain).type(AbilityGraph.Edge.TYPE.diamond).color(AbilityGraph.Edge.COLOR.red));
                    }else {
                        g.edges.add(AbilityGraph.Edge.of(domain, rootDomain).type(AbilityGraph.Edge.TYPE.diamond));//聚合关系用菱形表示
                    }
                }
            } catch (Exception e1) {}
        }
    }

    /**
     *
     * @param domainType
     * @param fields
     * @return
     */
    private boolean isDependencyType(Type domainType, Field[] fields){
        if(fields == null){
            return false;
        }
        for (Field f : fields){
            if(f.getGenericType() == domainType){
                return true;
            }
        }
        return false;
    }

    /*
     * 正则表达式匹配两个指定字符串中间的内容  
     */    
    private List<String> getClassT(String soap){
    	String regex = "<(.*?)>";//匹配泛型
        List<String> list = new ArrayList<String>();    
        Pattern pattern = Pattern.compile(regex);// 匹配的模式    
        Matcher m = pattern.matcher(soap);    
        while (m.find()) {   
        	String match = m.group(1);
        	//有些泛型里带? extends 等需要过滤掉
        	String[] s = match.split(" ");
            list.add(s[s.length - 1]);    
        }    
        if(list.isEmpty()){
        	list.add(soap);
        }
        return list;    
    } 
}
