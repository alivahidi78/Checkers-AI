package checkers;

import checkers.util.Color;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Game game;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Mode: 1.PvN 2.PvP");
        if (scanner.nextInt() == 2)
            game = new Game(Color.BLACK, false);
        else {
            System.out.println("Color: 1.Black 2.White");
            game = new Game(scanner.nextInt() == 1 ? Color.BLACK : Color.WHITE, true);
        }
        game.start();
    }
}
