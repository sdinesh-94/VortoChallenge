# VortoChallenge - Vehicle Routing Problem

To solve the vehicle routing problem wherein we are given a list of trips and each trip contains the tripNumber and pair of vertices denoting start point and end point and we are asked to provide an optimal configuration of set of drivers {ranging 1 to trips size} and trips that each driver has to take such that the cost of trip is the most minimal.

Since this is NP-Hard problem where there does not exists yet a perfect solution that can be solved in polynomial time, we reduce the problem to finding an optimal cost solution based on the cost function provided. We see that the cost calculation is a linear function on the number of drivers and time taken for each assigned driver. We may assume the heuristics that num drivers is inversely proportional to the time taken for each driver (for example, more the number of drivers, less the time taken for each driver to complete assigned trips). Therefore the problem scape becomes finding the minima among neighbouring set of configuration cost values which is minimal.

go to src folder

```cd VortoChallenge/src```

To compile

```javac VortoChallenge/VehicleRoutingProblem.java```

To run

```java VortoChallenge/VehicleRoutingProblem pathtoProblemFile```
