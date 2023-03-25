package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.Statistics;
import com.example.demo.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface Usermapper extends BaseMapper<User> {

    public List<Statistics> getUserHistoryNewTypeCount(@Param("timeString1") String timeString1,
                                                       @Param("timeString2") String timeString2,
                                                       @Param("userIDString") int userIDString);

    public List<User> getAllUser(@Param("search") String search);
}
