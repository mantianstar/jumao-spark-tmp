package org.jumao.google.analytics;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.google.api.services.analyticsreporting.v4.AnalyticsReportingScopes;
import com.google.api.services.analyticsreporting.v4.AnalyticsReporting;
import com.google.api.services.analyticsreporting.v4.model.ColumnHeader;
import com.google.api.services.analyticsreporting.v4.model.DateRange;
import com.google.api.services.analyticsreporting.v4.model.DateRangeValues;
import com.google.api.services.analyticsreporting.v4.model.Dimension;
import com.google.api.services.analyticsreporting.v4.model.GetReportsRequest;
import com.google.api.services.analyticsreporting.v4.model.GetReportsResponse;
import com.google.api.services.analyticsreporting.v4.model.Metric;
import com.google.api.services.analyticsreporting.v4.model.MetricHeaderEntry;
import com.google.api.services.analyticsreporting.v4.model.Report;
import com.google.api.services.analyticsreporting.v4.model.ReportRequest;
import com.google.api.services.analyticsreporting.v4.model.ReportRow;
import org.jumao.google.analytics.entity.TargetHourVo;
import org.jumao.google.analytics.utils.CalendarUtils;
import org.jumao.google.analytics.utils.DLOG;
import org.jumao.google.analytics.utils.DateUtils;


public class HelloAnalyticsReporting {

    private static final String APPLICATION_NAME = "Hello Analytics Reporting";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String KEY_FILE_LOCATION = "src/main/resources/MyProject-81155b910cde.p12";
    private static final String SERVICE_ACCOUNT_EMAIL = "id-646@mercurial-craft-170709.iam.gserviceaccount.com";
    private static final String VIEW_ID = "140502429";


    public static void main(String[] args) {
        try {
            AnalyticsReporting service = initializeAnalyticsReporting();

            TargetHourVo targetHourVo = new TargetHourVo();
            GetReportsResponse response = getReport(service, targetHourVo);
            printResponse(response, targetHourVo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Initializes an authorized Analytics Reporting service object.
     *
     * @return The analytics reporting service object.
     * @throws IOException
     * @throws GeneralSecurityException
     */
    private static AnalyticsReporting initializeAnalyticsReporting() throws GeneralSecurityException, IOException {

        HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        GoogleCredential credential = new GoogleCredential.Builder()
                .setTransport(httpTransport)
                .setJsonFactory(JSON_FACTORY)
                .setServiceAccountId(SERVICE_ACCOUNT_EMAIL)
                .setServiceAccountPrivateKeyFromP12File(new File(KEY_FILE_LOCATION))
                .setServiceAccountScopes(AnalyticsReportingScopes.all())
                .build();

        // Construct the Analytics Reporting service object.
        return new AnalyticsReporting.Builder(httpTransport, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME).build();
    }

    /**
     * Query the Analytics Reporting API V4.
     * Constructs a request for the sessions for the past seven days.
     * Returns the API response.
     *
     *
     * Is there a way to to additionally specify a hours?
     * No, the day starts at midnight of the view you are querying.
     * However you can query for the hour of the day as a dimension
     * (ga:hour for the hour of the day, or ga:dateHour for the date with the hour)
     * and use that to filter in your results for the timeframe you want.
     *
     * @param service
     * @return GetReportResponse
     * @throws IOException
     */
    private static GetReportsResponse getReport(AnalyticsReporting service, TargetHourVo targetHourVo) throws Exception {
        Calendar nowCal = CalendarUtils.getCalendarBy(new Date());
        int nowHour = nowCal.get(Calendar.HOUR_OF_DAY);
        String targetHour = null;

        // Create the DateRange object.
        DateRange dateRange = new DateRange();
        if (nowHour == 0) {
            targetHour = "23";
            String yesterday = getYesterday(nowCal);
            dateRange.setStartDate(yesterday);//7DaysAgo
            dateRange.setEndDate(yesterday);//today
        } else {
            targetHour = String.valueOf(nowHour - 1);
            dateRange.setStartDate("today");//7DaysAgo
            dateRange.setEndDate("today");//today
        }
        targetHourVo.setTargetHour(targetHour);

        //Create the Dimensions object.
        Dimension dimension1 = new Dimension()
                .setName("ga:hour");//ga:day ga:hour  ga:browser

        // Create the Metrics object.
        Metric metric1 = new Metric()
                .setExpression("ga:pageviews") //ga:pageviews  ga:uniquePageviews
                .setAlias("pv");

        Metric metric2 = new Metric()
                .setExpression("ga:uniquePageviews") //ga:pageviews  ga:uniquePageviews
                .setAlias("uv");


        // Create the ReportRequest object.
        ReportRequest request = new ReportRequest()
                .setViewId(VIEW_ID)
                .setDateRanges(Arrays.asList(dateRange))
                .setDimensions(Arrays.asList(dimension1))
                .setMetrics(Arrays.asList(metric1, metric2));

        ArrayList<ReportRequest> requests = new ArrayList<ReportRequest>();
        requests.add(request);

        // Create the GetReportsRequest object.
        GetReportsRequest getReport = new GetReportsRequest()
                .setReportRequests(requests);

        // Call the batchGet method.
        GetReportsResponse response = service.reports().batchGet(getReport).execute();

        // Return the response.
        return response;
    }

    private static String getYesterday(Calendar nowCal) throws Exception {
        nowCal.set(Calendar.DAY_OF_MONTH, nowCal.get(Calendar.DAY_OF_MONTH) - 1);
        return DateUtils.formatToNoHourF(nowCal.getTime());
    }

    /**
     * Parses and prints the Analytics Reporting API V4 response.
     *
     * @param response the Analytics Reporting API V4 response.
     */
    private static void printResponse(GetReportsResponse response, TargetHourVo targetHourVo) throws Exception {
        Report report = response.getReports().get(0);//batchGet 可以同时请求多个 view
        if (report == null) {
            throw new Exception("response.getReports().get(0) return null");
        }
        ColumnHeader header = report.getColumnHeader();
        List<String> dimensionHeaders = header.getDimensions();
        List<MetricHeaderEntry> metricHeaders = header.getMetricHeader().getMetricHeaderEntries();
        List<ReportRow> rows = report.getData().getRows();

        if (rows == null) {
            throw new Exception("report.getData().getRows() return null");
        }

        String targetHour = targetHourVo.getTargetHour();
        String targetVal = null;

        for (ReportRow row : rows) {
            List<String> dimensions = row.getDimensions();
            List<DateRangeValues> metrics = row.getMetrics();
            String thisHour = null;

            for (int i = 0; i < dimensionHeaders.size() && i < dimensions.size(); i++) {
                thisHour = dimensions.get(i);
                if (thisHour != null) {
                    break;
                }
            }

            if (thisHour == null) {
                break;
            }
            if (!targetHour.equals(thisHour)) {
                continue;
            }

            for (int j = 0; j < metrics.size(); j++) {
                DateRangeValues values = metrics.get(j);
                List<String> valList = values.getValues();
                for (int k = 0; k < valList.size() && k < metricHeaders.size(); k++) {
                    targetVal = valList.get(k);
                    if (targetVal != null) {
                        break;
                    }
                }
                if (targetVal != null) {
                    break;
                }
            }
        }

        if (targetVal == null) {
            targetVal = "0";
        }
        System.err.println(targetHour);
        System.err.println(targetVal);
    }

}
