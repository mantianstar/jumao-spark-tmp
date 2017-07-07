import org.apache.commons.io.IOUtils;
import org.jumao.googleAnalytics.utils.CalendarUtils;
import org.jumao.googleAnalytics.utils.DateUtils;
import org.jumao.googleAnalytics.utils.DigestUtils;
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

//        System.err.println(HBASE_NO_HOUR_FORMAT.format(new Date()));

//        String str = "=?utf-8?B?QlVHICMyMDUzMiDjgJDlk4HniYzppoYt5Lit5Y+w44CR55u4?=? =?utf-8?B?5YaM566h55CGIOaWsOWinuaPkOekuuacieemgeeUqOivje+8jOS9huaYrw==?=? =?utf-8?B?5rKh5pyJ5qCH5piOIOemgeeUqOivjeaYr+WTquS4qiAtI";
        String str = "=?utf-8?B?QlVHICMyMDUzMiDjgJDlk4HniYzppoYt5Lit5Y+w44CR55u4?=?";
        String decode = DigestUtils.decodeBase64(str);
        System.err.println(decode);
    }


}
