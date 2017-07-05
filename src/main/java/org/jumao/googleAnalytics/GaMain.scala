package org.jumao.googleAnalytics

import org.apache.spark.rdd.PairRDDFunctions
import org.apache.spark.sql.SparkSession
import org.jumao.google.analytics.constants.Key
import org.jumao.google.analytics.service.GaBasic
import org.jumao.google.analytics.utils.{HbaseUtils, SystemPropUtils}
import org.jumao.googleAnalytics.constants.Key
import org.jumao.googleAnalytics.service.GaBasic
import org.jumao.googleAnalytics.utils.{HbaseUtils, SystemPropUtils}



/**
  * 从 谷歌分析 导出数据的 spark driver
  */
object GaMain extends GaBasic {

    def main(args: Array[String]): Unit = {
        if (args.isEmpty || args(0) == null) {
            println("no effictive conf path, System.exit(1).")
            System.exit(1)
        }
        val confPath = args(0)
        SystemPropUtils.initPropFile(confPath)

        val spark = SparkSession.builder.appName(Key.APP_NAME)
                .master(Key.SPARK_MASTER).getOrCreate()

        val platformIdsRDD = spark.sparkContext.parallelize(getPlatformIds())
        val hbasePoRDD = platformIdsRDD.mapPartitions(getHbasePo(_)) //the same as .map(), here.

        val pairRDD = new PairRDDFunctions(hbasePoRDD.map(convertToPuts(_)))
        pairRDD.saveAsHadoopDataset(HbaseUtils.jobConfig)

        spark.stop();
    }

}
