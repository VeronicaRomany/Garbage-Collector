import java.io.*;
import java.util.*;

public class CSVReader {
      static List <HeapObject> objects = new ArrayList<>();

    public HashMap<Integer, Integer> heapInput(String path) {
        HashMap<Integer, Integer> heap = new HashMap<>();
        String line = "";
        String splitBy = ",";
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            while ((line = br.readLine()) != null) {
                String[] object = line.split(splitBy);
                HeapObject heapObject = new HeapObject((Integer.parseInt(object[0])), Integer.parseInt(object[1]), Integer.parseInt(object[2]));
                objects.add(heapObject);
                heap.put(Integer.parseInt(object[0]),objects.indexOf(heapObject));
            }
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        return heap;
    }

    public List readRoots(String path) {
        List roots = new ArrayList();
        String st="";
        try{
            BufferedReader br = new BufferedReader(new FileReader(path));
            while ((st = br.readLine()) != null) {
                roots.add(Integer.parseInt(st));
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return roots;
    }

    public Graph readPointers(String path , HashMap<Integer,Integer> heap) {

        Graph g = new Graph(objects.size());
        String line = "";
        String splitBy = ",";
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            while ((line = br.readLine()) != null) {
                String[] object = line.split(splitBy);
                int src = Integer.parseInt(object[0]);
                int dest = Integer.parseInt(object[1]);
                g.addEdge(heap.get(src), heap.get(dest));
            }
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        return g ;
    }

    public void printHeap(List<HeapObject> h){
        for(int i=0 ; i<h.size();i++){
            System.out.println(h.get(i).getId() + " " + h.get(i).isMarked() + " " + h.get(i).getStart() + " " + h.get(i).getEnd());
        }
    }

    void writeOutput(List<HeapObject> newHeap, String outputPath) {
        File file = new File(outputPath + "\\new-heap.csv"); //out
        try {
            FileWriter myWriter = new FileWriter(file);
            for (HeapObject heapObject : newHeap) {
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

