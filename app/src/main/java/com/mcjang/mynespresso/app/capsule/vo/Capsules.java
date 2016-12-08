package com.mcjang.mynespresso.app.capsule.vo;

import java.util.List;

/**
 * Created by mcjan on 2016-11-30.
 */

public class Capsules {
    private List<CapsuleList> capsuleRange;
    private int numberOfCapsules;
    private boolean success;

    public List<CapsuleList> getCapsuleRange() {
        return capsuleRange;
    }

    public void setCapsuleRange(List<CapsuleList> capsuleRange) {
        this.capsuleRange = capsuleRange;
    }

    public int getNumberOfCapsules() {
        return numberOfCapsules;
    }

    public void setNumberOfCapsules(int numberOfCapsules) {
        this.numberOfCapsules = numberOfCapsules;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
