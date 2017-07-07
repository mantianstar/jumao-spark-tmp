package org.jumao.emailLog.entity

/**
  **/
case class EmailPo(id: String, subject: String, from: String, to: String, relay: String, status: String, isRemoved: Boolean) {


}

object EmailPo {
    def empty(): EmailPo = {
        EmailPo("", "", "", "", "", "", false);
    }

}



