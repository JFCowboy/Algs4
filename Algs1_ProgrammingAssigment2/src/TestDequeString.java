/*------------------------------------
 *   Author:       JFCowboy
 *   Written:      04/03/2016
 *   Last Updated: 04/03/2016
 * 
 *   
 * 
 *------------------------------------*/

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;


/**
 * Pruebas de unidad para la clase Deque.
 * Se usa para las pruebas el tipo de dato String.
 * 
 * @author JuanFelipe
 *
 */
public class TestDequeString {

	protected Deque<String> dq;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		dq = new Deque<>();
	}

	/**
	 * Test insertar 1 elemento al inicio.
	 */
	@Test(timeout=2000)
	public void testAddFirst() {
		String str = "Primer Items";
		int expectedSize = 1;
		
		dq.addFirst(str);
		assertEquals(dq.size(), expectedSize);
		assertEquals(dq.iterator().next(), str);
	}
	
	/**
	 * Test insertar 10 elementos al inicio.
	 */
	@Test(timeout=2000)
	public void testAddFirst10() {
		int TAM = 10;
		String[] expected = new String[TAM];
		String[] solution = new String[TAM];
		
		String aux;
		for (int i = 0; i < TAM; i++) {
			aux = "Ele" + i;
			dq.addFirst(aux);
			expected[TAM - i - 1] = aux;
		}
		
		int idx = 0;
		for(String str : dq){
			assertTrue(idx<=TAM);
			solution[idx++] = str;
		}
		assertArrayEquals(expected, solution);
	}
	
	/**
	 * Test Remover primero en lista vacias.
	 */
	@Test(timeout=2000, expected=NoSuchElementException.class)
	public void testRemoveFirstEmpty() {
		dq.removeFirst();
	}
	
	/**
	 * Test Remover final en lista vacias.
	 */
	@Test(timeout=2000, expected=NoSuchElementException.class)
	public void testRemoveLastEmpty() {
		dq.removeLast();
	}
	
	/**
	 * Test Insertar Elemento null.
	 */
	@Test(timeout=2000, expected=NullPointerException .class)
	public void testAddNull() {
		dq.addFirst("Primero");
		dq.addFirst(null);
	}
	
	/**
	 * Test Insertar Elemento null en Deque vacio.
	 */
	@Test(timeout=2000, expected=NullPointerException .class)
	public void testAddEmptyNull() {		
		dq.addFirst(null);
	}
	
	/**
	 * Test Insertar Elemento null.
	 */
	@Test(timeout=2000, expected=NullPointerException .class)
	public void testAddLastNull() {
		dq.addLast("Primero");
		dq.addLast(null);
	}
	
	/**
	 * Test Insertar Elemento null en Deque vacio.
	 */
	@Test(timeout=2000, expected=NullPointerException .class)
	public void testAddLastEmptyNull() {		
		dq.addLast(null);
	}
}
