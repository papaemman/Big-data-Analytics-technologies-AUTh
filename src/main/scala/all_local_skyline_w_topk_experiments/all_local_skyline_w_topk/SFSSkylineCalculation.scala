package all_local_skyline_w_topk_experiments.all_local_skyline_w_topk

import scala.collection.mutable.ArrayBuffer
import scala.util.control.Breaks.{break, breakable}

object SFSSkylineCalculation extends Serializable {

  // 1. addScoringFunction:
  // For every tuple (point in d-dimensional space) calculate a the sum of logs of its coordinates
  // This score is a monotone function.
  def addScoringFunction(array:Iterator[Array[Double]]): Iterator[(Array[Double], Double)] ={
    array.map(x => (x, 0))
      .map(x => {
        var sum = 0.0
        for (i<-0 to x._1.length - 1)
        {
          sum += math.log(x._1(i))
        }
        (x._1,sum)
      })
  }

  // 2. sortByScoringFunction: Sort based on score
  def sortByScoringFunction(iterator: Iterator[(Array[Double], Double)]):Iterator[(Array[Double], Double)]= {
    var array=iterator.toArray
    array.sortBy(x => - x._2)
    return array.toIterator
  }

  // 3. calculate: Calculate the skyline set based on array of sorted points

  // --> Algorithm:
  // The first point is inserted to a candidate list (i.e. arraybuffer)
  // The components of the list are compared with the rest of points (ie array)
  // If a point dominates one or more points of the list, that points are deleted.
  // If the point is not dominated by any point of the list, it is inserted in the list

  def calculate(x: Iterator[Array[Double]]): Iterator[Array[Double]] = {

    var arraybuffer = ArrayBuffer[Array[Double]]()
    val array = x.toArray
    arraybuffer += array(0)

    for (i<-1 to array.length - 1) // p1
    {
      var j=0
      var stopped = false
      breakable
      {
        while (j < arraybuffer.length) { // p2

          if (dominationCondition.isDominated(array(i), arraybuffer(j))) {
            arraybuffer.remove(j)
            j-=1
          }
          else if (dominationCondition.isDominated(arraybuffer(j), array(i))) {
            stopped = true
            break()
          }
          j += 1
        }
      }
      if(!stopped)
        arraybuffer+=array(i)
    }
    return arraybuffer.toIterator
  }

  // addScoreAndCalculate: Use the previously defined functions to get the skyline set
  // 1. addScoringFunction
  // 2. sortByScoringFunction
  // 3. calculate

  def addScoreAndCalculate(x: Iterator[Array[Double]]): Iterator[Array[Double]]={
    val y = addScoringFunction(x)
    val ysort = sortByScoringFunction(y)
    //ysort.toList.foreach(println)
    val result = calculate(ysort.map(x=>x._1))
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