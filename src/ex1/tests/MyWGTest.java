package ex1.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;


import ex1.src.WGraph_DS;
import ex1.src.WGraph_DS.node;
import ex1.src.node_info;
import ex1.src.weighted_graph;

class MyWGTest {

	@Test
	void testGetNode() {
		int n1=1;
		//Node n2=new Node;
		ex1.src.WGraph_DS a=new WGraph_DS();
		WGraph_DS b=new WGraph_DS();
		a.addNode(n1);
		//Node n3=new a.getNode(1);
		assertEquals(n1,a.getNode(1) );
	}

//	@Test
//	void testGetEdge() {
//		node n1=new Node ( 1);
//		Node n2=new Node ( 2);
//		//Node n2=new Node;
//		WGraph_DS a=new WGraph_DS();
//		a.addNode(n1);
//		a.addNode(n2);
//		a.connect(n1.getKey(), n2.getKey(), 4.5);
//		Edge e=new Edge ( 1,2,4.5);
//		a.getEdge(1, 2);
//		assertEquals(e.toString(),a.getEdge(1, 2).toString() );//without to string its not make it 
//
//
//	}

	@Test
	void testAddNode() {
		
		 weighted_graph g = new WGraph_DS();
	        g.addNode(0);
	        
		fail("Not yet implemented");
	}

	@Test
	void testToString() {
		fail("Not yet implemented");
	}

	@Test
	void testConnect() {
		WGraph_DS a=new  WGraph_DS();
		
		 a.addNode(1);
		 a.addNode(2);
		 a.addNode(3);
		
		a.connect(1, 2, 4.5);
//		Edge e=new Edge ( 1,2,4.5);
//		assertEquals(e.toString(),a.getEdge(1, 2).toString() );//without to string its not make it 
		//		//System.out.println(a.getEdge(1, 2));
		//		a.addNode(n3);
		//		System.out.println(a);
		//		a.connect(2, 3, 6);
		//		System.out.println(a);
		//		a.connect(1, 2, 4.5);
		//		System.out.println(a);
		//		System.out.println(a.getEdge(1,2));
		//		System.out.println(n1);
	}

	@Test
	void testGetV() {
		fail("Not yet implemented");
	}

	@Test
	void testGetE() {
		fail("Not yet implemented");
	}

	@Test
	void testRemoveNode() {
		WGraph_DS a=new  WGraph_DS();
		
		 a.addNode(1);
		 a.addNode(2);
		 a.addNode(3);
		

		
		a.connect(1, 2, 4.5);
		a.connect(2, 3, 7);
		a.removeNode(2);
		
		//	System.out.println(a.toString());
		//		System.out.println(a.removeNode(2));
		//		System.out.println(a.toString());
		/// do assert......

	}

	@Test
	void testRemoveEdge() {
		WGraph_DS a=new  WGraph_DS();
		
		 a.addNode(1);
		 a.addNode(2);
		 a.addNode(3);


		a.connect(1, 2, 4.5);
		a.connect(2, 3, 7);
		
		System.out.println(a.toString());
		a.removeEdge(2, 3);
			//System.out.println(a.removeNode(2));
			System.out.println(a.toString());;
	}

	@Test
	void testNodeSize() {
		WGraph_DS a=new  WGraph_DS();
		
		 a.addNode(1);
		 a.addNode(2);
		 a.addNode(3);

		a.connect(1, 2, 4.5);
		a.connect(2, 3, 7);
		
		assertEquals(3,a.nodeSize());
	}

	@Test
	void testEdgeSize() {
		WGraph_DS a=new  WGraph_DS();
		
		 a.addNode(1);
		 a.addNode(2);
		 a.addNode(3);

		a.connect(1, 2, 4.5);
		a.connect(2, 3, 7);
		a.removeNode(1);
		assertEquals(1, a.edgeSize());
	}

	@Test
	void testGetMC() {
		WGraph_DS a=new  WGraph_DS();
		
		 a.addNode(1);
		 a.addNode(2);
		 a.addNode(3);

		a.connect(1, 2, 4.5);
		a.connect(2, 3, 7);
		
		assertEquals(5,a.getMC());
	}

}
