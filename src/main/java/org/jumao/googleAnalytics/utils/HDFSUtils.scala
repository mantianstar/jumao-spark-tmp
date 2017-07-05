package org.jumao.googleAnalytics.utils

import java.io.{BufferedInputStream, FileInputStream}

import org.apache.hadoop.conf._
import org.apache.hadoop.fs._
import org.jumao.googleAnalytics.constants.Key

/**
  * Created by kartty on 16-9-9.
  */
object HDFSUtils {

    private val conf = new Configuration()
    private val CORE_SITE_CONF = new Path(Key.HADOOP_CORE_SITE_CONF)
    private val HDFS_SITE_CONF = new Path(Key.HDFS_SITE_CONF)

    //没有这步会读写到本地
    conf.addResource(CORE_SITE_CONF)
    conf.addResource(HDFS_SITE_CONF)

    private val fileSystem = FileSystem.get(conf)

    def instance(): FileSystem = {
        fileSystem
    }

    def mySaveFile(localFilePath: String, hdfsPath: String) = {
        val input = new BufferedInputStream(new FileInputStream(localFilePath))
        val path = new Path(hdfsPath)
        val out = fileSystem.create(path, true)

        val buffer = new Array[Byte](1024)
        var readCount = input.read(buffer)

        while (readCount > 0) {
            out.write(buffer)
            readCount = input.read(buffer)
        }

        out.close()
        input.close()

        //same as below
//        fileSystem.copyFromLocalFile(false, true, new Path(localFilePath), new Path(hdfsPath))
    }


    def main(args: Array[String]) {
        //以下两种 path 都可以
        val hdfsPath = "hdfs://localhost:9000/user/kartty/tmp/approve.xlsx"
//        val hdfsPath = "/user/kartty/tmp/taskref.txt"

        HDFSUtils.mySaveFile(
            "/home/kartty/taskref.txt", hdfsPath)
    }

}
