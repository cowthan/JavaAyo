package com.cowthan.rsa;

import java.util.ArrayList;
import java.util.List;

import org.ayo.lang.Ayo;

import Decoder.BASE64Decoder;
import Decoder.BASE64Encoder;


/**
 * Created by cowthan on 2015/8/9.
 */
public class Crypt {
	
	/**
	 * 源字符串长度小于117，直接加密，否则，每100个分一段，每一端加密，用逗号拼起来，返回
	 * 
	 * elementLengthLimit不能大于117
	 * 
	 * @param s
	 * @return
	 */
	public static String encodeBySegment(String s, int elementLengthLimit, String deletemeter){
		List<String> listEncoded = new ArrayList<String>();
		if(s.length() <= elementLengthLimit){
			listEncoded.add(encode(s));
		}else{
			List<String> list = Ayo.splitString(s, elementLengthLimit);
			for(String str: list){
				listEncoded.add(encode(str));
			}
//			return StringProcessor.on(",").join(listEncoded);
		}
		
		///
		String r = "";
		for (int i = 0; i < listEncoded.size(); i++) {
			r += listEncoded.get(i) + ((i == listEncoded.size()-1) ? "" : deletemeter);
		}
		return r;
	}
	
	public static String decodeBySegment(String s, String delemeter){
		if(s == null || s.length() == 0){
			return "";
		}else{
			String result = "";
			String ss[] = s.split(delemeter);
			for(String str: ss){
				result += decode(str);
			}
			return result;
		}
    }
	
    public static String encode(String s){
        try{
            PublicEncryptUtil service = new PublicEncryptUtil();
            String content = s;
            String uniContent = java.net.URLEncoder.encode(content);
           /// System.out.println(uniContent);
            byte[] result = PublicEncryptUtil.encryptByPublicKey(uniContent);
            //System.out.println(new BASE64Encoder().encode(result));
            String str = new BASE64Encoder().encode(result);

//            System.out.println("原文：" + content);
//            System.out.println("加密结果：" + str);
            return str;
        }catch (Exception e){
            e.printStackTrace();
            return s;
        }

    }

    public static String decode(String s){
        String result = "";
        try{
            byte[] buf = PrivateDecryptUtil.decryptByPrivateKey(new BASE64Decoder().decodeBuffer(s));
            result = java.net.URLDecoder.decode(new String(buf));
            return result;
        }catch (Exception e){
            e.printStackTrace();
            return s;
        }
    }
    
    public static void main1(String[] args) {
//    	//String s = "3QBXEaHTy5j3fd1WnM2Lvz8OveTkQH2ZnrxNL13Hvk6eEq8lOj06kxCI6thC3IXgdr1vfbrfOVPZ1+naDCRWICJIO5ilEbE76P/0m4453JAudAZcF0scu0Z7fsumBa8gn4qh5D8Dlsv1QczGaaGufnOrJTIf/2RMsGGF1EIVc2M=";
		String s = "t72Md8P6BS7nrXV2pg5vQB1s0iydUMAZiHOW/G4NZUQT/oLVLyiJ9sJCr0YzuFW/AHjdFTYGOfpa4bRiMdVWI8edTfCZfFc9dNM+r767SJMB5XiJBBOKZzXXZKCKGdCxwP6QnQQuo9du69ZjX+bxESWEa1oJzTatLOH1fjbySc4=";
    	s = Crypt.decode(s);
		System.out.println("解密----" + s);
		
		//String s = Crypt.encode("dddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd");
		s = "fuqiang, come on, fuck you!";
    	s = Crypt.encode(s);
		System.out.println("加密----" + s);
	}
    
    public static void main(String[] args) {
    	//String s = "dddddd ddddd中文ddddsfa\" {}sd[]gw: qgw";
    	//String s = "{\"allPrice\":\"7000.0\",\"consultPrice\":\"5000\",\"isPay\":\"1\",\"orderNum\":\"\",\"payPrice\":\"33\",\"projectIds\":\"1,2\",\"remark";
		//String s= "{\"allPrice\":\"7000.0\",\"consultPrice";
    	String s = "{\"allPrice\":\"7000.0\",\"consultPrice\":\"5000\",\"isPay\":\"1\",\"orderNum\":\"\",\"payPrice\":\"33\",\"projectIds\":\"1,2\",\"remark\":\"哈哈哈哈哈哈哈哈hiJSP生生死死\",\"ryId\":\"1501157130255eff3879981e\",\"yuyueTime\":\"1449457200\"}";
    	System.out.println("加密长度--" + s.length());
    	String s1 = Crypt.encodeBySegment(s, 50, ",");
		System.out.println("加密--" + s1);
	}
    
    
}
