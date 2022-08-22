package cn.xdwanj.onlinestore.config

import cn.xdwanj.onlinestore.annotation.Slf4j
import cn.xdwanj.onlinestore.annotation.Slf4j.Companion.logger
import org.apache.commons.net.ftp.FTPClient
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import java.io.File
import java.io.IOException

@Slf4j
@Configuration
class FtpServerConfig(
  private val properties: FtpConfigProperties
) {
  companion object {
    private const val FTP_WORK_DIR = "img"
  }

  @Bean
  fun ftpClient(): FTPClient = FTPClient().apply {
    logger.info("ftp服务器初始化: {}", properties)

    // TODO：暂时不开启
    // connect(properties.ip)
    // login(properties.username, properties.password)
    // bufferSize = 1024
    // controlEncoding = "UTF-8"
    // changeWorkingDirectory(FTP_WORK_DIR)
    // setFileType(FTPClient.BINARY_FILE_TYPE)
    // enterLocalPassiveMode()
  }
}

fun FTPClient.uploadFile(fileList: List<File>): Boolean {
  logger.info("开始上传文件", fileList)
  try {
    for (file in fileList) {
      file.inputStream().use {
        this.storeFile(file.name, it)
      }
    }
  } catch (e: IOException) {
    logger.error("上传文件异常", e)
    e.printStackTrace()
    return false
  } finally {
    this.disconnect()
  }
  logger.info("上传文件结束", fileList)
  return true
}

@Component
@ConfigurationProperties(prefix = "ftp")
data class FtpConfigProperties(
  var ip: String = "",
  var username: String = "",
  var password: String = "",
  var serverPrefix: String = "",
)