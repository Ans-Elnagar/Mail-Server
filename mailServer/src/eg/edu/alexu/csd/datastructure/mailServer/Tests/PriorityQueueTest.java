package eg.edu.alexu.csd.datastructure.mailServer.Tests;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import eg.edu.alexu.csd.datastructure.mailServer.LogicClasses.PriorityQueue;

class PriorityQueueTest {
	PriorityQueue PQ = new PriorityQueue();
	@Test
	void checkInsert() {
		PQ.insert(64, 5);
		PQ.insert(625, 4);
		PQ.insert(132, 1);
		PQ.insert(121, 4);
		PQ.insert(444, 15);
		int[] order= {132,625,121,64,444};
		for(int i=0;i<order.length;i++)
			assertEquals(PQ.removeMin(),order[i]);
		PQ.insert(11, 6);
		assertEquals(PQ.min(),11);
	}

}
