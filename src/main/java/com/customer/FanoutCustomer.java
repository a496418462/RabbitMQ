package com.customer;

import com.rabbitmq.client.*;

import java.io.IOException;

public class FanoutCustomer {
    private static final String EXCHANGE = "exchange";
    public static void main(String[] args) throws Exception{
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE, "fanout");

        //产生一个随机的队列名称
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, EXCHANGE, "");//对队列进行绑定

        System.out.println("ReceiveLogs1 Waiting for messages");
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println("ReceiveLogs1 Received '" + message + "'");
            }
        };
        channel.basicConsume(queueName, true, consumer);//队列会自动删除
    }
}
