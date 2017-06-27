import org.apache.commons.io.IOUtils;
import org.jumao.google.analytics.utils.CalendarUtils;
import org.jumao.google.analytics.utils.DateUtils;
import sun.nio.ch.IOUtil;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class LocalTest {

    private static final SimpleDateFormat HBASE_NO_HOUR_FORMAT = new SimpleDateFormat("yyyyMMdd");

    public static void main(String[] args) throws Exception {
//        URL url = new URL("https://www.google.com.hk/");
//        System.err.println(IOUtils.toString(url.openStream(), "utf8"));

//        Calendar nowCal = CalendarUtils.getCalendarBy("2017-6-25 25:59:58");
//        int nowHour = nowCal.get(Calendar.HOUR_OF_DAY);
//        System.err.println(nowHour);


        System.err.println(HBASE_NO_HOUR_FORMAT.format(new Date()));
    }


}
