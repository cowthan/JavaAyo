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
package okhttp3.recipes;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public final class PostFile {
  public static final MediaType MEDIA_TYPE_MARKDOWN
      = MediaType.parse("text/x-markdown; charset=utf-8");
  private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");


  private final OkHttpClient client = new OkHttpClient();

  public void run() throws Exception {
    File file = new File("README.md");

    String url = "http://124.133.240.71:8822/jjservice/service.ashx?action=suborder&userid=e5c9d39b-5232-4c14-bb83-b6b5f0d77ba0&address=&phone=&realname=&transportmoney=&productmoney=&ordermoney=&ids=&taobaohao=dd";
	File file2 = new File("test.png");
    System.out.println(file2.getAbsolutePath());
    
    Request request = new Request.Builder()
    //http://124.133.240.71:8822/jjservice/service.ashx?action=suborder&userid=e5c9d39b-5232-4c14-bb83-b6b5f0d77ba0&address=&phone=&realname=&transportmoney=&productmoney=&ordermoney=&ids=&taobaohao=dd
//        .url("https://api.github.com/markdown/raw")
    .url(url)
    //.post(RequestBody.create(MEDIA_TYPE_MARKDOWN, file))
        .post(RequestBody.create(MEDIA_TYPE_PNG, file2))
        .build();
    
    	

    Response response = client.newCall(request).execute();
    System.out.println(response.code());
    if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

    System.out.println("response = " + response.body().string());
  }

  public static void main(String... args) throws Exception {
    new PostFile().run();
  }
}
