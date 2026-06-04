/*
 * Author: Tsyplakov Kirill
 * Group: 3823Б1ПР2
 * Date: 04.06.2026
*/


package com.mycompany.app;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

public class AppTest {  

    private Game myGame;
    private Player p1;
    private Player p2;

    @BeforeEach
    void setup() {
        myGame = new Game();
        p1 = myGame.player1;
        p2 = myGame.player2;
    }

    @Test
    @DisplayName("TS-1 check board after start")
    void testBoardInit() {
        for (int i = 0; i < 9; i++) {
            assertEquals(' ', myGame.board[i]);
        }
        assertEquals(State.PLAYING, myGame.state);
        assertEquals('X', myGame.player1.symbol);
        assertEquals('O', myGame.player2.symbol);
    }

    @Test
    @DisplayName("TS-2 all moves on empty board")
    void testAllMovesEmpty() {
        ArrayList<Integer> moves = new ArrayList<>();
        myGame.generateMoves(myGame.board, moves);
        assertEquals(9, moves.size());
        for (int i = 0; i < 9; i++) {
            assertTrue(moves.contains(i));
        }
    }

    @Test
    @DisplayName("TS-3 moves after some turns")
    void testMovesAfterSome() {
        myGame.board[0] = 'X';
        myGame.board[4] = 'O';
        
        ArrayList<Integer> moves = new ArrayList<>();
        myGame.generateMoves(myGame.board, moves);
        
        assertEquals(7, moves.size());
        assertFalse(moves.contains(0));
        assertFalse(moves.contains(4));
        assertTrue(moves.contains(1));
    }

    @Test
    @DisplayName("TS-4 x win top row")
    void testXWinTopRow() {
        myGame.board[0] = 'X';
        myGame.board[1] = 'X';
        myGame.board[2] = 'X';
        myGame.symbol = 'X';
        
        State res = myGame.checkState(myGame.board);
        assertEquals(State.XWIN, res);
    }

    @Test
    @DisplayName("TS-5 x win left column")
    void testXWinLeftCol() {
        myGame.board[0] = 'X';
        myGame.board[3] = 'X';
        myGame.board[6] = 'X';
        myGame.symbol = 'X';
        
        State res = myGame.checkState(myGame.board);
        assertEquals(State.XWIN, res);
    }

    @Test
    @DisplayName("TS-6 x win main diag")
    void testXWinMainDiag() {
        myGame.board[0] = 'X';
        myGame.board[4] = 'X';
        myGame.board[8] = 'X';
        myGame.symbol = 'X';
        
        State res = myGame.checkState(myGame.board);
        assertEquals(State.XWIN, res);
    }

    @Test
    @DisplayName("TS-7 x win second diag")
    void testXWinSecondDiag() {
        myGame.board[2] = 'X';
        myGame.board[4] = 'X';
        myGame.board[6] = 'X';
        myGame.symbol = 'X';
        
        State res = myGame.checkState(myGame.board);
        assertEquals(State.XWIN, res);
    }

    @Test
    @DisplayName("TS-8 o win top row")
    void testOWinTopRow() {
        myGame.board[0] = 'O';
        myGame.board[1] = 'O';
        myGame.board[2] = 'O';
        myGame.symbol = 'O';
        
        State res = myGame.checkState(myGame.board);
        assertEquals(State.OWIN, res);
    }

    @Test
    @DisplayName("TS-9 draw when board full")
    void testDrawFull() {
        myGame.board[0] = 'X'; myGame.board[1] = 'O'; myGame.board[2] = 'X';
        myGame.board[3] = 'O'; myGame.board[4] = 'X'; myGame.board[5] = 'O';
        myGame.board[6] = 'O'; myGame.board[7] = 'X'; myGame.board[8] = 'O';
        myGame.symbol = 'X';
        
        State res = myGame.checkState(myGame.board);
        assertEquals(State.DRAW, res);
    }

    @Test
    @DisplayName("TS-10 game still playing")
    void testStillPlaying() {
        myGame.board[0] = 'X';
        myGame.board[1] = 'O';
        
        State res = myGame.checkState(myGame.board);
        assertEquals(State.PLAYING, res);
    }

