package org.jumao.google.analytics.constants

import org.jumao.google.analytics.utils.SystemPropUtils


object Key {

    val APP_NAME = "export-jumore-en-from-ga"

    private val LOG_FILE_PATH_PROP = "log.file.path"

    private val HADOOP_CORE_SITE_CONF_PROP = "hadoop.core.site.conf"
    private val HDFS_SITE_CONF_PROP = "hdfs.site.conf"
    private val GA_KEY_FILE_LOCATION_PROP = "ga.key.file.location"

    val SPARK_MASTER = SystemPropUtils.get("spark.master", "yarn")
    val LOG_FILE_PATH = SystemPropUtils.get(LOG_FILE_PATH_PROP, "/tmp/spark/app.log")
    val HADOOP_CORE_SITE_CONF = SystemPropUtils.get(HADOOP_CORE_SITE_CONF_PROP, "/opt/cloudera/parcels/CDH/lib/hadoop/etc/hadoop/core-site.xml")
    val HDFS_SITE_CONF = SystemPropUtils.get(HDFS_SITE_CONF_PROP, "/opt/cloudera/parcels/CDH/lib/hadoop/etc/hadoop/hdfs-site.xml")
    val GA_KEY_FILE_LOCATION = SystemPropUtils.get(GA_KEY_FILE_LOCATION_PROP, "/tmp/spark/MyProject-81155b910cde.p12")

    /**
      * 若将 conf 文件放在 hdfs，则初始化 [[org.apache.hadoop.hdfs.client.HdfsUtils]] 时，部分参数要写死，
      * 不大方便，不如统一写在一个本机文件里。
      */
//    val CONF_PATH = "hdfs://nn1:8020/user/root/conf/spark-job-conf.properties"
//    val LOG_FILE_PATH = "E:\\tmp\\app.log"
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

}
