package regex.compiler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class State {

    private boolean isEnd;
    private Map<Character,State> transitions;
    private List<State> epsilonTransitions;

    public State(boolean isEnd){
        this.isEnd = isEnd;
        transitions = new HashMap<Character,State>();
        epsilonTransitions = new ArrayList<State>();
    }
    public void addEpsilonTransition(State to){
        this.epsilonTransitions.add(to);
    }
    public void addTransitions(Character c, State to){
        transitions.put(c,to);
    }
    public Map<Character, State> getTransitions() {
        return transitions;
    }
    public List<State> getEpsilonTransitions() {
        return epsilonTransitions;
    }
    public boolean isEnd() {
        return isEnd;
    }
    public void setEnd(boolean end) {
        isEnd = end;
    }

}
