package developer.rpc

import org.json.JSONObject
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class RpcApplicationTests {

	@Autowired
	private val client: Client ?= null

	@Test
	fun funcTest(){
		val num = 5
		println("func1")
		val result :JSONObject = client!!.send(num, "varargVersion", routingKey = "func1")
		println(result)
	}

	@Test
	fun func2Test(){
		val num = 1
		println("func2")
		val result :JSONObject = client!!.send(num, routingKey = "func2");
		println(result)
	}

	@Test
	fun func3Test(){
		val num = 5
		val s: String = "example string"
		val s2: String = "example second string"
		println("func3")
		val result :JSONObject = client!!.send(num, s, s2, "template string", routingKey = "func3")
		println(result)
	}
}
