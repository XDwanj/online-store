package cn.xdwanj.onlinestore.service;

import cn.xdwanj.onlinestore.common.ServerResponse
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
  fun checkValid(str: String?, type: String?): ServerResponse<User>
  fun checkUsername(username: String?): Boolean
  fun checkEmail(email: String?): Boolean
  fun login(username: String, password: String): ServerResponse<User>
  fun register(user: User): ServerResponse<User>
  fun getQuestion(username: String): ServerResponse<String>
  fun checkAnswer(username: String, question: String, answer: String): ServerResponse<String>

}
