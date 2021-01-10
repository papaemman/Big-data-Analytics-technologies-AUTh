package skyline

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext

object skyline {
  def main(args: Array[String]): Unit = {

    val conf = new SparkConf().setMaster("local").setAppName("skylineCalculator")
    val sc = new SparkContext(conf)
    val start = System.nanoTime
    new ALS("./datasets/dataset_10000_points_4_dimension_anticorrelated_distribution.csv", sc)
    //println(System.getProperty("user.dir"))
    val timeElapsed = System.nanoTime - start
    println("Total runtime: " + timeElapsed.asInstanceOf[Double] / 1000000000.0)

  }
}
