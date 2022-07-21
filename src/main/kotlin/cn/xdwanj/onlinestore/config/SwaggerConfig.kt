package cn.xdwanj.onlinestore.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.Contact
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket

@Configuration
open class SwaggerConfig { // http://localhost:8080/swagger-ui/index.html
  @Value("\${spring.profiles.active}")
  private lateinit var active: String

  @Bean
  open fun restApi(): Docket = Docket(DocumentationType.OAS_30)
    .enable(
      listOf("dev", "test").contains(active) // 通过配置文件判断是否开启Swagger
    )
    .apiInfo(apiInfo())
    .select()
    .apis(RequestHandlerSelectors.basePackage("cn.xdwanj.onlinestore.controller"))
    .paths(PathSelectors.any())
    .build()

  private fun apiInfo(): ApiInfo = ApiInfoBuilder()
    .title("OnlineStore")
    .description("网上商城")
    .contact(Contact("XDwanj", null, "xdwanj@qq.com"))
    .build()
}