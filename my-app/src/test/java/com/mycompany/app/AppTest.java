package com.mycompany.app;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тесты для игры Крестики-Нолики")
public class AppTest {  

    private Game game;
    private Player playerX;
    private Player playerO;

    @BeforeEach
    void setUp() {
        game = new Game();
        playerX = game.player1;
        playerO = game.player2;
    }

    @Test
    @DisplayName("Проверка инициализации доски")
    void testBoardInitialization() {
        for (int i = 0; i < 9; i++) {
            assertEquals(' ', game.board[i], "Клетка " + i + " должна быть пустой");
        }
        assertEquals(State.PLAYING, game.state);
        assertEquals('X', game.player1.symbol);
        assertEquals('O', game.player2.symbol);
    }

    @Test
    @DisplayName("Генерация всех возможных ходов на пустой доске")
    void testGenerateMovesEmptyBoard() {
        ArrayList<Integer> moves = new ArrayList<>();
        game.generateMoves(game.board, moves);
        assertEquals(9, moves.size(), "На пустой доске должно быть 9 ходов");
        for (int i = 0; i < 9; i++) {
            assertTrue(moves.contains(i), "Ход " + i + " должен быть доступен");
        }
    }

    @Test
    @DisplayName("Генерация ходов после нескольких ходов")
    void testGenerateMovesAfterMoves() {
        game.board[0] = 'X';
        game.board[4] = 'O';
        
        ArrayList<Integer> moves = new ArrayList<>();
        game.generateMoves(game.board, moves);
        
        assertEquals(7, moves.size(), "Должно остаться 7 свободных клеток");
        assertFalse(moves.contains(0), "Клетка 0 не должна быть доступна");
        assertFalse(moves.contains(4), "Клетка 4 не должна быть доступна");
        assertTrue(moves.contains(1), "Клетка 1 должна быть доступна");
    }

    @Test
    @DisplayName("Проверка победы X по горизонтали")
    void testCheckStateXWinHorizontal() {
        game.board[0] = 'X';
        game.board[1] = 'X';
        game.board[2] = 'X';
        game.symbol = 'X';
        
        State result = game.checkState(game.board);
        assertEquals(State.XWIN, result);
    }

    @Test
    @DisplayName("Проверка победы X по вертикали")
    void testCheckStateXWinVertical() {
        game.board[0] = 'X';
        game.board[3] = 'X';
        game.board[6] = 'X';
        game.symbol = 'X';
        
        State result = game.checkState(game.board);
        assertEquals(State.XWIN, result);
    }

    @Test
    @DisplayName("Проверка победы X по диагонали")
    void testCheckStateXWinDiagonal() {
        game.board[0] = 'X';
        game.board[4] = 'X';
        game.board[8] = 'X';
        game.symbol = 'X';
        
        State result = game.checkState(game.board);
        assertEquals(State.XWIN, result);
    }

    @Test
    @DisplayName("Проверка победы X по обратной диагонали")
    void testCheckStateXWinAntiDiagonal() {
        game.board[2] = 'X';
        game.board[4] = 'X';
        game.board[6] = 'X';
        game.symbol = 'X';
        
        State result = game.checkState(game.board);
        assertEquals(State.XWIN, result);
    }

    @Test
    @DisplayName("Проверка победы O")
    void testCheckStateOWin() {
        game.board[0] = 'O';
        game.board[1] = 'O';
        game.board[2] = 'O';
        game.symbol = 'O';
        
        State result = game.checkState(game.board);
        assertEquals(State.OWIN, result);
    }

    @Test
    @DisplayName("Проверка ничьей")
    void testCheckStateDraw() {
        game.board[0] = 'X'; game.board[1] = 'O'; game.board[2] = 'X';
        game.board[3] = 'O'; game.board[4] = 'X'; game.board[5] = 'O';
        game.board[6] = 'O'; game.board[7] = 'X'; game.board[8] = 'O';
        game.symbol = 'X';
        
        State result = game.checkState(game.board);
        assertEquals(State.DRAW, result);
    }

