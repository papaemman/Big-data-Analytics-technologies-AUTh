package all_local_skyline_w_topk_experiments.all_local_skyline_w_topk

import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD

import java.io.FileWriter
import scala.collection.mutable.ArrayBuffer

class ALTOPK(inputPath: String, sc: SparkContext, k: Int, cores:Int) extends Serializable {


  // Start timer
  val startTime: Long = System.nanoTime

  // Read text file (.csv) as RDD object (Resilient Distributed Dataset) - parallelization of the input source
  // Prepare dataset
  // 1. split on ","
  // 2. convert to Double
  val rdd: RDD[Array[Double]] = sc.textFile(inputPath)
    .map(x => x.split(","))
    .map(x => x.map(y => y.toDouble))

  // Rdd create timer
  val rddCreateTime: Long = System.nanoTime

  // All-Local Skyline (Task 1)
  // - perform parallel skyline calculation for each partition
  // - collect to driver and calculate the final skyline
  val rdd1: RDD[Array[Double]] = rdd.mapPartitions(SFSSkylineCalculation.addScoreAndCalculate)
  rdd1.persist()

  var partitionSkylines: ArrayBuffer[Array[Double]] = ArrayBuffer[Array[Double]]()
  rdd1.collect.foreach(x => SFSSkylineCalculation.calculatePartition(partitionSkylines, Iterator(x)))

  // skyline (Task 1) timer
  val skylineTime: Long = System.nanoTime



  // Top-K Points according to Dominance Score (Task 2)
  val rdd2: RDD[Array[Double]] = rdd.mapPartitions(dt => SFSTopkCalculation.addDominanceScoreAndCalculate(dt, k=k))
  rdd2.persist()
  val rdd2result: Array[Array[Double]] = rdd2.take(k)

  // topk (Task 2) timer
  val topkTime: Long = System.nanoTime

  // Top-K Points according to Dominance Score from Skyline (Task 3)
  var temp: Array[Array[Double]] = partitionSkylines.toArray
  val rdd3: RDD[Array[Double]] = rdd2.filter(p => SFSTopkCalculation.existsIn(p, temp))
  rdd3.persist()
  val rdd3result: Array[Array[Double]] = rdd3.take(k)

  // topk (Task 3) timer
  val skylinetopkTime: Long = System.nanoTime


  // IF you want to save Results uncomment the following code
  //  rdd1.map(x => x.mkString(",")).saveAsTextFile(path="./results/skyline")
  //  sc.parallelize(rdd2result).map(x => x.mkString(",")).saveAsTextFile("./results/top-k-from-all")
  //  sc.parallelize(rdd3result).map(x => x.mkString(",")).saveAsTextFile("./results/top-k-from-skyline")


  // Write experiment times (Parameters)

  // 1. dataset path
  // 2. number of cores
  // 3. k (topk)
  // 4. times (startTime, rddCreateTime, skylineTime, topkTime, skylinetopkTime)

  val stage1 = (rddCreateTime-startTime).asInstanceOf[Double] / 1000000000.0
  val stage2 = (skylineTime-rddCreateTime).asInstanceOf[Double] / 1000000000.0
  val stage3 = (topkTime-skylineTime).asInstanceOf[Double] / 1000000000.0
  val stage4 = (skylinetopkTime-topkTime).asInstanceOf[Double] / 1000000000.0

  val str_to_append: String = inputPath + "," + cores + "," + k + "," + stage1 + "," + stage2 + "," + stage3 + "," + stage4

  // Write in file
  val filename = "./docs/experiment_times.txt"
  val fw = new FileWriter(filename,true) //the true will append the new data
  fw.write(str_to_append + "\n") //appends the string to the file
  fw.close

  println("END of experiment")

}