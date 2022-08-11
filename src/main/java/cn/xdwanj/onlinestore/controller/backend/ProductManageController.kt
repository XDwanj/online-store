package cn.xdwanj.onlinestore.controller.backend

import cn.xdwanj.onlinestore.common.FTP_HOST
import cn.xdwanj.onlinestore.common.ResponseCode
import cn.xdwanj.onlinestore.common.ServerResponse
import cn.xdwanj.onlinestore.common.UPLOAD_PATH
import cn.xdwanj.onlinestore.entity.Product
import cn.xdwanj.onlinestore.service.FileService
import cn.xdwanj.onlinestore.service.ProductService
import cn.xdwanj.onlinestore.annotation.Slf4j
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
@Tag(name = "后台商品管理")
@Slf4j
@RestController
@RequestMapping("/manage/product")
class ProductManageController(
  private val productService: ProductService,
  private val fileService: FileService
) {

  @Operation(summary = "商品保存")
  @PostMapping
  fun save(product: Product): ServerResponse<String> {
    if (productService.save(product)) {
      return ServerResponse.success("保存成功")
    }
    return ServerResponse.error("保存失败")
  }

  @Operation(summary = "商品更新")
  @PutMapping
  fun update(product: Product): ServerResponse<String> {
    productService.ktUpdate()
      .eq(Product::id, product.id)
      .setEntity(product)
      .update()
      .let {
        if (it) return ServerResponse.success("更新成功")
      }

    return ServerResponse.error("更新失败")
  }

  @Operation(summary = "设置商品销售状态")
  @PutMapping("/sale-status")
  fun setSaleStatus(productId: Int, status: Int): ServerResponse<String> {
    productService.ktUpdate()
      .eq(Product::id, productId)
      .set(Product::status, status)
      .update()
      .let {
        if (it) return ServerResponse.success("更新成功")
      }

    return ServerResponse.error("更新失败")
  }

  @Operation(summary = "获取商品详情")
  @GetMapping("/{productId}")
  fun getProduct(@PathVariable productId: Int): ServerResponse<ProductDetailVo> {
    val productDetailVo = productService.getDetailByManage(productId)
      ?: return ServerResponse.error("商品不存在")
    return ServerResponse.success(data = productDetailVo)
  }

  @Operation(summary = "获取商品列表，带搜索功能")
  @GetMapping("/list", "/search")
  fun listProduct(
    // 这里有一个问题，使用 defaultValue="" 还是使用 required=false
    // 我的建议是使用 kotlin 特性，这样在 swagger 不会有多余的介绍
    // kotlin默认值是没有 springdoc 功能的
    keyword: String = "",
    @RequestParam(defaultValue = "1") pageNum: Int,
    @RequestParam(defaultValue = "5") pageSize: Int
  ): ServerResponse<IPage<ProductListVo>> {
    if (pageNum < 1 || pageSize < 1)
      return ServerResponse.error(ResponseCode.ILLEGAL_ARGUMENT.msg, ResponseCode.ILLEGAL_ARGUMENT.code)

    val page = productService.listProductByManage(pageNum, pageSize, keyword)
    return ServerResponse.success(data = page)
  }

  @Operation(summary = "文件上传")
  @PostMapping("/upload/file")
  fun upload(@RequestPart file: MultipartFile): ServerResponse<Map<String, String>> {
    val uri = fileService.upload(file, UPLOAD_PATH)
    val url = "$FTP_HOST/$uri"
    val resultMap = mapOf(
      "uri" to uri,
      "url" to url
    )
    return ServerResponse.success(data = resultMap)
  }

  @Operation(summary = "富文本上传")
  @PostMapping("/upload/rich-text")
  fun uploadRichText(): ServerResponse<String> {
    TODO("回头实现富文本上传，这里要考虑当今最流行的富文本框架有什么")
  }
}
