# Minimum Spanning Tree (MST) Edge Replacement Project
# Student: Zarina Kossaman
# Group: SE-2429

## 1. Overview

This project implements a **Minimum Spanning Tree (MST)** construction algorithm and demonstrates a procedure for maintaining the MST after the removal of one of its edges. The implementation is written in **Java (JDK 17+)** and requires no external dependencies. The primary objective is to identify a valid replacement edge that reconnects the graph while preserving minimal total weight.

The project was developed to satisfy the course requirements related to graph algorithms, MST maintenance, and efficient reconnection strategies.


## 2. Objectives

The assignment required the program to:

1. **Construct the MST** for a given weighted undirected graph.
2. **Display the MST edges** before modification.
3. **Remove a single edge** from the MST.
4. **Determine the connected components** formed after the removal.
5. **Identify the optimal replacement edge** that reconnects these components while maintaining minimum cost.
6. **Display the updated MST**, including the newly added edge.
7. Provide clear instructions so the project can be executed immediately after cloning or downloading.

## 3. Implementation Summary

The program uses:

* **Kruskal’s Algorithm** to compute the initial MST.
* **Disjoint Set Union** for efficient cycle detection.
* **Depth-first traversal** to determine the resulting connected components once an MST edge is removed.
* A linear scan across all original graph edges to identify the minimum‑weight edge reconnecting the separated components.

The edge selected for removal is, by default, the **maximum‑weight edge** in the MST. This choice follows the typical demonstration approach, but it can be easily modified.

All computations operate in polynomial time, and the program is suitable for medium-scale academic test graphs.


## 4. Project Structure

* **pom.xml** – Maven configuration file.
* **Main.java** – Core program logic (graph parsing, MST computation, edge removal, replacement search).
* **input.txt** – Sample input graph.
* **SAMPLE_OUTPUT.txt** – Example program output.


## 5. Input Format

The input graph must follow the structure:

```
n m
u1 v1 w1
u2 v2 w2
...
```

Where:

* `n` – number of vertices,
* `m` – number of edges,
* `ui vi wi` – an undirected edge between vertices `ui` and `vi` with weight `wi`.

Vertices are assumed to be numbered from **1 to n**.


## 6. Program Output

The program prints:

1. All edges in the input graph.
2. The edges of the MST and its total weight.
3. The edge removed from the MST.
4. The connected components after the removal.
5. The selected replacement edge.
6. The updated MST and its total weight.

Example output is available in **SAMPLE_OUTPUT.txt**.
