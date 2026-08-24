package com.mustafaqasimov.fleettrack.config;

import com.mustafaqasimov.fleettrack.notification.VehicleOfflineListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

@Configuration
public class RedisPubSubConfig {

    public static final String VEHICLE_OFFLINE_CHANNEL = "vehicle-offline-channel";

    @Bean
    public ChannelTopic vehicleOfflineTopic(){
        return new ChannelTopic(VEHICLE_OFFLINE_CHANNEL);
    }

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory connectionFactory,
                                                                       ChannelTopic vehicleOfflineTopic,
                                                                       VehicleOfflineListener listener){
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(new MessageListenerAdapter(listener, "onMessage"), vehicleOfflineTopic);
        return container;
    }
}
