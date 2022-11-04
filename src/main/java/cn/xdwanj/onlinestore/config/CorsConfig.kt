package cn.xdwanj.onlinestore.config

import cn.xdwanj.onlinestore.annotation.Slf4j
import cn.xdwanj.onlinestore.annotation.Slf4j.Companion.logger
import org.springframework.context.annotation.Bean
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter

/**
 * 这个目前是Cors解决方案最优解，可以定义cors方案拦截，但暂时废弃
 *
 */
@Slf4j
@Deprecated("暂时废弃的Cors解决方案")
// @Configuration
class CorsConfig {

  @Bean
  fun corsFilter(
    corsSource: CorsConfigurationSource
  ) = CorsFilter(corsSource).apply {
    logger.info("corsFilter init is success")
  }

  @Bean
  fun corsSource(
    corsConfig: CorsConfiguration
  ) = UrlBasedCorsConfigurationSource().apply {
    registerCorsConfiguration("/**", corsConfig)
    logger.info("corsSource init is success")
  }

  @Bean
  fun corsConfiguration() = CorsConfiguration().apply {
    // allowCredentials = true
    // allowedOriginPatterns = listOf("*")
    allowedOrigins = listOf("*")
    allowedMethods = listOf("GET, POST, PUT, DELETE, PATCH, OPTIONS, HEAD")
    allowedHeaders = listOf("*")
    maxAge = 1728000L
    logger.info("corsConfig init is success")
  }
}