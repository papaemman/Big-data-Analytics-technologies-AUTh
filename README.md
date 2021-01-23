# M.Sc. Data and Web Science 2020-2021

![MSC-LOGO](./assets/dws-logo.png)

## Team members
* Panagiotis Papaemmanouil, 56
* Theodoros Konstantinidis, 64

## Course: Technologies for Big Data Analytics

**Assignment:** Scalable Processing of Dominance-Based Queries 
An implementation of efficient skyline computation algorithms.

[Assignment](./docs/bigdata-project-2020.pdf)

[Report](./docs/report.pdf)

---

##  Technology Stack

![test](./assets/Scala-logo.png)

**Scala Documentation 3.0.1:** https://docs.scala-lang.org/

![Spark](./assets/Apache_Spark_logo.png)

**Spark Documentation 3.0.1:** https://spark.apache.org/docs/latest/index.html

For more Info on versions and libraries go to [build.sbt](build.sbt) file.

* **Development Environment:** Intellij IDEA (JetBrains)


---

## Solution 

Based on [Bibliography](docs/bibliography) that can be found here ("docs/bibliography") we decided
to use the following architecture.

* **ALS**: All local skyline

(pic)

* **SKFSS skyline calculation**

More on this can be found in [Final Report](docs/report).

---

## Datasets

Note: In order to Run the application you have to create the datasets

Go to `data_gen/` and run the *Data-generation.ipynb* notebook.

**Datasets Degrees of Freedom**: (Distribution) x (Number of points) x (Dimensions)

* Distribution: uniform, normal, correlated, anticorrelated

* Number of points: 10.000, 100.000, 1.000.000

* Dimensions: 2, 4, 10, 50

**Total datasets:** 4 x 3 x 4 = 48