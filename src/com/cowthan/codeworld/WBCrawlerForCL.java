/*
 * Copyright (C) 2014 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.cowthan.codeworld;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import org.ayo.io.Files;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import okhttp3.Cache;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.NamedRunnable;


/**
 * 草榴社区的爬虫，抓图片，生成json
 */
public final class WBCrawlerForCL {

	private final OkHttpClient client;
	private final Set<HttpUrl> fetchedUrls = Collections.synchronizedSet(new LinkedHashSet<HttpUrl>());
	private final LinkedBlockingQueue<HttpUrl> queue = new LinkedBlockingQueue<>();
	private final ConcurrentHashMap<String, AtomicInteger> hostnames = new ConcurrentHashMap<>();
	
	private final Map<String, String> pastUrls= new HashMap<String, String>();
	
	public WBCrawlerForCL(OkHttpClient client) {
		this.client = client;
	}

	private void parallelDrainQueue(int threadCount) {
		ExecutorService executor = Executors.newFixedThreadPool(threadCount);
		for (int i = 0; i < threadCount; i++) {
			executor.execute(new NamedRunnable("Crawler %s", i) {
				@Override
				protected void execute() {
					try {
						drainQueue();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}
		executor.shutdown();
	}

	private void drainQueue() throws Exception {
		for (HttpUrl url; (url = queue.take()) != null;) {
			if (!fetchedUrls.add(url)) {
				continue;
			}

			try {
				fetch(url);
			} catch (IOException e) {
				System.out.printf("XXX: %s %s%n", url, e);
			}
		}
	}

	int count = 0;

	public void fetch(HttpUrl url) throws IOException {
		count++;
		System.out.println("抓--" + url.toString());
		// Skip hosts that we've visited many times.
//		AtomicInteger hostnameCount = new AtomicInteger();
//		AtomicInteger previous = hostnames.putIfAbsent(url.host(), hostnameCount);
//		if (previous != null)
//			hostnameCount = previous;
//		if (hostnameCount.incrementAndGet() > 100)
//			return; // 遍历过100个域名，就跳出，有道理啊

		Request request = new Request.Builder().url(url).build();
		Response response = client.newCall(request).execute();
		String responseSource = response.networkResponse() != null
				? ("(network: " + response.networkResponse().code() + " over " + response.protocol() + ")") : "(cache)";
		int responseCode = response.code();

		//System.out.printf("%03d: %s %s%n", responseCode, url, responseSource);

		String contentType = response.header("Content-Type");
		if (responseCode != 200 || contentType == null) {
			response.body().close();
			return;
		}

		Document document = Jsoup.parse(response.body().string(), url.toString());

		for (Element element : document.getAllElements()) {
			
			
			if (element.tagName().equals("input") && element.hasAttr("src")) {
				// <input src="image url" />
				String src = element.attr("src");
				String title = pastUrls.get(url.toString());
				if(title == null) title = "mess";
				ImageRepo.add(count+"", src);
			}
			
			if(element.tagName().equals("a") && element.hasAttr("href") && element.parent().tagName().equals("h3")){
				// <h3> <a href="">item</a> </h3>
				String href = element.attr("href");
				String title = element.text();
//				if(title != null){
//					title = new String(element.text().getBytes("iso8859-1"), "utf-8");
//				}
				System.out.println(href);
				pastUrls.put("http://cl.oxox.lol/" + href, title);
				HttpUrl link = response.request().url().resolve(href);
				if (link != null)
					queue.add(link);
			}
			
			//<div class="pages"> <a><a>...<a><a id=last></div>   the second last <a> is our nextpage
//			if(element.tagName().equals("a") 
//					&& element.hasAttr("href") 
//					&& element.attr("id").equals("last")){
//
//				String href = element.firstElementSibling().attr("href");
//				System.out.println("aaaaaaaaaaaaaaaaaaa--" + href);
//				HttpUrl link = response.request().url().resolve(href);
//				if (link != null)
//					queue.add(link);
//			}
			
			//http://cl.oxox.lol/thread0806.php?fid=8&search=&page=2
			if(count < 200){
				HttpUrl link = response.request().url().resolve("http://cl.oxox.lol/thread0806.php?fid=8&search=&page=" + count);
				if (link != null)
					queue.add(link);
			}
		}

		// 只取图片
		// for (Element element : document.select("input[src]")) {
		// String src = element.attr("src");
		// ImageRepo.add(src);
		// }
		//
		// 遍历下一级页面：只遍历h3标签下面的a标签，和下一页按钮：a标签 文字是下一頁
//		for (Element element : document.select("h3 a[href]")) {
//			String href = element.attr("href");
//			System.out.println("抓" + element.text());
//			HttpUrl link = response.request().url().resolve(href);
//			if (link != null)
//				queue.add(link);
//		}

		// 下一页按钮：a标签 文字是下一頁
		// for (Element element : document.select("a:containsOwn(下一頁)")) {
		// String href = element.attr("href");
		// System.out.println("找到下一页了--" + href);
		// HttpUrl link = response.request().url().resolve(href);
		// if (link != null) queue.add(link);
		// }

	}

	public static void main(String[] args) throws IOException {
		// if (args.length != 2) {
		// System.out.println("Usage: Crawler <cache dir> <root>");
		// return;
		// }

		// 在项目的根目录下，创建了一个文件夹， crawler.tmp，里面存的是缓存，实在找不到你可以用everything搜
		args = new String[2];
		args[0] = "C:\\Users\\Administrator\\Desktop\\login\\crawler";
		args[1] = "http://cl.oxox.lol/thread0806.php?fid=8";
		//http://cl.oxox.lol/thread0806.php?fid=8&search=&page=2
		int threadCount = 1;
		long cacheByteCount = 1024L * 1024L * 100L;

		Cache cache = new Cache(new File(args[0]), cacheByteCount);
		OkHttpClient client = new OkHttpClient.Builder().cache(cache).build();

		WBCrawlerForCL crawler = new WBCrawlerForCL(client);
		crawler.queue.add(HttpUrl.parse(args[1]));
		crawler.parallelDrainQueue(threadCount);
		
	}

	/**
	 * 图片仓库，内存里的，其实就他妈是个List，不断往里add新抓到的图，
	 * 
	 * @author Administrator
	 *
	 */
	public static class ImageRepo {
		
		private static List<String> urls = new ArrayList<String>();
		private static String last_title = "aaaaaaabbbbbccccccc";

		public synchronized static void add(String title, String url) {
			
			//System.out.println("+++add new url+++  " + title + "  " + url);

			if(last_title.equals(title)){
				urls.add(url);
			}else{
				//mk a new dir, save last title
				if(urls.size() > 0){
					Piper.saveToHtml(title, urls);
					Piper.saveToJson(urls);
				}
				urls = new ArrayList<String>();
				
				//start new
				last_title = title;
				urls = new ArrayList<String>();
				urls.add(url);
				
			}
			
//			urls.add(url);
//			if (urls.size() >= 200) {
//				Piper.saveToHtml(urls);
//				urls = new ArrayList<String>();
//			}

		}

	}

	public static class Piper {

		private static String htmlTmpl = "<!DOCTYPE html><html lang='en'><head><meta charset='utf-8'><title>sexy</title><meta name='description' content='slick Login'>"
				+ "<meta name='author' content='Webdesigntuts+'><link rel='stylesheet' type='text/css' href='css/style.css' /></head>"
				+ "<body><!-- images here  --></body></html>";
		static String placeHolder = "<!-- images here  -->";
		static String tag = "<img src='{img}' /><br />";

		static String saveDir = "C:\\Users\\Administrator\\Desktop\\login\\";

		public static void saveToHtml(String filename, List<String> list) {

			String imgs = "";
			for (String s : list) {
				String img = tag.replace("{img}", s);
				imgs += img;
			}

			String html = htmlTmpl.replace(placeHolder, imgs);

			Files.steam.output(html, saveDir + filename + ".html", false);

		}
		
		private static List<List<String>> lists = new ArrayList<List<String>>();
		public synchronized static void saveToJson(List<String> list){
			
			lists.add(list);
			if(lists.size() == 10){
				String dirPath = "C:\\Users\\Administrator\\Desktop\\weibo-json\\real-cl2";
				java.util.Random r = new java.util.Random();
				r.setSeed(System.currentTimeMillis());
				Utils.generateTimeLine2(dirPath, lists, 100, r);
				lists.clear();
			}else{
				return;
			}
		}

	}
}