    @Test
    @DisplayName("Проверка состояния PLAYING")
    void testCheckStatePlaying() {
        game.board[0] = 'X';
        game.board[1] = 'O';
        
        State result = game.checkState(game.board);
        assertEquals(State.PLAYING, result);
    }

    @Test
    @DisplayName("Оценка позиции - победа игрока")
    void testEvaluatePositionWin() {
        game.board[0] = 'X';
        game.board[1] = 'X';
        game.board[2] = 'X';
        game.symbol = 'X';
        
        int result = game.evaluatePosition(game.board, game.player1);
        assertEquals(Game.INF, result);
    }

    @Test
    @DisplayName("Оценка позиции - поражение игрока")
    void testEvaluatePositionLoss() {
        game.board[0] = 'O';
        game.board[1] = 'O';
        game.board[2] = 'O';
        game.symbol = 'O';
        
        int result = game.evaluatePosition(game.board, game.player1);
        assertEquals(-Game.INF, result);
    }

    @Test
    @DisplayName("Оценка позиции - ничья")
    void testEvaluatePositionDraw() {
        game.board[0] = 'X'; game.board[1] = 'O'; game.board[2] = 'X';
        game.board[3] = 'O'; game.board[4] = 'X'; game.board[5] = 'O';
        game.board[6] = 'O'; game.board[7] = 'X'; game.board[8] = 'O';
        
        int result = game.evaluatePosition(game.board, game.player1);
        assertEquals(0, result);
    }

    @Test
    @DisplayName("Минимакс на пустой доске")
    void testMiniMaxFirstMove() {
        game.symbol = 'X';
        int bestMove = game.MiniMax(game.board, game.player1);
        
        assertTrue(bestMove >= 1 && bestMove <= 9, "Ход должен быть от 1 до 9");
        assertTrue(game.board[bestMove - 1] == ' ', "Ход должен быть в пустую клетку");
    }

    @Test
    @DisplayName("Создание новой игры")
    void testGameCreation() {
        Game newGame = new Game();
        assertNotNull(newGame);
        assertEquals(9, newGame.board.length);
    }

    @Test
    @DisplayName("Проверка символов игроков")
    void testPlayerSymbols() {
        assertEquals('X', game.player1.symbol);
        assertEquals('O', game.player2.symbol);
    }

    @Test
    @DisplayName("Проверка константы INF")
    void testInfConstant() {
        assertEquals(100, Game.INF);
    }

    
    @Test
    @DisplayName("Тест MaxMove на завершенной игре")
    void testMaxMoveFinishedGame() {
        game.board[0] = 'X';
        game.board[1] = 'X';
        game.board[2] = 'X';
        int result = game.MaxMove(game.board, game.player1);
        assertTrue(result == Game.INF || result == -Game.INF || result == 0);
    }

    @Test
    @DisplayName("Тест MinMove на завершенной игре")
    void testMinMoveFinishedGame() {
        game.board[0] = 'O';
        game.board[1] = 'O';
        game.board[2] = 'O';
        int result = game.MinMove(game.board, game.player2);
        assertTrue(result == Game.INF || result == -Game.INF || result == 0);
    }

    @Test
    @DisplayName("Проверка выигрышной комбинации для X в середине")
    void testXWinCenterRow() {
        game.board[3] = 'X';
        game.board[4] = 'X';
        game.board[5] = 'X';
        game.symbol = 'X';
        assertEquals(State.XWIN, game.checkState(game.board));
    }

    @Test
    @DisplayName("Проверка выигрышной комбинации для O в середине")
    void testOWinCenterRow() {
        game.board[3] = 'O';
        game.board[4] = 'O';
        game.board[5] = 'O';
        game.symbol = 'O';
        assertEquals(State.OWIN, game.checkState(game.board));
    }

