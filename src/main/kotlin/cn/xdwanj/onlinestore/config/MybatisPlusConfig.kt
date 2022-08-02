package cn.xdwanj.onlinestore.config

import com.baomidou.mybatisplus.annotation.DbType
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MybatisPlusConfig {

  @Bean
  fun interceptor(): MybatisPlusInterceptor = MybatisPlusInterceptor().apply {
    addInnerInterceptor(PaginationInnerInterceptor(DbType.MYSQL)) // 分页插件
  }
}