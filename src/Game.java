import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Game {
    private Board board; // the game board
    private int numGenerations; // the number of generations to simulate
    private ExecutorService executor;

    public Game(Board board, int numGenerations) {
        this.board = board;
        this.numGenerations = numGenerations;
        // Use a fixed thread pool with the same number of threads as cells on the board
        this.executor = Executors.newFixedThreadPool(board.getNumRows() * board.getNumColumns());
    }

    public void startGame() {
        for (int i = 0; i < numGenerations; i++) {
            Board newBoard = new Board(board.getNumRows(), board.getNumColumns());
            CountDownLatch latch = new CountDownLatch(board.getNumRows() * board.getNumColumns());

            for (int j = 0; j < newBoard.getNumRows(); j++) {
                for (int k = 0; k < newBoard.getNumColumns(); k++) {
                    final int row = j;
                    final int col = k;
                    executor.submit(() -> {
                        Cell cell = board.getCell(row, col);
                        int numAliveNeighbors = board.countAliveNeighbors(row, col);
                        // Update the cell based on the number of alive neighbors
                        cell.Update(numAliveNeighbors);
                        newBoard.setCell(row, col, cell.isAlive());
                        latch.countDown();
                    });
                }
            }

            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            board = newBoard;
            printBoard(i + 1); // print the updated board state with the generation counter
        }
        executor.shutdown(); // Shutdown the executor after the loop
    }


    private boolean applyRules(Cell cell, int numAliveNeighbors) {
        if (cell.isAlive()) {
            // An alive cell with 2 or 3 alive neighbors stays alive; otherwise, it dies.
            return numAliveNeighbors == 2 || numAliveNeighbors == 3;
        } else {
            // A dead cell with exactly 3 alive neighbors comes to life.
            return numAliveNeighbors == 3;
        }
    }

    public void printBoard(int generation) {
        System.out.println("Generation " + generation + ":");
        for (int i = 0; i < board.getNumRows(); i++) {
            for (int j = 0; j < board.getNumColumns(); j++) {
                System.out.print(board.getCell(i, j).isAlive() ? "X" : ".");
            }
            System.out.println();
        }
        System.out.println();
    }
}
