
package cpcs302project;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

public class Cpcs302projects {
    //Array for reserved words
    static final String ResWords[] = {"abstract", "assert", "boolean", "break", "byte", "case", "catch", "char", "class", "const", "String",
        "continue", "default", "double", "do", "else", "enum", "extends", "false", "final", "finally", "float", "for", "goto", "if", "implements",
        "import", "instanceof", "int", "interface", "long", "native", "new", "null", "package", "private", "protected", "public", "return", "short",
        "static", "strictfp", "super", "switch", "synchronized", "this", "throw", "throws", "transient", "true", "try", "void", "volatile", "while"};

    // variables
    int ind = 0;
    Scanner input;
    int state = 0;
    String lookahead;
    char lexeme[];
    String temporaryString;
    PrintWriter output;
    // hashmap for tokens
    public static final HashMap<Integer, String> Tokens = new HashMap<Integer, String>();
    static {
        Tokens.put(2, "identifier");
        Tokens.put(3, "leftParenthesis");
        Tokens.put(4, "rightParenthesis");
        Tokens.put(5, "leftBracket");
        Tokens.put(6, "lightBracket");
        Tokens.put(7, "leftCurly");
        Tokens.put(8, "rightCurly");
        Tokens.put(9, "semiColon");
        Tokens.put(10, "colon");
        Tokens.put(11, "comma");
        Tokens.put(12, "questionMark");
        Tokens.put(13, "escapeCharacter");
        Tokens.put(14, "hash");
        Tokens.put(15, "tilde");
        Tokens.put(16, "atSign");
        Tokens.put(17, "backTick");
        Tokens.put(29, "logicalGreaterThan");
        Tokens.put(28, "logicalGreaterThanOrEqual");
        Tokens.put(32, "logicalLessThan");
        Tokens.put(31, "logicalLessThanOrEqual");
        Tokens.put(34, "remainderEqual");
        Tokens.put(35, "Remainder");
        Tokens.put(38, "bitAnd");
        Tokens.put(37, "logicalAnd");
        Tokens.put(41, "BitOr");
        Tokens.put(40, "logicalOr");
        Tokens.put(43, "notEqual");
        Tokens.put(42, "LogicalNot");
        Tokens.put(47, "incrementOp");
        Tokens.put(48, "plusOperator");
        Tokens.put(46, "incrementEqual");
        Tokens.put(51, "decrementOp");
        Tokens.put(52, "minusOperator");
        Tokens.put(50, "decrementEqual");
        Tokens.put(55, "multOperator");
        Tokens.put(54, "multEqual");
        Tokens.put(57, "divideEqual");
        Tokens.put(58, "divideOp");
        Tokens.put(25, "logicalEqual");
        Tokens.put(26, "assignOp");
        Tokens.put(63, "doubleQuote");
        Tokens.put(64, "String");
        Tokens.put(68, "single quotation");
        Tokens.put(69, "char");
        Tokens.put(23, "integerLiteral");
        Tokens.put(21, "floatLiteral");
        Tokens.put(19, "dot");
        Tokens.put(70, "other");

    }
/*
 * Initializes output writer linked to "output.txt" and input scanner linked to "input.txt".
 * Sets the input scanner to read one character at a time.
 * If "input.txt" is not found, prints an error message and exits the program.
 */

    public Cpcs302projects() throws FileNotFoundException, IOException {
        File outputfile = new File("output.txt");
        output = new PrintWriter(outputfile);
        try {
            File inputfile = new File("input.txt");
            this.input = new Scanner(inputfile);
            this.input.useDelimiter("");
            lexeme = new char[6000];

        } catch (FileNotFoundException exception) {
            System.out.println("File not found!");
            System.exit(0);
        }
    }
/*
 * Starts the lexical analysis process.
 * Prints headers "Lexemes" and "Tokens" both to console and output file, followed by a separator line.
 * Initiates reading of characters and processes them through finite automata.
 * Continues processing as long as the lookahead string is not empty.
 * Begins with a specific finite automata based on the initial state and continues with another as needed.
 * Closes the output file stream upon completion of the analysis.
 */

