// Klassen i denna fil måste döpas om till DHeap för att testerna ska fungera.

//BinaryHeap class
//
//CONSTRUCTION: with optional capacity (that defaults to 100)
//            or an array containing initial items
//
//******************PUBLIC OPERATIONS*********************
//void insert( x )       --> Insert x
//Comparable deleteMin( )--> Return and remove smallest item
//Comparable findMin( )  --> Return smallest item
//boolean isEmpty( )     --> Return true if empty; else false
//void makeEmpty( )      --> Remove all items
//******************ERRORS********************************
//Throws UnderflowException as appropriate

/**
 * Implements a binary heap.
 * Note that all "matching" is based on the compareTo method.
 * @author Mark Allen Weiss
 */
public class DHeap<AnyType extends Comparable<? super AnyType>>
{
    private int d;
    private int size;
    private int capacity;
    private int currentSize;      // Number of elements in heap
    private AnyType [ ] array; // The heap array
    /**
     * Construct the binary heap.
     */
    public DHeap( )
    {
        this( DEFAULT_CAPACITY );
    }

    /**
     * Construct the binary heap.
     * @param d the capacity of the binary heap.
     */
    public DHeap( int d )
    {
        if(d<=1)
            throw new IllegalArgumentException();
        this.d = d;
        currentSize = 0;
        array = (AnyType[]) new Comparable[DEFAULT_CAPACITY +1];


    }

    private void bubbleUp(int childIndex){
        AnyType temporary = array[childIndex];
        while(childIndex > 0 &&  temporary.compareTo(array[parentIndex(childIndex)]) < 0 ){
            array[childIndex] = array[parentIndex(childIndex)];
            childIndex = parentIndex(childIndex);
        }
        array[childIndex] = temporary;


    }





    public void insert( AnyType x )
    {
        if(currentSize == array.length -1)
            enlargeArray(array.length * 2 +1);
        int hole = ++currentSize;
        for(array[0] = x; hole != 1 && x.compareTo(array[parentIndex(hole)]) < 0; hole = parentIndex(hole)){
            if(hole != 1)
                array[hole] = array[parentIndex(hole)];


        }
        array[hole] = x;



    }

    private void enlargeArray( int newSize )
    {
        AnyType [] old = array;
        array = (AnyType []) new Comparable[ newSize ];
        for( int i = 0; i < old.length; i++ )
            array[ i ] = old[ i ];
    }



    public AnyType findMin( )
    {
        if( isEmpty( ) )
            throw new UnderflowException( );
        return array[ 1 ];
    }


    /**
     * Remove the smallest item from the priority queue.
     * @return the smallest item, or throw an UnderflowException if empty.
     */
    public AnyType deleteMin()
    {
        if( isEmpty( ) )
            throw new UnderflowException( );

        AnyType minItem = findMin();
        array[1] = array[currentSize-- ];
        percolateDown( 1);
        return minItem;
    }

    /**
     * Establish heap order property from an arbitrary
     * arrangement of items. Runs in linear time.
     */
    private void buildHeap( )
    {
        for( int i = currentSize / 2; i > 0; i-- )
            percolateDown( i );
    }


    public boolean isEmpty( )
    {
        return currentSize == 0;
    }

    /**
     * Make the priority queue logically empty.
     */
    public void makeEmpty( )
    {
        currentSize = 0;
    }

    private static final int DEFAULT_CAPACITY = 10;



    /**
     * Internal method to percolate down in the heap.
     * @param hole the index at which the percolate begins.
     */
    private void percolateDown( int hole )
    {
        int child;
        AnyType tmp = array[hole];
        int minChild;
        for(; firstChildIndex(hole) <= currentSize; hole = minChild){
            child = firstChildIndex(hole);
            minChild = child;
            for(int i = 0; i<d && child + i <= currentSize; i++ ){
                if(child +1 <= currentSize && minChild <= currentSize && array[child + i].compareTo(array[minChild]) < 0){
                    minChild = child+i;
            }
        }
            if(array[minChild].compareTo(tmp) < 0){
                array[hole] = array[minChild];
            }
            else{
                break;
            }

        }

        array[hole] = tmp;


    }

    public int minChildIndex(int parent){
        int firstChildIndex = firstChildIndex(parent);
        if(firstChildIndex > currentSize)
            return Integer.MAX_VALUE;
        AnyType minChild = array[firstChildIndex];
        int minChildIndex = firstChildIndex;
        for(int i =1;i<d;i++){
            if(firstChildIndex+i <= currentSize){
                AnyType compareWith = array[firstChildIndex+i];
                if(compareWith !=null  && minChild.compareTo(compareWith) > 0){
                    minChildIndex = firstChildIndex+i;
                    minChild = compareWith;
                }
            }
        }
        return minChildIndex;
    }



    public int firstChildIndex(int parent) throws IllegalArgumentException{
        if(parent < 1){
            throw new java.lang.IllegalArgumentException("too small parent");
        }
        int child = 1;
        return d*(parent-1)+child+1;
    }

    // Test program
    public static void main( String [ ] args )
    {
        int numItems = 10000;
        DHeap<Integer> h = new DHeap<>( );
        int i = 37;

        for( i = 37; i != 0; i = ( i + 37 ) % numItems )
            h.insert( i );
        for( i = 1; i < numItems; i++ )
            if( h.deleteMin( ) != i )
                System.out.println( "Oops! " + i );
    }



    public void printHeap() {
        System.out.print("\nHeap = ");
        for (int i = 0; i < currentSize; i++)
            System.out.print(array[i] + " ");
        System.out.println();
    }



    public int parentIndex(int i) {
        if(i < 2){
            throw new java.lang.IllegalArgumentException();
        }
        if(i == 2)
            return 1;
        return ((i-2)/d +1);

    }

    public int parentIndex(AnyType value){
        for(int i = 0; i<array.length; i++){
            if(array[i].compareTo(value) == 0){
                return parentIndex(i);
            }
        }
        throw new java.lang.IllegalArgumentException("NOT FOUND");
    }

    public AnyType parent(int child){
        return array[parentIndex(child)];
    }


    private AnyType minChild(int parent){
        return get(minChildIndex(parent));

    }



    public AnyType get(int index){
        return array[index];
    }



    public int size() {

        return currentSize;
    }


}

