package developer.rpc

import net.sf.json.JSON
import org.json.JSONObject
import org.springframework.amqp.core.*
import org.springframework.amqp.rabbit.annotation.*
import org.springframework.amqp.rabbit.annotation.Exchange
import org.springframework.amqp.rabbit.annotation.Queue
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.amqp.support.converter.MessageConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component

@Component
class Server{ // Consumer(Receiver) role (Conference.js와 같은)

    //@SendTo("rpc") // used when the client doesn't set replyTo.
    @RabbitListener(bindings = [QueueBinding(
            value = Queue( ""), // durable을 통해 죽어도 사라지지 않고 보존
            exchange = Exchange(name = "rpc", type = ExchangeTypes.DIRECT),
            key = ["first"])])
    fun first(vararg args: Any, message: Message): String { // message로 변환 가능한 형태로 return 해야한다.
        val n = args[0] as Int
        val s = args[1] as String

        val correlationId = message.messageProperties.correlationId

        val value = fib(n)
        println(" [1] Returned $value")

        val result = """
            {
            "num": $value,
            "str": $s
            }
        """.trimIndent()

        return result
    }

    @RabbitListener(bindings = [QueueBinding(
            value = Queue( ""),
            exchange = Exchange(name = "rpc", type = ExchangeTypes.DIRECT),
            key = ["second"])])
    fun second(vararg args: Any): String {
        val n: Int = args[0] as Int
        //val s = args[1] // 이 코드를 실행하면 val n: Int = args[0] 윗 줄까지 infinite loop. 잘못된 메모리 접근 때문?
        println(" [2] Received request for $n")
        val result = """
            {
            "num": ${fib(n)}
            }
        """.trimIndent()
        //println(" [.] Returned $result")
        return result
    }


    @RabbitListener(bindings = [QueueBinding(
            value = Queue( ""),
            exchange = Exchange(name = "rpc", type = ExchangeTypes.DIRECT),
            key = ["third"])])
    fun third(vararg args: Any): String {
        val n: Int = args[0] as Int
//        val s: String = args[1] as String

        val value = fib(n)
        println(" [3] Returned $value")
        val result = """
            {
            "num": $value,
            "DD": "sdfwsewf"
            }
        """.trimIndent()

        return result
    }

//    @RabbitListener(bindings = [QueueBinding(
//            value = Queue(name = "clientQueue"),
//            exchange = Exchange(name = "rpc", type = ExchangeTypes.DIRECT),
//            key = ["JSON"])])
//    fun jsontest(args: JSONObject): JSONObject {
//        val result: JSONObject = {} as JSONObject
//        result.put("d", "d")
//
//        println(" [json] Returned ")
//
//        return result
//    }

    fun fib(n: Int): Int { // example method(message)
        return if (n == 0) 0 else if (n == 1) 1 else fib(n - 1) + fib(n - 2)
    }
}