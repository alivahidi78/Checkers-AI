package checkers;

import checkers.stream.DefaultStream;
import checkers.stream.NetworkStream;
import checkers.stream.Stream;
import checkers.util.Color;
import checkers.util.Game;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Game game = Game.getInstance();
        Scanner scanner = new Scanner(System.in);
        Stream stream;
        Color color;
        int d1 = 0, d2 = 0;
        System.out.println("Mode:\n" +
                " 1.Player vs Player\n" +
                " 2.Player vs Computer\n" +
                " 3.Computer vs Computer");
        int mode = scanner.nextInt();
        if (mode == 2) {
            System.out.println("Color: 1.Black 2.White");
            color = scanner.nextInt() == 1 ? Color.BLACK : Color.WHITE;
        } else
            color = Color.BLACK;
        switch (mode) {
            case 2:
                System.out.println("Computer difficulty:");
                d1 = scanner.nextInt();
                break;
            case 3:
                System.out.println("Black Computer difficulty:");
                d1 = scanner.nextInt();
                System.out.println("White Computer difficulty:");
                d2 = scanner.nextInt();
        }
        System.out.println("Graphics:\n 1.Graphical UI\n 2.Terminal");
        if (scanner.nextInt() == 2) {
            stream = DefaultStream.getInstance();
        } else {
            stream = NetworkStream.getInstance();
            Thread thread = new Thread(() -> NetworkStream.getInstance().start());
            thread.setDaemon(true);
            thread.start();
        }
        game.start(stream, color, mode, d1, d2);
    }
}
