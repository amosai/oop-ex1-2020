package ex1.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import ex1.src.WGraph_DS;
import ex1.src.WGraph_DS.node;
import ex1.src.node_info;
import ex1.src.weighted_graph;

class WGraph_DSTest {
	
	@Test
	void testWGraph_DS() {
		
		//fail("Not yet implemented");
	}

	@Test
	void testGetNode() {
		 weighted_graph g = new WGraph_DS();
		g.addNode(0);
		 try {
			 	 node_info i= g.getNode(0);
			 	System.out.println(i.getKey()); 
			 	assertEquals(0, i.getKey());
			} catch (Exception e) {
				System.out.println();
			}
	
		
	}

//	@Test
//	void testEqualsObject() {
//		//fail("Not yet implemented");
//	}

	@Test
	void testHasEdge() {
		
		WGraph_DS a=new WGraph_DS();
		a.addNode(1);
		a.addNode(2);
		a.addNode(3);
		a.connect(1, 2, 12);
		
		assertFalse(!a.hasEdge(1, 2));
		assertFalse(!a.hasEdge(2, 1));
		assertFalse(a.hasEdge(3, 1));
		assertFalse(a.hasEdge(1, 3));//
		assertFalse(a.hasEdge(1, 4));//4 not in graph
		//System.out.println(a.hasEdge(4, 1));
		//System.out.println("e");
	}

	@Test
	void testGetEdge() {
		WGraph_DS a=new WGraph_DS();
		a.addNode(1);
		a.addNode(2);
		a.addNode(3);
		a.connect(1, 2, 12.7);
		assertEquals(12.70, a.getEdge(1,2));
		assertEquals(12.7, a.getEdge(2,1));
		assertEquals(-1, a.getEdge(1,3));///not have edge between but the nodes in graph
		assertEquals(-1, a.getEdge(1,4));/// the 4 node not in graph
	}

	@Test
	void testAddNode() {
		WGraph_DS a=new WGraph_DS();
		a.addNode(1);
		a.addNode(2);
		a.addNode(1);
		assertEquals(2, a.nodeSize());
		a.removeNode(1);
		assertEquals(1, a.nodeSize());
		//a.nodeSize();
	}

	@Test
	void testConnect() {
		WGraph_DS a=new WGraph_DS();
		a.addNode(1);
		a.addNode(2);
		a.connect(1, 2, 12.7);
		a.connect(1, 2, 12.7);
		assertEquals(2, a.nodeSize());
		System.out.println(a.getEdge(1, 2));
		a.connect(1, 2, 12.7);
		assertEquals(2, a.nodeSize());
		a.removeNode(1);
		assertEquals(1, a.nodeSize());
	}

	@Test
	void testGetV() {
		fail("Not yet implemented");
	}

	@Test
	void testGetVInt() {
		fail("Not yet implemented");
	}

	@Test
	void testRemoveNode() {
		fail("Not yet implemented");
	}

	@Test
	void testRemoveEdge() {
		fail("Not yet implemented");
	}

	@Test
	void testNodeSize() {
		fail("Not yet implemented");
	}

	@Test
	void testEdgeSize() {
		//fail("Not yet implemented");
	}

	@Test
	void testGetMC() {
		//fail("Not yet implemented");
	}

}
