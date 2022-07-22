package cn.xdwanj.onlinestore.common

data class BizException(
  val errorMsg: String,
  val errorCode: Int = ResponseCode.ERROR.code
) : RuntimeException()
