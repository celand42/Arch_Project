import java.util.Scanner;
import java.io.FileReader;
import java.io.File;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;


public class ArchProject
{
	
	public static void main(String[] args)
	{
		String fileName;		
		
		ArrayList<Instruction> instructions;
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

        try
        {
            pipe = readFile(fileName);
            new ArchGUI(pipe.getStages(), pipe.getInstructions(), pipe.getAllRegisters());
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            return;
        }
        
        //if (instructions == null)
        //    return;
		
		//pipe = new Pipeline(instructions);
		//pipe.printInstructions();
		
		
	
		System.out.println("End");

	}
    
    /*
    * Reads file and creates list of instructions
    */
    public static Pipeline readFile(String fileName) throws Exception
    {
        BufferedReader br;
        String line;
        String[] lineArr;
        ArrayList<Instruction> instructions = new ArrayList<Instruction>();
        HashMap<String,String> map = new HashMap<String, String>();
        HashMap<String,Instruction> instrMap = new HashMap<String, Instruction>();
        
        // Attempts to open connection to file
        File file = new File(fileName);
        br = new BufferedReader(new FileReader(file));
       
        line = br.readLine();
        lineArr = line.split("(?<!,) |,");
       
        for (int count = 0; count < lineArr.length; count+=2)
        {
            lineArr[count] = lineArr[count].replaceAll("\\s+","");  // Remove whitespace
            
            map.put(lineArr[count], lineArr[count+1]);
            //System.out.println(map.get(lineArr[count]));       
        }
       
        Instruction instr;
        
        // Iterates through file
        while ((line = br.readLine()) != null) 
        {
            //System.out.println(line);
			line.trim();
		    
			if (line.isEmpty())
				continue;
		   
            lineArr = line.split("(?<!,) |,");
			
            for (int count = 0; count < lineArr.length; count++)
            {
                lineArr[count] = lineArr[count].replaceAll("\\s+","");
                //System.out.println(lineArr[count]);
            }
            
            instr = new Instruction();
            
            instr.setPC(lineArr[0]);
            instr.setOpcode(lineArr[1]);
            instr.setDest(lineArr[2]);
            instr.setSrcOne(lineArr[3]);
            instr.setSrcTwo(lineArr[4]);
         
            if (lineArr[4].charAt(0) == '#')
            {
                instr.setImmediate(true);
            }	
            else
            {
                instr.setImmediate(false);
            }	
            
                        
            instructions.add(instr);
            instrMap.put(instr.getPC(), instr);
        }   
    
        return new Pipeline(instructions, map, instrMap);
    
    }





}