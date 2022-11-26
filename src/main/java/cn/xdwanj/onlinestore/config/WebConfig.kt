package cn.xdwanj.onlinestore.config

import cn.dev33.satoken.interceptor.SaInterceptor
import cn.xdwanj.onlinestore.interceptor.CheckAdminInterceptor
import cn.xdwanj.onlinestore.interceptor.LoginInterceptor
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig(
  private val loginInterceptor: LoginInterceptor,
  private val checkAdminInterceptor: CheckAdminInterceptor,
  private val saInterceptor: SaInterceptor
) : WebMvcConfigurer {
  override fun addInterceptors(registry: InterceptorRegistry) {
    registry.apply {
      // addInterceptor(saInterceptor)

      // 登录拦截器
      addInterceptor(loginInterceptor)
        .addPathPatterns(
          "/user/info/**",
          "/user/logout",
          "/user/password/reset",
        )
        .addPathPatterns("/cart/**")
        .addPathPatterns("/shipping/**")
        .addPathPatterns("/order/**")

        .addPathPatterns("/pay/**")
        .excludePathPatterns("/pay/alipay/callback")

        .addPathPatterns("/manage/**")
        .excludePathPatterns("/manage/login")

      // 用户角色拦截器
      addInterceptor(checkAdminInterceptor)
        .addPathPatterns("/manage/**")
        .excludePathPatterns("/manage/login")
    }
  }

  // 这个方式在引入 springSecurity 后会失效，由于优先级问题
  // override fun addCorsMappings(registry: CorsRegistry) {
  //   registry.addMapping("/**")
  //     .allowedOrigins("*")
  //     .allowedHeaders("*")
  //     .allowCredentials(true)
  //     .maxAge(10000);
  // }
}