/*
  Yingrong Chen
  ych2429

THIS CODE WAS MY OWN WORK , IT WAS WRITTEN WITHOUT CONSULTING ANY
SOURCES OUTSIDE OF THOSE APPROVED BY THE INSTRUCTOR. Yingrong Chen
*/

import java.util.Stack;

public class NQueens {
 
  //***** fill in your code here *****
  //feel free to add additional methods as necessary
  static Stack<Integer> solution = new Stack<>();
  static int index = 0;

  public static boolean check(int index){
    //column check
    if (solution.contains(index)){
      return false;
    }
    //diagonal check
    Integer copy[] = new Integer[solution.size()];
    solution.copyInto(copy); //array index represent the row, value represent the column
    for (int i = 0; i < copy.length; i++){
      // if column distance from other queen equals to the row distance
      if (Math.abs(i - solution.size()) == Math.abs(copy[i] - index)){
        return false;
      }
    }
    return true;
  }

  public static void backtrack(int n) {
    while (!solution.isEmpty() && index == n - 1) { //while current & previous rows are at the end
      index = solution.pop(); //return to last row, starts from existing column
    }
    index++; //try next slot
  }

    //finds and prints out all solutions to the n-queens problem
  public static int solve(int n) {
    //***** fill in your code here *****

    int numSolution = 0;
    while (index < n) {
      //base case: if all rows are filled
      if (solution.size() == n) {
        numSolution++;
        printSolution(solution);
        if (n == 1){
          return numSolution;
        }
        index = solution.pop(); //return to last row
        backtrack(n); //continue searching
      }
      if (check(index)) {
        solution.push(index);
        index = 0; //starts from 0 col for the next row
      } else {
        backtrack(n);
      }
    }
    //update the following statement to return the number of solutions found
    return numSolution;
  }//solve()
  
  //this method prints out a solution from the current stack
  //(you should not need to modify this method)
  private static void printSolution(Stack<Integer> s) {
    for (int i = 0; i < s.size(); i ++) {
      for (int j = 0; j < s.size(); j ++) {
        if (j == s.get(i))
          System.out.print("Q ");
        else
          System.out.print("* ");
      }//for
      System.out.println();
    }//for
    System.out.println();  
  }//printSolution()
  
  // ----- the main method -----
  // (you shouldn't need to change this method)
  public static void main(String[] args) {
    int n = 8;
//   pass in parameter n from command line
    if (args.length == 1) {
      n = Integer.parseInt(args[0].trim());
      if (n < 1) {
        System.out.println("Incorrect parameter");
        System.exit(-1);
      }//if
    }//if

    int number = solve(n);
    System.out.println("There are " + number + " solutions to the " + n + "-queens problem.");
  }//main()
}