    public void start() {
        System.out.printf("%-20s%16s%n", "Lexemes", "Tokens");
        System.out.println("-----------------------------------------");
        output.printf("%-20s%16s%n", "Lexemes", "Tokens");
        output.println("-----------------------------------------");

        // ---------------------------------------------------------------------
        nextChar();
        boolean flag = true;
        while (!(lookahead.equals(""))) {
            if (state == 0) {
                finiteAutomataToBeginWith(lookahead);
                flag = false;
            }
            finiteAutomataToContinueWith(lookahead);
        }
        output.close();
    }
/*
 * Processes the initial character (lookahead) using finite automata principles.
 * Based on the character's pattern, updates the state and adds the character to the lexeme.
 * Special handling for whitespace (sets state to 0) and identifiable characters (changes state and adds to lexeme).
 * Calls addToLexeme() for recognized characters to build lexemes.
 * Advances to the next character after processing the current one.
 */

    public void finiteAutomataToBeginWith(String lookahead) {
        if (lookahead.matches("\\n|\\t|\\r")) {
            state = 0;
        } else if (lookahead.matches("[a-zA-Z$_]")) {
            state = 1;
            addToLexeme();
        } else if (lookahead.matches("\\(")) {
            state = 3;
            addToLexeme();
        } else if (lookahead.matches("\\)")) {
            state = 4;
            addToLexeme();
        } else if (lookahead.matches("\\[")) {
            state = 5;
            addToLexeme();
        } else if (lookahead.matches("\\[")) {
            state = 6;
            addToLexeme();
        } else if (lookahead.matches("\\{")) {
            state = 7;
            addToLexeme();
        } else if (lookahead.matches("\\}")) {
            state = 8;
            addToLexeme();
        } else if (lookahead.matches(";")) {
            state = 9;
            addToLexeme();
        } else if (lookahead.matches(":")) {
            state = 10;
            addToLexeme();
        } else if (lookahead.matches(",")) {
            state = 11;
            addToLexeme();
        } else if (lookahead.matches("\\?")) {
            state = 12;
            addToLexeme();
        } else if (lookahead.matches("\\\\")) {
            state = 13;
            addToLexeme();
        } else if (lookahead.matches("#")) {
            state = 14;
            addToLexeme();
        } else if (lookahead.matches("~")) {
            state = 15;
            addToLexeme();
        } else if (lookahead.matches("@")) {
            state = 16;
            addToLexeme();
        } else if (lookahead.matches("`")) {
            state = 17;
            addToLexeme();
        } else if (lookahead.matches("\\.")) {
            state = 18;
            addToLexeme();
        } else if (lookahead.matches("\\d")) {
            state = 22;
            addToLexeme();
        } else if (lookahead.matches("=")) {
            state = 24;
            addToLexeme();
        } else if (lookahead.matches(">")) {
            state = 27;
            addToLexeme();
        } else if (lookahead.matches("<")) {
            state = 30;
            addToLexeme();
        } else if (lookahead.matches("%")) {
            state = 33;
            addToLexeme();
        } else if (lookahead.matches("&")) {
            state = 36;
            addToLexeme();
        } else if (lookahead.matches("\\|")) {
            state = 39;
            addToLexeme();
        } else if (lookahead.matches("!")) {
            state = 42;
            addToLexeme();
        } else if (lookahead.matches("\\+")) {
            state = 45;
            addToLexeme();
        } else if (lookahead.matches("-")) {
            state = 49;
            addToLexeme();
        } else if (lookahead.matches("\\*")) {
            state = 53;
            addToLexeme();
        } else if (lookahead.matches("/")) {
            state = 56;
            addToLexeme();
        } else if (lookahead.matches("\"")) {
            temporaryString = lookahead;
            state = 62;
            addToLexeme();
        } else if (lookahead.matches("'")) {
            temporaryString = lookahead;
            state = 66;
            addToLexeme();
        } else {

        }

        nextChar();

    }
/*
 * Continues processing the lookahead character based on the current state of the finite automata.
 * Handles transitions between states for various language elements like identifiers, operators, and literals.
 * In each case, it may add the character to the current lexeme, print the lexeme, reset the state, or adjust the state based on the lookahead character.
 */

