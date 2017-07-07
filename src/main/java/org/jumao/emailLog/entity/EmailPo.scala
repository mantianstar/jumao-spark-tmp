package org.jumao.emailLog.entity

/**
  **/
case class EmailPo(var id: String, var subject: String, var from: String,
                   var to: String, var relay: String, var status: String, var isRemoved: Boolean = false) {


}

object EmailPo {
    def empty(): EmailPo = {
        EmailPo("", "", "", "", "", "", false);
    }

}



