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
//        String str = "QlVHICMyMDUzMiDjgJDlk4HniYzppoYt5Lit5Y+w44CR55u4";
//        String str = "5YaM566h55CGIOaWsOWinuaPkOekuuacieemgeeUqOivje+8jOS9huaYrw==?=?";
//        String str = " =?utf-8?B?QlVHICMyMDcxNiDjgJDliY3lj7At6LWE6K6v44CR?=? =?utf-8?B?6LWE6K6v6aG155qE5Y+z6L655pyA5paw6LWE6K6v77yM5qCH6aKY6L+H6ZW/?=? =?utf-8?B?5bu66K6u55So55yB55Wl5Y+36KGo56S6IO+8jOWPquaYvuekuuS4g";
        String str = "QlVHICMyMDcxNiDjgJDliY3lj7At6LWE6K6v44CR6LWE6K6v6aG155qE5Y+z6L655pyA5paw6LWE6K6v77yM5qCH6aKY6L+H6ZW/5bu66K6u55So55yB55Wl5Y+36KGo56S6IO+8jOWPquaYvuekuuS4g";

//        String[] strArr = str.split(" =\\?utf-8\\?B\\?");
//
//        for (String ele : strArr) {
//            String decode = DigestUtils.decodeBase64(ele);
//            System.err.println(decode);
//        }

        System.err.println(DigestUtils.decodeBase64(str));
    }


}
