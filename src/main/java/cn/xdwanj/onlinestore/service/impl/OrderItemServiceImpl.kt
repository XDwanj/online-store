package cn.xdwanj.onlinestore.service.impl;

import cn.xdwanj.onlinestore.entity.OrderItem;
import cn.xdwanj.onlinestore.mapper.OrderItemMapper;
import cn.xdwanj.onlinestore.service.OrderItemService;
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
class OrderItemServiceImpl : ServiceImpl<OrderItemMapper, OrderItem>(), OrderItemService
