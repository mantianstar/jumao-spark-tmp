package org.jumao.google.analytics.constants

import org.jumao.google.analytics.utils.SystemPropUtils


object Key {

    val APP_NAME = "export-jumore-en-pv-uv"

    private val LOG_FILE_PATH_PROP = "log.file.path"

    private val HADOOP_CORE_SITE_CONF_PROP = "hadoop.core.site.conf"
    private val HDFS_SITE_CONF_PROP = "hdfs.site.conf"
    private val GA_KEY_FILE_LOCATION_PROP = "ga.key.file.location"

    val CONF_PATH = "src/main/resources/spark-job-conf.properties"
//    val CONF_PATH = "/tmp/spark/spark-job-conf.properties"
    //SystemPropUtils 初始化要用到上面的参数，声明顺序不能乱
    val MASTER_OF_SPARK = SystemPropUtils.get("master", "")
    val LOG_FILE_PATH = SystemPropUtils.get(LOG_FILE_PATH_PROP, "/tmp/spark/app.log")
    val HADOOP_CORE_SITE_CONF = SystemPropUtils.get(HADOOP_CORE_SITE_CONF_PROP, "")
    val HDFS_SITE_CONF = SystemPropUtils.get(HDFS_SITE_CONF_PROP, "")
    val GA_KEY_FILE_LOCATION = SystemPropUtils.get(GA_KEY_FILE_LOCATION_PROP, "")

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

}
