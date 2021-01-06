package word_count_scala

/* WordCount.scala */
  
import org.apache.spark.{SparkConf, SparkContext}

object WordCount {

  def main(args: Array[String]): Unit = {
    
    println("***********************************************************************************************")
    println("***********************************************************************************************")
    
    println("Hi, this is the WordCount application for Spark.")
   
    // Create spark configuration
    val sparkConf = new SparkConf()
      .setMaster("local[2]")
      .setAppName("WordCount")

    // Create spark context  
    val sc = new SparkContext(sparkConf)  // create spark context
    
    val currentDir = System.getProperty("user.dir")  // get the current directory
    val inputFile = "file://" + currentDir + "/datasets/leonardo.txt"
    //val outputDir = "file://" + currentDir + "/output"

    println("reading from input file: " + inputFile)

    val txtFile = sc.textFile(inputFile)
    
    //println("folder used for output: " + outputDir)

    val result = txtFile.flatMap(line => line.split(" ")) // split each line based on spaces
      .map(word => (word,1)) // map each word into a word,1 pair
      .reduceByKey(_+_) // reduce 
      .foreach(println)
      //.collect()
      //.saveAsTextFile(outputDir) // save the output

    sc.stop()

    println("***********************************************************************************************")
    println("***********************************************************************************************") 
    
  }
}


