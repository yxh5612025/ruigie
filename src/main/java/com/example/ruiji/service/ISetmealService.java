package com.example.ruiji.service;

import com.example.ruiji.dto.SetmealDto;
import com.example.ruiji.entity.Setmeal;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 套餐 服务类
 * </p>
 *
 * @author 袁希宏
 * @since 2022-06-04
 */
public interface ISetmealService extends IService<Setmeal> {

    void addWithDish(SetmealDto setmealDto);

    SetmealDto getByIdWithDish(Long id);

    void updateWithDish(SetmealDto setmealDto);

    void delete(Long ids);
}
