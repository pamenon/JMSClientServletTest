package org.pamenon.artemisclient;

import java.io.IOException;
import java.io.PrintWriter;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;

@WebServlet("/ArtemisClient")
public class ArtemisJMSClientServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            InitialContext initCtx = new InitialContext();
            Context envContext = (Context) initCtx.lookup("java:comp/env");
            ConnectionFactory connectionFactory = (ConnectionFactory) envContext.lookup("jms/ConnectionFactory");
            Connection connection = connectionFactory.createConnection();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer producer = session.createProducer((Destination) envContext.lookup("jms/topic/MyTopic"));
        
            Message testMessage = session.createMessage();
            testMessage.setStringProperty("testKey", "testValue");
            producer.send(testMessage);
        } catch (NamingException e) {
            // TODO handle exception
        } catch (JMSException e) {
            // TODO handle exception
        }
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<h3>Hello World!</h3>");
    }
}
