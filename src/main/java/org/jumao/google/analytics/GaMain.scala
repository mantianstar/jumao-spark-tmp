package org.jumao.google.analytics

import org.apache.hadoop.hbase.mapred.TableOutputFormat
import org.apache.hadoop.mapred.JobConf
import org.apache.spark.rdd.PairRDDFunctions
import org.apache.spark.sql.SparkSession
import org.apache.spark.{SparkConf, SparkContext}
import org.jumao.google.analytics.constants.Key
import org.jumao.google.analytics.entity.HbasePo
import org.jumao.google.analytics.service.{GaBasic, JumoreAnalytics}
import org.jumao.google.analytics.utils.HbaseUtils.conf
import org.jumao.google.analytics.utils.{HbaseUtils, PlatformUtil}

import scala.collection.mutable.ArrayBuffer


/**
  * 从 谷歌分析 导出数据的 spark driver
  */
object GaMain extends GaBasic {

    def main(args: Array[String]): Unit = {
        val spark = SparkSession.builder
                .appName(Key.APP_NAME)
                .master(Key.MASTER_OF_SPARK)
                .getOrCreate()

        val platformIdsRDD = spark.sparkContext.parallelize(getPlatformIds())

        val hbasePoRDD = platformIdsRDD.mapPartitions(getHbasePo(_)) //the same as .map(), here.

        val pairRDD = new PairRDDFunctions(hbasePoRDD.map(convertToPuts(_)))

        pairRDD.saveAsHadoopDataset(HbaseUtils.jobConfig)
    }

}
