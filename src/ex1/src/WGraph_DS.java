
package ex1.src;

import java.io.Serializable;
import java.util.*;

public class WGraph_DS implements weighted_graph,Serializable {
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HashMap<Integer, node_info> nodes; //list of vertexes in graph
	private HashMap<Integer, HashMap<Integer, Double>> weights; // { 5 = { 456 = n1 } } arraylist<n1.key>
//	private HashMap<Integer, HashMap<Integer, node_info>> neighbors;
	
	private int mode_count;
	private int edge_count;
	
	public class node implements node_info, Comparable<node_info>,Serializable  {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private int key;
		private String info;
		private Double tag;
		
		public node(int key) {
			// TODO Auto-generated constructor stub
			this.key = key;
			this.info = "";
			this.tag = 0.0;
		}
		
	    /* (non-Javadoc)
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode() {
			//result = prime * result + getOuterType().hashCode();
			return Objects.hash(info, key, tag);
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (!(obj instanceof node)) {
				return false;
			}
			node other = (node) obj;
			return Objects.equals(info, other.info) && key == other.key && Objects.equals(tag, other.tag);
		}

		/**
	     * Return the key (id) associated with this node.
	     * Note: each node_data should have a unique key.
	     * @return
	     */
		@Override
		public int getKey() {
			return this.key;
		}

	    /**
	     * return the remark (meta data) associated with this node.
	     * @return
	     */
		@Override
		public String getInfo() {
			return info;
		}

	    /**
	     * Allows changing the remark (meta data) associated with this node.
	     * @param s
	     */
		@Override
		public void setInfo(String s) {
			this.info = s;
		}

	    /**
	     * Temporal data (aka distance, color, or state)
	     * which can be used be algorithms
	     * @return
	     */
		@Override
		public double getTag() {
			return this.tag;
		}

	    /**
	     * Allow setting the "tag" value for temporal marking an node - common
	     * practice for marking by algorithms.
	     * @param t - the new value of the tag
	     */
		@Override
		public void setTag(double t) {
			this.tag = t;
		}

		@Override
		public String toString() {
			return "";
		}

		@Override
		public int compareTo(node_info o) {
			Double w1 = this.getTag();
			Double w2 = o.getTag();
			
			if (w1 > w2) //o1 > o2
				return 1;
			
			else if(w1 < w2) //o1 < o2
				return -1;
			
			return 0; //o1 == o2
		}

		private WGraph_DS getOuterType() {
			return WGraph_DS.this;
		}
		
	}
	
	// ~~~ GRAPH ~~~
	
	public WGraph_DS() {
		// TODO Auto-generated constructor stub
		this.nodes = new HashMap<>();
		this.weights = new HashMap<>();
//		this.neighbors = new HashMap<>();
		
		this.edge_count = 0;
		this.mode_count = 0;
	}
	
    /**
     * return the node_data by the node_id,
     * @param key - the node_id
     * @return the node_data by the node_id, null if none.
     */
	@Override
	public node_info getNode(int key) {
		return this.nodes.get(key);
	}
	/**
     * return  Collection<Integer> of the nodes in graph by the node_id,
     * 
     * @param 
     * @return 
     */
	public Collection<Integer> getNodes() {
		return this.nodes.keySet();
	}
	
    /* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return Objects.hash(edge_count, mode_count, nodes, weights);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			
			return false;
		}
		if (!(obj instanceof weighted_graph)) {
			
			return false;
		}
		
		WGraph_DS other = (WGraph_DS) obj;
		return edge_count == (other.edge_count) &&mode_count == other.mode_count && Objects.equals(nodes, other.nodes)
			&& Objects.equals(weights, other.weights);
	}

	/**
     * return true iff (if and only if) there is an edge between node1 and node2
     * Note: this method should run in O(1) time.
     * @param node1
     * @param node2
     * @return
     */
	@Override
	public boolean hasEdge(int node1, int node2) {
		
		if (this.nodes.containsKey(node1) && this.nodes.containsKey(node2)) { //o(1) contains		
			if (node1 != node2) {
				
				if (this.nodes.get(node1) != null && this.nodes.get(node2) != null)
					
					return this.weights.get(node1).containsKey(node2);
			}
		}
		
		return false;
	}

    /**
     * return the weight if the edge (node1, node2) exist. In case
     * there is no such edge - should return -1
     * Note: this method should run in O(1) time.
     * @param node1
     * @param node2
     * @return
     */
	@Override
	public double getEdge(int node1, int node2) {
		
		if (this.nodes.containsKey(node1) && this.nodes.containsKey(node2)) { //o(1) contains		
			if (node1 != node2) //same node
				if (this.nodes.get(node1) != null || this.nodes.get(node2) != null) {
					
					if (hasEdge(node1, node2)) {
						
						this.mode_count++;
						
						return this.weights.get(node1).get(node2);
						
					}
					
				}
			
		}
		
		return -1;
	}

