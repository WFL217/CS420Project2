/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs420project2;

/**
 * Class to handle an n-queen problem with simulated annealing.
 * 
 * @author Jason Kaufman
 */
public class SimulatedAnnealing
{

    private static boolean solved;

    //Solves the board using simulated annealing.
    public static boolean Solve(Board initialBoard)
    {
        //The temperature variable.
        float temperature = 200.0f;

        int currentCost = BoardHelper.GetCost(initialBoard.getCurrentState());

        //Makes random moves in order to not get stuck and eventually return a solution.
        for (int i = 0; i < 1000; i++)
        {
            MakeRandomMove(initialBoard, currentCost, temperature);
            currentCost = BoardHelper.GetCost(initialBoard.getCurrentState());

            //If the board is a solution, stop and display solution.
            if (currentCost == 0)
            {
                System.out.println("Solved");
                initialBoard.DisplayState();
                solved = true;
                break;
            }

            //Reduce the temperature, reducing the number of bad moves.
            temperature = temperature *= .95;

            //If temperature is 0, stop looping.
            if (temperature == 0)
            {
                break;
            }
        }

        return solved;
    }

    //Makes a random move on the board.
    //Will only make a good move, unless the acceptance probability is met, 
    //which means a bad move will be made.
    private static void MakeRandomMove(Board initialBoard, int currentCost_, float temperature_)
    {
        while (true)
        {
            int row = BoardHelper.RANDOM.nextInt(initialBoard.getCurrentState().length);
            int column = BoardHelper.RANDOM.nextInt(initialBoard.getCurrentState().length);

            int tempRow = initialBoard.getCurrentState()[column];

            initialBoard.getCurrentState()[column] = row;

            int newCost = BoardHelper.GetCost(initialBoard.getCurrentState());

            //Accept good move.
            if (newCost < currentCost_)
            {
                break;
            }

            int changeInE = currentCost_ - newCost;
            double probability = Math.min(1, Math.exp(changeInE / temperature_));

            //Accept bad move if probability was met.
            if (Math.random() < probability)
            {
                break;
            }

            //Undo move.
            initialBoard.getCurrentState()[column] = tempRow;
        }
        
        //New move means step cost increases by 1.
        initialBoard.IncrementSearchCost();
    }
}
