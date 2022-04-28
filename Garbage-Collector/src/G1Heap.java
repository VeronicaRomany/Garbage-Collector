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
            System.out.println("read object "+i+" "+objects.get(i).getId() +" " + objects.get(i).getStart()+" "+objects.get(i).getEnd());
        }


        for(int i=0;i<objects.size();i++){
            HeapObject current = objects.get(i);
            int block=current.getStart()/blockSize;
            int offset=current.getStart()%blockSize;
            System.out.println(current.getStart()+" " +current.getEnd()+" " + block+" " + offset);
            current.setStart(offset);
            current.setEnd(current.getEnd()%blockSize);
            heap[block].initializeBlock(current);
            blocked[block]=true;
        }
        for(int i=0;i<16;i++){
            if(heap[i].residentObjects.size()>1){;
                Collections.sort(heap[i].residentObjects, (o1, o2) -> o1.getStart()-o2.getStart());
            }
        }

    }

    public void sweep(){
        System.out.println("before Sweeping");

        this.printHeap();
        for(int i=0;i<16;i++){
            if(heap[i].sweepBlock()) blocked[i]=false;
        }
        System.out.println("After Sweeping");
        this.printHeap();
        System.out.println("After Sweeping end");

    }

    public void defragment(){
        System.out.println(" ");
        System.out.println("Enter Defragment");


        for(int i=0;i<16;i++){
            if( !blocked[i])    continue;
            //reaching here, we need to set where our objects will go
            while (heap[i].residentObjects.size()>0 ) {
                System.out.println(i+" s:"+heap[i].residentObjects.size());
                //now we try to move all objects in this to the destination
                //we search for the destination until it fits
                for(int j=0;j<16;j++){
                    if(!blocked[j]) {
                        if(heap[j].tryToFit(heap[i].residentObjects.get(0)))
                            break;
                    }
                }
                heap[i].residentObjects.remove(0);
                if(heap[i].residentObjects.size()==0) heap[i].free=true;
            }
            this.printHeap();
        }


//            System.out.println("x");
//            if(i==nextFreeIndex) continue;
//            int destination = nextFreeIndex;
//            System.out.println(">> "+heap[i].residentObjects.size());
//            if(!heap[i].free){
//                while( heap[i].residentObjects.size()> 0){
//                    System.out.println("start of loop " + i +" " +heap[i].residentObjects.size());
//
//                    //try to fit an object from this block to the free block.
//                    HeapObject traveller=heap[i].residentObjects.remove(0);
//                    System.out.println(heap[i].residentObjects.size());
//
//                    System.out.println("A");
//                    do{
//                        System.out.println("b");
//                        if (heap[destination%16].tryToFit(traveller)){
//                            if(heap[i].residentObjects.size()==0) {
//                                heap[i].free = true;
//                                i++;
//                            }
//                            Collections.sort(heap[destination%16].residentObjects,(o1, o2) -> o1.getStart()-o2.getStart());
////                            System.out.println("\nFitted an object");
////                            this.printHeap();
////                            System.out.println(destination+" "+nextFreeIndex);
//                            break;
//                        } else {
//                            destination++;
//                        }
//                    }while ((destination%16)!=nextFreeIndex);
////                    System.out.println("out of while now");
////                    this.printHeap();
//
////                    if(destination==nextFreeIndex)//failed
////                    {
////                        System.out.println("ERROR, removed and object and couldn't fit it anywhere");
////                        System.exit(0);
////                    }
//
//                }
//            }
//        }
        System.out.println("Exit Defragment");
    }

    public void printHeap(){
        for(int i=0;i<16;i++){
            System.out.println(i+"> " +heap[i].free );
            for(int j=0;j<heap[i].residentObjects.size();j++){
                System.out.println(heap[i].residentObjects.get(j).getStart()+">>"+heap[i].residentObjects.get(j).getEnd() +" :: "+heap[i].residentObjects.get(j).getId());
            }
        }
    }
}


class G1Block {
    //objects in that block
    ArrayList<HeapObject> residentObjects;

    int size;
    //to indicate whether the block can be considered a free block
    Boolean free;

    public G1Block(int blockSize){
        residentObjects=new ArrayList<>();
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
        for(int i=0;i<residentObjects.size();i++){
            HeapObject o = residentObjects.get(i);
            if(!o.isMarked()) //sweep
                residentObjects.remove(o);
        }
        if(residentObjects.size()==0){
            this.free=true;
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
        for(int i=0;i<=limit;i++){
            int start=0;
            int end=size-1;
            if(i!=0) {
                start=residentObjects.get(i-1).getEnd()+1;
            }
            if(i!=limit){
                end=residentObjects.get(i).getStart()-1;
            }
            if(end-start+1>=targetSize) {
                o.setStart(start);
                o.setEnd(start+targetSize-1);
                residentObjects.add(i,o);
                this.free=false;
                return true;
            }
            else continue;
        }
        return false;
    }

}