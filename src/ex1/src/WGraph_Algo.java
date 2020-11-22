package ex1.src;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;


public class WGraph_Algo implements weighted_graph_algorithms {
	
	weighted_graph graph;
	
	private HashMap<Integer, Double> dist;
	private HashMap<Integer, Integer> prev;
	
	
	
	/**
     * Init the graph on which this set of algorithms operates on.
     * @param g
     */
	@Override
	public void init(weighted_graph g) {
		this.graph = g;
		
		this.dist = new HashMap<>();
		this.prev = new HashMap<>();
	}

    /**
     * Return the underlying graph of which this class works.
     * @return
     */
	@Override
	public weighted_graph getGraph() {
		return this.graph;
	}

    /**
     * Compute a deep copy of this weighted graph.
     * @return
     */
	@Override
	public weighted_graph copy() {

		weighted_graph deep_copy = new WGraph_DS();
		
		for (node_info vertex : this.graph.getV()) {			
			//new node based on same KEY.
			//add to the new graph (deep_copy)
			
			deep_copy.addNode(vertex.getKey());
			
			node_info node = deep_copy.getNode(vertex.getKey());
			
			node.setInfo(vertex.getInfo());
			node.setTag(vertex.getTag());
			
		}
		
		
		//connect all new nodes in the new graph
		//based on the old connections in the old graph
		
		for (node_info vertex : this.graph.getV()) { //for each vertex in graph
			
			for (node_info neighbor : this.graph.getV(vertex.getKey())) { //for each neighbor of vertex
				
				deep_copy.connect(vertex.getKey(), neighbor.getKey(),
									this.graph.getEdge(vertex.getKey(), neighbor.getKey()));
				
			}
			
		}
		
		
		//update all new nodes information:
		//update weight, tag, info
		
		return deep_copy;
	}

	
	public void djikstra(int source) {
		//google "djikstra psuedo code" (wikipedia)
		//https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm
		
		//create priority Queue
		PriorityQueue<node_info> q = new PriorityQueue<>();
		
		for (node_info vertex : this.graph.getV()) { //for each node in graph
			
			if (vertex.getKey() == source) { 
			
				this.dist.put(source, 0.0); //dist[source] -> 0
				vertex.setTag(0.0); //for comperable in priority queue
				
			}
			
			else {
				
				this.dist.put(vertex.getKey(), Double.MAX_VALUE); //dist[node] -> inf
				this.prev.put(vertex.getKey(), null); //prev[node] -> null
				vertex.setTag(Double.MAX_VALUE); //for comperable in priority queue
			}
			
			q.add(vertex);
		}
		
		//add source to Q
		
		while( !q.isEmpty() ) { //Q is not empty
			
			node_info u = q.poll(); // Q.poll(); minheap.poll()
			
			for (node_info neighbor : this.graph.getV(u.getKey())) { //for each neighbor of node
				
				
				double alt = this.dist.get(u.getKey()) + this.graph.getEdge(u.getKey(), neighbor.getKey());
				
				if (alt < this.dist.get(neighbor.getKey())) {
					
					this.dist.put(neighbor.getKey(), alt);
					this.prev.put(neighbor.getKey(), u.getKey());
					
					neighbor.setTag(alt);
					
					q.remove(neighbor); //remove neighbor from Q
					q.add(neighbor); //insert into correct place (decrease priority)
					//https://stackoverflow.com/questions/1871253/updating-java-priorityqueue-when-its-elements-change-priority

				}
				
			}
			
		}
		
	}
	
	
    /**
     * Returns true if and only if (iff) there is a valid path from EVREY node to each
     * other node. NOTE: assume ubdirectional graph.
     * @return
     */
	@Override
	public boolean isConnected() {
		
		if (this.graph.nodeSize() == 1 || this.graph.nodeSize() == 0)
			return true;
		
		List<node_info> list = new ArrayList<>(this.graph.getV());
		djikstra(list.get(0).getKey());
		
		for (Double dist : this.dist.values()) {
			if (dist == Double.MAX_VALUE) 
				return false;
		}
		
		return true;
	}

