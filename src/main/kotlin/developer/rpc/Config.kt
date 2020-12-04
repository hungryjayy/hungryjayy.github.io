package developer.rpc

import org.springframework.amqp.core.DirectExchange
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile


@Profile("rpc") // 실행시 여기로 전달(yaml active conditons / 런타임 환경 설정)
@Configuration
class Config {

    @Profile("server")
    class ServerConfig {

        @Bean // 이걸 지우면 NullPointerException (아마도 Client에서 DirectExchange 설정부분인가)
        fun exchange(): DirectExchange {
            return DirectExchange("rpc") // 이름이 안맞으면(server에서 명시한 exchange name과) server에 매칭 안됨(got null)
        }

        @Bean // 이걸 지우면 server를 못찾는 것 같다 (got null)-> server에 match 안됨.
        fun server(): Server {
            return Server()
        }
    }
}