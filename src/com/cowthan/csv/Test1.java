package com.cowthan.csv;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.ayo.lang.JsonUtilsUseFast;


/**
 * 读取QQ同步助手导出的通讯录csv文件，转成json
 * 
 * @author Administrator
 *
 */
public class Test1 {
	
	public static class Contact{
		public String familyName;
		public String givenName;
		public String phoneType1;
		public String phone1;
		public String phoneType2;
		public String phone2;
	}

	public static void main(String[] args) {
		readeCsv();
		
	}

	public static void readeCsv() {
		try {
			System.out.println(Thread.currentThread().getContextClassLoader().getResource(""));
			System.out.println(Test1.class.getClassLoader().getResource(""));
			ArrayList<String[]> csvList = new ArrayList<String[]>(); // 用来保存数据
			String csvFilePath = Test1.class.getClassLoader().getResource("") + "com/cowthan/csv/contact-91.csv";
			URI uri = URI.create(csvFilePath);
			File f = new File(uri.getPath());

			CsvReader reader = new CsvReader(f.getAbsolutePath(), ',', Charset.forName("gbk")); // 一般用这编码读就可以了

			reader.readHeaders(); // 跳过表头 如果需要表头的话，不要写这句。

			while (reader.readRecord()) { // 逐行读入除表头的数据
				csvList.add(reader.getValues());
			}
			reader.close();

			List<Contact> list = new ArrayList<>();
			for (int row = 0; row < csvList.size(); row++) {
				Contact c = new Contact();
				c.familyName = csvList.get(row)[0].replace("\t", ""); 
				c.givenName = csvList.get(row)[1].replace("\t", "");
				c.phoneType1 = csvList.get(row)[4].replace("\t", "");
				c.phone1 = csvList.get(row)[5].replace("\t", "");
				c.phoneType2 = csvList.get(row)[6].replace("\t", "");
				c.phone2 = csvList.get(row)[7].replace("\t", "");
				list.add(c);
			}
			System.out.println(JsonUtilsUseFast.toJson(list));

		} catch (Exception ex) {
			System.out.println(ex);
		}
	}

	/**
	 * 写入CSV文件
	 */
	public void writeCsv() {
		try {

			String csvFilePath = "c:/test.csv";
			CsvWriter wr = new CsvWriter(csvFilePath, ',', Charset.forName("SJIS"));
			String[] contents = { "aaaaa", "bbbbb", "cccccc", "ddddddddd" };
			wr.writeRecord(contents);
			wr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
