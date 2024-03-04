package cn.xdwanj.onlinestore.config

import cn.dev33.satoken.interceptor.SaInterceptor
import cn.dev33.satoken.router.SaRouter
import cn.dev33.satoken.stp.StpInterface
import cn.dev33.satoken.stp.StpUtil
import cn.xdwanj.onlinestore.constant.RoleEnum
import cn.xdwanj.onlinestore.constant.USER_SESSION
import cn.xdwanj.onlinestore.entity.User
import cn.xdwanj.onlinestore.exception.BusinessException
import cn.xdwanj.onlinestore.exception.LogLevelEnum
import cn.xdwanj.onlinestore.service.UserService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SecurityConfig(
  private val userService: UserService
) : StpInterface {

  override fun getPermissionList(loginId: Any?, loginType: String?): MutableList<String> {
    val user = StpUtil.getSession()[USER_SESSION] as User
    return mutableListOf("*")
  }

  override fun getRoleList(loginId: Any?, loginType: String?): MutableList<String> {
    val user = userService.getById(loginId as Int)
    return when (user.role) {
      RoleEnum.ADMIN.code -> mutableListOf(RoleEnum.ADMIN.desc, RoleEnum.CUSTOMER.desc)
      RoleEnum.CUSTOMER.code -> mutableListOf(RoleEnum.CUSTOMER.desc)
      else -> {
        throw BusinessException("未知用户", logLevel = LogLevelEnum.ERROR)
      }
    }
  }

  @Bean
  fun saTokenSecurityInterceptor(): SaInterceptor = SaInterceptor {
    SaRouter.match(
      "/user/info*",
      "/user/logout",
      "/user/password/reset",
      "/cart/**",
      "/shipping/**",
      "/order/**",
      "/pay/**",
      "/manage/**",
    ).notMatch(
      "/pay/alipay/callback",
      "/manage/login",
    ).check { _ -> StpUtil.checkLogin() }

    SaRouter.match("/manage/**")
      .notMatch("/manage/login")
      .check { _ -> StpUtil.checkRole(RoleEnum.ADMIN.desc) }

  }.isAnnotation(false)

}
