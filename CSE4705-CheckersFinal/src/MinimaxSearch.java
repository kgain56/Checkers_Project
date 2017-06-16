

public class MinimaxSearch {
	public static double value;
	public static Move minimaxDecision(CheckersGameState state, String player){
		int i = 0;
		Move result = null;
		if(player.equals("Black")){
			double v = -1000000;
			for (Move action: state.actions()){
				//System.out.println("Action is " + action);
				value = Double.max(v, minValue(state.result(action), i));
				//System.out.println("Value is " + value);
				if(value > v){
					result = action;
					v = value;
				}
			}
		}
		else{
			double v = 1000000;
			for (Move action: state.actions()){
				//System.out.println("Action is " + action);
				value = Double.min(v, maxValue(state.result(action), i));
				//System.out.println("Value is " + value);
				if(value < v){
					result = action;
					v = value;
				}
			}
		}
		
		System.out.println("Best Action is " + result);
		return result;
	}
	
	public static double minValue(CheckersGameState state, int i){
		i++;
		if (i > 4){
			i = 0;
			return state.utility(state.player());
		}
		value = 100000;
		for (Move x:state.actions()){
			value = Math.min(value, maxValue (state.result(x), i));
		}
		return value;
	}
	
	public static double maxValue(CheckersGameState state, int i){
		i++;
		if (i > 4){
			i = 0;
			return state.utility(state.player());
		}
		value = -1000000;
		for (Move x:state.actions()){
			value = Math.max(value,  minValue(state.result(x), i));
		}
		return value;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// could be useful in testing Minimax search code
	}

}