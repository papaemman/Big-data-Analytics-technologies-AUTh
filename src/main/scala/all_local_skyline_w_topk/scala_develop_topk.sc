import topk.dominationCondition
// ---- Top-k Query ----- //
// Return the top-k (k=2) points


// ---------------------------------------------------------------------------- //

// DEFINE HELPER FUNCTIONS //


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

// Check if a point x is dominated by a point y
def isDominated(x: Array[Double], y:Array[Double]): Boolean = {
  return isSmaller(x,y) & isSmallerEqual(x,y)}

// ---------------------------------------------------------------------------- //
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

// Define a toy dataset
val rdd = Array((4.0,7.0),
                (1.0,7.0),
                (2.0,2.0),
                (3.0,2.0),
                (1.0,2.0),
                (1.0,4.0),
                (2.0,5.0))


// Check the toy dataset
rdd

val x = rdd(0).toString().toArray.map(x => x.toDouble)
println(x)

// Calculate the dominance score of each point

var domination_array = Array(0,0,0,0,0,0,0)

for (i<-0 until rdd.length){

  val x = rdd(i).toString().toArray.map(x => x.toDouble)
  var domination = 0

  for (j<-0 until rdd.length-1){

    if(isDominated(x, rdd(j).toString().toArray.map(x => x.toDouble))){
      domination +=1
    }
  }
  println(domination)
  domination_array(i) = domination
}

domination_array


val rdd_temp = Iterator(Array(1.0,2.0,3.1,5.0))

val res = dominatedScoreCompute(rdd_temp)
println(res)

