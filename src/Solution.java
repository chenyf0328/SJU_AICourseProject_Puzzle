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

import java.awt.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

public class Solution {
	
	public static final int MAXSTATE = 1000000;
	
	//State array
	State[] stat = new State[MAXSTATE];
	
	//Goal State
	State goal=null;
	
	//Depth Array
	int[] depth = new int[MAXSTATE];
	
	//To store the goal state
	State goalState = null;
	
	//Stand for the up, down, left and right moves
	private int[] dirX={-1,1,0,0};
	private int[] dirY={0,0,-1,1};
	
	private int newX, newY, newZeroPos;
	
	//For BFS use, because it always needs to point the start of queue and the end of queue
	private int head=1, tail=2;
	
	//For DFS use, because it always needs to point the top elements of stack
	private int top=1;
	
	boolean judgeBreak=false;
	
	//****************************************************** 
	//*** Purpose: In BFS, I want to check if the state has been existed before to minimize the size of tree. 
	//***		If it has been existed, I will not insert it into the tree. Instead, I will give up this state.
	//*** Input: My new state 
	//*** Output: true or false 
	//******************************************************
	public boolean judgeHaveBeenInsertedBFS(State newState){
		for (int i=1;i<tail;i++)
			if (Arrays.equals(newState.state, stat[i].state))
				return false;
		return true;
	}
	
	//****************************************************** 
	//*** Purpose: In DFS, I want to check if the state has been existed before to minimize the size of tree. 
	//***		If it has been existed, I will not insert it into the tree. Instead, I will give up this state.
	//*** Input: New state 
	//*** Output: true or false 
	//******************************************************
	public boolean judgeHaveBeenInsertedDFS(State newState){
		for (int i=1;i<top;i++)
			if (Arrays.equals(newState.state, stat[i].state))
				return false;
		return true;
	}
	
	//****************************************************** 
	//*** Purpose: Receive the initial state and the goal state. Then call the bfs method.	
	//*** Input: Initial State, Goal State
	//*** Output: Goal State or null 
	//******************************************************
	public State solvePuzzleBFS(State initial_state, State goal_state){
		stat[head]=null;
		if (bfs(initial_state, goal_state)==true)
			return goalState;
		else
			return null;
	}
	
	//****************************************************** 
	//*** Purpose: Receive the initial state and the goal state. Then call the dfs method.	
	//*** Input: Initial State, Goal State
	//*** Output: Goal State or null 
	//******************************************************
	public State solvePuzzleDFS(State initial_state, State goal_state){
		stat[top]=null;
		if (dfs(initial_state, goal_state)==true)
			return goalState;
		else
			return null;
	}
	
	//****************************************************** 
	//*** Purpose: Do left move. Let y - 1	
	//*** Input: x, y
	//*** Output: None 
	//******************************************************
	public void left(int x, int y){
		newX=x+dirX[2];
		newY=y+dirY[2];
		newZeroPos=newX*4+newY;
	}
	
	//****************************************************** 
	//*** Purpose: Do right move. Let y + 1	
	//*** Input: x, y
	//*** Output: None 
	//******************************************************
	public void right(int x, int y){
		newX=x+dirX[3];
		newY=y+dirY[3];
		newZeroPos=newX*4+newY;
	}
	
	//****************************************************** 
	//*** Purpose: Do up move. Let x - 1	
	//*** Input: x, y
	//*** Output: None 
	//******************************************************
	public void up(int x, int y){
		newX=x+dirX[0];
		newY=y+dirY[0];
		newZeroPos=newX*4+newY;
	}
	
	//****************************************************** 
	//*** Purpose: Do the down move. Let x + 1	
	//*** Input: x, y
	//*** Output: None 
	//******************************************************
	public void down(int x, int y){
		newX=x+dirX[1];
		newY=y+dirY[1];
		newZeroPos=newX*4+newY;
	}
	
