package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@TableName("history")
@Data           //自动填写set/get方法
public class History {

    private Integer userId;
    private Integer newId;
    private Date hTime;
}
