package cn.xdwanj.onlinestore.service.impl

import cn.hutool.core.io.FileUtil
import cn.hutool.core.io.file.FileNameUtil
import cn.hutool.core.util.IdUtil
import cn.hutool.extra.ftp.Ftp
import cn.xdwanj.onlinestore.annotation.Slf4j
import cn.xdwanj.onlinestore.annotation.Slf4j.Companion.logger
import cn.xdwanj.onlinestore.service.FileService
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File

@Slf4j
@Service
class FileServiceImpl(
  private val ftpUtil: Ftp,
) : FileService {

  override fun upload(file: MultipartFile, path: String): String {
    val fileName = FileNameUtil.mainName(file.name)
    val fileExtName = FileNameUtil.extName(file.name)
    val uploadFileName = "${IdUtil.fastSimpleUUID()}.$fileExtName"

    logger.info("文件开始上传，文件名：$fileName，文件类型：$fileExtName，上传文件名：$uploadFileName")
    val pathFile = File(path)
    FileUtil.mkdir(pathFile)
    val targetFile = File("${pathFile.absoluteFile}${File.separator}$uploadFileName")

    // save file
    file.transferTo(targetFile)
    logger.info("$targetFile 成功保存到web服务器")

    val uploadIsSuccess = ftpUtil.upload("", targetFile)
    if (uploadIsSuccess) {
      logger.info("$targetFile 上传到ftp服务器成功成功")
    }

    // 删除文件
    FileUtil.del(targetFile)
    return FileUtil.getName(targetFile)
  }
}