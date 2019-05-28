package checkers.util;

import checkers.player.Player;

import java.util.ArrayList;
import java.util.List;

public class MinimaxTree {
    private final int DEPTH_LIMIT = 5;
    private Database db;
    private Node root;
    private Player me;
    private Player opponent;

    public MinimaxTree(PieceType[][] board, Player me, Player opponent) {
        db = Database.getInstance();
        root = new Node(true, null, board);
        this.me = me;
        this.opponent = opponent;
    }

    private void calculateValue(Node node) {
        PieceType[][] board = node.board;
        int h1 = 0;
        int h2 = 0;
        int h3 = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                PieceType p = board[i][j];
                if (me.color == Color.WHITE) {
                    switch (p) {
                        case WHITE_MAN:
                            h1 += 3;
                            h2 += 7 - i;
                            h3++;
                            break;
                        case BLACK_MAN:
                            h1 -= 3;
                            h2 -= i;
                            h3--;
                            break;
                        case WHITE_KING:
                            h1 += 5;
                            h3++;
                            break;
                        case BLACK_KING:
                            h1 -= 5;
                            h3--;
                    }
                } else {
                    switch (p) {
                        case WHITE_MAN:
                            h1 -= 3;
                            h2 -= 7 - i;
                            h3--;
                            break;
                        case BLACK_MAN:
                            h1 += 3;
                            h2 += i;
                            h3++;
                            break;
                        case WHITE_KING:
                            h1 -= 5;
                            h3--;
                            break;
                        case BLACK_KING:
                            h1 += 5;
                            h3++;
                    }
                }
            }
        }
        node.value = (8 * h1 + 2 * h2 + h3);
    }

    public void dls() {
        dls(root, 0);
    }

    private void dls(Node node, int depth) {
        if (depth >= DEPTH_LIMIT) {
            return;
        }
        for (Node child : node.getChildren()) {
            dls(child, depth + 1);
        }
    }

    private class Node {
        final boolean isMax;
        Node parent;
        PieceType[][] board;
        int value;
        int u;

        public Node(boolean isMax, Node parent, PieceType[][] board) {
            this.isMax = isMax;
            if (isMax)
                value = Integer.MIN_VALUE;
            else
                value = Integer.MAX_VALUE;
            this.parent = parent;
            this.board = board;
        }

        public List<Node> getChildren() {
            List<Node> children = new ArrayList<>();
            List<Move> pm = new ArrayList<>();
            List<Move> pj = new ArrayList<>();
            Util.updateChoices(board, pm, pj, isMax ? me.color : opponent.color);
            List<Move> p;
            if (!pj.isEmpty())
                p = pj;
            else
                p = pm;
            for (Move move : p) {
                Node child;
                PieceType[][] newBoard = Util.cloneBoard(board);
                boolean turnChanged = Util.move(newBoard, move);
                child = new Node(turnChanged ? !isMax : isMax, this, newBoard);
                children.add(child);
            }
            return children;
        }
    }
}
