package cn.xdwanj.onlinestore.service.impl

import cn.xdwanj.onlinestore.annotation.Slf4j
import cn.xdwanj.onlinestore.annotation.Slf4j.Companion.logger
import cn.xdwanj.onlinestore.common.SERVER_HOST
import cn.xdwanj.onlinestore.service.AlipayService
import com.alipay.easysdk.factory.Factory
import com.alipay.easysdk.kernel.util.ResponseChecker
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@Slf4j
@Service
class AlipayServiceImpl(
  private val objectMapper: ObjectMapper
) : AlipayService {
  override fun toPay(outTradeNo: String, subject: String, money: BigDecimal): String {
    val pay = Factory.Payment
      .Page()
      .pay(subject, outTradeNo, money.toString(), SERVER_HOST)

    if (ResponseChecker.success(pay)) {
      logger.info("交易正式开始：{}", pay.getBody())
      return pay.getBody()
    }

    return ""
  }

  override fun queryTradeStatus(outTradeNo: String): String? {
    val query = Factory.Payment.Common()
      .query(outTradeNo)
    val map = objectMapper.readValue(query.httpBody, Map::class.java)
    return map["alipay_trade_query_response"] as String?
  }

  private fun generateTradeNo(): String {
    val formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS")

    return LocalDateTime.now(ZoneOffset.of("+8"))
      .format(formatter)
  }
}