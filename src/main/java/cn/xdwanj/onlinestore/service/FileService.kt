package cn.xdwanj.onlinestore.service

import org.springframework.web.multipart.MultipartFile

interface FileService {
  /**
   * 文件上传
   *
   * @param file
   * @param path
   * @return
   */
  fun upload(file: MultipartFile, path: String): String
}