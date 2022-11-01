package cn.xdwanj.onlinestore.service.impl

import cn.xdwanj.onlinestore.common.RoleEnum
import cn.xdwanj.onlinestore.entity.User
import cn.xdwanj.onlinestore.service.UserService
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.transaction.annotation.Transactional


@SpringBootTest
@RunWith(SpringRunner::class)
class UserServiceImplTest {

  @Autowired
  private lateinit var userService: UserService

  @Test
  @Transactional
  @Rollback
  fun updateInfo() {
    val currentUser = userService.getById(25)

    val user = User().apply {
      id = 25
      username = "XDwanj"
      email = "xdwanj@qq.com"
      phone = "11122223333"
      question = "question"
      answer = "answer"
      role = RoleEnum.ADMIN.code
    }

    assert(userService.updateById(user)) { "update userInfo is error" }

    userService.updateById(currentUser)
  }
}