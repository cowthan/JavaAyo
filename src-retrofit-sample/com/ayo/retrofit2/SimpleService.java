/*
 * Copyright (C) 2012 Square, Inc.
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
package com.ayo.retrofit2;

import java.io.IOException;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Path;

public final class SimpleService {
  public static final String API_URL = "https://api.github.com";

  public static class Contributor {
    public final String login;
    public final int contributions;

    public Contributor(String login, int contributions) {
      this.login = login;
      this.contributions = contributions;
    }
  }

  public interface GitHub {
    @GET("/repos/{owner}/{repo}/contributors")
    Call<List<Contributor>> contributors(
        @Path("owner") String owner,
        @Path("repo") String repo);
  }

  public static void main(String... args) throws IOException {
    // Create a very simple REST adapter which points the GitHub API.
    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(API_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build();

    // Create an instance of our GitHub API interface.
    GitHub github = retrofit.create(GitHub.class);

    // Create a call instance for looking up Retrofit contributors.
    Call<List<Contributor>> call = github.contributors("square", "retrofit");
    
    //----------------------------------------
    //异步方式：请求完这一个，程序还不退出，说明开了个主循环线程
    //---------------------------------------
    call.enqueue(new Callback<List<Contributor>>(){

		@Override
		public void onResponse(Call<List<Contributor>> call, Response<List<Contributor>> response) {
			for (Contributor contributor : response.body()) {
			      System.out.println(contributor.login + " (" + contributor.contributions + ")");
			    }
		}

		@Override
		public void onFailure(Call<List<Contributor>> call, Throwable t) {
			
		}
    	
    });

    //---------------------------------------
    //同步方式
    //---------------------------------------
//    List<Contributor> contributors = call.execute().body();
//    for (Contributor contributor : contributors) {
//      System.out.println(contributor.login + " (" + contributor.contributions + ")");
//    }
  }
}
