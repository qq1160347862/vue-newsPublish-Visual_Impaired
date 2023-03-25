package com.example.demo.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.Admin;
import com.example.demo.entity.News;
import com.example.demo.entity.User;
import com.example.demo.mapper.Adminmapper;
import com.example.demo.mapper.Usermapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;


@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    Usermapper usermapper;
    @Resource
    Adminmapper adminmapper;

    //  登录(管理员)
    @PostMapping("/login")
    public Result<?> login(@RequestBody Admin admin){
        Admin res = adminmapper.selectOne(Wrappers.<Admin>lambdaQuery().eq(Admin::getAdminName,
                admin.getAdminName()).eq(Admin::getAdminPwd,admin.getAdminPwd()));
        System.out.println(admin.toString());
        if(res == null){
            return Result.error("-1","账号或者密码错误");

        }
        return Result.success(res);
    }
    //  查询
    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "5") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search){
        LambdaQueryWrapper<User> wrapper = Wrappers.<User>lambdaQuery().like(User::getUserTel,search);
        if(StrUtil.isNotBlank(search)){
            wrapper.like(User::getUserTel,search);

        }
        Page<User> newsPage = usermapper.selectPage(new Page<>(pageNum,pageSize), wrapper);
        return Result.success(newsPage);
    }
    //  新增
    @PostMapping
    public Result<?> save(@RequestBody User user){
        System.out.println(user.toString());
        usermapper.insert(user);
        return Result.success();
    }
    //更新
    @PutMapping
    public Result<?> update(@RequestBody User user){
        usermapper.updateById(user);
        return Result.success();
    }
    //删除
    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id){
        usermapper.deleteById(id);
        return Result.success();
    }
}
