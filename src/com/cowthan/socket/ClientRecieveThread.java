package com.cowthan.socket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientRecieveThread extends Thread {
	private ChatApplet applet = null;
    private Socket socket = null;

    public ClientRecieveThread(Socket socket, ChatApplet applet) {
        this.socket = socket;
        this.applet = applet;
    }

    public void run() {
        BufferedReader in = null;
        String inputLine;
        try {
            in = new BufferedReader(
				    new InputStreamReader(
				    socket.getInputStream()));
            while ((inputLine = in.readLine()) != null) {
                //if (inputLine.equalsIgnoreCase("quit")) break;
                applet.appendMessage(inputLine + "\n");
            }
        }
        catch (Exception ex) {
            ex.printStackTrace(System.err);
        }
        finally {
            Close();
        }
    }

    void Close() {
        try {
            socket.close();
        }
        catch (Exception ex) {
            ex.printStackTrace(System.err);
        }
    }
}
