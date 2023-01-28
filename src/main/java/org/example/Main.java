package org.example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.example.csp.CSP;
import org.example.csp.SudokuSearchConstraint;
import org.example.sudoku.Sudoku;
import org.example.sudoku.Sudoku.SudokuCell;

public class Main {
    public static void main(String[] args) {

        int initSudoku[][] = {
                {0, 0, 0, 2, 0, 0, 0, 3, 8},
                {2, 1, 8, 0, 3, 9, 0, 4, 7},
                {4, 9, 0, 8, 6, 7, 0, 5, 1},

                {9, 3, 0, 0, 0, 0, 7, 0, 2},
                {0, 8, 5, 0, 7, 0, 4, 0, 0},
                {0, 0, 0, 6, 0, 0, 0, 0, 9},

                {5, 0, 0, 0, 0, 0, 8, 0, 0},
                {0, 0, 0, 0, 9, 5, 0, 0, 4},
                {0, 2, 9, 4, 0, 6, 0, 0, 0},
        };

        int evil[][] = {
                {8, 1, 0, 0, 2, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 5, 0},
                {6, 0, 0, 1, 0, 0, 0, 3, 8},

                {0, 0, 4, 0, 0, 9, 0, 0, 0},
                {3, 0, 0, 2, 0, 0, 0, 1, 6},
                {0, 0, 0, 0, 0, 0, 7, 0, 0},

                {0, 0, 0, 8, 0, 0, 5, 0, 0},
                {7, 0, 0, 0, 0, 0, 2, 0, 0},
                {0, 0, 3, 0, 4, 0, 0, 8, 7},
        };

        int zero[][] = {
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},

                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},

                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
        };

        Sudoku sudoku = new Sudoku(zero);
        sudoku.printSudoku();

        List<SudokuCell> variables = sudoku.toList();

        Map<SudokuCell, List<Integer>> domains = new HashMap<>();
        for (SudokuCell i : variables) {
            domains.put(i, List.of(1, 2, 3, 4, 5, 6, 7, 8, 9));
        }

        CSP<SudokuCell, Integer> csp = new CSP<>(variables, domains);
        csp.addConstraint(new SudokuSearchConstraint(variables));

        Map<SudokuCell, Integer> initialAssignments = sudoku.toAssignment();
        Map<SudokuCell, Integer> result = csp.backtrackingSearch(initialAssignments);

        if (result == null) {
            System.out.println("No result");
        } else {
            sudoku.mark(result);
            System.out.println();
            sudoku.printSudoku();
        }

    }
}