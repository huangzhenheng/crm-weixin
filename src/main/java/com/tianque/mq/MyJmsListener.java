package com.tianque.mq;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.jms.annotation.JmsListener;

/**
 * Created by 123 on 2017/12/27.
 */
public class MyJmsListener {

    @JmsListener(destination = "message-queue",containerFactory = "jmsListenerContainerFactory")
    public void getMessageFromQueue(Message message, Session session) {
        TextMessage textMessage = (TextMessage) message;
        try {
            System.out.println("From queue message -> " + textMessage.getText());
            if(1==1) {
                throw new JMSException("");
            }
            message.acknowledge();
        } catch (JMSException e) {
            e.printStackTrace();
            try {
                session.recover();
            } catch (JMSException e1) {
                e1.printStackTrace();
            }
        }
    }

    @JmsListener(destination = "message-topic",containerFactory = "jmsTopicListenerContainerFactory")
    public void getMessageFromTopic(String message) {
        System.out.println("From topic message -> " + message);
    }

}
