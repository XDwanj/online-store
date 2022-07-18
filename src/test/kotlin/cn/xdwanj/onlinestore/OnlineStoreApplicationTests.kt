package cn.xdwanj.onlinestore

import cn.xdwanj.onlinestore.entity.User
import cn.xdwanj.onlinestore.service.UserService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDateTime

@SpringBootTest
class OnlineStoreApplicationTests {

  @Autowired
  private lateinit var userService: UserService

  @Test
  fun contextLoads() {

  }
}