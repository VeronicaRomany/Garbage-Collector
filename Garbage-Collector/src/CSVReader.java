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
     //   List<Edge> edges= new ArrayList<>();
        Graph g = new Graph(objects.size());
        String line = "";
        String splitBy = ",";
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            while ((line = br.readLine()) != null) {
                String[] object = line.split(splitBy);
                int src = Integer.parseInt(object[0]);
                int dest = Integer.parseInt(object[1]);
             //   System.out.println("src is " + src + dest);
              //  System.out.println(heap.get(src)+"  "+ heap.get(dest));
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

    public static void main(String[] args) throws IOException {
        CSVReader csv = new CSVReader();
        ArrayList <HeapObject> newHeap = new ArrayList<>();

        HashMap heapReturned = csv.heapInput("C:\\Users\\veror\\Desktop\\Book1.csv");
        List rootsReturned = csv.readRoots("C:\\Users\\veror\\Desktop\\roots.txt");
         Graph g = csv.readPointers("C:\\Users\\veror\\Desktop\\pointers.csv",heapReturned);

        System.out.println("Heap Input >> ");
        csv.printHeap(objects);

        MarkAndSweep m = new MarkAndSweep();
        newHeap =  m.solve((ArrayList<HeapObject>) csv.objects,g, (ArrayList<Integer>) rootsReturned,heapReturned);

        System.out.println("Heap after mark and sweep >> ");
        csv.printHeap(newHeap);

      //   m.Mark((ArrayList<HeapObject>) csv.objects,g, (ArrayList<Integer>) rootsReturned,heapReturned);

    //    for(int i=0 ; i<rootsReturned.size();i++){
    //        if(heapReturned.containsKey(rootsReturned.get(i))){
    //            HeapObject h = (HeapObject) csv.objects.get(i);
    //            h.setMarked(true);
    //        }
    //    }
//


//       Iterator hmIterator = heapReturned.entrySet().iterator();
//       while (hmIterator.hasNext()) {
//           Map.Entry mapElement = (Map.Entry)hmIterator.next();
//           System.out.println(mapElement.getKey() + " "+ mapElement.getValue());
         //  HeapObject h = ((HeapObject) mapElement.getValue() );
      //     int index = (int) mapElement.getValue();
     //      System.out.println(mapElement.getKey() + " : " +csv.objects.get(index).getId() + " " +csv.objects.get(index).isMarked());
//       }

//        heapReturned.forEach((key, value) -> System.out.println("Key = " + key ));
//        for (Map.Entry<Integer, HeapObject> entry : heapReturned.entrySet()) {
//            Object key = entry.getKey();
//            Object value = entry.getValue();
//            //TODO: other cool stuff
//        }
////
//
//        for (int i=0 ; i< rootsReturned.size();i++){
//            System.out.println(rootsReturned.get(i));
//
//        }
        //        for (int i=0 ; i< heapReturned.size();i++){
//            HeapObject obj = (HeapObject) heapReturned.get(i);
//            System.out.println("Marked what : "+ obj.isMarked());
//        }



    }
}

