package org.jumao.google.analytics.entity;

public class HbasePo {

    private String date = "";
    private String targetHour = "";
    private String platformId = "";
    private String pv = "0";
    private String uv = "0";

    public String getTargetHour() {
        return targetHour;
    }

    public void setTargetHour(String targetHour) {
        this.targetHour = targetHour;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPlatformId() {
        return platformId;
    }

    public void setPlatformId(String platformId) {
        this.platformId = platformId;
    }

    public String getPv() {
        return pv;
    }

    public void setPv(String pv) {
        this.pv = pv;
    }

    public String getUv() {
        return uv;
    }

    public void setUv(String uv) {
        this.uv = uv;
    }


    @Override
    public String toString() {
        return "HbasePo{" +
                "date='" + date + '\'' +
                ", targetHour='" + targetHour + '\'' +
                ", platformId='" + platformId + '\'' +
                ", pv='" + pv + '\'' +
                ", uv='" + uv + '\'' +
                '}';
    }
}
