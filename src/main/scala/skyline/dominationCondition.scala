package skyline

object dominationCondition extends Serializable {
  def isDominated(x: Array[Double], y:Array[Double]): Boolean = {
    return isSmaller(x,y) & isSmallerEqual(x,y)}
  def isSmaller(x: Array[Double], y:Array[Double]):Boolean = {
    val size = x.length
    var flag = false
    var i = 0
    for (i <- 0 to size - 1) {
      if (x(i) < y(i))
        flag = true}
    return flag}
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