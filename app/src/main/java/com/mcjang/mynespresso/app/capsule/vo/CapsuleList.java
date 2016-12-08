package com.mcjang.mynespresso.app.capsule.vo;

import java.util.List;

/**
 * Created by mcjan on 2016-11-30.
 */

public class CapsuleList {

    private List<Capsule> capsuleList;
    private String rangeName;
    private String rangeCode;

    public List<Capsule> getCapsuleList() {
        return capsuleList;
    }

    public void setCapsuleList(List<Capsule> capsuleList) {
        this.capsuleList = capsuleList;
    }

    public String getRangeName() {
        return rangeName;
    }

    public void setRangeName(String rangeName) {
        this.rangeName = rangeName;
    }

    public String getRangeCode() {
        return rangeCode;
    }

    public void setRangeCode(String rangeCode) {
        this.rangeCode = rangeCode;
    }
}
