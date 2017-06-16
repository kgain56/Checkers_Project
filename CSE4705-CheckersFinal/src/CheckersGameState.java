import java.util.List;

public interface CheckersGameState {
	String player();
	List<Move> actions();
	void printState();
	double utility(String player);
	CheckersGameState result(Move x);
}
