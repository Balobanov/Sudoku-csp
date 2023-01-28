package org.example.csp;

import java.util.List;
import java.util.Map;
import org.example.sudoku.Sudoku;
import org.example.sudoku.Sudoku.SudokuCell;

public class SudokuSearchConstraint extends Constraint<SudokuCell, Integer> {

	public SudokuSearchConstraint(List<SudokuCell> words) {
		super(words);
	}

	@Override
	public boolean satisfied(Map<SudokuCell, Integer> assignment) {
		Sudoku t = new Sudoku();
		for (SudokuCell sudokuCell : assignment.keySet()) {
			Integer number = assignment.get(sudokuCell);

			if(!t.validate(number, sudokuCell.getR(), sudokuCell.getC())) {
				return false;
			}

			t.mark(sudokuCell.getR(), sudokuCell.getC(), number);
		}

		return true;
	}

}
