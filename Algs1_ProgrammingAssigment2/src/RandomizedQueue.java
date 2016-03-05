/*------------------------------------
 *   Author:       JFCowboy
 *   Written:      04/03/2016
 *   Last Updated: 05/03/2016
 * 
 *   
 * 
 *------------------------------------*/

import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

    
/**
 * La clase <tt>RandomizedQueue</tt> es la implementacion de una cola aleatoria
 * similar a un <em>stack</em> o <em>queue</em>, solo que el item a remover es
 * seleccionado de forma uniforme sobre el numero de items.
 * 
 * @author JuanFelipe
 * 
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
    private static final int INITAM = 4;
    private Item[] rq; // Elementos de la cola
    private int N; // Numero de elementos en la cola

    /**
     * Construct an empty randomized queue.
     */
    public RandomizedQueue() {
        N = 0;
        rq = (Item[]) new Object[INITAM];
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
        if (item == null) {
            throw new NullPointerException("No se admiten elementos nulos");
        }
        if (N == rq.length) {
            resize(2 * rq.length);
        }
        rq[N] = item;
        int idx = StdRandom.uniform(N + 1);
        swap(idx, N);
        N++;
    }

    private void swap(int i1, int i2) {
        Item swap = rq[i1];
        rq[i1] = rq[i2];
        rq[i2] = swap;
    }

    private void resize(int tam) {
        Item[] temp = (Item[]) new Object[tam];
        int idx;
        for (int i = 0; i < N; i++) {
            temp[i] = rq[i];
            idx = StdRandom.uniform(i + 1);
            Item swap = temp[idx];
            temp[idx] = temp[i];
            temp[i] = swap;
        }
        rq = temp;
    }

    /**
     * Remove and return a random item.
     * 
     * @return
     */
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack underflow");
        }
        N--;
        int idx = StdRandom.uniform(N + 1);
        swap(idx, N);
        Item item = rq[N];
        rq[N] = null;

        if (N > 0 && N <= rq.length / 4) {
            resize(rq.length / 2);
        }

        return item;
    }

    /**
     * Return (but do not remove) a random item.
     * 
     * @return
     */
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack underflow");
        }
        int idx = StdRandom.uniform(N);
        return rq[idx];
    }

    /**
     * Return an independent iterator over items in random order.
     * 
     * @return
     */
    public Iterator<Item> iterator() {
        return new RandomIterator(rq, N);
    }

    /**
     * Un iterador, no implementa remove.
     * 
     * @author JuanFelipe
     * 
     */
    private class RandomIterator implements Iterator<Item> {
        private Item[] seq;
        private int idx;

        /**
         * @param seq
         *            la secuencia que se recorrera aleatoriamente.
         * @param idx
         */
        public RandomIterator(Item[] seq, int N) {
            super();
            this.seq = seq.clone();
            this.idx = N - 1;
            if (this.idx >= 0) {
                StdRandom.shuffle(this.seq, 0, idx);
            }
        }

        @Override
        public boolean hasNext() {
            return idx >= 0;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No hay mas elementos");
            }
            return seq[idx--];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

    }

    /**
     * unit testing.
     * 
     * @param args
     */
    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<>();
        int ope, value;
        while (!StdIn.isEmpty()) {
            ope = StdIn.readInt();
            if (ope == 1) { // Agregar elemento
                value = StdIn.readInt();
                rq.enqueue(value);
            } else if (ope == 2) { // Eliminar elemento
                StdOut.println(rq.dequeue());
            } else if (ope == 3) { // Obtener elemento
                StdOut.println(rq.sample());
            } else if (ope == 4) { // Saber Tamano
                StdOut.println(rq.size());
            } else if (ope == 5) { // Saber si esta vacia
                StdOut.println(rq.isEmpty());
            } else { // Iteradores
                StdOut.println("Primer Iterador");
                for (Integer num : rq) {
                    StdOut.println(num);
                }
                StdOut.println("Segundo Iterador");
                for (Integer num : rq) {
                    StdOut.println(num);
                }
                StdOut.println("Iteradores anidados");
                for (Integer num : rq) {
                    StdOut.println("1:" + num);
                    for (Integer num2 : rq) {
                        StdOut.println("2: " + num2);
                    }
                }
            }
        }
    }

}
