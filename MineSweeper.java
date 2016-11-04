import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MineSweeper extends JFrame {
	static final int LEFTCLICK = 1;
	static final int RIGHTCLICK = 3;
	static final int BOARDSIZE = 10;
	static int count;
	int openBlocks = 100;
	char[][] board;
	MineButton[][] buttons;

	JTextField mine;
	JLabel mineCount;

	public MineSweeper() {

		setTitle("Mine Sweeper");
		setSize(BOARDSIZE * 70, BOARDSIZE * 70);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		board = new char[BOARDSIZE + 2][BOARDSIZE + 2];
		buttons = new MineButton[BOARDSIZE + 2][BOARDSIZE + 2];

		JPanel boardPanel = new JPanel();
		boardPanel.setLayout(new GridLayout(BOARDSIZE, BOARDSIZE));

		for (int row = 0; row < BOARDSIZE + 2; row++) {
			for (int col = 0; col < BOARDSIZE + 2; col++) {
				board[row][col] = ' ';
				buttons[row][col] = new MineButton();
				buttons[row][col].setBackground(Color.WHITE);

				if (row >= 1 && row <= BOARDSIZE && col >= 1
						&& col <= BOARDSIZE) {
					boardPanel.add(buttons[row][col]);
					buttons[row][col]
							.addMouseListener(new MouseClick(row, col));
				}
			}
		}

		add(boardPanel, BorderLayout.CENTER);

		// mineCount = new JLabel("Unmarked Mines:"+count)

		// mine = new JTextField(10);

		// JPanel displayPanel = new JPanel();
		JPanel controlPanel = new JPanel();

		// displayPanel.add(mineCount);
		// controlPanel.add(mine);
		add(controlPanel, BorderLayout.SOUTH);
		// add(displayPanel, BorderLayout.NORTH);

		newGame();
	}

	void newGame()

	{
		for (int r = 0; r < BOARDSIZE + 2; r++) {
			for (int c = 0; c < BOARDSIZE + 2; c++) {
				buttons[r][c].setText(" ");
				buttons[r][c].setBackground(Color.WHITE);
				board[r][c] = ' ';
				buttons[r][c].mineNumber = 0;
				openBlocks = 100 - count;
			}
		}

		GenerateMines();

		for (int r = 0; r < BOARDSIZE + 2; r++) {
			for (int c = 0; c < BOARDSIZE + 2; c++) {
				if (r >= 1 && r <= BOARDSIZE && c >= 1 && c <= BOARDSIZE) {
					if (!buttons[r][c].isMine) {
						buttons[r][c].setText(" ");// +
													// buttons[r][c].mineNumber);
					} else {
						buttons[r][c].setText(" ");
					}
				}
			}
		}
	}

	void GenerateMines() {

		for (int r = 1; r <= BOARDSIZE; r++) {
			for (int c = 1; c <= BOARDSIZE; c++) {
				if (count < 15 && Math.random() < 0.15) {
					buttons[r][c - 1].incrementMineCount();
					buttons[r][c + 1].incrementMineCount();

					buttons[r - 1][c].incrementMineCount();
					buttons[r + 1][c].incrementMineCount();

					buttons[r - 1][c + 1].incrementMineCount();
					buttons[r - 1][c - 1].incrementMineCount();

					buttons[r + 1][c + 1].incrementMineCount();
					buttons[r + 1][c - 1].incrementMineCount();

					buttons[r][c].setMine(true);
					count++;
				}

				else {
					buttons[r][c].setMine(false);
				}
			}

			openBlocks = 100 - count;

			JPanel displayPanel = new JPanel();

			mineCount = new JLabel("Unmarked Mines: " + count);
			displayPanel.add(mineCount);
			add(displayPanel, BorderLayout.NORTH);
		}
	}

	private class MouseClick implements MouseListener {
		int r, c;

		public MouseClick(int row, int column) {
			r = row;
			c = column;
		}

		public void mousePressed(MouseEvent e) {
			if (e.getButton() == LEFTCLICK) {
				buttons[r][c].setText(" ");
				buttons[r][c].setBackground(Color.CYAN);
				board[r][c] = ' ';

				--openBlocks;

				System.out.println(openBlocks);

				if (openBlocks == 0) {
					JOptionPane.showMessageDialog(null, "You Win");

				}

				if (buttons[r][c].isMine) {
					buttons[r][c].setText("*M*");
					buttons[r][c].setBackground(Color.RED);
					board[r][c] = ' ';
					mineCount.setText("You stepped on a mine!");
					JOptionPane.showMessageDialog(null,
							"        You stepped on a mine! GAME OVER!");
				} else
					ShowValues(r, c);
			}

			else if (e.getButton() == RIGHTCLICK) {
				buttons[r][c].setText("Flag");
				buttons[r][c].setBackground(Color.YELLOW);
				board[r][c] = 'F';

				if (buttons[r][c].isMine) {
					--count;
					mineCount.setText("Unmarked Mines: " + count);
					// buttons[r][c].setText("MINE");
					// buttons[r][c].setBackground(Color.ORANGE);
					// board[r][c] = 'F';
				}
				if (count == 0) {
					mineCount.setText("All mines have been marked!");
				}
				if (count < 0) {
					mineCount.setText("All mines have been marked!");
				}
			}
		}

		public void mouseClicked(MouseEvent arg0) {

		}

		public void mouseEntered(MouseEvent e) {

		}

		public void mouseExited(MouseEvent e) {

		}

		public void mouseReleased(MouseEvent e) {

		}

		private void ShowValues(int row, int col) {
			if (row >= 1 && row <= BOARDSIZE && col >= 1 && col <= BOARDSIZE
					&& !buttons[row][col].isMine && !buttons[row][col].exposed) {
				buttons[row][col].setText(" " + buttons[row][col].mineNumber);
				buttons[r][c].setBackground(Color.CYAN);
				buttons[row][col].exposed = true;

				if (buttons[row][col].mineNumber == 0)
					for (int x = -1; x <= 1; x++) {
						for (int y = -1; y <= 1; y++) {
							ShowValues(row + x, col + y);
						}
					}
			}

		}
	}
}
