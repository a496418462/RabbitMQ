package com.producer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class FanoutProducer {

    private static final String EXCHANGE = "exchange";
    public static void main(String[] args) throws Exception{
        ConnectionFactory factory=new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection=factory.newConnection();
        Channel channel=connection.createChannel();

        channel.exchangeDeclare(EXCHANGE,"fanout");//fanout表示分发，所有的消费者得到同样的队列信息
        //分发信息
        for (int i=0;i<5;i++){
            String message="Hello World"+i;
            channel.basicPublish(EXCHANGE,"",null,message.getBytes());
            System.out.println("EmitLog Sent '" + message + "'");
        }
        channel.close();
        connection.close();
    }
}
