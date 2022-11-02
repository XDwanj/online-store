package cn.xdwanj.onlinestore.config

import cn.xdwanj.onlinestore.annotation.Slf4j
import org.springframework.context.annotation.Configuration

@Slf4j
@Configuration
class CorsConfig {
  //
  // @Bean
  // fun corsFilter(
  //   corsSource: CorsConfigurationSource
  // ) = CorsFilter(corsSource).apply {
  //   logger.info("corsFilter init is success")
  // }
  //
  // @Bean
  // fun corsSource(
  //   corsConfig: CorsConfiguration
  // ) = UrlBasedCorsConfigurationSource().apply {
  //   registerCorsConfiguration("/**", corsConfig)
  //   logger.info("corsSource init is success")
  // }
  //
  // @Bean
  // fun corsConfiguration() = CorsConfiguration().apply {
  //   allowedOriginPatterns = listOf("*")
  //   allowedMethods = listOf("GET, POST, PUT, DELETE, PATCH, OPTIONS, HEAD")
  //   allowedHeaders = listOf("*")
  //   allowCredentials = true
  //   maxAge = 1728000L
  //   logger.info("corsConfig init is success")
  // }
}