import java.util.ArrayList;

public class Move {
	String piece;
	int start;
	int finish;
	ArrayList<String> sequence;
	
	public Move(int finish, String piece, int start){
		this.piece = piece;
		this.start = start;
		this.finish = finish;
	}
	
	//jump constructor
	public Move(int start, String piece, ArrayList<String> sequence){
		this.piece = piece;
		this.start = start;
		this.sequence = sequence;
	}
	
	public String toCoordinates(int position){
		String coords;
		switch(position){
			case 1:
				coords = "(7:1)";
				return coords;
			case 2:
				coords = "(7:3)";
				return coords;
			case 3:
				coords = "(7:5)";
				return coords;
			case 4:
				coords = "(7:7)";
				return coords;
			case 5:
				coords = "(6:0)";
				return coords;
			case 6:
				coords = "(6:2)";
				return coords;
			case 7:
				coords = "(6:4)";
				return coords;
			case 8:
				coords = "(6:6)";
				return coords;
			case 10:
				coords = "(5:1)";
				return coords;
			case 11:
				coords = "(5:3)";
				return coords;
			case 12:
				coords = "(5:5)";
				return coords;
			case 13:
				coords = "(5:7)";
				return coords;
			case 14:
				coords = "(4:0)";
				return coords;
			case 15:
				coords = "(4:2)";
				return coords;
			case 16:
				coords = "(4:4)";
				return coords;
			case 17:
				coords = "(4:6)";
				return coords;
			case 19:
				coords = "(3:1)";
				return coords;
			case 20:
				coords = "(3:3)";
				return coords;
			case 21:
				coords = "(3:5)";
				return coords;
			case 22:
				coords = "(3:7)";
				return coords;
			case 23:
				coords = "(2:0)";
				return coords;
			case 24:
				coords = "(2:2)";
				return coords;
			case 25:
				coords = "(2:4)";
				return coords;
			case 26:
				coords = "(2:6)";
				return coords;
			case 28:
				coords = "(1:1)";
				return coords;
			case 29:
				coords = "(1:3)";
				return coords;
			case 30:
				coords = "(1:5)";
				return coords;
			case 31:
				coords = "(1:7)";
				return coords;
			case 32:
				coords = "(0:0)";
				return coords;
			case 33:
				coords = "(0:2)";
				return coords;
			case 34:
				coords = "(0:4)";
				return coords;
			case 35:
				coords = "(0:6)";
				return coords;
		}
		return "Invalid Input";
	}
	
	public static int toPosition(String coords){
		int pos;
		switch(coords){
			case "(7:1)":
				pos = 1;
				return pos;
			case "(7:3)":
				pos = 2;
				return pos;
			case "(7:5)":
				pos = 3;
				return pos;
			case "(7:7)":
				pos = 4;
				return pos;
			case "(6:0)":
				pos = 5;
				return pos;
			case "(6:2)":
				pos = 6;
				return pos;
			case "(6:4)":
				pos = 7;
				return pos;
			case "(6:6)":
				pos = 8;
				return pos;
			case "(5:1)":
				pos = 10;
				return pos;
			case "(5:3)":
				pos = 11;
				return pos;
			case "(5:5)":
				pos = 12;
				return pos;
			case "(5:7)":
				pos = 13;
				return pos;
			case "(4:0)":
				pos = 14;
				return pos;
			case "(4:2)":
				pos = 15;
				return pos;
			case "(4:4)":
				pos = 16;
				return pos;
			case "(4:6)":
				pos = 17;
				return pos;
			case "(3:1)":
				pos = 19;
				return pos;
			case "(3:3)":
				pos = 20;
				return pos;
			case "(3:5)":
				pos = 21;
				return pos;
			case "(3:7)":
				pos = 22;
				return pos;
			case "(2:0)":
				pos = 23;
				return pos;
			case "(2:2)":
				pos = 24;
				return pos;
			case "(2:4)":
				pos = 25;
				return pos;
			case "(2:6)":
				pos = 26;
				return pos;
			case "(1:1)":
				pos = 28;
				return pos;
			case "(1:3)":
				pos = 29;
				return pos;
			case "(1:5)":
				pos = 30;
				return pos;
			case "(1:7)":
				pos = 31;
				return pos;
			case "(0:0)":
				pos = 32;
				return pos;
			case "(0:2)":
				pos = 33;
				return pos;
			case "(0:4)":
				pos = 34;
				return pos;
			case "(0:6)":
				pos = 35;
				return pos;

		}
		return 0;
	}
	
	public String toString(){
		String move = toCoordinates(start);
		if(this.sequence != null){
			int current = start;
			for(int i = 0; i < sequence.size(); i++){
				if(this.sequence.get(i).equals("DL")){
					move = move + ":" + toCoordinates(current+8);
					current += 8;
				}
				else if(this.sequence.get(i).equals("DR")){
					move = move + ":" + toCoordinates(current+10);
					current += 10;
				}
				else if(this.sequence.get(i).equals("UL")){
					move = move + ":" + toCoordinates(current-10);
					current -= 10;
				}
				else if(this.sequence.get(i).equals("UR")){
					move = move + ":" + toCoordinates(current-8);
					current -= 8;
				}
			}
			return move;
		}
		else{
			move = move + ":" + toCoordinates(finish);
			return move;
		}
	}
	
}
