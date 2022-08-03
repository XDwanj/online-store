package cn.xdwanj.onlinestore.controller.portal

import cn.xdwanj.onlinestore.common.ResponseCode
import cn.xdwanj.onlinestore.common.ServerResponse
import cn.xdwanj.onlinestore.service.ProductService
import cn.xdwanj.onlinestore.util.Slf4j
import cn.xdwanj.onlinestore.vo.ProductDetailVo
import cn.xdwanj.onlinestore.vo.ProductListVo
import com.baomidou.mybatisplus.core.metadata.IPage
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author XDwanj
 * @since 2022-08-03
 */
@Slf4j
@Tag(name = "商品管理")
@RestController
@RequestMapping("/product")
class ProductController(
  private val productService: ProductService
) {

  @Operation(summary = "获取商品详情")
  @GetMapping("/detail/{productId}")
  fun getDetail(@PathVariable productId: Int): ServerResponse<ProductDetailVo> {
    return productService.getDetail(productId)
  }

  @Operation(summary = "获取商品集合")
  @GetMapping("/list", "/search")
  fun listDetail(
    pageNum: Int,
    pageSize: Int,
    keyword: String = "",
    categoryId: Int,
    @Parameter(description = "格式：'排序规则_排序字段'，默认不排序") orderBy: String = ""
  ): ServerResponse<IPage<ProductListVo>> {
    if (pageNum < 1 || pageSize < 1)
      return ServerResponse.error(ResponseCode.ILLEGAL_ARGUMENT.desc, ResponseCode.ILLEGAL_ARGUMENT.code)

    return productService.listProduct(pageNum, pageSize, keyword, categoryId, orderBy)
  }

}