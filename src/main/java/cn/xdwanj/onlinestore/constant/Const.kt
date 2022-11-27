package cn.xdwanj.onlinestore.constant

import cn.dev33.satoken.SaManager
import cn.xdwanj.onlinestore.exception.BusinessException
import cn.xdwanj.onlinestore.exception.LogLevelEnum
import org.springframework.core.env.Environment
import org.springframework.core.env.get
import org.springframework.stereotype.Component

// const val USER_REQUEST = "userRequest"
const val USER_SESSION = "userSession"
const val UPLOAD_PATH = "upload"

@Component
class Const(environment: Environment) {
  init {
    en = environment
  }
}

private lateinit var en: Environment
val FTP_HOST by lazy { "${en["serverPrefix"]}/${en["ftp.username"]}" }
val PASSWORD_SALT by lazy { en["password.salt"]!! }
val HOME_PAGE by lazy { en["front-desk.home-page"]!! }
val AUTHORIZATION_TOKEN_NAME by lazy { SaManager.config.tokenName!! }

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


