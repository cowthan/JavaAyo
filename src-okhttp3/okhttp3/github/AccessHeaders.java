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
package okhttp3.github;

import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public final class AccessHeaders {
  private final OkHttpClient client = new OkHttpClient();

  public void run(String url) throws Exception {
    Request request = new Request.Builder()
        .url(url)
        .header("User-Agent", "OkHttp Headers.java")
        .addHeader("Accept", "application/json; q=0.5")
        .addHeader("Accept", "application/vnd.github.v3+json")
        .build();

    Response response = client.newCall(request).execute();
    if (!response.isSuccessful()) {
        System.out.println("Code: " + response.code());
        System.out.println("response: " + response.body().string());
    	throw new IOException("Unexpected code " + response);
    }else{
    	System.out.println("Server: " + response.header("Server"));
        System.out.println("Date: " + response.header("Date"));
        System.out.println("Vary: " + response.headers("Vary"));
        System.out.println("response: " + response.body().string());
    }

  }

  public static void main(String... args) throws Exception {
	    new AccessHeaders().run("https://api.github.com/repos/square/okhttp/issues");
	    
	    System.out.println("\n-------------给一个不存在的username，看github返回什么");
	    new AccessHeaders().run("https://api.github.com/repos/squared/okhttp/issues");
  }
}
