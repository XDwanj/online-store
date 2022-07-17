package cn.xdwanj.onlinestore.util

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.baomidou.mybatisplus.generator.FastAutoGenerator
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine

class MPGenerator {
  fun generator() {
    FastAutoGenerator.create(
      "jdbc:mysql://gz-cynosdbmysql-grp-e24eyf27.sql.tencentcdb.com:22800/mmall?useSSL=true&charterEncoding=utf-8",
      "XDwanj",
      "1234qwer."
    ).globalConfig { builder ->
      builder.author("XDwanj")
        .enableSwagger()
        .outputDir("/home/xdwanj/TEMP")
        .enableKotlin()
    }.packageConfig { builder ->
      builder.parent("cn.xdwanj.onlinestore")
    }.strategyConfig { builder ->
      builder
        .addTablePrefix("mmall_")

        .mapperBuilder()
        .formatMapperFileName("%sMapper")
        .enableMapperAnnotation()
        .superClass(BaseMapper::class.java)

        .serviceBuilder()
        .formatServiceFileName("%sService")
        .formatServiceImplFileName("%sServiceImpl")

        .entityBuilder()
        .disableSerialVersionUID()
        .naming(NamingStrategy.underline_to_camel)
        .columnNaming(NamingStrategy.underline_to_camel)
        // .enableTableFieldAnnotation()

        .controllerBuilder()
        .formatFileName("%sController")
        .enableRestStyle()
    }.templateEngine(FreemarkerTemplateEngine())
      .execute()
  }
}
