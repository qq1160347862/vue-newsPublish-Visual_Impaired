package com.example.demo.Tools;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chinamobile.cmss.sdk.nlp.ECloudDefaultClient;
import com.chinamobile.cmss.sdk.nlp.http.constant.Region;
import com.chinamobile.cmss.sdk.nlp.http.signature.Credential;
import com.chinamobile.cmss.sdk.nlp.request.IECloudRequest;
import com.chinamobile.cmss.sdk.nlp.request.nlp.NlpRequestFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author CMSS
 */
public class NLPDemo {
    public static String user_ak;
    private static String user_sk;
    private static ECloudDefaultClient client;

    static {

        user_ak = "8c674b40ed9f4dc892ba8530a2fb7a44";
        user_sk = "cf2db2008b9b4b1dbe93428a78796343";

        Credential credential = new Credential(user_ak, user_sk);
        client = new ECloudDefaultClient(credential, Region.POOL_SZ);
    }

//    public static void main(String[] args) {
//        //命名实体
////        testNlpEntity();
//        //情绪对话
////        testEmotion();
//        //行业分类
////        testIndustry();
//        //文本信息抽取
////        testNlpConvertFile();
//        //微博情感分析
////        testWb();
//        //新闻情感分析
////        testNews();
//        // 分词
////        testSegmentation();
//        // 关键词提取
////        testNlpKeyWords();
//        //论坛情感分析
////        testForum();
//
//        //低质量内容过滤
////        testFilter();
//        //地域识别
////        testLocation();
//
//        //负面情感分析
////        testNegative();
//
//        //观点评论过滤
////        testViewPoint();
//
//        //招投标网页信息抽取
////        testAnnouncement();
//
//        //谣言识别
////        testRumor();
//
//        //主题相关性
////        testThemeMatcher();
//
//        //文本相似度
////        testTextSimilarity();
//        //转发来源抽取
////        testTransmit();
//
//        //文档摘要抽取
//        testSummary();
//
//        //微博情绪识别
//        testWbEmotion();
//
//        //评论标签抽取
//        testCommentMining();
//    }

