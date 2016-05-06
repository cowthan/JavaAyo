package com.cowthan.env;

import java.io.BufferedReader; 
import java.io.InputStreamReader; 

import com.ice.jni.registry.RegStringValue; 
import com.ice.jni.registry.Registry; 
import com.ice.jni.registry.RegistryKey; 
public class RegistryTest { 
public static void main(String[] str) 
{ 
  try { 
   RegistryKey openPath1 = Registry.HKEY_LOCAL_MACHINE 
     .openSubKey("SYSTEM\\CurrentControlSet\\Control\\Session Manager\\Environment"); 
   String path_Old = openPath1.getStringValue("Path");    //获取原Path键值 

   RegistryKey openPath2 = Registry.HKEY_LOCAL_MACHINE 
     .openSubKey("SYSTEM\\CurrentControlSet\\Control\\Session Manager"); 

   RegistryKey subKey = openPath2.createSubKey("Environment", ""); 

  //定义Path所在目录的句柄（相当于在Session Manager路径下面，新建Environment文件夹，如果存在不改变已有的值。） 
//   String path_New = path_Old + ";" + "D:\\myTinoProject\\bingy"; 
   String path_New = path_Old + "bin;"; 
   subKey.setValue(new RegStringValue(subKey, "Path", path_New)); //修改Path键值 
   subKey.closeKey(); 
   
   //查看进程的方法 
   String[] cmd = { "D:\\dfqd\\workspace\\tasklist" }; 
   Process proc = Runtime.getRuntime().exec(cmd); 
   BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream())); 
   String string_Temp = in.readLine(); 
   while (string_Temp != null) { 
       System.out.println(string_Temp); 
       string_Temp = in.readLine(); 
   } 
   //删除explorer.exe进程 
   Process proc2 = Runtime.getRuntime().exec("D:\\dfqd\\workspace\\taskkill /F /IM explorer.exe"); 
   Thread.sleep(500); 
   //重启explorer.exe进程 
   Process proc3 = Runtime.getRuntime().exec("explorer.exe"); 
   System.out.println("=====SUCCESS====="); 
  } catch (Exception e) { 
   e.printStackTrace(); 
  } 
} 
} 