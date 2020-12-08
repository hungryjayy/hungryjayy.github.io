package developer.rpc

import net.sf.json.JSON
import org.json.JSONObject
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class RpcApplicationTests {

	@Autowired
	private val client: Client ?= null

	@Test
	fun firstTest(){
		val num = 6
		println("func1")
		val result = client!!.send(num, "varargVersion", routingKey = "first")
		println("1-test: " + result)
	}

	@Test
	fun secondTest(){
		val num = 1
		println("func2")
		val result = client!!.send(num, routingKey = "second")
		println("2-test: " + result)
	}

	@Test
	fun thirdTest(){
		val num = 3
		println("func3")
		val result = client!!.send(num, "Dd", routingKey = "third")
		println("3-test: " + result)
	}

}
