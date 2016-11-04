import javax.swing.*;

public class MineButton extends JButton 
{

	boolean isMine;
	int mineNumber;
	boolean exposed;

	public MineButton() 
	{
		super(" ");
		isMine = false;
		exposed = false;
		mineNumber = 0;
	}

	public boolean isMine() 
	{
		return isMine;
	}

	public void setMine(boolean isMine) 
	{
		this.isMine = isMine;
	}

	public void incrementMineCount()
	{
		mineNumber++;
	}
}