    public void finiteAutomataToContinueWith(String lookahead) {
        switch (state) {
            case 1: // IDENTIFIER
                if (lookahead.matches("[a-zA-Z_$]*")) {
                    state = 1;
                    addToLexeme();
                    nextChar();
                } else {
                    state = 2;
                    ungetc();
                    print();
                    state = 0;
                }
                break;

            case 2: // IDENTIFIER
                break;

            case 3: //PARENTHESIS
                print();
                break;

            case 4: // PARENTHESIS
                print();
                break;

            case 5: //BRACKET
                print();
                break;

            case 6: // BRACKET
                print();
                break;

            case 7: // CURLY
                print();
                break;

            case 8: // CURLY
                print();
                break;

            case 9: // SEMICOLON
                print();
                break;

            case 10: // COLON
                print();
                break;

            case 11: //COMMA
                print();
                break;

            case 12: // QUESTIONMARK
                print();
                break;

            case 13: // ESCAPE CHAR
                print();
                break;

            case 14: // HASH
                print();
                break;

            case 15: // TILDE
                print();
                break;

            case 16: // @
                print();
                break;

            case 17: // BACK TICK
                print();
                break;

            case 18: // DOT
                if (lookahead.matches("\\d")) {
                    state = 20;
                    addToLexeme();
                    nextChar();
                } else {
                    state = 19;
                    print();
                }
                break;

            case 19: // DOT EXIT 
                print();
                break;

            case 20: // DOTDIGIT 
                if (lookahead.matches("\\d")) {
                    state = 20;
                    addToLexeme();
                    nextChar();
                } else {
                    state = 21;
                    print();
                }
                break;

            case 21: // DOTDIGIT EXIT 
                print();
                break;

            case 22: // DIGIT
                if (lookahead.matches("\\d")) {
                    state = 22;
                    addToLexeme();
                    nextChar();
                } else if (lookahead.matches("\\.")) {
                    state = 20;
                    addToLexeme();
                    nextChar();
                } else {
                    state = 23;
                    print();

                }
                break;

            case 23: //DIGIT EXIT
                print();
                break;

            case 24: // EQUAL ASSIGNMENT
                if (lookahead.matches("=")) {
                    state = 25;
                    addToLexeme();
                    nextChar();
                } else {
                    state = 26;
                }
                break;

            case 25: // EQUAL COMPARISON OPERATOR
                print();
                break;

            case 26: // EQUAL ASSIGNMENT SIGN EXIT
                print();
                break;

            case 27: // BIGGER THAN SIGN
                if (lookahead.matches("=")) {
                    state = 28;
                    addToLexeme();
                    nextChar();
                } else {
                    state = 29;
                }
                break;

            case 28: // BIGGER THAN OR EQUAL
                print();
                break;

            case 29: // BIGGER THAN EXIT
                print();
                break;

            case 30: // LESS THAN SIGN
                if (lookahead.matches("=")) {
                    state = 31;
                    addToLexeme();
                    nextChar();
                } else {
                    state = 32;
                }
                break;

            case 31: // LESS THAN OR EQUAL
                print();
                break;

            case 32: // LESS THAN EXIT
                print();
                break;

            case 33: // REMAINDER OPERATOR
                if (lookahead.matches("=")) {
                    state = 34;
                    addToLexeme();
                    nextChar();
                } else {
                    state = 35;
                }
                break;

            case 34: // REMAINDER ASSIGNMENT OPERATOR
                print();
                break;

            case 35: // REMAINDER OPERATOR EXIT
                print();
                break;

            case 36: // AMPERSAND OPERATOR
                if (lookahead.matches("&")) {
                    state = 37;
                    addToLexeme();
                    nextChar();
                } else {
                    state = 38;
                }
                break;

            case 37: // AND COMPARISON OPERATOR
                print();
                break;

            case 38: // AND BITWISE COMPARISON OPERATOR
                print();
                break;

            case 39: // BAR OPERATOR
                if (lookahead.matches("\\|")) {
                    state = 40;
                    addToLexeme();
                    nextChar();
                } else {
                    state = 41;
                }
                break;

            case 40: // OR COMPARISON OPERATOR
                print();
                break;

            case 41: // OR BITWISE COMPARISON OPERATOR
                print();
                break;

            case 42: // NOT/!
                if (lookahead.matches("=")) {
                    state = 43;
                    addToLexeme();
                    nextChar();
                } else {
                    state = 44;
                }
                break;
            case 43: // NOT EQUAL
                print();
                break;

            case 44: // EXCLAMATION MARK EXIT
                print();
                break;

            case 45: // PLUS
                if (lookahead.matches("=")) {
                    state = 46;
                    addToLexeme();
                    nextChar();
                } else if (lookahead.matches("\\+")) {
                    state = 47;
                    addToLexeme();
                    nextChar();
                } else {
                    state = 48;
                }
                break;

            case 46: // PLUS ASSIGNMENT OPERATOR
                print();
                break;

            case 47: // INCREMENT OPERATOR
                print();
                break;

            case 48: // PLUS EXIT
                print();
                break;

            case 49: // MINUS
                if (lookahead.matches("=")) {
                    state = 50;
                    addToLexeme();
                    nextChar();
                } else if (lookahead.matches("\\-")) {
                    state = 51;
                    addToLexeme();
                    nextChar();
                } else {
                    state = 52;
                }
                break;

            case 50: // MINUS ASSIGNMENT OPERATOR
                print();
                break;

            case 51: // DECREMENT OPERATOR
                print();
                break;

            case 52: // MINUS EXIT
                print();
                break;

            case 53: // MULTIPLICATION
                if (lookahead.matches("=")) {
                    state = 54;
                    addToLexeme();
                    nextChar();
                } else {
                    state = 55;
                }
                break;

            case 54: // MULTIPLICATION ASSIGNMENT OPERATOR
                print();
                break;

            case 55: // STAR EXIT
                print();
                break;

            case 56: // FORWARD SLASH
                if (lookahead.matches("=")) {
                    state = 57;
                } else if (lookahead.matches("/")) {
                    state = 59;

                } else if (lookahead.matches("\\*")) {
                    state = 60;
                } else {
                    state = 58;
                    print();
                }
                nextChar();
                break;

            case 57: // DIVIDE ASSIGNMENT OPERATOR
                print();
                break;

            case 58: // DIVIDE OPERATOR EXIT
                print();
                break;

            case 59: // COMMENT (WE DONT READ THAT SO IT GOES TO STATE 0)
                if (lookahead.matches("\\n")) {
                    state = 0;
                    newLexeme();
                }
                nextChar();
                break;

            case 60: // MULTILINE COMMENT    
                if (lookahead.matches("\\*")) {
                    state = 61;
                }
                nextChar();
                break;

            case 61: // MULTILINE COMMENT 
                if (lookahead.matches("\\*")) {
                    state = 61;
                } else if (lookahead.matches("/")) {
                    state = 0;
                    newLexeme();
                } else {
                    state = 60;
                }
                nextChar();
                break;

            case 62: // DOUBLE QUOTE
                if (lookahead.matches("\"")) {
                    addToLexeme();
                    state = 64;
                    print();
                } else if (lookahead.matches("\\t|\\n|\\r")) {
                    state = 63;
                    addToLexeme();
                    specialCasePrint();

                } else {
                    state = 62;
                    addToLexeme();
                }
                nextChar();
                break;

            case 63: // DOUBLE QUOTE EXIT
                print();
                break;

            case 64: // END OF STRING
                print();
                break;

            case 65: // STRING HAS \n , \r, \t etc....
                if (lookahead.matches("\"")) {
                    state = 64;
                    addToLexeme();
                    print();
                } else {
                    state = 65;
                    addToLexeme();

                }
                nextChar();
                break;

            case 66: // SINGLE QUOTE
                if (lookahead.matches("'")) {
                    state = 69;
                    addToLexeme();
                    print();

                } else if (lookahead.matches("\\t|\\n|\\r")) {
                    state = 68;
                    addToLexeme();
                    specialCasePrint();

                } else {
                    state = 66;
                    addToLexeme();
                }
                nextChar();
                break;

            case 67:
                print();
                break;

            case 68:
                print();
                break;

            case 69:
                print();
                break;

        }
    }
/*
 * Advances to the next character in the input stream.
 * Updates the lookahead variable with the next character from the input if available.
 * If no more characters are available (catching NoSuchElementException), sets lookahead to an empty string.
 */

