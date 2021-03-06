package org.jumao.spark.googleAnalytics.service.helper;

import com.google.api.services.analyticsreporting.v4.model.Cohort;
import com.google.api.services.analyticsreporting.v4.model.CohortGroup;
import com.google.api.services.analyticsreporting.v4.model.DateRange;
import com.google.api.services.analyticsreporting.v4.model.Dimension;
import com.google.api.services.analyticsreporting.v4.model.Metric;
import com.google.api.services.analyticsreporting.v4.model.ReportRequest;
import org.jumao.spark.googleAnalytics.utils.DateUtils;
import org.jumao.spark.googleAnalytics.utils.PlatformUtil;
import org.jumao.spark.googleAnalytics.utils.constants.Key;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 */
public class GaHelperJ {


    protected static String getOneHourAgo(int nowHour) {
        int oneHourAgo = nowHour - 1;
        String str = String.valueOf(oneHourAgo);
        if (oneHourAgo < 10) {
            return "0" + str;
        } else {
            return str;
        }
    }

    /**
     *
     * @param nowCal
     * @return 2017-02-02
     */
    protected static String getYesterday(Calendar nowCal) throws Exception {
        nowCal.set(Calendar.DAY_OF_MONTH, nowCal.get(Calendar.DAY_OF_MONTH) - 1);
        return DateUtils.formatToNoHourF(nowCal.getTime());
    }


    protected static ReportRequest getBasicVisitReq(String platformId, DateRange dateRange) {
        //Create the Dimensions object.
        Dimension dimension1 = new Dimension()
                .setName(Key.GA_HOUR());//ga:day ga:hour  ga:browser

        // Create the Metrics object.
        Metric metric1 = new Metric()
                .setExpression(Key.GA_PAGEVIEWS());

        Metric metric2 = new Metric()
                .setExpression(Key.GA_UNIQUE_PAGEVIEWS());

        Metric metric3 = new Metric()
                .setExpression(Key.GA_BOUNCE_RATE());

        Metric metric4 = new Metric()
                .setExpression(Key.GA_AVG_SESSION_DURATION());

//        Metric metric5 = new Metric()
//                .setExpression("ga:country")

        Metric metric6 = new Metric()
                .setExpression(Key.GA_USERS());

        Metric metric7 = new Metric()
                .setExpression(Key.GA_NEW_USERS());


        String viewId = PlatformUtil.get(platformId);
        // Create the ReportRequest object.
        ReportRequest request = new ReportRequest()
                .setViewId(viewId)
                .setDateRanges(Arrays.asList(dateRange))
                .setDimensions(Arrays.asList(dimension1))
                .setMetrics(Arrays.asList(
                        metric1, metric2, metric3, metric4, metric6, metric7
                ));

        return request;
    }


    protected static ReportRequest getRetentionRateReq(String platformId) {
        Dimension dimension1 = new Dimension()
                .setName(Key.GA_COHORT());//ga:cohort  ga:cohortNthDay

        Dimension dimension2 = new Dimension()
                .setName(Key.GA_COHORT_NTH_DAY());

        // Create the Metrics object.
        Metric metric1 = new Metric()
                .setExpression(Key.GA_COHORT_ACTIVE_USERS());//cohortActiveUsers cohortTotalUsers cohortNewVisitor?
//                .setAlias(Key.COHORT_RETENTION_RATE());

        Metric metric2 = new Metric()
                .setExpression(Key.GA_COHORT_RETENTION_RATE());

//        Cohort cohort1 = new Cohort().setName("cohort1")
//                .setDateRange(new DateRange().setStartDate("2017-07-10").setEndDate("2017-07-10"));
        Cohort cohort2 = new Cohort().setName("cohort2")
                .setDateRange(new DateRange().setStartDate("2017-07-15").setEndDate("2017-07-15"));

        List<Cohort> cohorts = Arrays.asList(cohort2);
        CohortGroup cohortGroup = new CohortGroup().setCohorts(cohorts);

        String viewId = PlatformUtil.get(platformId);
        ReportRequest request = new ReportRequest()
                .setViewId(viewId)
                .setCohortGroup(cohortGroup)
                .setDimensions(Arrays.asList(dimension1, dimension2))//
                .setMetrics(Arrays.asList(
                        metric1, metric2
                ));

        return request;
    }


    protected static ReportRequest getSearchKeywordReq(String platformId) {
        Dimension dimension1 = new Dimension()
                .setName(Key.GA_DATE());

        Dimension dimension2 = new Dimension()
                .setName(Key.GA_SEARCH_KEYWORD());//


        // Create the Metrics object.
        Metric metric1 = new Metric()
                .setExpression(Key.GA_SEARCH_RESULT_VIEWS());//

        DateRange dateRange = new DateRange().setStartDate("2015-07-18").setEndDate("2017-07-18");

        String viewId = PlatformUtil.get(platformId);
        ReportRequest request = new ReportRequest()
                .setViewId(viewId)
                .setDateRanges(Arrays.asList(dateRange))
                .setDimensions(Arrays.asList(dimension1, dimension2))//
                .setMetrics(Arrays.asList(
                        metric1
                ));

        return request;
    }


