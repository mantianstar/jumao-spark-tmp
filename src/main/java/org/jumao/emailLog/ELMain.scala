package org.jumao.emailLog

import org.jumao.googleAnalytics.utils.SystemPropUtils

/**
  **/
object ELMain {


    def main(args: Array[String]): Unit = {
        if (args.isEmpty || args(0) == null) {
            println("no effictive conf path, System.exit(1).")
            System.exit(1)
        }
        val confPath = args(0)
        SystemPropUtils.initPropFile(confPath)



    }

}
