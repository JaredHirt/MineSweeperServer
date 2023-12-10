/**
 * This file is part of a solution to a Minesweeper server client for a coding competition
 */
public class Main {
    public static void main(String[] args) {
        boolean[] seed1 = new boolean[256];
        seed1[17] = true;
        Game game1 = new Game(seed1);
        game1.printGameBoard();
    }

}