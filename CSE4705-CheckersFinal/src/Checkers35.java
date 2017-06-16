import java.util.ArrayList;
import java.util.List;

public class Checkers35 implements CheckersGameState{

	String board[] = new String[36];
	String player;
	int turns;

	public Checkers35(String[] board, int turns){
		this.turns = turns;
		this.player = player();
		this.board = board;
	}
	
	@Override
	public String player() {
		if(turns % 2 == 0){
			player = "Black";
		}
		else{ 
			player = "White";
		}
		return player;
	}

	@Override
	public List<Move> actions() {
		List<Move> actions = new ArrayList<Move>();
		if(player.equals("Black")){
			boolean jump = false;
			for(int i = 1; i < 36; i++){
				if(i % 9 == 0)
					continue;
				//black non king or king piece 
				if(board[i].equalsIgnoreCase("b")){
					//down and left 
					if(i+4 < 36 && board[i+4].equals("-") && !jump){
						Move m;
						if(isKing(i+4, board[i])){
							m = new Move(i+4, "B", i);
						}
						else
							m = new Move(i+4, board[i], i);
						actions.add(m);
					}
					//down and right 
					if(i+5 < 36 && board[i+5].equals("-") && !jump){
						Move m;
						if(isKing(i+5, board[i])){
							m = new Move(i+5, "B", i);
						}
						else
							m = new Move(i+5, board[i], i);
						actions.add(m);
					}
					//jumps
					//down and left jump 
					if(i+4 < 36 && i+8 < 36 && board[i+4].equalsIgnoreCase("w") 
							&& board[i+8].equals("-")){
						if(jump == false){
							actions.clear();
							jump = true;
						}
						ArrayList<String[]> jumps = new ArrayList<String[]>();
						ArrayList<String[]> jumpList = checkJumps(i, board[i], this, jumps, 0);
						ArrayList<String> jumpSequence = new ArrayList<String>();
						int depth = 0;
						for(int j = 0; j < jumpList.size(); j++){
							if(depth < Integer.parseInt(jumpList.get(j)[1])){
								depth = Integer.parseInt(jumpList.get(j)[1]);
								jumpSequence.add(jumpList.get(j)[0]);
							}
							else{
								break;
							}
						}
						Move m = new Move(i, board[i], jumpSequence);
						actions.add(m);
					}
					//down and right jump
					if(i+5 < 36 && i+10 < 36 && board[i+5].equalsIgnoreCase("w")
							&& board[i+10].equals("-")){
						if(jump == false){
							actions.clear();
							jump = true;
						}
						ArrayList<String[]> jumps = new ArrayList<String[]>();
						ArrayList<String[]> jumpList = checkJumps(i, board[i], this, jumps, 0);
						ArrayList<String> jumpSequence = new ArrayList<String>();
						int depth = 0;
						for(int j = 0; j < jumpList.size(); j++){
							if(depth < Integer.parseInt(jumpList.get(j)[1])){
								depth = Integer.parseInt(jumpList.get(j)[1]);
								jumpSequence.add(jumpList.get(j)[0]);
							}
							else{
								break;
							}
						}
						Move m = new Move(i, board[i], jumpSequence);
						actions.add(m);
					}
				}
				//black king piece 
				if(board[i].equals("B")){
					//up and right 
					if(i-4 > 0 && board[i-4].equals("-")){
						Move m = new Move(i-4, "B", i);
						actions.add(m);
					}
					//up and left 
					if(i-5 > 0 && board[i-5].equals("-")){
						Move m = new Move(i-5, "B", i);
						actions.add(m);
					}
					//basic single jumps
					//up and right jump 
					if(i-4 > 0 && i-8 > 0 && board[i-4].equalsIgnoreCase("w")
							&& board[i-8].equals("-")){
						if(jump == false){
							actions.clear();
							jump = true;
						}
						ArrayList<String[]> jumps = new ArrayList<String[]>();
						ArrayList<String[]> jumpList = checkJumps(i, board[i], this, jumps, 0);
						ArrayList<String> jumpSequence = new ArrayList<String>();
						int depth = 0;
						for(int j = 0; j < jumpList.size(); j++){
							if(depth < Integer.parseInt(jumpList.get(j)[1])){
								depth = Integer.parseInt(jumpList.get(j)[1]);
								jumpSequence.add(jumpList.get(j)[0]);
							}
							else{
								break;
							}
						}
						Move m = new Move(i, board[i], jumpSequence);
						actions.add(m);
					}
					//up and left jump 
					if(i-5 > 0 && i-10 > 0 && board[i-5].equalsIgnoreCase("w")
							&& board[i-10].equals("-")){
						if(jump == false){
							actions.clear();
							jump = true;
						}
						ArrayList<String[]> jumps = new ArrayList<String[]>();
						ArrayList<String[]> jumpList = checkJumps(i, board[i], this, jumps, 0);
						ArrayList<String> jumpSequence = new ArrayList<String>();
						int depth = 0;
						for(int j = 0; j < jumpList.size(); j++){
							if(depth < Integer.parseInt(jumpList.get(j)[1])){
								depth = Integer.parseInt(jumpList.get(j)[1]);
								jumpSequence.add(jumpList.get(j)[0]);
							}
							else{
								break;
							}
						}
						Move m = new Move(i, board[i], jumpSequence);
						actions.add(m);
					}
					else
						continue;
				}
				//end of black king piece
				else 
					continue;
			}
			//end of for loop
		}
		
		//player is white
		else{
			boolean jump = false;
			for(int i = 1; i < 36; i++){
				
				//no out of bounds moves
				if(i % 9 == 0)
					continue;
				
				//white piece 
				if(board[i].equalsIgnoreCase("w")){
					//up and right  
					if(i-4 > 0 && board[i-4].equals("-") && !jump){
						Move m;
						if(isKing(i-4, board[i])){
							m = new Move(i-4, "W", i);
						}
						else
							m = new Move(i-4, board[i], i);
						actions.add(m);
					}
					//up and left 
					if(i-5 > 0 && board[i-5].equals("-") && !jump){
						Move m;
						if(isKing(i-5, board[i])){
							m = new Move(i-5, "W", i);
						}
						else
							m = new Move(i-5, board[i], i);
						actions.add(m);
					}
					//up and right jump 
					if((i-4 > 0 && i-8 > 0 &&
							board[i-4].equalsIgnoreCase("b")) && board[i-8].equals("-")){
						if(jump == false){
							actions.clear();
							jump = true;
						}
						ArrayList<String[]> jumps = new ArrayList<String[]>();
						ArrayList<String[]> jumpList = checkJumps(i, board[i], this, jumps, 0);
						ArrayList<String> jumpSequence = new ArrayList<String>();
						int depth = 0;
						for(int j = 0; j < jumpList.size(); j++){
							if(depth < Integer.parseInt(jumpList.get(j)[1])){
								depth = Integer.parseInt(jumpList.get(j)[1]);
								jumpSequence.add(jumpList.get(j)[0]);
							}
							else{
								break;
							}
						}
						Move m = new Move(i, board[i], jumpSequence);
						actions.add(m);	
					}
					//up and left jump 
					if(i-5 > 0 && i-10 > 0 && board[i-5].equalsIgnoreCase("b")
							&& board[i-10].equals("-")){
						if(jump == false){
							actions.clear();
							jump = true;
						}
						ArrayList<String[]> jumps = new ArrayList<String[]>();
						ArrayList<String[]> jumpList = checkJumps(i, board[i], this, jumps, 0);
						ArrayList<String> jumpSequence = new ArrayList<String>();
						int depth = 0;
						for(int j = 0; j < jumpList.size(); j++){
							if(depth < Integer.parseInt(jumpList.get(j)[1])){
								depth = Integer.parseInt(jumpList.get(j)[1]);
								jumpSequence.add(jumpList.get(j)[0]);
							}
							else{
								break;
							}
						}
						Move m = new Move(i, board[i], jumpSequence);
						actions.add(m);
					}
				}
				//white king piece 
				if(board[i].equals("W") && !jump){
					//down and right 
					if(i+5 > 0 && board[i+5].equals("-") && !jump){
						Move m = new Move(i+4, "W", i);
						actions.add(m);
					}
					//down and left 
					if(i+4 > 0 && board[i+4].equals("-") && !jump){
						Move m = new Move(i+4, "W", i);
						actions.add(m);
					}
					//basic single jumps
					//down and right jump 
					if(i+5 > 0 && i+10 > 0 && board[i+5].equalsIgnoreCase("b")
							&& board[i+10].equals("-")){
						if(jump == false){
							actions.clear();
							jump = true;
						}
						ArrayList<String[]> jumps = new ArrayList<String[]>();
						ArrayList<String[]> jumpList = checkJumps(i, board[i], this, jumps, 0);
						ArrayList<String> jumpSequence = new ArrayList<String>();
						int depth = 0;
						for(int j = 0; j < jumpList.size(); j++){
							if(depth < Integer.parseInt(jumpList.get(j)[1])){
								depth = Integer.parseInt(jumpList.get(j)[1]);
								jumpSequence.add(jumpList.get(j)[0]);
							}
							else{
								break;
							}
						}
						Move m = new Move(i, board[i], jumpSequence);
						actions.add(m);
					}
					//down and left jump 
					if(i+4 > 0 && i+8 > 0 && board[i+4].equalsIgnoreCase("b")
							&& board[i+10].equals("-")){
						if(jump == false){
							actions.clear();
							jump = true;
						}
						ArrayList<String[]> jumps = new ArrayList<String[]>();
						ArrayList<String[]> jumpList = checkJumps(i, board[i], this, jumps, 0);
						ArrayList<String> jumpSequence = new ArrayList<String>();
						int depth = 0;
						for(int j = 0; j < jumpList.size(); j++){
							if(depth < Integer.parseInt(jumpList.get(j)[1])){
								depth = Integer.parseInt(jumpList.get(j)[1]);
								jumpSequence.add(jumpList.get(j)[0]);
							}
							else{
								break;
							}
						}
						Move m = new Move(i, board[i], jumpSequence);
						actions.add(m);
					}
					else
						continue;
				}
				//end of white king piece 
				else 
					continue;
			}
			//end of white turn
		}
		return actions;
	}
	
