/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs420project2;

import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Jason Kaufman
 */
public class CS420Project2
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        // TODO code application logic here

        //The length of the board.
        final int BOARD_LENGTH = 21;

        Random random = new Random();

        Scanner keyboard = new Scanner(System.in);

        int[] initialQueenPositions = new int[BOARD_LENGTH];

        int numberSucceeded = 0;
        int numberFailed = 0;

        //Will be used to determine runtime for finding each solution.
        long initTime;
        long finalTime;
        long totalTime;

        long sumTime = 0;
        int sumSearchCost = 0;

        System.out.print("Simulated Annealing (1) or Genetic Algorithm (2): ");
        int userChoice = Integer.parseInt(keyboard.nextLine());

        int numRuns = 200;

        switch (userChoice)
        {
            //Perform simulated annealing.
            case 1:
                for (int i = 0; i < numRuns; i++)
                {
                    for (int j = 0; j < BOARD_LENGTH; j++)
                    {
                        initialQueenPositions[j] = random.nextInt(BOARD_LENGTH);
                    }

                    Board newBoard = new Board(initialQueenPositions);

                    initTime = System.currentTimeMillis();

                    if (!SimulatedAnnealing.Solve(newBoard))
                    {
                        numberFailed++;
                    }
                    else
                    {
                        numberSucceeded++;
                    }

                    finalTime = System.currentTimeMillis();
                    totalTime = finalTime - initTime;

                    sumTime += totalTime;
                    sumSearchCost += newBoard.getSearchCost();
                }

                System.out.println("\nSimulated Annealing");
                break;
                
            //Perform genetic algorithm.
            case 2:
                Board solutionBoard;

                for (int i = 0; i < numRuns; i++)
                {
                    for (int j = 0; j < BOARD_LENGTH; j++)
                    {
                        initialQueenPositions[j] = random.nextInt(BOARD_LENGTH);
                    }

                    initTime = System.currentTimeMillis();

                    solutionBoard = GeneticAlgorithm.Solve(BOARD_LENGTH);

                    if (!solutionBoard.isSolved())
                    {
                        numberFailed++;
                    }
                    else
                    {
                        numberSucceeded++;
                    }

                    finalTime = System.currentTimeMillis();
                    totalTime = finalTime - initTime;

                    sumTime += totalTime;
                    sumSearchCost += solutionBoard.getSearchCost();
                }

                System.out.println("\nGenetic Algorithm");
                break;
            default:
                System.out.println("Incorrect option");
                System.exit(0);
                break;
        }

        //Display information about the chosen algorithm.
        System.out.println("Successful attempts: " + numberSucceeded + "/" + numRuns);
        System.out.println("Failed attempts: " + numberFailed + "/" + numRuns);
        System.out.println("Average search cost: " + sumSearchCost / numRuns);
        System.out.println("Average time: " + sumTime / numRuns + " ms");
    }

}