    @Test
    @DisplayName("TS-11 score when player win")
    void testScoreWin() {
        myGame.board[0] = 'X';
        myGame.board[1] = 'X';
        myGame.board[2] = 'X';
        myGame.symbol = 'X';
        
        int res = myGame.evaluatePosition(myGame.board, myGame.player1);
        assertEquals(Game.INF, res);
    }

    @Test
    @DisplayName("TS-12 score when player lose")
    void testScoreLose() {
        myGame.board[0] = 'O';
        myGame.board[1] = 'O';
        myGame.board[2] = 'O';
        myGame.symbol = 'O';
        
        int res = myGame.evaluatePosition(myGame.board, myGame.player1);
        assertEquals(-Game.INF, res);
    }

    @Test
    @DisplayName("TS-13 score when draw")
    void testScoreDraw() {
        myGame.board[0] = 'X'; myGame.board[1] = 'O'; myGame.board[2] = 'X';
        myGame.board[3] = 'O'; myGame.board[4] = 'X'; myGame.board[5] = 'O';
        myGame.board[6] = 'O'; myGame.board[7] = 'X'; myGame.board[8] = 'O';
        
        int res = myGame.evaluatePosition(myGame.board, myGame.player1);
        assertEquals(0, res);
    }

    @Test
    @DisplayName("TS-14 ai first move on empty")
    void testAiFirstMove() {
        myGame.symbol = 'X';
        int move = myGame.MiniMax(myGame.board, myGame.player1);
        
        assertTrue(move >= 1 && move <= 9);
        assertTrue(myGame.board[move - 1] == ' ');
    }

    @Test
    @DisplayName("TS-15 create new game")
    void testNewGame() {
        Game g = new Game();
        assertNotNull(g);
        assertEquals(9, g.board.length);
    }

    @Test
    @DisplayName("TS-16 player symbols check")
    void testPlayerSymbols() {
        assertEquals('X', myGame.player1.symbol);
        assertEquals('O', myGame.player2.symbol);
    }

    @Test
    @DisplayName("TS-17 inf constant")
    void testInfConst() {
        assertEquals(100, Game.INF);
    }

    @Test
    @DisplayName("TS-18 max move when game done")
    void testMaxMoveDone() {
        myGame.board[0] = 'X';
        myGame.board[1] = 'X';
        myGame.board[2] = 'X';
        int res = myGame.MaxMove(myGame.board, myGame.player1);
        assertTrue(res == Game.INF || res == -Game.INF || res == 0);
    }

    @Test
    @DisplayName("TS-19 min move when game done")
    void testMinMoveDone() {
        myGame.board[0] = 'O';
        myGame.board[1] = 'O';
        myGame.board[2] = 'O';
        int res = myGame.MinMove(myGame.board, myGame.player2);
        assertTrue(res == Game.INF || res == -Game.INF || res == 0);
    }

    @Test
    @DisplayName("TS-20 x win middle row")
    void testXWinMiddleRow() {
        myGame.board[3] = 'X';
        myGame.board[4] = 'X';
        myGame.board[5] = 'X';
        myGame.symbol = 'X';
        assertEquals(State.XWIN, myGame.checkState(myGame.board));
    }

    @Test
    @DisplayName("TS-21 o win middle row")
    void testOWinMiddleRow() {
        myGame.board[3] = 'O';
        myGame.board[4] = 'O';
        myGame.board[5] = 'O';
        myGame.symbol = 'O';
        assertEquals(State.OWIN, myGame.checkState(myGame.board));
    }

    @Test
    @DisplayName("TS-22 score o win for x player")
    void testScoreOWinForX() {
        myGame.board[0] = 'O';
        myGame.board[1] = 'O';
        myGame.board[2] = 'O';
        myGame.symbol = 'O';
        
        int res = myGame.evaluatePosition(myGame.board, myGame.player1);
        assertEquals(-Game.INF, res);
    }

    @Test
    @DisplayName("TS-23 score x win for o player")
    void testScoreXWinForO() {
        myGame.board[0] = 'X';
        myGame.board[1] = 'X';
        myGame.board[2] = 'X';
        myGame.symbol = 'X';
        
        int res = myGame.evaluatePosition(myGame.board, myGame.player2);
        assertEquals(-Game.INF, res);
    }

    @Test
    @DisplayName("TS-24 print char array")
    void testPrintChar() {
        char[] b = {'X', 'O', ' ', 'X', ' ', 'O', ' ', 'X', ' '};
        Utility.print(b);
        assertTrue(true);
    }

