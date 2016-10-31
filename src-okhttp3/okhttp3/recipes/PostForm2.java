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

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public final class PostForm2 {
  private final OkHttpClient client = new OkHttpClient();

  public void run() throws Exception {
    RequestBody formBody = new FormBody.Builder()
        .add("username", "Jaaaaaa")
        .add("password", "111111")
        .build();
    
    RequestBody requestBody = new MultipartBody.Builder()
	    .setType(MultipartBody.FORM)
	    .addFormDataPart("username", "Jaaaaaa")
	    .addFormDataPart("password", "111111")
	    .build();
    
    Request request = new Request.Builder()
        .url("http://127.0.0.1:81/user/create")
        .post(formBody)
        .build();
    
    Response response = client.newCall(request).execute();
    if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

    System.out.println(response.body().string());
  }

  public static void main(String... args) throws Exception {
    new PostForm2().run();
  }
}
