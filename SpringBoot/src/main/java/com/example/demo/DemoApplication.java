package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


@SpringBootApplication
public class DemoApplication {



	public static void main(String[] args) {

		SpringApplication.run(DemoApplication.class, args);
//		int timeConst = 24 * 60 * 60 * 1000;
//		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
//		ParsePosition p1 = new ParsePosition( 0 );
//
//
//		String time = "2022-01-01";
//		String time2 = "2022-01-15";
//
//		Date a = df.parse(time,p1);
//		p1.setIndex(0);
//		Date b = df.parse(time2,p1);
//
//		String res = df.format(new Date(a.getTime()));
//		System.out.println( (int)((b.getTime() - a.getTime()) / timeConst) );
//		System.out.println("你好世界");
//		System.out.println(df.format(new Date(a.getTime() - 2 * timeConst)));
//		System.out.println(df.format(new Date(a.getTime() + 3 * timeConst)));
	}
}
