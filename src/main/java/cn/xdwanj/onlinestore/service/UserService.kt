package cn.xdwanj.onlinestore.service;

import cn.xdwanj.onlinestore.common.CommonResponse
import cn.xdwanj.onlinestore.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

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
  fun checkValid(str: String?, type: String?): CommonResponse<User>

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
  fun login(username: String, password: String): User?


//  /**
//   * 返回忘记密码的问题
//   *
//   * @param username
//   * @return
//   */
//  fun getQuestion(username: String): ServerResponse<String>

//  /**
//   * 校验答案
//   *
//   * @param username
//   * @param question
//   * @param answer
//   * @return
//   */
//  fun checkAnswer(username: String, question: String, answer: String): ServerResponse<String>

//  /**
//   * 忘记密码的情况下重置密码
//   *
//   * @param username
//   * @param passwordNew
//   * @param forgetToken
//   * @return
//   */
//  fun forgetResetPassword(username: String, passwordNew: String, forgetToken: String): ServerResponse<String>

//  /**
//   * 登录状态下重置密码
//   *
//   * @param user
//   * @param passwordOld
//   * @param passwordNew
//   * @return
//   */
//  fun resetPassword(user: User, passwordOld: String, passwordNew: String): ServerResponse<String>

//  /**
//   * TODO更新用户信息
//   *
//   * @param user
//   * @return
//   */
//  fun updateInfo(user: User): ServerResponse<User>

//  /**
//   * 获取用户信息
//   *
//   * @param id
//   * @return
//   */
//  fun getInfo(id: Int?): ServerResponse<User>


}
