package org.jumao.emailLog.service.basic

import org.jumao.emailLog.ELMain.uselessInIdPos
import org.jumao.emailLog.entity.EmailPo
import org.jumao.emailLog.service.helper.ELHelper
import org.jumao.googleAnalytics.constants.Key

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




    def aggSeqOrCombOp(emptyOrOtherU: EmailPo, toRet: EmailPo): EmailPo = {
        if (toRet.subject.isEmpty) {
            toRet.subject = emptyOrOtherU.subject
        }
        if (toRet.from.isEmpty) {
            toRet.from = emptyOrOtherU.from
        }
        if (toRet.to.isEmpty) {
            toRet.to = emptyOrOtherU.to
        }
        if (toRet.relay.isEmpty) {
            toRet.relay = emptyOrOtherU.relay
        }
        if (toRet.status.isEmpty) {
            toRet.status = emptyOrOtherU.status
        }
        if (!toRet.isRemoved) {
            toRet.isRemoved = emptyOrOtherU.isRemoved
        }
        return toRet
    }

}
