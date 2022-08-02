package cn.xdwanj.onlinestore.common

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

const val CURRENT_USER = "currentUser"
const val USERNAME = "username"
const val EMAIL = "email"

@Component
class Const(
  @Value("\${ftp.server.http.prefix}")
  private val _imageHost: String
) {
  init {
    IMAGE_HOST = _imageHost
  }
}

lateinit var IMAGE_HOST: String

enum class Role(
  val code: Int,
  val desc: String
) {
  ADMIN(0, "管理员"),
  CUSTOMER(1, "普通用户")
}

