import java.util.ArrayList;

public class Pipeline
{
	private int[][] matrix;
	private ArrayList<Instruction> instructions;
	
	public Pipeline(ArrayList<Instruction> i)
	{
		// 6 rows, 8 columns
		matrix = new int[6][8];
		instructions = i;
	}
	
	public void printInstructions()
	{
		for (int count = 0; count < instructions.size(); count++)
		{
			System.out.print(instructions.get(count).getOpcode() + " ");
			System.out.print(instructions.get(count).getDest() + ", ");
			System.out.print(instructions.get(count).getSrcOne());
			
			if (!instructions.get(count).isShortInstr())
				System.out.print(", " + instructions.get(count).getSrcTwo());
			
			System.out.println();
			
		}
		
	}






}