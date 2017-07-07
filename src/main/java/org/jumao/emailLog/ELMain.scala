package org.jumao.emailLog

import org.apache.spark.sql.SparkSession
import org.jumao.emailLog.entity.EmailPo
import org.jumao.emailLog.service.basic.ELBasic
import org.jumao.googleAnalytics.constants.Key
import org.jumao.googleAnalytics.service.traits.MainBasicTrait
import org.jumao.googleAnalytics.utils.SystemPropUtils

/**
  **/
object ELMain extends ELBasic with MainBasicTrait {

    val APP_NAME = "email-log-analysis"

    def main(args: Array[String]): Unit = {
        checkAndLoadConfPath(args)

        val spark = SparkSession.builder.appName(APP_NAME)
                .master(Key.SPARK_MASTER).getOrCreate()
        import spark.implicits._

        val ds = spark.read.textFile(Key.EMAIL_LOG_LOCATION)

        val ds2 = ds.map(parseLineToEmailPo(_))

//        ds.map(line => {
//
//        })


    }

}
