package cn.xdwanj.onlinestore.service.impl;

import cn.xdwanj.onlinestore.entity.Order;
import cn.xdwanj.onlinestore.mapper.OrderMapper;
import cn.xdwanj.onlinestore.service.OrderService;
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
open class OrderServiceImpl : ServiceImpl<OrderMapper, Order>(), OrderService {

}
