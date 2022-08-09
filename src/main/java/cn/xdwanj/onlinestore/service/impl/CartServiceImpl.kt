package cn.xdwanj.onlinestore.service.impl

import cn.xdwanj.onlinestore.common.*
import cn.xdwanj.onlinestore.entity.Cart
import cn.xdwanj.onlinestore.entity.Product
import cn.xdwanj.onlinestore.exception.BusinessException
import cn.xdwanj.onlinestore.mapper.CartMapper
import cn.xdwanj.onlinestore.service.CartService
import cn.xdwanj.onlinestore.service.ProductService
import cn.xdwanj.onlinestore.vo.CartProductVo
import cn.xdwanj.onlinestore.vo.CartVo
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import org.springframework.stereotype.Service
import java.math.BigDecimal

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author XDwanj
 * @since 2022-07-16
 */
@Service
class CartServiceImpl(
  private val productService: ProductService
) : ServiceImpl<CartMapper, Cart>(), CartService {

  override fun add(userId: Int, productId: Int, count: Int): CartVo? {
    val product: Product = productService.ktQuery()
      .eq(Product::id, productId)
      .one()
      ?: throw BusinessException(ResponseCode.ILLEGAL_ARGUMENT.desc, ResponseCode.ILLEGAL_ARGUMENT.code)

    val cartFromDB: Cart? = ktQuery()
      .eq(Cart::userId, userId)
      .eq(Cart::productId, product)
      .one()

    if (cartFromDB == null) {
      val cart = Cart().also {
        it.userId = userId
        it.productId = productId
        it.quantity = count
        it.checked = CartConst.CHECKED
      }
      save(cart)
    } else {
      ktUpdate()
        .eq(Cart::userId, userId)
        .eq(Cart::productId, productId)
        .set(Cart::checked, CartConst.CHECKED)
        .set(Cart::quantity, cartFromDB.quantity?.plus(count)
          ?: throw BusinessException("${cartFromDB.id} 的在DB中的值为空"))
        .update()
    }

    return getCartVoLimit(userId)

  }

  override fun updateCart(userId: Int, productId: Int, count: Int): CartVo? {
    TODO()
  }

  private fun getCartVoLimit(userId: Int): CartVo {
    val cartVo = CartVo()
    var cartTotalPrice = BigDecimal.ZERO
    val cartProductVoList = mutableListOf<CartProductVo>()

    val cartList = ktQuery()
      .eq(Cart::userId, userId)
      .list()

    // 购物车列表为空
    if (cartList.isEmpty()) {
      return CartVo(
        cartTotalPrice = BigDecimal.ZERO,
        cartProductVoList = mutableListOf(),
        allChecked = getAllCheckedStatus(userId),
        imageHost = FTP_HOST
      )
    }

    for (cartItem in cartList) {
      val cartProductVo = CartProductVo(
        id = cartItem.id,
        userId = cartItem.userId,
        productId = cartItem.productId
      )

      productService.getById(cartItem.productId)?.let { product ->
        cartProductVo.run {
          productMainImage = product.mainImage
          productName = product.name
          productSubtitle = product.subtitle
          productStatus = product.status
          productPrice = product.price
          productStock = product.stock
        }

        cartProductVo.quantity = if (product.stock!! >= cartItem.quantity!!) {
          // 库存充足
          cartProductVo.limitQuantity = CartConst.LIMIT_NUM_SUCCESS
          cartItem.quantity
        } else {
          cartProductVo.limitQuantity = CartConst.LIMIT_NUM_FAIL
          // 购物车中更新有效库存
          ktUpdate()
            .eq(Cart::id, cartItem.id)
            .set(Cart::quantity, product.stock)
            .update()
          product.stock
        }

        // 计算总价
        val price = product.price ?: throw BusinessException("商品无单价")
        val quantity = cartProductVo.quantity ?: 0
        cartProductVo.productTotalPrice = price * quantity.toBigDecimal()
        cartProductVo.productChecked = cartItem.checked
      }

      if (cartItem.checked == CartConst.CHECKED) {
        // 如果已经勾选了，添加到整个的购物车总价中
        cartTotalPrice += cartProductVo.productTotalPrice ?: BigDecimal.ZERO // 如果为空，就算0
      }

      cartProductVoList += cartProductVo
    }

    return cartVo.also {
      it.cartTotalPrice = cartTotalPrice
      it.cartProductVoList = cartProductVoList
      it.allChecked = getAllCheckedStatus(userId)
      it.imageHost = FTP_HOST
    }
  }

  private fun getAllCheckedStatus(userId: Int): Boolean = ktQuery()
    .eq(Cart::userId, userId)
    .eq(Cart::checked, CartConst.UN_CHECKED)
    .exists()
    .not()

}


