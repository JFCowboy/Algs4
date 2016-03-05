/*------------------------------------
 *   Author:       JFCowboy
 *   Written:      04/03/2016
 *   Last Updated: 05/03/2016
 * 
 *   Compilation: 	javac Deque.java
 *   Execution:		java Deque < input.txt
 *   Dependencies:	StdIn.java, StdOut.java
 *   
 * 
 *------------------------------------*/

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * La clase <tt>Deque</tt> es la implementacion de un <tt>deque</tt>, 
 * generalizacion de stack y queue que soporta inserciones y eliminaciones 
 * al inicio y fin de la estructura.
 * 
 * @author JuanFelipe
 *
 * @param <Item> El tipo generico que se usara en el Deque.
 */
public class Deque<Item> implements Iterable<Item> {
	private Node<Item> first; 	//Apuntador al primer elemento del deque
	private Node<Item> last;  	//Apuntador al ultimo elemento del deque
	private int N;				//Tamano del Deque
	
	/**
	 * Clase nodo auxiliar, usada para la implementacion del Deque.
	 * @author JuanFelipe
	 * 
	 * @param <Item> 
	 */
	private class Node<Item> {
		private Item data;			//Tipo de dato que almacenara el nodo.
		private Node next, prev;	//Apuntador al siguiente y anterior nodo.
	
		/**
		 * Inicializa el nodo vacio.
		 */
		public Node() {
			super();
			next = null;
			prev = null; 
		}
		
		/**
		 * Inicializa el nodo con un valor.
		 * @param data 
		 */
		public Node(Item data) {
			super();
			this.data = data;
			next = null;
			prev = null;
		}
	}
	
	/**
	 * construct an empty deque. 
	 */
	public Deque() {
		last = first = null;
		N = 0;
	}
	
	/**
	 * is the deque empty?
	 * Retorna true si el Deque esta vacio.
	 * @return true si el Deque esta vacio; false en otro caso
	 */
	public boolean isEmpty() {
		return N == 0;
	}
	
	
	/**
	 * Return the number of items on the deque.
	 * 
	 * @return El numero de elementos en el Deque
	 */
	public int size() {
		return N;
	}
	
	/**
	 * Add the item to the front.
	 * 
	 * @param item el elemento a agregar al inicio.
	 * @throws NullPointerException si el elemento a ingresar es nulo
	 */
	public void addFirst(Item item) {
		if (item == null) {
			throw new NullPointerException("No es posible agregar " 
					+ "un elemento null");
		}
		Node<Item> nuevo = new Node<>(item);
		nuevo.next = first;
		if (first != null) {
			first.prev = nuevo;
		}
		
		first = nuevo;
		if (N == 0) {
			last = first;
		}
		N++;
	}
	
	/**
	 * Add the item to the end.
	 * 
	 * @param item el elemento a agregar al final.
	 * @throws NullPointerException si el elemento a ingresar es nulo
	 */
	public void addLast(Item item) {
		if (item == null) {
			throw new NullPointerException("No es posible agregar " 
					+ "un elemento null");
		}
		if (isEmpty()) {
			addFirst(item);
			return;
		}
		Node<Item> nuevo = new Node<>(item);
		nuevo.prev = last;
		last.next = nuevo;
		last = nuevo;
		N++;
	}
	
	/**
	 * Remove and return the item from the front.
	 * 
	 * @return el item que se encuentra en la parte de adelante del deque
	 * @throws NoSuchElementException si la estructura deque esta vacia.
	 */
	public Item removeFirst() {
		if (N == 0) {
			throw new NoSuchElementException("Deque UnderFLow, " 
					+ "el Deque esta vacio");
		}
		Item data = first.data;
		first = first.next;
		if (first != null) {
			first.prev = null;
		} else {
			last = null;
		}
		N--;
		return data;
	}
	
	/**
	 * Remove and return the item from the end.
	 * 
	 * @return el item que se encuentra en la parte de atras del deque
	 * @throws NoSuchElementException si la estructura deque esta vacia. 
	 */
	public Item removeLast() {
		if (N == 0) {
			throw new NoSuchElementException("Deque UnderFLow, " 
					+ "el Deque esta vacio");
		}
		Item data = last.data;
		last = last.prev;
		if (last != null) {
			last.next = null;
		} else {
			first = null;
		}
		N--;
		return data;
	}

	/**
	 * Return an iterator over items in order from front to end.
	 * 
	 *  @return un iterador del deque en orden del principio al final.
	 */
	public Iterator<Item> iterator() {
		return new DequeIterator<Item>(first);
	}
	
	
	/**
	 * Clase que retorna el iterador, no soporta remove.
	 *
	 * @param <Item>
	 */
	private class DequeIterator<Item> implements Iterator<Item>{
		private Node<Item> current;		//Iterador al elemento actual.
		
		/**
		 * .
		 * @param current
		 */
		public DequeIterator(Node<Item> current) {
			super();
			this.current = current;
		}

		@Override
		public boolean hasNext() {
			return current != null;
		}

		@Override
		public Item next() {
			if (!hasNext()) {
				throw new NoSuchElementException("No hay mas elementos");
			}
			Item item = current.data;
			current = current.next;
			return item;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
		
	}
	
	/**
	 * Metodo para probar la clase.
	 * @param args
	 */
	public static void main(String[] args) {
		Deque<String> dq = new Deque<>();
		int ope;
		String input;
		while (!StdIn.isEmpty()) {
			ope = StdIn.readInt();
			StdOut.println("Ope:" + ope);
			if (ope <= 2) {
				input = StdIn.readString();
				if (ope == 1) {
					dq.addFirst(input);
				} else {
					dq.addLast(input);
				}
			} else if (ope <= 4) {
				if (ope == 3) {
					dq.removeFirst();
				} else {
					dq.removeLast();
				}
			} else if (ope == 5) {
				StdOut.println(dq.size());
				for (String str : dq) {
					StdOut.println("1: " + str);
					for (String str2 : dq) {
						StdOut.println("2: " + str2);
					}
				}
			} else {
				int idx = 0;
				StdOut.println(dq.size());
				for (String str : dq) {
					StdOut.println(idx + ": " + str);
					idx++;
				}
			}
		}
	}

}
