package me.Danker.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class IceWalkUtils {

    public static List<Point> solve(char[][] board) {
        Point startPos = new Point(board.length - 1, board[0].length / 2);
        Point endPos = new Point(0, board[0].length / 2);
        List<Point> route = new ArrayList<>();
        route.add(startPos);
        return findSolution(board, startPos, endPos, route);
    }

    public static List<Point> findSolution(char[][] board, Point startPos, Point endPos, List<Point> route) {
        for (Direction direction : Direction.values()) {
            Point nextPoint = move(board, startPos, direction);
            if (nextPoint == null || route.contains(nextPoint)) continue;
            List<Point> newRoute = new ArrayList<>(route);
            newRoute.add(nextPoint);
            if (nextPoint.equals(endPos) && isComplete(board, newRoute)) return newRoute;
            List<Point> solution = findSolution(board, nextPoint, endPos, newRoute);
            if (solution == null) continue;
            return solution;
        }
        return null;
    }

    public static Point move(char[][] board, Point pos, Direction direction) {
        switch (direction) {
            case UP:
                if (pos.row != 0 && board[pos.row - 1][pos.column] != 'X') {
                    return new Point(pos.row - 1, pos.column);
                }
                break;
            case DOWN:
                if (pos.row != board.length - 1 && board[pos.row + 1][pos.column] != 'X') {
                    return new Point(pos.row + 1, pos.column);
                }
                break;
            case LEFT:
                if (pos.column != 0 && board[pos.row][pos.column - 1] != 'X') {
                    return new Point(pos.row, pos.column - 1);
                }
                break;
            case RIGHT:
                if (pos.column != board[0].length - 1 && board[pos.row][pos.column + 1] != 'X') {
                    return new Point(pos.row, pos.column + 1);
                }
                break;
        }
        return null;
    }

    public static boolean isComplete(char[][] board, List<Point> route) {
        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board[0].length; column++) {
                if (board[row][column] != 'X' && !route.contains(new Point(row, column))) return false;
            }
        }
        return true;
    }

    public static class Point {

        public int row;
        public int column;

        public Point(int row, int column) {
            this.row = row;
            this.column = column;
        }

        @Override
        public int hashCode() {
            return Objects.hash(row, column);
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Point) {
                Point point = (Point) obj;
                return row == point.row && column == point.column;
            }
            return false;
        }

    }

    enum Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }

}
