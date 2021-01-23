import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

// Initialize Spark application //
val sparkConf = new SparkConf()
  .setMaster("local[2]")
  .setAppName("skylineCalculator")

val sc = new SparkContext(sparkConf)


// Create an RDD object
val data = Array(1, 2, 3, 4, 5)

val distData = sc.parallelize(data) // RDD from Array object
println("RDD object has been created")

// Apply Transformations and Actions to RDD
val total_elements = distData.count()
println("Total elements of RDD: "+total_elements)


// Write RDD
distData.map(a=> {
  val line = a.toString
  line
}).saveAsTextFile(path = "/home/user7/Projects/MSc/Big-data-Analytics-technologies-AUTh/results_temp")

//val myRdd.map{case(a, b) =>
//  var line = a.toString + "," + b.toString
//  line
//}.saveAsTextFile("path");

println("END")