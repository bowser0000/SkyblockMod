package me.Danker.utils;

import java.util.*;

public class SilverfishUtils {

    // bfs
    public static List<Point> solve(char[][] board, Point startPos, List<Point> endColumns) {
        LinkedList<Point> queue = new LinkedList<>();
        Map<Point, Point> visited = new HashMap<>();
        queue.add(startPos);
        visited.put(startPos, null);
        while (!queue.isEmpty()) {
            if (queue.size() > 1000000) break;
            Point position = queue.pollFirst();
            for (Direction direction : Direction.values()) {
                Point pushedPoint = push(board, position, direction);
                if (visited.containsKey(pushedPoint)) continue;
                queue.add(pushedPoint);
                visited.put(pushedPoint, position);
                for (Point endColumn : endColumns) {
                    if (pushedPoint.equals(endColumn)) {
                        List<Point> route = new ArrayList<>();
                        Point lastPoint = pushedPoint;
                        while (lastPoint != null) {
                            route.add(0, lastPoint);
                            lastPoint = visited.get(lastPoint);
                        }
                        return route;
                    }
                }
            }
        }
        return new ArrayList<>();
    }

    public static Point push(char[][] board, Point pos, Direction direction) {
        switch (direction) {
            case UP:
                for (int row = pos.row; row >= 0; row--) {
                    if (board[row][pos.column] == 'X') {
                        return new Point(row + 1, pos.column);
                    }
                }
                return new Point(0, pos.column);
            case DOWN:
                for (int row = pos.row; row <= 18; row++) {
                    if (board[row][pos.column] == 'X') {
                        return new Point(row - 1, pos.column);
                    }
                }
                return new Point(18, pos.column);
            case LEFT:
                for (int column = pos.column; column >= 0; column--) {
                    if (board[pos.row][column] == 'X') {
                        return new Point(pos.row, column + 1);
                    }
                }
                return new Point(pos.row, 0);
            case RIGHT:
                for (int column = pos.column; column <= 18; column++) {
                    if (board[pos.row][column] == 'X') {
                        return new Point(pos.row, column - 1);
                    }
                }
                return new Point(pos.row, 18);
        }
        return null;
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
