import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class MarkAndCompact {

    public ArrayList<HeapObject> sorting (ArrayList<HeapObject> heap){

        for (int i = 0; i < heap.size() - 1; i++)
            for (int j = 0; j < heap.size() - i - 1; j++)
                if (heap.get(j).getStart() > heap.get(j+1).getStart()) {
                    Collections.swap(heap,j,j+1);
                }
        return  heap;
    }

    public ArrayList<HeapObject> compact (ArrayList<HeapObject> heap){
        heap = sorting(heap);

        ArrayList<Integer> blockSize = new ArrayList<>();
        for(int i=0 ; i<heap.size();i++){               // calculate the blocks sizes
            blockSize.add(heap.get(i).getEnd()-heap.get(i).getStart());
        }

        for(int i=0 ; i<heap.size(); i++){
            if(i==0){
                if(heap.get(i).getStart()!=0){
                    heap.get(i).setStart(0);
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

    public static void main(String[] args) {
        MarkAndCompact gc = new MarkAndCompact();
        if(args.length!=4) {
            System.out.println("Error, wrong number of arguements.\n" +
                    "Expected heap path, roots path, pointers path, output folder path.");
            System.exit(0);
        }
        String heapFilePath=args[0];
        String rootsPath=args[1];
        String pointersPath=args[2];
        String outputPath=args[3];

        //Initialization Steps //////////////////////////////////////////
        CSVReader r = new CSVReader();
        List<Integer> rootlist=r.readRoots(rootsPath); //get roots
        HashMap<Integer, Integer> heapHash = r.heapInput(heapFilePath);
        Graph graph = r.readPointers(pointersPath,heapHash); //get pointers graph
        List<HeapObject> objects= CSVReader.objects;

        List<HeapObject> out = gc.solve((ArrayList<HeapObject>) objects,graph,(ArrayList<Integer>) rootlist,heapHash);
        r.writeOutput(out,outputPath);
    }

}
