package checkers.util;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class MinimaxTree {
    private final int DEPTH_LIMIT = 5;
    private Set<Node> rootChildren = new LinkedHashSet<>();
    private Node root;
    private Color me;

    public MinimaxTree(PieceType[][] board, Color me, Move preMove, boolean turnChanged) {
        root = new Node(true, null, board, preMove, turnChanged);
        this.me = me;
    }

    private void calculateValue(Node node) {
        PieceType[][] board = node.board;
        int h1 = 0;
        int h2 = 0;
        int h3 = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                PieceType p = board[i][j];
                if (me == Color.WHITE) {
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

    public Move dls_getMove() {
        dls(root, 0);
        for (Node node : rootChildren) {
            if (node.value == root.value)
                return node.preMove;
        }
        throw new RuntimeException("Best child not found");
    }

    private void dls(Node node, int depth) {
        if (Util.isGameFinished(node.board, node.pm, node.pj)) {
            if (node.isMax)
                node.value = Integer.MIN_VALUE;
            else
                node.value = Integer.MAX_VALUE;
        } else if (depth >= DEPTH_LIMIT) {
            calculateValue(node);
        } else {
            for (Node child : node.getChildren())
                dls(child, depth + 1);
        }
        if (node.parent != null) {
            if (node.parent.isMax) {
                if (node.value > node.parent.value)
                    node.parent.value = node.value;
            } else if (node.value < node.parent.value)
                node.parent.value = node.value;
        }
    }

    private class Node {
        final boolean isMax;
        Node parent;
        PieceType[][] board;
        List<Move> pm = new ArrayList<>();
        List<Move> pj = new ArrayList<>();
        Move preMove;
        boolean turnChanged;
        int value;
        int u;

        Node(boolean isMax, Node parent, PieceType[][] board
                , Move preMove, boolean turnChanged) {
            this.preMove = preMove;
            this.isMax = isMax;
            this.turnChanged = turnChanged;
            if (isMax)
                value = Integer.MIN_VALUE;
            else
                value = Integer.MAX_VALUE;
            this.parent = parent;
            this.board = board;
            Util.updateChoices(board, pm, pj,
                    isMax ? Color.BLACK : Color.WHITE
//                            me : me.not()//FIXME
                    , turnChanged, preMove
            );
        }

        List<Node> getChildren() {
            List<Node> children = new ArrayList<>();
            List<Move> p;
            if (!pj.isEmpty())
                p = pj;
            else
                p = pm;
            for (Move move : p) {
                Node child;
                PieceType[][] newBoard = Util.cloneBoard(board);
                boolean turnChanged = Util.move(newBoard, move);
                child = new Node(turnChanged ? !isMax : isMax, this,
                        newBoard, move, turnChanged);
                children.add(child);


                if (this == root) {
                    rootChildren.add(child);
                }
            }
            return children;
        }
    }
}
