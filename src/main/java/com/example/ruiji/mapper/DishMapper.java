package com.example.ruiji.mapper;

import com.example.ruiji.entity.Dish;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 菜品管理 Mapper 接口
 * </p>
 *
 * @author 袁希宏
 * @since 2022-06-04
 */
@Mapper
public interface DishMapper extends BaseMapper<Dish> {

}
