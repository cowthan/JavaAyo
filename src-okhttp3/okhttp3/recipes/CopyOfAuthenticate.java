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
import okhttp3.Authenticator;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

public final class CopyOfAuthenticate {
	private final OkHttpClient client;

	public CopyOfAuthenticate() {

		// /你请求一个页面，会提示你输入用户名密码，然后提交用户名密码，再发起一次请求，，这个例子就是解决这个问题
		client = new OkHttpClient.Builder().authenticator(new Authenticator() {
			@Override
			public Request authenticate(Route route, Response response)
					throws IOException {
				System.out.println("Authenticating for response: " + response);
				System.out.println("Challenges: " + response.challenges());
				String credential = Credentials.basic("admin22222", "111111");
				return response.request().newBuilder()
						.header("Authorization", credential).build();
			}
		})
		.build();
	}

	public void run() throws Exception {
		String credential = Credentials.basic("admin22222", "111111");
		System.out.println(credential);  //Basic YWRtaW4yMjIyMjoxMTExMTE=
		Request request = new Request.Builder().url(
				"http://172.16.12.191:9090/plugins/restapi/v1/users")
				.header("Authorization", credential)
				.build();

		Response response = client.newCall(request).execute();
		if (!response.isSuccessful())
			throw new IOException("Unexpected code " + response);

		System.out.println(response.body().string());//
	}

	public static void main(String... args) throws Exception {
		new CopyOfAuthenticate().run();
	}
}
