/**
 * 
 */
package grammar.buildJtigGrammar;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import tools.tokenizer.Token;

/**
 * A recursive data structure for accessing efficiently several {@link TIGRule}'s.
 * Basically, it consists of a {@link HashMap} where all {@link TIGRule}'s with a given {@link String}-key are
 * stored. Because the {@link TIGRule}'s can describe a chain of {@link String}'s, 
 * the value associated  with the first {@link String}-key is again a {@link Lexicon}-object.
 * @author Fabian Gallenkamp
 */
public class Lexicon {

	private List<ElementaryTree> content;
	
	private HashMap<String,Lexicon> entrys;
	
	private Set<String> startsymbols;
	
	public Lexicon() {
		content = new LinkedList<ElementaryTree>();
		entrys = new HashMap<String,Lexicon>();
	}
	
	public void add(ElementaryTree toadd){
		List<String> remaining =  new LinkedList<String>();
		remaining.addAll(toadd.getLexicalAnchors());
		add (toadd,remaining);
	}
	
	private void add(ElementaryTree toadd,List<String> remaining){
		if (remaining.size() <= 0)
			return;
		
		String anchor = remaining.get(0);
		Lexicon sublexicon = entrys.get(anchor);
		
		if (sublexicon == null){
			sublexicon = new Lexicon();
			entrys.put(anchor,sublexicon);
		}
		
		if (remaining.size() > 1){
			remaining.remove(0);
			sublexicon.add(toadd, remaining);
		} else {
			sublexicon.content.add(toadd);
		}
	}
	
	public List<ElementaryTree> find(List<Token> index,int pos){
		if (index.size() == 0)
			return this.content;
		Lexicon found = this.entrys.get(index.get(pos).getLabel());
		if (found == null)
			return new LinkedList<ElementaryTree>();
		if (pos < index.size() - 1){
			return found.find(index,pos+1);
		}
		return found.content;
	}

	public void setStartSymbols(Set<String> startsymbols){
		this.startsymbols = startsymbols;
	}
	public Set<String> getStartSymbols(){
		return this.startsymbols==null?new HashSet<String>():this.startsymbols;
	}
	public int size() {
		int i = this.content.size();
		for (Entry<String, Lexicon> l : this.entrys.entrySet()){
			i += l.getValue().size();
		}
		return i;
	}
	
	@Override
	public String toString() {
		return "Lexicon: {Startymbols"+this.getStartSymbols()+"}<\n" + toString(0)+">";
	}
	
	public String toString(int depth){
		StringBuilder sb = new StringBuilder();
		sb.append("");

		for (Entry<String, Lexicon> l : this.entrys.entrySet()){
			indent(sb,depth);
			sb.append("'"+l.getKey()+"'");
			sb.append(" ");
			Lexicon lex = l.getValue();
			sb.append(lex.content);
			if (lex.entrys.size() >0)
				sb.append(":");
			sb.append("\n");
			sb.append(l.getValue().toString(depth+1));
		}
		return sb.toString();
	}
	
	private void indent(StringBuilder sb, int i){
		for (int k=0;k < i;k++){
			sb.append("\t");
		}
	}
}
