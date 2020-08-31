import java.util.Scanner;

public class PegSolitaireGame {

    public static void main(String[] args)
    {
        System.out.println("\nWELCOME TO CS300 PEG SOLITAIRE!");
        System.out.println("===============================\n");
        System.out.println("Board Style Menu\n1) Cross\n2) Circle\n3) Triangle\n4) Simple T\n");
        Scanner sc = new Scanner(System.in);
        int board_type = readValidInt(sc, "Choose a board style: ", 1, 4);
        char[][] board = createBoard(board_type);
        while (true) {
            displayBoard(board != null ? board : new char[0][]);
            if (countPegsRemaining(board) == 1) {
                System.out.println("Congrats, you won!");
                break;
            } else if (countMovesAvailable(board) == 0) {
                System.out.println("It looks like there are no more legal moves.  Please try again.");
                break;
            } else {
                int[] move = readValidMove(sc, board);
                int column = move[0];
                int row = move[1];
                int direction = move[2];
                board = performMove(board, row, column, direction);
            }
        }
        System.out.println("\n==========================================");
        System.out.println("THANK YOU FOR PLAYING CS300 PEG SOLITAIRE!");
    }


    public static int readValidInt(Scanner in, String prompt, int min, int max)
    {
        System.out.print(prompt);
        String input = in.nextLine();
        try {
            int userInput = Integer.parseInt(input);
            if (userInput < min || userInput > max) {
                return readValidInt(in, String.format("Please enter your choice as an integer between %d and %d: ", min, max), min, max);
            } else {
                return userInput;
            }
        }
        catch (NumberFormatException ex){
            return readValidInt(in, String.format("Please enter your choice as an integer between %d and %d: ", min, max), min, max);
        }
    }


    public static char[][] createBoard(int boardType)
    {
        switch (boardType) {
            case 1:
                return new char[][] {
                        { '#', '#', '#', '@', '@', '@', '#', '#', '#' },
                        { '#', '#', '#', '@', '@', '@', '#', '#', '#' },
                        { '@', '@', '@', '@', '@', '@', '@', '@', '@' },
                        { '@', '@', '@', '@', '-', '@', '@', '@', '@' },
                        { '@', '@', '@', '@', '@', '@', '@', '@', '@' },
                        { '#', '#', '#', '@', '@', '@', '#', '#', '#' },
                        { '#', '#', '#', '@', '@', '@', '#', '#', '#' }
                };
            case 2:
                return new char[][] {
                        { '#', '-', '@', '@', '-', '#' },
                        { '-', '@', '@', '@', '@', '-' },
                        { '@', '@', '@', '@', '@', '@' },
                        { '@', '@', '@', '@', '@', '@' },
                        { '-', '@', '@', '@', '@', '-' },
                        { '#', '-', '@', '@', '-', '#' }
                };
            case 3:
                return new char[][] {
                        { '#', '#', '#', '-', '@', '-', '#', '#', '#' },
                        { '#', '#', '-', '@', '@', '@', '-', '#', '#' },
                        { '#', '-', '@', '@', '-', '@', '@', '-', '#' },
                        { '-', '@', '@', '@', '@', '@', '@', '@', '-' }
                };
            case 4:
                return new char[][] {
                        { '-', '-', '-', '-', '-' },
                        { '-', '@', '@', '@', '-' },
                        { '-', '-', '@', '-', '-' },
                        { '-', '-', '@', '-', '-'},
                        { '-', '-', '-', '-', '-' }
                };
            default:
                return null;
        }
    }


    public static void displayBoard(char[][] board)
    {
        System.out.print("\n  ");
        for (int i = 1; i < board[0].length + 1; i++) {
            System.out.print(String.format("%d ", i));
        }
        System.out.println();
        for (int i = 0; i < board.length; i++) {
            System.out.print(String.format("%d ", i+1));
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(String.format("%s ", board[i][j]));
            }
            System.out.println();
        }
    }


    public static int[] readValidMove(Scanner in, char[][] board)
    {
        int column = readValidInt(in, "Choose the COLUMN of a peg you'd like to move: ", 1, board[0].length);
        int row = readValidInt(in, "Choose the ROW of a peg you'd like to move: ", 1, board.length);
        int direction = readValidInt(in, "Choose a DIRECTION to move that peg 1) UP, 2) DOWN, 3) LEFT, or 4) RIGHT:", 1, 4);
        if (isValidMove(board, row, column, direction)) {
            return new int[] {column, row, direction};
        } else {
            String directionString;
            switch (direction) {
                case 1:
                    directionString = "UP";
                    break;
                case 2:
                    directionString = "DOWN";
                    break;
                case 3:
                    directionString = "LEFT";
                    break;
                case 4:
                    directionString = "RIGHT";
                    break;
                default:
                    directionString = "";
            }
            System.out.println(String.format("Moving a peg from row %d and column %d %s is not currently a legal move.\n", row, column, directionString));
            return readValidMove(in, board);
        }
    }


    public static boolean isValidMove(char[][] board, int row, int column, int direction)
    {
        boolean condition1 = board[row - 1][column - 1] == '@';
        boolean condition2;
        boolean condition3;
        switch (direction) {
            case 1:
                if (row <= 2) {
                    condition2 = false;
                    condition3 = false;
                } else {
                    condition2 = board[row - 2][column - 1] == '@';
                    condition3 = board[row - 3][column - 1] == '-';
                }
                break;
            case 2:
                if (row >= board.length - 1) {
                    condition2 = false;
                    condition3 = false;
                } else {
                    condition2 = board[row][column - 1] == '@';
                    condition3 = board[row + 1][column - 1] == '-';
                }
                break;
            case 3:
                if (column <= 2) {
                    condition2 = false;
                    condition3 = false;
                } else {
                    condition2 = board[row - 1][column - 2] == '@';
                    condition3 = board[row - 1][column - 3] == '-';
                }
                break;
            case 4:
                if (column >= board[0].length - 1) {
                    condition2 = false;
                    condition3 = false;
                } else {
                    condition2 = board[row - 1][column] == '@';
                    condition3 = board[row - 1][column + 1] == '-';
                }
                break;
            default:
                return false;
        }
        return condition1 && condition2 && condition3;
    }


    public static char[][] performMove(char[][] board, int row, int column, int direction)
    {
        if (isValidMove(board, row, column, direction)) {
            board[row - 1][column - 1] = '-';
            switch (direction) {
                case 1:
                    board[row - 2][column - 1] = '-';
                    board[row - 3][column - 1] = '@';
                    break;
                case 2:
                    board[row][column - 1] = '-';
                    board[row + 1][column - 1] = '@';
                    break;
                case 3:
                    board[row - 1][column - 2] = '-';
                    board[row - 1][column - 3] = '@';
                    break;
                case 4:
                    board[row - 1][column] = '-';
                    board[row - 1][column + 1] = '@';
                default:
                    return board;
            }
        }
        else {
            return board;
        }
        return board;
    }


    public static int countPegsRemaining(char[][] board)
    {
        int cnt = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == '@') {
                    cnt ++;
                }
            }
        }
        return cnt;
    }


    public static int countMovesAvailable(char[][] board)
    {
        int cnt = 0;
        for (int i = 1; i < board.length + 1; i++) {
            for (int j = 1; j < board[0].length + 1; j++) {
                for (int k = 1; k < 5; k++) {
                    if (isValidMove(board, i, j, k)) {
                        cnt ++;
                    }
                }
            }
        }
        return cnt;
    }
}
