/*------------------------------------
 *   Author:       JFCowboy
 *   Written:      04/03/2016
 *   Last Updated: 04/03/2016
 * 
 *   
 * 
 *------------------------------------*/

import java.util.Iterator;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * @author JuanFelipe
 *
 */
public class Subset {

    /**
     * @param args
     */
    public static void main(String[] args) {
        int k = Integer.parseInt(args[ 0 ]);
        RandomizedQueue<String> ranQueue = new RandomizedQueue<>();
        while (!StdIn.isEmpty()) {
            ranQueue.enqueue(StdIn.readString());
        }
        Iterator<String> it = ranQueue.iterator();
        for (int i = 0; i < k; i++) {
            StdOut.println(it.next());
        }
    }

}
