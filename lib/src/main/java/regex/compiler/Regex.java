package regex.compiler;

import java.util.ArrayList;
import java.util.List;

public class Regex {
     private static void getNextStates(State state, List<State> nextStates, List<State> visited){
          if(state.getEpsilonTransitions().size() > 0){
               for(State t : state.getEpsilonTransitions()){
                    if(!visited.contains(t)){
                         visited.add(t);
                         getNextStates(t,nextStates,visited);
                    }
               }
          }
          else{
               nextStates.add(state);
          }
     }
     private static boolean search(ThompsonNFA nfa, String word){
          ArrayList<State> currentStates = new ArrayList<>();
          getNextStates(nfa.getStart(),currentStates,new ArrayList<State>());

          for(Character c : word.toCharArray()){
               ArrayList<State> nextStates = new ArrayList<>();
               for(State s : currentStates){
                    State nextState = s.getTransitions().get(c);
                    if(nextState != null){
                         getNextStates(nextState,nextStates,new ArrayList<State>());
                    }
               }
               currentStates = nextStates;
          }
          for(State s : currentStates){
               if(s.isEnd()){
                    return true;
               }
          }
          return false;
     }
     public static boolean match(String word,String regex){
          String postfix = "";
          try{
               postfix = Parser.regexToPostFix(regex);
          }
          catch (Exception e){
               e.printStackTrace();
          }

          ThompsonNFA nfa = ThompsonNFA.postFixToNFA(postfix);
          return search(nfa,word);
     }
}
