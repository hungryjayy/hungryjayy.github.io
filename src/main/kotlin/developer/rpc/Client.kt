package developer.rpc

import org.springframework.amqp.core.DirectExchange
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.json.JSONObject

@Service // annotation for test. repository에 호출, business logic code에 사용.
class Client { // Producer(Sender) role

    @Autowired
    private val template: RabbitTemplate? = null

    @Autowired
    private val exchange: DirectExchange? = null // serverConfig(yaml에서 active시켜놓은 config에서의 exchange)

    fun send(vararg args: Any, routingKey: String): JSONObject { // callRPC 역할
        val functionNumber = args[0]

        println(" [x] Requesting fib(" + functionNumber.toString() + ")")
        // convertSendAndReceive() : exchange name, routing key, message
        val response = template!!.convertSendAndReceive(exchange!!.name, routingKey, args) as String
        val json = JSONObject(response)

        println(" [.] Got '$json'")
        println(json.get("num"))

        return json
    }

}