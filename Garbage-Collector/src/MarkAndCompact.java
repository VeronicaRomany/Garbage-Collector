import java.util.ArrayList;
import java.util.HashMap;

public class MarkAndCompact {

    public ArrayList<HeapObject> compact (ArrayList<HeapObject> heap){
       // ArrayList<HeapObject> compactHeap = new ArrayList<>();
        // assume sorted by start point
        ArrayList<Integer> blockSize = new ArrayList<>();
        for(int i=0 ; i<heap.size();i++){               // calculate the blocks sizes
            blockSize.add(heap.get(i).getEnd()-heap.get(i).getStart());
        }

        for(int i=0 ; i<heap.size(); i++){
            if(i==0){
                if(heap.get(i).getStart()!=1){
                    heap.get(i).setStart(1);
                }
                heap.get(i).setEnd(heap.get(i).getStart()+blockSize.get(i));
            }else{
                heap.get(i).setStart(heap.get(i-1).getEnd()+1);
                heap.get(i).setEnd(heap.get(i).getStart()+blockSize.get(i));
            }
        }

        return heap;

    }

    public ArrayList<HeapObject> solve (ArrayList<HeapObject> objects, Graph pointers, ArrayList<Integer> roots, HashMap<Integer, Integer> heap){
        MarkAndSweep phase1 = new MarkAndSweep();
        ArrayList<HeapObject> newHeap = phase1.solve(objects,pointers,roots,heap);

        return compact(newHeap);
    }
}