package com.example.demo.controller;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.Tools.NLPDemo;
import com.example.demo.common.Result;
import com.example.demo.entity.News;
import com.example.demo.mapper.Newsmapper;
import com.example.demo.service.Userservice;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.sql.Wrapper;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//@CrossOrigin
@RestController
@RequestMapping("/news")
public class NewsController {

    @Resource
    Newsmapper newsmapper;

    //  新增
    @PostMapping
    public Result<?> save(@RequestBody News news){
        news.setPubtime(new Date());
        Userservice userservice = new Userservice();
        userservice.addNews(news);
        newsmapper.insert(news);
        return Result.success();
    }
    //  查询
    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "5") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search){
        LambdaQueryWrapper<News> wrapper = Wrappers.<News>lambdaQuery().like(News::getTitle,search);
        if(StrUtil.isNotBlank(search)){
            wrapper.like(News::getTitle,search);

        }
        Page<News> newsPage = newsmapper.selectPage(new Page<>(pageNum,pageSize), wrapper);
        return Result.success(newsPage);
    }
    //更新
    @PutMapping
    public Result<?> update(@RequestBody News news){
        newsmapper.updateById(news);
        return Result.success();
    }
    //删除
    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id){
        newsmapper.deleteById(id);
        return Result.success();
    }
}
