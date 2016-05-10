package com.cowthan.annotation;


public class UseCaseDemo {
	
	@UseCase(id = 1, description = "密码的格式必须规范")
	public boolean testPassword(String password){
		return false;
	}
	
	@UseCase(id = 2, description = "用户名的格式必须规范")
	public boolean testUsername(String username){
		return false;
	}
	
	@UseCase(id = 3, description = "新密码不能等于旧密码")
	public boolean testNewPwd(String password){
		return false;
	}
}
