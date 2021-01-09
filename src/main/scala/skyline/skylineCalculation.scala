package skyline

object skylineCalculation extends Serializable {
  def calculate(x: Iterator[Array[Double]]): Iterator[Array[Double]] = {
    var tempList = x.toList
    var i = 0
    var listLength = tempList.length
    while (i < listLength - 1) {
      var k = i + 1
      while (k < listLength) {
        if (dominationCondition.isDominated(tempList(i),tempList(k))) {
          tempList = tempList.take(k) ++ tempList.drop(k + 1)
          k = k - 1
          listLength = listLength - 1
        }
        else if (dominationCondition.isDominated(tempList(k),tempList(i))) {
          tempList = tempList.take(i) ++ tempList.drop(i + 1)
          listLength = listLength - 1
          i = i - 1
          k = listLength
        }
        k = k + 1
      }
      i = i + 1
    }
    return tempList.toIterator
  }
}