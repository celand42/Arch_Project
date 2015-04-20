import java.awt.*; 
import java.awt.event.*; 
import javax.swing.*;
import java.util.ArrayList;

public class ArchGUI extends JPanel
{
	private JButton pressme = new JButton("Press Me");
    private ArrayList<Object[][]> stages;
	
	public ArchGUI(ArrayList<Object[][]> s)        // the frame constructor
	{
		//super(new GridBagLayout());
		stages = s;
        
		JFrame frame = new JFrame("Architecture Pipelining");
		frame.setBounds(100, 100, 600, 400);
		
	
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
		{"RY", "-", "-", "-", "-", "-", "-", "-", "-"},
        };
        
        Object[][] data2 = {
        {"PC", "-", "-", "HELLO", "-", "-", "-", "-", "-"},
        {"IR", "-", "-", "-", "-", "-", "-", "-", "-"},
		{"RA", "-", "-", "-", "-", "-", "-", "-", "-"},
		{"RB", "-", "-", "-", "-", "-", "-", "-", "-"},
		{"RZ", "-", "-", "-", "-", "-", "-", "-", "-"},
		{"RY", "-", "-", "-", "-", "-", "-", "-", "-"},
        };
		
		//setLayout(new BorderLayout());
		
		 JTable table = new JTable(data, columnNames);
        //table.getModel().setValueAt("TEST", 3, 3);
        //table = new JTable(data2, columnNames);
		table.setShowGrid(false);
		table.setPreferredScrollableViewportSize(new Dimension(575,150));
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
		pressme.setMnemonic('P'); // associate hotkey to button
		pressme.setPreferredSize(new Dimension(100,100));
		//pressme.setBounds(50,50,50,50);
		
		add(scrollPane);
		add(pressme); pressme.requestFocus();
		frame.setVisible(true); // make frame visible
	}




}