package com.example.ruiji.service;

import com.example.ruiji.entity.Orders;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单表 服务类
 * </p>
 *
 * @author 袁希宏
 * @since 2022-06-09
 */
public interface IOrdersService extends IService<Orders> {

    void submit(Orders orders);
}
