import scala.collection.mutable.Seq
import scala.collection.mutable.ArrayBuffer

//val numbers = List(1, 2, 3, 4)
//numbers.filter(_ % 2 == 0)

val seq = Seq(1,2,3)

seq.foreach(_ * 2)
seq.foreach(println)

//val set = Set(2,3)
//
//val ab = new ArrayBuffer[Int]()
//ab += 1
//ab ++= Seq(2,3,4)