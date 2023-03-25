//package com.example.demo.Tools;
//
////import org.thunlp.text.classifiers.BasicTextClassifier;
////import org.thunlp.text.classifiers.ClassifyResult;
////import org.thunlp.text.classifiers.LinearBigramChineseTextClassifier;
//
//
//public class NewsTypeClassify {
//    public static String runLoadModelAndUse(String news) {
//        // 新建分类器对象
//        BasicTextClassifier classifier = new BasicTextClassifier();
//
//        // 设置分类种类，并读取模型
//        String CategoryPath = System.getProperty("user.dir") + "/SpringBoot/src/main/resources/TypeClassfityModel/category";
//        String ModelPath = System.getProperty("user.dir") + "/SpringBoot/src/main/resources/TypeClassfityModel";
//        classifier.loadCategoryListFromFile(CategoryPath);
//        classifier.setTextClassifier(new LinearBigramChineseTextClassifier(classifier.getCategorySize()));
//        classifier.getTextClassifier().loadModel(ModelPath);
//
//        // 之后就可以使用分类器进行分类
//        int topN = 3;  // 保留最有可能的3个结果
////      分类结果序号  result[i].label
////      分类结果字符  classifier.getCategoryName(result[i].label)
////      分类结果确认率 result[i].prob
//        ClassifyResult[] result = classifier.classifyText(news, topN);
//        //result[0]为确认率最高的分类项
//        return classifier.getCategoryName(result[0].label);
//    }
//}
