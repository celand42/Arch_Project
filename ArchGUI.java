import java.awt.*; 
import java.awt.event.*; 
import javax.swing.*;
import java.util.ArrayList;
import javax.swing.table.*;

public class ArchGUI extends JPanel implements ActionListener
{
    private JTable table;
    private JLabel currStage;
    private JLabel currRegisters;
    private JLabel currInstr;
	private JButton nextStage = new JButton("Next Stage");
    private JButton prevStage = new JButton("Previous Stage");
    private ArrayList<Object[][]> stages;
    private ArrayList<Instruction> instructions;
    private ArrayList<ArrayList<String[]>> allRegisters;
    private int currentStage;
	
	public ArchGUI(ArrayList<Object[][]> s, ArrayList<Instruction> i, ArrayList<ArrayList<String[]>> r)        // the frame constructor
	{
		super(new FlowLayout(FlowLayout.LEFT, 25, 10));
		//super(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        //setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        
        stages = s;
        instructions = i;
        allRegisters = r;
        
		JFrame frame = new JFrame("Architecture Pipelining");
		frame.setIconImage(new ImageIcon("pipe.png").getImage());
		frame.setBounds(100, 100, 750, 400);
		
	
		String[] columnNames = {"",
								"1",
                                "2",
                                "3",
                                "4",
                                "5",
								"6",
                                "7",
                                "8"};
 
        Object[][] data = { 
            {"PC", "-", "-", "-", "-", "-", "-", "-", "-"},
            {"IR", "-", "-", "-", "-", "-", "-", "-", "-"},
            {"RA", "-", "-", "-", "-", "-", "-", "-", "-"},
            {"RB", "-", "-", "-", "-", "-", "-", "-", "-"},
            {"RZ", "-", "-", "-", "-", "-", "-", "-", "-"},
            {"RY", "-", "-", "-", "-", "-", "-", "-", "-"}};
		
        currentStage = 1;
		
        currStage = new JLabel("Stage " + currentStage + ":", SwingConstants.RIGHT);        
        currStage.setFont (currStage.getFont().deriveFont (16.0f));
        
        
        
        String instrStr = "<html><body>";
        
        for (int count = 0; count < instructions.size(); count++)
            instrStr += instructions.get(count).toString() + "<br>";
            
        instrStr += "</body></html>";
        
        currInstr = new JLabel(instrStr);        
        currInstr.setFont (currStage.getFont().deriveFont (12.0f));
        
        currRegisters = new JLabel("");        
        currRegisters.setFont (currStage.getFont().deriveFont (12.0f));
        
		table = new JTable(data, columnNames);
        updateTable();

		table.setShowGrid(false);
		table.setPreferredScrollableViewportSize(new Dimension(690,150));
        table.getTableHeader().setReorderingAllowed(false);
		table.setRowHeight(25);
		table.setEnabled(false);
		JScrollPane scrollPane = new JScrollPane(table);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container con = frame.getContentPane(); // inherit main frame
		con.add(this);    // JPanel containers default to FlowLayout
        
        prevStage.setActionCommand("Prev"); // associate hotkey to button
        prevStage.addActionListener(this);
		prevStage.setPreferredSize(new Dimension(150,50));
        prevStage.setFocusPainted(false);
        prevStage.setFont(prevStage.getFont().deriveFont (14.0f));
        
		nextStage.setActionCommand("Next"); // associate hotkey to button
        nextStage.addActionListener(this);
		nextStage.setPreferredSize(new Dimension(150,50));
        nextStage.setFocusPainted(false);
        nextStage.setFont(nextStage.getFont().deriveFont (14.0f));
		//pressme.setBounds(50,50,50,50);
		add(currStage);
		add(scrollPane);
        add(currInstr);
        add(currRegisters);
		add(prevStage);
        add(nextStage);          
		frame.setVisible(true); // make frame visible
        
        
	}
    
    public void actionPerformed(ActionEvent ae) 
    {
        if (ae.getActionCommand().equals("Next"))
        {
            if (currentStage < 8)
            {                
                currentStage++;
                currStage.setText("Stage " + currentStage + ":");
                updateTable();     
            }
        }
        
        else if (ae.getActionCommand().equals("Prev"))
        {
            if (currentStage > 1)
            {                
                currentStage--;              
                currStage.setText("Stage " + currentStage + ":");
                updateTable();
            }
        }
    }
    
    public void updateTable()
    {
        TableModel model = table.getModel();
        Object[][] newStage = new Object[6][9];
        
        String str = "<html><body>";
        
        for (int count = 0; count < allRegisters.get(0).size(); count++)
        {
            str += allRegisters.get(currentStage-1).get(count)[0] + ": " + allRegisters.get(currentStage-1).get(count)[1] + "<br>";
        }
    
        str += "</body></html>";
    
        currRegisters.setText(str);
    
        for (int x = 0; x < 6; x++)
        {
            for (int y = 1; y < 9; y++)
            {
                model.setValueAt(stages.get(currentStage-1)[x][y], x, y);
            }
        } 
    
    }




}