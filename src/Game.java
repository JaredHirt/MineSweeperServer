/**
 * This file is part of a solution to a Minesweeper server client
 * This creates an instance of the game which holds a 16x16 game of minesweeper
 */
public class Game {
    private byte[][] gameBoard;
    private byte[][] clientBoard;

    /**
     * When supplied with an array of booleans of size 256, it will create a gameBoard and an empty client board
     * @param seed Seed of bombs, true meaning a bomb will occur in that location
     */
    public Game(boolean[] seed){
        if (seed.length != 256)
            throw new RuntimeException("Invalid Size");
        gameBoard = createGameBoard(seed);
    }
    public void printGameBoard(){
        for(byte[] i:gameBoard) {
            for (byte j : i)
                System.out.printf("%5d",j);
            System.out.println();
        }



    }

    /**
     * Creates a game board with numbers 0-8 representing the amount of surrounding bombs, and -1 representing a bomb
     * @param seed an array of booleans of size 256 with 1 representing a bomb, and 0 representing no bomb
     * @return a Game board
     */
    private static byte[][] createGameBoard(boolean[] seed){
        byte[][] returnBoard = new byte[16][16];
        for (int i = 0; i < 256; i++){
            //Checking if bomb
            if(seed[i])
                returnBoard[i/16][i%16] = -1;
           //If not a bomb, how many bombs are beside it
            else{
                if(i-17>= 0 && seed[i-17])
                    returnBoard[i/16][i%16]++;
                if(i-16>= 0 && seed[i-16])
                    returnBoard[i/16][i%16]++;
                if(i-15>= 0 && seed[i-15])
                    returnBoard[i/16][i%16]++;
                if(i-1>= 0 && seed[i-1])
                    returnBoard[i/16][i%16]++;
                if(i+1< 256 && seed[i+1])
                    returnBoard[i/16][i%16]++;
                if(i+15< 256 && seed[i+15])
                    returnBoard[i/16][i%16]++;
                if(i+16< 256 && seed[i+16])
                    returnBoard[i/16][i%16]++;
                if(i+17< 256 && seed[i+17])
                    returnBoard[i/16][i%16]++;
            }
        }
        return returnBoard;
    }
}
