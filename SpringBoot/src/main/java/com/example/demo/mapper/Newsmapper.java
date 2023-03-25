package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.News;
import com.example.demo.entity.Statistics;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface Newsmapper extends BaseMapper<News> {

    public List<News> getNewsType(@Param("search") String search);
    public int getCountAll();
    public int getCount(@Param("timeString") String timeString,@Param("typeString2") String typeString2);

    public List<Statistics> getRangecount(@Param("timeString1") String timeString1, @Param("timeString2") String timeString2);

    public List<Statistics> getAllType(@Param("timeString3") String timeString3, @Param("timeString4") String timeString4);


    public List<Statistics> getTypeByTime(@Param("timeString5") String timeString5);

    public List<Statistics> getTypeAndTime(@Param("timeString6") String timeString6,@Param("typeString") String typeString);


}
