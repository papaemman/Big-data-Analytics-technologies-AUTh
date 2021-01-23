package all_local_skyline_w_topk

import scala.collection.mutable.ArrayBuffer
import scala.util.control.Breaks.{break, breakable}

object SFSTopkCalculation extends Serializable {

  //  ==== TOPK ======


  // 1. dominatedScoreCompute:
  // For every tuple (point in d-dimensional space) calculate the domination score

  def dominatedScoreCompute(x: Iterator[Array[Double]]): Iterator[(Array[Double], Int)] = {

    var arraybuffer = x.toArray.map(x => (x,0))

    arraybuffer = arraybuffer.map(x => {
      var dominationScore = 0
      for (q <- 0 until arraybuffer.length) {
        if (dominationCondition.isDominated(x._1, arraybuffer(q)._1)) {
          dominationScore += 1
        }
      }
      (x._1, dominationScore)})

    // Check domination score for each point
    // arraybuffer.foreach(x => println("Point: " + x._1.mkString(",") + " Domination score: " + x._2))

    return arraybuffer.toIterator
  }

  // 2. sortByDominatingFunction: Sort based on score
  def sortByDominatingScore(iterator: Iterator[(Array[Double], Int)]):Iterator[(Array[Double], Int)]= {
    var array=iterator.toArray
    var array_sorted = array.sortBy(x => -x._2)

    // Check sorting
    // array_sorted.foreach(x => println("Point: " + x._1.mkString(",") + " Domination score: " + x._2))

    return array_sorted.toIterator

  }

  // returnTopK: Use the previously defined functions to get the skyline set
  // 1. dominatedScoreCompute
  // 2. sortByDominatingFunction

  def addDominanceScoreAndCalculate(x: Iterator[Array[Double]], k:Int): Iterator[Array[Double]]={

    // Calculate domination score
    val y = dominatedScoreCompute(x)

    // Sort based on domination score
    val ysort = sortByDominatingScore(y)

    // println("Sorted Array")
    // ysort.foreach(x => println("Point: " + x._1.mkString(",") + " Domination score: " + x._2))

    // Return top k points
    val result = ysort.map(x=>x._1).take(k)
    return result
  }


  def existsIn(x: Array[Double], y: Array[Array[Double]]): Boolean={
    for(i <- 0 to y.length - 1) {
      if (x.toList.equals(y(i).toList)) {
        return true
      }
    }
    return false
  }

  // -------------------------------------------------------------------- //

  // calculatePartition:
  // calculate final topk set based on each partition's skyline set

  def calculatePartition(previousTopk: ArrayBuffer[Array[Double]], enteredPartition: Iterator[Array[Double]]): Iterator[Array[Double]]= {

    var wasEmpty=false
    val array = enteredPartition.toArray

    if(previousTopk.length==0){
      previousTopk += array(0)
      wasEmpty=true
    }

    // For every skyline point of the enteredPartition,
    // check if it is dominated by or if it is dominate any other previous skyline point from other partitions

    for (i <- 0 to array.length - 1) {
      var j = 0
      var breaked = false
      breakable {
        while (j < previousTopk.length) {
          if (dominationCondition.isDominated(array(i), previousTopk(j))) {
            previousTopk.remove(j)
            j -= 1
          }
          else if (dominationCondition.isDominated(previousTopk(j), array(i))) {
            breaked = true
            break()
          }
          if(wasEmpty & i==0)
          {
            breaked=true
            break()
          }
          j += 1
        }
      }
      if (!breaked) {
        previousTopk += array(i)
      }
    }
    return previousTopk.toIterator
  }

}


