package skyline

import scala.collection.mutable.ArrayBuffer
import scala.util.control.Breaks.{break, breakable}

object SFSSkylineCalculation extends Serializable {
  def calculate(x: Iterator[Array[Double]]): Iterator[Array[Double]] = {
    var arraybuffer = ArrayBuffer[Array[Double]]()
    val array = x.toArray
    arraybuffer += array(0)
    for (i<-1 to array.length - 1)
    {
      var j=0
      var breaked = false
      breakable
      {
        while (j < arraybuffer.length) {
          if (dominationCondition.isDominated(array(i), arraybuffer(j))) {
            arraybuffer.remove(j)
            j-=1
          }
          else if (dominationCondition.isDominated(arraybuffer(j), array(i))) {
            breaked = true
            break()
          }
          j += 1
        }
      }
      if(!breaked)
        arraybuffer+=array(i)
    }
    return arraybuffer.toIterator
  }
  def addScoreAndCalculate(x: Iterator[Array[Double]]):Iterator[Array[Double]]={
    val y = addScoringFunction(x)
    val ysort = sortByScoringFunction(y)
    val result = calculate(ysort.map(x=>x._1))
    return result
  }
  def calculatePartition(previousSkylines: ArrayBuffer[Array[Double]], enteredPartition: Iterator[Array[Double]]): Iterator[Array[Double]]= {
    var wasEmpty=false
    val array = enteredPartition.toArray
    if(previousSkylines.length==0){
      previousSkylines += array(0)
      wasEmpty=true
    }
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
  def sortByScoringFunction(iterator: Iterator[(Array[Double], Double)]):Iterator[(Array[Double], Double)]=
  {
    var array=iterator.toArray
    array.sortBy(x => - x._2)
    return array.toIterator
  }
  def addScoringFunction(array:Iterator[Array[Double]]): Iterator[(Array[Double], Double)] ={
    array.map(x => (x, 0))
      .map(x => {
        var sum =0.0
        for (i<-0 to x._1.length - 1)
        {
          sum += math.log(x._1(i)+1)
        }
        (x._1,sum)
      })
  }
}