package org.jumao.spark.googleAnalytics.service.basic

import org.apache.hadoop.hbase.client.Put
import org.apache.hadoop.hbase.io.ImmutableBytesWritable
import org.apache.hadoop.hbase.util.Bytes
import org.jumao.googleAnalytics.entity.HbasePo
import org.jumao.googleAnalytics.service.JumoreAnalytics
import org.jumao.googleAnalytics.utils.PlatformUtil
import org.jumao.spark.googleAnalytics.constants.Key
import org.jumao.spark.googleAnalytics.entity.HbasePo
import org.jumao.spark.googleAnalytics.service.JumoreAnalytics
import org.jumao.spark.googleAnalytics.utils.PlatformUtil

import scala.collection.mutable.ArrayBuffer


class GaBasic {


    def getPlatformIds(): ArrayBuffer[String] = {
        val platformIds = PlatformUtil.platformIdViewIdMap.keySet()
        val ab = new ArrayBuffer[String](platformIds.size())
        for (id <- platformIds.toArray) {
            ab += id.toString
        }
        ab
    }


    def convertToPuts(hbasePo: HbasePo) = {
        val rowKey = hbasePo.getPlatformId + hbasePo.getDate
        val put = new Put(Bytes.toBytes(rowKey))

        put.addColumn(Bytes.toBytes(Key.CF_INFO),
            Bytes.toBytes(Key.DATE),
            Bytes.toBytes(hbasePo.getDate))

        put.addColumn(Bytes.toBytes(Key.CF_INFO),
            Bytes.toBytes(Key.PLATFORM_ID),
            Bytes.toBytes(hbasePo.getPlatformId))

        put.addColumn(Bytes.toBytes(Key.CF_INFO),
            Bytes.toBytes(Key.PV),
            Bytes.toBytes(hbasePo.getPv))

        put.addColumn(Bytes.toBytes(Key.CF_INFO),
            Bytes.toBytes(Key.UV),
            Bytes.toBytes(hbasePo.getUv))

        (new ImmutableBytesWritable, put)
    }


    val getHbasePo = (it: Iterator[String]) => {
        val platformIds = it.toArray
        val hbasePoAb = new ArrayBuffer[HbasePo](platformIds.length)

        for (id <- platformIds) {
            hbasePoAb += JumoreAnalytics.reqAndGetHbasePo(id)
        }
        hbasePoAb.toIterator
    }


}
