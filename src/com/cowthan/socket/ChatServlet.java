package com.cowthan.socket;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ChatServlet extends HttpServlet {
	private static final String CONTENT_TYPE = "text/html";
    /**Initialize global variables*/
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        try {
            new Server();
        }
        catch (IOException ex) {
            System.err.println("IO ����");
            ex.printStackTrace(System.err);
            destroy();
        }
    }
    /**Process the HTTP Get request*/
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head><title>ChatServlet</title></head>");
        out.println("<body>");
        out.println("<p>The servlet has received a GET. This is the reply.</p>");
        out.println("</body></html>");
    }
    /**Clean up resources*/
    public void destroy() {
    }
}
