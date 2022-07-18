package cn.xdwanj.onlinestore.common

const val CURRENT_USER = "currentUser"
const val USERNAME = "username"
const val EMAIL = "email"

enum class Role(
  val code: Int,
  val desc: String
) {
  CUSTOMER(0, "普通用户"),
  ADMIN(1, "管理员")
}

