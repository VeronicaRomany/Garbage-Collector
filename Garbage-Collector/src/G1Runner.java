import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class G1Runner {
    public static void main(String[] args){
        String heapFilePath="heapG1.csv";
        String pointersPath="pointersG1.csv";
        String outputPath="newHeapG1.csv";
        String rootsPath="roots.txt";

        CSVReader r = new CSVReader();
        List<Integer> rootlist=r.readRoots(rootsPath);
        HashMap<Integer, Integer> heapHash = r.heapInput(heapFilePath) ;
        Graph graph = r.readPointers(pointersPath,heapHash);
        List<HeapObject> objects= CSVReader.objects;


        MarkAndSweep marker=new MarkAndSweep();

        G1Heap g1 = new G1Heap(800);
        System.out.println("START");
        g1.initializeHeap((ArrayList<HeapObject>) objects);
        g1.printHeap();
        System.out.println("START");

        marker.Mark((ArrayList<HeapObject>) objects,graph,(ArrayList<Integer>) rootlist,heapHash);
        g1.sweep();
        g1.defragment();
        g1.printHeap();


    }
}
