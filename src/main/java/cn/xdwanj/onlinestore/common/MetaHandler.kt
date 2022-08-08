package cn.xdwanj.onlinestore.common

import cn.xdwanj.onlinestore.annotation.Slf4j
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler
import org.apache.ibatis.reflection.MetaObject
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Slf4j
@Component
class MetaHandler : MetaObjectHandler {
  override fun insertFill(metaObject: MetaObject?) {
    strictInsertFill(metaObject, "createTime", LocalDateTime::class.java, LocalDateTime.now())
    strictInsertFill(metaObject, "updateTime", LocalDateTime::class.java, LocalDateTime.now())
  }

  override fun updateFill(metaObject: MetaObject?) {
    strictUpdateFill(metaObject, "updateTime", LocalDateTime::class.java, LocalDateTime.now())
  }
}