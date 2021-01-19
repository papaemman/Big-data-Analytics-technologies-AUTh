package all_local_skyline_w_topk

import org.apache.spark.SparkContext
import org.apache.spark.sql.Row

import scala.collection.mutable.ArrayBuffer

class ALTOPK(inputPath: String, sc: SparkContext) extends Serializable {

  // Start timer
  val inputTime = System.nanoTime

  // Read text file (.csv) as RDD object (Resilient Distributed Dataset) - parallelization of the input source
  val rdd = sc.textFile(inputPath)
    .map(x => x.split(","))
    .map(x => x.map(y => y.toDouble))
  println("rdd created")

  // Prepare dataset
  // 1. split on ","
  // 2. convert to Double
  // 3. perform parallel skyline calculation for each partition


  //All-Local Skyline (Task 1)
  val rdd1 = rdd.mapPartitions(SFSSkylineCalculation.addScoreAndCalculate)
  rdd1.persist()
  println(rdd1.count())
  var partitionSkylines = ArrayBuffer[Array[Double]]()
  rdd1.collect.foreach(x => SFSSkylineCalculation.calculatePartition(partitionSkylines, Iterator(x)))

  //Top-K Points according to Dominance Score (Task 2)
  val rdd2 = rdd.mapPartitions(SFSTopkCalculation.addDominanceScoreAndCalculate)
  rdd2.persist()
  val rdd2result = rdd2.take(2)

  //Top-K Points according to Dominance Score from Skyline (Task 3)
  var temp = rdd1.toLocalIterator.toArray
  val rdd3 = rdd2.filter(p => SFSTopkCalculation.existsIn(p, temp))
  rdd3.persist()

  val rdd3result = rdd3.take(3)
  rdd3result.foreach(arr => println(arr.toList))
  println(rdd3.count())


  // Total skyline points <<Task 1>>
  // println("number of local skylines: "+rdd1.count())

  // End timer
  val extTime = System.nanoTime
  println("time of extracting local skyline points:"+(extTime-inputTime).asInstanceOf[Double] / 1000000000.0)

  // Calculate final skyline set based on individual skyline sets of each partition and collect in driver.
//  var partitionSkylines = ArrayBuffer[Array[Double]]()
//  rdd2.collect.foreach(x => SFSTopkCalculation.calculatePartition(partitionSkylines, Iterator(x)))
  rdd2result.foreach(arr => println(arr.toList))
  // println("skyline completed. total skylines:"+partitionSkylines.length)
  println("time of extracting final skylines:"+(System.nanoTime-extTime).asInstanceOf[Double] / 1000000000.0)
}