	public void jumpAndRemovePiece(String piece, int start, int direction, int finish, String[] board) {
		board[start] = "-";
		board[direction] = "-";
		if(isKing(finish, piece)){
			piece = piece.toUpperCase();
		}
		board[finish] = piece;
	}

	int itr = 0;
	//recursively finds all jumps possible and returns the sequence as a list of strings defined below
	public ArrayList<String[]> checkJumps(int position, String piece, Checkers35 state, ArrayList<String[]> jumps, int itr){
		itr++;
		Checkers35 teststate = stateClone(state);
		String[] jumpPair = new String[2];

		//UR = UP and Right, UL = UP and Left
		//DR = Down and Right, DL = Down and Left
		if(piece.equalsIgnoreCase("w")){
			if(position-4 > 0 && position-8 > 0 && 
					teststate.board[position-4].equalsIgnoreCase("b") && teststate.board[position-8].equals("-")){
				jumpPair[0] = "UR";
				jumpPair[1] = Integer.toString(itr);
				jumps.add(jumpPair);
				jumpAndRemovePiece(piece, position, position-4, position-8, teststate.board);
				checkJumps(position-8, piece, teststate, jumps, itr);
			}
			if(position-5 > 0 && position-10 > 0 &&
					teststate.board[position-5].equalsIgnoreCase("b") && teststate.board[position-10].equals("-")){
				jumpPair[0] = "UL";
				jumpPair[1] = Integer.toString(itr);
				jumps.add(jumpPair);
				jumpAndRemovePiece(piece, position, position-5, position-10, teststate.board);
				checkJumps(position-10, piece, teststate, jumps, itr);
			}
			if(piece.equals("W")){
				if(position+4 < 36 && position+8 < 36 &&
						teststate.board[position+4].equalsIgnoreCase("b") && teststate.board[position+8].equals("-")){
					jumpPair[0] = "DL";
					jumpPair[1] = Integer.toString(itr);
					jumps.add(jumpPair);
					jumpAndRemovePiece(piece, position, position+4, position+8, teststate.board);
					checkJumps(position+8, piece, teststate, jumps, itr);
				}
				if(position+5 < 36 && position+10 < 36 &&
						teststate.board[position+5].equalsIgnoreCase("b") && teststate.board[position+10].equals("-")){
					jumpPair[0] = "DR";
					jumpPair[1] = Integer.toString(itr);
					jumps.add(jumpPair);
					jumpAndRemovePiece(piece, position, position+5, position+10, teststate.board);
					checkJumps(position+10, piece, teststate, jumps, itr);
				}
			}
		}
		else if(piece.equalsIgnoreCase("b")){
			if(position+4 < 36 && position+8 < 36 &&
					teststate.board[position+4].equalsIgnoreCase("w") && teststate.board[position+8].equals("-")){
				jumpPair[0] = "DL";
				jumpPair[1] = Integer.toString(itr);
				jumps.add(jumpPair);
				jumpAndRemovePiece(piece, position, position+4, position+8, teststate.board);
				checkJumps(position+8, piece, teststate, jumps, itr);
			}
			if(position+5 < 36 && position+10 <36 &&
					teststate.board[position+5].equalsIgnoreCase("w") && teststate.board[position+10].equals("-")){
				jumpPair[0] = "DR";
				jumpPair[1] = Integer.toString(itr);
				jumps.add(jumpPair);
				jumpAndRemovePiece(piece, position, position+5, position+10, teststate.board);
				checkJumps(position+10, piece, teststate, jumps, itr);
			}
			if(piece.equals("B")){
				if(position-4 > 0 && position-8 > 0 && 
						teststate.board[position-4].equalsIgnoreCase("w") && teststate.board[position-8].equals("-")){
					jumpPair[0] = "UR";
					jumpPair[1] = Integer.toString(itr);
					jumps.add(jumpPair);
					jumpAndRemovePiece(piece, position, position-4, position-8, teststate.board);
					checkJumps(position-8, piece, teststate, jumps, itr);
				}
				if(position-5 > 0 && position-10 > 0 &&
						teststate.board[position-5].equalsIgnoreCase("w") && teststate.board[position-10].equals("-")){
					jumpPair[0] = "UL";
					jumpPair[1] = Integer.toString(itr);
					jumps.add(jumpPair);
					jumpAndRemovePiece(piece, position, position-5, position-10, teststate.board);
					checkJumps(position-10, piece, teststate, jumps, itr);
				}
			}
		}
		return jumps;
	}
	
