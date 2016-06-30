package okhttp3.recipes;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpConnectionMultiFile {
	public static void main(String[] args) {
		 String url = "http://124.133.240.71:8822/jjservice/service.ashx?action=suborder&userid=e5c9d39b-5232-4c14-bb83-b6b5f0d77ba0&address=&phone=&realname=&transportmoney=&productmoney=&ordermoney=&ids=&taobaohao=dd";
			File file2 = new File("test.png");
		    //System.out.println(file2.getAbsolutePath());
		   
		 upload(new String[]{"F:\\ws-jee\\JavaAyo\\test.png","F:\\ws-jee\\JavaAyo\\test.png"}, url);
	}
	
	public static void upload(String[] uploadFiles, String actionUrl) {
        String end = "/r/n";
        String twoHyphens = "--";
        String boundary = "*****";
        try {
            URL url = new URL(actionUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            // 发送POST请求必须设置如下两行
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setUseCaches(false);
            con.setRequestMethod("POST");
            con.setRequestProperty("Connection", "Keep-Alive");
            con.setRequestProperty("Charset", "UTF-8");
            con.setRequestProperty("Content-Type",
                    "multipart/form-data;boundary=" + boundary);
            DataOutputStream ds =
                    new DataOutputStream(con.getOutputStream());
            for (int i = 0; i < uploadFiles.length; i++) {
                String uploadFile = uploadFiles[i];
                String filename = uploadFile.substring(uploadFile.lastIndexOf("\\") + 1);
                System.out.println(uploadFile);
                ds.writeBytes(twoHyphens + boundary + end);
                ds.writeBytes("Content-Disposition: form-data; " +
                                "name=\"file" + i + "\";filename=\"" +
                        filename + "\"" + end);
                        ds.writeBytes(end);
                FileInputStream fStream = new FileInputStream(uploadFile);
                int bufferSize = 1024;
                byte[] buffer = new byte[bufferSize];
                int length = -1;
                while ((length = fStream.read(buffer)) != -1) {
                    ds.write(buffer, 0, length);
                }
                ds.writeBytes(end);
              /* close streams */
                fStream.close();
            }
            ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
            ds.flush();
            // 定义BufferedReader输入流来读取URL的响应
            InputStream is = con.getInputStream();
            int ch;
            StringBuffer b = new StringBuffer();
            while ((ch = is.read()) != -1) {
                b.append((char) ch);
            }
            String s = b.toString();
            System.out.println(con.getResponseCode());
            System.out.println("response--" + s);
            if (s.contains("successfully")) {
                // for (int i = 1; i < 5; i++) {
                int beginIndex = s.indexOf("url =") + 5;
                int endIndex = s.indexOf("/n", beginIndex);
                String urlStr = s.substring(beginIndex, endIndex).trim();
                System.out.println(urlStr);
                // }
            }
            ds.close();
        } catch (Exception e) {
        }
    }
}
