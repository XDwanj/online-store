package cn.xdwanj.onlinestore.service.impl

import cn.xdwanj.onlinestore.annotation.Slf4j
import cn.xdwanj.onlinestore.annotation.Slf4j.Companion.logger
import cn.xdwanj.onlinestore.config.uploadFile
import cn.xdwanj.onlinestore.service.FileService
import org.apache.commons.net.ftp.FTPClient
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.util.*

@Slf4j
@Service
class FileServiceImpl(
  private val ftpClient: FTPClient
) : FileService {

  override fun upload(file: MultipartFile, path: String): String {
    val fileName = file.name.run { substring(0, lastIndexOf(".")) }
    val fileExtensionName = file.name.run { substring(lastIndexOf(".") + 1, lastIndex) }
    val uploadFileName = UUID.randomUUID().toString() + ".$fileExtensionName"

    logger.info("文件开始上传，文件名：$fileName，文件类型：$fileExtensionName，上传文件名：$uploadFileName")
    val fileDir = File(path)
    if (!fileDir.exists()) {
      fileDir.setWritable(true)
      fileDir.mkdirs()
    }

    val targetFile = File(path, uploadFileName)

    // save file
    file.transferTo(targetFile)

    // upload file
    ftpClient.uploadFile(listOf(targetFile))

    // 删除文件
    targetFile.delete()
    return targetFile.name
  }
}