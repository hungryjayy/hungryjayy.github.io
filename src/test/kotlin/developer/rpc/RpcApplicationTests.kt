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
	fun firstTest(){
		val num = 7
		val jsonObj = JSONObject()
		jsonObj.put("num", num)
		jsonObj.put("s", "example")
		println(" [first-test] ")
		val result = client!!.send(jsonObj, routingKey = "first")
		println(" [first-test] $result")
	}

	@Test
	fun secondTest(){
		val num = 6
		val jsonObj = JSONObject()
		jsonObj.put("num", num)
		println(" [second-test] ")
		val result = client!!.send(jsonObj, routingKey = "second")
		println(" [second-test] $result")
	}

	@Test
	fun thirdTest(){
		val num = 5
		val str = "third Str"
		val jsonObj = JSONObject()
		jsonObj.put("num", num)
		jsonObj.put("s", str)
		println(" [third-test]")
		val result = client!!.send(jsonObj, routingKey = "third")
		println(" [third-test] $result")
	}

}
