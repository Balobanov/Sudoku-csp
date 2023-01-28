package org.example.sudoku;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.function.Supplier;

public class Sudoku {
    private static final int SIZE = 9;

    private int columns;
    private int rows;
    private int grid[][];
    private double sparseness = 0.5;

    public Sudoku() {
        this.rows = SIZE;
        this.columns = SIZE;
        this.grid = new int[rows][columns];
    }

    public Sudoku(int initGrid[][]) {
        this.rows = SIZE;
        this.columns = SIZE;
        this.grid = initGrid;
    }

    public int[][] getGrid() {
        return grid;
    }

    public void generate() {
        Random rand = new Random();

        Supplier<Integer> generate = () -> rand.nextInt((9 - 1) + 1) + 1;

        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                if (Math.random() < sparseness) {
                    int valueToInsert = generate.get();

                    while (!validate(valueToInsert, row, column)) {
                        valueToInsert = generate.get();
                    }

                    grid[row][column] = valueToInsert;
                }
            }
        }
    }

    public boolean validate(int valueToInsert, int row, int column) {

        // validate full row
        if (!unUsedInRow(row, valueToInsert)) {
            return false;
        }

        // validate full column
        if (!unUsedInCol(column, valueToInsert)) {
            return false;
        }

        if (!unUsedInBox(row, column, valueToInsert)) {
            return false;
        }

        return true;
    }

    public List<SudokuCell> toList() {
        LinkedList<SudokuCell> integers = new LinkedList<>();

        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                integers.add(new SudokuCell(row, column));
            }
        }

        return integers;
    }

    public Map<SudokuCell, Integer> toAssignment() {
        HashMap<SudokuCell, Integer> sudokuCellIntegerHashMap = new HashMap<>();

        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {

                if (grid[row][column] != 0) {
                    sudokuCellIntegerHashMap.put(new SudokuCell(row, column), grid[row][column]);
                }
            }
        }

        return sudokuCellIntegerHashMap;
    }

    public void mark(Map<SudokuCell, Integer> result) {
        for (SudokuCell sudokuCell : result.keySet()) {
            Integer value = result.get(sudokuCell);
            grid[sudokuCell.r][sudokuCell.c] = value;
        }
    }

    public void mark(int r, int c, int v) {
        grid[r][c] = v;
    }

    public static class SudokuCell {
        private int r;
        private int c;

        public SudokuCell() {
        }

        public SudokuCell(int r, int c) {
            this.r = r;
            this.c = c;
        }

        public int getR() {
            return r;
        }

        public void setR(int r) {
            this.r = r;
        }

        public int getC() {
            return c;
        }

        public void setC(int c) {
            this.c = c;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            SudokuCell that = (SudokuCell) o;
            return r == that.r && c == that.c;
        }

        @Override
        public int hashCode() {
            return Objects.hash(r, c);
        }
    }

    public boolean unUsedInBox(int rows, int columns, int num) {

        int rowStart = rows - rows % 3;
        int colStart = columns - columns % 3;

        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (grid[rowStart + i][colStart + j] == num)
                    return false;

        return true;
    }

    // check in the row for existence
    public boolean unUsedInRow(int row, int num) {
        for (int j = 0; j < SIZE; j++)
            if (grid[row][j] == num)
                return false;
        return true;
    }

    // check in the row for existence
    public boolean unUsedInCol(int column, int num) {
        for (int i = 0; i < SIZE; i++)
            if (grid[i][column] == num)
                return false;
        return true;
    }

    public void printSudoku() {
        String s1 = "   ";
        String s2 = "  ";
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {

                int value = grid[row][column];
                System.out.printf(String.format("%s%s", value == 0 ? "*" : value, value < 10 ? s1 : s2));
            }

            System.out.println();
        }
    }
}
