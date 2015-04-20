import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Collections;
import java.util.Comparator;


public class Pipeline
{
	private Object[][] matrix;                                  // Stores values for pipeline
	private ArrayList<Instruction> instructions;                // List of instructions
    private HashMap<String, String> registers;                  // Map of registers and their values
    private HashMap<String, Instruction> instrMap;              // Map of addresses and their instructions
    private HashMap<String, Integer> usedRegisters;             // Map of current destination registers (used for data deps) 
    private InterstageBuffer[] buffers;                         // Interstage buffers
    private ArrayList<Object[][]> stages;                       // Contains every pipeline stage
    private ArrayList<ArrayList<String[]>> allStageRegisters;   // Contains register values for every stage 
    
    private String PC, IR, RA, RB, RZ, RY;
    private int programCounter;
    private boolean endOfInstr;
	
	public Pipeline(ArrayList<Instruction> i, HashMap<String, String> m, HashMap<String, Instruction> im)
	{
		// 6 rows, 8 columns
		matrix = new Object[6][8];     // Temp stage matrix
        
        for (int x = 0; x < 6; x++)
        {
            for (int y = 0; y < 8; y++)
            {
                matrix[x][y] = "-";
            }
        } 
                
		instructions = i;
        registers = m;
        instrMap = im;
        usedRegisters = new HashMap<String, Integer>();
        buffers = new InterstageBuffer[5];
        
        for (int count = 0; count < 5; count++)
            buffers[count] = new InterstageBuffer();
        
        stages = new ArrayList<Object[][]>();
        allStageRegisters = new ArrayList<ArrayList<String[]>>();
        RA = RB = RY = RZ = IR = PC = "";
        programCounter = Integer.parseInt(instructions.get(0).getPC());
        endOfInstr = false;
        
        printInstructions();
        
        calculateStages();
        
        //getRegisters();
        
        
	}
	
    private void calculateStages()
    {
        if (instructions.size() == 0)
            return;
    
        Object[][] arr;
        
        for (int count = 0; count < 8; count++)
        {
            flow(5, count);
            //System.out.println(programCounter);
            
            
            stages.add(copyArray());
            allStageRegisters.add(getRegisters());
            //getRegisters();
            
            /*
            System.out.println("BUFFERS FOR STAGE " + (count+1));
            System.out.println("-----------------------------");
            System.out.println("BUFFER 1:");
            buffers[0].printBuffer();
            System.out.println("BUFFER 2:");
            buffers[1].printBuffer();
            System.out.println("BUFFER 3:");
            buffers[2].printBuffer();
            System.out.println("BUFFER 4:");
            buffers[3].printBuffer();
            System.out.println("BUFFER 5:");
            buffers[4].printBuffer();
            System.out.println("-----------------------------");
            System.out.println("");
            */
        
            programCounter += 4;
        }
        

    }
    
    
    public void flow(int bufferStage, int stage)
    {
        if (bufferStage < 0)
            return;
            
        InterstageBuffer buff;
        Instruction instr;
        String pc_tmp;
            
        // BUFFER 1    
        if (bufferStage == 0)     // Fetch
        {             
            buffers[0].setBuffer("", "", "", "", "", Integer.toString(programCounter), "", false);   
            matrix[0][stage] = Integer.toString(programCounter);
            
            if (instrMap.get(Integer.toString(programCounter)) == null) 
                buffers[0].setJunkAddress(true);
                
        }        
        // -------------------------------
        
        // BUFFER 2    
        else if (bufferStage == 1)      // Get Opcode
        {         
            if (buffers[0].isJunkAddress())
            {
                buffers[1].setBuffer("", "", "", "", "", buffers[0].getPC_Temp(), "", false);   
                buffers[1].setJunkAddress(true);
            }
            
            else if (!buffers[0].getPC_Temp().equals(""))
            {
                pc_tmp = buffers[0].getPC_Temp();
                
                if (instrMap.get(pc_tmp) != null)     
                {             
                    instr = instrMap.get(pc_tmp);
                    buffers[1].setBuffer("", "", "", "", instr.getOpcode(), pc_tmp, "", false);  
                    matrix[1][stage] = instr.getOpcode();       
                    System.out.println("USED " + instr.getDest());
                    usedRegisters.put(instr.getDest(), stage+1);
                }
                else
                    buffers[1].setBuffer("", "", "", "", "", pc_tmp, "", false);  

                  
            }
        }
        // -------------------------------
        
        // BUFFER 3
        else if (bufferStage == 2)      // Get Src and Dest registers
        {       
            if (buffers[1].isJunkAddress())
            {
                buffers[2].setBuffer("", "", "", "", "", buffers[1].getPC_Temp(), "", false);   
                buffers[2].setJunkAddress(true);
            }   
        
            else if (!buffers[1].getIR().equals(""))
            {
                pc_tmp = buffers[1].getPC_Temp();
                instr = instrMap.get(pc_tmp);
                buffers[2].setBuffer(instr.getSrcOne(), instr.getSrcTwo(), "", "", instr.getOpcode(), pc_tmp, instr.getDest(), instr.isImmediate());
                
                matrix[2][stage] = registers.get(instr.getSrcOne()); 
                
                if (instr.isImmediate())
                    matrix[3][stage] = "Junk";
                else
                    matrix[3][stage] = registers.get(instr.getSrcTwo());
            }     
        }
        // -------------------------------
        
        // BUFFER 4
        else if (bufferStage == 3)      // ALU operation and copy to RZ 
        {      
            if (buffers[2].isJunkAddress())
            {
                buffers[3].setBuffer("", "", "", "", "", buffers[2].getPC_Temp(), "", false);   
                buffers[3].setJunkAddress(true);
            }    
        
            else if (!buffers[2].getIR().equals(""))
            {
                // NEED TO ADD SUPPORT FOR DATA DEPS

                buff = buffers[2];
                int alu = 0;
                int ra = 0;
                int rb = 0;
                
                // Assign value to first ALU input
                if (usedRegisters.get(buff.getRA()) == null || usedRegisters.get(buff.getRA()) == stage)
                {
                    ra = Integer.parseInt(registers.get(buff.getRA()));  
                }
                

                // Assign value to second ALU input
                if (buff.isImmediate())
                    rb = Integer.parseInt(buff.getRB().substring(1));
                
                else if (usedRegisters.get(buff.getRB()) == null || usedRegisters.get(buff.getRA()) == stage)
                {
                    rb = Integer.parseInt(registers.get(buff.getRB()));
                }
                
                
                if (buff.getIR().equals("Add"))
                {
                    alu =  ra + rb;
                }
                
                else if (buff.getIR().equals("Subtract"))
                {
                    alu =  ra - rb;
                }
                
                else if (buff.getIR().equals("And"))
                {
                
                }
                
                else if (buff.getIR().equals("Or"))
                {
                
                }
                
                buffers[3].setBuffer(buff.getRA(), buff.getRB(), "", Integer.toString(alu), buff.getIR(), buff.getPC_Temp(), buff.getDest(), buff.isImmediate()); 
                
                matrix[4][stage] = Integer.toString(alu); 
            }     
        }
        // -------------------------------
        
        // BUFFER 5
        else if (bufferStage == 4)      // Copy to RY
        {                  
            if (buffers[3].isJunkAddress())
            {
                buffers[4].setBuffer("", "", "", "", "", buffers[3].getPC_Temp(), "", false);   
                buffers[4].setJunkAddress(true);
            }   
            
            else if (!buffers[3].getIR().equals(""))
            {
                buff = buffers[3];
                           
                buffers[4].setBuffer(buff.getRA(), buff.getRB(), buff.getRZ(), buff.getRZ(), buff.getIR(), buff.getPC_Temp(), buff.getDest(), buff.isImmediate());    
                
                matrix[5][stage] = buff.getRZ(); 
            }    
        }
        // -------------------------------
        
        // COPY BUFFER TO REGISTER
        else if (bufferStage == 5 && !buffers[4].getDest().equals(""))      // Copy to Destination Register
        {                  
            registers.put(buffers[4].getDest(), buffers[4].getRY());
            usedRegisters.put(buffers[4].getDest(), null);
        }
        // -------------------------------
    
        flow(bufferStage-1, stage);
    }
    
    
    
