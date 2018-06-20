package com.tianque.mq;

import java.io.IOException;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.command.ActiveMQTopic;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.gson.Gson;

/**
 * Created by 123 on 2017/12/27.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "applicationContext-activemq.xml")
public class SpringJmsTestCase {


    @Autowired
    private JmsTemplate jmsTemplate;

    @Test
    public void sendMessageToQueue() throws IOException {
    	

        jmsTemplate.send(new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
				TextMessage textMessage = session.createTextMessage(new Gson().toJson(new User("Tom", 23)));
                textMessage.setJMSType("user");
                return textMessage;
            }
        });
    }

    @Test
    public void sendMessageToTopic() throws IOException {
      ActiveMQTopic topic = new ActiveMQTopic("message-topic");
        jmsTemplate.send(topic, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                TextMessage textMessage = session.createTextMessage("Hello,Spring-Topic");

                return textMessage;
            }
        });
        //System.in.read();
    }
}


