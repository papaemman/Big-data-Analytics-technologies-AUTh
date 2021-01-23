import topk.dominationCondition

val a1=Array(Array(4.0,7.0), Array(5.0,2.0),
             Array(3.0,1.0), Array(4.0,1.0),
             Array(1.0,3.0), Array(2.0,4.0))


var arraybuffer = a1.toArray.map(x => (x,0))
println(arraybuffer.length)

val res2 = arraybuffer.map( x => {
  var dominationScore:Int = 0
  for (q <- 0 until arraybuffer.length-1) {
    if (dominationCondition.isDominated(x._1, arraybuffer(q)._1)) {
      println(arraybuffer(q)._1)
      dominationScore += 1
    }
  }
  (x._1, dominationScore)})

//res2.foreach(println)

print("Changed")
var res3 = res2.sortBy(x => - x._2)

res3.take(1)



// Bug
println("HERE")
var dominationScore = 0
for (q <- 0 until arraybuffer.length) {
  println((arraybuffer(q)._1).mkString(","))
  if (dominationCondition.isDominated(Array(1.0,3.0), arraybuffer(q)._1)) {

    dominationScore += 1
  }}

println("Final dominance score: " + dominationScore)

dominationCondition.isDominated(Array(1.0,3.0), Array(4.0,7.0))
dominationCondition.isDominated(Array(1.0,3.0), Array(2.0,4.0))
