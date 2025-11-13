package com.example;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Main {
    static class Edge implements Comparable<Edge> {
        int u, v;
        int w;
        Edge(int u, int v, int w) { this.u = u; this.v = v; this.w = w; }
        public int compareTo(Edge o) { return Integer.compare(this.w, o.w); }
        public String toString() { return String.format("(%d - %d : %d)", u, v, w); }
    }

    static class DSU {
        int[] p; int[] r;
        DSU(int n) { p = new int[n+1]; r = new int[n+1]; for (int i=1;i<=n;i++) p[i]=i; }
        int find(int a){ return p[a]==a? a : (p[a]=find(p[a])); }
        boolean unite(int a, int b){ a=find(a); b=find(b); if(a==b) return false; if(r[a]<r[b]) p[a]=b; else if(r[b]<r[a]) p[b]=a; else { p[b]=a; r[a]++; } return true; }
    }

    public static void main(String[] args) throws Exception {
        String filename = (args.length>0)? args[0] : "input.txt";
        List<String> lines = Files.readAllLines(Path.of(filename));
        String[] header = lines.get(0).trim().split("\\s+");
        int n = Integer.parseInt(header[0]);
        int m = Integer.parseInt(header[1]);
        List<Edge> edges = new ArrayList<>();
        for (int i=1;i<=m;i++){
            String[] t = lines.get(i).trim().split("\\s+");
            int u = Integer.parseInt(t[0]);
            int v = Integer.parseInt(t[1]);
            int w = Integer.parseInt(t[2]);
            edges.add(new Edge(u,v,w));
        }

        System.out.println("All edges in graph:");
        for (Edge e: edges) System.out.println(e);
        System.out.println();

        // Build MST with Kruskal
        List<Edge> sorted = new ArrayList<>(edges);
        Collections.sort(sorted);
        DSU dsu = new DSU(n);
        List<Edge> mst = new ArrayList<>();
        int total = 0;
        for (Edge e: sorted){
            if (dsu.unite(e.u,e.v)) { mst.add(e); total+=e.w; }
        }

        System.out.println("MST edges before removal:");
        for (Edge e: mst) System.out.println(e);
        System.out.println("MST total weight: " + total);
        System.out.println();

        // Remove one edge from MST — choose maximum weight edge in MST
        if (mst.isEmpty()) { System.out.println("MST is empty — nothing to remove."); return; }
        Edge removed = mst.get(0);
        for (Edge e: mst) if (e.w > removed.w) removed = e;
        System.out.println("Removed edge from MST: " + removed);
        List<Edge> mstAfterRemoval = new ArrayList<>(mst);
        mstAfterRemoval.remove(removed);

        // Build adjacency of the remaining tree (forest)
        List<List<Integer>> adj = new ArrayList<>();
        for (int i=0;i<=n;i++) adj.add(new ArrayList<>());
        for (Edge e: mstAfterRemoval){ adj.get(e.u).add(e.v); adj.get(e.v).add(e.u); }

        // Find components
        boolean[] vis = new boolean[n+1];
        List<List<Integer>> comps = new ArrayList<>();
        for (int i=1;i<=n;i++) if (!vis[i]){
            List<Integer> comp = new ArrayList<>();
            Deque<Integer> dq = new ArrayDeque<>(); dq.add(i); vis[i]=true;
            while(!dq.isEmpty()){
                int cur = dq.poll(); comp.add(cur);
                for (int to: adj.get(cur)) if (!vis[to]){ vis[to]=true; dq.add(to); }
            }
            comps.add(comp);
        }

        System.out.println("Components after removal:");
        for (int i=0;i<comps.size();i++) System.out.println("Component " + (i+1) + ": " + comps.get(i));
        System.out.println();

        // Find replacement edge: minimal weight edge connecting two different components
        // We'll determine component id for each vertex
        int[] compId = new int[n+1];
        for (int i=0;i<comps.size();i++) for (int v: comps.get(i)) compId[v]=i;

        Edge replacement = null;
        for (Edge e: edges) {
            if (e.u==removed.u && e.v==removed.v && e.w==removed.w) continue; // skip exactly removed
            // skip edges that are currently in mstAfterRemoval
            boolean inForest = false; for (Edge f: mstAfterRemoval) if ( ((f.u==e.u && f.v==e.v)||(f.u==e.v && f.v==e.u)) && f.w==e.w) { inForest=true; break; }
            if (inForest) continue;
            if (compId[e.u] != compId[e.v]) {
                if (replacement==null || e.w < replacement.w) replacement = e;
            }
        }

        if (replacement==null){
            System.out.println("No replacement edge found: graph becomes disconnected.");
            return;
        }

        System.out.println("Replacement edge found: " + replacement);
        // Add replacement to form new MST
        List<Edge> newMst = new ArrayList<>(mstAfterRemoval);
        newMst.add(replacement);
        int newTotal = 0; for (Edge e: newMst) newTotal+=e.w;
        System.out.println("New MST edges:"); for (Edge e: newMst) System.out.println(e);
        System.out.println("New MST total weight: " + newTotal);
    }
}
