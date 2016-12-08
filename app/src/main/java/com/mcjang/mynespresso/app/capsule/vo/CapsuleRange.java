package com.mcjang.mynespresso.app.capsule.vo;

import java.io.Serializable;

/**
 * Created by mcjan on 2016-11-30.
 */

public class CapsuleRange implements Serializable {
    private String rangeName;
    private String rangeCode;
    private String rangePageLabel;

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

    public String getRangePageLabel() {
        return rangePageLabel;
    }

    public void setRangePageLabel(String rangePageLabel) {
        this.rangePageLabel = rangePageLabel;
    }
}