    public void nextChar() {
        try {
            lookahead = input.next();

        } catch (Exception NoSuchElementException) {
            lookahead = "";
        }
    }
/*
 * Adds the current lookahead character to the lexeme array.
 * Stores the character at the current index and then increments the index for the next character.
 */

    public void addToLexeme() {
        lexeme[ind] = lookahead.charAt(0);
        ind++;
    }
/*
 * Prints the completed lexeme to both the console and output file.
 * If the lexeme is a reserved word, it prints the lexeme itself as both the lexeme and its token.
 * Otherwise, if the state is greater than 0, it prints the lexeme and its corresponding token based on the current state.
 * After printing, it resets the lexeme for the next tokenization process.
 */

    public void print() {

        String completeLexeme = String.valueOf(lexeme).trim();
        if (Arrays.asList(ResWords).contains(completeLexeme)) {
            System.out.printf("%-30s%s%n", completeLexeme, completeLexeme);
            output.printf("%-30s%s%n", completeLexeme, completeLexeme);
        } else if (state > 0) {
            System.out.printf("%-30s%s%n", completeLexeme, Tokens.get(state));
            output.printf("%-30s%s%n", completeLexeme, Tokens.get(state));
        }
        newLexeme();
    }

    public void ungetc() {
        lexeme[ind] = '\0';
    }
/*
 * Resets the lexeme array and state for new lexical analysis.
 * Sets the state back to 0 (initial state) and reinitializes the lexeme array to be empty with a length of 6000 characters.\
 */

    public void newLexeme() {
        state = 0;
        lexeme = new char[6000];
    }
/*
 * Prints the temporary lexeme stored in 'temporaryString' to both the console and output file, 
 * formats it against the current state's associated token. Resets the state if 'temporaryString' is empty.
 * If 'state' is greater than 0, it prints 'temporaryString' along with its corresponding token.
 * calls newLexeme() to reinitialize for the next analysis cycle.
 */

    public void specialCasePrint() {

        lexeme[0] = ' ';
        if (temporaryString.equals("")) {
            state = 0;
        } else if (state > 0) {
            System.out.printf("%-30s%s%n", temporaryString, Tokens.get(state));
            output.printf("%-30s%s%n", temporaryString, Tokens.get(state));
        }
        for (int i = 0; i < lexeme.length - 1; i++) {
            if (lexeme[i] == temporaryString.charAt(0)) {
                lexeme[i] = ' ';
            }
        }
        temporaryString = "";
        newLexeme();

    }
    
}
