package org.jumao.spark.emailLog.service.helper

import org.jumao.spark.emailLog.entity.EmailPo
import org.jumao.spark.googleAnalytics.utils.constants.Key
import org.jumao.spark.googleAnalytics.utils.{DigestUtils, Verifier}
import org.slf4j.LoggerFactory

/**
  **/
class ELHelper {

    val LOG = LoggerFactory.getLogger(classOf[ELHelper])
    val ID_ARR_IDX = 3
    val Subject = "Subject"
    val FROM_SIGNAL = "from=<"
    val TO_SIGNAL = "to=<"


    def getSubject(line: String): String = {
        val startIdx = line.indexOf(Subject)
        val endIdx = line.indexOf(Key.FROM, startIdx)

        //e.g. "...Subject: =?utf-8?B?5Zyw5Z2AIO+8jOiAjOS4lOWcsOWdgOS4reeahOecgeW4g from..."
        val encoded = line.substring(startIdx + 8, endIdx - 1)

        val strArr = encoded.split(" =\\?utf-8\\?B\\?")
//        if (line.contains("QlVHICMyMDcxNiDjgJDliY3lj7At6LWE6K6v44CR")) {
//            println()
//        }
        val sb = new StringBuilder

        for (str <- strArr if !str.isEmpty) {
            sb.append(DigestUtils.decodeBase64(str))
        }

        if (sb.isEmpty) {
            return sb.toString()
        }
        val lenDecOne = sb.length - 1
        val lastChar = sb.charAt(lenDecOne).toString
        if (Verifier.isEffectiveStr(lastChar, true)) {
            return sb.toString()
        } else {
            return sb.toString().substring(0, lenDecOne)
        }
    }


    @deprecated
    def getFromAndTo(onePo: EmailPo, arr: Array[String]) = {
        val lastPart = arr(arr.length - 1)
        val strArr = lastPart.split(" ")

        def getFromOrTo(str: String): String = {
            val endIdx = str.length - 1
            if (endIdx > 6) {
                return str.substring(6, endIdx)
            }
            return ""
        }

        for (str <- strArr if !str.isEmpty) {
            try {
                //str like "from=<....>"
                if (str.startsWith(Key.FROM)) {
                    onePo.from = getFromOrTo(str)

                } else if (str.startsWith(Key.TO)) {
                    onePo.to = getFromOrTo(str)
                }
            } catch {
                case e: Exception => LOG.error("", e)
            }
        }
        println()
    }


    def getFromOrTo(line: String, signal: String): String = {
        val startIdx = line.indexOf(signal) + signal.length

        if (startIdx > 0) {
            //e.g.  from=<.....>
            val endIdx = line.indexOf(">", startIdx)
            if (endIdx > 0) {
                return line.substring(startIdx, endIdx)
            }
        }
        return ""
    }


    def getToAndRelayAndStatus(onePo: EmailPo, arr: Array[String]) = {
        val lastPartSb = new StringBuilder
        for (idx <- 4 to arr.length - 1) {
            lastPartSb.append(arr(idx).trim)
        }
        val strArr = lastPartSb.mkString.split(",")

        for (str <- strArr if !str.isEmpty) {
            try {
                //e.g. "to=<wujiemiao@jumore.com>" or "relay=..." or "status=..."
                val strTrimed = str.trim
                if (strTrimed.contains(TO_SIGNAL)) {
                    onePo.to = getFromOrTo(strTrimed, TO_SIGNAL)

                } else if (strTrimed.contains(Key.RELAY)) {
                    onePo.relay = getRelayOrStatus(strTrimed)

                } else if (strTrimed.contains(Key.STATUS)) {
                    onePo.status = getRelayOrStatus(strTrimed)
                }

            } catch {
                case e: Exception => LOG.error("", e)
            }
        }
    }

    def getRelayOrStatus(str: String): String = {
        var startIdx = str.indexOf("=")
        if (startIdx < 0) {
            return ""
        }

        startIdx += 1
        val endIdx = str.length

        if (endIdx > startIdx) {
            return str.substring(startIdx, endIdx)
        } else {
            return ""
        }
    }



}
