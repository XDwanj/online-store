package cn.xdwanj.onlinestore.service;

import cn.xdwanj.onlinestore.common.ServerResponse
import cn.xdwanj.onlinestore.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import javax.swing.text.StyledEditorKit.BoldAction

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
  fun checkEmail(email: String?, userId: Int): Boolean
  fun checkPassword(userId: Int, password: String): Boolean
  fun login(username: String, password: String): ServerResponse<User>
  fun register(user: User): ServerResponse<User>
  fun getQuestion(username: String): ServerResponse<String>
  fun checkAnswer(username: String, question: String, answer: String): ServerResponse<User>
  fun forgetResetPassword(username: String, passwordNew: String, forgetToken: String): ServerResponse<String>
  fun resetPassword(user: User, passwordOld: String, passwordNew: String): ServerResponse<String>
  fun updateInfo(user: User): ServerResponse<User>
  fun getInfo(id: Int?): ServerResponse<User>
}
