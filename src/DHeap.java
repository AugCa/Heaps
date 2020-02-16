//August Carlsson auca4478
//Adan Anwar adan9862

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


public class DHeap<AnyType extends Comparable<? super AnyType>>
{
    private int d;
    private int currentSize;      // Number of elements in heap
    private AnyType [ ] array; // The heap array



    public DHeap( )
    {
        this( DEFAULT_CAPACITY );
    }


    public DHeap( int d )
    {
        if(d<=1)
            throw new IllegalArgumentException();
        this.d = d;
        currentSize = 0;
        array = (AnyType[]) new Comparable[DEFAULT_CAPACITY +1];


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



    public AnyType deleteMin()
    {
        if( isEmpty( ) )
            throw new UnderflowException( );

        AnyType minItem = findMin();
        array[1] = array[currentSize-- ];
        percolateDown( 1);
        return minItem;
    }

    


    public boolean isEmpty( )
    {
        return currentSize == 0;
    }


    public void makeEmpty( )
    {
        currentSize = 0;
    }

    private static final int DEFAULT_CAPACITY = 10;



  
    private void percolateDown( int hole )
    {
        AnyType temp = array[hole];
        int childIndex;
        int minChildIndex;
        for(; firstChildIndex(hole) <= currentSize; hole = minChildIndex){
            childIndex = firstChildIndex(hole);
            minChildIndex = childIndex;
            for(int i = 0; i<d && childIndex + i <= currentSize; i++ ){
                if(childIndex +1 <= currentSize && minChildIndex <= currentSize && array[childIndex + i].compareTo(array[minChildIndex]) < 0){
                    minChildIndex = childIndex+i;
            }
        }
            if(array[minChildIndex].compareTo(temp) < 0){
                array[hole] = array[minChildIndex];
            }
            else{
                break;
            }

        }
        array[hole] = temp;

    }

    public int minChildIndex(int parentIndex){
        int firstChildIndex = firstChildIndex(parentIndex);
        if(firstChildIndex > currentSize)
            return Integer.MAX_VALUE;
        AnyType minChild = array[firstChildIndex];
        int minChildIndex = firstChildIndex;
        for(int i =1;i<d;i++){
            if(firstChildIndex+i <= currentSize){
                AnyType cmp = array[firstChildIndex+i];
                if(cmp != null  && cmp.compareTo(minChild) < 0){
                    minChildIndex = firstChildIndex+i;
                    minChild = cmp;
                }
            }
        }
        return minChildIndex;
    }

    public int firstChildIndex(int parentIndex) throws IllegalArgumentException{
        if(parentIndex < 1){
            throw new java.lang.IllegalArgumentException("parent too small");
        }
        int childIndex = 1;
        return d*(parentIndex-1)+childIndex+1;
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



    public int parentIndex(int index) {
        if(index < 2){
            throw new java.lang.IllegalArgumentException();
        }
        if(index == 2)
            return 1;
        return ((index-2)/d +1);

    }

    public AnyType get(int index){
        return array[index];
    }


    public int size() {
        return currentSize;
    }


}

