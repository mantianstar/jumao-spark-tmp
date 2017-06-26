package org.jumao.google.analytics.utils

import java.io.File
import java.nio.charset.Charset
import java.text.SimpleDateFormat
import java.util

import com.google.common.io.Files
import org.jumao.google.analytics.constants.Key

/**
  * driver log
  */
object DLOG {

    val minDataFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    var logFile = new File(Key.LOG_FILE_PATH)
    var errLogFile = new File(Key.LOG_FILE_PATH + ".error")
    val NEW_LINE = "\n"

    def log(tuple: (String, Long)): Unit = {
        val str = s"${minDataFormat.format(new util.Date())} ${tuple._1}_${tuple._2}\n"
        Files.append(str, logFile, Charset.defaultCharset())
    }

    def log(str: String): Unit = {
        try {
            Files.append(s"${minDataFormat.format(new util.Date())} $str\n", logFile, Charset.defaultCharset())
        } catch {
            case e: Exception => {
                e.printStackTrace()
                logFile = new File(Key.LOG_FILE_PATH)
            }
        }
    }

    def errLog(str: String): Unit = {
        try {
            Files.append(s"${minDataFormat.format(new util.Date())} $str\n", errLogFile, Charset.defaultCharset())
        } catch {
            case e: Exception => {
                e.printStackTrace()
                errLogFile = new File(Key.LOG_FILE_PATH + ".error")
            }
        }
    }

    def info(str: String): Unit = {
        log(str)
    }

    def info(str: String, e: Exception): Unit = {
        log(GeneralUtils.getAllStackTraceFromExp(e))
    }

    def error(str: String): Unit = {
        errLog(str)
    }

    def error(str: String, e: Exception): Unit = {
        errLog(GeneralUtils.getAllStackTraceFromExp(e))
    }

}
