package cn.xdwanj.onlinestore.util

import org.springframework.util.DigestUtils
import java.util.*

fun String.encodeByMD5() = DigestUtils
  .md5DigestAsHex(this.toByteArray())
  .toString()
  .uppercase(Locale.getDefault())

