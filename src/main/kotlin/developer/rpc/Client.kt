package developer.rpc

import io.netty.util.concurrent.Promise
import kotlinx.coroutines.runBlocking
import net.sf.json.JSON
import org.json.JSONObject
import org.springframework.amqp.core.*
import org.springframework.amqp.core.Queue
import org.springframework.amqp.rabbit.AsyncRabbitTemplate
import org.springframework.amqp.rabbit.AsyncRabbitTemplate.RabbitConverterFuture
import org.springframework.amqp.rabbit.connection.Connection
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.amqp.support.converter.MessageConverter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import java.util.*

//@Service // annotation for test. repository에 호출, business logic code에 사용.
@Component
class Client { // Producer(Sender) role

    @Autowired
    private val template: AsyncRabbitTemplate? = null

    @Autowired
    private val replyTo: Queue? = null

    @Autowired
    private val exchange: DirectExchange? = null // serverConfig(yaml에서 active시켜놓은 config에서의 exchange)

//    @Autowired
//    private val binding: Binding? = null

    @Autowired
    private val convert: MessageConverter? = null

    fun send(vararg args: Any, routingKey: String): JSONObject { // callRPC 역할
        println(" [x] Requesting fib(${args[0]})")

        val correlationId = UUID.randomUUID()

        val messagePostProcessor = MessagePostProcessor { message: Message -> // Sender와 receiver를 나눌 필요 있을 때 사용(원래는 AMQP가 해줌)
            val messageProperties = message.messageProperties                   // DB에 corrID 저장해야한다.
            messageProperties.replyTo = replyTo!!.name
            messageProperties.correlationId = correlationId.toString()
            message
        }

        //BindingBuilder.bind(replyTo).to(exchange).with(routingKey)

        //val arguments = JSONObject("""{"DD":"FF"}""")
        println(convert)
        println(exchange)
        println(replyTo)
        println(routingKey)
        println(template)
        val response = template!!.convertSendAndReceive<String>(exchange!!.name, routingKey, args, messagePostProcessor)

        val result = response.get()
        val json = JSONObject(result)

//        println(result)
        return json
    }
}