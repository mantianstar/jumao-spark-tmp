package org.jumao.google.analytics.service

import org.apache.hadoop.hbase.client.Put
import org.apache.hadoop.hbase.io.ImmutableBytesWritable
import org.apache.hadoop.hbase.util.Bytes
import org.jumao.google.analytics.constants.Key
import org.jumao.google.analytics.entity.HbasePo
import org.jumao.google.analytics.utils.PlatformUtil

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


    def convert(hbasePo: HbasePo) = {
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


    val mapPartiFunc = (it: Iterator[String]) => {
        val platformIds = it.toArray
        val hbasePoAb = new ArrayBuffer[HbasePo](platformIds.length)

        for (id <- platformIds) {
            hbasePoAb += JumoreAnalytics.reqAndGetHbasePo(id)
        }
        hbasePoAb.toIterator
    }


}
