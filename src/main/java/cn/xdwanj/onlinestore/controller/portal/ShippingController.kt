package cn.xdwanj.onlinestore.controller.portal

import cn.xdwanj.onlinestore.annotation.Slf4j
import cn.xdwanj.onlinestore.common.CURRENT_USER
import cn.xdwanj.onlinestore.common.ResponseCode
import cn.xdwanj.onlinestore.common.ServerResponse
import cn.xdwanj.onlinestore.entity.Shipping
import cn.xdwanj.onlinestore.entity.User
import cn.xdwanj.onlinestore.service.ShippingService
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
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
 * @since 2022-07-16
 */
@Slf4j
@Tag(name = "物流模块")
@RestController
@RequestMapping("/shipping")
class ShippingController(
  private val shippingService: ShippingService
) {

  @Operation(summary = "添加地址")
  @PostMapping
  fun add(
    @Parameter(hidden = true)
    @SessionAttribute(CURRENT_USER)
    user: User,
    shipping: Shipping
  ): ServerResponse<Map<String, Int>> {
    shipping.id = null
    shipping.userId = user.id

    // 查询结束后，id 会映射到 entity 中
    val isSuccess = shippingService.save(shipping)

    return if (isSuccess) {
      ServerResponse.success("添加成功", data = mapOf("shippingId" to shipping.id!!))
    } else {
      ServerResponse.error("添加失败")
    }
  }

  @Operation(summary = "删除地址")
  @DeleteMapping
  fun delete(
    @Parameter(hidden = true)
    @SessionAttribute(CURRENT_USER)
    user: User,
    shippingId: Int
  ): ServerResponse<String> {
    if (shippingId < 1)
      return ServerResponse.error(ResponseCode.ILLEGAL_ARGUMENT.desc, ResponseCode.ILLEGAL_ARGUMENT.code)

    val isSuccess = shippingService.ktUpdate()
      .eq(Shipping::userId, user.id)
      .eq(Shipping::id, shippingId)
      .remove()

    return if (isSuccess) {
      ServerResponse.success("删除成功")
    } else {
      ServerResponse.error("删除失败")
    }
  }

  @Operation(summary = "更新地址")
  @PutMapping
  fun update(
    @Parameter(hidden = true)
    @SessionAttribute(CURRENT_USER)
    user: User,
    shipping: Shipping
  ): ServerResponse<String> {
    shippingService.ktQuery()
      .eq(Shipping::id, shipping.id)
      .eq(Shipping::userId, user.id)
      .exists()
      .let {
        if (!it) return ServerResponse.error("该地址不存在")
      }

    shipping.id = null
    shipping.userId = user.id

    val isSuccess = shippingService.ktUpdate()
      .update(shipping)

    return if (isSuccess) {
      ServerResponse.success("更新成功")
    } else {
      ServerResponse.error("更新失败")
    }
  }

  @Operation(summary = "查询地址")
  @GetMapping("/{shippingId}")
  fun get(
    @Parameter(hidden = true)
    @SessionAttribute(CURRENT_USER)
    user: User,
    @PathVariable shippingId: String
  ): ServerResponse<Shipping> {

    val shipping = shippingService.ktQuery()
      .eq(Shipping::userId, user.id)
      .eq(Shipping::id, shippingId)
      .one()
      ?: return ServerResponse.error("地址不存在")

    return ServerResponse.success(data = shipping)
  }

  @Operation(summary = "地址列表")
  @GetMapping("/list")
  fun list(
    @Parameter(hidden = true)
    @SessionAttribute(CURRENT_USER)
    user: User,
    @RequestParam(defaultValue = "1") pageNum: Int,
    @RequestParam(defaultValue = "10") pageSize: Int,
  ): ServerResponse<IPage<Shipping>> {
    if (pageNum < 1 || pageSize < 1)
      return ServerResponse.error(ResponseCode.ILLEGAL_ARGUMENT.desc, ResponseCode.ILLEGAL_ARGUMENT.code)

    val page = shippingService.ktQuery()
      .eq(Shipping::userId, user.id)
      .page(Page(pageNum.toLong(), pageSize.toLong()))

    return ServerResponse.success(data = page)
  }
}
