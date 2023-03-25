package com.example.demo.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.common.Result;
import com.example.demo.entity.News;
import com.example.demo.entity.Statistics;
import com.example.demo.entity.User;
import com.example.demo.mapper.Historymapper;
import com.example.demo.mapper.Newsmapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/browserTrend")
public class BrowserTrendController {

    @Resource
    Historymapper historymapper;

    //  查询
    @GetMapping
    public Result<?> getTypeTrend(@RequestParam(defaultValue = "2022-07-04,2022-07-06") String Daterangepickvalue,
                                  @RequestParam String newsTypeSelected){



        int timeConst = 24 * 60 * 60 * 1000;
        String[] Timerange = Daterangepickvalue.split(",");           //分割字符串，两个具体时间



        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
        ParsePosition pp = new ParsePosition( 0 );

        String timeStringStart = Timerange[0];
        String timeStringEnd = Timerange[1];

        Date timeStart = df.parse(timeStringStart,pp);
        pp.setIndex(0);
        Date timeEnd = df.parse(timeStringEnd,pp);

        ArrayList timeList = new ArrayList();
        Vector<ArrayList> allCount = new Vector<ArrayList>();
        List<Statistics> allType = historymapper.getAllType(df.format(new Date(timeStart.getTime())),
                                                            df.format(new Date(timeEnd.getTime())));
        for (int i = 0; i < allType.size(); i++){
            ArrayList temp = new ArrayList();
            allCount.add(temp);
        }

        //构造对应新闻分类的数量列表 eg:  体育: [3,5,6,7] 分别对应不同时间
        int dayRange = (int) ((timeEnd.getTime() - timeStart.getTime()) / timeConst);
        for (int i = 0; i <= dayRange; i++){
            //当前循环时间
            String timeString = df.format(new Date(timeStart.getTime() + (long) i * timeConst));

            //记录当前时间
            timeList.add(timeString);


            //构造开始
            if(newsTypeSelected==""){
                List<Statistics> tempType = historymapper.getTypeByTime(timeString);
                for (int j = 0; j< allType.size(); j++){
                    List<Statistics> resTemp = historymapper.getTypeAndTime(timeString,allType.get(j).getNewType());
                    if (resTemp.isEmpty()){
                        allCount.get(j).add(null);
                    }
                    for (int k = 0; k < tempType.size(); k++){
                        if (allType.get(j).toString().equals(tempType.get(k).toString())){
                            allCount.get(j).add(resTemp.get(0).getCount());
                        }
                    }
                }

            }else {
                String[] newsTypeSelectedSplited = newsTypeSelected.split("&");
                ArrayList typeSelected = new ArrayList();
                for (int q=1;q<=newsTypeSelectedSplited.length-1;q++){
                    typeSelected.add(newsTypeSelectedSplited[q]);
                }

                for(int j = 0; j<allType.size(); j++){
                    List<Statistics> restemp2 = historymapper.getTypeAndTime(timeString,allType.get(j).getNewType());
                    if (restemp2.size() == 0){
                        System.out.println("******");
                        allCount.get(j).add(null);
                    }
                    for (int k = 0; k < typeSelected.size(); k++){
                        if (  (restemp2.size() != 0) && (allType.get(j).getNewType().equals(typeSelected.get(k).toString()))){
                            allCount.get(j).add(restemp2.get(0).getCount());
                        }
                    }
                }
            }

        }



        //构造JSON:series
        JSONArray series = new JSONArray();
        for (int i = 0; i < allType.size(); i++){


            JSONObject data = new JSONObject();
            data.put("type","line");
            data.put("data",allCount.get(i));
            data.put("stack","x");


            series.add(data);
        }

        //构造JSON:timeList
        JSONArray res = new JSONArray();
        res.add(timeList);
        res.add(timeList.size());
        res.add(series);


        return Result.success(res);
    }

    @Resource
    Newsmapper newsmapper;

    //  查询
    @GetMapping("/newType")
    public Result<?> getUsers(@RequestParam String search){
        List<News> res = newsmapper.getNewsType(search);
        return Result.success(res);
    }

}
