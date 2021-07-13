package regex.compiler;

import java.util.Stack;

public class ThompsonNFA {
    private State start;
    private State end;

    public ThompsonNFA(State start, State end){
        this.start = start;
        this.end   = end;
    }

    private ThompsonNFA fromEpsilon(){
        State start = new State(false);
        State end   = new State(true);
        start.addEpsilonTransition(end);
        return  new ThompsonNFA(start,end);
    }

    private static ThompsonNFA fromSymbol(Character c){
        State start = new State(false);
        State end   = new State(true);
        start.addTransitions(c,end);
        return  new ThompsonNFA(start,end);
    }

    private static ThompsonNFA concat(ThompsonNFA first, ThompsonNFA second){
        first.end.setEnd(false);
        first.end.addEpsilonTransition(second.start);
        return new ThompsonNFA(first.start,second.end);
    }

    private static ThompsonNFA union(ThompsonNFA first, ThompsonNFA second){
        State start = new State(false);
        State end   = new State(true);
        //add a transition from start to the 2 branches
        start.addEpsilonTransition(first.start);
        start.addEpsilonTransition(second.start);
        // add a transition from end of the 2 branches to the final state
        second.end.addEpsilonTransition(end);
        first.end.addEpsilonTransition(end);
        // set the end state of the 2 branches to false
        first.end.setEnd(false);
        second.end.setEnd(false);

        return new ThompsonNFA(start,end);
    }

    private static ThompsonNFA closure(ThompsonNFA nfa){
        State start = new State(false);
        State end   = new State(true);
        start.addEpsilonTransition(end);
        start.addEpsilonTransition(nfa.start);

        nfa.end.setEnd(false);
        nfa.end.addEpsilonTransition(nfa.start);
        nfa.end.addEpsilonTransition(end);

        return new ThompsonNFA(start,end);
    }

    private static ThompsonNFA zeroOrOne(ThompsonNFA nfa){
        State start = new State(false);
        State end   = new State(true);

        start.addEpsilonTransition(end);
        start.addEpsilonTransition(nfa.start);
        nfa.end.setEnd(false);
        nfa.end.addEpsilonTransition(end);

        return new ThompsonNFA(start,end);

    }

    private static ThompsonNFA oneOrMore(ThompsonNFA nfa){
        State start = new State(false);
        State end   = new State(true);

        start.addEpsilonTransition(nfa.start);
        nfa.end.addEpsilonTransition(nfa.start);
        nfa.end.addEpsilonTransition(end);
        nfa.end.setEnd(false);

        return new ThompsonNFA(start,end);
    }

    public static ThompsonNFA postFixToNFA(String postfixExp){
        Stack<ThompsonNFA> stack = new Stack<>();
        ThompsonNFA left,right;
        for(Character token: postfixExp.toCharArray()){
            switch (token){
                case '+':
                    stack.push(oneOrMore(stack.pop()));
                    break;
                case '?':
                    stack.push(zeroOrOne(stack.pop()));
                    break;
                case '*':
                    stack.push(closure(stack.pop()));
                    break;
                case '#':
                    left  = stack.pop();
                    right = stack.pop();
                    stack.push(concat(left,right));
                    break;
                case '|':
                    left  = stack.pop();
                    right = stack.pop();
                    stack.push(union(left,right));
                    break;
                default:
                    stack.push(fromSymbol(token));
                    break;
            }
        }

        return stack.pop();
    }

    public State getStart() {
        return start;
    }

    public State getEnd() {
        return end;
    }
}
