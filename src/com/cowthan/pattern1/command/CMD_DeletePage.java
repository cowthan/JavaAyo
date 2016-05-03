package com.cowthan.pattern1.command;

public class CMD_DeletePage extends CMD{

	@Override
	public void execute() {
		this.pg.find();
		this.pg.delete();
		this.pg.plan();
	}

}
