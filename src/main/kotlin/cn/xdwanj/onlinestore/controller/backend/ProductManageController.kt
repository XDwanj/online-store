package cn.xdwanj.onlinestore.controller.backend

import cn.xdwanj.onlinestore.common.ServerResponse
import cn.xdwanj.onlinestore.entity.Product
import cn.xdwanj.onlinestore.service.ProductService
import cn.xdwanj.onlinestore.util.Slf4j
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.apache.catalina.Server
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.function.ServerRequest
import javax.servlet.http.HttpSession

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author XDwanj
 * @since 2022-07-16
 */
@Tag(name = "商品管理")
@Slf4j
@RestController
@RequestMapping("/manage/product")
class ProductManageController(
  private val productService: ProductService
) {

  @Operation(summary = "商品保存")
  @PostMapping
  fun save(product: Product): ServerResponse<String> {
    product.id = null
    return productService.saveProduct(product)
  }

  @Operation(summary = "商品更新")
  @PutMapping
  fun update(product: Product): ServerResponse<String> {
    return productService.updateProduct(product)
  }

  @Operation(summary = "设置商品销售状态")
  @PatchMapping("/sale-status")
  fun setSaleStatus(productId: Int, status: Int): ServerResponse<String> {
    return productService.setSaleStatus(productId, status)
  }

  @Operation(summary = "获取商品详情")
  @GetMapping
  fun detail(productId: Int): ServerResponse<Product> {
    return productService.getDetail(productId)
  }
}
