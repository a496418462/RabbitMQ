package com.producer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;

public class TopicProducer {

    private static final String EXCHANGE = "topic_test";

    public static void main(String[] args) throws Exception {
        Connection connection = null;
        Channel channel = null;
        try{
            ConnectionFactory factory=new ConnectionFactory();
            factory.setHost("localhost");
            connection=factory.newConnection();
            channel=connection.createChannel();

            //声明一个匹配模式的交换机
            channel.exchangeDeclare(EXCHANGE,"topic");
            //待发送的消息
            String[] routingKeys=new String[]{
                    "blackboard.black.one",
                    "paper.write.one",
                    "mouse.orange.four",
                    "fox.black.two",
                    "mouse.black.one",
                    "mouse.orange.a.two",
                    "male.orange.a.four"
            };
            //发送消息
            for(String severity :routingKeys){
                String message = "From "+severity+" routingKey' s message!";
                channel.basicPublish(EXCHANGE, severity, null, message.getBytes());
                System.out.println("TopicSend Sent '" + severity + "':'" + message + "'");
            }
        }catch (Exception e){
            e.printStackTrace();
            if (connection!=null){
                channel.close();
                connection.close();
            }
        }finally {
            if (connection!=null){
                channel.close();
                connection.close();
            }
        }
    }
}
