import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class GraphCycleCheck {

    /*************************************
     Build the direct graph class
     ************************************/
    static class DirectGraph {
        private int V; // V: vertices
        private List<List<Integer>> adjacent;

        public DirectGraph(int V) {
            this.V = V;
            adjacent = new ArrayList<>(V);
            for (int i = 0; i < V; i++) { //all vertices are the arrayList, and inside of the each
                                         //list element, add the linkedList to connect the directed vertices
                adjacent.add(new LinkedList<>());
            }
        }

        /************************************************
         The function fot add the edge
         */
        private void addEdge(int from, int to) {
            adjacent.get(from).add(to);
        }

        /************************************************
         The function to check whole graph has at least one cycle
         */
        private boolean hasCycle() {
            //build the array for mark the visited vertices
            boolean[] visitedVertex = new boolean[V];
            //build the stack to put the visit vertices for the back check
            boolean[] visitedStack = new boolean[V];

            for (int i = 0; i < V; i++) { //check each vertices
                if (cycleCheck(i, visitedVertex, visitedStack)) {
                    return true; //if it has cycle, return true;
                }
            }
            return false; //otherwise return false
        }

        /************************************************
         This is the function check each vertex start with. return true if it has cycle.
         */
        private boolean cycleCheck(int i, boolean[] visitedVertex, boolean[] visitedStack) {
            if (visitedStack[i]) {   //if the next vertex already inside of stack which means cycle
                return true;
            }
            if (visitedVertex[i]) {//if vertices already visited return false. We don't need to trace this vertex again.
                return false;
            }
            //otherwise keep continue to check the other vertices
            visitedVertex[i] = true; //set current vertex true
            visitedStack[i] = true; //set the visitedStack current i also true

            List<Integer> vertexILinkedList = adjacent.get(i); //get the current vertex linkedList to check connection
                                                               //get(i) get the arrayList element, not the index
            for (Integer j : vertexILinkedList) {
                if (cycleCheck(j, visitedVertex, visitedStack)) { //recursive call
                    return true;
                }
            }
            visitedStack[i] = false; //if vertices no connection. set visitStack false.
            return false;
        }

    }//end the direct graph class

    /*************************************
     Main function
     ************************************/
    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        String answer;
        do {
            try {
           /* int vertexNumber;
            System.out.println("Input how many vertices in the graph");
            vertexNumber = scan.nextInt();*/

                DirectGraph directGraph = new DirectGraph(10); //give the big vertices number 10 for checking.
                                                                  // it can work for (1-10 vertices in the graph), we can change this for how many vertices we want.

                System.out.println("Input the the connected edges. Format as (1,2). * finish input. \n Or");
                System.out.println(" ==You can copy this input to the console for 5 vertices==\n" +
                        "(1,2)\n" +
                        "(2,3)\n" +
                        "(3,1)\n" +
                        "(1,4)\n" +
                        "(4,5)\n" +
                        "*");
                System.out.println(" ==You can copy this input to the console for 3 vertices==\n" +
                        "(1,2)\n" +
                        "(2,3)\n" +
                        "(1,3)\n" +
                        "*\n");

                String input; //consider input format as string
                int from; //will get from vertex from input
                int to; //will get to vertices from input
                input = scan.next();
                while (!input.equals("*")) {
                    from = Character.getNumericValue(input.charAt(1));
                    to = Character.getNumericValue(input.charAt(3)); //check string position 1 and position 3 is numerical to add to the edge
                    directGraph.addEdge(from - 1, to - 1); //add input value to build the graph
                    input = scan.next();
                }

                //call the function has cycle to check.
                if (directGraph.hasCycle()) {
                    System.out.println("\ndirectGraph.hasCycle()="+ directGraph.hasCycle() + "; Graph has cycle\n");
                } else {
                    System.out.println("\ndirectGraph.hasCycle()="+ directGraph.hasCycle() + "; Graph does not have cycle\n");
                }
            } catch (Exception e) {
                System.out.println("\nPlease input the right format edges with correspond vertices\n");
            }

            System.out.println("Do you want check anther Graph? Y/N (Y for continue; N for stop)");
            answer = scan.next();
        } while (answer.equalsIgnoreCase("Y"));
    }

}
