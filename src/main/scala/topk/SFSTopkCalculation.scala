package topk

import scala.collection.mutable.ArrayBuffer
import scala.util.control.Breaks.{break, breakable}

object SFSTopkCalculation extends Serializable {

  //  ==== TOPK ======


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




  // -------------------------------------------------------------------- //

  // calculatePartition:
  // calculate final Skyline set based on each partition's skyline set

  def calculatePartition(previousSkylines: ArrayBuffer[Array[Double]], enteredPartition: Iterator[Array[Double]]): Iterator[Array[Double]]= {

    var wasEmpty=false
    val array = enteredPartition.toArray

    if(previousSkylines.length==0){
      previousSkylines += array(0)
      wasEmpty=true
    }

    // For every skyline point of the enteredPartition,
    // check if it is dominated by or if it is dominate any other previous skyline point from other partitions

    for (i <- 0 to array.length - 1) {
      var j = 0
      var breaked = false
      breakable {
        while (j < previousSkylines.length) {
          if (dominationCondition.isDominated(array(i), previousSkylines(j))) {
            previousSkylines.remove(j)
            j -= 1
          }
          else if (dominationCondition.isDominated(previousSkylines(j), array(i))) {
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
        previousSkylines += array(i)
      }
    }
    return previousSkylines.toIterator
  }

}