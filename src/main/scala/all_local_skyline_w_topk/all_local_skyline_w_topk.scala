package all_local_skyline_w_topk

import org.apache.spark.{SparkConf, SparkContext}

object all_local_skyline_w_topk {
  def main(args: Array[String]): Unit = {

    println("***********************************************************************************************")
    println("This is all local skyline with top-k application for Spark.")
    println("***********************************************************************************************")

    // Define parameters for a specific experiment
    //    val datapath: String = args(0)
    //    val cores:Int = args(1).toInt
    //    val k: Int = args(2).toInt

    val datapath: String = "./datasets/toy_dataset_4d.csv"
    val cores:Int = 3
    val k: Int = 1

    // Define Spark configuration and spark context
    val conf = new SparkConf().setMaster("local[" + cores + "]").setAppName("skylineCalculator")
    val sc = new SparkContext(conf)

    // Start timer
    val start = System.nanoTime

    // Call ALTOPK method in a specific dataset
    new ALTOPK(inputPath=datapath, sc=sc, k=k, cores=cores)
    // new ALTOPK(inputPath="./datasets/toy_dataset_2d.csv", sc=sc, k=k)

    // End timer
    val timeElapsed = System.nanoTime - start
    println("Total runtime: " + timeElapsed.asInstanceOf[Double] / 1000000000.0)

  }
}
