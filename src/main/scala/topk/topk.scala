package topk

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext

object topk {
  def main(args: Array[String]): Unit = {

    println("***********************************************************************************************")
    println("This is the top-k query application for Spark.")
    println("***********************************************************************************************")

    // Define spark configuration and spark context
    val conf = new SparkConf().setMaster("local[2]").setAppName("skylineCalculator")
    val sc = new SparkContext(conf)

    // Start timer
    val start = System.nanoTime

    // Call ALTOPK method in a specific dataset
    // new ALTOPK(inputPath="./datasets/dataset_10000_points_4_dimension_anticorrelated_distribution.csv", sc, k=2)
    new ALTOPK(inputPath="./datasets/toy_dataset_2d.csv", sc, k=3)

    // End timer
    val timeElapsed = System.nanoTime - start
    println("Total runtime: " + timeElapsed.asInstanceOf[Double] / 1000000000.0)

  }
}
