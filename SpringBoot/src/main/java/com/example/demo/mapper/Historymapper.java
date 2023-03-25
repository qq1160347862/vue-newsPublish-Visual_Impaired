package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.History;
import com.example.demo.entity.News;
import com.example.demo.entity.Statistics;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface Historymapper extends BaseMapper<History> {

    public List<Statistics> getTypeByTime(@Param("timeString1") String timeString1);
    public List<Statistics> getTypeAndTime(@Param("timeString2") String timeString2,
                                           @Param("typeString") String typeString);

    public List<Statistics> getAllType(@Param("timeString3") String timeString3,
                                       @Param("timeString4") String timeString4);
}
