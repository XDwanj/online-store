package cn.xdwanj.onlinestore.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig { // http://127.0.0.1:8080/swagger-ui.html
  @Bean
  fun openApi() = OpenAPI().apply {
    info = Info().title("online-store")
      .description("一个简单网上商城")
      .version("1.0.0")
  }

}