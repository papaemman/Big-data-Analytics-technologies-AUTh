# TODO and Notes


---

ALS
A baseline approach proposes the horizontal partitioning of the dataset into chunks
and locally calculating the skyline points of each chunk. The results are then collected by
the coordinator which calculates the final skyline tuples. This approach, called all local
skylines (ALS) [16] does not guarantee that the local skylines are few enough to fit and
be processed in main memory. Additionally, the algorithm calculates and transfers all the
local skylines, without using smart methods to distinguish those that are dominated by
tuples of another partition. This leads to expensive bandwidth consumption. The effectiveness of this approach depends on the local and centralized skyline algorithms used.

SFSkylineComputation (SFS: Sort Filter Skyline)
SFSkylineComputation, is the implementation of Ilaria Bartolini’s algorithm [29];
It first sorts the dataset in ascending order according to a monotone preference function.
The first point is inserted to a candidate list
and the components of the list are compared with the rest of points. If a point dominates
one or more points of the list, that points are deleted. If the point is not dominated by any
point of the list, it is inserted in the list

Spark RDD persistence is an optimization technique in which saves the result of RDD evaluation. Using this we save the intermediate result so that we can use it further if required. It reduces the computation overhead.

---

The top-k dominating query returns k data objects which dominate the highest number of objects in a
dataset. This query is an important tool for decision support
since it provides data analysts an intuitive way for finding
significant objects.


TOP-K belongs to k-skyband . It only needs to iterate through the k-skyband objects of each partition without
traversing all the objects of the partition, avoiding the calculation of dominance of other unrelated objects, which
greatly improving the efficiency of the query. Divide the data set into each partition node and execute each algorithm
separately. The candidate set is obtained by screening the original data set, reducing the comparison between
unnecessary data sets and speeding up the Top-k dominating query.



---

**Members**   

P: Panagiotis   
T: Thodoris

---

Tasks 

- [X] Setup Development environment: Scala, Spark, Git repo (P, T)
- [ ] Update README with Instructions of How to setup dev environment, project details, results, report etc (P, T)
- [X] Identify and assign tasks in team members (P, T)
- [X] Create datasets in python, save datasets as csv files and upload them in Google Drive (T)

Datasets creation: Distribution X Number of points X Dimensions

1. 4 datasets of (1000,2) dimensions with correlated, uniform, normal, anticorrelated (+Plots)
2. 4 datasets of (1000,5) dimensions with correlated, uniform, normal, anticorrelated
3. 4 datasets of (1000,10) dimensions with correlated, uniform, normal, anticorrelated
4. 4 datasets of (10000,2) dimensions with correlated, uniform, normal, anticorrelated (+Plots)
5. 4 datasets of (10000,5) dimensions with correlated, uniform, normal, anticorrelated
6. 4 datasets of (10000,10) dimensions with correlated, uniform, normal, anticorrelated

- [ ] Search for a suitable Real Estate dataset from UCI repository or kaggle (P)
- [ ] Solve and Review Task 1
- [ ] Solve and Review Task 2
- [ ] Solve and Review Task 3
- [ ] Experimentation process (different number of cores, dataset dimensions, distributions, etc)
- [ ] Create tables, plots to describe experimentation results
- [ ] Write report
- [ ] Prepare presentation
- [ ] Presentation practice
- [ ] Submit assignment

**The END**


Remove sampling rate

1. Write report
2. Evaluate results 
3. Why sum = math.log()