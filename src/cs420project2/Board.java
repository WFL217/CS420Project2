/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs420project2;

import java.util.Arrays;

/**
 * Class for creating an object that represents the state of an n-queen board.
 *
 * @author Jason Kaufman
 */
public class Board
{

    //Declare instance variables.
    private final int[] currentState;
    private final int boardLength;
    private final int boardCost;

    private int searchCost;
    private boolean solved;

    //Constructor to create a Board object based on the order of tiles, the solution depth, if it is an initial state, and the type of heuristic.
    public Board(int[] initialQueenPositions)
    {
        currentState = initialQueenPositions;
        boardLength = initialQueenPositions.length;
        boardCost = BoardHelper.GetCost(initialQueenPositions);

        searchCost = 0;
        solved = false;
    }

    //Displays the board of n-queens.
    public void DisplayState()
    {
        char[][] solvedBoard = new char[boardLength][boardLength];

        //Fill board with "_".
        for (char[] solvedBoard_ : solvedBoard)
        {
            Arrays.fill(solvedBoard_, '_');
        }

        //Place queens.
        for (int i = 0; i < solvedBoard.length; i++)
        {
            solvedBoard[currentState[i]][i] = 'Q';
        }

        for (int i = 0; i < boardLength; i++)
        {
            System.out.print(" _");
        }

        System.out.println();

        //From here, display the board.
        for (char[] solvedBoard_ : solvedBoard)
        {
            System.out.print("|");

            for (int j = 0; j < boardLength; j++)
            {
                System.out.print(solvedBoard_[j] + "|");
            }

            System.out.println();
        }

        System.out.println();

        for (int i = 0; i < boardLength; i++)
        {
            System.out.print("__");
        }

        System.out.println();
    }

    public int[] getCurrentState()
    {
        return currentState;
    }

    public int getBoardCost()
    {
        return boardCost;
    }

    public void IncrementSearchCost()
    {
        searchCost++;
    }

    public int getSearchCost()
    {
        return searchCost;
    }

    public void setSearchCost(int searchCost)
    {
        this.searchCost = searchCost;
    }

    public boolean isSolved()
    {
        return solved;
    }

    public void setSolved(boolean solved)
    {
        this.solved = solved;
    }
}