    public static void testCommentMining() {

        JSONArray textList = new JSONArray();
        textList.add("今天天气不错，很舒服");
        textList.add("今天天气很差，感觉很不舒服");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("textList", textList);
        jsonObject.put("textId", "test_text_id");

        JSONArray jsonArray = new JSONArray();
        jsonArray.add(jsonObject);
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("items", jsonArray);
        IECloudRequest commentMiningRequest = NlpRequestFactory.getnlpRequest("/api/nlp/v1/comment-mining", jsonObject1);
        try {
            JSONObject response = (JSONObject) client.call(commentMiningRequest);
            System.out.println(response.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void testWbEmotion() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("text", "今天天气很好，很舒服");
        jsonObject.put("textId", "test_text_id");
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(jsonObject);
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("items", jsonArray);
        IECloudRequest wbEmotionRequest = NlpRequestFactory.getnlpRequest("/api/nlp/v1/wbemotion", jsonObject1);
        try {
            JSONObject response = (JSONObject) client.call(wbEmotionRequest);
            System.out.println(response.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void testSummary() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("text", "北京是中国的首都城市");
        jsonObject.put("textId", "test_text_id");
        jsonObject.put("count",1);
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(jsonObject);
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("items", jsonArray);
        IECloudRequest summaryRequest = NlpRequestFactory.getnlpRequest("/api/nlp/v1/summary", jsonObject1);
        try {
            JSONObject response = (JSONObject) client.call(summaryRequest);
            System.out.println(response.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void testTransmit(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("text", "甘肃张掖网讯（张掖日报融媒体记者 杨静文）10月26日，市人大常委会党组书记王海峰在甘州区检查督导疫情防控工作");
        jsonObject.put("textId", "test_text_id");
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(jsonObject);
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("items", jsonArray);
        IECloudRequest transmitRequest = NlpRequestFactory.getnlpRequest("/api/nlp/v1/transmit", jsonObject1);
        try {
            JSONObject response = (JSONObject) client.call(transmitRequest);
            System.out.println(response.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void testRumor(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("text", "北京是中国的首都");
        jsonObject.put("textId", "test_text_id");
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(jsonObject);
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("items", jsonArray);
        IECloudRequest rumorRequest = NlpRequestFactory.getnlpRequest("/api/nlp/v1/rumor", jsonObject1);
        try {
            JSONObject response = (JSONObject) client.call(rumorRequest);
            System.out.println(response.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void testThemeMatcher(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("textId", "11");
        jsonObject.put("title", "北京是中国的首都");
        jsonObject.put("content", "北京北京");
        jsonObject.put("keyString", "北京北京");
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(jsonObject);
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("items", jsonArray);
        IECloudRequest themeMatcherRequest = NlpRequestFactory.getnlpRequest("/api/nlp/v1/theme-matcher", jsonObject1);
        try {
            JSONObject response = (JSONObject) client.call(themeMatcherRequest);
            System.out.println(response.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void testTextSimilarity(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("textId", "test_text_id");
        jsonObject.put("source", "北京是中国的首都");
        jsonObject.put("target", "北京是中国的首都");
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(jsonObject);
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("items", jsonArray);
        IECloudRequest textSimilarityRequest = NlpRequestFactory.getnlpRequest("/api/nlp/v1/text-similarity", jsonObject1);
        try {
            JSONObject response = (JSONObject) client.call(textSimilarityRequest);
            System.out.println(response.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void testAnnouncement(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("textId", "1");

        jsonObject.put("htmlText", "<div class=table><h5>公告概要：</h5><table width=600 border=0 cellspacing=1 bgcolor=bfbfbf style=text-align:left;><tr><td colspan=4><b>公告信息：</b></td></tr><tr><td class=title width=128>采购项目名称</td><td colspan=3 width=430>妇科医疗设备采购</td></tr><tr><td class=title>品目</td><td colspan=3><p></p></td></tr><tr><td class=title>采购单位</td><td colspan=3>广西中医药大学第一附属医院</td></tr><tr><td class=title>行政区域</td><td width=168>广西壮族自治区</td><td class=title width=128>公告时间</td><td width=168>2019年01月04日  17:55</td></tr><tr><td class=title>本项目招标公告日期</td><td width=168>2018年12月26日</td><td class=title width=128>成交日期</td><td width=168>2019年01月04日</td></tr><tr><td class=title>谈判小组、询价小组成员、磋商小组成员名单及单一来源采购人员名单</td><td colspan=3>黄花新、何小苏、黄巍（采购人代表）</td></tr><tr><td class=title>总成交金额</td><td colspan=3>￥56.377800 万元（人民币）</td></tr><tr><td colspan=4><b>联系人及联系方式：</b></td></tr><tr><td class=title>项目联系人</td><td colspan=3>详见公告内文</td></tr><tr><td class=title>项目联系电话</td><td colspan=3>详见公告内文</td></tr><tr><td class=title width=128>采购单位</td><td width=430 colspan=3>广西中医药大学第一附属医院</td></tr><tr><td class=title>采购单位地址</td><td colspan=3>广西南宁市东葛路89-9号</td></tr><tr><td class=title>采购单位联系方式</td><td colspan=3>许工；联系电话：0771-5602026</td></tr><tr><td class=title>代理机构名称</td><td colspan=3>广西科联招标中心</td></tr><tr><td class=title>代理机构地址</td><td colspan=3>详见公告内文</td></tr><tr><td class=title>代理机构联系方式</td><td colspan=3>详见公告内文</td></tr><tr><td colspan=4><b>附件：</b></td></tr><tr><td class=title>附件1</td><td colspan=3 width=430><a class=bizDownload href= id=D35EA9BF2FD5F5EF3D0CFAA111B93B title=点击下载>（J1-21797(重)）成交公告附件.rar</a></td></tr></table></div><div class=vF_detail_content>                <div style=line-height: 14pt><span>   </span>广西科联招标中心受广西中医药大学第一附属医院委托，根据《中华人民共和国政府采购法》等有关规定，于2019年<span>01月03日</span>就妇科医疗设备采购采用竞争性谈判进行采购，现就本次谈判的成交结果公告如下：</div><div style=line-height: 14pt><span>   <b> </b></span><b>一、采购项目名称及编号：</b>妇科医疗设备采购（<span>GXZC2018-J1-21797-KLZB（重））</span> <b><span><span>                                                             </span></span></b></div><div style=line-height: 14pt; text-indent: 23.25pt><b>二、采购项目简要说明：</b>A分标：热电复合治疗仪<span>4台；B分标：空气波压力循环治疗仪1台, 臭氧妇科治疗仪2台, 宫腔镜镜头3个, 宫腔镜软性异物钳3把, 宫腔镜软性剪刀3把，冲洗床1张等。合同履行日期：详见附件。</span></div><div style=line-height: 14pt; text-indent: 24pt><b>三、公告媒体及日期：</b>本项目于2018年<span>12月26日</span>在中国政府采购网（<span>www.ccgp.gov.cn）、广西壮族自治区政府采购网（www.gxzfcg.gov.cn）。发布竞争性谈判公告。</span></div><div style=line-height: 14pt; text-indent: 24pt><b>四、谈判日期</b>：2019年<span>01月03日</span></div><div style=line-height: 14pt><span>         </span>评审地点：广西南宁大学东路<span>170号广西科联招标中心二楼会议室。<span><span>     </span></span></span></div><div style=line-height: 14pt; text-indent: 47.25pt>谈判小组成员名单：黄花新、何小苏、黄巍（采购人代表）</div><div style=line-height: 14pt; text-indent: 20.65pt><b>五、成交信息：</b></div><div style=line-height: 14pt; text-indent: 20.65pt><b>A</b><b>分标：</b></div><div style=line-height: 14pt><span>    1.</span>成交供应商名称：<span style=color: black>南宁市帅立商贸有限公司</span></div><div style=line-height: 14pt><span>    2.</span>成交供应商地址：南宁市青秀区新民路<span>4号华星时代广场名仕阁16层1601、1603号。 </span></div><div style=line-height: 14pt; text-indent: 20.25pt>3.成交金额：<span>人民币</span>叁拾捌万玖仟陆佰元整（<span>¥389600.00）</span></div><div style=line-height: 14pt; text-indent: 21pt>4.主要成交标的的名称、规格型号、数量、单价、服务要求（详见附件）</div><div style=line-height: 14pt; text-indent: 21pt> </div><div style=line-height: 14pt; text-indent: 21.1pt><b>B</b><b>分标：</b></div><div style=line-height: 14pt; text-indent: 21pt>1.成交供应商名称：<span style=color: black>广西智竹商贸有限公司</span></div><div style=line-height: 14pt><span>    2.</span>成交供应商地址：南宁市江南区星光大道<span>17号国际经贸大厦22楼B座。 </span></div><div style=line-height: 14pt; text-indent: 20.25pt>3.成交金额：<span>人民币</span>壹拾柒万肆仟壹佰柒拾捌元整（<span>¥174178.00）</span></div><div style=line-height: 14pt; text-indent: 21pt>4.主要成交标的的名称、规格型号、数量、单价、服务要求（详见附件）</div><div style=line-height: 14pt; text-indent: 21pt> </div><div style=line-height: 14pt; text-indent: 21.1pt><b>六、代理服务费：</b></div><div style=line-height: 15pt; text-indent: 21.1pt><b>A</b><b>分标：</b>按国家发展计划委员会《招标代理服务费管理暂行办法》（计价格<span>[2002]1980号）服务类收费标准向成交人收取:人民币</span>伍仟捌佰肆拾肆元整（<span>¥5844.00）</span></div><div style=line-height: 14pt; text-indent: 21.1pt><b> </b></div><div style=line-height: 15pt; text-indent: 21.1pt><b>B</b><b>分标：</b>按国家发展计划委员会《招标代理服务费管理暂行办法》（计价格<span>[2002]1980号）服务类收费标准向成交人收取:人民币</span>贰仟陆佰壹拾贰元陆角柒分（<span>¥2612.67）</span></div><div style=line-height: 14pt; text-indent: 21.1pt><b> </b></div><div style=line-height: 14pt><span>   <b> </b></span><b>七、联系事项：</b></div><div style=line-height: 14pt>1、采购单位：广西中医药大学第一附属医院</div><div style=line-height: 14pt; text-indent: 15.75pt>联系人：许工；联系电话：<span>0771-5602026</span></div><div style=line-height: 14pt; text-indent: 15.75pt>地址：广西南宁市东葛路<span>89-9号</span></div><div style=line-height: 14pt>2、采购代理机构：广西科联招标中心</div><div style=line-height: 14pt; text-indent: 15.75pt>项目咨询电话：<span>0771-2273830<span>           </span>　联系人：钟悦瑰</span></div><div style=line-height: 14pt; text-indent: 15.75pt>保证金退付联系电话：<span>0771-2203986<span>       </span>联系人：小黄</span></div><div style=line-height: 14pt; text-indent: 15.75pt>邮<span><span>    </span>编：530007</span></div><div style=line-height: 14pt; text-indent: 21pt>地<span><span>    </span>址：南宁市大学东路170号</span></div><div style=line-height: 14pt>3、政府采购监督管理部门：广西财政厅政府采购监督管理处，联系电话：<span>0771-5331544，地址：桃源路69号广西财政大厦</span></div><div style=line-height: 14pt><span>    </span><b>八、成交结果公告期限</b>：自成交结果公告发布之日起一个工作日。</div><div style=line-height: 14pt><span>    </span>供应商认为成交结果使自己的权益受到损害的，可以在成交结果公告期限届满之日起七个工作日内以书面形式向采购人或采购代理机构提出质疑，逾期将不再受理。</div><div style=line-height: 14pt; text-indent: 23.25pt>附件：<span>1、采购文件</span></div><div style=line-height: 14pt; text-indent: 23.25pt><span>      2</span>、主要成交标的的名称、规格型号、数量、单价、服务要求</div><div style=line-height: 14pt><span>                                                </span></div><div style=text-align: right; line-height: 14pt align=right>广西科联招标中心</div><div style=text-align: right; line-height: 14pt align=right><span>                              <span style=color: red>        </span></span>2019年<span>01月04日</span></div><div style=line-height: 14pt> </div>            </div>");
//        jsonObject.put("htmlText", "招标人：中国移动");

        JSONArray jsonArray = new JSONArray();
        jsonArray.add(jsonObject);

        JSONObject requestParam = new JSONObject();
        requestParam.put("items", jsonArray);

        IECloudRequest announcementRequest = NlpRequestFactory.getnlpRequest("/api/nlp/v1/announcement-ie", requestParam);
        try {
            JSONObject response = (JSONObject) client.call(announcementRequest);
            System.out.println(response.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void testNegative(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("textId", "1");
        jsonObject.put("title", "心情");
        jsonObject.put("content", "今天心情很差");

        JSONObject jsonObject2 = new JSONObject();
        jsonObject2.put("textId", "2");
        jsonObject2.put("title", "心情");
        jsonObject2.put("content", "今天心情很好");
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(jsonObject);
        jsonArray.add(jsonObject2);
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("items", jsonArray);
        IECloudRequest formRequest = NlpRequestFactory.getnlpRequest("/api/nlp/v1/sentiment/negative", jsonObject1);
        try {
            JSONObject response = (JSONObject) client.call(formRequest);
            System.out.println(response.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void testViewPoint(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("text", "这个手机真的很好用，推荐大家购买");
        jsonObject.put("textId", "test_text_id");
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(jsonObject);
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("items", jsonArray);
        IECloudRequest formRequest = NlpRequestFactory.getnlpRequest("/api/nlp/v1/viewpoint", jsonObject1);
        try {
            JSONObject response = (JSONObject) client.call(formRequest);
            System.out.println(response.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 文本信息抽取
     */
    public static void testNlpConvertFile() {
        File file = new File("./extract.docx");
        IECloudRequest formRequest = NlpRequestFactory.getnlpRequest("/api/nlp/v1/convertdoc", file);
        try {
            JSONObject response = (JSONObject) client.call(formRequest);
            System.out.println(response.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 命名实体
     */
    public static void testNlpEntity() {
        JSONObject item = new JSONObject();
        item.put("text", "北京欢迎你");
        item.put("textId", "123123");
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(item);
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("items", jsonArray);
        IECloudRequest formRequest = NlpRequestFactory.getnlpRequest("/api/nlp/v1/entity", jsonObject1);
        try {
            JSONObject response = (JSONObject) client.call(formRequest);
            System.out.println(response.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 关键词搜索
     */
    public static void testNlpKeyWords() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("text", "上海欢迎你");
        jsonObject.put("textId", "test_text_id");
        jsonObject.put("initial","words");
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(jsonObject);
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("items", jsonArray);
        IECloudRequest formRequest = NlpRequestFactory.getnlpRequest("/api/nlp/v1/keywords", jsonObject1);
        try {
            JSONObject response = (JSONObject) client.call(formRequest);
            System.out.println(response.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 情绪对话
     */
    public static void testEmotion() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("text", "你很好");
        jsonObject.put("textId", "1");
        JSONArray jsonArray = new JSONArray();

        JSONObject jsonObject2 = new JSONObject();
        jsonObject2.put("text", "你杜绝浪费手机端法拉盛很好");
        jsonObject2.put("textId", "2");
        jsonArray.add(jsonObject);
        jsonArray.add(jsonObject2);
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("items", jsonArray);
        IECloudRequest formRequest = NlpRequestFactory.getnlpRequest("/api/nlp/v1/emotion", jsonObject1);
        try {
            JSONObject response = (JSONObject) client.call(formRequest);
            System.out.println(response.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 分词
     */
    public static void testSegmentation() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("text", "紫陌红尘，蓦然回首。多少的春花秋月;多少的逝水沉香;多少的海誓山盟，如沿途的风景花开花谢。人世间的情缘触痛了多少无言的感慨，情深缘浅的风吹散了多少相聚离散。花开是有情，花落是无意。来者是萍水相逢，去者是江湖相忘。缘起时，我在人群中看到你。缘灭时，你消失在人群中。");
        jsonObject.put("textId", "1");
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(jsonObject);
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("items", jsonArray);
        IECloudRequest formRequest = NlpRequestFactory.getnlpRequest("/api/nlp/v1/segmentation", jsonObject1);
        try {
            JSONObject response = (JSONObject) client.call(formRequest);
            System.out.println(response.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 行业分类
     */
    public static JSONObject testIndustry(String newstitle,String newscontent) {//        修改接口
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("textId", "1");
        jsonObject.put("title", newstitle);
        jsonObject.put("content", newscontent);

//        JSONObject jsonObject2 = new JSONObject();
//        jsonObject2.put("textId", "2");
//        jsonObject2.put("title", "文明测试员");
//        jsonObject2.put("content", "管道工程序猿");
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(jsonObject);
//        jsonArray.add(jsonObject2);
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("items", jsonArray);
        IECloudRequest formRequest = NlpRequestFactory.getnlpRequest("/api/nlp/v1/industry", jsonObject1);

//        修改接口
        JSONObject res = new JSONObject();
        try {
            JSONObject response = (JSONObject) client.call(formRequest);
            res = response;//        修改接口
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;//        修改接口
    }

    /**
     * 微博情感分析
     */
    public static void testWb() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("textId", "1");
        jsonObject.put("text", "今天天气不错，阳光晴朗，我因此也很高兴");
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(jsonObject);
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("items", jsonArray);
        IECloudRequest formRequest = NlpRequestFactory.getnlpRequest("/api/nlp/v1/sentiment/wb", jsonObject1);
        try {
            JSONObject response = (JSONObject) client.call(formRequest);
            System.out.println(response.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 新闻情感分析
     */
    public static void testNews() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("textId", "1");
        jsonObject.put("title", "文明测试员");
        jsonObject.put("text", "管道工程序猿");
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(jsonObject);
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("items", jsonArray);
        IECloudRequest formRequest = NlpRequestFactory.getnlpRequest("/api/nlp/v1/sentiment/news", jsonObject1);
        try {
            JSONObject response = (JSONObject) client.call(formRequest);
            System.out.println(response.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void testForum() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("textId", "1");
        jsonObject.put("title", "文明测试员");
        jsonObject.put("text", "管道工程序猿");
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(jsonObject);
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("items", jsonArray);
        IECloudRequest formRequest = NlpRequestFactory.getnlpRequest("/api/nlp/v1/sentiment/forum", jsonObject1);
        try {
            JSONObject response = (JSONObject) client.call(formRequest);
            System.out.println(response.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void testFilter(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("textId", "12");
        jsonObject.put("title", "北京是首都");
        jsonObject.put("content", "北京是中国的首都");
        jsonObject.put("type","weibo");
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(jsonObject);
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("items", jsonArray);
        IECloudRequest filterRequest = NlpRequestFactory.getnlpRequest("/api/nlp/v1/filter", jsonObject1);
        try {
            JSONObject response = (JSONObject) client.call(filterRequest);
            System.out.println(response.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试地域识别
     */
    public static void testLocation(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("textId", "12");
        jsonObject.put("title", "北京是首都");
        jsonObject.put("content", "北京是中国的首都，清华大学是北京的著名大学");
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(jsonObject);
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("items", jsonArray);
        IECloudRequest locationRequest = NlpRequestFactory.getnlpRequest("/api/nlp/v1/location", jsonObject1);
        try {
            JSONObject response = (JSONObject) client.call(locationRequest);
            System.out.println(response.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}