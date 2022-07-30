package cn.xdwanj.onlinestore.service.impl;

import cn.xdwanj.onlinestore.common.ServerResponse
import cn.xdwanj.onlinestore.entity.Product;
import cn.xdwanj.onlinestore.mapper.ProductMapper;
import cn.xdwanj.onlinestore.service.ProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.catalina.Server
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
class ProductServiceImpl : ServiceImpl<ProductMapper, Product>(), ProductService {
  override fun saveProduct(product: Product): ServerResponse<String> {
    if (save(product)) {
      return ServerResponse.success("保存成功")
    }

    return ServerResponse.error("保存失败")
  }

  override fun updateProduct(product: Product): ServerResponse<String> {
    ktUpdate()
      .eq(Product::id, product.id)
      .setEntity(product)
      .update().let {
        if (it) return ServerResponse.success("更新成功")
      }

    return ServerResponse.error("更新失败")
  }

  override fun setSaleStatus(productId: Int, status: Int): ServerResponse<String> {
    ktUpdate()
      .eq(Product::id, productId)
      .set(Product::status, status)
      .update().let {
        if (it) return ServerResponse.success("状态更新成功")
      }

    return ServerResponse.error("状态更新失败")
  }

  override fun getDetail(productId: Int): ServerResponse<Product> {
    TODO("Not yet implemented")
  }
}
