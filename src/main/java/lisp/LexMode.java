/**
 * 
 */
package lisp;
/**
 * Defines the different states of the lexer in the {@link LispParser}.
 * @author Fabian Gallenkamp
 */
public enum LexMode {
	DEFAULT,STRING,ATTRIBUTE,COMMENT,INT,FLOAT
}