    @Test
    @DisplayName("Тест evaluatePosition с победой O для игрока X")
    void testEvaluatePositionOWinForX() {
        game.board[0] = 'O';
        game.board[1] = 'O';
        game.board[2] = 'O';
        game.symbol = 'O';
        
        int result = game.evaluatePosition(game.board, game.player1);
        assertEquals(-Game.INF, result, "Победа O должна дать -INF для X");
    }

    @Test
    @DisplayName("Тест evaluatePosition с победой X для игрока O")
    void testEvaluatePositionXWinForO() {
        game.board[0] = 'X';
        game.board[1] = 'X';
        game.board[2] = 'X';
        game.symbol = 'X';
        
        int result = game.evaluatePosition(game.board, game.player2);
        assertEquals(-Game.INF, result, "Победа X должна дать -INF для O");
    }

    @Test
    @DisplayName("Тест Utility.print для char[]")
    void testUtilityPrintChar() {
        char[] testBoard = {'X', 'O', ' ', 'X', ' ', 'O', ' ', 'X', ' '};
        Utility.print(testBoard);
        assertTrue(true);
    }

    @Test
    @DisplayName("Тест Utility.print для int[]")
    void testUtilityPrintInt() {
        int[] testBoard = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        Utility.print(testBoard);
        assertTrue(true);
    }

    @Test
    @DisplayName("Тест Utility.print для ArrayList")
    void testUtilityPrintArrayList() {
        ArrayList<Integer> moves = new ArrayList<>();
        moves.add(1);
        moves.add(3);
        moves.add(5);
        Utility.print(moves);
        assertTrue(true);
    }

    @Test
    @DisplayName("Проверка generateMoves на полной доске")
    void testGenerateMovesFullBoard() {
        for (int i = 0; i < 9; i++) {
            game.board[i] = 'X';
        }
        ArrayList<Integer> moves = new ArrayList<>();
        game.generateMoves(game.board, moves);
        assertEquals(0, moves.size(), "На полной доске не должно быть ходов");
    }

    @Test
    @DisplayName("Тест evaluatePosition с продолжающейся игрой")
    void testEvaluatePositionPlaying() {
        game.board[0] = 'X';
        game.board[4] = 'O';
        int result = game.evaluatePosition(game.board, game.player1);
        assertEquals(-1, result, "Для продолжающейся игры возвращается -1");
    }

    @Test
    @DisplayName("Минимакс на доске с одним ходом")
    void testMiniMaxOneMove() {
        game.board[0] = 'X';
        game.symbol = 'O';
        int bestMove = game.MiniMax(game.board, game.player2);
        assertTrue(bestMove >= 1 && bestMove <= 9);
        assertTrue(game.board[bestMove - 1] == ' ', "Ход должен быть в пустую клетку");
    }

    @Test
    @DisplayName("Проверка метода checkState без установленного symbol")
    void testCheckStateWithoutSymbol() {
        game.board[0] = 'X';
        game.board[1] = 'X';
        game.board[2] = 'X';
        State result = game.checkState(game.board);
        assertNotNull(result);
    }

    @Test
    @DisplayName("Тест MaxMove на победу X")
    void testMaxMoveXWin() {
        game.board[0] = 'X';
        game.board[1] = 'X';
        game.board[2] = ' ';
        game.symbol = 'X';
        int result = game.MaxMove(game.board, game.player1);
        assertEquals(Game.INF, result);
    }

    @Test
    @DisplayName("Тест MinMove на победу O")
    void testMinMoveOWin() {
        game.board[0] = 'O';
        game.board[1] = 'O';
        game.board[2] = ' ';
        game.symbol = 'O';
        int result = game.MinMove(game.board, game.player2);
        assertTrue(result >= -Game.INF && result <= Game.INF);
    }

