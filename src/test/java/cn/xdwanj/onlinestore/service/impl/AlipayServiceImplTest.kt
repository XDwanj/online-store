package cn.xdwanj.onlinestore.service.impl

import cn.hutool.core.lang.Snowflake
import cn.xdwanj.onlinestore.service.AlipayService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import java.math.BigDecimal

@SpringBootTest
@ActiveProfiles("dev")
internal class AlipayServiceImplTest {
  @Autowired
  private lateinit var alipayService: AlipayService

  @Autowired
  private lateinit var snowflake: Snowflake

  @Test
  fun toPay() {
    val payHtml = alipayService.toPay(snowflake.nextIdStr(), "test", BigDecimal.valueOf(35L))
    assert(payHtml.isNotBlank())
  }

  @Test
  fun queryTradeStatus() {
  }
}