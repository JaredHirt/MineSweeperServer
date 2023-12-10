/**
 * This file is part of a solution to a Minesweeper server client for a coding competition
 */
package main;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        File infile = new File("Games.seed");
        boolean[][] seeds = convertFileToSeeds(infile);
        int score = 0;
        int[] input;
        Game game;






        //Creating the Socket to communicate with client program
        ServerSocket serverSocket = new ServerSocket(4999);
        Socket socket = serverSocket.accept();
        System.out.println("Client Connected");

        InputStreamReader clientIn = new InputStreamReader(socket.getInputStream());
        Scanner clientInput = new Scanner(clientIn);

        PrintWriter clientOutput = new PrintWriter(socket.getOutputStream());


        for (boolean[] seed : seeds) {
            game = new Game(seed);
            while (game.gameGoing()) {
                sendClientBoard(clientOutput, game.getClientBoard());
               input = receiveInput(clientInput);
                game.revealTile(input[0], input[1]);
            }
            score += game.getScore();
            System.out.printf("Your current score is: %d%nYou earned %d points this game%n", score, game.getScore());
        }
        System.out.printf("Your final score was: %d", score);
        socket.close();
        serverSocket.close();
    }

    /**
     * Converts a file to different seeds to feed the Game
     * @param file the file containing the seeds
     * @return An array of seeds
     * @throws FileNotFoundException if the file does not exist
     */
    public static boolean[][] convertFileToSeeds(File file) throws FileNotFoundException {
        int numLines = -1;
        String[] lines;
        Scanner lineCounter = new Scanner(file);
        while(lineCounter.hasNext()){
            lineCounter.nextLine();
            numLines++;
        }
        boolean[][] returnArr = new boolean[numLines][256];
        Scanner reader = new Scanner(file);
        lines = new String[numLines];
        for(int i = 0; i< numLines; i++){
            lines[i] = reader.nextLine();
        }

            for (int i = 0; i < numLines; i++)
                for (int j = 0; j < 256; j++)
                    try {
                        returnArr[i][j] = lines[i].charAt(j) == '1';
                    }catch(StringIndexOutOfBoundsException e){
                        System.out.printf("Input file only has %d characters on line %d, must have 256%n", j, i);
                        System.exit(-1);
                    }
            return returnArr;

    }

    /**
     * Sends the clients board to the client
     * @param clientBoard the board to send to the client
     */
    public static void sendClientBoard(PrintWriter clientOutput, String[] clientBoard){
        for (String s:clientBoard)
            clientOutput.println(s);
        clientOutput.flush();
    }
    /**
     * Receives input from the client in the form of an integer array of size 2
     * @return input array of size 2
     */
    public static int[] receiveInput(Scanner clientInput){
       int[] input = new int[2];
       input[0] = clientInput.nextInt();
       input[1] = clientInput.nextInt();
       return input;
    }

}