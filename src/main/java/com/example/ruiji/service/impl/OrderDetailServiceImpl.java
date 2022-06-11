package com.example.ruiji.service.impl;

import com.example.ruiji.entity.OrderDetail;
import com.example.ruiji.mapper.OrderDetailMapper;
import com.example.ruiji.service.IOrderDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单明细表 服务实现类
 * </p>
 *
 * @author 袁希宏
 * @since 2022-06-11
 */
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements IOrderDetailService {

}
