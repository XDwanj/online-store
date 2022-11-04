package cn.xdwanj.onlinestore.config

import cn.hutool.extra.ftp.Ftp
import cn.xdwanj.onlinestore.annotation.Slf4j
import cn.xdwanj.onlinestore.annotation.Slf4j.Companion.logger
import org.apache.commons.net.ftp.FTPClient
import org.apache.commons.net.ftp.FTPReply
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
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
  fun ftpClient() = FTPClient().apply {
    logger.info("ftp服务器初始化: {}", properties)

    connect(properties.ip)
    val isLoginSuccess = login(properties.username, properties.password)

    if (isLoginSuccess) {
      logger.info("ftp服务器连接成功")
    }
    bufferSize = 1024
    controlEncoding = "UTF-8"
    changeWorkingDirectory(FTP_WORK_DIR)
    setFileType(FTPClient.BINARY_FILE_TYPE)
    enterLocalPassiveMode()

    if (!FTPReply.isPositiveCompletion(this.replyCode)) {
      try {
        disconnect()
      } catch (e: IOException) {
        logger.error("关闭ftpClient失败", e)
      }
    } else {
      logger.info("ftp服务器连接成功二次确定")
    }
  }

  @Bean
  fun ftpUtil(ftpClient: FTPClient): Ftp {
    return Ftp(ftpClient)
  }
}

@Component
@ConfigurationProperties(prefix = "ftp")
class FtpConfigProperties {
  var ip: String = ""
  var username: String = ""
  var password: String = ""
  var serverPrefix: String = ""

  override fun toString(): String {
    return "FtpConfigProperties(ip='$ip', username='$username', password='$password', serverPrefix='$serverPrefix')"
  }
}