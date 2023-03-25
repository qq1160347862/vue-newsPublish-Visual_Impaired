package com.example.demo.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("user")
@Data           //自动填写set/get方法
public class User {
    @TableId(value = "user_id" ,type = IdType.AUTO)
    private Integer userId;
    private String userTel;
    private String userPwd;
    private String userName;
}
