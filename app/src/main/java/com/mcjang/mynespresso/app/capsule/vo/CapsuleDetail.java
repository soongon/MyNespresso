package com.mcjang.mynespresso.app.capsule.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mcjan on 2016-11-30.
 */

public class CapsuleDetail implements Serializable {

    private String name;
    private String code;

    //컵사이즈(urlDetail)
    private List<String> cupSize;

    //강도(urlDetail)
    private String intensity;

    //원숙함과 부드러움 같은 제목(urlDetail)
    private String detailSubject;

    //설명(urlDetail)
    private String detailDescription;

    //향(urlDetail)
    private String profile;

    //원산지설명
    private String originDetail;

    //로스팅
    private String roasting;

    //커피아로마 프로파일
    private String coffeAromaProfile;

    //원산지명
    private String origin;

    //큰 이미지(urlQuickView)
    private String bigImage;

    private String fileName;

    private String reviewUrl;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBigImage() {
        return bigImage;
    }

    public void setBigImage(String bigImage) {
        this.bigImage = bigImage;
    }

    public String getCoffeAromaProfile() {
        return coffeAromaProfile;
    }

    public void setCoffeAromaProfile(String coffeAromaProfile) {
        this.coffeAromaProfile = coffeAromaProfile;
    }

    public List<String> getCupSize() {
        return cupSize;
    }

    public void setCupSize(List<String> cupSize) {
        this.cupSize = cupSize;
    }

    public void addCupSize(String cupSize) {
        if(this.cupSize == null) {
            this.cupSize = new ArrayList<String>();
        }
        this.cupSize.add(cupSize);
    }

    public String getDetailDescription() {
        return detailDescription;
    }

    public void setDetailDescription(String detailDescription) {
        this.detailDescription = detailDescription;
    }

    public String getDetailSubject() {
        return detailSubject;
    }

    public void setDetailSubject(String detailSubject) {
        this.detailSubject = detailSubject;
    }

    public String getIntensity() {
        return intensity;
    }

    public void setIntensity(String intensity) {
        this.intensity = intensity;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getOriginDetail() {
        return originDetail;
    }

    public void setOriginDetail(String originDetail) {
        this.originDetail = originDetail;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getRoasting() {
        return roasting;
    }

    public void setRoasting(String roasting) {
        this.roasting = roasting;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getReviewUrl() {
        return reviewUrl;
    }

    public void setReviewUrl(String reviewUrl) {
        this.reviewUrl = reviewUrl;
    }
}
