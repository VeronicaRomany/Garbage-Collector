import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class MarkAndSweep {

    void DFSUtil(int v, boolean visited[] , ArrayList<HeapObject> objects ,Graph pointers ) {
        // Mark the current node as visited and print it
        visited[v] = true;
        System.out.print(v + " ");
        objects.get(v).setMarked(true);


        // Recur for all the vertices adjacent to this
        // vertex
        Iterator<Integer> i = pointers.adj[v].listIterator();
        while (i.hasNext()) {
            int n = i.next();
            if (!visited[n])
                DFSUtil(n, visited , objects,pointers);
        }
    }

    public void Mark(ArrayList<HeapObject> objects, Graph pointers, ArrayList<Integer> roots, HashMap<Integer, Integer> heap) {

        boolean visited[] = new boolean[pointers.getV()];
        // Call the recursive helper
        // function to print DFS
        // traversal
        for (int i = 0; i < roots.size(); i++) {

            DFSUtil(heap.get(roots.get(i)), visited ,objects, pointers);

        }
        for(int i=0 ; i<objects.size();i++){
            System.out.println(objects.get(i).getId() + " " + objects.get(i).isMarked());
        }
    }
}