package org.jumao.spark.testData

import org.apache.hadoop.hbase.client.Put
import org.apache.hadoop.hbase.io.ImmutableBytesWritable
import org.apache.spark.sql.SparkSession
import org.jumao.spark.googleAnalytics.service.basic.GaBasic
import org.jumao.spark.googleAnalytics.service.traits.MainBasicTrait
import org.jumao.spark.googleAnalytics.utils.constants.Key

import scala.collection.mutable.ArrayBuffer

/**
  **/
object GaDataMain extends GaBasic with MainBasicTrait {

    val APP_NAME = "ga test data"

    def main(args: Array[String]): Unit = {
        checkAndLoadConfPath(args)

        val spark = SparkSession.builder.appName(APP_NAME)
                .master(Key.SPARK_MASTER).getOrCreate()

        val platformIdsRDD = spark.sparkContext.parallelize(getPlatformIds())
        platformIdsRDD.flatMap(genGaTestData(_))

    }


    def genGaTestData(platformId: String): ArrayBuffer[(ImmutableBytesWritable, Put)] = {



    }


}
