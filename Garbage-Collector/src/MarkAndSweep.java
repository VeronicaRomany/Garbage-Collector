import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class MarkAndSweep {

    void DFSUtil(int v, boolean visited[] , ArrayList<HeapObject> objects ,Graph pointers ) {
        // Mark the current node as visited and print it and mark it to be saved in the new heap
        visited[v] = true;
        objects.get(v).setMarked(true);

        Iterator<Integer> i = pointers.adj[v].listIterator();
        while (i.hasNext()) {
            int n = i.next();
            if (!visited[n])
                DFSUtil(n, visited , objects,pointers);
        }
    }

    public void Mark(ArrayList<HeapObject> objects, Graph pointers, ArrayList<Integer> roots, HashMap<Integer, Integer> heap) {

        boolean visited[] = new boolean[pointers.getV()];

        for (int i = 0; i < roots.size(); i++) {
            DFSUtil(heap.get(roots.get(i)), visited ,objects, pointers);
        }

    }

    public ArrayList<HeapObject> sweep (ArrayList<HeapObject> objects){
        ArrayList<HeapObject> newHeap = new ArrayList<>();

        for(int i=0 ; i<objects.size() ; i++){
            if(objects.get(i).isMarked()){
                newHeap.add(objects.get(i));
            }
        }
        return  newHeap;
    }

    public ArrayList<HeapObject> solve (ArrayList<HeapObject> objects, Graph pointers, ArrayList<Integer> roots, HashMap<Integer, Integer> heap){
        ArrayList<HeapObject> answer = new ArrayList<>();
        Mark(objects,pointers,roots,heap);
        return sweep(objects);
    }

    public static void main(String[] args) {
        MarkAndSweep gc = new MarkAndSweep();
        //gc.solve()
    }
}