package ex1.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.Test;

import ex1.src.WGraph_Algo;
import ex1.src.WGraph_DS;
import ex1.src.node_info;
import ex1.src.weighted_graph;



class MyWGATest {

	private WGraph_DS connectGraph() {
		WGraph_DS graph = new WGraph_DS();
		graph.addNode( 1);
		graph.addNode(2);
		graph.addNode(3);
		graph.connect(1, 3, 10);
		graph.connect(1, 2, 1);

		graph.connect(2, 3, 1);
		graph.connect(3, 1, 10);
		return graph;
	}

	private WGraph_DS notConnectGraph() {
		WGraph_DS graph = new WGraph_DS();
		graph.addNode( 1);
		graph.addNode(2);
		graph.addNode(3);
		graph.connect(1, 3, 1);
		graph.connect(1, 2, 1);


		return graph;
	}

	@Test
	void testInitGraph() {
		fail("Not yet implemented");
	}

	//	@Test
	//	void testInitString() {//test copy implement
	//		Graph_Algo algo = new Graph_Algo(null);
	//		algo.init("test.txt");
	//		System.out.println( algo.g );
	//		fail("Not yet implemented");
	//	}
	//	@Test
	//	void testSave() {//test copy implement
	//		DGraph graph = connectGraph();
	//		Graph_Algo algo = new Graph_Algo(graph);
	//		
	//		//algo.save("test.txt");
	//		fail("Not yet implemented");
	//	}

	@Test
	void testIsConnected() {
		WGraph_DS conGraph = connectGraph();//
		WGraph_DS NConGraph = notConnectGraph();

		WGraph_Algo algo = new   WGraph_Algo();
		algo.init(conGraph);
		assertEquals(true, algo.isConnected());
		algo.init(NConGraph);
		assertEquals(false, algo.isConnected());
	}

	@Test
	void testShortestPathDist() {
		//(1) --> 3 --> (2)
		WGraph_DS graph = new WGraph_DS();
		graph.addNode(1);
		graph.addNode(2);
		graph.connect(1, 2, 3);
		WGraph_Algo algo = new   WGraph_Algo();
		algo.init(graph);
		double dist = algo.shortestPathDist(1, 2);
		assertEquals(3, dist);

		graph = connectGraph();
		algo.init(graph);
		dist = algo.shortestPathDist(1, 3);
		assertEquals(2, dist);




	}

	@Test
	void testShortestPath() {
		WGraph_DS graph = new WGraph_DS();
		graph.addNode(1);

		graph.addNode(3);
		graph.addNode(4);

		graph.connect(1, 3, 5);
		graph.connect(3, 4, 5);
		graph.connect(4, 1, 3);

		graph.addNode(2);
		graph.connect(1, 2, 70);
		graph.connect(2, 3, 10);
		WGraph_Algo algo = new   WGraph_Algo();
		algo.init(graph);
		List<node_info> l = algo.shortestPath(1, 4);

		assertEquals("[Node [key=4, location=null], Node [key=3, location=null], Node [key=1, location=null]]",l.toString());


		//	
	}

	@Test
	void testTSP() {
		WGraph_DS graph = new WGraph_DS();
		graph.addNode(1);

		graph.addNode(3);
		graph.addNode(4);
		graph.addNode(5);
		graph.connect(1, 3, 5);
		graph.connect(3, 4, 5);
		graph.connect(4, 5, 3);
		graph.connect(5, 1, 3);
		graph.addNode(2);

		graph.connect(1, 2, 1);
		graph.connect(2, 3, 1);
		WGraph_Algo algo = new WGraph_Algo();
		algo.init(graph);
		List<Integer> targets=new ArrayList<Integer>(); 
		targets.add(1);targets.add(3);targets.add(5);
		//System.out.println(algo.TSP(targets));
		//List<node_info> path=algo.TSP(targets);
        
//         System.out.println(path); 
         
		
}




@Test
void testCopy() {


	weighted_graph a = connectGraph();
	WGraph_Algo algo = new WGraph_Algo();
	algo.init(a);
	weighted_graph b = algo.copy();

	assertEquals(a.toString(), b.toString());
	b.addNode(4);
	b.connect(3, 4, 1);
	assertNotEquals(a.toString(), b.toString());
}

}
