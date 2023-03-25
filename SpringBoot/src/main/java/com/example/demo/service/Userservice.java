package com.example.demo.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.Tools.NLPDemo;
import com.example.demo.entity.News;

public class Userservice {

    public void addNews(News news){
        //新闻未定义标签时
        if(news.getType() == null) {
            NLPDemo nlp = new NLPDemo();
            JSONObject res = nlp.testIndustry(news.getTitle(),news.getContent());
            JSONObject temp1 = res.getJSONObject("body");
            JSONArray temp2 = temp1.getJSONArray("items");
            JSONObject temp3 = temp2.getJSONObject(0);

            String labelName = "";
            String subIndustries = "";
            String events = "";
            JSONArray temp4 = temp3.getJSONArray("industries");
            if(temp4.isEmpty()){
                System.out.println("subIndustries is null");
            }else {
                JSONObject temp5 = temp4.getJSONObject(0);
                labelName = temp5.getString("labelName");
            }
            JSONArray temp6 = temp3.getJSONArray("subIndustries");
            if(temp6.isEmpty()){
                System.out.println("subIndustries is null");
            }else {
                JSONObject temp7 = temp6.getJSONObject(0);
                subIndustries = temp7.getString("labelName");
            }
            JSONArray temp8 = temp3.getJSONArray("events");
            if(temp8.isEmpty()){

            }else {
                JSONObject temp9 = temp8.getJSONObject(0);
                events = temp9.getString("labelName");
            }
//            String TypeClassified = NewsTypeClassify.runLoadModelAndUse(news.getContent());
//            优先选择小分类新闻标签
            if(events == ""){
                if(subIndustries == ""){
                    if(labelName == ""){
                        news.setType("暂无分类");
                    }else {
                        news.setType(labelName);
                    }
                }else {
                    news.setType(subIndustries);
                }
            }else {
                news.setType(events);
            }
        }
    }
}
