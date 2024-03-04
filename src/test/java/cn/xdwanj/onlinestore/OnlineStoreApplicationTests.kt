package cn.xdwanj.onlinestore

import cn.hutool.core.date.LocalDateTimeUtil
import cn.xdwanj.onlinestore.service.ProductService
import cn.xdwanj.onlinestore.service.UserService
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDateTime

@SpringBootTest
class OnlineStoreApplicationTests {

  @Autowired
  private lateinit var userService: UserService

  @Autowired
  private lateinit var productService: ProductService

  @Test
  fun contextLoad() {
    val str = LocalDateTimeUtil.formatNormal(LocalDateTime.now())
    println(str)
  }

  // @Test
  // fun insertProduct() {
  //
  //   val productList = mutableListOf<Product>()
  //   val colorList = mutableListOf("红色", "绿色", "白色", "黑色")
  //   for (i in 1..100) {
  //     val color = colorList.random()
  //     productList += Product().apply {
  //       id = null
  //       categoryId = 100002
  //       name = "苹果手机$i"
  //       subtitle = "苹果手机 $color"
  //       mainImage = "image.png"
  //       subImages = "image.png"
  //       detail = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"
  //       price = BigDecimal.valueOf(3000.00)
  //       stock = 3000
  //       status = ProductStatusEnum.ON_SALE.code
  //     }
  //   }
  //   productService.saveBatch(productList)
  // }
}