package cn.xdwanj.onlinestore.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig { // http://127.0.0.1:8080/swagger-ui.html
  @Value("\${spring.profiles.active}")
  private lateinit var active: String

  @Bean
  fun openApi() = OpenAPI().apply {
    info = Info().title("online-store")
      .description("一个简单网上商城")
      .version("1.0.0")
  }

  // @Bean
  // open fun restApi(): Docket = Docket(DocumentationType.OAS_30)
  //   .enable(
  //     listOf("dev", "test").contains(active) // 通过配置文件判断是否开启Swagger
  //   )
  //   .apiInfo(apiInfo())
  //   .select()
  //   .apis(RequestHandlerSelectors.basePackage("cn.xdwanj.onlinestore.controller"))
  //   .paths(PathSelectors.any())
  //   .build()
  //
  // private fun apiInfo(): ApiInfo = ApiInfoBuilder()
  //   .title("OnlineStore")
  //   .description("网上商城")
  //   .contact(Contact("XDwanj", null, "xdwanj@qq.com"))
  //   .build()
}