    /**
     * returns the length of the shortest path between src to dest
     * Note: if no such path --> returns -1
     * @param src - start node
     * @param dest - end (target) node
     * @return
     */
	@Override
	public double shortestPathDist(int src, int dest) {
		djikstra(src);
		return this.dist.get(dest);
	}

    /**
     * returns the the shortest path between src to dest - as an ordered List of nodes:
     * src--> n1-->n2-->...dest
     * see: https://en.wikipedia.org/wiki/Shortest_path_problem
     * Note if no such path --> returns null;
     * @param src - start node
     * @param dest - end (target) node
     * @return
     */
	@Override
	public List<node_info> shortestPath(int src, int dest) {
		
	   List<node_info> res = new ArrayList<>();
	
	   List<node_info> back_res = new ArrayList<>();
	   
	   djikstra(src);
	   
	   int current = dest;
	   
	   res.add(this.graph.getNode(current));
	   
	   while (true) {
	   	
	   	if (current == src) {
	   		break;
	   	}
	   	
	   	res.add(this.graph.getNode(this.prev.get(current)));
	   	current = this.prev.get(current);     	
	  	
	   }
	   
	   for (int i = res.size() - 1; i >= 0; i--) {
			back_res.add(res.get(i));
		}
	     
	   return back_res;
	}

    /**
     * Saves this weighted (undirected) graph to the given
     * file name
     * thanks to "https://stackoverflow.com/questions/2744962/load-store-objects-in-file-in-java"
     * @param file - the file name (may include a relative path).
     * @return true - iff the file was successfully saved
     */
	@Override
	public boolean save(String file) {
		try {
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(this.graph);
//            oos.writeObject(bookedTickets);
//            oos.writeObject(baggage);
            oos.close();
           
        } catch (IOException ex) {
            return false;
        }
        return true;
	
	}
	       
	 /**
     * This method load a graph to this graph algorithm.
     * if the file was successfully loaded - the underlying graph
     * of this class will be changed (to the loaded one), in case the
     * graph was not loaded the original graph should remain "as is".
     * @param file - file name
     * @return true - iff the graph was successfully loaded.
     *  thanks to "https://stackoverflow.com/questions/2744962/load-store-objects-in-file-in-java"
     */
	@Override
	public boolean load(String file) {
		 
		 try {
	            FileInputStream fis = new FileInputStream(file);
	            ObjectInputStream ois = new ObjectInputStream(fis);
	            this.graph = (weighted_graph)ois.readObject();
//	            this.bookedTickets = (HashSet<Ticket>)ois.readObject();
//	                this.baggage = (HashMap<Ticket,ArrayList<Object>>)ois.readObject();
	            ois.close();
	           // fis.close();
	        } catch (IOException e) {
	            return false;
	        } catch (ClassNotFoundException e) {
	            return false;
	        }
	        return true;
	    }
}
	

