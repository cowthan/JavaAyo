package com.cowthan.image;
import java.awt.image.ImageProducer;  
import java.io.File;  
  
import com.sun.jimi.core.Jimi;  
import com.sun.jimi.core.JimiException;  
import com.sun.jimi.core.JimiWriter;  
import com.sun.jimi.core.options.JPGOptions;  

public class GifJpgConverter {
	
	public static void toJPG(String source, String dest, int quality) {  
		  
        if (dest == null || dest.trim().equals(""))  
            dest = source;  
  
        if (!dest.toLowerCase().trim().endsWith("jpg")) {  
            dest += ".jpg";  
            System.out.println("Overriding to JPG, output file: " + dest);  
        }  
          
          
        if (quality < 0 || quality > 100 || (quality + "") == null || (quality + "").equals("")) {  
            quality = 75;  
        }  
          
        try {  
            JPGOptions options = new JPGOptions();  
            options.setQuality(quality);  
            ImageProducer image = Jimi.getImageProducer(source);  
            JimiWriter writer = Jimi.createJimiWriter(dest);  
            writer.setSource(image);  
            writer.setOptions(options);  
            writer.putImage(dest);  
        } catch (JimiException je) {  
            System.err.println("Error: " + je);  
        }  
    }  
  
      
    public static void main(String[] args) {  
          
    	//J盘下建个gif文件夹，下面再建个1和2两个目录，1中放任意张gif图
        File file = new File("J:\\gif\\1\\");  
        for(String fl : file.list()){  
  
            if(fl.endsWith(".gif")){  
                System.out.println(fl);  
                toJPG("J:\\gif\\1\\" + fl, "J:\\gif\\2\\" + fl.replace(".gif", ""), -1);  
            }  
              
        }  
          
          
          
    }  
}
