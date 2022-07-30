package cn.xdwanj.onlinestore.common

/**
 * 不要滥用这个类，请一定要在出现业务错误是使用，过多的使用异常机制，会影响性能
 *
 * @property errorMsg 错误信息
 * @property errorCode 错误code
 */
data class BusinessException(
  val errorMsg: String,
  val errorCode: Int = ResponseCode.ERROR.code
) : RuntimeException()
