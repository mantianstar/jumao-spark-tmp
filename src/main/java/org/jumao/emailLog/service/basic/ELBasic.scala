package org.jumao.emailLog.service.basic

import org.jumao.emailLog.ELMain.uselessInIdPos
import org.jumao.emailLog.entity.EmailPo

/**
  **/
class ELBasic {

    val uselessInIdPos = Seq("connect", "disconnect", "warning", "lost", "statistics")
    val Subject = "Subject"


    def parseLineToEmailPo(line: String): EmailPo = {
        val arr = line.split(":")
        val id = arr(3).replace(" ", "")
        var kick = false
        for (useless <- uselessInIdPos if id.startsWith(useless) && !kick) {
            kick = true
        }

        if (kick) {
            return EmailPo.empty()
        } else {
            if (line.contains(Subject)) {

            } else {

            }
            return EmailPo.empty()
        }
    }


}
