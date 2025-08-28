import java.util.Random;
import java.util.Scanner;

public class GameOfLife {

    private static final int SIZE = 5;
    private static final char LIVE_CELL = '*';
    private static final char DEAD_CELL = '.';
    private char[][] grid = new char[SIZE][SIZE];

    public GameOfLife() {
        initializeGrid();
    }

    // Randomly initialize the grid with live or dead cells
    private void initializeGrid() {
        Random random = new Random();
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                grid[i][j] = random.nextBoolean() ? LIVE_CELL : DEAD_CELL;
            }
        }
    }

    // Display grid's current state
    private void displayGrid() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                System.out.print(grid[i][j] + " "); // Print the cell's value followed by a space'
            }
            System.out.println(); // Move to the next line after printing the row
        }
        System.out.println(); // Move to the next line after printing the grid (Space between generations)
    }

    // Count live neighbors for a given cell
    private int countLiveNeighbors(int x, int y) {
        int liveCount = 0; // Initialize a counter for live neighbors

        // Loop through the 3x3 grid around the cell (x, y)
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {

                // Skip the cell itself
                if (i == 0 && j == 0) {
                    continue; // Move to the next iteration
                }

                // Calculate the neighbor's coordinates
                int neighborX = x + i; // Row index of the neighbor
                int neighborY = y + j; // Column index of the neighbor

                // Check if the neighbor is within bounds and is alive
                if (isInBounds(neighborX, neighborY) && grid[neighborX][neighborY] == LIVE_CELL) {
                    liveCount++; // Increment the count of live neighbors
                }
            }
        }
        return liveCount; // Return the total count of live neighbors
    }

    // Check if the coordinates are within the grid bounds
    private boolean isInBounds(int x, int y) {
        // Check if the coordinates (x, y) are within the grid bounds
        return x >= 0 && x < SIZE && y >= 0 && y < SIZE; // Return true if within bounds, false otherwise
    }


    // Check if two grids are equal
    private boolean areGridsEqual(char[][] grid1, char[][] grid2) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (grid1[i][j] != grid2[i][j]) {
                    return false; // Grids are not equal
                }
            }
        }
        return true; // Grids are equal
    }

    private void updateGrid() {
        char[][] newGrid = new char[SIZE][SIZE]; // Create a new grid to store the updated state

        // Iterate through each cell in the current grid
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                // Count the number of live neighbors for the current cell
                int liveNeighbors = countLiveNeighbors(i, j);

                // Apply the rules of the Game of Life
                if (grid[i][j] == LIVE_CELL) { // If the current cell is alive
                    // Remain alive if it has 2 or 3 live neighbors
                    newGrid[i][j] = (liveNeighbors == 2 || liveNeighbors == 3) ? LIVE_CELL : DEAD_CELL;
                } else { // If the current cell is dead
                    // Become alive if it has exactly 3 live neighbors
                    newGrid[i][j] = (liveNeighbors == 3) ? LIVE_CELL : DEAD_CELL;
                }
            }
        }

        // Check for stalemate: if the new grid is the same as the current grid
        if (areGridsEqual(grid, newGrid)) {
            System.out.println("= Oops!! Stalemate reached! = \n= Exiting... =");
            System.exit(0); // Exit the program
        }

        grid = newGrid; // Update the grid reference to the new grid with the updated state
    }


    public static void main(String[] args) {
        GameOfLife game = new GameOfLife();
        Scanner scanner = new Scanner(System.in);
        String command;

        // Display initial grid
        System.out.println("===================================");
        System.out.println("= \uD83D\uDC83\uD83D\uDD7A\uD83D\uDC83\uD83D\uDD7A Game Of Life \uD83D\uDC83\uD83D\uDD7A\uD83D\uDC83\uD83D\uDD7A =");
        System.out.println("===================================");
        System.out.println("Initial Grid:");
        game.displayGrid();

        do {
            System.out.print("Press Enter to continue to the next generation (type 'exit' to quit): ");
            command = scanner.nextLine();
            if (!command.equalsIgnoreCase("exit")) {
                game.updateGrid();
                game.displayGrid();
            }
        } while (!command.equalsIgnoreCase("exit"));

        System.out.println("= Exiting... =");
        scanner.close();
    }
}