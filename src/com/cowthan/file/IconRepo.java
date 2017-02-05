package com.cowthan.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.ayo.lang.JsonUtilsUseFast;

/**
 * 遍历一个目录，返回目录结构，返回的格式是json
 *
 */
public class IconRepo {
	
	public static void main(String[] args) {
		
		String path1111 = System.getProperty("user.dir");
		System.out.println(path1111);
		String rootDir = path1111 + "\\ic";
		String json = JsonUtilsUseFast.toJson(dirToDir(rootDir));
		System.out.println(json);
		String data = "var data={json};".replace("{json}", json);
		putContent("./data.js", data);
		
		String url = ("file:///" + path1111 + "/icons.html").replace("\\", "/");
		System.out.println(url);
		openBrowser(url);
	}
	
	public static Dir dirToDir(String rootDir){
		
		String path1111 = System.getProperty("user.dir");
		System.out.println(path1111);
		
		Dir root = new Dir();
		root.path = rootDir;
		
		///---
		File fr = new File(root.path);
		root.name = fr.getName();
		
		File[] files = fr.listFiles();
		if(files == null || files.length == 0){
			return root;
		}else{
			for(File f: files){
				if(f.isDirectory()){
					root.subDirs.add(dirToDir(f.getAbsolutePath()));
				}else{
					JustFile jf = new JustFile();
					jf.path = f.getAbsolutePath().replace("\\", "/");
					jf.name = f.getName();
					jf.size = (long) (f.length()/1024f);
					
					
					jf.path = jf.path.replace(path1111 + "\\", "./");
					//String url = "file:///" + jf.path;
					//System.out.println("<img src='" + url + "' alt='" + url +"' />");
					
					root.subFiles.add(jf);
				}
			}
		}
		
		///---
		
		return root;
	}
	
	public static class JustFile{
		public String name;
		public String path;
		public String suffix;
		public long size; //K
	
	}
	public static class Dir{
		public String name;
		public String path;
		public List<Dir> subDirs = new ArrayList<>();
		public List<JustFile> subFiles = new ArrayList<>();
	}
	
	public static void putContent(String absolutePath, String content){
		try {
			File f = new File(absolutePath);
			if(!f.exists()){
				f.createNewFile();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(new File(absolutePath));   
			out.write(content.getBytes()); 
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(out != null){
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		 
	}
	
	public static void openBrowser(String url){
		if(url == null){
			System.out.println("url为空");
			return;
		}
		if(java.awt.Desktop.isDesktopSupported()){
            try{
                //创建一个URI实例,注意不是URL
                java.net.URI uri=java.net.URI.create(url);
                //获取当前系统桌面扩展
                java.awt.Desktop dp=java.awt.Desktop.getDesktop();
                //判断系统桌面是否支持要执行的功能
                if(dp.isSupported(java.awt.Desktop.Action.BROWSE)){
                    //获取系统默认浏览器打开链接
                    dp.browse(uri);
                }else{
                	System.out.println("不支持打开浏览器功能");
                }
            }catch(java.io.IOException e){
                //此为无法获取系统默认浏览器
            	e.printStackTrace();
            	System.out.println("无法获取系统默认浏览器");
            }
        }
	}


}
