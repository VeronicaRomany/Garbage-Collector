import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;


public class G1Heap {
    G1Block[] heap;
    int blockSize;
    Boolean[] blocked;

    public G1Heap(int wholeSize){
        heap=new G1Block[16];
        this.blockSize=wholeSize/16;
        for(int i=0;i<16;i++) heap[i]=new G1Block(this.blockSize);
        blocked=new Boolean[16];
        for(int i=0;i<16;i++) blocked[i]=false;
    }

    public void initializeHeap(ArrayList<HeapObject> objects){
        for(int i=0;i<objects.size();i++){
            HeapObject current = objects.get(i);
            int block=current.getStart()/blockSize;
            int offset=current.getStart()%blockSize;
            current.setStart(offset);
            current.setEnd(current.getEnd()%blockSize);
            heap[block].initializeBlock(current);
            blocked[block]=true;
        }

//        for(int i=0;i<16;i++){
//            if(heap[i].residentObjects.size()>1){
//                HeapObject[] arr = heap[i].residentObjects.toArray(new HeapObject[heap[i].residentObjects.size()]);
//                Arrays.sort(arr, (o1, o2) -> o1.getStart()-o2.getStart());
//                heap[i].residentObjects.clear();
//                Collections.addAll(heap[i].residentObjects,arr);
//            }
//        }

    }

    public void sweep(){
        for(int i=0;i<16;i++){
            if(heap[i].sweepBlock()) blocked[i]=false;
        }
    }

    public void defragment(){
        for(int i=0;i<16;i++){
            if( !blocked[i])    continue; //blocked? true:continue , false:skip
            //reaching here, we need to set where our objects will go
            while (heap[i].residentObjects.size()>0 ) {
                //now we try to move all objects in this to the destination
                //we search for the destination until it fits
                for(int j=0;j<16;j++){
                    if(!blocked[j]) {
                        assert heap[i].residentObjects.peekFirst() != null;
                        if(heap[j].tryToFit(heap[i].residentObjects.peekFirst()))
                            break;
                    }
                }
                heap[i].residentObjects.removeFirst();
                if(heap[i].residentObjects.size()==0) heap[i].free=true;
            }
        }
    }

    public void printHeap(){
        for(int i=0;i<16;i++){
            HeapObject[] arr = heap[i].residentObjects.toArray(new HeapObject[0]);
            System.out.println(i+"> " +heap[i].free );
            for(int j=0;j<heap[i].residentObjects.size();j++){
                System.out.println(arr[j].getStart()+">>"+arr[j].getEnd() +" :: "+arr[j].getId());
            }
        }
    }

    public List<HeapObject> getObjectsList(){
        List<HeapObject> heaplist=new ArrayList<>();
        for(int i=0;i<16;i++){
            HeapObject[] arr = heap[i].residentObjects.toArray(new HeapObject[heap[i].residentObjects.size()]);
            for(int j=0;j<heap[i].residentObjects.size();j++){
                arr[j].setStart(arr[j].getStart()+this.blockSize*i);
                arr[j].setEnd(arr[j].getEnd()+this.blockSize*i);
                heaplist.add(arr[j]);
            }
        }
        return heaplist;
    }
}


class G1Block {
    //objects in that block
    Deque<HeapObject> residentObjects;

    int size;
    //to indicate whether the block can be considered a free block
    Boolean free;

    public G1Block(int blockSize){
        residentObjects=new LinkedList<HeapObject>();
        free=true;
        size= blockSize;
    }

    //assuming no overlapping
    //this function is called when setting up the heap
    public void initializeBlock(HeapObject o){
        residentObjects.add(o);
        free=false;
    }


    /**
     * function sweeps the block only,
     * @return true if the block becomes free
     * or false if the block still not free
     */
    public boolean sweepBlock(){
        Deque<HeapObject> temporary= new LinkedList<>();
        int sizeBefore=residentObjects.size();
        for(int i=0;i<sizeBefore;i++){
            HeapObject o = residentObjects.removeFirst();
            if(o.isMarked()) //sweep
                temporary.addLast(o);
        }
        if(temporary.size()==0){
            this.free=true;
        }
        int sizeAfter=temporary.size();
        for(int i=0;i<sizeAfter;i++){
            residentObjects.addLast(temporary.removeFirst());
        }
        return this.free;
    }

    /**
     * @param o heap object we are looking to insert
     * @return boolean whether the placement is successful
     */
    public boolean tryToFit(HeapObject o){
        int targetSize=o.getEnd()-o.getStart()+1;
        int limit=residentObjects.size();
        int start=0;
        int end=size-1;

        if(limit!=0){
            start=residentObjects.peekLast().getEnd()+1;
        }
        if(end-start+1>=targetSize){
            o.setStart(start);
            o.setEnd(start+targetSize-1);
            residentObjects.addLast(o);
            this.free=false;
            return true;
        }
        return false;
    }

}