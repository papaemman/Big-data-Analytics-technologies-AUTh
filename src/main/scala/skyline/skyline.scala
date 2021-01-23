package skyline

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext

object skyline {
  def main(args: Array[String]): Unit = {

    println("***********************************************************************************************")
    println("This is the skylineCalculator application for Spark.")
    println("***********************************************************************************************")

    // Define spark configuration and spark context
    val conf = new SparkConf().setMaster("local").setAppName("skylineCalculator")
    val sc = new SparkContext(conf)

    // Start timer
    val start = System.nanoTime

    // Call ALS method in a specific dataset
    // new ALS(inputPath="./datasets/dataset_10000_points_4_dimension_anticorrelated_distribution.csv", sc)
    new ALS(inputPath="./datasets/toy_dataset_2d.csv", sc)

    // End timer
    val timeElapsed = System.nanoTime - start
    println("Total runtime: " + timeElapsed.asInstanceOf[Double] / 1000000000.0)

  }
}
