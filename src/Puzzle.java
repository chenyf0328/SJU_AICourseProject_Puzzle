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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
 
public class Puzzle {
	private static int[] initState;
	private static int[] goalState;
	
	//****************************************************** 
	//*** Purpose: According to the file path to read the file and get the initial state and goal state.	
	//*** Input: File path
	//*** Output: None
	//******************************************************
	public static void readFile(String path) throws IOException{
        File file=new File(path);
        if(!file.exists()||file.isDirectory())
            throw new FileNotFoundException();
        BufferedReader br=new BufferedReader(new FileReader(file));
        String temp=null;
        initState=new int[16];
        goalState=new int[16];
        temp=br.readLine();
        String[] stringSplit = temp.split(" ");
        
        //Check the number of data in test file is correct or not.
        if (stringSplit.length!=16){
        	System.out.println("The number of data in test file is not correct. Program shut down. "
        			+ "Please re-start the program and check the data! ");
        	System.exit(0);
        }
        
        //Split the String by space
    	for (int i=0;i<16;i++)
    		initState[i]=Integer.parseInt(stringSplit[i]);
        temp=br.readLine();
        stringSplit = temp.split(" ");
        
      //Check the number of data in test file is correct or not.
        if (stringSplit.length!=16){
        	System.out.println("The number of data in test file is not correct. Program shut down. "
        			+ "Please re-start the program and check the data! ");
        	System.exit(0);
        }
        
        for (int i=0;i<16;i++)
        	goalState[i]=Integer.parseInt(stringSplit[i]);
        
        //Check the number in initial state and goal state is the same or not.
        boolean judgeSameNum=false;
        for (int i=0;i<16;i++){
        	judgeSameNum=false;
        	for (int j=0;j<16;j++)
        		if (initState[i]==goalState[j])
        			judgeSameNum=true;
        	if (judgeSameNum==false){
        		System.out.println("There's different data in initial state and goal state. "
        				+ "Program shut down. Please re-start the program and check the data! ");
        		System.exit(0);
        	}
        }      
    }
	
	//****************************************************** 
	//*** Purpose: Beginning of the program and get the input order and test file's path.	
	//*** Input: Order and test file path
	//*** Output: None
	//******************************************************
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Solution mySolution=new Solution();
		mySolution.depth[0]=0;
        
		//Judge if the the input order and file format is correct.
        if (args[0].equals("BFS") && args[1].substring(args[1].length()-4, args[1].length()).equals(".dat")){
        	readFile(args[1]);
			if (mySolution.solvePuzzleBFS(new State(initState),new State(goalState))==null)
				System.out.println("Sorry, cannot find the solution!");
        }
        else if (args[0].equals("DFS") && args[1].substring(args[1].length()-4, args[1].length()).equals(".dat")){
			readFile(args[1]);
			if (mySolution.solvePuzzleDFS(new State(initState),new State(goalState))==null)
				System.out.println("Sorry, cannot find the solution!");
        }
        else
        	System.out.println("Sorry, the input search method or the test file's format is wrong. "
        			+ "Program shut down. Please re-start the program and re-enter the search method!");
	}

}
