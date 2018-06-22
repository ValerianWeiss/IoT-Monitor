package com.vuebackend.controllers;

import com.vuebackend.communication.DataPoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ValueGenerator {

    @Autowired
    WebSocketController webSocket;
    
    @Bean
    public int startSending() {
        Thread one = new Thread(new Sender("graph/rand",30,10,1000));
        Thread two = new Thread(new Sender("graph/test",5,50,2000));
        one.start();
        two.start();
        return 0;
    }

    private class Sender implements Runnable {

        private String topic;
        private int range;
        private int offset;
        private int interval;

        public Sender(String topic, int range, int offset, int interval) {
            this.topic = topic;
            this.range = range;
            this.offset = offset;
            this.interval = interval;
        }

		@Override
		public void run() {
            while(true) {
                double value = Math.random() * this.range + this.offset;
                webSocket.send(topic, new DataPoint(value));
                try {
                    Thread.sleep(this.interval);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}