package org.xujin.halo.domain;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Arrays;

/**
 * 领域模型工具
 */
public class DomainUtils {
    /**
     * 生成领域模型图
     * @param packages
     */
    public static void generationDomainGraph(String... packages){
        AbilityCollection ac = new AbilityCollection();
        Arrays.stream(packages).forEach(pkg -> {
            ac.addPackage(pkg);
        });
        AbilityGraph ag = ac.collectAbility();
        File file = new File("domain.dot");
        try(FileOutputStream out = new FileOutputStream(file)){
            file.createNewFile();
            //写入dotsource
            out.write(ag.toString().getBytes());
            //执行dot命令生成png图片
            Runtime rt = Runtime.getRuntime();
            String[] param = {"dot", "-Tpng", "-o", "domain.png", "domain.dot"};
            Process p = rt.exec(param);
            p.waitFor();
            System.out.println("Runtime process finish:" + String.join(" ", param));
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
