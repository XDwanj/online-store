package cn.xdwanj.onlinestore.config

import cn.xdwanj.onlinestore.interceptor.CheckAdminInterceptor
import cn.xdwanj.onlinestore.interceptor.LoginInterceptor
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig(
  private val loginInterceptor: LoginInterceptor,
  private val checkAdminInterceptor: CheckAdminInterceptor
) : WebMvcConfigurer {
  override fun addInterceptors(registry: InterceptorRegistry) {
    registry.apply {
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

        .addPathPatterns("/manage/**")
        .excludePathPatterns("/manage/login")

      // 用户角色拦截器
      addInterceptor(checkAdminInterceptor)
        .addPathPatterns("/manage/**")
        .excludePathPatterns("/manage/login")
    }
  }
}