package skyline

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._

object skyline {
  def main(args: Array[String]): Unit = {

    val conf = new SparkConf().setMaster("local").setAppName("skylineCalculator")
    val sc = new SparkContext(conf)
    val now = System.nanoTime
    new ALS("./datasets/dataset_10000_points_4_dimension_anticorrelated_distribution.csv", sc)
    //println(System.getProperty("user.dir"))
    val timeElapsed = System.nanoTime - now
    println("total time elapsed: " + timeElapsed.asInstanceOf[Double] / 1000000000.0)

  }
}
