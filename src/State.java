/* *********************************************************************
 * ********* Author¡¯s name(s): Yifan Chen
 * Course Title: Artificial Intelligence
 * Semester: Fall 2017
 * Assignment Number 1
 * Submission Date: 9/26/2017
 * Purpose: This program simulates the Puzzle game which has 4 rows and 4 columns.
 * Input: The search method you want to use and the name of test file. 
 * 		For example: If you want to use BFS as the search method, you can input "java Puzzle BFS test.dat".
 * 		For example: If you want to use DFS as the search method, you can input "java Puzzle DFS test.dat".
 * Output: The shortest path from initial state to goal state.
 * 		For example: LLLUUURRR
 * Help: The most important knowledge which I learned from this program is that BFS is really more optimal than DFS.
 * 		For example: The goal maybe in the right child of initial state. If we use DFS, we will miss it until it has 
 * 		finished searching all of the left tree. Maybe the left tree is extremely long, it will take a long time to 
 * 		get the answer. DFS only find the left tree first.
 * 		On the contrary, if we use BFS the time it cost is shorter and shorter than DFS cost.
 * ************************************************************************
 * ****** */

public class State {
	int[] state = new int[16];  
	String printDirection="";
	State superState = null;
	boolean hasBeenVisited=false;
	private int depth=1;
	
	//****************************************************** 
	//*** Purpose: The constructor of class State to initialize the state array.
	//*** Input: state
	//*** Output: None
	//******************************************************
    public State(int[] state) {  
        this.state = state;  
    }
    
    public void setDepth(int depth){
    	this.depth=depth;
    }
    
    public int getDepth(){
    	return this.depth;
    }
}
