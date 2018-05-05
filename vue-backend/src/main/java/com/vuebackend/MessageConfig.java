package com.vuebackend;

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
public class MessageConfig {

    @Autowired
    private static UserRepository unserRepository;

    String[] getQueueNames() {
        List<String> queueNames = new ArrayList<>();
        Iterable<User> users;
        try {
            users = unserRepository.findAll();
        } catch (NullPointerException e) {
            System.out.println("cauht");
            queueNames.add("broadcast");
            return  queueNames.toArray(new String[queueNames.size()]);
        }

        if(users != null) {
            for(User user : unserRepository.findAll()) {
                queueNames.add(user.getId().toString());
            }
        }

        queueNames.add("broadcast");
        return queueNames.toArray(new String[queueNames.size()]);
    }

    @Bean
    List<Queue> queues() {
        List<Queue> queues = new ArrayList<Queue>();
        for (String queueName : getQueueNames()) {
            Queue queue  = new Queue(queueName, false);
            queues.add(queue);
        }
        queues.add(new Queue("broadcast"));
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
        container.setQueueNames(getQueueNames());
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(MessageReceiver receiver) {
        return new MessageListenerAdapter(receiver, "receivedMessage");
    }

    @Bean
    public RabbitTemplate template(ConnectionFactory connectionFactory) {
        return new RabbitTemplate(connectionFactory);
    }
}