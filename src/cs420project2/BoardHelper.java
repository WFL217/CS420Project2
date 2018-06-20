/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs420project2;

import java.util.Random;

/**
 * A class to assist both the genetic algorithm and simulated annealing.
 * 
 * @author Jason Kaufman
 */
public class BoardHelper
{
    //Create a Random variable.
    public static final Random RANDOM = new Random();

    //Obtains the cost of a board depending on the number of attacking queens.
    //The smaller the number of attacking queens, the better.
    public static int GetCost(int[] boardState)
    {
        int cost = 0;

        for (int i = 0; i < boardState.length; i++)
        {
            for (int j = i + 1; j < boardState.length; j++)
            {
                if ((boardState[i] == boardState[j]) || (j - i == Math.abs(boardState[i] - boardState[j])))
                {
                    cost++;
                }
            }
        }

        return cost;
    }
}
