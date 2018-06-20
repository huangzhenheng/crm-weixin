package com.tianque.mq;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.stereotype.Component;

import com.google.gson.Gson;

/**
 * Created by 123 on 2017/12/27.
 */
@Component
public class MyQueueListener implements MessageListener {
    @Override
    public void onMessage(Message message) {

        if (message instanceof TextMessage){
            TextMessage textMessage = (TextMessage) message;
            try {
                if (textMessage.getJMSType().equals("user")){
                    System.out.println(textMessage.getJMSType());
                }
            } catch (JMSException e) {
                e.printStackTrace();
            }

            try {
                User  user=new Gson().fromJson(textMessage.getText(),User.class);
                System.out.println(user.getName());
            } catch (JMSException e) {
                e.printStackTrace();
            }

        }

    }
}
