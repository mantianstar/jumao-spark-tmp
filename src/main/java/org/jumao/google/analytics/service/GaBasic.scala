package org.jumao.google.analytics.service

import org.jumao.google.analytics.utils.PlatformUtil

import scala.collection.mutable.ArrayBuffer


class GaBasic {


    def getPlatformIds(): ArrayBuffer[String] = {
        val platformIds = PlatformUtil.platformIdViewIdMap.keySet()
        val ab = new ArrayBuffer[String](platformIds.size())
        for (id <- platformIds) {
            ab += id
        }
        ab
    }

}
