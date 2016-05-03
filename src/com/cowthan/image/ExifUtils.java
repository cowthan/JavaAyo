package com.cowthan.image;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.imaging.jpeg.JpegProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifIFD0Directory;

/**
 * 照片的Exif信息的读写，参考：
 * http://blog.csdn.net/ghsau/article/details/8472486
 * 
 * 需要的jar包：
 * metadata-extractor-2.7.0.jar
 * xmpcore-5.1.2.jar
 * 
 * @author cowthan
 *
 */
public class ExifUtils {
	
	public static void main(String[] args) throws Exception {  
		String path = "J:\\照片2015\\CemareHanHan\\IMG_3480.jpg";
        File jpegFile = new File(path);  
        Metadata metadata = JpegMetadataReader.readMetadata(jpegFile);  
        Directory exif = metadata.getDirectory(ExifIFD0Directory.class);  
        
        ///下面就是怎么获取属性了
        Iterator tags = exif.getTags().iterator(); //getTagIterator();  
        while (tags.hasNext()) {  
            Tag tag = (Tag)tags.next();  
            System.out.println(tag.getTagName() + " ==> " + tag.getDescription());  
        }  
        
        ///获取拍摄时间
        System.out.println(getShootTime(path));
    }  
	
	/**
	 * 获取拍摄时间
	 * @param path 照片路径
	 * @return   秒数
	 */
	public static int getShootTime(String path){
		File jpegFile = new File(path);  
        Metadata metadata;
		try {
			metadata = JpegMetadataReader.readMetadata(jpegFile);
			Directory exif = metadata.getDirectory(ExifIFD0Directory.class); 
	        String model = exif.getString(ExifIFD0Directory.TAG_DATETIME);
	        
	        //Date/Time ==> 2015:09:17 15:24:43
	        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
	        Date d = sdf1.parse(model);
	        return (int) (d.getTime() / 1000);
	        
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}  
          
	}
}
