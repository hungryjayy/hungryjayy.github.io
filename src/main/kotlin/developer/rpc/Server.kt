package developer.rpc

import org.springframework.amqp.rabbit.annotation.Exchange
import org.springframework.amqp.rabbit.annotation.Queue
import org.springframework.amqp.rabbit.annotation.QueueBinding
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.web.bind.annotation.ResponseBody

class Server{ // Consumer(Receiver) role (Conference.js와 같은)

    @RabbitListener(bindings = [QueueBinding(
            Queue("forFunc1", durable = "true"), // durable을 통해 죽어도 사라지지 않고 보존
            exchange = Exchange("rpc", type = "direct", durable = "true"),
            key = ["func1"])])
//    @SendTo("rpc.replies") // used when the client doesn't set replyTo.
    fun func(vararg args: Any): String { // message로 변환 가능한 형태로 return 해야한다.
        val n = args[0] as Int
        val s = args[1] as String

        println(" [1] Received request for $n")
        val value = fib(n)
        println(" [.] Returned $value")

        val result = """
            {
            "num": $value,
            "str": $s
            }
        """.trimIndent()

        return result
    }

    @RabbitListener(bindings = [QueueBinding(
            Queue("forFunc2"),
            exchange = Exchange("rpc"),
            key = ["func2"])])
    fun func2(vararg args: Any): Int {
        val n: Int = args[0] as Int
        //val s = args[1] // 이 코드를 실행하면 val n: Int = args[0] 윗 줄까지 infinite loop. 잘못된 메모리 접근 때문?
        println(" [2] Received request for $n")
        val result = fib(n)
        println(" [.] Returned $result")
        return result
    }

    @RabbitListener(bindings = [QueueBinding(
            Queue("forFunc3"),
            exchange = Exchange("rpc"),
            key = ["func3"]
    )])
    fun func3(vararg args: Any): Int {
        val n: Int = args[0] as Int
        val s = args[1]
        println(" [3] Received request for $n with $s")
        val result = fib(n)
        println(" [.] Returned $result with $s")
        return result
    }

//    suspend fun asyncFunc(req: ServerRequest): ServerResponse{
//
//    }

    fun fib(n: Int): Int { // example method(message)
        return if (n == 0) 0 else if (n == 1) 1 else fib(n - 1) + fib(n - 2)
    }
}