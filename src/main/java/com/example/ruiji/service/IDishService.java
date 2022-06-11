package com.example.ruiji.service;

import com.example.ruiji.dto.DishDto;
import com.example.ruiji.entity.Dish;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 菜品管理 服务类
 * </p>
 *
 * @author 袁希宏
 * @since 2022-06-04
 */
public interface IDishService extends IService<Dish> {

    //新增菜品，同时插入菜品对应的口味数据，需要操作两张表：dish、dish_flavor
    void saveWithFlavor(DishDto dishDto);

    //根据id查询菜品信息和对应的口味信息
    DishDto getByIdWithFlavor(Long id);

    //更新菜品信息，同时更新口味信息。
    void updateWithFlavor(DishDto dishDto);

    void deleteByIds(List<Long> ids);
}
