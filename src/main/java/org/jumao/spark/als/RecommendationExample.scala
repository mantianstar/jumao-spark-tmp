package org.jumao.spark.als

import org.apache.spark.mllib.recommendation.ALS
import org.apache.spark.mllib.recommendation.Rating
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession
import org.jumao.googleAnalytics.utils.DLOG
import org.jumao.spark.googleAnalytics.utils.DLOG


object RecommendationExample {

    val forLargeData = true

//    Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
//    Logger.getLogger("org.eclipse.jetty.server").setLevel(Level.OFF)

    def main(args: Array[String]): Unit = {
        val spark = SparkSession.builder
                .appName("ALS_RDD_Example")
//                .master("local[4]")
                .getOrCreate()

        import spark.implicits._

//        val csvPath = "E:\\tmp\\ml-20m\\ratings.csv"
        val csvPath = "hdfs://nn1:8020/tmp/spark/ratings.csv"
        val data = spark.read.csv(csvPath)
        val ratings = data.map(row => {
            try {
                var rowStr = row.toString()
                rowStr = rowStr.substring(1, rowStr.length - 1)
                var fields = rowStr.split(",")

                Rating(parseStrToInt(fields(0)), parseStrToInt(fields(1)), parseStrToInt(fields(2)).toDouble)
            } catch {
                case e: Exception => {
                    Rating(0, 0, 0D)
                }
            }
        }).rdd.filter(_.user != 0)


//        val splits = ratings.randomSplit(Array(0.8, 0.2))
//        val training = splits(0)
//        val test = splits(1)

        // Build the recommendation model using ALS
        val rank = 5 //One way to work it is to start with a rank of 5-10, then increase it, say 5 at a time until your results stop improving. That way you determine the best rank for your dataset by experimentation.
        val numIterations = 10
        val model = ALS.train(ratings, rank, numIterations, 0.01)

        //将训练集当作测试集来进行对比测试。从训练集中获取用户和商品的映射：
        val usersProducts = ratings.map { case Rating(user, product, rate) =>
            (user, product)
        }
        val predictions =
            model.predict(usersProducts).map { case Rating(user, product, rate) =>
                ((user, product), rate)
            }

        //将真实评分数据集与预测评分数据集进行合并，这样得到用户对每一个商品的实际评分和预测评分：
        val ratesAndPreds = ratings.map { case Rating(user, product, rate) =>
            ((user, product), rate)
        }.join(predictions)

        val mse = ratesAndPreds.map { case ((user, product), (r1, r2)) =>
            val err = (r1 - r2)
            err * err
        }.mean()
        val rmse= math.sqrt(mse)

        // Save and load model
//        model.save(sc, "E:\\tmp\\myCollaborativeFilter")
//        val sameModel = MatrixFactorizationModel.load(sc, "E:\\tmp\\myCollaborativeFilter")

        val topKRecs = model.recommendProductsForUsers(3)
//        val topKRecs2 = model.recommendUsersForProducts(10)

        showResult(rmse, topKRecs)

        spark.stop()
    }


    def showResult(rmse: Double, topKRecs: RDD[(Int, Array[Rating])]) = {

        if (forLargeData) {
//            topKRecs.foreachPartition(it => {
//                for (tp <- it) {
//                    println(tp._1)
//                }
//            })
            topKRecs.foreach(ele => {
                println(ele._1)
            })
            DLOG.log(s"RMSE = $rmse")

        } else {
            val arr = topKRecs.collect()
            val szie = arr.size
            arr.foreach(it => {
                println("-----------------------------------")
                val user = it._1
                println(it._2.mkString("\n"))
            })

            println(s"RMSE = $rmse")
        }
    }


    def parseStrToInt(str: String): Int = {
        val userSb = new StringBuilder()
        for (c <- str if Character.isDigit(c)) {
            userSb.append(c)
        }
        return userSb.toInt
    }

}
