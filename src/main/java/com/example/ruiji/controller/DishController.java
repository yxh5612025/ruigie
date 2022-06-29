package com.example.ruiji.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.ruiji.common.R;
import com.example.ruiji.dto.DishDto;
import com.example.ruiji.entity.Category;
import com.example.ruiji.entity.Dish;
import com.example.ruiji.entity.DishFlavor;
import com.example.ruiji.service.ICategoryService;
import com.example.ruiji.service.IDishFlavorService;
import com.example.ruiji.service.IDishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * <p>
 * 菜品管理 前端控制器
 * </p>
 *
 * @author 袁希宏
 * @since 2022-06-04
 */
@Slf4j
@RestController
@RequestMapping("/dish")
public class DishController {
    @Resource
    private IDishService dishService;
    @Resource
    private IDishFlavorService dishFlavorService;
    @Resource
    private ICategoryService categoryService;
    @Resource
    private RedisTemplate redisTemplate;


    /**
     * 新增菜品
     */
    @PostMapping
    public R<?> save(@RequestBody DishDto dishDto) {
        log.info(dishDto.toString());
        dishService.saveWithFlavor(dishDto);
        String key = "dish_"+dishDto.getCategoryId()+"_"+dishDto.getStatus();
        redisTemplate.delete(key);
        return R.success("新增菜品成功");
    }

    /**
     * 菜品信息分页查询
     *
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name) {

        //构造分页构造器对象
        Page<Dish> pageInfo = new Page<>(page, pageSize);
        Page<DishDto> dishDtoPage = new Page<>();

        //条件构造器
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        //添加过滤条件
        queryWrapper.like(name != null, Dish::getName, name);
        //添加排序条件
        queryWrapper.orderByDesc(Dish::getUpdateTime);

        //执行分页查询
        dishService.page(pageInfo, queryWrapper);
//        return R.success(pageInfo);
        //对象拷贝
        BeanUtils.copyProperties(pageInfo, dishDtoPage, "records");

        List<Dish> records = pageInfo.getRecords();

        List<DishDto> list = records.stream().map((item) -> {
            DishDto dishDto = new DishDto();

            BeanUtils.copyProperties(item, dishDto);

            Long categoryId = item.getCategoryId();//分类id
            //根据id查询分类对象
            Category category = categoryService.getById(categoryId);

            if (category != null) {
                String categoryName = category.getName();
                dishDto.setCategoryName(categoryName);
            }
            return dishDto;
        }).collect(Collectors.toList());

        dishDtoPage.setRecords(list);

        return R.success(dishDtoPage);
    }

    /**
     * 根据id查询菜品信息和对应的口味信息
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<DishDto> get(@PathVariable Long id) {

        DishDto dishDto = dishService.getByIdWithFlavor(id);

        return R.success(dishDto);
    }

    /**
     * 修改菜品
     */
    @PutMapping
    public R<?> update(@RequestBody DishDto dishDto) {
        log.info(dishDto.toString());
        dishService.updateWithFlavor(dishDto);
//        Set keys = redisTemplate.keys("dish_*");
//        redisTemplate.delete(keys);
        String key = "dish_"+dishDto.getCategoryId()+"_"+dishDto.getStatus();
        redisTemplate.delete(key);
        return R.success("修改菜品成功");
    }

    /**
     * 停售和起售
     * @param status
     * @param ids
     * @return
     */
//    @PostMapping("/status/{status}")
//    public R<?> status(@PathVariable Integer status,List<Long> ids){
//        //log.info("status:{}",status);
//        //log.info("ids:{}",ids);
//        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.in(ids !=null,Dish::getId,ids);
//        //根据数据进行批量查询
//        List<Dish> list = dishService.list(queryWrapper);
//
////        List<Object> dishs = list.stream().map(item -> {
////            if (item != null) {
////                item.setStatus(status);
////                dishService.updateById(item);
////            }
////            return null;
////        }).collect(Collectors.toList());
//        for (Dish dish : list) {
//            if (dish != null){
//                dish.setStatus(status);
//                dishService.updateById(dish);
//            }
//
//        }
//        return R.success("售卖状态修改成功");
//
//
//        }

    /**
     * 对菜品批量或者是单个 进行停售或者是起售
     *
     * @return
     */
    @PostMapping("/status/{status}")
//这个参数这里一定记得加注解才能获取到参数，否则这里非常容易出问题
    public R<String> status(@PathVariable("status") Integer status, @RequestParam Long[] ids) {
        // 增加日志验证是否接收到前端参数。
//        log.info("根据id修改菜品的状态:{},id为：{}", status, ids);
        // 通过id查询数据库。修改id为ids数组中的数据的菜品状态status为前端页面提交的status。
        for (Long id : ids) {
            //根据id得到每个dish菜品。
            Dish dish = dishService.getById(id);
            dish.setStatus(status);
            dishService.updateById(dish);
        }
        return R.success("修改菜品状态成功");
        }
    @DeleteMapping
    public R<String> delete(@RequestParam("ids") List<Long> ids){
        //删除菜品  这里的删除是逻辑删除
        dishService.deleteByIds(ids);

        return R.success("菜品删除成功");
    }
//    @GetMapping("list")
//    public R<?> list(Long categoryId){
//        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(Dish::getCategoryId,categoryId)
//                .eq(Dish::getStatus,1)
//                .orderByDesc(Dish::getUpdateTime);
//        List<Dish> list = dishService.list(queryWrapper);
//        return R.success(list);
//    }
     @GetMapping("list")
     public R<List<DishDto>> list(Dish dish){
         List<DishDto> dishDtoList =null;
        String key = "dish_"+dish.getCategoryId()+"_"+dish.getStatus();
        //先从redis中获取缓存数据
         dishDtoList = (List<DishDto>) redisTemplate.opsForValue().get(key);
         //如果存在，直接返回无需查询数据库
            if (dishDtoList!=null){
                return R.success(dishDtoList);
            }
         //如果不存在，需要查询数据库，将查询到的菜品数据缓存到redis
         //构造查询条件
         LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
         queryWrapper.eq(dish.getCategoryId() != null ,Dish::getCategoryId,dish.getCategoryId());
         //添加条件，查询状态为1（起售状态）的菜品
         queryWrapper.eq(Dish::getStatus,1);

         //添加排序条件
         queryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);

         List<Dish> list = dishService.list(queryWrapper);

          dishDtoList = list.stream().map((item) -> {
             DishDto dishDto = new DishDto();

             BeanUtils.copyProperties(item,dishDto);

             Long categoryId = item.getCategoryId();//分类id
             //根据id查询分类对象
             Category category = categoryService.getById(categoryId);

             if(category != null){
                 String categoryName = category.getName();
                 dishDto.setCategoryName(categoryName);
             }

             //当前菜品的id
             Long dishId = item.getId();
             LambdaQueryWrapper<DishFlavor> lambdaQueryWrapper = new LambdaQueryWrapper<>();
             lambdaQueryWrapper.eq(DishFlavor::getDishId,dishId);
             //SQL:select * from dish_flavor where dish_id = ?
             List<DishFlavor> dishFlavorList = dishFlavorService.list(lambdaQueryWrapper);
             dishDto.setFlavors(dishFlavorList);
             return dishDto;
         }).collect(Collectors.toList());
          //如果不存在，需要查询数据库，将查询到的菜品数据缓存到redis
         redisTemplate.opsForValue().set(key,dishDtoList,60, TimeUnit.MINUTES);
         return R.success(dishDtoList);
     }
}


