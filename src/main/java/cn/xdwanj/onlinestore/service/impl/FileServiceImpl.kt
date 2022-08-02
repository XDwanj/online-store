package cn.xdwanj.onlinestore.service.impl

import cn.xdwanj.onlinestore.service.FileService
import cn.xdwanj.onlinestore.util.Slf4j
import cn.xdwanj.onlinestore.util.Slf4j.Companion.logger
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.util.UUID

@Slf4j
@Service
class FileServiceImpl : FileService {
  override fun upload(file: MultipartFile, path: String): String {
    val fileName = file.name.run { substring(0, lastIndexOf(".")) }
    val fileExtensionName = file.name.run { substring(lastIndexOf(".") + 1, lastIndex) }
    val uploadFileName = UUID.randomUUID().toString() + ".$fileExtensionName"

    logger.info("文件开始上传，文件名：$fileName，文件类型：$fileExtensionName，上传文件名：$uploadFileName")
    File(path).run {
      if (!exists()) {
        setWritable(true)
        mkdirs()
      }
    }

    val targetFile = File(path, uploadFileName)

    TODO()
  }
}