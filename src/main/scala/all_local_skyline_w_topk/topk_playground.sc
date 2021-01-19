import org.apache.spark.{SparkConf, SparkContext}
import topk.{SFSTopkCalculation, dominationCondition}

//  ==== TOPK ======

// ----- HELPER FUNCTIONS ------

// 1. dominatedScoreCompute:
// For every tuple (point in d-dimensional space) calculate the domination score

def dominatedScoreCompute(x: Iterator[Array[Double]]): Iterator[(Array[Double], Int)] = {

  var arraybuffer = x.toArray.map(x => (x,0))

  for (p<-1 to arraybuffer.length-1) {
    var dominationScore = 0

    for (q <- 1 to arraybuffer.length-1) {
      if (dominationCondition.isDominated(arraybuffer(q)._1, arraybuffer(p)._1)) {
        dominationScore += 1
      }
    }
  }
  return arraybuffer.toIterator
}

// 2. sortByDominatingFunction: Sort based on score
def sortByDominatingFunction(iterator: Iterator[(Array[Double], Int)]):Iterator[(Array[Double], Int)]= {
  var array=iterator.toArray
  array.sortBy(x => - x._2)
  return array.toIterator

}

// returnTopK: Use the previously defined functions to get the skyline set
// 1. dominatedScoreCompute
// 2. sortByDominatingFunction

def addDominanceScoreAndCalculate(x: Iterator[Array[Double]]): Iterator[Array[Double]]={
  val y = dominatedScoreCompute(x)
  val ysort = sortByDominatingFunction(y)
  //ysort.toList.foreach(println)
  val result = ysort.map(x=>x._1).take(2)
  return result
}

println("***********************************************************************************************")
println("This is the top-k query application for Spark.")
println("***********************************************************************************************")

// Define spark configuration and sparc contect
val conf = new SparkConf().setMaster("local").setAppName("skylineCalculator")
val sc = new SparkContext(conf)

// Start timer
val start = System.nanoTime

val rdd = sc.textFile("/home/user7/Projects/MSc/Big-data-Analytics-technologies-AUTh//datasets/dataset_10000_points_4_dimension_anticorrelated_distribution.csv")

// Prepare dataset
// 1. split on ","
// 2. convert to Double
// 3. perform parallel skyline calculation for each partition

val rdd2 = rdd
  .map(x => x.split(","))
  .map(x => x.map(y => y.toDouble))
  .mapPartitions(SFSTopkCalculation.addDominanceScoreAndCalculate)

println("HEREEEEEEEEEEEEEEEE")


val temp = rdd2.collect()
println(temp)

// End timer
val timeElapsed = System.nanoTime - start
println("Total runtime: " + timeElapsed.asInstanceOf[Double] / 1000000000.0)