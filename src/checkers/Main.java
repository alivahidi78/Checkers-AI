package checkers;

import checkers.Stream.DefaultStream;
import checkers.Stream.NetworkStream;
import checkers.Stream.Stream;
import checkers.util.Color;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Game game;
        Scanner scanner = new Scanner(System.in);
        Stream stream;
        System.out.println("Graphics:\n 1.Graphical UI\n 2.Terminal");
        if (scanner.nextInt() == 2)
            stream = DefaultStream.getStream();
        else {
            NetworkStream.getStream().start();
            stream = NetworkStream.getStream();
        }
        System.out.println("Mode:\n 1.Player vs Computer\n 2.Player vs Player");
        if (scanner.nextInt() == 2)
            game = new Game(stream, Color.BLACK, false);
        else {
            System.out.println("Color: 1.Black 2.White");
            game = new Game(stream, scanner.nextInt() == 1 ? Color.BLACK : Color.WHITE, true);
        }
        game.start();
    }
}
