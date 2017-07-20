package org.jumao.spark.googleAnalytics.service.basic;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.analyticsreporting.v4.AnalyticsReporting;
import com.google.api.services.analyticsreporting.v4.AnalyticsReportingScopes;
import com.google.api.services.analyticsreporting.v4.model.ColumnHeader;
import com.google.api.services.analyticsreporting.v4.model.DateRange;
import com.google.api.services.analyticsreporting.v4.model.DateRangeValues;
import com.google.api.services.analyticsreporting.v4.model.GetReportsRequest;
import com.google.api.services.analyticsreporting.v4.model.GetReportsResponse;
import com.google.api.services.analyticsreporting.v4.model.MetricHeaderEntry;
import com.google.api.services.analyticsreporting.v4.model.Report;
import com.google.api.services.analyticsreporting.v4.model.ReportRequest;
import com.google.api.services.analyticsreporting.v4.model.ReportRow;
import org.jumao.spark.googleAnalytics.constants.Key;
import org.jumao.spark.googleAnalytics.entity.HbasePo;
import org.jumao.spark.googleAnalytics.service.helper.GaHelperJ;
import org.jumao.spark.googleAnalytics.utils.CalendarUtils;
import org.jumao.spark.googleAnalytics.utils.enums.GaReqEnums;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 */
public class GaBasicJ extends GaHelperJ {


    private static final SimpleDateFormat HBASE_NO_HOUR_FORMAT = new SimpleDateFormat("yyyyMMdd");
    private static final String APPLICATION_NAME = "Jumore Analytics Reporting";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String SERVICE_ACCOUNT_EMAIL = "id-646@mercurial-craft-170709.iam.gserviceaccount.com";


    /**
     * Initializes an authorized Analytics Reporting service object.
     *
     * @return The analytics reporting service object.
     * @throws IOException
     * @throws GeneralSecurityException
     */
    protected static AnalyticsReporting initializeAnalyticsReporting() throws GeneralSecurityException, IOException {

        HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        GoogleCredential credential = new GoogleCredential.Builder()
                .setTransport(httpTransport)
                .setJsonFactory(JSON_FACTORY)
                .setServiceAccountId(SERVICE_ACCOUNT_EMAIL)
                .setServiceAccountPrivateKeyFromP12File(new File(Key.GA_KEY_FILE_LOCATION()))
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
    protected static GetReportsResponse getBasicVisitRep(String platformId, AnalyticsReporting service,
                                                       HbasePo hbasePo) throws Exception {
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
            targetHour = getOneHourAgo(nowHour);
            dateRange.setStartDate(Key.TODAY());//7DaysAgo
            dateRange.setEndDate(Key.TODAY());//today
        }
        hbasePo.setTargetHour(targetHour);
        hbasePo.setDate(HBASE_NO_HOUR_FORMAT.format(nowCal.getTime()) + targetHour);


        // Create the GetReportsRequest object.
        GetReportsRequest getReport = new GetReportsRequest()
                .setReportRequests(Arrays.asList(getBasicVisitReq(platformId, dateRange)));

        // Call the batchGet method.
        GetReportsResponse response = service.reports().batchGet(getReport).execute();

        // Return the response.
        return response;
    }


    protected static GetReportsResponse getResponse(String platformId, AnalyticsReporting service, GaReqEnums gaReqEnum) throws Exception {
        ReportRequest reportRequest = null;
        switch (gaReqEnum) {
            case RETENTION_RATE:
                reportRequest = getRetentionRateReq(platformId);
                break;
            case SEARCH_KEY_WORD:
                reportRequest = getSearchKeywordReq(platformId);
                break;
            case COUNTRY:
                reportRequest = getCountryReq(platformId);
                break;
            case SOURCE_MEDIUM:
                reportRequest = getSourceMediumReq(platformId);
                break;
            case ENTRANCES_PAGE:
                reportRequest = getEntrancesPageReq(platformId);
                break;
            case ALL_HOST_NAME:
                reportRequest = getAllHostNameReq(platformId);
                break;
            case BEHAVIOR_FLOW:
                reportRequest = getBehaviorFlowReq(platformId);
                break;
            default:
                throw new Exception("unknown gaReqEnum:" + gaReqEnum);
        }

        GetReportsRequest getReport = new GetReportsRequest()
                .setReportRequests(Arrays.asList(reportRequest));

        GetReportsResponse response = service.reports().batchGet(getReport).execute();
        return response;
    }


    /**
     * Parses and prints the Analytics Reporting API V4 response.
     *
     * @param response the Analytics Reporting API V4 response.
     */
    protected static HbasePo reqAndGet(GetReportsResponse response, HbasePo hbasePo) throws Exception {
        Report report = response.getReports().get(0);
        if (report == null) {
            throw new Exception("response.getReports().get(0) return null");
        }
        ColumnHeader header = report.getColumnHeader();
        List<String> dimensionHeaders = header.getDimensions();
        List<MetricHeaderEntry> metricHeaders = header.getMetricHeader().getMetricHeaderEntries();
        List<ReportRow> rows = report.getData().getRows();

        if (rows == null) {
            return hbasePo;
        }

        String targetHour = hbasePo.getTargetHour();

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
                    String metricName = metricHeaders.get(k).getName();
                    String val = valList.get(k);

                    if (Key.PV().equals(metricName)) {
                        hbasePo.setPv(val);
                    } else if (Key.UV().equals(metricName)) {
                        hbasePo.setUv(val);
                    }
                }
            }
        }

        return hbasePo;
    }


    protected static void parseRetentionRateRep(GetReportsResponse response) throws Exception {
        Report report = response.getReports().get(0);
        if (report == null) {
            throw new Exception("response.getReports().get(0) return null");
        }
        ColumnHeader header = report.getColumnHeader();
        List<String> dimensionHeaders = header.getDimensions();
        List<MetricHeaderEntry> metricHeaders = header.getMetricHeader().getMetricHeaderEntries();
        List<ReportRow> rows = report.getData().getRows();

        if (rows == null) {
            return;
        }

        for (ReportRow row : rows) {
            List<String> dimensions = row.getDimensions();
            List<DateRangeValues> metrics = row.getMetrics();

            for (int i = 0; i < dimensionHeaders.size() && i < dimensions.size(); i++) {
                String dim = dimensions.get(i);
                System.out.println();
//                if (dim.contains("800483")) {
//                    System.out.println();
//                }
            }

            for (int j = 0; j < metrics.size(); j++) {
                DateRangeValues values = metrics.get(j);
                List<String> valList = values.getValues();

                int valListSize = valList.size();
                int metricHeadersSize = metricHeaders.size();

                for (int k = 0; k < valListSize && k < metricHeadersSize; k++) {
                    String metricName = metricHeaders.get(k).getName();
                    String val = valList.get(k);
                    System.out.println();
                }
            }
        }
    }



}
