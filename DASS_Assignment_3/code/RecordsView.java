/* RecordsView.java 
 *
 *  Version
 *  $Id$
 * 
 *  Revisions:
 * 		$Log: NewPatronView.java,v $
 * 		Revision 1.3  2003/02/02 16:29:52  ???
 * 		Added ControlDeskEvent and ControlDeskObserver. Updated Queue to allow access to Vector so that contents could be viewed without destroying. Implemented observer model for most of ControlDesk.
 * 		
 * 
 */

/*
  Class for GUI components need to view records 

 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

import java.util.*;

public class RecordsView implements ActionListener, ListSelectionListener {
    
    private Vector records_top, records_last, records_list,records_recent,bowlerDB;
    private JList TopRecords, LeastRecords,RecentRecords,allBowlers;
    private JFrame win;
    private String selectedNick="???", selectedBowler;
    
    
    // private String Sort_func(Score sc1, Score sc2)
    // {
    //     return sc1.getScore() - ;
    // }


    public RecordsView()
    {
        // selectedNick = "???";    
        JPanel overallBestRecordsPanel = new JPanel();
        overallBestRecordsPanel.setLayout(new FlowLayout());
        overallBestRecordsPanel.setBorder(new TitledBorder("Overall Best"));

        JPanel overallLeastRecordsPanel = new JPanel();
        overallLeastRecordsPanel.setLayout(new FlowLayout());
        overallLeastRecordsPanel.setBorder(new TitledBorder("Overall Least"));

        JPanel overallRecentRecordsPanel = new JPanel();
        overallRecentRecordsPanel.setLayout(new FlowLayout());
        overallRecentRecordsPanel.setBorder(new TitledBorder("Overall Recent"));

        win = new JFrame("Records");
		win.getContentPane().setLayout(new BorderLayout());
		((JPanel) win.getContentPane()).setOpaque(false);

		JPanel colPanel = new JPanel();
        colPanel.setLayout(new GridLayout(1, 3));
        
        JPanel BowlerPane = new JPanel();
		BowlerPane.setLayout(new FlowLayout());
		BowlerPane.setBorder(new TitledBorder("Bowler Database"));



        records_top = new Vector();
        records_last = new Vector();
        records_recent = new Vector();
		Vector empty = new Vector();
		empty.add("(Empty)");


        TopRecords = new JList(records_top);
        LeastRecords = new JList(records_last);
        RecentRecords = new JList(records_recent);
        allBowlers = new JList(bowlerDB);  

        get_data();
        // try
        // {
            // bowlerDB = new Vector(BowlerFile.getBowlers());

         			// bowlerDB = new Vector(BowlerFile.getBowlers());

            // records_list = ScoreHistoryFile.getScores(selectedNick);
            // System.out.println(records_list);
            // System.out.println();

            // for(int i=0;i<5;i++)
            // {
            //     records_recent.add(((Score)records_list.get(records_list.size()-1-i)).toSpaceString());
            // }
            
            // ScoreHistoryFile sh = new ScoreHistoryFile();
            // Comparator<Score> compare = sh.new comparision();
            // Collections.sort(records_list, compare);
            
            // System.out.println(records_list);

            // for(int i=4;i>=0;i--)
            // {
            //     Score top = (Score)records_list.get(i);
            //     Score last = (Score)records_list.get(records_list.size()-1-i); 
            //     records_top.add(top.toSpaceString());
            //     records_last.add(last.toSpaceString());   

            // //     records_top.add("a\tb");
            //     // records_last.add("a\tb");   
            // }
            // System.out.println();
            // System.out.println(records_top);

            // System.out.println();
            // System.out.println(records_last);   // records_list = ScoreHistoryFile.getScores(selectedNick);
            // System.out.println(records_list);
            // System.out.println();

            // for(int i=0;i<5;i++)
            // {
            //     records_recent.add(((Score)records_list.get(records_list.size()-1-i)).toSpaceString());
            // }
            
            // ScoreHistoryFile sh = new ScoreHistoryFile();
            // Comparator<Score> compare = sh.new comparision();
            // Collections.sort(records_list, compare);
            
            // System.out.println(records_list);

            // for(int i=4;i>=0;i--)
            // {
            //     Score top = (Score)records_list.get(i);
            //     Score last = (Score)records_list.get(records_list.size()-1-i); 
            //     records_top.add(top.toSpaceString());
            //     records_last.add(last.toSpaceString());   

            // //     records_top.add("a\tb");
            //     // records_last.add("a\tb");   
            // }
            // System.out.println();
            // System.out.println(records_top);

            // System.out.println();
            // System.out.println(records_last);

        // } 



        allBowlers.setVisibleRowCount(8);
		allBowlers.setFixedCellWidth(120);
		JScrollPane bowlerPane = new JScrollPane(allBowlers);
		bowlerPane.setVerticalScrollBarPolicy(
			ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		allBowlers.addListSelectionListener(this);
		BowlerPane.add(bowlerPane);


        TopRecords.setFixedCellWidth(200);
		JScrollPane BestRecordPane = new JScrollPane(TopRecords);
		BestRecordPane.setVerticalScrollBarPolicy(
			ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		TopRecords.addListSelectionListener(this);
		overallBestRecordsPanel.add(BestRecordPane);


        LeastRecords.setFixedCellWidth(200);
		JScrollPane LeastRecordPane = new JScrollPane(LeastRecords);
		LeastRecordPane.setVerticalScrollBarPolicy(
			ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		LeastRecords.addListSelectionListener(this);
		overallLeastRecordsPanel.add(LeastRecordPane);


        RecentRecords.setFixedCellWidth(200);
		JScrollPane RecentRecordPane = new JScrollPane(RecentRecords);
		RecentRecordPane.setVerticalScrollBarPolicy(
			ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		RecentRecords.addListSelectionListener(this);
		overallRecentRecordsPanel.add(RecentRecordPane);


        colPanel.add(overallBestRecordsPanel);
        colPanel.add(overallLeastRecordsPanel);
        colPanel.add(overallRecentRecordsPanel);
        colPanel.add(bowlerPane);

        win.getContentPane().add("Center", colPanel);

		win.pack();

		// Center Window on Screen
		Dimension screenSize = (Toolkit.getDefaultToolkit()).getScreenSize();
		win.setLocation(
			((screenSize.width) / 2) - ((win.getSize().width) / 2),
			((screenSize.height) / 2) - ((win.getSize().height) / 2));
		win.setVisible(true);

    }

    private void get_data()
    {
        try
        {
            bowlerDB = new Vector(BowlerFile.getBowlers());
            bowlerDB.add("Overall records");

            // records_list.removeAllElements();
            // records_last.removeAllElements();
            // records_recent.removeAllElements();
            // records_top.removeAllElements();

            records_list = ScoreHistoryFile.getScores(selectedNick);
            // System.out.println(records_list);
            // System.out.println();

            for(int i=0;i<5;i++)
            {
                records_recent.add(((Score)records_list.get(records_list.size()-1-i)).toSpaceString());
            }
            
            ScoreHistoryFile sh = new ScoreHistoryFile();
            Comparator<Score> compare = sh.new comparision();
            Collections.sort(records_list, compare);
            
            // System.out.println(records_list);

            for(int i=0;i<5;i++)
            {
                Score top = (Score)records_list.get(i);
                Score last = (Score)records_list.get(records_list.size()-1-i); 
                records_top.add(top.toSpaceString());
                records_last.add(last.toSpaceString());   

            //     records_top.add("a\tb");
                // records_last.add("a\tb");   
            }
            // System.out.println();
            // System.out.println(records_top);

            // System.out.println();
            // System.out.println(records_last);
        }
        catch(Exception e)
        {
            System.err.println("File Error in Records view");
        }
        // TopRecords.removeAll();
        // LeastRecords.removeAll();
        // RecentRecords.removeAll();
        // allBowlers.removeAll();


        TopRecords.setListData(records_top);
        LeastRecords.setListData(records_last);
        RecentRecords.setListData(records_recent);
        allBowlers.setListData(bowlerDB); 
    }

    @Override
	public void actionPerformed(ActionEvent e) {


    }
    
    @Override
	public void valueChanged(ListSelectionEvent e) {
		if (e.getSource().equals(allBowlers)) {
            selectedNick = ((String) ((JList) e.getSource()).getSelectedValue());
            if(selectedNick == "Overall records")
                selectedNick = "???";
            if(selectedNick != null)
            {
                // System.out.println("Selected   :   " + selectedNick);
                records_list.removeAllElements();
                records_last.removeAllElements();
                records_recent.removeAllElements();
                records_top.removeAllElements();
                get_data();
        
            }
       }
    }       



}