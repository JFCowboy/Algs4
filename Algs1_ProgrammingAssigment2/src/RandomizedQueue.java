import java.util.Iterator;

/*------------------------------------
 *   Author:       JFCowboy
 *   Written:      04/03/2016
 *   Last Updated: 04/03/2016
 * 
 *   
 * 
 *------------------------------------*/

/**
 * @author JuanFelipe
 *
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
	
	int N;
	/**
	 * Construct an empty randomized queue. 
	 */
	public RandomizedQueue() {
		
	}

	/**
	 * Is the queue empty?
	 * 
	 * @return true si la queue esta vacia.
	 */
	public boolean isEmpty() {
		return N == 0;
	}
	
	/**
	 * Return the number of items on the queue.
	 * 
	 * @return EL numero de elementos en la cola.
	 */
	public int size() {
		return N;
	}

	/**
	 * Add the item.
	 * 
	 * @param item
	 */
	public void enqueue(Item item) {
		
	}
	
	/**
	 * Remove and return a random item.
	 * 
	 * @return
	 */
	public Item dequeue() {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Return (but do not remove) a random item.
	 * @return
	 */
	public Item sample() {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Return an independent iterator over items in random order.
	 * @return 
	 */
	public Iterator<Item> iterator() {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * unit testing.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		

	}

}
