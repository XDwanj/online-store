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
    save(Category().apply {
      name = categoryName
      id = parentId
      status = true
      createTime = LocalDateTime.now()
      updateTime = LocalDateTime.now()
    }).let {
      if (!it) return ServerResponse.createByError("添加品类失败")
    }

    return ServerResponse.createBySuccess("添加成功")
  }

}
