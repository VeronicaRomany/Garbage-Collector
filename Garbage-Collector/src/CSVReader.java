import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class CSVReader {
  /*  public Stack heapInput(String path) {
        Stack heapStack = null;
        String line = "";
        String splitBy = ",";
        try {
            //parsing a CSV file into BufferedReader class constructor
            BufferedReader br = new BufferedReader(new FileReader(path));
            while ((line = br.readLine()) != null)
            //returns a Boolean value
            {
                String[] object = line.split(splitBy);
                //use comma as
                HeapObject heapObject = new HeapObject((Integer.parseInt(object[0])), Integer.parseInt(object[1]), Integer.parseInt(object[2]));
                heapStack.push(heapObject);
                System.out.println("Emp[First Name=" + object[0] + ", Last Name=" + object[1] + ", Contact=" + object[2]);
            }
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        return heapStack;
    }
*/
    public List readRoots(String path) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(path));
        List roots = new ArrayList();
        // Declaring a string variable
        String st;
        // Condition holds true till
        // there is character in a string
        while ((st = br.readLine()) != null) {
            roots.add(Integer.parseInt(st));
        // Print the string
        System.out.println(roots.size());
        System.out.println(roots);
    }
        return roots;
    }

    public void readPointers(String path) {
        List<Edge> edges= new ArrayList<>();
        String line = "";
        String splitBy = ",";
        try {
            //parsing a CSV file into BufferedReader class constructor
            BufferedReader br = new BufferedReader(new FileReader(path));
            while ((line = br.readLine()) != null)
            //returns a Boolean value
            {
                String[] object = line.split(splitBy);
                int src = Integer.parseInt(object[0]);
                int dest = Integer.parseInt(object[1]);
                System.out.println("src is " + src + dest);
                Edge edge = new Edge(src, dest);
                edges.add(edge);
                System.out.println("Emp[First Name=" + object[0] + ", Last Name=" + object[1]);
                System.out.println(edges);
                System.out.println(edges.size());
            }
            Graph graph = new Graph(edges);
          //  printGraph(graph);
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        CSVReader csv = new CSVReader();
     //   csv.heapInput("C:\\Users\\Dell\\Desktop\\heap.csv");
        //csv.readRoots("C:\\Users\\Dell\\Desktop\\roots.txt");
       // csv.readPointers("C:\\Users\\Dell\\Desktop\\pointers.csv");
    }
}

