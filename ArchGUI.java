import java.awt.*; 
import java.awt.event.*; 
import javax.swing.*;
import java.util.ArrayList;
import javax.swing.table.*;

public class ArchGUI extends JPanel implements ActionListener
{
    private JTable table;
    private JLabel currStage;
	private JButton nextStage = new JButton("Next Stage");
    private JButton prevStage = new JButton("Previous Stage");
    private ArrayList<Object[][]> stages;
    private int currentStage;
	
	public ArchGUI(ArrayList<Object[][]> s)        // the frame constructor
	{
		super(new FlowLayout(FlowLayout.LEFT));
		stages = s;
        
		JFrame frame = new JFrame("Architecture Pipelining");
		frame.setBounds(100, 100, 720, 480);
		
	
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
		table = new JTable(data, columnNames);
        updateTable();
        //table.getModel().setValueAt("TEST", 3, 3);
        //
		table.setShowGrid(false);
		table.setPreferredScrollableViewportSize(new Dimension(690,150));
        table.getTableHeader().setReorderingAllowed(false);
		//table.setFillsViewportHeight(true);
		table.setRowHeight(25);
		table.setEnabled(false);
		JScrollPane scrollPane = new JScrollPane(table);
		//scrollPane.setViewportBorder(null);
		//scrollPane.setBorder(BorderFactory.createEmptyBorder(100, 100, 100, 100));
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
        
        //System.out.println(stages.get(0)[1][2]);
        //table.getModel().setValueAt("TEST", 3, 3);
    
        for (int x = 0; x < 6; x++)
        {
            for (int y = 1; y < 9; y++)
            {
                model.setValueAt(stages.get(currentStage-1)[x][y], x, y);
                //arr[x][y]=matrix[x][y-1];
            }
        } 
    
    
    
    
    }




}