import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class G1Runner {
    public static void main(String[] args){
        if(args.length!=5) {
            System.out.println("Error, wrong number of arguements.\n" +
                    "Expected heap path, roots path, pointers path, output path, total size of heap(divisible by 16).");
            System.exit(0);
        }
        String heapFilePath=args[0];
        String rootsPath=args[1];
        String pointersPath=args[2];
        String outputPath=args[3];

        int sizeHeap=800; //random default
        try {
            sizeHeap = Integer.parseInt(args[4]);
        } catch (NumberFormatException e) {
            System.err.println("Argument" + args[4] + " must be an integer.");
            System.exit(1);
        };

        //Initialization Steps //////////////////////////////////////////
        CSVReader r = new CSVReader();
        List<Integer> rootlist=r.readRoots(rootsPath); //get roots
        HashMap<Integer, Integer> heapHash = r.heapInput(heapFilePath);
        Graph graph = r.readPointers(pointersPath,heapHash); //get pointers graph
        List<HeapObject> objects= CSVReader.objects;
        MarkAndSweep marker=new MarkAndSweep();
        G1Heap g1 = new G1Heap(sizeHeap);
        g1.initializeHeap((ArrayList<HeapObject>) objects);

        //Garbage first Algorithm /////////////////////////////////////////
        marker.Mark((ArrayList<HeapObject>) objects,graph,(ArrayList<Integer>) rootlist,heapHash); //marking
        g1.sweep(); //sweeping from the heap
        g1.defragment(); //defragmentation process
        r.writeOutput(g1.getObjectsList(),outputPath);

    }


}