	//checks if a position will result in a king piece 
	public boolean isKing(int position, String piece){
		if(piece.equals("w") && (position == 1 || position == 2 || position == 3 || position == 4)){
			return true;
		}
		else if(piece.equals("b") && (position == 32 || position == 33 || position == 34 || position == 35)){
			return true;
		}
		return false;
	}
	
	public Checkers35 stateClone(Checkers35 state){
		String[] newBoard = new String[36];
		for(int i = 0; i < 36; i++)
		{
			newBoard[i] = state.board[i];
		}
		int newTurns = new Integer(state.turns);
		Checkers35 newState = new Checkers35(newBoard, newTurns);
		return newState;
	}
	
	@Override
	public CheckersGameState result(Move x){
		Checkers35 newState = stateClone(this);
		//multiple jumps present
		if(x.sequence != null){
			int current = x.start;
			for(int i = 0; i < x.sequence.size(); i++){
				if(current+5 < 36 && current+10 < 36 &&
						x.sequence.get(i).equals("DR")){
					newState.board[current] = "-";
					newState.board[current+5] = "-";
					newState.board[current+10] = x.piece;
					current += 10;
				}
				else if(current+4 < 36 && current+8 < 36 &&
						x.sequence.get(i).equals("DL")){
					newState.board[current] = "-";
					newState.board[current+4] = "-";
					newState.board[current+8] = x.piece;
					current += 8;
				}
				else if(current-4 > 0 && current-8 > 0 &&
						x.sequence.get(i).equals("UR")){
					newState.board[current] = "-";
					newState.board[current-4] = "-";
					newState.board[current-8] = x.piece;
					current -= 8;
				}
				else if(current-5 > 0 && current-10 > 0 &&
						x.sequence.get(i).equals("UL")){
					newState.board[current] = "-";
					newState.board[current-5] = "-";
					newState.board[current-10] = x.piece;
					current -= 10;
				}
			}
		}
		else{
			newState.board[x.finish] = x.piece;
			newState.board[x.start] = "-";
		}
		newState.turns++;
		newState.player = newState.player();
		return newState;
	}
	
