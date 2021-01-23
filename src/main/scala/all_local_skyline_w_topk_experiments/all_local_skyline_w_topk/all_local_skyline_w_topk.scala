package all_local_skyline_w_topk_experiments.all_local_skyline_w_topk
import java.io.File
import org.apache.spark.{SparkConf, SparkContext}

object all_local_skyline_w_topk {
  def main(args: Array[String]): Unit = {

    println("***********************************************************************************************")
    println("This is all local skyline with top-k application for Spark.")
    println("***********************************************************************************************")

    def getListOfFiles(dir: String): List[String] = {
      val file = new File(dir)
      file.listFiles.filter(_.isFile)
        .filter(_.getName.startsWith("dataset_10000"))
        .map(_.getPath).toList
    }


    val datapaths_ls = getListOfFiles("./datasets")
    println("Total datapaths:" + datapaths_ls.length)
    // val datapaths_ls = List("toy_dataset_2d.csv", "toy_dataset_4d.csv")
    val cores_ls = List(1,2,4,8)
    val k_ls = List(1,10,50,100)


    for(datapath <- datapaths_ls){
      for(cores <- cores_ls){
        for(k <- k_ls){

          println("Experiment |" + " Datapath:" + datapath + " Cores:" + cores + " k:" + k)

          // Define Spark configuration and spark context
          val conf = new SparkConf().setMaster("local[" + cores + "]").setAppName("skylineCalculator")
          val sc = new SparkContext(conf)

          // Start timer
          val start = System.nanoTime

          // Call ALTOPK method in a specific dataset
          new ALTOPK(inputPath=datapath, sc=sc, k=k, cores=cores)
          // new ALTOPK(inputPath="./datasets/toy_dataset_2d.csv", sc=sc, k=k)

          // End timer
          val timeElapsed = System.nanoTime - start
          println("Total runtime: " + timeElapsed.asInstanceOf[Double] / 1000000000.0)
          sc.stop()
          println("-----------------------------------------------------------------------")
        }
      }
    }



  }
}
