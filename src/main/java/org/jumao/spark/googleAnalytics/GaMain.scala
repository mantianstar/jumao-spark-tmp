package org.jumao.spark.googleAnalytics

import org.apache.spark.rdd.PairRDDFunctions
import org.apache.spark.sql.SparkSession
import org.jumao.spark.googleAnalytics.service.basic.GaBasic
import org.jumao.spark.googleAnalytics.service.traits.MainBasicTrait
import org.jumao.spark.googleAnalytics.utils.HbaseUtils
import org.jumao.spark.googleAnalytics.utils.constants.Key



/**
  * 从 谷歌分析 导出数据的 spark driver
  */
object GaMain extends GaBasic with MainBasicTrait {

    val APP_NAME = "export-jumore-en-from-ga"


    def main(args: Array[String]): Unit = {
        checkAndLoadConfPath(args)

        val spark = SparkSession.builder.appName(APP_NAME)
                .master(Key.SPARK_MASTER).getOrCreate()

        val platformIdsRDD = spark.sparkContext.parallelize(getPlatformIds())
        val hbasePoRDD = platformIdsRDD.mapPartitions(getHbasePo(_)) //the same as .map(), here.

        val pairRDD = new PairRDDFunctions(hbasePoRDD.map(convertToPuts(_)))
        pairRDD.saveAsHadoopDataset(HbaseUtils.jobConfig)

        spark.stop()
    }


}
