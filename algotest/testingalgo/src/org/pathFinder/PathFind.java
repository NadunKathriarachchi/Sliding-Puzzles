package org.pathFinder;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class PathFind {
    private int rowCount;
    private int columnCount;
    private char[][] gridMap;
    private int startRowNib;
    private int endRowNib;
    private int beginColumnNib;
    private int endColumnNib;
    private final int[][] nibPath = {{0, -1}, {0, 1}, {-1, 0}, {1, 0}};

    public PathFind(String filePath) {
        try {
            if (!filePath.equals("")) {
                loadFile(filePath);
                FigureStartAndEnd();
                readMap();
                List<int[]> shortestPath = findingShortPath();
                if (shortestPath != null && !shortestPath.isEmpty()) {
                    showShortPath(shortestPath);
                    System.out.println();
                } else {
                    System.out.println("\nNo Path Found! Trying alternate path.\n");
                    findPossiblePath();
                }
            }
        } catch (IOException e) {
            System.out.println("\nThe File Not Exist!" + e);
        }
    }

    public void loadFile(String filePath) throws IOException {
        List<String> mapLines = new ArrayList<>();
        File file = new File(filePath);
        Scanner scanner = new Scanner(file);
        while (scanner.hasNext()) {
            mapLines.add(scanner.nextLine());
        }
        this.rowCount = mapLines.size();
        this.columnCount = mapLines.get(0).length();
        this.gridMap = new char[rowCount][columnCount];
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                gridMap[i][j] = mapLines.get(i).charAt(j);
            }
        }
    }

    public void readMap() {
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                System.out.print(gridMap[i][j]); // Show Elments in Map
            }
            System.out.println();
        }
    }

    private void FigureStartAndEnd() {
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                if (gridMap[i][j] == 'S') {
                    this.startRowNib = i;
                    this.beginColumnNib = j;
                } else if (gridMap[i][j] == 'F') {
                    this.endRowNib = i;
                    this.endColumnNib = j;
                }
            }
        }
        System.out.println("\nSuccessfully Load Files Into the Game.\n");
    }

    private List<int[]> constructPath(int[][] parent) {
        List<int[]> path = new ArrayList<>();
        int currentRow = endRowNib;
        int currentCol = endColumnNib;
        while (currentRow != startRowNib || currentCol != beginColumnNib) {
            path.add(0, new int[]{currentRow, currentCol});
            int index = parent[currentRow][currentCol];
            currentRow = index / columnCount;
            currentCol = index % columnCount;
        }
        path.add(0, new int[]{startRowNib, beginColumnNib});
        return path;
    }

    public List<int[]> findingShortPath() {
        boolean[][] visited = new boolean[rowCount][columnCount];
        int[][] parent = new int[rowCount][columnCount];
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{startRowNib, beginColumnNib});
        visited[startRowNib][beginColumnNib] = true;
        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int row = current[0];
            int col = current[1];
            if (row == endRowNib && col == endColumnNib) {
                return constructPath(parent);
            }
            for (int[] direction : nibPath) {
                int newRow = row;
                int newCol = col;
                while (true) {
                    newRow += direction[0];
                    newCol += direction[1];
                    if (!isValidUnit(newRow, newCol) || gridMap[newRow][newCol] == '0') {
                        break;
                    }
                    if (gridMap[newRow][newCol] == 'F') {
                        parent[newRow][newCol] = row * columnCount + col;
                        return constructPath(parent);
                    }
                }
                newRow -= direction[0];
                newCol -= direction[1];
                if (!visited[newRow][newCol]) {
                    queue.offer(new int[]{newRow, newCol});
                    visited[newRow][newCol] = true;
                    parent[newRow][newCol] = row * columnCount + col;
                }
            }
        }
        return new ArrayList<>();
    }

    public void showShortPath(List<int[]> path) {
        System.out.println("\nShortest Path Given Below:\n");
        System.out.println("1. Starting at (" + (beginColumnNib + 1) + "," + (startRowNib + 1) + ")");
        int stepCounter = 0;
        for (int i = 1; i < path.size(); i++) {
            int[] currentCell = path.get(i);
            int[] previousCell = path.get(i - 1);
            int changeOfRow = currentCell[0] - previousCell[0];
            int changeOfCol = currentCell[1] - previousCell[1];
            String moveDescription = "";
            if (changeOfCol == 0 && changeOfRow < 0) {
                moveDescription = "Move up to ";
            } else if (changeOfRow > 0 && changeOfCol == 0) {
                moveDescription = "Move down to ";
            } else if (changeOfRow == 0 && changeOfCol > 0) {
                moveDescription = "Move right to ";
            } else if (changeOfRow == 0 && changeOfCol < 0) {
                moveDescription = "Move left to ";
            }
            System.out.println((i + 1) + ". " + moveDescription + "(" + (currentCell[1] + 1) + "," + (currentCell[0] + 1) + ")");
            stepCounter = i;
        }
        System.out.println((stepCounter + 2) + ". Done!!");
        System.out.println("\nPath Found " + (stepCounter + 2) + " Steps.");
    }

    private boolean isValidUnit(int row, int col) {
        return row >= 0 && row < rowCount && col >= 0 && col < columnCount;
    }

    public void findPossiblePath() {
        System.out.println("Alternative Paths Not Implemented! Review Map and Algorithm.");
    }
}

