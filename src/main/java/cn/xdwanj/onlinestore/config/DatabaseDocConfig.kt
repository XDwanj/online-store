package cn.xdwanj.onlinestore.config

import cn.smallbun.screw.core.Configuration
import cn.smallbun.screw.core.engine.EngineConfig
import cn.smallbun.screw.core.engine.EngineFileType
import cn.smallbun.screw.core.engine.EngineTemplateType
import cn.smallbun.screw.core.execute.DocumentationExecute
import cn.xdwanj.onlinestore.annotation.Slf4j
import cn.xdwanj.onlinestore.annotation.Slf4j.Companion.logger
import javax.annotation.PostConstruct
import org.springframework.stereotype.Component
import javax.sql.DataSource

@Slf4j
@Component
class DatabaseDocConfig(
  private val dataSource: DataSource
) {

  @PostConstruct
  fun docGenerator() {
    DocumentationExecute(getScrewConfig())
      .execute()

    logger.info("数据库文档生成完毕")
  }

  private fun getEngineConfig() = EngineConfig.builder()
    .openOutputDir(true)
    .fileType(EngineFileType.WORD)
    .produceType(EngineTemplateType.freemarker)
    .fileName("databaseDoc")
    .build()


  private fun getScrewConfig() = Configuration.builder()
    .version("v1.0.0")
    .description("ask数据库文档")
    .dataSource(dataSource)
    .engineConfig(getEngineConfig())
    .build()

}