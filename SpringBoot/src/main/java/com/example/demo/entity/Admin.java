package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("admin")
@Data           //自动填写set/get方法
public class Admin {
    @TableId(value = "admin_id" ,type = IdType.AUTO)
    private Integer adminId;
    private String adminName;
    private String adminPwd;
}
