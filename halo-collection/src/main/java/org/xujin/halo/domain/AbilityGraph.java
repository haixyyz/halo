package org.xujin.halo.domain;

import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 系统能力图<br>
 * 
 * toString可生成标准dot语法，安装graphviz的机器可以通过命令<b>dot -Tpng -o target.png inputfile</b>生成能力图<br>
 * graphviz安装方法:<br>
 * ruby -e "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install)"<br>
 * brew install graphviz<br>
 * vim ~/.bash_profile<br>
 * export PATH=/usr/local/bin:$PATH<br>
 * source ~/.bash_profile<br>
 *
 * <b><a href="https://graphviz.gitlab.io/_pages/pdf/dotguide.pdf">dotguide</a></b>
 * 
 * @author xujin
 *
 */
public class AbilityGraph {
	
	static String BOX = "[shape=box]";
	static String POINT = "[shape=plaintext]";
	static String DOTBOX = "[shape=box,style=dotted]";
	/**
	 * 有向图
	 */
	String type = "digraph";
	String name = "G";
	/**
	 * 全局属性集合
	 */
	Set<String> attribute = new HashSet<>();
	/**
	 * 边集
	 */
	Set<Edge> edges = new HashSet<>();
	
	private List<AbilityGraph> subGraph = new ArrayList<>();

	/**
	 * 加为子图
	 * @param g
	 */
	public void addSubGraph(AbilityGraph g){
		g.type = "subgraph";
		g.name = this.name + subGraph.size();
		subGraph.add(g);
	}
	@Override
	public String toString() {
		//左右对齐
		attribute.add("rankdir=LR");
		StringBuffer sb = new StringBuffer();
		sb.append(type + " " + name + " {\n");
		attribute.forEach(attr -> {
			sb.append(attr + "\n");
		});
		edges.forEach(e -> {
			sb.append(e + "\n");
		});
		subGraph.forEach(sub -> {
			sb.append(sub.toString());
		});
		sb.append("}");
		return sb.toString();
	}
	/**
	 * 边
	 */
	@EqualsAndHashCode(of={"x", "y"})
	public static class Edge {
		/**
		 * 箭头方向：forward (the default), back, both, or none
		 * 箭头类型：normal, inv, dot, invdot, odot, invodot，none
		 */
		private List<String> attr = new ArrayList<>();
		public static enum DIR{
			forward, back, both, none
		}
		public static enum TYPE{
			normal,dot,inv,crow,tee,vee,diamond,none,box,curve,icurve,empty
		}
		public static enum COLOR{
		    red
        }
		
		String x;
		String y;
		
		public static Edge of(String x, String y){
			Edge e = new Edge();
			e.x = x;
			e.y = y;
			return e;
		}
		
		/**
		 * 虚线
		 * @return
		 */
		public Edge dottedLine(){
			attr.add("style=dotted");
			return this;
		}
		/**
		 * 箭头类型
		 * @param type
		 * @return
		 */
		public Edge type(TYPE type){
			attr.add("arrowhead=" + type);
			return this;
		}
        /**
         * 箭头颜色
         * @param color
         * @return
         */
        public Edge color(COLOR color){
            attr.add("color=" + color);
            return this;
        }
		/**
		 * 箭头方向
		 * @param dir
		 * @return
		 */
		public Edge direct(DIR dir){
			attr.add("dir=" + dir);
			return this;
		}
		@Override
		public String toString() {
			if(attr.isEmpty()){
				this.type(Edge.TYPE.normal);
			}
			StringBuffer sb = new StringBuffer();
			sb.append(x + " -> " + y + attr.toString());
			return sb.toString();
		}
	}
}
