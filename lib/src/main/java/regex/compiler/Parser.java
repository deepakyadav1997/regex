package regex.compiler;

import regex.exceptions.InvalidRegexException;
import regex.exceptions.InvalidRegexOperatorException;

import java.util.Stack;
import java.util.regex.Pattern;

public class Parser{
    /*
    Append a concat operator in a regex.
    Example: ab*(cdd) should return a#b*#(c#d#d)
    */ 
    public static String addConcatenationSymbol(String s){
        if(s == null){
            return "";
        }
        StringBuilder output = new StringBuilder();
        for(int i = 0; i < s.length(); i++){
            char c = s.charAt(i);
            output.append(c);
            if(c == '(' || c == '|')
                continue;
            
            if(i < s.length() - 1){
                char lookAhead = s.charAt(i+1);
                if(lookAhead == '*' || lookAhead == '?' || lookAhead == '|' || lookAhead == ')' || lookAhead == '+'){
                    continue;
                }
                output.append('#');
            }

        }
        return output.toString();
    }
    public static String regexToPostFix(String s) throws InvalidRegexOperatorException,InvalidRegexException {
        if(s == null){
            return "";
        }
        StringBuilder output = new StringBuilder();;
        Stack<Character> stack = new Stack<>();
        for(char c : s.toCharArray()){
            switch (c){
                case '(':
                    stack.push(c);
                    break;
                case ')':
                    while(!stack.isEmpty() && stack.peek() != '('){
                            output.append(stack.pop());
                    }
                    if(!stack.isEmpty() && stack.peek() == '('){
                        stack.pop();
                    }
                    else{
                        throw new InvalidRegexException("Invalid Regular Expression String");
                    }
                    break;
                case '*':
                case '+':
                case '?':
                case '|':
                case '#':
                    while(!stack.isEmpty() && getOperatorPrecedence(stack.peek()) - getOperatorPrecedence(c) >= 0){
                        output.append(stack.pop());
                    }
                    stack.push(c);
                    break;
                default:
                    output.append(c);
                    break;
            }
        }
        while(!stack.isEmpty()){
            output.append(stack.pop());
        }
        //System.out.println(s+"  "+output);
        return output.toString();
    }

    /*
    Precedence from  9.4.8 of the POSIX docs for regular expressions.
    +---+----------------------------------------------------------+
    |   |             ERE Precedence (from high to low)            |
    +---+----------------------------------------------------------+
    | 1 | Collation-related bracket symbols | [==] [::] [..]       |
    | 2 | Escaped characters                | \<special character> |
    | 3 | Bracket expression                | []                   |
    | 4 | Grouping                          | ()                   |
    | 5 | Single-character-ERE duplication  | * + ? {m,n}          |
    | 6 | Concatenation                     | #                    |
    | 7 | Anchoring                         | ^ $                  |
    | 8 | Alternation                       | |                    |
    +---+-----------------------------------+----------------------+

    * */
    public static int getOperatorPrecedence(char d) throws InvalidRegexOperatorException {
        int preference;
        switch (d){
            case '*':
            case '?':
            case '+':
                preference =  1000;
                break;
            case '#':
                preference = 100;
                break;
            case '|':
                preference = 10;
                break;
            case '(':
                preference = 0;
                break;
            default:
                throw new InvalidRegexOperatorException("Invalid operator in regex "+d);
        }
        return preference;
    }
}