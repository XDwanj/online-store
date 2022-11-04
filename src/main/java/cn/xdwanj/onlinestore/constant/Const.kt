package cn.xdwanj.onlinestore.constant

import cn.xdwanj.onlinestore.config.FtpConfigProperties
import cn.xdwanj.onlinestore.exception.BusinessException
import cn.xdwanj.onlinestore.exception.LogLevelEnum
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import javax.servlet.ServletContext

const val USER_REQUEST = "userRequest"
const val AUTHORIZATION_TOKEN = "authorization-token"
const val USER_SESSION = "userSession"
const val UPLOAD_PATH = "upload"

@Component
class Const(
  ftpConfigProperties: FtpConfigProperties,
  @Value("\${password.salt}")
  _salt: String,
  servletContext: ServletContext
) {
  init {
    FTP_HOST = "${ftpConfigProperties.serverPrefix}/${ftpConfigProperties.username}"
    PASSWORD_SALT = _salt
    SERVER_HOST = servletContext.contextPath
  }
}

lateinit var FTP_HOST: String
lateinit var PASSWORD_SALT: String
lateinit var SERVER_HOST: String

enum class RoleEnum(
  val code: Int,
  val desc: String
) {
  ADMIN(0, "管理员"),
  CUSTOMER(1, "普通用户")
}

enum class ProductStatusEnum(
  val code: Int,
  val desc: String
) {
  ON_SALE(1, "在线"),
  TAKE_DOWN(2, "下架"),
  DELETED(3, "删除")
}

enum class OrderStatusEnum(
  val code: Int,
  val desc: String
) {
  CANCELED(0, "已取消"),
  NO_PAY(10, "未支付"),
  PAID(20, "已付款"),
  SHIPPED(40, "已发货"),
  ORDER_SUCCESS(50, "订单完成"),
  ORDER_CLOSE(60, "订单关闭");

  companion object {
    fun codeOf(code: Int): OrderStatusEnum {
      for (orderStatusEnum in values()) {
        if (orderStatusEnum.code == code)
          return orderStatusEnum
      }

      throw BusinessException("订单状态错误")
    }
  }
}

enum class PaymentTypeEnum(
  val code: Int,
  val desc: String
) {
  ONLINE_PAY(1, "在线支付");

  companion object {
    fun codeOf(code: Int): PaymentTypeEnum {
      for (paymentTypeEnum in values()) {
        if (paymentTypeEnum.code == code)
          return paymentTypeEnum
      }

      throw BusinessException("没有一个支付状态正确", logLevel = LogLevelEnum.ERROR)
    }
  }
}

object CartConst {
  const val CHECKED = 1
  const val UN_CHECKED = 0

  const val LIMIT_NUM_FAIL = "LIMIT_NUM_FAIL"
  const val LIMIT_NUM_SUCCESS = "LIMIT_NUM_SUCCESS"
}


