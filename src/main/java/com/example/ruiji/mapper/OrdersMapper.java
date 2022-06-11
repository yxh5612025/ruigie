package com.example.ruiji.mapper;

import com.example.ruiji.entity.Orders;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 订单表 Mapper 接口
 * </p>
 *
 * @author 袁希宏
 * @since 2022-06-09
 */
@Mapper
public interface OrdersMapper extends BaseMapper<Orders> {

}
