package me.Danker.utils;

import me.Danker.features.puzzlesolvers.BoulderSolver;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class BoulderUtils {

    public static HashSet<String> seenBoardStates = new HashSet<>();
    public static long iterations = 0;
    public static int fastestSolution = 10;

    /*
    CC BY-SA 4.0
    Unmodified
    Question: https://stackoverflow.com/questions/5617016/
    Answer: https://stackoverflow.com/a/53397359
    Author: https://stackoverflow.com/users/3647002/gayan-weerakutti
    */
    public static char[][] copy(char[][] array) {
        return Arrays.stream(array).map(char[]::clone).toArray(char[][]::new);
    }

    public static char[][] flipVertically(char[][] board) {
        char[][] newBoard = new char[7][7];

        for (int row = 0; row < 7; row++) {
            System.arraycopy(board[6 - row], 0, newBoard[row], 0, 7);
        }

        return newBoard;
    }

    public static char[][] flipHorizontally(char[][] board) {
        char[][] newBoard = new char[7][7];

        for (int row = 0; row < 7; row++) {
            for (int column = 0; column < 7; column++) {
                newBoard[row][column] = board[row][6 - column];
            }
        }

        return newBoard;
    }

    public static char[][] rotateClockwise(char[][] board) {
        char[][] newBoard = new char[7][7];

        for (int row = 0; row < 7; row++) {
            for (int column = 0; column < 7; column++) {
                newBoard[column][6 - row] = board[row][column];
            }
        }

        return newBoard;
    }

    public static char[][] removeFirstRow(char[][] board) {
        List<char[]> list = new ArrayList<>(Arrays.asList(board));
        list.remove(0);
        return list.toArray(new char[][]{});
    }

    public static String toString(char[][] board) {
        StringBuilder sb = new StringBuilder();
        for (char[] row : board) {
            for (char column : row) {
                sb.append(column);
            }
        }
        return sb.toString();
    }

    public static int findSolution(char[][] board, int depth, List<int[]> route) {
        if (depth > 9) return 10;
        String boardString = toString(board);
        if (seenBoardStates.contains(boardString)) return 10; // this line turns 600 million iterations to 700 thousand
        seenBoardStates.add(boardString);
        if (hasOpenPath(board)) return depth;

        char[][] floodFilledBoard = floodFillBottom(board);
        List<int[]> newRoute = new ArrayList<>(route);
        int solutionLength = 10;
        int bestRow = -1;
        int bestColumn = -1;
        int bestDirection = -1;
        for (int row = 0; row < 6; row++) {
            for (int column = 0; column < 7; column++) {
                iterations++;
                if (floodFilledBoard[row][column] == 'X' && isTouchingOpenSpace(floodFilledBoard, row, column)) {
                    if (canBePushed(floodFilledBoard, row, column, 'u')) {
                        int solution = findSolution(push(board, row, column, 'u'), depth + 1, add(newRoute, new int[]{row, column, 1}));
                        if (solution < solutionLength) {
                            solutionLength = solution;
                            bestRow = row;
                            bestColumn = column;
                            bestDirection = 1;
                        }
                    }
                    if (canBePushed(floodFilledBoard, row, column, 'd')) {
                        int solution = findSolution(push(board, row, column, 'd'), depth + 1, add(newRoute, new int[]{row, column, 2}));
                        if (solution < solutionLength) {
                            solutionLength = solution;
                            bestRow = row;
                            bestColumn = column;
                            bestDirection = 2;
                        }
                    }
                    if (canBePushed(floodFilledBoard, row, column, 'l')) {
                        int solution = findSolution(push(board, row, column, 'l'), depth + 1, add(newRoute, new int[]{row, column, 3}));
                        if (solution < solutionLength) {
                            solutionLength = solution;
                            bestRow = row;
                            bestColumn = column;
                            bestDirection = 3;
                        }
                    }
                    if (canBePushed(floodFilledBoard, row, column, 'r')) {
                        int solution = findSolution(push(board, row, column, 'r'), depth + 1, add(newRoute, new int[]{row, column, 4}));
                        if (solution < solutionLength) {
                            solutionLength = solution;
                            bestRow = row;
                            bestColumn = column;
                            bestDirection = 4;
                        }
                    }
                }
            }
        }

        if (bestRow != -1) {
            newRoute.add(0, new int[]{bestRow, bestColumn, bestDirection});
        }
        if (solutionLength < fastestSolution) {
            fastestSolution = solutionLength;
            newRoute.add(newRoute.remove(0)); // dont know why the last one goes first but this fixes it
            BoulderSolver.route = new ArrayList<>(newRoute);
        }
        return solutionLength;
    }

    public static boolean canBePushed(char[][] floodFilledBoard, int row, int column, char direction) {
        switch (direction) {
            case 'u':
                if (row > 0 && row < 5 && floodFilledBoard[row - 1][column] != 'X' && floodFilledBoard[row + 1][column] == 'O') return true;
                break;
            case 'd':
                if (row > 0 && row < 5 && floodFilledBoard[row - 1][column] == 'O' && floodFilledBoard[row + 1][column] != 'X') return true;
                break;
            case 'l':
                if (column > 0 && column < 6 && floodFilledBoard[row][column - 1] != 'X' && floodFilledBoard[row][column + 1] == 'O') return true;
                break;
            case 'r':
                if (column > 0 && column < 6 && floodFilledBoard[row][column - 1] == 'O' && floodFilledBoard[row][column + 1] != 'X') return true;
                break;
        }
        return false;
    }

    public static char[][] push(char[][] board, int row, int column, char direction) {
        char[][] newBoard = copy(board);
        switch (direction) {
            case 'u':
                newBoard[row - 1][column] = 'X';
                break;
            case 'd':
                newBoard[row + 1][column] = 'X';
                break;
            case 'l':
                newBoard[row][column - 1] = 'X';
                break;
            case 'r':
                newBoard[row][column + 1] = 'X';
        }
        newBoard[row][column] = '\0';
        return newBoard;
    }

    public static boolean isTouchingOpenSpace(char[][] floodFilledBoard, int row, int column) {
        return (row > 0 && floodFilledBoard[row - 1][column] == 'O') ||
                (row < 5 && floodFilledBoard[row + 1][column] == 'O') ||
                (column > 0 && floodFilledBoard[row][column - 1] == 'O') ||
                (column < 6 && floodFilledBoard[row][column + 1] == 'O');
    }

    public static boolean hasOpenPath(char[][] board) {
        char[][] newBoard = floodFillBottom(copy(board));
        // Check if flood fill reached top
        for (int column = 0; column < 7; column++) {
            if (newBoard[0][column] == 'O') return true;
        }
        return false;
    }

    public static char[][] floodFillBottom(char[][] board) {
        char[][] newBoard = copy(board);
        for (int column = 0; column < 7; column++) {
            if (newBoard[5][column] == '\0') {
                newBoard = floodFill(newBoard, 5, column);
            }
        }
        return newBoard;
    }

    public static char[][] floodFill(char[][] board, int row, int column) {
        if (row < 0 || row > 5 || column < 0 || column > 6) return board;
        if (board[row][column] == '\0') {
            board[row][column] = 'O';
            floodFill(board, row - 1, column);
            floodFill(board, row + 1, column);
            floodFill(board, row, column - 1);
            floodFill(board, row, column + 1);
            return board;
        }
        return board;
    }

    public static List<int[]> add(List<int[]> list, int[] arrayToAdd) {
        List<int[]> newList = new ArrayList<>(list);
        newList.add(arrayToAdd);
        return newList;
    }

    public static AxisAlignedBB getBoulder(int row, int column, BlockPos chestLocation, String boulderRoomDirection) {
        BlockPos boulderPosition;
        switch (boulderRoomDirection) {
            case "north":
                boulderPosition = chestLocation.add(3 * column - 10, -2, 3 * row + 4);
                break;
            case "east":
                boulderPosition = chestLocation.add(-3 * row - 6, -2, 3 * column - 10);
                break;
            case "south":
                boulderPosition = chestLocation.add(-3 * column + 8, -2, -3 * row - 6);
                break;
            case "west":
                boulderPosition = chestLocation.add(3 * row + 4, -2, -3 * column + 8);
                break;
            default:
                return null;
        }
        return new AxisAlignedBB(boulderPosition.getX() - 0.01, boulderPosition.getY() - 0.01, boulderPosition.getZ() - 0.01, boulderPosition.getX() + 3.01, boulderPosition.getY() + 3.01, boulderPosition.getZ() + 3.01);
    }

    public static void drawArrow(AxisAlignedBB aabb, String boulderRoomDirection, char direction, int colourInt, float partialTicks) {
        double averageX = (aabb.minX + aabb.maxX) / 2;
        double thirtyPercent = (aabb.maxX - aabb.minX) * 0.3;
        double averageZ = (aabb.minZ + aabb.maxZ) / 2;
        if (((boulderRoomDirection.equals("north") || boulderRoomDirection.equals("south")) && (direction == 'u' || direction == 'd')) ||
            ((boulderRoomDirection.equals("east") || boulderRoomDirection.equals("west")) && (direction == 'l' || direction == 'r'))) {
            Utils.draw3DLine(new Vec3(averageX, aabb.minY, aabb.minZ), new Vec3(averageX, aabb.minY, aabb.maxZ), colourInt, 10, false, partialTicks);
        } else {
            Utils.draw3DLine(new Vec3(aabb.minX, aabb.minY, averageZ), new Vec3(aabb.maxX, aabb.minY, averageZ), colourInt, 10, false, partialTicks);
        }

        if ((boulderRoomDirection.equals("north") && direction == 'u') || (boulderRoomDirection.equals("south") && direction == 'd') ||
            (boulderRoomDirection.equals("east") && direction == 'l') || (boulderRoomDirection.equals("west") && direction == 'r')) {
            Utils.draw3DLine(new Vec3(averageX, aabb.minY, aabb.minZ), new Vec3(aabb.minX + thirtyPercent, aabb.minY, aabb.minZ + thirtyPercent), colourInt, 10, false, partialTicks);
            Utils.draw3DLine(new Vec3(averageX, aabb.minY, aabb.minZ), new Vec3(aabb.maxX - thirtyPercent, aabb.minY, aabb.minZ + thirtyPercent), colourInt, 10, false, partialTicks);
        } else if ((boulderRoomDirection.equals("north") && direction == 'd') || (boulderRoomDirection.equals("south") && direction == 'u') ||
                    (boulderRoomDirection.equals("east") && direction == 'r') || (boulderRoomDirection.equals("west") && direction == 'l')) {
            Utils.draw3DLine(new Vec3(averageX, aabb.minY, aabb.maxZ), new Vec3(aabb.minX + thirtyPercent, aabb.minY, aabb.maxZ - thirtyPercent), colourInt, 10, false, partialTicks);
            Utils.draw3DLine(new Vec3(averageX, aabb.minY, aabb.maxZ), new Vec3(aabb.maxX - thirtyPercent, aabb.minY, aabb.maxZ - thirtyPercent), colourInt, 10, false, partialTicks);
        } else if ((boulderRoomDirection.equals("north") && direction == 'l') || (boulderRoomDirection.equals("south") && direction == 'r') ||
                    (boulderRoomDirection.equals("east") && direction == 'd') || (boulderRoomDirection.equals("west") && direction == 'u')) {
            Utils.draw3DLine(new Vec3(aabb.minX, aabb.minY, averageZ), new Vec3(aabb.minX + thirtyPercent, aabb.minY, aabb.minZ + thirtyPercent), colourInt, 10, false, partialTicks);
            Utils.draw3DLine(new Vec3(aabb.minX, aabb.minY, averageZ), new Vec3(aabb.minX + thirtyPercent, aabb.minY, aabb.maxZ - thirtyPercent), colourInt, 10, false, partialTicks);
        } else {
            Utils.draw3DLine(new Vec3(aabb.maxX, aabb.minY, averageZ), new Vec3(aabb.maxX - thirtyPercent, aabb.minY, aabb.minZ + thirtyPercent), colourInt, 10, false, partialTicks);
            Utils.draw3DLine(new Vec3(aabb.maxX, aabb.minY, averageZ), new Vec3(aabb.maxX - thirtyPercent, aabb.minY, aabb.maxZ - thirtyPercent), colourInt, 10, false, partialTicks);
        }
    }

}
