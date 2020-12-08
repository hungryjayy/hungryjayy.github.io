package developer.rpc

import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.DirectExchange
import org.springframework.amqp.core.Queue
import org.springframework.amqp.rabbit.AsyncRabbitTemplate
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.amqp.support.converter.MessageConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

//@Profile("rpc") // 실행시 여기로 전달(yaml active conditons / 런타임 환경 설정)
@Configuration
class Config {

    //@Profile("client")
    class ClientConfig {
//        @Bean
//        fun exchange(): DirectExchange {
//            return DirectExchange("rpc")
//        }

        @Bean
        fun template(rabbitTemplate: RabbitTemplate?): AsyncRabbitTemplate? {
            return AsyncRabbitTemplate(rabbitTemplate)
        }

    }

    class ServerConfig {
        @Bean
        fun jackson2MessageConverter(): MessageConverter? {
            return Jackson2JsonMessageConverter()
        }

        @Bean
        fun queue(): Queue? {
            return Queue("clientQueue")
        }

        @Bean // 이걸 지우면 NullPointerException (아마도 Client에서 DirectExchange 설정부분인가)
        fun exchange(): DirectExchange {
            return DirectExchange("rpc") // 이름이 안맞으면(server에서 명시한 exchange name과) server에 매칭 안됨(got null)
        }

        /*@Bean
        fun binding(exchange: DirectExchange?,
                    queue: Queue?): Binding? {
            return BindingBuilder.bind(queue)
                    .to(exchange)
                    .with("third")
        }*/
    }
}