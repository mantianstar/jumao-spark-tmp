package org.jumao.googleAnalytics

import java.io.File
import java.util

import org.apache.hadoop.fs.Path
import org.apache.spark.{SparkConf, SparkContext}
import org.jumao.googleAnalytics.constants.Key
import org.jumao.googleAnalytics.service.JumoreAnalytics
import org.jumao.googleAnalytics.utils.{DLOG, GeneralUtils}
import org.slf4j.LoggerFactory

/**
  * Created by kartty on 17-3-2.
  */
object TestMain {

//    val LOG = LoggerFactory.getLogger(this.getClass)


    def main(args: Array[String]): Unit = {
        val conf = new SparkConf()
                .setAppName("TestMain")
                .setMaster(Key.SPARK_MASTER)
        val sparkContext = SparkContext.getOrCreate(conf)

//        println(Key.CONF_PATH)
//        val CORE_SITE_CONF = new Path(Key.HADOOP_CORE_SITE_CONF)
//        println(CORE_SITE_CONF.getName)
//        println(SystemPropUtils.get(HADOOP_CORE_SITE_CONF_PROP, ""))

        val conf2 =  sparkContext.getConf
        DLOG.info(s"---conf2: ${conf2.get("spark.master")}")
        val rdd = sparkContext.parallelize(Seq(1))



        val sbRdd = rdd.map(e => {
            val sb = new StringBuilder

            sb.append("user.dir:"+System.getProperty("user.dir")).append(DLOG.NEW_LINE)
            sb.append("java.library.path:"+System.getProperty("java.library.path")).append(DLOG.NEW_LINE)
            sb.append("user.home:"+System.getProperty("user.home")).append(DLOG.NEW_LINE)

            val directory = new File("/")
            sb.append("/:"+directory.getPath()).append(DLOG.NEW_LINE)
            sb.append("/:"+directory.getCanonicalPath()).append(DLOG.NEW_LINE)
            sb.append("/:"+directory.getAbsolutePath()).append(DLOG.NEW_LINE)

            val directory2 = new File("")
            sb.append("\"\":"+directory2.getPath()).append(DLOG.NEW_LINE)
            sb.append("\"\":"+directory2.getCanonicalPath()).append(DLOG.NEW_LINE)
            sb.append("\"\":"+directory2.getAbsolutePath()).append(DLOG.NEW_LINE)

            try {
                sb.append(classOf[JumoreAnalytics].getResource("/").toString).append(DLOG.NEW_LINE)
                sb.append(classOf[JumoreAnalytics].getResource("").toString).append(DLOG.NEW_LINE)
            } catch {
                case e: Exception => sb.append(DLOG.NEW_LINE).append("error:").append(GeneralUtils.getAllStackTraceFromExp(e))
            }

            sb
        })

        val arr = sbRdd.collect()
        DLOG.info(arr(0).toString())
    }





}
