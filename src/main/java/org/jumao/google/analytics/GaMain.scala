package org.jumao.google.analytics

import org.apache.spark.{SparkConf, SparkContext}
import org.jumao.google.analytics.constants.Key
import org.jumao.google.analytics.service.{GaBasic, JumoreAnalyticsReporting}
import org.jumao.google.analytics.utils.PlatformUtil


/**
  * 从 谷歌分析 导出数据的 spark driver
  */
object GaMain extends GaBasic {

    def main(args: Array[String]): Unit = {
        val conf = new SparkConf()
                .setAppName(Key.APP_NAME)
                .setMaster(Key.MASTER_OF_SPARK)
        val sparkContext = SparkContext.getOrCreate(conf)

        val rdd = sparkContext.parallelize(getPlatformIds())
        rdd.foreachPartition(it => {

//            JumoreAnalyticsReporting.reqAndGetHbasePo()

        })

    }


}
