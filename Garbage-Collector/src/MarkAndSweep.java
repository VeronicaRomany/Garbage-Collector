import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

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
        writeOutput(out,outputPath);
    }

    private static void writeOutput(List<HeapObject> toSpace, String outputPath) {
        File file = new File(outputPath + "\\new-heap.csv"); //out
        try {
            FileWriter myWriter = new FileWriter(file);
            for (HeapObject heapObject : toSpace) {
                myWriter.write(heapObject.getId() + "," + heapObject.getStart() + "," + heapObject.getEnd());
                myWriter.write("\n");
            }
            myWriter.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}