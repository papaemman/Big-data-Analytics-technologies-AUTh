import org.apache.spark.{SparkConf, SparkContext}

println("***********************************************************************************************")
println("***********************************************************************************************")

println("Hi, this is the WordCount application for Spark.")

// Create spark configuration
val sparkConf = new SparkConf()
  .setMaster("local[2]")
  .setAppName("WordCount")

// Create spark context
val sc = new SparkContext(sparkConf)  // create spark context

val textFile = sc.textFile(path= "file:///home/user7/Projects/MSc/Big-data-Analytics-technologies-AUTh/datasets/leonardo.txt")
val counts = textFile.flatMap(line => line.split(" "))
  .map(word => (word, 1))
  .reduceByKey(_ + _)


println(counts)

counts.saveAsTextFile(path= "file:///home/user7/Projects/MSc/Big-data-Analytics-technologies-AUTh/output/word_count.txt")
