package cn.xdwanj.onlinestore.service;

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
   * 递归查询类别
   *
   * @param categoryId
   * @return
   */
  fun deepCategory(categoryId: Int): List<Int>
}
