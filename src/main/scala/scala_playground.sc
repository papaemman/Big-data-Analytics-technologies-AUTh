import shapeless.Path
// -------------------------------------------------- //
print("HERE")

val str = "test"
// Print simple text
println("Scala test")

// Simple variables
val x = 2
x+2
println(x)

// Lists
val args = List("test",1, 1, 3 ,5)
println(args)

args(0)
args(1)
args(2).toString
args(3)
println("List:" + args(1))


// Loops
val a1=Array(1.0,2.0,3.0)
for(i <- 0 until a1.length){
  println("i is: " + i)
  println("i'th element is: " + a1(i))
}


// Write in file
import java.io.FileWriter

val filename = "/home/user7/Projects/MSc/Big-data-Analytics-technologies-AUTh/docs/experiment_times.txt"
val fw = new FileWriter(filename,true) //the true will append the new data
fw.write("add a line\n") //appends the string to the file
fw.close

print("END")
