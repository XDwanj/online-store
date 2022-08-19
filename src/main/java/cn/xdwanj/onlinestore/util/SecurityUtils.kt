package cn.xdwanj.onlinestore.util

import cn.xdwanj.onlinestore.common.PASSWORD_SALT
import org.springframework.util.DigestUtils
import java.util.*

fun String.encodeByMD5(): String = DigestUtils
  .md5DigestAsHex("${this}${PASSWORD_SALT}".toByteArray())
  .toString()
  .uppercase(Locale.getDefault())


