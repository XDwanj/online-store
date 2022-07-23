package cn.xdwanj.onlinestore.service.impl;

import cn.xdwanj.onlinestore.common.ServerResponse
import cn.xdwanj.onlinestore.entity.Category;
import cn.xdwanj.onlinestore.mapper.CategoryMapper;
import cn.xdwanj.onlinestore.service.CategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author XDwanj
 * @since 2022-07-16
 */
@Service
open class CategoryServiceImpl : ServiceImpl<CategoryMapper, Category>(), CategoryService {
  override fun addCategory(categoryName: String, parentId: Int): ServerResponse<String> {
    save(Category().also {
      it.name = categoryName
      it.parentId = parentId
      it.status = true
      it.createTime = LocalDateTime.now()
      it.updateTime = LocalDateTime.now()
    }).let {
      if (!it) return ServerResponse.error("添加品类失败")
    }

    return ServerResponse.success("添加成功")
  }

  override fun updateCategory(categoryId: Int, categoryName: String): ServerResponse<String> {

    ktUpdate()
      .eq(Category::id, categoryId)
      .set(Category::name, categoryName)
      .set(Category::updateTime, LocalDateTime.now())
      .update().let {
        if (!it) return ServerResponse.error("更新失败")
      }

    return ServerResponse.success("更新品类名称成功")
  }
}
