package cn.xdwanj.onlinestore.service.impl;

import cn.xdwanj.onlinestore.entity.Category;
import cn.xdwanj.onlinestore.mapper.CategoryMapper;
import cn.xdwanj.onlinestore.service.CategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
