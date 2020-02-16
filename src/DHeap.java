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
        new DHeap(2, DEFAULT_CAPACITY);
    }
    public DHeap(int d, int capacity){
        this.d = d;
        currentSize = 0;
        array = (AnyType[]) new Comparable[capacity +1];
    }

    //d är antal barn
    public DHeap(int d)
    {
        if(d<=1)
            throw new IllegalArgumentException();
        this.d = d;
        currentSize = 0;
        array = (AnyType[]) new Comparable[DEFAULT_CAPACITY +1];
    }

    public boolean isMax(){
        return currentSize == array.length -1;
    }


    public void insert(AnyType x)
    {
        if(isMax())
            enlargeArray(array.length * 2 +1);
        currentSize++;
        int hole = currentSize;
        for(array[0] = x; hole != 1 && x.compareTo(array[parentIndex(hole)]) < 0; hole = parentIndex(hole)){
            array[hole] = array[parentIndex(hole)];
        }
        array[hole] = x;

    }

    private void enlargeArray(int newSize)
    {
        AnyType [] oldArray = array;
        array = (AnyType []) new Comparable[ newSize ];
        System.arraycopy(oldArray, 0, array, 0, oldArray.length);
    }



    public AnyType findMin()
    {
        if(isEmpty())
            throw new UnderflowException();
        return array[1];
    }



    public AnyType deleteMin()
    {
        if(isEmpty())
            throw new UnderflowException();
        AnyType minNode = findMin();
        array[1] = array[currentSize--];
        percolateDown( 1);
        return minNode;
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


    public int firstChildIndex(int parentIndex){
        if(parentIndex < 1)
            throw new java.lang.IllegalArgumentException("parent too small");
        if(d<2){
            return (parentIndex == 1) ? 2 : (2*parentIndex);

        }
        return  d*(parentIndex-1)+2;
    }



    public int parentIndex(int x){
        if(x < 2){
            throw new java.lang.IllegalArgumentException();
        }if(d<3){
           int index = (x-1)/2;
           if(index > 1)
               return (index % 2 == 0) ? index : index+1;
           return 1;
        }
        return (x == 2) ? 1 : ((x-2)/d +1);
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





    public AnyType get(int index){
        return array[index];
    }


    public int size() {
        return currentSize;
    }


}

