
import java.io.*;
import java.util.*;

class Graph {
    private int V; // No. of vertices
     LinkedList<Integer> adj[];     // Array of lists for Adjacency List Representation


    @SuppressWarnings("unchecked") Graph(int v) {
        V = v;
        adj = new LinkedList[v];
        for (int i = 0; i < v; ++i)
            adj[i] = new LinkedList();
    }

    public int getV() {
        return V;
    }
    void addEdge(int v, int w) {
        adj[v].add(w);
    }

    // A function used by DFS
    void DFSUtil(int v, boolean visited[]) {
        visited[v] = true;  // Mark the current node as visited

        Iterator<Integer> i = adj[v].listIterator();
        while (i.hasNext()) {
            int n = i.next();
            if (!visited[n])
                DFSUtil(n, visited);
        }
    }

    // The function to do DFS traversal.
    void DFS(int v) {
        boolean visited[] = new boolean[V];
        DFSUtil(v, visited);
    }
}
