/*

  To change this generated comment edit the template variable "typecomment":
  Window>Preferences>Java>Templates.
  To enable and disable the creation of type comments go to
  Window>Preferences>Java>Code Generation.
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import jdk.nashorn.api.tree.Tree;

public class LaneStatusView implements ActionListener, LaneObserver, PinsetterObserver {

	private JPanel jp, buttonPanel,viewLanePanel,viewPinSetterPanel,maintenancePanel,PausePanel,ResumePanel;
	private JLabel curBowler, pinsDown,foul;
	private JButton viewLane;
	private JButton viewPinSetter, maintenance, Pause_Button, Resume_button;


	private PinSetterView psv;
	private LaneView lv;
	private Lane lane;
	int laneNum;

	boolean laneShowing;
	boolean psShowing;

	public LaneStatusView(Lane lane, int laneNum ) {

		this.lane = lane;
		this.laneNum = laneNum;

		laneShowing=false;
		psShowing=false;

		psv = new PinSetterView( laneNum );
		Pinsetter ps = lane.getPinsetter();
		ps.subscribe(psv);

		lv = new LaneView( lane, laneNum );
		lane.subscribe(lv);


		jp = new JPanel();
		jp.setLayout(new FlowLayout());
		JLabel cLabel = new JLabel( "Now Bowling: " );
		curBowler = new JLabel( "(no one)" );
		JLabel fLabel = new JLabel( "Foul: " );
		foul = new JLabel( " " );
		JLabel pdLabel = new JLabel( "Pins Down: " );
		pinsDown = new JLabel( "0" );

		// Button Panel
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());

		Insets buttonMargin = new Insets(4, 4, 4, 4);

		viewLane = new JButton("View Lane");
		viewLanePanel = new JPanel();
		viewLanePanel.setLayout(new FlowLayout());
		viewLane.addActionListener(this);
		viewLanePanel.add(viewLane);

		viewPinSetter = new JButton("Pinsetter");
		viewPinSetterPanel = new JPanel();
		viewPinSetterPanel.setLayout(new FlowLayout());
		viewPinSetter.addActionListener(this);
		viewPinSetterPanel.add(viewPinSetter);

		maintenance = new JButton("MAINTENANCE");
		maintenance.setForeground( Color.GREEN );
		maintenance.setBackground( Color.GREEN );
		maintenance.setOpaque(true);
		maintenancePanel = new JPanel();
		maintenancePanel.setLayout(new FlowLayout());
		maintenance.addActionListener(this);
		maintenancePanel.add(maintenance);

		Pause_Button = new JButton("Pause");
		PausePanel = new JPanel();
		PausePanel.setLayout(new FlowLayout());
		Pause_Button.addActionListener(this);
		PausePanel.add(Pause_Button);

		Resume_button = new JButton("Resume");
		ResumePanel = new JPanel();
		ResumePanel.setLayout(new FlowLayout());
		Resume_button.addActionListener(this);
		ResumePanel.add(Resume_button);


		
		viewLane.setEnabled( false );
		viewPinSetter.setEnabled( false );
		Pause_Button.setEnabled(false);		


		buttonPanel.add(viewLanePanel);
		buttonPanel.add(viewPinSetterPanel);
		buttonPanel.add(maintenancePanel);
		buttonPanel.add(PausePanel);
		// buttonPanel.add(ResumePanel);

		jp.add( cLabel );
		jp.add( curBowler );
//		jp.add( fLabel );
//		jp.add( foul );
		jp.add( pdLabel );
		jp.add( pinsDown );
		
		jp.add(buttonPanel);

	}

	public JPanel showLane() {
		return jp;
	}

	private void view_lane()
	{
		if ( lane.isPartyAssigned() ) { 
			if ( laneShowing == false ) {
				lv.show();
				laneShowing=true;
			} else if ( laneShowing == true ) {
				lv.hide();
				laneShowing=false;
			}
		}
	}

	private void maintanance_call()
	{
		if ( lane.isPartyAssigned() ) {			
			lane.unPauseGame();
			maintenance.setBackground( Color.GREEN );
			maintenance.setForeground( Color.GREEN );
		}
	}

	@Override
	public void actionPerformed( ActionEvent e ) {
		if ( lane.isPartyAssigned() ) { 
			if (e.getSource().equals(viewPinSetter)) {
				if ( psShowing == false ) {
					psv.show();
					psShowing=true;
				} else if ( psShowing == true ) {
					psv.hide();
					psShowing=false;
				}
			}
		}
		if (e.getSource().equals(viewLane)) {
			view_lane();
		}
		if (e.getSource().equals(maintenance)) {
			maintanance_call();
		}
		if (e.getSource().equals(Pause_Button)) {
			lane.pauseGame();
			buttonPanel.remove(PausePanel);
			buttonPanel.add(ResumePanel);
			// Pause_Button.setEnabled(false);
			// Resume_button.setEnabled(true);
		}
		if (e.getSource().equals(Resume_button)) {
			lane.unPauseGame();
			// Pause_Button.setEnabled(true);
			// Resume_button.setEnabled(false);
			buttonPanel.remove(ResumePanel);
			buttonPanel.add(PausePanel);
			maintenance.setBackground( Color.GREEN );
			maintenance.setForeground( Color.GREEN );
		}
	}

	@Override
	public void receiveLaneEvent(LaneEvent le) {
		curBowler.setText( le.getBowler().getNickName() );
		if ( le.isMechanicalProblem() ) {
			maintenance.setBackground( Color.RED );
			maintenance.setForeground( Color.RED );
		}	
		if ( lane.isPartyAssigned() == false ) {
			viewLane.setEnabled( false );
			viewPinSetter.setEnabled( false );
			Pause_Button.setEnabled(false);
		} else {
			viewLane.setEnabled( true );
			viewPinSetter.setEnabled( true );
			Pause_Button.setEnabled(true);
		}
	}

	@Override
	public void receivePinsetterEvent(PinsetterEvent pe) {
		pinsDown.setText( ( new Integer(pe.totalPinsDown()) ).toString() );
//		foul.setText( ( new Boolean(pe.isFoulCommited()) ).toString() );
		
	}

}
