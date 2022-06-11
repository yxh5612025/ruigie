package com.example.ruiji.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.ruiji.common.R;
import com.example.ruiji.dto.SetmealDto;
import com.example.ruiji.entity.Category;
import com.example.ruiji.entity.Setmeal;
import com.example.ruiji.service.ICategoryService;
import com.example.ruiji.service.ISetmealService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 套餐 前端控制器
 * </p>
 *
 * @author 袁希宏
 * @since 2022-06-04
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Resource
    private ISetmealService setmealService;
    @Resource
    private ICategoryService categoryService;

    @GetMapping("page")
    public R<Page> page(int page, int pageSize, String name) {
        Page<Setmeal> pageInfo = new Page<>(page, pageSize);
        Page<SetmealDto> setmealDtoPage = new Page<>(page, pageSize);
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(name != null, Setmeal::getName, name).orderByDesc(Setmeal::getUpdateTime);
        setmealService.page(pageInfo, queryWrapper);
//        return R.success(pageInfo);
        BeanUtils.copyProperties(pageInfo, setmealDtoPage, "records");
        List<Setmeal> records = pageInfo.getRecords();
        List<SetmealDto> list = records.stream().map(item -> {
            SetmealDto setmealDto = new SetmealDto();
            Long categoryId = item.getCategoryId();
            Category category = categoryService.getById(categoryId);
            if (category != null) {
                String categoryName = category.getName();
                setmealDto.setCategoryName(categoryName);
            }
            BeanUtils.copyProperties(item, setmealDto);
            return setmealDto;
        }).collect(Collectors.toList());
        setmealDtoPage.setRecords(list);
        return R.success(setmealDtoPage);
    }

    @PostMapping
    public R<?> add(@RequestBody SetmealDto setmealDto) {
        setmealService.addWithDish(setmealDto);
        return R.success("添加成功");
    }

    //    @PutMapping
//    public R<?> update(){
//
//    }
    @GetMapping("/{id}")
    public R<?> get(@PathVariable Long id) {

        SetmealDto list = setmealService.getByIdWithDish(id);
        return R.success(list);
    }

    @PutMapping
    public R<?> save(@RequestBody SetmealDto setmealDto) {
        setmealService.updateWithDish(setmealDto);
        return R.success("更新成功");
    }

    @PostMapping("status/{status}")
    public R<?> status(@PathVariable("status") Integer status, @RequestParam List<Long> ids) {
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(ids != null, Setmeal::getId, ids);
        List<Setmeal> list = setmealService.list(queryWrapper);
        for (Setmeal setmeal : list) {
            if (setmeal != null) {
                setmeal.setStatus(status);
                setmealService.updateById(setmeal);
            }
        }
        return R.success("状态修改成功");
    }

    @DeleteMapping
    public R<?> delete(Long ids) {
        setmealService.delete(ids);
        return R.success("删除成功");
    }

    @GetMapping("/list")
    public R<?> list(Long categoryId, Integer status) {
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Setmeal::getCategoryId, categoryId)
                .eq(Setmeal::getStatus, 1)
                .orderByDesc(Setmeal::getUpdateTime);
        List<Setmeal> setmeals = setmealService.list(queryWrapper);
        return R.success(setmeals);
    }
}
