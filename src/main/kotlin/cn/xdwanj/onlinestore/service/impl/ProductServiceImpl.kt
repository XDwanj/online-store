package cn.xdwanj.onlinestore.service.impl;

import cn.xdwanj.onlinestore.entity.Product;
import cn.xdwanj.onlinestore.mapper.ProductMapper;
import cn.xdwanj.onlinestore.service.ProductService;
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
open class ProductServiceImpl : ServiceImpl<ProductMapper, Product>(), ProductService {

}
