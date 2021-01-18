package topk

object dominationCondition extends Serializable {

  // Check if a point x is dominated by a point y
  def isDominated(x: Array[Double], y:Array[Double]): Boolean = {
    return isSmaller(x,y) & isSmallerEqual(x,y)}

  // isSmaller:
  // returns true if x(i) < y(i) for any i
  def isSmaller(x: Array[Double], y:Array[Double]):Boolean = {
    val size = x.length
    var flag = false
    var i = 0
    for (i <- 0 to size - 1) {
      if (x(i) < y(i))
        flag = true
    }
    return flag}

  // isSmallerEqual:
  // returns false if x(i) > y(i) for any i
  def isSmallerEqual(x: Array[Double], y:Array[Double]):Boolean = {
    val size = x.length
    var flag = true
    var i = 0
    for (i <- 0 to size - 1) {
      if (x(i) > y(i))
        flag = false}
    return flag
  }
}