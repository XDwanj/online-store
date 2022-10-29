package cn.xdwanj.onlinestore.service;

import cn.xdwanj.onlinestore.entity.User
import cn.xdwanj.onlinestore.response.CommonResponse
import com.baomidou.mybatisplus.extension.service.IService

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author XDwanj
 * @since 2022-07-16
 */
interface UserService : IService<User> {
  /**
   * 校验数据是否有效
   *
   * @param str
   * @param type
   * @return
   */
  fun checkValid(str: String?, type: String?): CommonResponse<String>

  /**
   * 校验用户名
   *
   * @param username
   * @return
   */
  fun checkUsername(username: String?): Boolean

  /**
   * 校验邮箱是否有效
   *
   * @param email
   * @return
   */
  fun checkEmail(email: String?): Boolean

  /**
   * 通过用户名，校验邮箱是否有效
   *
   * @param email
   * @param userId
   * @return
   */
  fun checkEmail(email: String?, userId: Int): Boolean

  /**
   * 校验密码
   *
   * @param userId
   * @param password
   * @return
   */
  fun checkPassword(userId: Int, password: String): Boolean

  /**
   * 判断用户是否是管理员
   *
   * @param user
   * @return
   */
  fun checkAdmin(user: User): Boolean

  /**
   * 登录
   *
   * @param username
   * @param password
   * @return
   */
  fun login(username: String, password: String): User
}
