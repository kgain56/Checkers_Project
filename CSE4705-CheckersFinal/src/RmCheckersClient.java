// RmCheckersClient.java is a client that interacts with Sam, a checkers 
// server. It is designed to illustrate how to communicate with the server
// in a minimal way.  It is not meant to be beautiful java code.
// Given the correct machine name and port for the server, a user id, and a 
// password (_machine, _port, _user, and _password in the code), running 
// this program will initiate connection and start a game with the default 
// player. (the _machine and _port values used should be correct, but check
// the protocol document.)
// 
// the program has been tested and used under Java 5.0, and 6.0, but probably 
// would work under older or newer versions. (also works under Java 8).
//
// Copyright (C) 2008 Robert McCartney
 
// This program is free software; you can redistribute it and/or
// modify it under the terms of the GNU General Public License as
// published by the Free Software Foundation; either version 2 of the
// License, or (at your option) any later version.

// This program is distributed in the hope that it will be useful, but
// WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// General Public License for more details.

// You should have received a copy of the GNU General Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307
// USA

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class RmCheckersClient {

    private final static String _user = "16";  // need legit id here (16)(15)
    private final static String _password = "807861";  // need password here (807861)(848088)
    private final static String _opponent = "15";
    private final String _machine  = "icarus.engr.uconn.edu"; 
    private int _port = 3499;
    private Socket _socket = null;
    private PrintWriter _out = null;
    private BufferedReader _in = null;

    private String _gameID;
    private String _myColor;
  
    public RmCheckersClient(){	
    	_socket = openSocket();
    }

    public Socket getSocket(){
    	return _socket;
    }

    public PrintWriter getOut(){
    	return _out;
    }

    public BufferedReader getIn(){
    	return _in;
    }

    public void setGameID(String id){
    	_gameID = id;
    }

    public String getGameID() {
    	return _gameID;
    }

    public void setColor(String color){
    	_myColor = color;
    }

    public String getColor() {
    	return _myColor;
    }

    public static void main(String[] argv){
    	String readMessage;
    	RmCheckersClient myClient = new RmCheckersClient();


	//test.result(actionTest.get(0)).printState();
	
	try{
	    myClient.readAndEcho(); // start message
	    myClient.readAndEcho(); // ID query
	    myClient.writeMessageAndEcho(_user); // user ID(15)
	    
	    myClient.readAndEcho(); // password query 
	    myClient.writeMessage(_password);  // password(848088)

	    myClient.readAndEcho(); // opponent query
	    myClient.writeMessageAndEcho(_opponent);  // opponent (0)

	    myClient.setGameID(myClient.readAndEcho().substring(4,10)); // game 
	    myClient.setColor(myClient.readAndEcho().substring(6,11));  // color
	    System.out.println("I am playing as "+myClient.getColor()+ " in game number "+ myClient.getGameID());
	    readMessage = myClient.readAndEcho();  
	    // depends on color--a black move if i am white, Move:Black:i:j
	    // otherwise a query to move, ?Move(time):
	    String initial[] = {"", "b", "b", "b", "b",
				"b", "b", "b", "b", "",
				"b", "b", "b", "b",
				"-", "-", "-", "-", "",
				"-", "-", "-", "-",
				"w", "w", "w", "w", "",
				"w", "w", "w", "w",
				"w", "w", "w", "w"};
		Checkers35 board = new Checkers35(initial, 0);
		//MinimaxSearch search = new MinimaxSearch();
		
		//White
	    if (myClient.getColor().equals("White")) {
	    	while(true){
	    		//parse white move message
	    		String[] message2 = readMessage.split(":");
	    		if(message2[0].equals("Result")){
	    			myClient.getSocket().close();
	    			return;
	    		}
	    		String bmessage1 = message2[2] + ":" + message2[3];
	    		int bstart = Move.toPosition(bmessage1);
	    		String b = board.board[bstart];
    			board.board[bstart] = "-";
	    		if(message2.length > 6){//if there are jumps present 
	    			ArrayList<Integer> multiple = new ArrayList<Integer>();
	    			//parses message containing multiple jumps and turns them into Checkers35 positions
	    			for(int i = 4; i < message2.length; i+=2){
	    				String bmessage = message2[i] + ":" + message2[i+1];
	    	    		int nextposition = Move.toPosition(bmessage);
	    	    		multiple.add(nextposition);
	    			}
	    			//performs all the jumps on the board
	    			int current = bstart;
	    			for(int j = 0; j < multiple.size(); j++){
	    				int direction = (multiple.get(j) - current) / 2;
	    				if(board.isKing(multiple.get(j), b))
	    					b = "B";
	    				board.jumpAndRemovePiece(b, current, current + direction, 
	    						multiple.get(j), board.board);
	    				current = multiple.get(j);
	    			}
	    		}
	    		else{//end position if multiple jumps are not present 
	    			String bmessage2 = message2[4] + ":" + message2[5];
		    		int bfinish = Move.toPosition(bmessage2);
		    		if(board.isKing(bfinish, b))
    					b = "B";
		    		//if its a single jump
		    		if(Math.abs(bfinish - bstart) > 5){
		    			int direction = (bfinish - bstart) / 2;
		    			board.jumpAndRemovePiece(b, bstart, (bstart + direction), bfinish, board.board);
		    		}
		    		//if the piece is just moving 
		    		else{
		    			board.board[bfinish] = b;
		    		}
	    		}
	    		board.turns++;
	    		board.player();
	    		readMessage = myClient.readAndEcho();  // black move
	    		board.printState();
	    		System.out.println("\n");
	    		Move result = MinimaxSearch.minimaxDecision(board, board.player);
	    		myClient.writeMessageAndEcho(result.toString());//sent
	    		readMessage = myClient.readAndEcho();  // black move
	    		//parse black move message
	    		String[] message1 = readMessage.split(":");
	    		if(message1[0].equals("Result")){
	    			myClient.getSocket().close();
	    			return;
	    		}
	    		String wmessage1 = message1[2] + ":" + message1[3];
	    		int wstart = Move.toPosition(wmessage1);
	    		String w = board.board[wstart];
    			board.board[wstart] = "-";
	    		if(message1.length > 6){//if there are jumps present 
	    			ArrayList<Integer> multiple = new ArrayList<Integer>();
	    			//parses message containing multiple jumps and turns them into Checkers35 positions
	    			for(int i = 4; i < message1.length; i+=2){
	    				String wmessage = message1[i] + ":" + message1[i+1];
	    	    		int nextposition = Move.toPosition(wmessage);
	    	    		multiple.add(nextposition);
	    			}
	    			//performs all the jumps on the board
	    			int current = wstart;
	    			for(int j = 0; j < multiple.size(); j++){
	    				int direction = (multiple.get(j) - current) / 2;
	    				if(board.isKing(multiple.get(j), w))
	    					w = "W";
	    				board.jumpAndRemovePiece(w, current, current + direction, 
	    						multiple.get(j), board.board);
	    				current = multiple.get(j);
	    			}
	    		}
	    		else{//end position if multiple jumps are not present 
	    			String wmessage2 = message1[4] + ":" + message1[5];
		    		int wfinish = Move.toPosition(wmessage2);
		    		if(board.isKing(wfinish, w))
    					w = "W";
		    		//if its a single jump
		    		if(Math.abs(wfinish - wstart) > 5){
		    			int direction = (wfinish - wstart) / 2;
		    			board.jumpAndRemovePiece(w, wstart, wstart + direction, wfinish, board.board);
		    		}
		    		//if the piece is just moving 
		    		else{
		    			board.board[wfinish] = w;
		    		}
	    		}
	    		board.turns++;
	    		board.player();
	    		board.printState();
	    		System.out.println("\n");
	    		//-----------------------------------------//
	    		readMessage = myClient.readAndEcho();  // move query
	    		String[] message3 = readMessage.split(":");
	    		if(message3[0].equals("Result")){
	    			myClient.getSocket().close();
	    			return;
	    		}
	    	}
	    }
	    
	    //Black
	    else {    		
	    	while(true){
	    		Move result = MinimaxSearch.minimaxDecision(board, board.player);
	    		myClient.writeMessageAndEcho(result.toString());//sent
	    		readMessage = myClient.readAndEcho();  // black move (read)
	    		//parse black move message
	    		String[] message1 = readMessage.split(":");
	    		if(message1[0].equals("Result")){
	    			myClient.getSocket().close();
	    			return;
	    		}
	    		String bmessage1 = message1[2] + ":" + message1[3];
	    		int bstart = Move.toPosition(bmessage1);
	    		String b = board.board[bstart];
    			board.board[bstart] = "-";
	    		if(message1.length > 6){//if there are jumps present 
	    			ArrayList<Integer> multiple = new ArrayList<Integer>();
	    			//parses message containing multiple jumps and turns them into Checkers35 positions
	    			for(int i = 4; i < message1.length; i+=2){
	    				String bmessage = message1[i] + ":" + message1[i+1];
	    	    		int nextposition = Move.toPosition(bmessage);
	    	    		multiple.add(nextposition);
	    			}
	    			//performs all the jumps on the board
	    			int current = bstart;
	    			for(int j = 0; j < multiple.size(); j++){
	    				int direction = (multiple.get(j) - current) / 2;
	    				if(board.isKing(multiple.get(j), b))
	    					b = "B";
	    				board.jumpAndRemovePiece(b, current, current + direction, 
	    						multiple.get(j), board.board);
	    				current = multiple.get(j);
	    			}
	    		}
	    		else{//end position if multiple jumps are not present 
	    			String bmessage2 = message1[4] + ":" + message1[5];
		    		int bfinish = Move.toPosition(bmessage2);
		    		if(board.isKing(bfinish, b))
    					b = "B";
		    		//if its a single jump
		    		if(Math.abs(bfinish - bstart) > 5){
		    			int direction = (bfinish - bstart) / 2;
		    			board.jumpAndRemovePiece(b, bstart, bstart + direction, bfinish, board.board);
		    		}
		    		//if the piece is just moving 
		    		else{
		    			board.board[bfinish] = b;
		    		}
	    		}
	    		board.turns++;
	    		board.player();
	    		board.printState();
	    		System.out.println("\n");
	    		readMessage = myClient.readAndEcho();  // white move (read)
	    		//parse white move message
	    		String[] message2 = readMessage.split(":");
	    		if(message2[0].equals("Result")){
	    			myClient.getSocket().close();
	    			return;
	    		}
	    		String wmessage1 = message2[2] + ":" + message2[3];
	    		int wstart = Move.toPosition(wmessage1);
	    		String w = board.board[wstart];
    			board.board[wstart] = "-";
	    		if(message2.length > 6){//if there are jumps present 
	    			ArrayList<Integer> multiple = new ArrayList<Integer>();
	    			//parses message containing multiple jumps and turns them into Checkers35 positions
	    			for(int i = 4; i < message2.length; i+=2){
	    				String wmessage = message2[i] + ":" + message2[i+1];
	    	    		int nextposition = Move.toPosition(wmessage);
	    	    		multiple.add(nextposition);
	    			}
	    			//performs all the jumps on the board
	    			int current = wstart;
	    			for(int j = 0; j < multiple.size(); j++){
	    				int direction = (multiple.get(j) - current) / 2;
	    				if(board.isKing(multiple.get(j), w))
	    					w = "W";
	    				board.jumpAndRemovePiece(w, current, current + direction, 
	    						multiple.get(j), board.board);
	    				current = multiple.get(j);
	    			}
	    		}
	    		else{//end position if multiple jumps are not present 
	    			String wmessage2 = message2[4] + ":" + message2[5];
		    		int wfinish = Move.toPosition(wmessage2);
		    		if(board.isKing(wfinish, w))
    					w = "W";
		    		//if its a single jump
		    		if(Math.abs(wfinish - wstart) > 5){
		    			int direction = (wfinish - wstart) / 2;
		    			board.jumpAndRemovePiece(w, wstart, (wstart + direction), wfinish, board.board);
		    		}
		    		//if the piece is just moving 
		    		else{
		    			board.board[wfinish] = w;
		    		}
	    		}
	    		board.turns++;
	    		board.player();
	    		board.printState();
	    		System.out.println("\n");
	    		readMessage = myClient.readAndEcho();  // move query (move)
	    		String[] message3 = readMessage.split(":");
	    		if(message3[0].equals("Result")){
	    			myClient.getSocket().close();
	    			return;
	    		}
	    	}
	    }
	} catch  (IOException e) {
	    System.out.println("Failed in read/close");
	    System.exit(1);
	}
    }

    public String readAndEcho() throws IOException
    {
    	String readMessage = _in.readLine();
    	System.out.println("read: "+readMessage);
    	return readMessage;
    }

    public void writeMessage(String message) throws IOException
    {
    	_out.print(message+"\r\n");  
    	_out.flush();
    }

    public void writeMessageAndEcho(String message) throws IOException
    {
    	_out.print(message+"\r\n");  
    	_out.flush();
    	System.out.println("sent: "+ message);
    }

    public  Socket openSocket(){
    	//Create socket connection, adapted from Sun example
    	try{
    		_socket = new Socket(_machine, _port);
    		_out = new PrintWriter(_socket.getOutputStream(), true);
    		_in = new BufferedReader(new InputStreamReader(_socket.getInputStream()));
    	} catch (UnknownHostException e) {
    		System.out.println("Unknown host: " + _machine);
    		System.exit(1);
    	} catch  (IOException e) {
    		System.out.println("No I/O");
    		System.exit(1);
    	}
    	return _socket;
  }
}