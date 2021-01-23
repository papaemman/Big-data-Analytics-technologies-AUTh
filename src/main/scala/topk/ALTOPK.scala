package topk

import org.apache.spark.SparkContext
import scala.collection.mutable.ArrayBuffer

class ALTOPK(inputPath: String, sc: SparkContext, k:Int) extends Serializable {

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
    .mapPartitions(dt => SFSTopkCalculation.addDominanceScoreAndCalculate(dt, k=k))

  // Save the rdd2
  rdd2.persist()

  // End timer
  val extTime = System.nanoTime
  println("time of extracting topk points:"+(extTime-inputTime).asInstanceOf[Double] / 1000000000.0)

  // Calculate final topk set based on individual topk sets of each partition and collect in driver.
  //  var partitionSkylines = ArrayBuffer[Array[Double]]()
  //  rdd2.collect.foreach(x => SFSTopkCalculation.calculatePartition(previousTopk, Iterator(x)))
  // println("topk completed. topk points:"+partitionSkylines.length)

  rdd2.collect.foreach(arr => println(arr.toList))
  println("time of extracting final topk points:"+(System.nanoTime-extTime).asInstanceOf[Double] / 1000000000.0)
}