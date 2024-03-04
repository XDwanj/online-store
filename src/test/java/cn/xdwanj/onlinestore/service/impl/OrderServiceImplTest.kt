package cn.xdwanj.onlinestore.service.impl

import cn.xdwanj.onlinestore.service.OrderService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class OrderServiceImplTest {
  @Autowired
  private lateinit var orderService: OrderService

  @Test
  fun generateOrderNo() {
    println(orderService.generateOrderNo())
  }
}