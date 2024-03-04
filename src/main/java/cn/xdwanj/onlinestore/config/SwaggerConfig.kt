package cn.xdwanj.onlinestore.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig { // http://localhost:8080/swagger-ui/index.html
  @Bean
  fun openApi() = OpenAPI().apply {
    info = Info().title("online-store")
      .description("基于 Springboot Kotlin 的 网上商城后台API系统")
      .version("1.0.0")
  }

}