package cn.xdwanj.onlinestore.config

import com.baomidou.mybatisplus.annotation.DbType
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MybatisPlusConfig {

  @Bean
  // 添加插件
  fun interceptor(): MybatisPlusInterceptor = MybatisPlusInterceptor().apply {
    addInnerInterceptor(PaginationInnerInterceptor(DbType.MYSQL)) // 分页插件
  }

  // TODO: MP v3.5.2 与 SpringBoot v3.0.0-RC1 出现兼容性问题，估计需要等待 MP 官方解决
  // @Bean
  // fun sqlSessionFactory(
  //   dataSource: DataSource,
  //   mybatisPlusInterceptor: MybatisPlusInterceptor
  // ) = MybatisSqlSessionFactoryBean().apply {
  //   setDataSource(dataSource)
  //   setTypeAliasesPackage("cn.xdwanj.onlinestore.entity")
  //   setPlugins(mybatisPlusInterceptor)
  // }
}