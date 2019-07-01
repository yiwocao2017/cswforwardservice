package com.cdkj.service.dto.req;

public class XN805158Req {
    // 用户编号(必填)
    private String userId;

    // 经度(必填)
    private String longitude;

    // 维度(必填)
    private String latitude;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
}
