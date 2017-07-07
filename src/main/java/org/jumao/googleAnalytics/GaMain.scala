package org.jumao.googleAnalytics

import org.apache.spark.rdd.PairRDDFunctions
import org.apache.spark.sql.SparkSession
import org.jumao.googleAnalytics.constants.Key
import org.jumao.googleAnalytics.service.traits.MainBasicTrait
import org.jumao.googleAnalytics.service.basic.GaBasic
import org.jumao.googleAnalytics.utils.{HbaseUtils, SystemPropUtils}



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