    protected static ReportRequest getCountryReq(String platformId) {
        Dimension dimension1 = new Dimension()
                .setName(Key.GA_HOUR());

        Dimension dimension2 = new Dimension()
                .setName(Key.GA_COUNTRY());


        Metric metric1 = new Metric()
                .setExpression(Key.GA_PAGEVIEWS());

        Metric metric2 = new Metric()
                .setExpression(Key.GA_UNIQUE_PAGEVIEWS());

        Metric metric3 = new Metric()
                .setExpression(Key.GA_BOUNCE_RATE());

        DateRange dateRange = new DateRange().setStartDate("2017-07-19").setEndDate("2017-07-19");

        String viewId = PlatformUtil.get(platformId);
        ReportRequest request = new ReportRequest()
                .setViewId(viewId)
                .setDateRanges(Arrays.asList(dateRange))
                .setDimensions(Arrays.asList(dimension1, dimension2))//
                .setMetrics(Arrays.asList(
                        metric1, metric2, metric3
                ));

        return request;
    }


    /**
     * 用类似以下的 sql 可以查询三种访问来源的饼图和折线图
     * select sum(pv), type, strleft(create_time, 10) as ct from jmbi_ga_source_medium
     * where create_time between '2017-07-01' and '2017-07-20' group by type, ct;
     *
     * @param platformId
     * @return
     */
    protected static ReportRequest getSourceMediumReq(String platformId) {
        Dimension dimension1 = new Dimension()
                .setName(Key.GA_HOUR());

        Dimension dimension2 = new Dimension()
                .setName(Key.GA_SOURCE_MEDIUM());


        Metric metric1 = new Metric().setExpression(Key.GA_PAGEVIEWS());
        Metric metric2 = new Metric().setExpression(Key.GA_UNIQUE_PAGEVIEWS());
        Metric metric3 = new Metric().setExpression(Key.GA_BOUNCE_RATE());

        DateRange dateRange = new DateRange().setStartDate("2017-07-19").setEndDate("2017-07-19");

        String viewId = PlatformUtil.get(platformId);
        ReportRequest request = new ReportRequest()
                .setViewId(viewId)
                .setDateRanges(Arrays.asList(dateRange))
                .setDimensions(Arrays.asList(dimension1, dimension2))
                .setMetrics(Arrays.asList(
                        metric1, metric2, metric3
                ));

        return request;
    }


    protected static ReportRequest getEntrancesPageReq(String platformId) {
        Dimension dimension1 = new Dimension()
                .setName(Key.GA_HOUR());

        Dimension dimension2 = new Dimension()
                .setName(Key.GA_LANDING_PAGE_PATH());

        Metric metric1 = new Metric()
                .setExpression(Key.GA_PAGEVIEWS());

        DateRange dateRange = new DateRange().setStartDate("2017-07-19").setEndDate("2017-07-19");

        String viewId = PlatformUtil.get(platformId);
        ReportRequest request = new ReportRequest()
                .setViewId(viewId)
                .setDateRanges(Arrays.asList(dateRange))
                .setDimensions(Arrays.asList(dimension1, dimension2))
                .setMetrics(Arrays.asList(
                        metric1
                ));

        return request;
    }


    protected static ReportRequest getAllHostNameReq(String platformId) {
        Dimension dimension1 = new Dimension()
                .setName(Key.GA_HOUR());

        Dimension dimension2 = new Dimension()
                .setName(Key.GA_HOST_NAME());

        Metric metric1 = new Metric()
                .setExpression(Key.GA_SESSIONS());

        DateRange dateRange = new DateRange().setStartDate("2017-07-19").setEndDate("2017-07-19");

        String viewId = PlatformUtil.get(platformId);
        ReportRequest request = new ReportRequest()
                .setViewId(viewId)
                .setDateRanges(Arrays.asList(dateRange))
                .setDimensions(Arrays.asList(dimension1, dimension2))
                .setMetrics(Arrays.asList(
                        metric1
                ));

        return request;
    }


    protected static ReportRequest getBehaviorFlowReq(String platformId) {
//        Dimension dimension1 = new Dimension()
//                .setName(Key.GA_HOUR());

        Dimension dimension2 = new Dimension()
                .setName("ga:pagePathLevel1");

        Dimension dimension3 = new Dimension()
                .setName("ga:pagePathLevel2");

        Metric metric1 = new Metric()
                .setExpression(Key.GA_PAGEVIEWS());

        Metric metric2 = new Metric()
                .setExpression(Key.GA_BOUNCE_RATE());

        DateRange dateRange = new DateRange().setStartDate("2017-07-19").setEndDate("2017-07-19");

        String viewId = PlatformUtil.get(platformId);
        ReportRequest request = new ReportRequest()
                .setViewId(viewId)
                .setDateRanges(Arrays.asList(dateRange))
                .setDimensions(Arrays.asList(dimension2, dimension3))
                .setMetrics(Arrays.asList(
                        metric1, metric2
                ));

        return request;
    }

}
