package cn.xdwanj.onlinestore.controller.backend

import cn.xdwanj.onlinestore.common.ServerResponse
import cn.xdwanj.onlinestore.entity.Product
import cn.xdwanj.onlinestore.service.ProductService
import cn.xdwanj.onlinestore.util.Slf4j
import cn.xdwanj.onlinestore.vo.ProductDetailVo
import cn.xdwanj.onlinestore.vo.ProductListVo
import com.baomidou.mybatisplus.core.metadata.IPage
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

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
  @PutMapping("/sale-status")
  fun setSaleStatus(productId: Int, status: Int): ServerResponse<String> {
    return productService.setSaleStatus(productId, status)
  }

  @Operation(summary = "获取商品详情")
  @GetMapping("/{productId}")
  fun getProduct(@PathVariable productId: Int): ServerResponse<ProductDetailVo> {
    return productService.getDetail(productId)
  }

  @Operation(summary = "获取商品列表")
  @GetMapping("/list")
  fun listProduct(pageNum: Int, pageSize: Int): ServerResponse<IPage<ProductListVo>> {
    if (pageNum < 1) return ServerResponse.error("页码不可小于1")
    if (pageSize < 1) return ServerResponse.error("总页数不可小于1")
    return productService.listProduct(pageNum, pageSize)
  }

  @Operation(summary = "商品搜索")
  @GetMapping("/search")
  fun searchProduct(productName: String, pageNum: Int, pageSize: Int): ServerResponse<IPage<ProductListVo>> {
    if (productName.isBlank()) return productService.listProduct(pageNum, pageSize)
    return productService.searchProduct(productName, pageNum, pageSize)
  }

  @Operation(summary = "文件上传")
  @PostMapping("/upload")
  fun upload(@RequestPart file: MultipartFile): ServerResponse<String> {
    TODO()
  }
}
