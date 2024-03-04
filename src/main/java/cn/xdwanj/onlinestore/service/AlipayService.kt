package cn.xdwanj.onlinestore.service

import java.math.BigDecimal

interface AlipayService {
  /**
   * 生成支付表单
   *
   * @param outTradeNo
   * @param subject
   * @param money
   * @return
   */
  fun toPay(outTradeNo: String, subject: String, money: BigDecimal): String

  /**
   * 查询交易状态
   *
   * @param outTradeNo
   * @return
   */
  fun queryTradeStatus(outTradeNo: String): String?
}