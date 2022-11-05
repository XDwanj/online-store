package cn.xdwanj.onlinestore.config

import com.baomidou.mybatisplus.annotation.DbType
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor
import org.mybatis.spring.annotation.MapperScan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.annotation.EnableTransactionManagement

@Configuration
@MapperScan("cn.xdwanj.onlinestore.mapper")
@EnableTransactionManagement
class MybatisPlusConfig {

  @Bean
  // 添加插件
  fun interceptor(): MybatisPlusInterceptor = MybatisPlusInterceptor().apply {
    addInnerInterceptor(PaginationInnerInterceptor(DbType.MYSQL)) // 分页插件
  }
}