	public double utility(String player) {
		//Black is maximizer, White is minimizer

		if(player.equals("Black")){
			for(int i = 0; i < 36; i++){
				if(i % 9 == 0)
					continue;
				else if(board[i].equals("b")){
					if(i+4 < 36 && board[i+4].equals("-")){
						if(isKing(i+4, board[i])){
							System.out.println("10");
							return 10;
						}
						else
							return 1;
					}
					if(i+5 < 36 && board[i+5].equals("-")){;
					if(isKing(i+5, board[i])){
						return 10;
					}
					else
						return 1;
					}
					if(i+4 < 36 && i+8 < 36 && board[i+4].equals("w") && board[i+8].equals("-")){
						if(isKing(i+8, board[i])){
							return 150;
						}
						else
							return 100;
					}
					if(i+5 < 36 && i+10 < 36 && board[i+5].equals("w") && board[i+10].equals("-")){
						if(isKing(i+10, board[i])){
							return 150;
						}
						else
							return 100;
					}
					if(i+4 < 36 && i+8 < 36 && board[i+4].equals("W") && board[i+8].equals("-")){
						if(isKing(i+8, board[i])){
							return 500;
						}
						else
							return 300;
					}
					if(i+5 < 36 && i+10 < 36 && board[i+5].equals("W") && board[i+10].equals("-")){
						if(isKing(i+10, board[i])){
							return 500;
						}
						else
							return 300;
					}	
					else
						continue;
				}
				else if(board[i].equals("B")){
					if(i-4 > 0 && board[i-4].equals("-")){
						return 1;
					}
					if(i-5 > 0 && board[i-5].equals("-")){
						return 1;
					}
					if(i+4 < 36 && board[i+4].equals("-")){
						return 1;
					}
					if(i+5 < 36 && board[i+5].equals("-")){
						return 1;
					}
					if(i-4 > 0 && i-8 > 0 && board[i-4].equals("w") && board[i-8].equals("-")){
						return 100;
					}
					if(i-5 > 0 && i-10 > 0 && board[i-5].equals("w") && board[i-10].equals("-")){
						return 100;
					}
					if(i+4 < 36 && i+8 < 36 && board[i+4].equals("w") && board[i+8].equals("-")){
						return 100;
					}
					if(i+5 < 36 && i+10 < 36 && board[i+5].equals("w") && board[i+10].equals("-")){
						return 100;
					}
					if(i-4 > 0 && i-8 > 0 && board[i-4].equals("W") && board[i-8].equals("-")){
						return 300;
					}
					if(i-5 > 0 && i-10 > 0 && board[i-5].equals("W") && board[i-10].equals("-")){
						return 300;
					}
					if(i+4 < 36 && i+8 < 36 && board[i+4].equals("W") && board[i+8].equals("-")){
						return 300;
					}
					if(i+5 < 36 && i+10 < 36 && board[i+5].equals("W") && board[i+10].equals("-")){
						return 300;
					}
					else
						continue;
				}
				else 
					continue;
			}
		}
		else{	
			for(int i = 0; i < 36; i++){		
				if(i % 9 == 0)
					continue;

				else if(board[i].equals("w")){

					if(i-4 > 0 && board[i-4].equals("-")){
						if(isKing(i-4, board[i])){
							return -10;
						}
						else
							return -1;
					}
					if(i-5 > 0 && board[i-5].equals("-")){
						if(isKing(i-5, board[i])){
							return -10;
						}
						else
							return -1;
					} 
					if(i-4 > 0 && i-8 > 0 && board[i-4].equals("b") && board[i-8].equals("-")){
						if(isKing(i-8, board[i])){
							return -150;
						}
						else
							return -100;
					}
					if(i-5 > 0 && i-10 > 0 && board[i-5].equals("b") && board[i-10].equals("-")){
						if(isKing(i-10, board[i])){
							return -150;
						}
						else
							return -100;
					}
					if(i-4 > 0 && i-8 > 0 && board[i-4].equals("B") && board[i-8].equals("-")){
						if(isKing(i-8, board[i])){
							return -500;
						}
						else
							return -300;
					}
					if(i-5 > 0 && i-10 > 0 && board[i-5].equals("B") && board[i-10].equals("-")){
						if(isKing(i-10, board[i])){
							return -500;
						}
						else
							return -300;
					}
					else
						continue;
				}
				else if(board[i].equals("W")){

					if(i-4 > 0 && board[i-4].equals("-")){
						return -1;
					}
					if(i-5 > 0 && board[i-5].equals("-")){
						return -1;
					}
					if(i+4 < 36 && board[i+4].equals("-")){
						return -1;
					}
					if(i+5 < 36 && board[i+5].equals("-")){
						return -1;
					}
					if(i-4 > 0 && i-8 > 0 && board[i-4].equals("b") && board[i-8].equals("-")){
						return -100;
					}
					if(i-5 > 0 && i-10 > 0 && board[i-5].equals("b") && board[i-10].equals("-")){
						return -100;
					}
					if(i+4 < 36 && i+8 < 36 && board[i+4].equals("b") && board[i+8].equals("-")){
						return -100;
					}
					if(i+5 < 36 && i+10 < 36 && board[i+5].equals("b") && board[i+10].equals("-")){
						return -100;
					}
					if(i-4 > 0 && i-8 > 0 && board[i-4].equals("B") && board[i-8].equals("-")){
						return -300;
					}
					if(i-5 > 0 && i-10 > 0 && board[i-5].equals("B") && board[i-10].equals("-")){
						return -300;
					}
					if(i+4 < 36 && i+8 < 36 && board[i+4].equals("B") && board[i+8].equals("-")){
						return -300;
					}
					if(i+5 < 36 && i+10 < 36 && board[i+5].equals("B") && board[i+10].equals("-")){
						return -300;
					}
					else
						continue;
				}
				else 
					continue;
			}
		}
		return 0;
	}

