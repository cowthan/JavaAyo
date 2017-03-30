package com.cowthan.algrithm.java3d;

import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.DirectionalLight;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.universe.SimpleUniverse;

public class J3dTest {
	public J3dTest(){  
		   // 创建一个虚拟空间  
		     SimpleUniverse universe = new SimpleUniverse();  
		     // 创建一个用来包含对象的数据结构  
		     BranchGroup group = new BranchGroup();  
		      
		     // 创建一个球并把它加入到group中  
		     Sphere sphere = new Sphere(0.5f); // 小球的半径为0.5米  
		     group.addChild(sphere);  
		      
		     Color3f light1Color = new Color3f(1.8f, 0.1f, 0.1f);  
		     // 设置光线的颜色  
		     BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0), 100.0);  
		     // 设置光线的作用范围  
		     Vector3f light1Direction = new Vector3f(4.0f, -7.0f, -12.0f);  
		     // 设置光线的方向  
		     DirectionalLight light1= new DirectionalLight(light1Color, light1Direction);  
		     // 指定颜色和方向，产生单向光源  
		     light1.setInfluencingBounds(bounds);  
		     // 把光线的作用范围加入光源中  
		     group.addChild(light1);  
		     // 将光源加入group组,安放观察点  
		     universe.getViewingPlatform().setNominalViewingTransform();  
		     // 把group加入到虚拟空间中  
		     universe.addBranchGraph(group);  
		 }  
		 public static void main(String[] args) {  
		  new J3dTest();  
		 }  
}