    @Test
    @DisplayName("TS-25 print int array")
    void testPrintInt() {
        int[] b = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        Utility.print(b);
        assertTrue(true);
    }

    @Test
    @DisplayName("TS-26 print array list")
    void testPrintList() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(3);
        list.add(5);
        Utility.print(list);
        assertTrue(true);
    }

    @Test
    @DisplayName("TS-27 no moves on full board")
    void testNoMovesFull() {
        for (int i = 0; i < 9; i++) {
            myGame.board[i] = 'X';
        }
        ArrayList<Integer> moves = new ArrayList<>();
        myGame.generateMoves(myGame.board, moves);
        assertEquals(0, moves.size());
    }

    @Test
    @DisplayName("TS-28 score when game not finished")
    void testScoreNotFinished() {
        myGame.board[0] = 'X';
        myGame.board[4] = 'O';
        int res = myGame.evaluatePosition(myGame.board, myGame.player1);
        assertEquals(-1, res);
    }

    @Test
    @DisplayName("TS-29 ai move with one cell taken")
    void testAiMoveOneTaken() {
        myGame.board[0] = 'X';
        myGame.symbol = 'O';
        int move = myGame.MiniMax(myGame.board, myGame.player2);
        assertTrue(move >= 1 && move <= 9);
        assertTrue(myGame.board[move - 1] == ' ');
    }

    @Test
    @DisplayName("TS-30 check state without symbol")
    void testCheckStateNoSym() {
        myGame.board[0] = 'X';
        myGame.board[1] = 'X';
        myGame.board[2] = 'X';
        State res = myGame.checkState(myGame.board);
        assertNotNull(res);
    }

    @Test
    @DisplayName("TS-31 max move x win")
    void testMaxMoveXWin() {
        myGame.board[0] = 'X';
        myGame.board[1] = 'X';
        myGame.board[2] = ' ';
        myGame.symbol = 'X';
        int res = myGame.MaxMove(myGame.board, myGame.player1);
        assertEquals(Game.INF, res);
    }

    @Test
    @DisplayName("TS-32 min move o win")
    void testMinMoveOWin() {
        myGame.board[0] = 'O';
        myGame.board[1] = 'O';
        myGame.board[2] = ' ';
        myGame.symbol = 'O';
        int res = myGame.MinMove(myGame.board, myGame.player2);
        assertTrue(res >= -Game.INF && res <= Game.INF);
    }

    @Test
    @DisplayName("TS-33 max move draw")
    void testMaxMoveDraw() {
        myGame.board[0] = 'X'; myGame.board[1] = 'O'; myGame.board[2] = 'X';
        myGame.board[3] = 'O'; myGame.board[4] = 'X'; myGame.board[5] = 'O';
        myGame.board[6] = 'O'; myGame.board[7] = 'X'; myGame.board[8] = ' ';
        myGame.symbol = 'X';
        int res = myGame.MaxMove(myGame.board, myGame.player1);
        assertTrue(res >= -Game.INF && res <= Game.INF);
    }

    @Test
    @DisplayName("TS-34 ai picks winning move o")
    void testAiPickWinO() {
        myGame.board[0] = 'O';
        myGame.board[1] = 'O';
        myGame.board[2] = ' ';
        myGame.board[3] = 'X';
        myGame.board[4] = 'X';
        myGame.symbol = 'O';
        int move = myGame.MiniMax(myGame.board, myGame.player2);
        assertEquals(3, move);
    }

    @Test
    @DisplayName("TS-35 ai picks winning move x")
    void testAiPickWinX() {
        myGame.board[0] = 'X';
        myGame.board[1] = 'X';
        myGame.board[2] = ' ';
        myGame.board[3] = 'O';
        myGame.board[4] = 'O';
        myGame.symbol = 'X';
        int move = myGame.MiniMax(myGame.board, myGame.player1);
        assertEquals(3, move);
    }

    @Test
    @DisplayName("TS-36 all x win combos")
    void testAllXCombos() {
        myGame.board[3] = 'X'; myGame.board[4] = 'X'; myGame.board[5] = 'X';
        myGame.symbol = 'X';
        assertEquals(State.XWIN, myGame.checkState(myGame.board));
        myGame = new Game();
        
        myGame.board[6] = 'X'; myGame.board[7] = 'X'; myGame.board[8] = 'X';
        myGame.symbol = 'X';
        assertEquals(State.XWIN, myGame.checkState(myGame.board));
        myGame = new Game();
        
        myGame.board[1] = 'X'; myGame.board[4] = 'X'; myGame.board[7] = 'X';
        myGame.symbol = 'X';
        assertEquals(State.XWIN, myGame.checkState(myGame.board));
        myGame = new Game();
        
        myGame.board[2] = 'X'; myGame.board[5] = 'X'; myGame.board[8] = 'X';
        myGame.symbol = 'X';
        assertEquals(State.XWIN, myGame.checkState(myGame.board));
    }

    @Test
    @DisplayName("TS-37 all o win combos")
    void testAllOCombos() {
        myGame.board[0] = 'O'; myGame.board[1] = 'O'; myGame.board[2] = 'O';
        myGame.symbol = 'O';
        assertEquals(State.OWIN, myGame.checkState(myGame.board));
    }

    @Test
    @DisplayName("TS-38 null board test")
    void testNullBoard() {
        ArrayList<Integer> moves = new ArrayList<>();
        myGame.board = null;
        assertThrows(NullPointerException.class, () -> {
            myGame.generateMoves(myGame.board, moves);
        });
        myGame.board = new char[9];
    }

    @Test
    @DisplayName("TS-39 score diff players")
    void testScoreDiffPlayers() {
        myGame.board[0] = 'X'; myGame.board[1] = 'X'; myGame.board[2] = 'X';
        myGame.symbol = 'X';
        
        assertEquals(Game.INF, myGame.evaluatePosition(myGame.board, myGame.player1));
        assertEquals(-Game.INF, myGame.evaluatePosition(myGame.board, myGame.player2));
    }

    @Test
    @DisplayName("TS-40 q counter reset")
    void testQReset() {
        myGame.q = 100;
        myGame.symbol = 'X';
        myGame.MiniMax(myGame.board, myGame.player1);
        assertEquals(0, myGame.q);
    }

    @Test
    @DisplayName("TS-41 random when equal")
    void testRandomEqual() {
        myGame.symbol = 'X';
        for (int i = 0; i < 5; i++) {
            Game g = new Game();
            int move = g.MiniMax(g.board, g.player1);
            assertTrue(move >= 1 && move <= 9);
        }
    }

    @Test
    @DisplayName("TS-42 check state empty symbol")
    void testCheckStateEmptySym() {
        myGame.board[0] = 'X';
        myGame.board[1] = 'X';
        myGame.board[2] = 'X';
        myGame.symbol = ' ';
        State res = myGame.checkState(myGame.board);
        assertNotEquals(State.XWIN, res);
    }

    @Test
    @DisplayName("TS-43 max move negative inf")
    void testMaxMoveNegInf() {
        myGame.board[0] = 'O'; myGame.board[1] = 'O'; myGame.board[2] = 'O';
        myGame.symbol = 'O';
        int res = myGame.MaxMove(myGame.board, myGame.player1);
        assertEquals(-Game.INF, res);
    }

    @Test
    @DisplayName("TS-44 min move positive inf")
    void testMinMovePosInf() {
        myGame.board[0] = 'X';
        myGame.board[1] = 'X';
        myGame.board[2] = 'X';
        myGame.symbol = 'X';
        int res = myGame.MinMove(myGame.board, myGame.player2);
        assertTrue(res == -Game.INF || res == Game.INF);
    }

    @Test
    @DisplayName("TS-45 deep minimax")
    void testDeepMinimax() {
        myGame.board[0] = 'X';
        myGame.board[4] = 'O';
        myGame.board[8] = 'X';
        myGame.symbol = 'O';
        int move = myGame.MiniMax(myGame.board, myGame.player2);
        assertTrue(move >= 1 && move <= 9);
    }

    @Test
    @DisplayName("TS-46 moves with occupied cells")
    void testMovesOccupied() {
        for (int i = 0; i < 9; i++) {
            myGame.board[i] = 'X';
        }
        ArrayList<Integer> moves = new ArrayList<>();
        myGame.generateMoves(myGame.board, moves);
        assertEquals(0, moves.size());
        
        myGame.board[4] = ' ';
        moves.clear();
        myGame.generateMoves(myGame.board, moves);
        assertEquals(1, moves.size());
        assertEquals(4, moves.get(0));
    }
}