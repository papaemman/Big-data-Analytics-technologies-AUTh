package working_with_df
import org.apache.spark.sql.SparkSession


object working_with_df {

    def main(args: Array[String]): Unit = {

      // ---- Starting Point: SparkSession -----
      val spark = SparkSession
        .builder()
        .master("local")
        .appName("Spark SQL basic example")
        .getOrCreate()

      // For implicit conversions like converting RDDs to DataFrames

      // get the current directory
      val currentDir = "/home/user7/Projects/MSc/Big-data-Analytics-technologies-AUTh"

      val df = spark.read.format(source="csv")
        .option("sep", ",")
        .option("inferSchema", "true")
        .option("header", "true")
        .load(path = "file://" + currentDir + "/datasets/dataset_10000_points_2_dimension_anticorrelated_distribution.csv")

      // Show the first few lines of the DataFrame
      df.show()

      // Print the schema of the DataFrame
      df.printSchema()

      //

      // df.mapPartitions(SFSSkylineCalculation.addScoreAndCalculate)


    }
}