	public void printInstructions()
	{
		for (int count = 0; count < instructions.size(); count++)
		{
            System.out.print(instructions.get(count).getPC() + " ");
			System.out.print(instructions.get(count).getOpcode() + " ");
			System.out.print(instructions.get(count).getDest() + ", ");
			System.out.print(instructions.get(count).getSrcOne());
			
			if (!instructions.get(count).isShortInstr())
				System.out.print(", " + instructions.get(count).getSrcTwo());
			
			System.out.println();
			
		}
		
	}
    
    
    public void printMatrix()
    {
        for (int x = 0; x < 6; x++)
        {
            for (int y = 0; y < 8; y++)
            {
                System.out.print(matrix[x][y] + " ");
            }
            System.out.println();
        } 
    }
    
    
    public Object[][] copyArray()
    {   
        Object[][] arr = new Object[6][9];
        
        //for(int i=0; i<matrix.length; i++)
         //   for(int j=0; j<matrix[i].length; j++)
         //       arr[i][j]=matrix[i][j];
    
        arr[0][0] = "PC";
        arr[1][0] = "IR";
        arr[2][0] = "RA";
        arr[3][0] = "RB";
        arr[4][0] = "RZ";
        arr[5][0] = "RY";
    
        for (int x = 0; x < 6; x++)
        {
            for (int y = 1; y < 9; y++)
            {
                arr[x][y]=matrix[x][y-1];
            }
        } 
    
    /*for (int x = 0; x < 6; x++)
        {
            for (int y = 0; y < 8; y++)
            {
                System.out.print(arr[x][y] + " ");
            }
            System.out.println();
        }
    
    
    System.out.println();*/
        return arr;   
    }

    public ArrayList<Object[][]> getStages()
    {
        return stages;
    }
    
    public ArrayList<Instruction> getInstructions()
    {
        return instructions;    
    }
    
    public ArrayList<String[]> getRegisters()
    {
        ArrayList<String[]> arr = new ArrayList<String[]>();
        String[] reg;
        
        Iterator it = registers.entrySet().iterator();
        
        while (it.hasNext())
        {
            reg = new String[2];
            Map.Entry pair = (Map.Entry)it.next();
            //System.out.println(pair.getKey() + " = " + pair.getValue());
            reg[0] = pair.getKey().toString();
            reg[1] = pair.getValue().toString();
            arr.add(reg);     
        
        }
        
        
        Collections.sort(arr, new Comparator<String[]>() {
            @Override
            public int compare (String[] s1, String[] s2)
            {
                //System.out.println(s1[0] + " AND " + s2[0]);
                return s1[0].compareTo(s2[0]);
            }
        });
        
        return arr;
    }
    
    public ArrayList<ArrayList<String[]>> getAllRegisters()
    {
        return allStageRegisters;
    }
    
}
