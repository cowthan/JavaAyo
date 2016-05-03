package com.cowthan.socket;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
	Socket socket = null;
    private String host;
    private boolean connected = false;

    public boolean isConnected() { return connected; }

    public Client(ChatApplet applet) {
        try {
            host = applet.getDocumentBase().getHost();
            //host = "202.115.4.246";
            socket = new Socket(host, Server.port);
            connected = true;
            new ClientRecieveThread(socket, applet).start();
        }
        catch (Exception ex) {
            applet.appendMessage(ex.getMessage());
            ex.printStackTrace(System.err);
        }
    }

    public PrintWriter getOutputStream() throws IOException {
        return new PrintWriter(socket.getOutputStream(), true);
    }
}
