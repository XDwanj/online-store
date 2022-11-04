package cn.xdwanj.onlinestore.common

import cn.hutool.core.lang.Snowflake
import cn.hutool.core.util.IdUtil
import cn.xdwanj.onlinestore.annotation.Slf4j
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * 通用注册中心，提供组件注册的场所
 *
 */
@Slf4j
@Configuration
class CommonRegister {

  /**
   * 雪花ID工厂
   *
   * @return
   */
  @Bean
  fun snowflake(): Snowflake = IdUtil.getSnowflake()
}


