// HOW TO CHECK IF A POINT IS DOMINATED

// We say that a point p dominates another point q,
// when p is as good as q in all dimensions and
// it is strictly better in at least one dimension.

// "Better" = minimum in this case


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

// In order for a domination relation to occur, must be
// isSmaller(x,y) = true & isSmallerEqual(x,y) = true
def isDominated(x: Array[Double], y:Array[Double]): Boolean = {
  return isSmaller(x,y) & isSmallerEqual(x,y)}

// TEST

val x = Array(4.0,7.0,0.0,2.0)
val y = Array(5.0,2.0,1.0,0.0)
val z = Array(3.0,1.0,5.0,1.0)
val m = Array(5.0,2.0,6.0,2.0)
val n = Array(4.0,1.0,0.0,0.0)
val l = Array(0.0,3.0,2.0,1.0)

isDominated(l,y)
isDominated(y,l)
isDominated(z,x)
isDominated(x,z)
isDominated(m,n)
isDominated(n,m)


// TEST 2
val x = Array(1.0,2.0,3.0)
val y = Array(2.0,1.0,4.0)
val z = Array(0.0,5.0,5.0)
val m = Array(0.0,1.0,3.0)

isDominated(x,y)
isDominated(y,z)
isDominated(x,y)
isDominated(m,y)

