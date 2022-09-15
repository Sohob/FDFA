package csen1002.main.task4;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;

/**
 * Write your info here
 * 
 * @name Mostafa Mohamed Abdelnasser
 * @id 43-8530
 * @labNumber 11
 */
public class FDFA {
	HashMap<Integer,Integer> zeroTransMap;
	HashMap<Integer,Integer> oneTransMap;
	HashMap<Integer,String> Actions;
	HashSet<Integer> accStates;
	public FDFA(String description) {
		//Parse the input
		String[] splitInput = description.split("#");
		String[] stateArray = splitInput[0].split(";");
		String[] accStates = splitInput[1].split(",");

		//Initialize instance variables
		zeroTransMap = new HashMap<>();
		oneTransMap = new HashMap<>();
		Actions = new HashMap<>();
		this.accStates = new HashSet<>();

		//Add accept states
		for (String state :
				accStates) {
			this.accStates.add(Integer.parseInt(state));
		}

		//Loop over states and add them to our FDFA
		for(String state : stateArray){
			String[] splitState = state.split(",");
			int stateID = Integer.parseInt(splitState[0]);
			int zeroTrans = Integer.parseInt(splitState[1]);
			int oneTrans = Integer.parseInt(splitState[2]);
			String action = splitState[3];

			//Insert in states hashmap
			zeroTransMap.put(stateID, zeroTrans);
			oneTransMap.put(stateID, oneTrans);
			Actions.put(stateID,action);
		}
	}

	/**
	 * Returns a string of actions.
	 * 
	 * @param input is the string to simulate by the FDFA.
	 * @return string of actions.
	 */
	public String run(String input) {
		String outputString = "";
		outputString = runHelper(populateStack(input),input);


		return outputString;
	}

	private String runHelper(Stack stack, String lex){
		if (lex.length()==0)
			return "";
		int l = stack.size();
		int qr = (int) stack.peek();
		while(!stack.isEmpty()){
			l--;
			int qa = (int) stack.pop();
			if(accStates.contains(qa)){
				Stack nextStack = populateStack(lex.substring(l));
				return lex.substring(0,l) +","+ Actions.get(qa)+";"+runHelper(nextStack,lex.substring(l));
			}
		}
		return lex+","+Actions.get(qr)+";";
	}

	private Stack populateStack(String lex) {
		if(lex.length()==0)
			return new Stack<>();
		int currState = 0;
		Stack<Integer> stateStack = new Stack<>();
		String[] splitInput = lex.split("");

		stateStack.push(currState);
		for (String s :
				splitInput) {
			int letter = Integer.parseInt(s+"");
			if(letter == 0)
				currState = zeroTransMap.get(currState);
			else
				currState = oneTransMap.get(currState);
			stateStack.push(currState);
		}
		return stateStack;
	}
}
