package cn.xdwanj.onlinestore.service.impl

import cn.xdwanj.onlinestore.service.UserService
import cn.xdwanj.onlinestore.util.Slf4j
import org.junit.jupiter.api.Test

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@Slf4j
@SpringBootTest
internal class UserServiceImplTest {
  @Autowired
  private lateinit var userService: UserService

  @Test
  fun checkValid() {
  }

  @Test
  fun checkUsername() {
  }

  @Test
  fun checkEmail() {
  }

  @Test
  fun login() {
  }

  @Test
  fun register() {
  }

  @Test
  fun getQuestion() {
    println(userService.getQuestion("xdwanj").data)

  }
}