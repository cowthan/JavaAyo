package okhttp3.guide;

import java.io.IOException;
import java.lang.reflect.Method;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CopyOfGetExample {
  OkHttpClient client = new OkHttpClient();

  String run(String url) throws IOException {
    Request request = new Request.Builder()
        .url(url)
        .build();

    Response response = client.newCall(request).execute();
    return response.body().string();
  }

  public static void main1(String[] args) throws IOException {
    CopyOfGetExample example = new CopyOfGetExample();
    String response = example.run("http://localhost:9090/plugins/restapi/v1/users");
    System.out.println(response);
  }
  
  public static void main(String[] args) throws IOException {
	
	  try {
		System.out.println(new String(decodeBase64("YWRtaW46MTIzNDU="), "utf-8"));
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  
	  OkHttpClient client = new OkHttpClient();

	  
	  Request request = new Request.Builder()
      .url("http://172.16.12.191:9090/plugins/restapi/v1/users/")
      //.post(RequestBody.create(MediaType.parse("text/json"), "{}"))
      .get()
      .header("Authorization", "1122334")
      //.header("Authorization", "Basic " + encodeBase64("admin22222:111111".getBytes()) + "")
      .header("Accept", "application/json")
      .build();

	  Response response = client.newCall(request).execute();
	  System.out.println(response.isRedirect());
	  System.out.println(response.body().string());
  }
  
  public static String encodeBase64(byte[]input) {  
	  try {
		  Class clazz=Class.forName("com.sun.org.apache.xerces.internal.impl.dv.util.Base64");  
	      Method mainMethod= clazz.getMethod("encode", byte[].class);  
	      mainMethod.setAccessible(true);  
	       Object retObj=mainMethod.invoke(null, new Object[]{input});  
	       System.out.println((String)retObj);
	       return (String)retObj;  
	} catch (Exception e) {
		e.printStackTrace();
		return "";
	}
      
  }  
  /*** 
   * decode by Base64 
   */  
  public static byte[] decodeBase64(String input) throws Exception{  
      Class clazz=Class.forName("com.sun.org.apache.xerces.internal.impl.dv.util.Base64");  
      Method mainMethod= clazz.getMethod("decode", String.class);  
      mainMethod.setAccessible(true);  
       Object retObj=mainMethod.invoke(null, input);  
       return (byte[])retObj;  
  }  
  
}