	//****************************************************** 
	//*** Purpose: Use BFS method to generate the tree and search the tree.	
	//*** Input: Initial State, Goal State
	//*** Output: true, false
	//******************************************************
	public boolean bfs(State initial_state, State goal_state){
		stat[1]=initial_state;
		goal=goal_state;
		
		while(head<tail){
			State s=stat[head];
			
			//Find the goal in queue
			if (Arrays.equals(s.state, goal.state)){
				goalState=s;
				State findSuperState=goalState;
				
				//Constructing the path which is reverse.
				String path="";
				while (findSuperState!=null){
					path=path+findSuperState.printDirection;
					//System.out.print(findSuperState.printDirection);
					findSuperState=findSuperState.superState;
				}
				System.out.println(stringReverse(path));	
				return true;
			}
			
			//Find the position of zero in current state
			int zeroPos;
			for (zeroPos=0;zeroPos<16;zeroPos++)	
				if (0==s.state[zeroPos])
					break;
			
			//Get the zero's row and column
			int x=zeroPos/4, y=zeroPos%4;
			
			String printDirection="";
			String[] printDirectionArray=new String[MAXSTATE];
			
			//Start to move
			for (int direc=0;direc<4;direc++){
				switch(direc){
					case 0: up(x,y);printDirection="U"; break;
					case 1: down(x,y);printDirection="D"; break;
					case 2: left(x,y);printDirection="L"; break;
					case 3: right(x,y);printDirection="R"; break;
					default: break;
				}

				if (newX>=0 && newX<4 && newY>=0 && newY<4){
					int[] temp=Arrays.copyOf(s.state, 16);
					stat[tail]=new State(temp);
					//Set zero to the new zero position
					stat[tail].state[newZeroPos]=s.state[zeroPos];
					//Set the value of position zeroPos before move
					stat[tail].state[zeroPos]=s.state[newZeroPos];
					//After moving in all directions, depth++ 
					depth[tail]=depth[head]+1;
					
					//If the new move hasn't happened before, insert the new move into array
					if (judgeHaveBeenInsertedBFS(stat[tail])==true){
						stat[tail].printDirection=printDirection;
						stat[tail].superState=stat[head];
						tail++;
					}
				}
			}
			//After all directions move, the first state in state queue pop out
			head++;
			
			//Print traversal sequence
			//System.out.print(stat[head].printDirection);
		}
		return false;
	}
	
	//****************************************************** 
	//*** Purpose: Use DFS method to generate the tree and search the tree.	
	//*** Input: Initial State, Goal State
	//*** Output: true, false
	//******************************************************
	public boolean dfs(State initial_state, State goal_state){
		stat[1]=initial_state;
		goal=goal_state;
//		int[] depthDFS = new int[MAXSTATE];
		int parent=1;
//		depthDFS[1]=1;
		stat[1].setDepth(1);
		top=2;

		while(!judgeBreak){
			State s=stat[parent];
			
			s.hasBeenVisited=true;
			
			if (s.getDepth()<=10){
				//Find the goal in stack
				if (Arrays.equals(s.state, goal.state)){
					goalState=s;
					State findSuperState=goalState;
					
					//Constructing the path which is reverse.
					String path="";
					while (findSuperState!=null){
						path=path+findSuperState.printDirection;
						//System.out.print(findSuperState.printDirection);
						findSuperState=findSuperState.superState;
					}
					
					System.out.println(stringReverse(path));	
					return true;
				}
				
				//Find the position of zero in current state
				int zeroPos;
				for (zeroPos=0;zeroPos<16;zeroPos++)
					if (0==s.state[zeroPos])
						break;
				
				//Get the zero's row and column
				int x=zeroPos/4, y=zeroPos%4;
				
				String printDirection="";
				String[] printDirectionArray=new String[MAXSTATE];
				
				int judgeHasChildren=0;
				
				//Start to move
				for (int direc=0;direc<4;direc++){
					switch(direc){
						case 0: up(x,y);printDirection="U"; break;
						case 1: down(x,y);printDirection="D"; break;
						case 2: left(x,y);printDirection="L"; break;
						case 3: right(x,y);printDirection="R"; break;
						default: break;
					}
	
					if (newX>=0 && newX<4 && newY>=0 && newY<4){
						
						//Test
						//System.out.println("top="+top);
						
						int[] temp=Arrays.copyOf(s.state, 16);
						stat[top]=new State(temp);
						//Set zero to the new zero position
						stat[top].state[newZeroPos]=s.state[zeroPos];
						//Set the value of position zeroPos before move
						stat[top].state[zeroPos]=s.state[newZeroPos];
						//After moving in all directions, depth++
						//depthDFS[top]=depthDFS[parent]+1;
						stat[top].setDepth(stat[parent].getDepth()+1);
						
						//If the new move hasn't happened before, insert the new move into array
						if (judgeHaveBeenInsertedDFS(stat[top])==true){
							stat[top].printDirection=printDirection;
							stat[top].superState=s;
							judgeHasChildren++;
							
							//Because the new element will be inserted into stack, the top needs to be added
							top++;
						}
					}
				}
				parent=top-1;
				while(stat[parent].hasBeenVisited==true)
					parent--;
				
				//If current state has no child, the top needs to be decreased.
				if (judgeHasChildren==0){
					parent=top-1;
					while(stat[parent].hasBeenVisited==true)
						parent--;
					if (parent==0)
						judgeBreak=true;
				}
			}
			else{
				parent=top-1;
				while(stat[parent].hasBeenVisited==true)
					parent--;
			}
			//Print traversal sequence
			//System.out.print(stat[top].printDirection);
		}
		return false;
	}
	
	//****************************************************** 
	//*** Purpose: To get the right path which is reversed before.
	//*** Input: Reversed string
	//*** Output: Right string
	//******************************************************
	public String stringReverse(String string) {  
        StringBuffer sb = new StringBuffer();  
        for (int i = string.length() - 1; i >= 0; i--) {  
            sb.append(string.charAt(i));  
        }  
        return sb.toString();  
    }
}
