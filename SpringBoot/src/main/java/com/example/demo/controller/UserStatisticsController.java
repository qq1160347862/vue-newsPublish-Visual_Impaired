package com.example.demo.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.Statistics;
import com.example.demo.entity.User;
import com.example.demo.mapper.Usermapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/userStatistics")
public class UserStatisticsController {

    @Resource
    Usermapper usermapper;

    @GetMapping
    public Result<?> findPage(@RequestParam Integer statisticsType,
                              @RequestParam(defaultValue = "2022-01-01,2022-10-01") String Daterangepickvalue,
                              @RequestParam Integer userID){
        List<Statistics> restemp = null;
//        int count1 = 0;
        List newstype = new LinkedList();
        List count = new LinkedList();
        ArrayList res = new ArrayList();

        String[] Timerange = Daterangepickvalue.split(",");           //分割字符串，两个具体时间
        if(statisticsType == 1){
            if(StrUtil.isNotBlank(Daterangepickvalue)){
                System.out.println(Daterangepickvalue);
                restemp = usermapper.getUserHistoryNewTypeCount(Timerange[0],Timerange[1],userID);
                for(int i = 0; i < restemp.size(); i++){
                    newstype.add(restemp.get(i).getNewType());
                    count.add(restemp.get(i).getCount());
                }
                if(!newstype.isEmpty() && !count.isEmpty()){
                    res.add(newstype);
                    res.add(count);
                }
                return Result.success(res);
            }else {
                System.out.println("时间为空");
                return Result.error("-1","时间为空");
            }
        }else {
            return Result.success();
        }
    }



    //  查询
    @GetMapping("/user")
    public Result<?> getUsers(@RequestParam String search){
        List<User> res = usermapper.getAllUser(search);
        return Result.success(res);
    }
}
