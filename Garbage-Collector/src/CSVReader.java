import java.io.*;
import java.util.*;

public class CSVReader {
      List <HeapObject> objects = new ArrayList<>();

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

    public void readPointers(String path , HashMap<Integer,Integer> heap) {
     //   List<Edge> edges= new ArrayList<>();
        Graph g = new Graph(objects.size());
        String line = "";
        String splitBy = ",";
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            while ((line = br.readLine()) != null)
            {
                String[] object = line.split(splitBy);
                int src = Integer.parseInt(object[0]);
                int dest = Integer.parseInt(object[1]);
                System.out.println("src is " + src + dest);
                System.out.println(heap.get(src)+"  "+ heap.get(dest));
                g.addEdge(heap.get(src), heap.get(dest));

              //  Edge edge = new Edge(src, dest);
       //         edges.add(edge);
       //         System.out.println("Emp[First Name=" + object[0] + ", Last Name=" + object[1]);
       //         System.out.println(edges);
       //         System.out.println(edges.size());
            }
            g.PrintGraph();
            g.DFS(0);
            System.out.println("");
       //     Graph graph = new Graph(edges);
          //  printGraph(graph);
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        CSVReader csv = new CSVReader();
        HashMap heapReturned = csv.heapInput("C:\\Users\\marko\\Desktop\\p.csv");
        List rootsReturned = csv.readRoots("C:\\Users\\marko\\Desktop\\r.txt");
         csv.readPointers("C:\\Users\\marko\\Desktop\\h.csv",heapReturned);

        for(int i=0 ; i<rootsReturned.size();i++){
            if(heapReturned.containsKey(rootsReturned.get(i))){
                HeapObject h = (HeapObject) csv.objects.get(i);
                h.setMarked(true);
            }
        }

        for(int i=0 ; i<csv.objects.size();i++){
            System.out.println(csv.objects.get(i).getId() + " " + csv.objects.get(i).isMarked());
        }

       Iterator hmIterator = heapReturned.entrySet().iterator();
       while (hmIterator.hasNext()) {
           Map.Entry mapElement = (Map.Entry)hmIterator.next();
           System.out.println(mapElement.getKey() + " "+ mapElement.getValue());
         //  HeapObject h = ((HeapObject) mapElement.getValue() );
      //     int index = (int) mapElement.getValue();
     //      System.out.println(mapElement.getKey() + " : " +csv.objects.get(index).getId() + " " +csv.objects.get(index).isMarked());
       }

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

