import java.util.Scanner;
import java.io.FileReader;
import java.io.File;
import java.io.BufferedReader;
import java.util.ArrayList;


public class ArchProject
{
	
	public static void main(String[] args) throws Exception
	{
		String fileName;
		BufferedReader br;
		String line;
		String[] lineArr;
		ArrayList<Instruction> instructions = new ArrayList<Instruction>();
		Pipeline pipe;
		
		// Attempts to read in file at command line
		try
		{
			fileName = args[0];
		}
		
		catch (Exception e)
		{
			Scanner input = new Scanner( System.in );
			System.out.print("Enter file name of instructions: ");
			fileName = input.next();
		}

		// Attempts to open connection to file
		try
		{
			File file = new File(fileName);
			br = new BufferedReader(new FileReader(file));
		}
		
		catch (Exception e)
		{
			System.out.println("Invalid file name.");
			return;
		}
		
		
		Instruction instr;
		
        // Iterates through file
		while ((line = br.readLine()) != null) 
		{
            //System.out.println(line);
			
			lineArr = line.split("(?<!,) |,");
			
			for (int count = 0; count < lineArr.length; count++)
			{
				lineArr[count] = lineArr[count].replaceAll("\\s+","");
				//System.out.println(lineArr[count]);
			}
			
			instr = new Instruction();
			
			instr.setOpcode(lineArr[0]);
			instr.setDest(lineArr[1]);
			instr.setSrcOne(lineArr[2]);
			
			if (lineArr.length > 3)
			{
				instr.setSrcTwo(lineArr[3]);
				instr.setShortInstr(false);
				
				if (lineArr[3].charAt(0) == '#')
				{
					//System.out.println("YUS " + lineArr[3]);
					instr.setImmediate(true);
				}	
				else
				{
					//System.out.println("NAW " + lineArr[3]);
					instr.setImmediate(false);
				}	
			}
			
			else
			{
				instr.setShortInstr(true);
				
				if (lineArr[2].charAt(0) == '#')
				{
					//System.out.println("YUS " + lineArr[2]);
					instr.setImmediate(true);
				}	
				else
				{
					//System.out.println("NAW " + lineArr[2]);
					instr.setImmediate(false);
				}	
			}
			
			instructions.add(instr);
        }   
		
		pipe = new Pipeline(instructions);
		pipe.printInstructions();
		
		new ArchGUI();
		
		
		
		
		
		
		
		System.out.println("End");
		
		
		
		
		
	
	
	}





}