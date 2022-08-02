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
  private val _imageHost: String
) {
  init {
    FTP_HOST = _imageHost
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

