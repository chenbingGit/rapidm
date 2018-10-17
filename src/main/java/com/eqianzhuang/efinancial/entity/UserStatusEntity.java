package com.eqianzhuang.efinancial.entity;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;

/**
 String
 * @author chenbing
 */
public class  UserStatusEntity implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 7016022756424178657L;

    public UserStatusEntity(){
        this.firstSessionDate = System.currentTimeMillis();
    }

    /**
     * 用户开始时间
     *
     */
    private long firstSessionDate;

    /**
     * 用户最后交互时间
     *
     */
    private long lastSessionDate = 0L;


    /**
     * 已经推荐过产品的列表
     *
     */
    private HashSet<String> productSet = new HashSet<>();


    /**
     * 已经推荐过公司的列表
     *
     */
    private HashSet<String> companySet = new HashSet<>();


    /**默认不执行
     * 20分钟任务
     * true： 完成  ；false：等待执行
     */
    private Boolean notifyOfMin20 = true;

    /**
     * 半小时任务
     * true： 完成  ；false：等待执行
     */
    private boolean notifyOfMin30 = false;


    /**
     * 12小时任务
     * true： 完成  ；false：等待执行
     */
    private boolean notifyOf12Hour = false;


    /**
     * 用户问题状态对应问题处理分支(默认无状态)
     */
    private int status = 999;

    /**
     * 是否重复问问题。
     */
    private boolean repeat = false;


    /**
     * 主流程重复问问题。
     */
    private boolean repeatMain = false;


    /**
     * 媒体编号
     */
    private int mediaNo = 0;

    /**
     * 推荐产品组记录
     */
    private String groupId = "130001";



    /**
     * 用户资质
     *
     */
    private HashMap<String,String> qualificationMap = new HashMap<>();

    /**
     * 用户需要问的动态资质
     */
    private HashSet<String> questionsSet = new HashSet<>();

    /**
     *是否获客
     */
    private boolean insertUserQualification = true ;

    /**
     * 是否点击菜单
     */
    private boolean clickMenu = false;

    /**
     * 是否回复过AI
     */
    private boolean replyAI = false;


    /**
     * 上一个问题
     */
    private JSONObject previousQuestion = new JSONObject();

    public static long getSerialVersionUID() {

        return serialVersionUID;
    }

    public long getLastSessionDate() {

        return lastSessionDate;
    }

    public void setLastSessionDate(long lastSessionDate) {
        this.lastSessionDate = lastSessionDate;
    }


    public HashSet<String> getProductSet() {
        if (productSet == null) {
            return new HashSet<>() ;
        }
        return productSet;
    }

    public void setProductSet(HashSet<String> productSet) {
        this.productSet = productSet;
    }

    public boolean isInsertUserQualification() {
        return insertUserQualification;
    }

    public void setInsertUserQualification(boolean insertUserQualification) {
        this.insertUserQualification = insertUserQualification;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isRepeat() {
        return repeat;
    }

    public void setRepeat(boolean repeat) {
        this.repeat = repeat;
    }

    public boolean isRepeatMain() {
        return repeatMain;
    }

    public void setRepeatMain(boolean repeatMain) {
        this.repeatMain = repeatMain;
    }


    public HashMap<String, String> getQualificationMap() {
        if (qualificationMap == null) {
            return new HashMap<>();
        }
        return qualificationMap;
    }

    public void setQualificationMap(HashMap<String, String> qualificationMap) {
        this.qualificationMap = qualificationMap;
    }

    public HashSet<String> getQuestionsSet() {
        if (questionsSet == null) {
            return new HashSet<>();
        }
        return questionsSet;
    }

    public void setQuestionsSet(HashSet<String> questionsSet) {
        this.questionsSet = questionsSet;
    }

    public boolean isNotifyOfMin30() {
        return notifyOfMin30;
    }

    public void setNotifyOfMin30(boolean notifyOfMin30) {
        this.notifyOfMin30 = notifyOfMin30;
    }

    public long getFirstSessionDate() {
        return firstSessionDate;
    }

    public HashSet<String> getCompanySet() {
        if (companySet == null) {
            return new HashSet<>() ;
        }
        return companySet;
    }

    public void setCompanySet(HashSet<String> companySet) {
        this.companySet = companySet;
    }

    public boolean isClickMenu() {
        return clickMenu;
    }

    public void setClickMenu(boolean clickMenu) {
        this.clickMenu = clickMenu;
    }

    public boolean isReplyAI() {

        return replyAI;
    }

    public void setReplyAI(boolean replyAI) {
        this.replyAI = replyAI;
    }


    public boolean isNotifyOf12Hour() {
        return notifyOf12Hour;
    }

    public void setNotifyOf12Hour(boolean notifyOf12Hour) {
        this.notifyOf12Hour = notifyOf12Hour;
    }

    public boolean isNotifyOfMin20() {
        if (notifyOfMin20 == null){
            notifyOfMin20 = true;
        }
        return notifyOfMin20;
    }

    public void setNotifyOfMin20(boolean notifyOfMin20) {
        this.notifyOfMin20 = notifyOfMin20;
    }

    public int getMediaNo() {
        return mediaNo;
    }

    public void setMediaNo(int mediaNo) {
        this.mediaNo = mediaNo;
    }

    public String getGroupId() {
        if (groupId == null){
            return  "13001";
        }
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public JSONObject getPreviousQuestion() {
        if (previousQuestion == null){

            this.previousQuestion = new JSONObject();

        }
        return previousQuestion;
    }

    public void setPreviousQuestion(JSONObject previousQuestion) {
        this.previousQuestion = previousQuestion;
    }

}
