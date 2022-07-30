package cn.xdwanj.onlinestore.common

const val CURRENT_USER = "currentUser"
const val USERNAME = "username"
const val EMAIL = "email"

enum class Role(
  val code: Int,
  val desc: String
) {
  ADMIN(0, "管理员"),
  CUSTOMER(1, "普通用户")
}

