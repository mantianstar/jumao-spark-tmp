package org.jumao.spark.googleAnalytics.utils.enums;

/**
 */
enum GaFields {

    BOUNCE_RATE("bounceRate", "bounce_rate"), AVG_SESSION_DURATION("avgSessionDuration", "avg_session_dur"),
    NEW_USERS("newUsers", "new_users"), LANDING_PAGE_PATH("landingPagePath", "landing_page_path");


    private String orgin;
    private String forMysql;


    GaFields(String orgin, String forMysql) {
        this.orgin = orgin;
        this.forMysql = forMysql;
    }

    public String getOrgin() {
        return orgin;
    }

    public void setOrgin(String orgin) {
        this.orgin = orgin;
    }

    public String getForMysql() {
        return forMysql;
    }

    public void setForMysql(String forMysql) {
        this.forMysql = forMysql;
    }
}
