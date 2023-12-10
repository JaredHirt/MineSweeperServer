/**
 * This file is part of a solution to a Minesweeper server client
 * This creates an instance of the game which holds a 16x16 game of minesweeper
 */

package main;

public class Game {
    private byte[][] gameBoard;
    private byte[][] clientBoard;
    private int tilesRevealed;
    private int bombs;
    private boolean gameGoing;
    private boolean[] seed;

    /**
     * When supplied with an array of booleans of size 256, it will create a gameBoard and an empty client board
     * @param seed Seed of bombs, true meaning a bomb will occur in that location
     */
    public Game(boolean[] seed){
        if (seed.length != 256)
            throw new RuntimeException("Invalid Size");
        this.seed = seed;
        tilesRevealed = 0;
        bombs = 0;
        clientBoard = initializeClientBoard();
        gameGoing = true;

    }
    public boolean gameGoing(){
        return gameGoing;
    }
    public int getScore(){
        return tilesRevealed;
    }

    /**
     * Prints the gameBoard
     */
    public void printGameBoard(){
        for(byte[] i:gameBoard) {
            for (byte j : i)
                System.out.printf("%d,",j);
            System.out.println();
        }
    }

    /**
     * Reveals the tile at the specified coordinates
     * @param x The X coordinate you would like to reveal (left to right starting at 0 and less than 16)
     * @param y the Y coordinate you would like to reveal (top to bottom starting at 0 and less than 16)
     */
    public void revealTile(int x, int y){
        //Making sure the first place you click is not a bomb, if it is then removing that bomb as to emulate the original mine sweeper
        if(gameBoard == null) {
            seed[y*16 + x] = false;
            gameBoard = createGameBoard(seed);
        }
       if(x > 15 || x < 0 || y > 15 || y < 0)
           return;
       if(clientBoard[x][y] != -2)
           return;
       clientBoard[x][y] = gameBoard[x][y];
       //Checks if you stepped on a bomb
       if(clientBoard[x][y] == -1){
          gameGoing = false;
          return;
       }

       //Uncovers the surrounding tiles if you uncovered a blank slate
       if(clientBoard[x][y] == 0){
           revealTile(x, y-1);
           revealTile(x-1,y);
           revealTile(x+1,y);
           revealTile(x, y+1);
       }
       tilesRevealed++;
       if(tilesRevealed + bombs == 256)
           gameGoing = false;
    }

    /**
     * Returns the state of the clients board with comma seperated values
     * @return The state of the board in an array of strings, with commas separating each tile
     */
    public String[] getClientBoard() {
        String[] board = new String[16];
        for(int i = 0; i<16; i++){
            board[i] = "";
        }
        for (byte i = 0; i < 16; i++) {
            for (byte j = 0; j < 16; j++)
               if(j != 15)
                   board[i] = board[i] + clientBoard[i][j] + ",";
                else
                    board[i] = board[i] + clientBoard[i][j];
        }
        return board;
    }

    /**
     * Creates a client board of 16x16 size with -2 for every slot
     * @return Initialized client board
     */
    private byte[][] initializeClientBoard(){
       byte[][] returnBoard  = new byte[16][16];
       for(byte i = 0; i < 16; i++)
           for(byte j = 0; j < 16; j++)
               returnBoard[i][j] = -2;
       return returnBoard;
    }

    /**
     * Creates a game board with numbers 0-8 representing the amount of surrounding bombs, and -1 representing a bomb
     * @param seed an array of booleans of size 256 with 1 representing a bomb, and 0 representing no bomb
     * @return a Game board
     */
    private byte[][] createGameBoard(boolean[] seed){
        byte[][] returnBoard = new byte[16][16];
        for (int i = 0; i < 256; i++){
            //Checking if bomb
            if(seed[i]) {
                returnBoard[i / 16][i % 16] = -1;
               bombs++;
            }
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
