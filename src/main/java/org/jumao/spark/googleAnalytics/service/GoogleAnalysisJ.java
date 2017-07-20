package org.jumao.spark.googleAnalytics.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.google.api.services.analyticsreporting.v4.AnalyticsReportingScopes;
import com.google.api.services.analyticsreporting.v4.AnalyticsReporting;
import com.google.api.services.analyticsreporting.v4.model.ColumnHeader;
import com.google.api.services.analyticsreporting.v4.model.DateRange;
import com.google.api.services.analyticsreporting.v4.model.DateRangeValues;
import com.google.api.services.analyticsreporting.v4.model.GetReportsRequest;
import com.google.api.services.analyticsreporting.v4.model.GetReportsResponse;
import com.google.api.services.analyticsreporting.v4.model.MetricHeaderEntry;
import com.google.api.services.analyticsreporting.v4.model.Report;
import com.google.api.services.analyticsreporting.v4.model.ReportRow;
import org.jumao.spark.googleAnalytics.constants.Key;
import org.jumao.spark.googleAnalytics.entity.HbasePo;
import org.jumao.spark.googleAnalytics.service.basic.GaBasicJ;
import org.jumao.spark.googleAnalytics.utils.CalendarUtils;
import org.jumao.spark.googleAnalytics.utils.DateUtils;
import org.jumao.spark.googleAnalytics.utils.PlatformUtil;
import org.jumao.spark.googleAnalytics.utils.SystemPropUtils;
import org.jumao.spark.googleAnalytics.utils.enums.GaReqEnums;


/**
 * 一次只能请求一个 viewId
 */
public class GoogleAnalysisJ extends GaBasicJ {


    public static HbasePo reqAndGetHbasePo(String platformId) throws Exception {
        AnalyticsReporting service = initializeAnalyticsReporting();

        HbasePo hbasePo = new HbasePo();
        hbasePo.setPlatformId(platformId);
//        GetReportsResponse response = getBasicVisitRep(platformId, service, hbasePo);
//        reqAndGet(response, hbasePo);

//        GetReportsResponse response = getResponse(platformId, service, GaReqEnums.RETENTION_RATE);
//        parseRetentionRateRep(response);

//        GetReportsResponse response = getResponse(platformId, service, GaReqEnums.SEARCH_KEY_WORD);
//        parseRetentionRateRep(response);

//        GetReportsResponse response = getResponse(platformId, service, GaReqEnums.COUNTRY);
//        parseRetentionRateRep(response);

        //过滤 (direct) / (none)来计算来源网站，同时
//        GetReportsResponse response = getResponse(platformId, service, GaReqEnums.SOURCE_MEDIUM);
//        parseRetentionRateRep(response);

//        GetReportsResponse response = getResponse(platformId, service, GaReqEnums.ENTRANCES_PAGE);
//        parseRetentionRateRep(response);

//        GetReportsResponse response = getResponse(platformId, service, GaReqEnums.ALL_HOST_NAME);
//        parseRetentionRateRep(response);

        GetReportsResponse response = getResponse(platformId, service, GaReqEnums.BEHAVIOR_FLOW);
        parseRetentionRateRep(response);

        return hbasePo;
    }



    public static void main(String[] args) throws Exception {
        SystemPropUtils.initPropFile("src/main/resources/spark-job-conf.properties");
        HbasePo hbasePo = reqAndGetHbasePo(PlatformUtil.Agriculture);
        System.err.println(hbasePo);
    }


}
