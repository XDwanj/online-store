package cn.xdwanj.onlinestore.util

import com.baomidou.mybatisplus.annotation.FieldFill
import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.baomidou.mybatisplus.generator.FastAutoGenerator
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine
import com.baomidou.mybatisplus.generator.fill.Column
import org.apache.ibatis.annotations.Mapper

class MPGenerator {
  fun generator() {
    FastAutoGenerator.create(
      "jdbc:mysql://gz-cynosdbmysql-grp-e24eyf27.sql.tencentcdb.com:22800/mmall?useSSL=true&charterEncoding=utf-8",
      "XDwanj",
      "1234qwer."
    ).globalConfig { builder ->
      builder.author("XDwanj")
        // .enableSwagger()
        .enableSpringdoc()
        .outputDir("/home/xdwanj/TEMP")
        .enableKotlin()
    }.packageConfig { builder ->
      builder.parent("cn.xdwanj.onlinestore")
    }.strategyConfig { builder ->
      builder
        .addTablePrefix("mmall_")

        .mapperBuilder()
        .formatMapperFileName("%sMapper")
        .mapperAnnotation(Mapper::class.java)
        .superClass(BaseMapper::class.java)

        .serviceBuilder()
        .formatServiceFileName("%sService")
        .formatServiceImplFileName("%sServiceImpl")

        .entityBuilder()
        .disableSerialVersionUID()
        .naming(NamingStrategy.underline_to_camel)
        .columnNaming(NamingStrategy.underline_to_camel)
        .addTableFills(Column("create_time", FieldFill.INSERT))
        .addTableFills(Column("update_time", FieldFill.INSERT_UPDATE))

        .controllerBuilder()
        .formatFileName("%sController")
        .enableRestStyle()
    }.templateEngine(VelocityTemplateEngine())
      .execute()
  }
}
