package cn.xdwanj.onlinestore.util

import org.springframework.stereotype.Component
import org.springframework.util.DigestUtils
import java.util.*

@Component
class Security

// @Value("\${password.salt}")
const val salt = "A428DC27C09FBE613F60D10C87DC78A3"

fun String.encodeByMD5(): String = DigestUtils
  .md5DigestAsHex("${this}${salt}".toByteArray())
  .toString()
  .uppercase(Locale.getDefault())


