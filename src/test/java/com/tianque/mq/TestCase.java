package com.tianque.mq;

import java.io.IOException;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;

/**
 * Created by 123 on 2017/12/27.
 */
public class TestCase {

    @Test
    public void sendMessageToQueue() throws JMSException {
        //1.创建ConnectionFactory
        String brokerUrl = "tcp://localhost:61616";
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerUrl);
        //2.创建Connection
        Connection connection = connectionFactory.createConnection();
        //启动连接
        connection.start();
        //3.创建Session(两个参数：第一个参数表示是否使用事务，第二个参数指定签收消息的模式)
        Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        //使用事务，并且客户端签收
        //Session session = connection.createSession(true,Session.CLIENT_ACKNOWLEDGE);
        //4.创建 Destination 目的地对象
        Destination destination = session.createQueue("test-Message");
        //5.创建消息的生产者
        MessageProducer messageProducer = session.createProducer(destination);
        //设置持久化模式，消息没有被消费时，activemq重启以后消息还存在，看消息的重要程度，防止消息丢失
        // messageProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        //6.创建消息
        TextMessage textMessage = session.createTextMessage("Hello,MQ");
        //7.发送消息
        messageProducer.send(textMessage);
       // session.commit();
        //8.释放资源
        messageProducer.close();
        session.close();
        connection.close();

    }

    @Test
    public void consumerMessageFromQueue() throws JMSException, IOException {
        //1.创建ConnectionFactory
        String brokerUrl = "tcp://localhost:61616";
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerUrl);
        //2.创建Connection
        Connection connection = connectionFactory.createConnection();
        //启动连接
        connection.start();
        //3.创建Session
        Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
       // Session session = connection.createSession(false,Session.CLIENT_ACKNOWLEDGE);
        //4.创建目的地
        Destination destination = session.createQueue("test-Message");
        //5.创建消费者
        MessageConsumer messageConsumer = session.createConsumer(destination);
        //6.消费消息,监听队列中的消息，如果有新的消息，则会执行onMessage方法
        messageConsumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                TextMessage textMessage = (TextMessage) message;
                try {
                    System.out.println(">>> " + textMessage.getText());

                    //手动签收，队列删除消息。开发中最后是手动签收，要确定消息的正确性。自动签收已消息到底为准，不保证消息的正确性
                    //textMessage.acknowledge();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
        System.in.read();
        //7.释放资源
        messageConsumer.close();
        session.close();
        connection.close();
    }

    @Test
    public void consumerMessageFromQueue2() throws JMSException, IOException {
        //1.创建ConnectionFactory
        String brokerUrl = "tcp://localhost:61616";
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerUrl);
        //2.创建Connection
        Connection connection = connectionFactory.createConnection();
        //启动连接
        connection.start();
        //3.创建Session
        Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        // Session session = connection.createSession(false,Session.CLIENT_ACKNOWLEDGE);
        //4.创建目的地
        Destination destination = session.createQueue("test-Message");
        //5.创建消费者
        MessageConsumer messageConsumer = session.createConsumer(destination);
        //6.消费消息,监听队列中的消息，如果有新的消息，则会执行onMessage方法
        messageConsumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                TextMessage textMessage = (TextMessage) message;
                try {
                    System.out.println(">>> " + textMessage.getText());

                    //手动签收，队列删除消息。开发中最后是手动签收，要确定消息的正确性。自动签收已消息到底为准，不保证消息的正确性
                    //textMessage.acknowledge();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
        System.in.read();
        //7.释放资源
        messageConsumer.close();
        session.close();
        connection.close();
    }


    //发布、订阅

    @Test
    public void sendMessageToTopic() throws JMSException {
        //1.创建ConnectionFactory
        String brokerUrl = "tcp://localhost:61616";
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerUrl);
        //2.创建Connection
        Connection connection = connectionFactory.createConnection();
        //启动连接
        connection.start();
        //3.创建Session(两个参数：第一个参数表示是否使用事务，第二个参数指定签收消息的模式)
        Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        //使用事务，并且客户端签收
        //Session session = connection.createSession(true,Session.CLIENT_ACKNOWLEDGE);
        //4.创建 Destination 目的地对象
        Destination destination = session.createTopic("test-Message-topic");
        //5.创建消息的生产者
        MessageProducer messageProducer = session.createProducer(destination);
        //设置持久化模式，消息没有被消费时，activemq重启以后消息还存在，看消息的重要程度，防止消息丢失
        // messageProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        //6.创建消息
        TextMessage textMessage = session.createTextMessage("Hello,MQ----");
        //7.发送消息
        messageProducer.send(textMessage);
        // session.commit();
        //8.释放资源
        messageProducer.close();
        session.close();
        connection.close();

    }

    @Test
    public void consumerMessageFromTopic() throws JMSException, IOException {
        //1.创建ConnectionFactory
        String brokerUrl = "tcp://localhost:61616";
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerUrl);
        //2.创建Connection
        Connection connection = connectionFactory.createConnection();
        //启动连接
        connection.start();
        //3.创建Session
        Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        // Session session = connection.createSession(false,Session.CLIENT_ACKNOWLEDGE);
        //4.创建目的地
        Destination destination = session.createTopic("test-Message-topic");
        //5.创建消费者
        MessageConsumer messageConsumer = session.createConsumer(destination);
        //6.消费消息,监听队列中的消息，如果有新的消息，则会执行onMessage方法
        messageConsumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                TextMessage textMessage = (TextMessage) message;
                try {
                    System.out.println(">>> " + textMessage.getText());

                    //手动签收，队列删除消息。开发中最后是手动签收，要确定消息的正确性。自动签收已消息到底为准，不保证消息的正确性
                    //textMessage.acknowledge();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
        System.in.read();
        //7.释放资源
        messageConsumer.close();
        session.close();
        connection.close();
    }

    @Test
    public void consumerMessageFromTopic2() throws JMSException, IOException {
        //1.创建ConnectionFactory
        String brokerUrl = "tcp://localhost:61616";
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerUrl);
        //2.创建Connection
        Connection connection = connectionFactory.createConnection();
        //启动连接
        connection.start();
        //3.创建Session
        Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        // Session session = connection.createSession(false,Session.CLIENT_ACKNOWLEDGE);
        //4.创建目的地
        Destination destination = session.createTopic("test-Message-topic");
        //5.创建消费者
        MessageConsumer messageConsumer = session.createConsumer(destination);
        //6.消费消息,监听队列中的消息，如果有新的消息，则会执行onMessage方法
        messageConsumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                TextMessage textMessage = (TextMessage) message;
                try {
                    System.out.println(">>> " + textMessage.getText());

                    //手动签收，队列删除消息。开发中最后是手动签收，要确定消息的正确性。自动签收已消息到底为准，不保证消息的正确性
                    //textMessage.acknowledge();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
        System.in.read();
        //7.释放资源
        messageConsumer.close();
        session.close();
        connection.close();
    }

}
