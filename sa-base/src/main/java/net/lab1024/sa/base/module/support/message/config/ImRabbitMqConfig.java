package net.lab1024.sa.base.module.support.message.config;

import lombok.Data;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(ImRabbitMqConfig.ImProperties.class)
@ConditionalOnProperty(prefix = ImRabbitMqConfig.ImProperties.CONFIG_PREFIX, name = "enabled", havingValue = "true", matchIfMissing = true)
public class ImRabbitMqConfig {

    @Bean
    public TopicExchange imExchange(ImProperties properties) {
        return new TopicExchange(properties.getExchange(), true, false);
    }

    @Bean
    public Queue imUserQueue(ImProperties properties) {
        return QueueBuilder.durable(properties.getUserQueue()).build();
    }

    @Bean
    public Queue imAdminQueue(ImProperties properties) {
        return QueueBuilder.durable(properties.getAdminQueue()).build();
    }

    @Bean
    public Binding imUserBinding(Queue imUserQueue, TopicExchange imExchange, ImProperties properties) {
        return BindingBuilder.bind(imUserQueue).to(imExchange).with(properties.getUserRoutingKey());
    }

    @Bean
    public Binding imAdminBinding(Queue imAdminQueue, TopicExchange imExchange, ImProperties properties) {
        return BindingBuilder.bind(imAdminQueue).to(imExchange).with(properties.getAdminRoutingKey());
    }

    @Data
    @ConfigurationProperties(prefix = ImProperties.CONFIG_PREFIX)
    public static class ImProperties {

        public static final String CONFIG_PREFIX = "smart.im";

        private Boolean enabled = true;

        private String exchange = "smart.im.exchange";

        private String userQueue = "smart.im.user.queue";

        private String adminQueue = "smart.im.admin.queue";

        private String userRoutingKey = "smart.im.user";

        private String adminRoutingKey = "smart.im.admin";
    }
}
