package tictactoepackage;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class BoardInventory {
    private Board board;
    private char currentPlayer;
    private char computerPlayer;

    public BoardInventory(int size) {
        board = new Board(size);
        currentPlayer = 'X';
        computerPlayer = 'O';
    }
    public void playGame() {
        Scanner sc = new Scanner(System.in);
        Random rand = new Random();

        while (true) {
            board.printBoard();

            if (currentPlayer == 'X') {
                int row, col;
                boolean validMove = false;
                do {
                    try {
                        System.out.println("Enter row and col between (1-" + board.getSize() + "): ");
                        row = sc.nextInt() - 1;
                        col = sc.nextInt() - 1;

                        validMove = makeMove(row, col, currentPlayer);
                        if (!validMove) {
                            System.out.println("Invalid move. Please try again.");
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input. Please enter numbers.");
                        sc.nextLine(); // Consume invalid input
                    }
                } while (!validMove);

            } else {
                int emptySquares = countEmptySquares();
                if (emptySquares > 0) {
                    int randomTurn = rand.nextInt(emptySquares);
                    playComputerMove(randomTurn);
                }
            }

            if (checkWin()) {
                board.printBoard();
                System.out.println(currentPlayer + " wins!!");
                break;
            } else if (countOccupiedSquares() == board.getSize() * board.getSize()) {
                board.printBoard();
                System.out.println("It's a draw");
                break;
            }

            currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
        }
        sc.close();
    }

    private boolean makeMove(int row, int col, char player) {
        try {
            Square square = board.getSquare(row, col);
            if (square != null && square.getValue() == '-') {
                square.setValue(player);
                return true;
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Out of bounds. Please enter valid row and col values.");
        }
        return false;
    }



    private void playComputerMove(int randomTurn) {
        int emptySquareCount = 0;
        for (Square square : board.getAllSquares()) {
            if (square.getValue() == '-') {
                if (emptySquareCount == randomTurn) {
                    square.setValue(computerPlayer);
                    break;
                }
                emptySquareCount++;
            }
        }
    }

    private int countEmptySquares() {
        int count = 0;
        for (Square square : board.getAllSquares()) {
            if (square.getValue() == '-') {
                count++;
            }
        }
        return count;
    }

    private int countOccupiedSquares() {
        int count = 0;
        for (Square square : board.getAllSquares()) {
            if (square.getValue() != '-') {
                count++;
            }
        }
        return count;
    }

    private boolean checkWin() {
        char[][] boardArray = new char[board.getSize()][board.getSize()];
        for (Square square : board.getAllSquares()) {
            boardArray[square.getRow()][square.getCol()] = square.getValue();
        }

        // Check rows and columns for a win
        for (int i = 0; i < board.getSize(); i++) {
            if (boardArray[i][0] == currentPlayer && boardArray[i][1] == currentPlayer && boardArray[i][2] == currentPlayer) {
                return true;
            }
            if (boardArray[0][i] == currentPlayer && boardArray[1][i] == currentPlayer && boardArray[2][i] == currentPlayer) {
                return true;
            }
        }

        // Check for diagonal elements
        if (boardArray[0][0] == currentPlayer && boardArray[1][1] == currentPlayer && boardArray[2][2] == currentPlayer) {
            return true;
        }
        if (boardArray[0][2] == currentPlayer && boardArray[1][1] == currentPlayer && boardArray[2][0] == currentPlayer) {
            return true;
        }

        return false;
    }
}

