package cn.xdwanj.onlinestore.util

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.util.DigestUtils
import java.util.*

@Component
class Security

@Value("\${password.salt}")
lateinit var salt: String

fun String.encodeByMD5(): String = DigestUtils
  .md5DigestAsHex("${this}${salt}".toByteArray())
  .toString()
  .uppercase(Locale.getDefault())


