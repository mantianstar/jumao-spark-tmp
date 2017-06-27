package org.jumao.google.analytics.utils

import org.apache.hadoop.hbase.HBaseConfiguration
import org.apache.hadoop.hbase.mapred.TableOutputFormat
import org.apache.hadoop.mapred.JobConf
import org.jumao.google.analytics.constants.Key


object HbaseUtils {

    private val conf = HBaseConfiguration.create()

    // general hbase settings
    conf.set("hbase.rootdir", s"hdfs://${Key.HDFS_HOST}:${Key.HDFS_PORT}/hbase")
    conf.setBoolean("hbase.cluster.distributed", true)
    conf.set("hbase.zookeeper.property.clientPort", "2181")
    conf.set("hbase.zookeeper.quorum", Key.HDFS_HOST)

    val jobConfig: JobConf = new JobConf(conf, this.getClass)

    jobConfig.setOutputFormat(classOf[TableOutputFormat])
    jobConfig.set(TableOutputFormat.OUTPUT_TABLE, Key.HBASE_TABLE)

}
