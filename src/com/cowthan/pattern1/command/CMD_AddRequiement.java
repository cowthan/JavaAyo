package com.cowthan.pattern1.command;

public class CMD_AddRequiement extends CMD{

	@Override
	public void execute() {
		this.rg.find();
		this.rg.add();
		this.rg.plan();
	}

}
