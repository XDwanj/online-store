package cn.xdwanj.onlinestore.service.impl

import cn.hutool.extra.ftp.Ftp
import cn.xdwanj.onlinestore.annotation.Slf4j.Companion.logger
import cn.xdwanj.onlinestore.constant.UPLOAD_PATH
import cn.xdwanj.onlinestore.service.FileService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.mock.web.MockMultipartFile
import org.springframework.test.context.ActiveProfiles
import java.io.File
import java.io.FileInputStream

@SpringBootTest
@ActiveProfiles("dev")
internal class FileServiceImplTest {
  @Autowired
  private lateinit var ftpUtil: Ftp

  @Autowired
  private lateinit var fileService: FileService

  @Test
  fun upload() {
    val file = File("/home/xdwanj/Downloads/banner.jpg")
    val inputStream = FileInputStream(file)
    val multipartFile = MockMultipartFile(file.name, inputStream)
    val targetFileName = fileService.upload(multipartFile, UPLOAD_PATH)
    logger.info(targetFileName)
  }

  @Test
  fun ftpUpload() {
    ftpUtil.upload(null, File("/home/xdwanj/Downloads/banner.jpg"))
    ftpUtil.delFile("banner.jpg")
  }
}