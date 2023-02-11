import java.util.Scanner;

/**
 * @author: ______ONG JUN KYE (21201749)_________
 *
 *          For the instruction of the assignment please refer to the assignment
 *          GitHub.
 *
 *          Plagiarism is a serious offense and can be easily detected. Please
 *          don't share your code to your classmate even if they are threatening
 *          you with your friendship. If they don't have the ability to work on
 *          something that can compile, they would not be able to change your
 *          code to a state that we can't detect the act of plagiarism. For the
 *          first commit of plagiarism, regardless you shared your code or
 *          copied code from others, you will receive 0 with an addition of 5
 *          mark penalty. If you commit plagiarism twice, your case will be
 *          presented in the exam board and you will receive a F directly.
 *
 *          If you cannot work out the logic of the assignment, simply contact
 *          us on Piazza. The teaching team is more the eager to provide
 *          you help. We can extend your submission due if it is really
 *          necessary. Just please, don't give up.
 */
public class Connect4 {

    /**
     * Total number of rows of the game board. Use this constant whenever possible.
     */
    public static final int HEIGHT = 6;
    /**
     * Total number of columns of the game board. Use this constant whenever
     * possible.
     */
    public static final int WIDTH = 8;

    /**
     * Your main program. You don't need to change this part. This has been done for
     * you.
     */
    public static void main(String[] args) {
        new Connect4().runOnce();
    }

    /**
     * Your program entry. There are two lines missing. Please complete the line
     * labeled with TODO. You can, however, write more than two lines to complete
     * the logic required by TODO. You are not supposed to modify any part other
     * than the TODOs.
     */
    void runOnce() {
        // For people who are not familiar with constants - HEIGHT and WIDTH are two
        // constants defined above. These two constants are visible in the entire
        // program. They cannot be further modified, i.e., it is impossible to write
        // HEIGHT = HEIGHT + 1; or WIDTH = 0; anywhere in your code. However, you can
        // use
        // these two constants as a reference, i.e., row = HEIGHT - 1, for example.

        int[][] board = new int[HEIGHT][WIDTH];
        char[] symbols = { '1', '2' };
        int player = 1;
        printBoard(board, symbols);

        Scanner scanner = new Scanner(System.in);
        boolean quit = false;
        while (!isGameOver(board) && !quit) {
            System.out.println("Player " + player + ", please enter a command. Press 'h' for help");
            char s = scanner.next().charAt(0);
            switch (s) {
                case 'h':
                case 'H':
                    printHelpMenu();
                    break;
                case 'c':
                case 'C':
                    changeSymbol(player, symbols);
                    break;
                case 'q':
                case 'Q':
                    quit = true;
                    System.out.println("Bye~");
                    continue;
                case 'r':
                    restart(board);
                    printBoard(board, symbols);
                    continue;
                default:
                    if (!validate(s, board)) {
                        System.out.println("Wrong input!, please do again");
                        continue;
                    }

                    // convert the char 's' to the integer 'column', with the value 0 to 7
                    int column = Integer.parseInt(String.valueOf(s));

                    fillBoard(board, column, player);
                    printBoard(board, symbols);
                    if (isGameOver(board)) {
                        System.out.println("Player " + player + ", you win!");
                        break;
                    } else if (checkMate(player, board))
                        System.out.println("Check mate!");
                    else if (check(player, board))
                        System.out.println("Check!");

                    // After each iteration, change the variable "player" alternatively
                    // between the integers 1 and 2.
                    if(player == 1){
                        player = 2;
                    } else
                        player = 1;

            } // end switch
        } // end while
    }

    /**
     * Reset the board to the initial state
     *
     * @param board - the game board array
     */
    void restart(int[][] board) {
        for (int i = 0; i < board.length; i++){
            for (int j = 0; j < board[i].length; j++)
                board[i][j] = 0;
        }

    }

