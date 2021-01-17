package topk

import org.apache.spark.SparkContext
import scala.collection.mutable.ArrayBuffer

class ALTOPK(inputPath: String, sc: SparkContext) extends Serializable {

  // Start timer
  val inputTime = System.nanoTime

  // Read text file (.csv) as RDD object (Resilient Distributed Dataset) - parallelization of the input source
  val rdd = sc.textFile(inputPath)
  println("rdd created")

  // Prepare dataset
  // 1. split on ","
  // 2. convert to Double
  // 3. perform parallel skyline calculation for each partition

  val rdd2 = rdd
    .map(x => x.split(","))
    .map(x => x.map(y => y.toDouble))
    .mapPartitions(SFSTopkCalculation.addDominanceScoreAndCalculate)


  // Save the rdd2
  rdd2.persist()

  // Total skyline points
  // println("number of local skylines: "+rdd2.count())

  // End timer
  val extTime = System.nanoTime
  println("time of extracting local skyline points:"+(extTime-inputTime).asInstanceOf[Double] / 1000000000.0)

  // Calculate final skyline set based on individual skyline sets of each partition and collect in driver.
//  var partitionSkylines = ArrayBuffer[Array[Double]]()
//  rdd2.collect.foreach(x => SFSTopkCalculation.calculatePartition(partitionSkylines, Iterator(x)))
  rdd2.collect.foreach(arr => println(arr.toList))
  // println("skyline completed. total skylines:"+partitionSkylines.length)
  println("time of extracting final skylines:"+(System.nanoTime-extTime).asInstanceOf[Double] / 1000000000.0)
}