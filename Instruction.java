public class Instruction
{
	private String Opcode;
	private String Dest;
	private String SrcOne;
	private String SrcTwo;
	private boolean Immediate;
	private boolean ShortInstr;
	
	public Instruction()
	{
		Opcode = Dest = SrcOne = SrcTwo = "";
		Immediate = ShortInstr = false;
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
	
	// ShortInstr
	public boolean isShortInstr()
	{
		return ShortInstr;
	}
	
	public void setShortInstr(boolean b)
	{
		ShortInstr =  b;
	}
	
	
	
	
	
}