    /**
     * add a ****new node**** to the graph with the given key.
     * Note: this method should run in O(1) time.
     * Note2: if there is already a node with such a key -> no action should be performed.
     * @param key
     */
	@Override
	public void addNode(int key) {
		if (!this.nodes.containsKey(key)) {
			

			this.nodes.put(key, new node(key));
//			this.neighbors.put(key, new HashMap<Integer, node_info>());
			this.weights.put(key, new HashMap<Integer, Double>());
			
			this.mode_count++;
		}
	}
	
	
    /**
     * Connect an edge between node1 and node2, with an edge with weight >=0.
     * Note: this method should run in O(1) time.
     * Note2: if the edge node1-node2 already exists - the method simply updates the weight of the edge.
     */
	
	
	
	@Override
	public void connect(int node1, int node2, double w) {
		if (w >= 0){
			
			if (this.nodes.containsKey(node1) && this.nodes.containsKey(node2)) { //o(1) contains		
				if (node1 != node2) { //same node
					if (this.nodes.get(node1) != null && this.nodes.get(node2) != null) {
						
						if (!hasEdge(node1, node2) && !hasEdge(node2, node1)) { //if no edge
							
							this.weights.get(node1).put(node2, w);
							this.weights.get(node2).put(node1, w); //undirectional graph
							
//							this.neighbors.get(node1).put(node2, this.nodes.get(node2));
//							this.neighbors.get(node2).put(node1, this.nodes.get(node1)); //undirectional graph
							
							this.edge_count++; //only 1 edge => undirectional graph
							this.mode_count++;
							
						}
						else { //there is already an edge between node1->node2
							
							this.weights.get(node1).put(node2, w);
							this.weights.get(node2).put(node1, w); //undirectional graph
							
						}
						
						
					}
				}
			}
			
		}
	}

    /**
     * This method return a pointer (shallow copy) for a
     * Collection representing all the nodes in the graph.
     * Note: this method should run in O(1) tim
     * @return Collection<node_data>
     */
	@Override
	public Collection<node_info> getV() {
		return this.nodes.values();
	}

    /**
    *
    * This method returns a Collection containing all the
    * nodes connected to node_id
    * Note: this method can run in O(k) time, k - being the degree of node_id.
    * @return Collection<node_info>
    */
	@Override
	public Collection<node_info> getV(int node_id) {
		
		List<node_info> list = new ArrayList<>();
		
		for (Map.Entry<Integer, Double> entry : this.weights.get(node_id).entrySet()) { // Map<Integer,Double>
			list.add(getNode(entry.getKey()));
		}
		
		return list;
		
	}

    /**
     * Delete the node (with the given ID) from the graph -
     * and removes all edges which starts or ends at this node.
     * This method should run in O(n), |V|=n, as all the edges should be removed.
     * @return the data of the removed node (null if none).
     * @param key
     */
	@Override
	public node_info removeNode(int key) {
		
		if (this.nodes.containsKey(key)) {
			////////////////old///////////////////
			for (node_info neighbor : getV(key)) {
//				System.out.println("key is :"+key);
				
				removeEdge(key, neighbor.getKey()); //delete edge + neighbor
//				System.out.println("remove edge :" +key+"->"+neighbor.getKey());
//				System.out.println(this.toString());
			}
		////////////////////////////////////////////	
			////////new////////////
//			mode_count+=weights.get(key).size();
//			edge_count-=weights.get(key).size();
//			weights.get(key).clear();
		/////////////////////////////////
			this.weights.remove(key);
//			this.neighbors.remove(key);
			

			return this.nodes.remove(key);
			
		}
		
		return null;
	}

    /**
     * Delete the edge from the graph,
     * Note: this method should run in O(1) time.
     * @param node1
     * @param node2
     */
	@Override
	public void removeEdge(int node1, int node2) {
		
		if (this.nodes.containsKey(node1) && this.nodes.containsKey(node2)) {
			
			if (hasEdge(node1, node2) && hasEdge(node2, node1)) {
			
//				this.neighbors.get(node1).remove(node2);
//				this.neighbors.get(node2).remove(node1); //undirectional graph
				this.weights.get(node1).remove(node2);
				this.weights.get(node2).remove(node1); //undirectional graph
				
				this.mode_count++;
				this.edge_count--;
			}
		}
		
	}

    /** return the number of vertices (nodes) in the graph.
     * Note: this method should run in O(1) time.
     * @return
     */
	@Override
	public int nodeSize() {
		return getV().size();
		//return this.nodes.size();
	}

    /**
     * return the number of edges (undirectional graph).
     * Note: this method should run in O(1) time.
     * @return
     */
	@Override
	public int edgeSize() {
		// TODO Auto-generated method stub
		return this.edge_count;
	}

    /**
     * return the Mode Count - for testing changes in the graph.
     * Any change in the inner state of the graph should cause an increment in the ModeCount
     * @return
     */
	@Override
	public int getMC() {
		// TODO Auto-generated method stub
		return this.mode_count;
	}
	@Override
	public String toString() {
		return "WGraph [nodes" + nodes + ","  + "]";
	}
}