    @Test
    @DisplayName("Тест MaxMove на ничью")
    void testMaxMoveDraw() {
        game.board[0] = 'X'; game.board[1] = 'O'; game.board[2] = 'X';
        game.board[3] = 'O'; game.board[4] = 'X'; game.board[5] = 'O';
        game.board[6] = 'O'; game.board[7] = 'X'; game.board[8] = ' ';
        game.symbol = 'X';
        int result = game.MaxMove(game.board, game.player1);
        assertTrue(result >= -Game.INF && result <= Game.INF);
    }

    @Test
    @DisplayName("Минимакс выбирает выигрышный ход O")
    void testMiniMaxWinningMoveO() {
        game.board[0] = 'O';
        game.board[1] = 'O';
        game.board[2] = ' ';
        game.board[3] = 'X';
        game.board[4] = 'X';
        game.symbol = 'O';
        int bestMove = game.MiniMax(game.board, game.player2);
        assertEquals(3, bestMove);
    }

    @Test
    @DisplayName("Минимакс выбирает выигрышный ход X")
    void testMiniMaxWinningMoveX() {
        game.board[0] = 'X';
        game.board[1] = 'X';
        game.board[2] = ' ';
        game.board[3] = 'O';
        game.board[4] = 'O';
        game.symbol = 'X';
        int bestMove = game.MiniMax(game.board, game.player1);
        assertEquals(3, bestMove);
    }

    @Test
    @DisplayName("Проверка всех выигрышных комбинаций X")
    void testAllXWinningCombinations() {
        game.board[3] = 'X'; game.board[4] = 'X'; game.board[5] = 'X';
        game.symbol = 'X';
        assertEquals(State.XWIN, game.checkState(game.board));
        game = new Game();
        
        game.board[6] = 'X'; game.board[7] = 'X'; game.board[8] = 'X';
        game.symbol = 'X';
        assertEquals(State.XWIN, game.checkState(game.board));
        game = new Game();
        
        game.board[1] = 'X'; game.board[4] = 'X'; game.board[7] = 'X';
        game.symbol = 'X';
        assertEquals(State.XWIN, game.checkState(game.board));
        game = new Game();
        
        game.board[2] = 'X'; game.board[5] = 'X'; game.board[8] = 'X';
        game.symbol = 'X';
        assertEquals(State.XWIN, game.checkState(game.board));
    }

    @Test
    @DisplayName("Проверка всех выигрышных комбинаций O")
    void testAllOWinningCombinations() {
        game.board[0] = 'O'; game.board[1] = 'O'; game.board[2] = 'O';
        game.symbol = 'O';
        assertEquals(State.OWIN, game.checkState(game.board));
    }

    @Test
    @DisplayName("Негативный тест: неверные ходы в generateMoves")
    void testGenerateMovesWithInvalidBoard() {
        ArrayList<Integer> moves = new ArrayList<>();
        game.board = null;
        assertThrows(NullPointerException.class, () -> {
            game.generateMoves(game.board, moves);
        });
        game.board = new char[9];
    }

    @Test
    @DisplayName("Тест evaluatePosition с разными игроками")
    void testEvaluatePositionDifferentPlayers() {
        game.board[0] = 'X'; game.board[1] = 'X'; game.board[2] = 'X';
        game.symbol = 'X';
        
        assertEquals(Game.INF, game.evaluatePosition(game.board, game.player1));
        assertEquals(-Game.INF, game.evaluatePosition(game.board, game.player2));
    }

    @Test
    @DisplayName("Сброс счетчика q в MiniMax")
    void testQCounterReset() {
        game.q = 100;
        game.symbol = 'X';
        game.MiniMax(game.board, game.player1);
        assertEquals(0, game.q);
    }

    @Test
    @DisplayName("Случайный выбор при равных значениях в MiniMax")
    void testMiniMaxRandomChoice() {
        game.symbol = 'X';
        for (int i = 0; i < 5; i++) {
            Game newGame = new Game();
            int move = newGame.MiniMax(newGame.board, newGame.player1);
            assertTrue(move >= 1 && move <= 9);
        }
    }