//package ex1;
//
//import java.util.*;
//
//public class WGraph_Algo implements weighted_graph_algorithms {
//	
//	weighted_graph graph;
//	
//	private HashMap<Integer, Double> dist;
//	private HashMap<Integer, Integer> prev;
//	
//	
//	
//	/**
//     * Init the graph on which this set of algorithms operates on.
//     * @param g
//     */
//	@Override
//	public void init(weighted_graph g) {
//		this.graph = g;
//		
//		this.dist = new HashMap<>();
//		this.prev = new HashMap<>();
//	}
//
//    /**
//     * Return the underlying graph of which this class works.
//     * @return
//     */
//	@Override
//	public weighted_graph getGraph() {
//		return this.graph;
//	}
//
//    /**
//     * Compute a deep copy of this weighted graph.
//     * @return
//     */
//	@Override
//	public weighted_graph copy() {
//
//		weighted_graph deep_copy = new WGraph_DS();
//		
//		for (node_info vertex : this.graph.getV()) {			
//			//new node based on same KEY.
//			//add to the new graph (deep_copy)
//			
//			deep_copy.addNode(vertex.getKey());
//			
//			node_info node = deep_copy.getNode(vertex.getKey());
//			
//			node.setInfo(vertex.getInfo());
//			node.setTag(vertex.getTag());
//			
//		}
//		
//		
//		//connect all new nodes in the new graph
//		//based on the old connections in the old graph
//		
//		for (node_info vertex : this.graph.getV()) { //for each vertex in graph
//			
//			for (node_info neighbor : this.graph.getV(vertex.getKey())) { //for each neighbor of vertex
//				
//				deep_copy.connect(vertex.getKey(), neighbor.getKey(),
//									this.graph.getEdge(vertex.getKey(), neighbor.getKey()));
//				
//			}
//			
//		}
//		
//		
//		//update all new nodes information:
//		//update weight, tag, info
//		
//		return deep_copy;
//	}
//
//	
//	public void djikstra(int source) {
//		//google "djikstra psuedo code" (wikipedia)
//		//https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm
//		
//		//create Queue
//		
//		for (node_info vertex : this.graph.getV()) {
//			
//			if (vertex.getKey() == source) {
//			
//				this.dist.put(source, 0.0);
//				
//			}
//			
//			else {
//				
//				this.dist.put(vertex.getKey(), Double.MAX_VALUE);
//				this.prev.put(vertex.getKey(), null);
//				
//			}
//		}
//		
//		//add source to Q
//		
//		while( true ) { //Q is not empty
//			
//			node_info u = null; // Q.poll(); minheap.poll()
//			
//			for (node_info neighbor : this.graph.getV(u.getKey())) {
//				
//				double alt = this.dist.get(u.getKey()) + this.graph.getEdge(u.getKey(), neighbor.getKey());
//				
//				if (alt < this.dist.get(neighbor.getKey())) {
//					
//					this.dist.put(neighbor.getKey(), alt);
//					this.prev.put(neighbor.getKey(), u.getKey());
//					
//					//Q Q.decrease_priority(v, alt)
//					
//				}
//				
//			}
//			
//		}
//		
//	}
//	
//	
//    /**
//     * Returns true if and only if (iff) there is a valid path from EVREY node to each
//     * other node. NOTE: assume ubdirectional graph.
//     * @return
//     */
//	@Override
//	public boolean isConnected() {
////		djikstra(src);
//		//for each key in dist
//		//if dist.get(key) == Double.maxvalue - return false
//		return false;
//	}
//
//    /**
//     * returns the length of the shortest path between src to dest
//     * Note: if no such path --> returns -1
//     * @param src - start node
//     * @param dest - end (target) node
//     * @return
//     */
//	@Override
//	public double shortestPathDist(int src, int dest) {
//		djikstra(src);
//		return this.dist.get(dest);
//	}
//
//    /**
//     * returns the the shortest path between src to dest - as an ordered List of nodes:
//     * src--> n1-->n2-->...dest
//     * see: https://en.wikipedia.org/wiki/Shortest_path_problem
//     * Note if no such path --> returns null;
//     * @param src - start node
//     * @param dest - end (target) node
//     * @return
//     */
//	@Override
//	public List<node_info> shortestPath(int src, int dest) {
//		 
//		djikstra(src);
//		
//       List<node_info> res = new ArrayList<>();
//
//       List<node_info> back_res = new ArrayList<>();
//       
//       int current = dest;
//       
//       res.add(this.graph.getNode(current));
//       
//       while (true) {
//       	
//       	if (current == src) {
//       		break;
//       	}
//       	
//       	res.add(this.graph.getNode(this.prev.get(current)));
//       	current = this.prev.get(current);     	
//      	
//       }
//       
//       for (int i = res.size() - 1; i >= 0; i--) {
//			back_res.add(res.get(i));
//		}
//         
//       return back_res;
//	}
//
//    /**
//     * Saves this weighted (undirected) graph to the given
//     * file name
//     * @param file - the file name (may include a relative path).
//     * @return true - iff the file was successfully saved
//     */
//	@Override
//	public boolean save(String file) {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	/**
//     * This method load a graph to this graph algorithm.
//     * if the file was successfully loaded - the underlying graph
//     * of this class will be changed (to the loaded one), in case the
//     * graph was not loaded the original graph should remain "as is".
//     * @param file - file name
//     * @return true - iff the graph was successfully loaded.
//     */
//	@Override
//	public boolean load(String file) {
//		// TODO Auto-generated method stub
//		return false;
//	}
//	
//}
