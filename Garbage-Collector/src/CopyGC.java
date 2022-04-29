import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class CopyGC {
    private List roots;
    private HashMap<Integer, Integer> heapHash;
    private Graph heapGraph; // graph to gain access to pointers of each node...adjList
    private List<HeapObject> objects;
    private int startLoc, endLoc;
    private CSVReader csvReader = new CSVReader();
    private void init(String heapPath, String rootsPath, String pointersPath) {
        heapHash = csvReader.heapInput(heapPath); //"C:\\Users\\Dell\\Desktop\\heaps.csv.txt"
        roots = csvReader.readRoots(rootsPath); //"C:\\Users\\Dell\\Desktop\\roots.txt"
        heapGraph = csvReader.readPointers(pointersPath, heapHash); //"C:\\Users\\Dell\\Desktop\\pointers.txt"
        objects = csvReader.objects;
    }

    private List<HeapObject> copyAlgorithm() {
        List<HeapObject> toSpace = new LinkedList<>();
        startLoc = 0; // next free byte initialized to zero
        endLoc = 0;
        /** phase 1
         * move all roots currently in stack to the toSpace
         * mark them as visited/marked to avoid loops around the same node
         * update start and end locations of each node
         */
        for (int i=0; i< roots.size();i++){//HeapObject root : roots) {
            int index = heapHash.get(roots.get(i));
            toSpace.add(objects.get(index));
            objects.get(index).setMarked(true);
            updateStatus(objects.get(index));
        }

        /** phase 2
         * traverse over adjacent nodes of each root adding each to the toSpace
         * update start and end locations of each node
         */

        for (int i=0; i< toSpace.size(); i++) {
            if (startLoc == endLoc) return toSpace;
            HeapObject heapObj = toSpace.get(i);
            int neighboourIndex = heapHash.get(heapObj.getId());
            List<Integer> adjacentNodes = heapGraph.adj[neighboourIndex]; // hena 3yza el index bta3t el object dy to access its adj lsit
            for (Integer adjNodes : adjacentNodes) { // hena m3aya index bta3 kol neighbour
                if (objects.get(adjNodes).isMarked()) continue; // to avoid cycles
                HeapObject neighbour = objects.get(adjNodes);
                toSpace.add(neighbour);
                neighbour.setMarked(true);
                updateStatus(neighbour);
            }
        }

        return toSpace;
    }

    private void updateStatus(HeapObject node) {
        int size = node.getEnd() - node.getStart();
        endLoc = startLoc + size;
        node.setStart(startLoc);
        node.setEnd(endLoc);
        startLoc = ++endLoc;
        endLoc++;
    }



    public void copyGC(String heapPath, String rootsPath, String pointersPath, String outputPath) {
        init(heapPath, rootsPath, pointersPath);
        List<HeapObject> newHeap = copyAlgorithm();
        csvReader.writeOutput(newHeap, outputPath);
    }

    public static void main(String[] args) {
        CopyGC gc = new CopyGC();
        gc.copyGC(args[0], args[1], args[2], args[3]);
    }
}