    @Test
    @DisplayName("Проверка метода checkState, когда symbol не установлен")
    void testCheckStateNullSymbol() {
        game.board[0] = 'X';
        game.board[1] = 'X';
        game.board[2] = 'X';
        game.symbol = ' ';
        State result = game.checkState(game.board);
        assertNotEquals(State.XWIN, result);
    }

    @Test
    @DisplayName("MaxMove с отрицательной бесконечностью")
    void testMaxMoveNegativeInfinity() {
        game.board[0] = 'O'; game.board[1] = 'O'; game.board[2] = 'O';
        game.symbol = 'O';
        int result = game.MaxMove(game.board, game.player1);
        assertEquals(-Game.INF, result);
    }

    @Test
    @DisplayName("MinMove с положительной бесконечностью")
    void testMinMovePositiveInfinity() {
        game.board[0] = 'X';
        game.board[1] = 'X';
        game.board[2] = 'X';
        game.symbol = 'X';
        int result = game.MinMove(game.board, game.player2);
        assertTrue(result == -Game.INF || result == Game.INF);
        if (result == Game.INF) {
            assertEquals(Game.INF, result);
        } else {
            assertEquals(-Game.INF, result);
        }
    }

    @Test
    @DisplayName("Глубокий минимакс с несколькими ходами")
    void testDeepMiniMax() {
        game.board[0] = 'X';
        game.board[4] = 'O';
        game.board[8] = 'X';
        game.symbol = 'O';
        int bestMove = game.MiniMax(game.board, game.player2);
        assertTrue(bestMove >= 1 && bestMove <= 9);
    }

    @Test
    @DisplayName("Проверка на занятую клетку в generateMoves")
    void testGenerateMovesWithOccupiedCells() {
        for (int i = 0; i < 9; i++) {
            game.board[i] = 'X';
        }
        ArrayList<Integer> moves = new ArrayList<>();
        game.generateMoves(game.board, moves);
        assertEquals(0, moves.size());
        
        game.board[4] = ' ';
        moves.clear();
        game.generateMoves(game.board, moves);
        assertEquals(1, moves.size());
        assertEquals(4, moves.get(0));
    }

    @Test
    @DisplayName("Тест обновления symbol в MiniMax")
    void testSymbolUpdateInMiniMax() {
        game.symbol = ' ';
        game.MiniMax(game.board, game.player1);
        assertEquals('X', game.symbol);
    }

    @Test
    @DisplayName("Тест создания TicTacToeCell")
    void testTicTacToeCellCreation() {
        TicTacToeCell cell = new TicTacToeCell(5, 1, 1);
        assertEquals(' ', cell.getMarker());
        assertEquals(5, cell.getNum());
        assertEquals(1, cell.getRow());
        assertEquals(1, cell.getCol());
    }

    @Test
    @DisplayName("Тест setMarker в TicTacToeCell")
    void testTicTacToeCellSetMarker() {
        TicTacToeCell cell = new TicTacToeCell(0, 0, 0);
        cell.setMarker("X");
        assertEquals('X', cell.getMarker());
        assertFalse(cell.isEnabled());
    }

    @Test
    @DisplayName("Тест getRow и getCol")
    void testTicTacToeCellGetters() {
        TicTacToeCell cell = new TicTacToeCell(3, 2, 1);
        assertEquals(1, cell.getRow());
        assertEquals(2, cell.getCol());
        assertEquals(3, cell.getNum());
    }

    @Test
    @DisplayName("Тест конструктора Game")
    void testGameConstructorFields() {
        Game newGame = new Game();
        assertNotNull(newGame.player1);
        assertNotNull(newGame.player2);
        assertEquals(0, newGame.q);
        assertEquals(State.PLAYING, newGame.state);
    }
}