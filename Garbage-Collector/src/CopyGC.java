import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class CopyGC {
    private List<HeapObject> toSpace = new LinkedList<>();
    private List<HeapObject> roots;
    private HashMap heapHash;
    private Graph heap; // graph to gain access to pointers of each node

    private void init() {
        CSVReader csvReader = new CSVReader();
        heapHash = csvReader.heapInput("");
        roots = csvReader.readRoots("");
        heap = csvReader.readPointers(" c", heapHash);
    }

    public void copyGC() {
        init();
        int startLoc = 0; // next free byte initialized to zero
        int endLoc = 0, size;
        /** phase 1
         * move all roots currently in stack to the toSpace
         * mark them as visited/marked to avoid loops around the same node
         * update start and end locations of each node
         */
        for (HeapObject root : roots) {
            //toSpace.add(heapHash.get(i)); /// check dy
            toSpace.add(root);// wla dy
            root.setMarked(true);
            updateStatus(startLoc, endLoc, root);
        }

        /** phase 2
         * traverse over adjacent nodes of each root adding each to the toSpace
         * update pointers
         * update start and end locations of each node
         */

        for (HeapObject heapObj : toSpace) {
            if (startLoc == endLoc) return;
            List adjacentNodes = heap.adj[(int) heapHash.get(heapObj.getId())]; // hena 3yza el index bta3t el object dy to access its adj lsit
            for (Object adjNodes : adjacentNodes) {
                // hena m3aya index bta3 kol neighbour
                if (roots.get((Integer) adjNodes).isMarked()) continue; // to avoid cycles
                HeapObject neighbour = roots.get((Integer) adjNodes);
                toSpace.add(neighbour);
                neighbour.setMarked(true);
                updateStatus(startLoc, endLoc, neighbour);

            }
        }
    }

    private void updateStatus(int startLoc, int endLoc, HeapObject node) {
        int size = node.getEnd() - node.getStart();
        endLoc = startLoc + size;
        node.setStart(startLoc);
        node.setEnd(endLoc);
        startLoc = ++endLoc;
    }

}