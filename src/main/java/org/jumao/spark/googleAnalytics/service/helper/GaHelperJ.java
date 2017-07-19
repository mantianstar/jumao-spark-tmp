package org.jumao.spark.googleAnalytics.service.helper;

import com.google.api.services.analyticsreporting.v4.model.Cohort;
import com.google.api.services.analyticsreporting.v4.model.CohortGroup;
import com.google.api.services.analyticsreporting.v4.model.DateRange;
import com.google.api.services.analyticsreporting.v4.model.Dimension;
import com.google.api.services.analyticsreporting.v4.model.Metric;
import com.google.api.services.analyticsreporting.v4.model.ReportRequest;
import org.jumao.spark.googleAnalytics.constants.Key;
import org.jumao.spark.googleAnalytics.utils.DateUtils;
import org.jumao.spark.googleAnalytics.utils.PlatformUtil;

import java.util.ArrayList;
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
                .setName("ga:hour");//ga:day ga:hour  ga:browser

        // Create the Metrics object.
        Metric metric1 = new Metric()
                .setExpression("ga:pageviews")
                .setAlias(Key.PV());

        Metric metric2 = new Metric()
                .setExpression("ga:uniquePageviews")
                .setAlias(Key.UV());

        Metric metric3 = new Metric()
                .setExpression("ga:bounceRate")
                .setAlias(Key.BOUNCE_RATE());

        Metric metric4 = new Metric()
                .setExpression("ga:avgSessionDuration")
                .setAlias(Key.AVG_SESSION_DUR());

//        Metric metric5 = new Metric()
//                .setExpression("ga:country")
//                .setAlias(Key.COUNTRY());

        Metric metric6 = new Metric()
                .setExpression("ga:users")
                .setAlias(Key.USERS());

        Metric metric7 = new Metric()
                .setExpression("ga:newUsers")
                .setAlias(Key.NEW_USERS());


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
                .setName("ga:cohort");//ga:cohort  ga:cohortNthDay

        Dimension dimension2 = new Dimension()
                .setName("ga:cohortNthDay");

        // Create the Metrics object.
        Metric metric1 = new Metric()
                .setExpression("ga:cohortActiveUsers");//cohortActiveUsers cohortTotalUsers cohortNewVisitor?
//                .setAlias(Key.COHORT_RETENTION_RATE());

        Metric metric2 = new Metric()
                .setExpression("ga:cohortRetentionRate");

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
                .setName("ga:date");

        Dimension dimension2 = new Dimension()
                .setName("ga:searchKeyword");//


        // Create the Metrics object.
        Metric metric1 = new Metric()
                .setExpression("ga:searchResultViews");//
//                .setAlias(Key.COHORT_RETENTION_RATE());

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
//        Dimension dimension1 = new Dimension()
//                .setName("ga:date");

        Dimension dimension2 = new Dimension()
                .setName("ga:country");//


        Metric metric1 = new Metric()
                .setExpression("ga:pageviews")
                .setAlias(Key.PV());

        Metric metric2 = new Metric()
                .setExpression("ga:uniquePageviews")
                .setAlias(Key.UV());

        DateRange dateRange = new DateRange().setStartDate("2017-07-19").setEndDate("2017-07-19");

        String viewId = PlatformUtil.get(platformId);
        ReportRequest request = new ReportRequest()
                .setViewId(viewId)
                .setDateRanges(Arrays.asList(dateRange))
                .setDimensions(Arrays.asList(dimension2))//
                .setMetrics(Arrays.asList(
                        metric1, metric2
                ));

        return request;
    }


}
