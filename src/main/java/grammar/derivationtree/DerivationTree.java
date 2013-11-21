/**
 * 
 */
package grammar.derivationtree;

import grammar.buildJtigGrammar.ElementaryTree;

import java.io.OutputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.mxgraph.view.mxGraph;

import parser.lookup.ActivatedElementaryTree;

/**
 * 
 * @author Fabian Gallenkamp
 */
public class DerivationTree {
	/**
	 * All {@link ElementaryTree} occurring in the {@link IndependentDerivationTree} and
	 * the Object it's printed into
	 */
	protected Map<ActivatedElementaryTree, Object> nodesprinted;

	/**
	 * Connections between two {@link ElementaryTree}'s with the underlying
	 * operation details (see {@link DerivationEdge},
	 * {@link SubstitutionDerivationEdge},{@link AdjunctionDerivationEdge}).
	 */
	protected List<DerivationEdge> edges;

	private long index = -1;
	
	public DerivationTree() {
		this.edges = new LinkedList<DerivationEdge>();
		this.nodesprinted = new HashMap<ActivatedElementaryTree, Object>();
	}

	public DerivationTree(Map<ActivatedElementaryTree, Object> nodesprinted,List<DerivationEdge> edges) {
		this.edges = edges;
		this.nodesprinted = nodesprinted;
	}
	
	public void addDerivation(DerivationEdge edge) {
		// add trees, if not present
		if (!nodesprinted.containsKey(edge.first))
			nodesprinted.put(edge.first, null);
		if (!nodesprinted.containsKey(edge.second))
			nodesprinted.put(edge.second, null);
		// add edges
		edges.add(edge);
	}
	
	public void removeDerivation(AdjunctionDerivationEdge toremove) {
		edges.remove(toremove);
	}
	
	public void storeToXML(OutputStream stream, String comment) {

	}
	
	public void paintWithJGraphX(mxGraph graph) {
		resetNodesPrinted();
		Object parent = graph.getDefaultParent();
		// print nodes
		for (Entry<ActivatedElementaryTree, Object> eset : nodesprinted.entrySet()) {
			String description = eset.getKey().toString();
			Object o = graph.insertVertex(parent, description,	description, 0, 0, description.length() * 7, 20);
			eset.setValue(o);
		}
		// sort edges from left to right
		int i = 1;
		Collections.sort(edges,new Comparator<DerivationEdge>() {
			@Override
			public int compare(DerivationEdge o1, DerivationEdge o2) {
				return o1.second.getLeft() - o2.second.getLeft() ;
			}
		});
		// print them
		for (DerivationEdge edge : edges) {
			String description = edge instanceof SubstitutionDerivationEdge ? "Substitution": "Adjunction";
			graph.insertEdge(parent, Integer.toString(i++), description+"\n"+Arrays.toString(edge.getConnector()),nodesprinted.get(edge.first), nodesprinted.get(edge.second));
		}
	}
	
	private void resetNodesPrinted() {
		if (nodesprinted == null)
			return;
		for (Entry<ActivatedElementaryTree, Object> eset : nodesprinted.entrySet()) {
			eset.setValue(null);
		}
	}
	
	public IndependentDerivationTree copy() {
		Map<ActivatedElementaryTree, Object> copynodesprinted = new HashMap<ActivatedElementaryTree, Object>();
		copynodesprinted.putAll(nodesprinted);
		List<DerivationEdge> copyedges = new LinkedList<DerivationEdge>();
		copyedges.addAll(edges);
		return new IndependentDerivationTree( copynodesprinted,	copyedges);
	}
	
	public void copyFrom(IndependentDerivationTree derivtree){
		this.nodesprinted = new HashMap<ActivatedElementaryTree, Object>();
		this.nodesprinted.putAll(derivtree.nodesprinted);
		this.edges = new LinkedList<DerivationEdge>();
		this.edges.addAll(derivtree.edges);
	}
	
	public void setIndex(long index){
		this.index = index;
	}
	public long getIndex() {
		return index;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DerivationTree [nodesprinted="
				+ nodesprinted + ", edges=" + edges + "]";
	}
}