	@Override
	public void printState() {
		int col = -1;
		int row = 0;
		for(int i = 1; i < 36; i++){
			if(i % 9 == 0){
				continue;
			}
			else if(row % 2 == 0){
				if(col < 3){
					System.out.print("-" + board[i]);
					col++;
				}
				else{
					System.out.print("\n" + board[i] + "-");
					col = 0;
					row++;
				}
			}
			else{
				if(col < 3){
					System.out.print(board[i] + "-");
					col++;
				}
				else{
					System.out.print("\n" + "-" + board[i]);
					col = 0;
					row++;
				}
			}
		}
	}
	
	public static void main(String[] args){
		String board1[] = {"", "b", "b", "b", "b",
				"b", "b", "b", "b", "",
				"b", "b", "b", "b",
				"-", "-", "-", "-", "",
				"-", "-", "-", "-",
				"w", "w", "w", "w", "",
				"w", "w", "w", "w",
				"w", "w", "w", "w"};
		String board2[] ={"", "-", "-", "-", "=",
				   "b", "-", "-", "-", "",
				      "b", "-", "b", "-",
				   "b", "-", "-", "-", "",
				      "b", "-", "b", "b",
				   "-", "-", "-", "-", "",
				      "-", "-", "-", "-",
				   "-", "-", "-", "w"};
		String board3[] ={"", "b", "b", "b", "b",
						   "b", "b", "b", "b", "",
						      "-", "-", "b", "b",
						   "b", "b", "-", "-", "",
						      "w", "-", "-", "-",
						   "-", "w", "w", "w", "",
						      "w", "w", "w", "w",
						   "w", "w", "w", "w"};
		Checkers35 test = new Checkers35(board3, 1);
		test.printState();
		System.out.println("\n\n" + test.player() + "'s turn.\n");
		/*System.out.println("Possible Actions:");
		List<Move> actionTest = test.actions();
		for(Move m: actionTest){
			System.out.println(m.toString());
		}
		System.out.print("\n");*/
		//MinimaxSearch search = new MinimaxSearch();
		System.out.println(MinimaxSearch.minimaxDecision(test, test.player()));
		//test.result(actionTest.get(0)).printState();
	}

}
