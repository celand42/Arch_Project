public class Instruction
{
    private String PC;
	private String Opcode;
	private String Dest;
	private String SrcOne;
	private String SrcTwo;
	private boolean Immediate;
	
	public Instruction()
	{
		PC = Opcode = Dest = SrcOne = SrcTwo = "";
		Immediate = false;
	}
	
    public void printInstr()
    {
            System.out.print(getPC() + " ");
			System.out.print(getOpcode() + " ");
			System.out.print(getDest() + ", ");
			System.out.print(getSrcOne());
            System.out.print(", " + getSrcTwo());		
			System.out.println();
    }
    
    public String toString()
    {
        String str = "";
        
        str += getPC() + " ";
        str += getOpcode() + " ";
        str += getDest() + ", ";
        str += getSrcOne() + ", ";
        str += getSrcTwo();    
        
        return str;
    }
    
    // PC
	public String getPC()
	{
		return PC;
	}
	
	public void setPC(String val)
	{
		PC =  val;
	}
    
	// Opcode
	public String getOpcode()
	{
		return Opcode;
	}
	
	public void setOpcode(String val)
	{
		Opcode =  val;
	}
	
	// Dest
	public String getDest()
	{
		return Dest;
	}
	
	public void setDest(String val)
	{
		Dest =  val;
	}
	
	// SrcOne
	public String getSrcOne()
	{
		return SrcOne;
	}
	
	public void setSrcOne(String val)
	{
		SrcOne =  val;
	}
	
	// SrcTwo
	public String getSrcTwo()
	{
		return SrcTwo;
	}
	
	public void setSrcTwo(String val)
	{
		SrcTwo =  val;
	}
	
	// Immediate
	public boolean isImmediate()
	{
		return Immediate;
	}
	
	public void setImmediate(boolean b)
	{
		Immediate =  b;
	}
		
	
	
}