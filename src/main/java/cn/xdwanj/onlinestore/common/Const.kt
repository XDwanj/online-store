package cn.xdwanj.onlinestore.common

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

const val CURRENT_USER = "currentUser"
const val USERNAME = "username"
const val EMAIL = "email"
const val UPLOAD_PATH = "./"

@Component
class Const(
  @Value("\${ftp.server.http.prefix}")
  private val _ftpHost: String
) {
  init {
    FTP_HOST = _ftpHost
  }
}

lateinit var FTP_HOST: String

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
  ON_SALE(1, "在线")
}

object CartConst {
  const val CHECKED = 1
  const val UN_CHECKED = 0

  const val LIMIT_NUM_FAIL = "LIMIT_NUM_FAIL"
  const val LIMIT_NUM_SUCCESS = "LIMIT_NUM_SUCCESS"
}


