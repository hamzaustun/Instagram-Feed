import java.util.Arrays;

/**
 * This class is to implement priority queue
 * It consists of post array named table and the number of elements in this table named currentSize
 */
public class MaxHeap{
    Post[] table;
    int currentSize;

    /**
     * Constructor of max heap
     * @param table it takes a table as an input and change it to a max heap by using build heap method
     */
    public MaxHeap(Post[] table) {
        this.table = table;
        currentSize = 0;
        buildHeap();
    }

    /**
     * This constructor is used to create heaps in which are already have elements
     */
    public MaxHeap(int currentSize, Post[] table) {
        this.currentSize = currentSize;
        this.table = table;
    }

    /**
     * This method enlarges the table by given number named multiplier
     */
    private void enlargeArray(int multiplier){
        table = Arrays.copyOf(table,multiplier);
    }

    /**
     * To add a new element in the heap
     */
    public void insert(Post element){
        if(currentSize == table.length - 1) // if there is not a place to be inserted
            enlargeArray( table.length * 2 + 1 ); // enlarge the table
        int hole = ++currentSize; // assign the first position after the last element in the array to the hole
        for(table[0] = element; element.compareToByLike(table[hole/2]) > 0; hole/=2)  // percolate up the hole until a suitable position for the inserted element is found
            table[ hole ] = table[ hole / 2 ];
        table[ hole ] = element; // insert the element in the suitable position
    }

    /**
     * This method deletes the first element in the table and change the positions of the other elements according to heap property
     * @return the first element
     */
    public Post deleteMax( ) {
        if(currentSize == 0)  // if there is no element in the heap
            return null;
        Post maxItem = table[1]; // Array location 1
        table[ 1 ] = table[ currentSize-- ]; // the last element
        percolateDown( 1 );
        return maxItem;
    }

    /**
     * This method is to protect the heap property
     * It changes the positions of the elements according to heap property
     */
    private void percolateDown( int hole ) {
        int child;
        Post tmp = table[ hole ];
        for( ; hole * 2 <= currentSize; hole = child ) { // repeat until hole finds a suitable position
            child = hole * 2; // check the child
            if(child < currentSize && table[child+1].compareToByLike(table[child]) > 0) // if the right child is greater
                child++;
            if( table[child].compareToByLike( tmp ) > 0 ) // if the hole(parent) is greater and its child
                table[hole] = table[child]; // elevate the child up
            else // if children is greater than hole
                break;
        }
        table[ hole ] = tmp; // insert the percolated down element to suitable position
    }

    /**
     * This method percolates down all the elements in the table one by one to ensure the heap property
     */
    private void buildHeap( ) {
        for( int i = currentSize / 2; i > 0; i-- )
            percolateDown( i );
    }
}
