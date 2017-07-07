package org.jumao.emailLog

import org.apache.spark.sql.SparkSession
import org.jumao.emailLog.entity.EmailPo
import org.jumao.emailLog.service.basic.ELBasic
import org.jumao.googleAnalytics.constants.Key
import org.jumao.googleAnalytics.service.traits.MainBasicTrait
import org.jumao.googleAnalytics.utils.SystemPropUtils

/**
  * 部分 base64 解码出来末尾有乱码，手动解码一样
  */
object ELMain extends ELBasic with MainBasicTrait {

    val APP_NAME = "email-log-analysis"

    def main(args: Array[String]): Unit = {
        checkAndLoadConfPath(args)

        val spark = SparkSession.builder.appName(APP_NAME)
                .master(Key.SPARK_MASTER).getOrCreate()
        import spark.implicits._

        val originDS = spark.read.textFile(Key.EMAIL_LOG_LOCATION)
        val dealedDS = originDS.map(parseLineToEmailPo(_)).filter(_.id.nonEmpty)
        val empty = EmailPo.empty() //official recommend
        val idPoRDD = dealedDS.rdd.keyBy(_.id).aggregateByKey(empty)(aggSeqOrCombOp, aggSeqOrCombOp)

        idPoRDD.collect().foreach(tp => {
            val po = tp._2
            println(po)
        })

        spark.stop()
    }

}