    /**
     * It allows a player to choose a new symbol to represents its chess.
     * This method should ask the player to enter a new symbol so that symbol is not
     * the same as its opponent.
     * Otherwise the player will need to enter it again until they are different.
     *
     * @param player  - the player who is about to change its symbol
     * @param symbols - the symbols array storing the players' symbols.
     */
    void changeSymbol(int player, char[] symbols) {
        boolean sym = false;
        while (!sym) {
            Scanner in = new Scanner(System.in);
            System.out.println("Enter the new symbol:");

            //To check whether the symbol entered is the same as opponent,
            char checkSym = in.next().charAt(0);
            if (player == 1) {
                if (checkSym != symbols[1]) {
                    symbols[0] = checkSym;
                    sym = true;
                }
                else
                    continue;
            } else if (player == 2) {
                if (checkSym != symbols[0]) {
                    symbols[1] = checkSym;
                    sym = true;
                } else
                    continue;
            }
        }
    }

    /**
     * This method returns true if the player "player" plays immediately, he/she may
     * end the game. This warns the other player to
     * place his/her next block in a correct position.
     *
     *
     * @param player - the player who is about to win if the other player does not
     *               stop him
     * @param board  - the 2D array of the game board.
     * @return true if the player is about to win, false if the player is not.
     */
    boolean check(int player, int[][] board) {
        //Horizontal check
        for(int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH - 3; j++) {
                //Needs to have a base for check
                if (i < 5){
                    //Checks whether there is a base for checking so there is something to place on top of it
                    if (board[i+1][j]!=0 && board[i+1][j+1] != 0 && board[i+1][j+2] != 0 && board[i+1][j+3] != 0) {
                        //3 in a row
                        if (board[i][j+1] == player && board[i][j+1] == board[i][j+2] && board[i][j+1] == board[i][j+3]
                                && board[i][j] == 0)
                            return true;
                        if (board[i][j] == player && board[i][j] == board[i][j+1] && board[i][j] == board[i][j+2]
                                && board[i][j+3] == 0)
                            return true;
                        //2 in a row and 1 separated
                        if (board[i][j] != 0 && board[i][j] == board[i][j+1] && board[i][j] == board[i][j+3]
                                && (board[i][j+2] == 0))
                            return true;
                        if (board[i][j] != 0 && board[i][j] == board[i][j+2] && board[i][j] == board[i][j+3]
                                && (board[i][j+1] == 0))
                            return true;
                    }
                }
                //At the last row, so no need to have a bottom below the checking
                if (i == 5) {
                    //3 in a row
                    if (board[i][j + 1] == player && board[i][j + 1] == board[i][j + 2] && board[i][j + 1] == board[i][j + 3]
                            && board[i][j] == 0)
                        return true;
                    if (board[i][j] == player && board[i][j] == board[i][j + 1] && board[i][j] == board[i][j + 2]
                            && board[i][j + 3] == 0)
                        return true;
                    //2 in a row and 1 separated
                    if (board[i][j] != 0 && board[i][j] == board[i][j + 1] && board[i][j] == board[i][j + 3]
                            && (board[i][j + 2] == 0))
                        return true;
                    if (board[i][j] != 0 && board[i][j] == board[i][j + 2] && board[i][j] == board[i][j + 3]
                            && (board[i][j + 1] == 0))
                        return true;
                }
            }
        }

        //Vertical Check
        for (int i = 0; i < HEIGHT-3; i++) {
            for (int j = 0; j < WIDTH; j++) {
                if (board[i + 3][j] == player && board[i + 3][j] == board[i + 2][j]
                        && board[i + 3][j] == board[i + 1][j] && board[i][j] == 0)
                    return true;
            }
        }

