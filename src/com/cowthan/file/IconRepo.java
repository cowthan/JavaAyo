package com.cowthan.file;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.ayo.lang.JsonUtilsUseFast;

/**
 * 遍历一个目录，返回目录结构，返回的格式是json
 *
 */
public class IconRepo {
	
	public static void main(String[] args) {
		
		String rootDir = "C:\\Users\\Administrator\\Desktop\\icons\\ic";
		System.out.println(JsonUtilsUseFast.toJson(dirToDir(rootDir)));
		
	}
	
	public static Dir dirToDir(String rootDir){
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
					
					
					jf.path = jf.path.replace("C:/Users/Administrator/Desktop/icons/", "./");
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

}
