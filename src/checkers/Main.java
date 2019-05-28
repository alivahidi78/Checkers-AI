package checkers;

import checkers.stream.DefaultStream;
import checkers.stream.NetworkStream;
import checkers.stream.Stream;
import checkers.util.Color;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Game game;
        Scanner scanner = new Scanner(System.in);
        Stream stream;
        Color color;
        boolean isPvC;
        System.out.println("Mode:\n 1.Player vs Computer\n 2.Player vs Player");
        if (scanner.nextInt() == 2) {
            color = Color.BLACK;
            isPvC = false;
        } else {
            System.out.println("Color: 1.Black 2.White");
            color = scanner.nextInt() == 1 ? Color.BLACK : Color.WHITE;
            isPvC = true;
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
        game = new Game(stream, color, isPvC);
        game.start();
    }
}
