package org.jumao.emailLog

import org.apache.spark.sql.SparkSession
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

        val ds = spark.read.textFile(Key.EMAIL_LOG_LOCATION)

        ds.map(line => {
            val arr = line.split(":")
            val id = arr(3).replace(" ", "")
            var kick = false
            for (useless <- uselessInIdPos if id.startsWith(useless)) {
                kick = true
            }

            if (kick) {

            } else {

            }
        })

        ds.map(line => {

        })


    }

}