        //Upward Diagonal Check from bottom left to top right
        // i will go from 3 to 5 (3 rows) & j will go from 0 to 4 (5 cols)
        // i start from 3 because that is the smallest row it can start checking
        for (int i = 3; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH-3; j++) {
                //All checking slots need bases
                if (i < 5) {
                    //i = 3 or 4 (row)
                    //Cases for 3 in a row
                    if (board[i][j] == player && board[i - 3][j + 3] == 0 && board[i - 2][j + 3] != 0
                            && board[i][j] == board[i - 1][j + 1] && board[i][j] == board[i - 2][j + 2])
                        return true;
                    if (board[i - 1][j + 1] == player && board[i - 1][j + 1] == board[i - 2][j + 2]
                            && board[i - 1][j + 1] == board[i - 3][j + 3] && board[i][j] == 0 && board[i+1][j]!=0)
                        return true;
                    //Cases for 2 in a row and 1 separated
                    if (board[i][j] == player && board[i][j] == board[i - 1][j + 1] && board[i][j] == board[i - 3][j + 3] &&
                            board[i - 2][j + 2] == 0 && board[i - 1][j + 2] != 0)
                        return true;
                    if (board[i][j] == player && board[i][j] == board[i - 2][j + 2] && board[i][j] == board[i - 3][j + 3] &&
                            board[i - 1][j + 1] == 0 && board[i][j + 1] != 0)
                        return true;
                }
                //At the last row (i = 5), some need bases, some do not need
                //3 in a row
                //Bottom left of the upward diagonal check is empty
                if (board[i][j] == player && board[i - 3][j + 3] == 0 && board[i - 2][j + 3] != 0
                        && board[i][j] == board[i - 1][j + 1] && board[i][j] == board[i - 2][j + 2])
                    return true;
                //Top right of the upward diagonal is empty
                if (board[i - 1][j + 1] == player && board[i - 1][j + 1] == board[i - 2][j + 2]
                        && board[i - 1][j + 1] == board[i - 3][j + 3] && board[i][j] == 0)
                    return true;
                //Cases for 2 in a row and 1 separated
                //Checks for empty spaces in between the upward diagonal check
                if (board[i][j] == player && board[i][j] == board[i - 1][j + 1] && board[i][j] == board[i - 3][j + 3] &&
                        board[i - 2][j + 2] == 0 && board[i - 1][j + 2] != 0)
                    return true;
                if (board[i][j] == player && board[i][j] == board[i - 2][j + 2] && board[i][j] == board[i - 3][j + 3] &&
                        board[i - 1][j + 1] == 0 && board[i][j + 1] != 0)
                    return true;
            }
        }

        //Downward Diagonal check from top left to bottom right
        //i will go from 0 to 2 (3 rows), j will go from 0 to 4 (5 cols)
        for(int i = 0; i < HEIGHT -3; i++) {
            for (int j = 0; j < WIDTH -3; j++) {
                //All checking slots need bases
                if (i < 2) {
                    //i is 0 or 1
                    //Case for 3 in a row
                    if (board[i][j] == player && board[i][j] == board[i + 1][j + 1] && board[i][j] == board[i + 2][j + 2] &&
                            board[i + 3][j + 3] == 0 && board[i + 4][j + 3] != 0)
                        return true;
                    if (board[i + 1][j + 1] == player && board[i + 1][j + 1] == board[i + 2][j + 2]
                            && board[i + 1][j + 1] == board[i + 3][j + 3] && board[i][j] == 0 && board[i + 1][j] != 0)
                        return true;
                    //Cases for 2 in a row and 1 separated
                    if (board[i][j] == player && board[i][j] == board[i + 1][j + 1] && board[i][j] == board[i + 3][j + 3] &&
                            board[i + 2][j + 2] == 0 && board[i + 3][j + 2] != 0)
                        return true;
                    if (board[i][j] == player && board[i][j] == board[i + 2][j + 2] && board[i][j] == board[i + 3][j + 3] &&
                            board[i + 1][j + 1] == 0 && board[i + 2][j + 1] != 0)
                        return true;
                }
                //i is 3 because that is the max row the downward diagonal can check
                //Some need bases, some do not need
                //Cases for 3 in a row
                //Bottom right of downward diagonal is empty
                if (board[i][j] == player && board[i][j] == board[i + 1][j + 1] && board[i][j] == board[i + 2][j + 2] &&
                        board[i + 3][j + 3] == 0)
                    return true;
                //Top left of downward diagonal is empty
                if (board[i + 1][j + 1] == player && board[i + 1][j + 1] == board[i + 2][j + 2] && board[i + 1][j + 1] == board[i + 3][j + 3] &&
                        board[i][j] == 0 && board[i + 1][j] != 0)
                    return true;
                //Cases for 2 in a row and 1 separated
                //Checks for empty spaces in between the downard diagonal check
                if (board[i][j] == player && board[i][j] == board[i + 1][j + 1] && board[i][j] == board[i + 3][j + 3] &&
                        board[i + 2][j + 2] == 0 && board[i + 3][j + 2] != 0)
                    return true;
                if (board[i][j] == player && board[i][j] == board[i + 2][j + 2] && board[i][j] == board[i + 3][j + 3] &&
                        board[i + 1][j + 1] == 0 && board[i + 2][j + 1] != 0)
                    return true;
            }
        }
        return false;
    }

    /**
     * This method is very similar to the method check. However, a check-mate move
     * means no matter how the other player place his/her next block, in the next
     * turn the player can win the game with certain move.
     *
     * A check-mate move must be a check move. Not all check moves are check-mate
     * move.
     *
     * @param player - the player who is about to win no matter what the other
     *               player does
     * @param board  - the 2D array of the game board/
     * @return true if the player is about to win
     */
    boolean checkMate(int player, int[][] board) {
        //Horizontal checkmate
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < 4; j++) {
                //Needs to have a base for checkmate
                if (i < 5){
                    if (board[i][j+1] == player && board[i][j+1] == board[i][j+2] && board[i][j+1] == board[i][j+3]
                            && board[i][j+4] == 0 && board[i][j] == 0 && board[i+1][j+4] != 0 && board[i+1][j] != 0)
                        return true;
                }
                //Last row, so need to have base for checkmate
                //3 in a row
                if( i == 5) {
                    if (board[i][j + 1] == player && board[i][j + 1] == board[i][j + 2] && board[i][j + 1] == board[i][j + 3]
                            && (board[i][j + 4] == 0 && board[i][j] == 0))
                        return true;
                }
            }
        }
        //No checkmate for vertical checkmate

        //Upward Diagonal checkmate from bottom left to top right
        // i will go from 3 to 4 (2 rows) & j will go from 0 to 3 (4 cols)
        // i start from 3 because that is the smallest row it can start checkmating
        for (int i = 3; i < 5; i++) {
            for (int j = 0; j < 4; j++) {
                //i == 3 because both empty slots need to have a base
                if (i == 3) {
                    if (board[i][j+1] == player && board[i][j+1] == board[i-1][j+2] && board[i][j+1] == board[i-2][j+3]
                            && board[i+1][j] == 0 && board[i+2][j] != 0 && board[i-3][j+4] == 0 && board[i-2][j+4] != 0)
                        return true;
                }
                //only the empty slot on the top right need to have a base
                if (board[i][j+1] == player && board[i][j+1] == board[i-1][j+2] && board[i][j+1] == board[i-2][j+3]
                        && board[i+1][j] == 0 && board[i-3][j+4] == 0 && board[i-2][j+4] != 0)
                    return true;
            }
        }

        //Downward Diagonal checkmate from top left to bottom right
        //i will go from 0 to 1 (2 rows), j will go from 0 to 3 (4 cols)
        for(int i = 0; i < 2; i++) {
            for (int j = 0; j < 4; j++) {
                //i == 0 because both empty slots need to have base
                if (i == 0) {
                    if (board[i + 1][j + 1] == player && board[i + 1][j + 1] == board[i + 2][j + 2] && board[i + 1][j + 1] == board[i + 3][j + 3]
                            && board[i + 4][j + 4] == 0 && board[i][j] == 0 && board[i + 5][j + 4] != 0 && board[i + 1][j] != 0)
                        return true;
                }
                //only the empty slot on the top left need to have a base
                if (board[i + 1][j + 1] == player && board[i + 1][j + 1] == board[i + 2][j + 2] && board[i + 1][j + 1] == board[i + 3][j + 3]
                        && board[i + 4][j + 4] == 0 && board[i][j] == 0 && board[i + 1][j] != 0)
                    return true;
            }
        }
        return false;
    }

    /**
     * Validate if the input is valid. This input should be one of the character
     * '0', '1', '2', '3,' ..., '7'.
     * The column corresponding to that input should not be full.
     *
     * @param input - the character of the column that the block is intended to
     *              place
     * @param board - the game board
     * @return - true if it is valid, false if it is invalid (e.g., '8', 'c', '@',
     *         EOT (which has an unicode 4) )
     */
    boolean validate(char input, int[][] board) {
        //Only accepts characters from 0 to 7
        if ((int)input < 48 || (int)input >= 56){
            return false;
        }
        int s_Int = Integer.parseInt(String.valueOf(input));
        if(board[0][s_Int] != 0)
            return false;
        return true;
    }

    /**
     * Given the column (in integer) that a player wish to place his/her block,
     * update the gameboard. You may assume that the input has been validated before
     * calling this method, i.e., there always has room to place the block when
     * calling this method.
     *
     * @param board  - the game board
     * @param column - the column that the player want to places its block
     * @param player - 1 or 2, the player.
     */
    void fillBoard(int[][] board, int column, int player) {
        //to fill the bottom part of the board, if the bottom already has input, then place on top of it
        for (int i = HEIGHT-1; i >= 0; i--) {
            if (board[i][column] == 0) {
                board[i][column] = player;
                break;
            }
        }
    }

    /**
     * Print the Help Menu. Please try to understand the switch case in runOnce and
     * Provide a one line comment about the purpose of each symbol.
     */
    void printHelpMenu() {
        System.out.println("'h' or 'H' is the help button.");
        System.out.println("'c' or 'C' is to change the player's symbol. Player will not lose their turn if" +
                " they decide to change their symbol.");
        System.out.println("'q' or 'Q' is quit the game.");
        System.out.println("'r' is to restart the game. Player will not lose their turn if they decide to" +
                " restart the game");
    }

    /**
     * Determine if the game is over. Game is over if and only if one of the player
     * has a connect-4 or the entire gameboard is fully filled.
     *
     * @param board - the game board
     * @return - true if the game is over, false other wise.
     */
    boolean isGameOver(int[][] board) {
        //check for 4 across
        for(int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH-3; j++) {
                if (board[i][j] != 0 && board[i][j] == board[i][j+1] && board[i][j] == board[i][j+2]
                        && board[i][j] == board[i][j+3])
                    return true;
                else continue;
            }
        }
        //check for 4 vertically up & down
        for(int i = 0; i < 3; i++) {
            for (int j = 0; j < WIDTH; j++) {
                if (board[i][j] != 0 && board[i][j] == board[i + 1][j] && board[i][j] == board[i + 2][j] && board[i][j] == board[i + 3][j])
                    return true;
                else continue;
            }
        }

        //check for upward diagonal across
        for(int i = 3; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH-3; j++) {
                if (board[i][j] != 0 && board[i][j] == board[i-1][j+1] && board[i][j] == board[i-2][j+2]
                        && board[i][j] == board[i-3][j+3])
                    return true;
                else continue;
            }
        }

        //check for downward diagonal across
        for(int i = 0; i < 3; i++) {
            for (int j = 0; j < WIDTH-3; j++) {
                if (board[i][j] != 0 && board[i][j] == board[i+1][j+1] && board[i][j] == board[i+2][j+2]
                        && board[i][j] == board[i+3][j+3])
                    return true;
                else continue;
            }
        }

        // check if board is fully filled
        int count = 0;
        for(int i = 0; i < HEIGHT; i++) {
            for(int j = 0; j < WIDTH; j++) {
                if (board[i][j] != 0)
                    count += 1;
            }
        }
        if (count == 48)
            return true;
        return false;
    }

    /**
     * Print the game board in a particular format. The instruction can be referred
     * to the GitHub or the demo program. By default, Player 1 uses the character
     * '1' to represent its block. Player 2 uses the character '2'. They can be
     * overrided by the value of symbols array. This method does not change the
     * value of the gameboard nor the symbols array.
     *
     * @param board   - the game board to be printed.
     * @param symbols - the symbols that represents player 1 and player 2.
     */
    void printBoard(int[][] board, char[] symbols) {
        System.out.println(" 01234567");
        System.out.println(" --------");
        for (int row = 0; row < HEIGHT; row++){
            System.out.print("|");
            for (int col = 0; col < WIDTH; col++){
                if (board[row][col] == 0)
                    System.out.print(" ");
                else
                    System.out.print(symbols[board[row][col]-1]);
            }
            System.out.println("|");
        }
        System.out.println(" --------");
    }

}
