public class GameOfLife {
    public static void main(String[] args) {
        String inputFile = "initial_state.txt";
        int numRows = 20;
        int numColumns = 20;
        int numGenerations = 15;
        System.out.println("Welcome to Conway's Game of Life!");

        Board board = new Board(numRows, numColumns);
        board.readFromFile(inputFile);

        System.out.println("Initial configuration: ");
        board.printBoard();

        Game game = new Game(board, numGenerations);
        game.startGame();
    }
}
