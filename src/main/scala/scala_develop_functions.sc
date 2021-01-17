// Test functions

// addScoringFunction:
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

// Define an Array of tuples
// val a1 = Array((1.0,1.0,1.0), (1.0,2.0,3.0), (3.0,2.0,1.0))
val a1 = Array(1.0, 1.5, 2.0, 3.0)
a1.length

val a1_iter = Iterator(a1)

var res = addScoringFunction(a1_iter)

res foreach println

// How addScoringFunction() function works?
// For every tuple calculates a score: the Sum of logs of the coordinates:
// log(1) + log(1.5) + log(2.0) + log(3.0)
// Use this score to sort points




