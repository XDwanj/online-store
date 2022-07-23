package cn.xdwanj.onlinestore.service;

import cn.xdwanj.onlinestore.common.ServerResponse
import cn.xdwanj.onlinestore.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author XDwanj
 * @since 2022-07-16
 */
interface CategoryService : IService<Category> {
  /**
   * 添加类别
   *
   * @param categoryName
   * @param parentId
   * @return
   */
  fun addCategory(categoryName: String, parentId: Int): ServerResponse<String>

  /**
   * 更新类别
   *
   * @param categoryId
   * @param categoryName
   * @return
   */
  fun updateCategory(categoryId: Int, categoryName: String): ServerResponse<String>
}
