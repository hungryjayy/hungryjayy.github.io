package developer.rpc

import org.springframework.amqp.core.DirectExchange
import org.springframework.amqp.core.Queue
import org.springframework.amqp.rabbit.AsyncRabbitTemplate
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class Config {

    @Bean
    fun template(rabbitTemplate: RabbitTemplate?): AsyncRabbitTemplate? {
        return AsyncRabbitTemplate(rabbitTemplate)
    }

    @Bean
    fun queue(): Queue? {
        return Queue("")
    }

    @Bean // 이걸 지우면 NullPointerException (아마도 Client에서 DirectExchange 설정부분인가)
    fun exchange(): DirectExchange {
        return DirectExchange("rpc") // 이름이 안맞으면(server에서 명시한 exchange name과) server에 매칭 안됨(got null)
    }
}