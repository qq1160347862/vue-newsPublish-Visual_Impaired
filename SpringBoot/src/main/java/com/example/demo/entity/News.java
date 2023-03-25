package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@TableName("news")
@Data           //自动填写set/get方法
public class News {
    @TableId(value = "new_id" ,type = IdType.AUTO)
    private Integer newId;
    private String type;
    private String title;
    private String author;
    private String content;
//    @JsonFormat(pattern = "yyyy-MM-dd HH:nn:ss",timezone = "GMT+8")
    private Date pubtime;
}
