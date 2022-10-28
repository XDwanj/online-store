package cn.xdwanj.onlinestore

import cn.xdwanj.onlinestore.service.UserService
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@SpringBootTest
@RunWith(SpringRunner::class)
class OnlineStoreApplicationTests {

  @Autowired
  private lateinit var userService: UserService

  @Autowired
  private lateinit var objectMapper: ObjectMapper

  @Test
  fun contextLoads() {
    // println(userService.login("XDwanj", "123456"))

    objectMapper.readValue<String>("hello")
  }
}