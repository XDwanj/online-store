package cn.xdwanj.onlinestore.service.impl

import org.springframework.stereotype.Service

@Service
class TestServiceImpl {

  fun npe(): Int {
    val str: String? = null

    val num = str!!.toInt()
    return num
  }
}