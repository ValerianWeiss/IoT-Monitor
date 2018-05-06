package com.communication;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

import com.dbrepositories.UserRepository;
import com.entities.User;

@Configuration
public class RabbitConfig {

    @Autowired
    private UserRepository userRepository;


    @Bean
    List<Queue> queues() {
        List<Queue> queues = new ArrayList<Queue>();
        List<String> queueNames = new ArrayList<>();

        for(User user : userRepository.findAll()) {
            queueNames.add(user.getId().toString());
        }
        
        queueNames.add("broadcast");

        for (String queueName : queueNames) {
            Queue queue  = new Queue(queueName, false);
            queues.add(queue);
        }
        return queues;
    }

    @Bean
    List<TopicExchange> exchanges() {
        List<TopicExchange> exchanges = new ArrayList<>();
        exchanges.add(new TopicExchange("posts"));
        return exchanges;
    }

    @Bean
    List<Binding> binding(List<Queue> queues, List<TopicExchange> exchanges) {
        List<Binding> bindings = new ArrayList<>();
        for (Queue queue : queues) {
            for(TopicExchange exchange : exchanges)
            bindings.add(BindingBuilder.bind(queue).to(exchange).with("topic"));
        }
        return bindings;
    }

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
                                             MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        
        List<Queue> queues = queues();
        List<String> queueNames = new ArrayList<>();
        
        for (Queue queue : queues) {
            queueNames.add(queue.getName());    
        }

        container.setQueueNames(queueNames.toArray(new String[queueNames.size()]));
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(RabbitMessageReceiver receiver) {
        return new MessageListenerAdapter(receiver, "onMessage");
    }

    @Bean
    public RabbitTemplate template(ConnectionFactory connectionFactory) {
        return new RabbitTemplate(connectionFactory);
    }
}