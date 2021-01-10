package skyline

import org.apache.spark.SparkContext
import scala.collection.mutable.ArrayBuffer

class ALS(inputPath: String, sc: SparkContext) extends Serializable {
  val inputTime = System.nanoTime
  val rdd = sc.textFile(inputPath)
  println("rdd created")
  val rdd2 = rdd.map(x => x.split(",")).map(x => x.map(y => y.toDouble)).mapPartitions(SFSSkylineCalculation.addScoreAndCalculate)
  rdd2.persist()
  println("number of local skylines: "+rdd2.count())
  val extTime = System.nanoTime
  println("time of extracting local skyline points:"+(extTime-inputTime).asInstanceOf[Double] / 1000000000.0)
  var partitionSkylines = ArrayBuffer[Array[Double]]()
  rdd2.collect.foreach(x => SFSSkylineCalculation.calculatePartition(partitionSkylines, Iterator(x)))
  rdd2.collect.foreach(arr => println(arr.toList))
  println("skyline completed. total skylines:"+partitionSkylines.length)
  println("time of extracting final skylines:"+(System.nanoTime-extTime).asInstanceOf[Double] / 1000000000.0)
}