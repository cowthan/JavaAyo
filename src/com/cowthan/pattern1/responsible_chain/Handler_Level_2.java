package com.cowthan.pattern1.responsible_chain;

/**
 * 只处理level为1的请求
 *
 */
public class Handler_Level_2 extends Handler{


	@Override
	public Response handle(Request req) {
		Response resp = new Response();
		resp.info = "request level = 2， 已被处理";
		return resp;
	}

	@Override
	protected int getLevel() {
		return 2;
	}


}
