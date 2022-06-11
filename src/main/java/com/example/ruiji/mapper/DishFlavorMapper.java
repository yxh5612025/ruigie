package com.example.ruiji.mapper;

import com.example.ruiji.entity.DishFlavor;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 菜品口味关系表 Mapper 接口
 * </p>
 *
 * @author 袁希宏
 * @since 2022-06-06
 */
@Mapper
public interface DishFlavorMapper extends BaseMapper<DishFlavor> {

    @Insert("")
    void saveFlavor(Long id);
}
