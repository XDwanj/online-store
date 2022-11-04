package cn.xdwanj.onlinestore.common

import cn.hutool.core.lang.Snowflake
import cn.hutool.core.util.IdUtil
import cn.xdwanj.onlinestore.constant.PASSWORD_SALT
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.util.DigestUtils
import java.util.*

fun String.encodeByMD5(): String = DigestUtils
  .md5DigestAsHex("${this}$PASSWORD_SALT".toByteArray())
  .toString()
  .uppercase(Locale.getDefault())

@Configuration
class CommonRegister {

  @Bean
  fun snowflake(): Snowflake = IdUtil.getSnowflake()
}


