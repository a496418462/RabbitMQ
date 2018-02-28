package com.producer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;

public class RouteProducer {

    private static final String EXCHANGE = "logs";
    // 路由关键字
    private static final String[] routingKeys = new String[]{"info" ,"warning", "error"};

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        //声明交换机
        channel.exchangeDeclare(EXCHANGE,"direct");//注意是direct
        //发送信息
        for (String routingKey:routingKeys){
            String message = "RoutingSendDirect Send the message level:" + routingKey;
            channel.basicPublish(EXCHANGE,routingKey,null,message.getBytes());
            System.out.println("RoutingSendDirect Send"+routingKey +"':'" + message);
        }
        channel.close();
        connection.close();
    }
}
