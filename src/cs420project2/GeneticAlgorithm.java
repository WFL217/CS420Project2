/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs420project2;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Class to handle an n-queen problem with a genetic algorithm.
 * 
 * @author Jason Kaufman
 */
public class GeneticAlgorithm
{

    //Chance to mutate.
    private static final float MUTATION_PROBABILITY = 0.50f;

    //Solves the board using crossovers and mutations.
    public static Board Solve(int boardLength)
    {
        //Holds the current generation.
        ArrayList<Board> population = GeneratePopulation(21);

        //Holds the next generation.
        ArrayList<Board> nextGeneration = new ArrayList<>();

        //The fitness value used to cull the population.
        int currentFitnessGoal;

        int[] parent1;
        int[] parent2;
        int[] child;

        //Positions of parent1 and parent2 respectively in the population. 
        int x, y;
        
        Board childBoard = null;

        //As long as there is a population to work with.
        while (!population.isEmpty())
        {
            //If population is 1 or 0, break the loop.
            if(population.size() <= 1)
            {
                break;
            }

            //Sort the population by their fitness values.
            Collections.sort(population, (Board b1, Board b2) -> Integer.compare(b1.getBoardCost(), b2.getBoardCost()));

            //New fitness value should remain quite low so the population does not die off so quickly.
            currentFitnessGoal = BoardHelper.GetCost(population.get(population.size() - 2).getCurrentState());

            //For everyone in the population, populate the next generation with children.
            for (int i = 0; i < population.size(); i++)
            {
                //Choose random parents with fitness values above the fitness value of the middle indiviual.
                x = BoardHelper.RANDOM.nextInt(population.size() / 2);
                y = BoardHelper.RANDOM.nextInt(population.size() / 2);
                
                parent1 = population.get(x).getCurrentState();

                parent2 = population.get(y).getCurrentState();

                //Cross the genes to make a new child.
                child = Crossover(parent1, parent2);

                //Mutate the child if a mutation happens.
                if (Math.random() < MUTATION_PROBABILITY)
                {
                    Mutate(child);
                }
                
                //Create child board and set its step cost.
                childBoard = new Board(child);
                childBoard.setSearchCost(population.get(x).getSearchCost() + 1);

                //If child is the solution, stop and display the solution.
                if (BoardHelper.GetCost(child) == 0)
                {
                    System.out.println("Solved");
                    new Board(child).DisplayState();
                    childBoard.setSolved(true);
                    return childBoard;
                }

                //If child is better than the current fitness level, keep it in the next generation.
                if (BoardHelper.GetCost(child) <= currentFitnessGoal)
                {
                    nextGeneration.add(childBoard);
                }
            }

            //Save the next generation.
            population = new ArrayList<>(nextGeneration);

            //Clear the list.
            nextGeneration.clear();

        }

        return childBoard;
    }

    //Crosses the information of two parents.
    private static int[] Crossover(int[] parent1, int[] parent2)
    {
        int[] child = new int[parent1.length];
        int crossoverPoint = BoardHelper.RANDOM.nextInt(parent1.length);

        System.arraycopy(parent1, 0, child, 0, crossoverPoint);

        System.arraycopy(parent2, crossoverPoint, child, crossoverPoint, child.length - crossoverPoint);

        return child;

    }

    //Changes a random value in the child to a random value.
    private static void Mutate(int[] child)
    {
        child[BoardHelper.RANDOM.nextInt(child.length)] = BoardHelper.RANDOM.nextInt(child.length);
    }

    //Creates the first generation of individuals.
    private static ArrayList<Board> GeneratePopulation(int boardLength)
    {
        ArrayList<Board> population = new ArrayList<>();

        int[] initialQueenPositions;

        for (int i = 0; i < 1000; i++)
        {
            initialQueenPositions = new int[boardLength];

            for (int j = 0; j < boardLength; j++)
            {
                initialQueenPositions[j] = BoardHelper.RANDOM.nextInt(boardLength);
            }

            population.add(new Board(initialQueenPositions));
        }

        return population;
    }
}
