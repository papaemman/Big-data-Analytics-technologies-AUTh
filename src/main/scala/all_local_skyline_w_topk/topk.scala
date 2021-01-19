package all_local_skyline_w_topk

import org.apache.spark.{SparkConf, SparkContext}

object topk {
  def main(args: Array[String]): Unit = {

    println("***********************************************************************************************")
    println("This is the top-k query application for Spark.")
    println("***********************************************************************************************")

    // Define spark configuration and sparc contect
    val conf = new SparkConf().setMaster("local").setAppName("skylineCalculator")
    val sc = new SparkContext(conf)

    // Start timer
    val start = System.nanoTime

    // Call ALTOPK method in a specific dataset
    new ALTOPK(inputPath="./datasets/dataset_10000_points_4_dimension_anticorrelated_distribution.csv", sc)
    //println(System.getProperty("user.dir"))

    // End timer
    val timeElapsed = System.nanoTime - start
    println("Total runtime: " + timeElapsed.asInstanceOf[Double] / 1000000000.0)

  }
}
