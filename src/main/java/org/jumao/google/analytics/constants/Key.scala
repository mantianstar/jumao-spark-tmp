package org.jumao.google.analytics.constants

import org.jumao.google.analytics.utils.SystemPropUtils


object Key {

    val APP_NAME = "export-jumore-en-pv-uv"

    val LOG_FILE_PATH_PROP = "log.file.path"

    val HADOOP_CORE_SITE_CONF_PROP = "hadoop.core.site.conf"
    val HDFS_SITE_CONF_PROP = "hdfs.site.conf"

    val CONF_HDFS_PATH = "src/main/resources/spark-job-conf.properties"
//    val CONF_HDFS_PATH = "/tmp/spark/spark-job-conf.properties"
    //SystemPropUtils 初始化要用到上面的参数，声明顺序不能乱
    val MASTER_OF_SPARK = SystemPropUtils.get("master", "")
    val LOG_FILE_PATH = SystemPropUtils.get(LOG_FILE_PATH_PROP, "/tmp/spark/app.log")
    val HADOOP_CORE_SITE_CONF = SystemPropUtils.get(HADOOP_CORE_SITE_CONF_PROP, "")
    val HDFS_SITE_CONF = SystemPropUtils.get(HDFS_SITE_CONF_PROP, "")

    /**
      * 若将 conf 文件放在 hdfs，则初始化 [[org.apache.hadoop.hdfs.client.HdfsUtils]] 时，部分参数要写死，
      * 不大方便，不如统一写在一个本机文件里。
      */
//    val CONF_HDFS_PATH = "hdfs://nn1:8020/user/root/conf/spark-job-conf.properties"
//    val LOG_FILE_PATH = "E:\\tmp\\app.log"
//    val HADOOP_CORE_SITE_CONF = "src/main/resources/local/hadoop/core-site.xml"
//    val HDFS_SITE_CONF = "src/main/resources/local/hadoop/hdfs-site.xml"




}
