package checkers.player;

import checkers.Game;
import checkers.util.Color;
import checkers.util.Move;

import java.util.Scanner;

public class HumanPlayer extends Player {

    public HumanPlayer(Game game, String name, Color color) {
        super(game, name, color);
    }

    @Override
    public Move getNextMove() {
        Scanner scanner = new Scanner(System.in);
        return new Move(scanner.nextInt() - 1, scanner.nextInt() - 1,
                scanner.nextInt() - 1, scanner.nextInt() - 1);
        //        checkers.Server server = new checkers.Server();
//        server.start();
//        while (!isGameFinished()) {
//            String s = scanner.next();
//            String[] moveStr = s.split(",");
//            int[] move = new int[4];
//            for (int i = 0; i < 4; i++)
//                move[i] = Integer.parseInt(moveStr[i]);
//            move(move);
//            JSONObject board = collectBoard();
//            oos.writeObject(board);
//        }
    }
}
