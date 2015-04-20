public class InterstageBuffer
{
	private String RA;
	private String RB;
	private String RY;
	private String RZ;
	private String IR;
	private String PC_Temp;
    private String Dest;
    private boolean Immediate;
    private boolean JunkAddress;
	
	public InterstageBuffer()
	{
		RA = RB = RY = RZ = IR = PC_Temp = Dest = "";
        Immediate = JunkAddress = false;
	}
    
    public void setBuffer(String a, String b, String y, String z, String i, String p, String d, boolean imm)
    {
        RA = a;
        RB = b;
        RY = y;
        RZ = z;
        IR = i;
        PC_Temp = p;
        Dest = d;
        Immediate = imm;
    }
    
    public void printBuffer()
    {
        System.out.println("RA: " + getRA());
        System.out.println("RB: " + getRB());
        System.out.println("RY: " + getRY());
        System.out.println("RZ: " + getRZ());
        System.out.println("IR: " + getIR());
        System.out.println("PC_Temp: " + getPC_Temp());
        System.out.println("Dest: " + getDest());
        System.out.println("Immediate: " + isImmediate());
        
        System.out.println();

    }
	
	// RA
	public String getRA()
	{
		return RA;
	}
	
	public void setRA(String val)
	{
		RA = val;
	}
	
	// RB
	public String getRB()
	{
		return RB;
	}
	
	public void setRB(String val)
	{
		RB = val;
	}
	
	// RY
	public String getRY()
	{
		return RY;
	}
	
	public void setRY(String val)
	{
		RY = val;
	}
	
	// RZ
	public String getRZ()
	{
		return RZ;
	}
	
	public void setRZ(String val)
	{
		RZ = val;
	}
	
	// IR
	public String getIR()
	{
		return IR;
	}
	
	public void setIR(String val)
	{
		IR = val;
	}
	
	// PC_Temp
	public String getPC_Temp()
	{
		return PC_Temp;
	}
	
	public void setPC_Temp(String val)
	{
		PC_Temp = val;
	}
    
    // Dest
	public String getDest()
	{
		return Dest;
	}
	
	public void setDest(String val)
	{
		Dest = val;
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
	
    // Junk Address
    public boolean isJunkAddress()
    {
        return JunkAddress;
    }
    
    public void setJunkAddress(boolean b)
    {
        JunkAddress = b;
    }
    
    
	
	
	
	
}