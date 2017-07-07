package org.jumao.spark.emailLog.service.basic

import org.jumao.emailLog.ELMain.uselessInIdPos
import org.jumao.emailLog.entity.EmailPo
import org.jumao.emailLog.service.helper.ELHelper
import org.jumao.googleAnalytics.constants.Key
import org.jumao.spark.emailLog.entity.EmailPo
import org.jumao.spark.emailLog.service.helper.ELHelper
import org.jumao.spark.googleAnalytics.constants.Key

/**
  **/
class ELBasic extends ELHelper {

    val uselessInIdPos = Seq("connect", "disconnect", "warning", "lost",
        "statistics", "timeout", "NOQUEUE", "improper")


    def parseLineToEmailPo(line: String): EmailPo = {
        val arr = line.split(":")
        val id = arr(ID_ARR_IDX).replace(" ", "")
        var kick = false
        for (useless <- uselessInIdPos if !kick && id.startsWith(useless)) {
            kick = true
        }

        val onePo = EmailPo.empty()

        if (!kick) {
            onePo.id = id

            if (line.contains(Subject)) {
                onePo.subject = getSubject(line)

            } else if (line.contains(FROM_SIGNAL)) {
                onePo.from = getFromOrTo(line, FROM_SIGNAL)

            } else if (line.contains(TO_SIGNAL)) {
                getToAndRelayAndStatus(onePo, arr)

            } else if (line.contains(Key.REMOVED)) {
                onePo.isRemoved = true
            }
        }
        return onePo
    }


    /**
      * 此处聚合算子的 seqOp 和 combOp 可以是同一个
      *
      * @param emptyOrOtherU  作为 zeroValue 的 “空”EmailPo 或 要合并到 retU 的另一个 EmailPo
      * @param retU  要 return 的 EmailPo
      * @return 合并后的 EmailPo
      */
    def aggSeqOrCombOp(emptyOrOtherU: EmailPo, retU: EmailPo): EmailPo = {
        if (retU.subject.isEmpty) {
            retU.subject = emptyOrOtherU.subject
        }
        if (retU.from.isEmpty) {
            retU.from = emptyOrOtherU.from
        }
        if (retU.to.isEmpty) {
            retU.to = emptyOrOtherU.to
        }
        if (retU.relay.isEmpty) {
            retU.relay = emptyOrOtherU.relay
        }
        if (retU.status.isEmpty) {
            retU.status = emptyOrOtherU.status
        }
        if (!retU.isRemoved) {
            retU.isRemoved = emptyOrOtherU.isRemoved
        }
        return retU
    }

}
