/**
 * 
 */
package grammar.buildJtigGrammar;

import java.util.List;

import parser.early.Item;

/**
 * A grammatical rule in the tree insertion grammar. 
 * Implicitly describes a tree with nodes of several types. 
 * These trees can be combined with two operations adjunction and substitution.
 * Consists of an list of context free grammar rules (also production rules) (see {@link Layer}), 
 * an unique index, an lexical anchor,...
 * @author Fabian Gallenkamp
 */
public class TIGRule {
	
	/**
	 * Unique index in the language of this TIG-rule.
	 */
	private long index;
	
	/**
	 * CFG-production-rules describing implicitly the TIG-rule tree.
	 */
	private List<Layer> productionrules;
	
	/**
	 * A lexical anchor is a lexical index (one or more words/units), the tree is connected to.
	 */
	private List<String> lexicalanchors;

	/**
	 * Frequency in which this TIG-rule occurs in the language.
	 */
	private long freq;
	
	/**
	 * Probability in which this TIG-rule occurs in language.
	 */
	private double prob;
	
	/**
	 * Gorn-number of the layer with the leaf-node where the adjunction happens.
	 * Null if the TIG rule dosn't represent an auxiliary tree and therefore couldn't 
	 */
	private int[] spine;

	/**
	 * Constructs a rule tree.
	 * @param index {@link #index}
	 * @param layers {@link #layers}
	 * @param lexicalanchors {@link #lexicalanchors}
	 * @param freq {@link #freq}
	 * @param prob {@link #prob}
	 * @param spine {@link #spine}
	 */
	public TIGRule(long index,List<Layer> productionrules, List<String> lexicalanchors,long freq, double prob,int[] spine){
		this.index = index;
		this.productionrules = productionrules;
		this.lexicalanchors = lexicalanchors;
		this.freq = freq;
		this.prob = prob;
		this.spine = spine;
	}
	
	public List<String> getlexicalanchors(){
		return lexicalanchors;
	}
	
	
	/**
	 * @return the index
	 */
	public long getIndex() {
		return index;
	}

	/**
	 * @return the layers
	 */
	public List<Layer> getProductionRules() {
		return productionrules;
	}

	/**
	 * @return the freq
	 */
	public long getFreq() {
		return freq;
	}

	/**
	 * @return the prob
	 */
	public double getProb() {
		return prob;
	}

	/**
	 * @return the spine
	 */
	public int[] getSpine() {
		return spine;
	}
	
	/**
	 * @return the nonterminal on the left side of the first production rule
	 */
	public String getRootSymbol() {
		if (this.productionrules.size() <= 0)
			throw new IllegalArgumentException("Rule tree hasn't any production rules.");
		Layer l = this.productionrules.get(0);
		Entry e = l.getEntry(0);
		if (e != null)
			return e.getLabel();
		return null;
	}
	
	@Override
	public boolean equals(Object obj){
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TIGRule other = (TIGRule) obj;
		if (other.index != this.index)
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(index);
		return sb.toString();
	}
}
