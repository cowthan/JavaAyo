package com.cowthan.socket;

public class Protocol {
	private String userid;

    public Protocol(String userid) {
        this.userid = userid;
    }

    public String processInput(String input) {
        return (userid + ": " + input);
    }
}
