package org.jumao.spark.googleAnalytics.constants

import org.jumao.spark.googleAnalytics.utils.SystemPropUtils


object Key {


    private val LOG_FILE_PATH_PROP = "log.file.path"
    private val EMAIL_LOG_LOCATION_PROP = "email.log.location"

    private val HADOOP_CORE_SITE_CONF_PROP = "hadoop.core.site.conf"
    private val HDFS_SITE_CONF_PROP = "hdfs.site.conf"
    private val GA_KEY_FILE_LOCATION_PROP = "ga.key.file.location"


    val SPARK_MASTER = SystemPropUtils.get("spark.master", "yarn")
    val DLOG_FILE_PATH = SystemPropUtils.get(LOG_FILE_PATH_PROP, "/tmp/spark/app.log")
    val EMAIL_LOG_LOCATION = SystemPropUtils.get(EMAIL_LOG_LOCATION_PROP, "/tmp/spark/emaillog.txt")

    val HADOOP_CORE_SITE_CONF = SystemPropUtils.get(HADOOP_CORE_SITE_CONF_PROP, "/opt/cloudera/parcels/CDH/lib/hadoop/etc/hadoop/core-site.xml")
    val HDFS_SITE_CONF = SystemPropUtils.get(HDFS_SITE_CONF_PROP, "/opt/cloudera/parcels/CDH/lib/hadoop/etc/hadoop/hdfs-site.xml")
    val GA_KEY_FILE_LOCATION = SystemPropUtils.get(GA_KEY_FILE_LOCATION_PROP, "/tmp/spark/MyProject-81155b910cde.p12")

    /**
      * 若将 conf 文件放在 hdfs，则初始化 [[org.apache.hadoop.hdfs.client.HdfsUtils]] 时，部分参数要写死，
      * 不大方便，不如统一写在一个本地文件里。
      */
//    val CONF_PATH = "hdfs://nn1:8020/user/root/conf/spark-job-conf.properties"
//    val DLOG_FILE_PATH = "E:\\tmp\\app.log"
//    val HADOOP_CORE_SITE_CONF = "src/main/resources/local/hadoop/core-site.xml"
//    val HDFS_SITE_CONF = "src/main/resources/local/hadoop/hdfs-site.xml"


    val PV = "pv"
    val UV = "uv"
    val TODAY = "today"
    val VIEW_ID = "viewId"

    private val HDFS_HOST_PROP = "hdfs.host"
    private val HDFS_PORT_PROP = "hdfs.port"

    val HDFS_HOST = SystemPropUtils.get(HDFS_HOST_PROP, "nn1")
    val HDFS_PORT = SystemPropUtils.get(HDFS_PORT_PROP, "8020")
    val HBASE_TABLE = "jmbi:google_analytics_en"
    val CF_INFO = "info"
    val DATE = "date"
    val PLATFORM_ID = "platform_id"
    val FROM = "from"
    val TO = "to"
    val RELAY = "relay"
    val STATUS = "status"
    val REMOVED = "removed"

    val GA_HOUR = "ga:hour"
    val GA_SESSIONS = "ga:sessions"
    val GA_PAGEVIEWS = "ga:pageviews"
    val GA_UNIQUE_PAGEVIEWS = "ga:uniquePageviews"
    val GA_BOUNCE_RATE = "ga:bounceRate"
    val GA_AVG_SESSION_DURATION = "ga:avgSessionDuration"
    val GA_COUNTRY = "ga:country"
    val GA_USERS = "ga:users"
    val GA_NEW_USERS = "ga:newUsers"
    val GA_COHORT = "ga:cohort"
    val GA_COHORT_NTH_DAY = "ga:cohortNthDay"
    val GA_COHORT_ACTIVE_USERS = "ga:cohortActiveUsers"
    val GA_COHORT_RETENTION_RATE = "ga:cohortRetentionRate"
    val GA_DATE = "ga:date"
    val GA_SEARCH_KEYWORD = "ga:searchKeyword"
    val GA_SEARCH_RESULT_VIEWS = "ga:searchResultViews"
    val GA_SOURCE_MEDIUM = "ga:sourceMedium"
    val GA_LANDING_PAGE_PATH = "ga:landingPagePath"
    val GA_ENTRANCES = "ga:entrances"
    val GA_HOST_NAME = "ga:hostname"
    val GA_PAGE_PATH = "ga:pagePath"
    val GA_PAGE_PATH_LEVEL1 = "ga:pagePathLevel1"
    val GA_PAGE_PATH_LEVEL2 = "ga:pagePathLevel2"


}
