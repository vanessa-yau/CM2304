/**
 * @author Vanessa Yau
 * @author Gorata Sehuhula
 * GUI interface for all forms for a module
 * Accessed via List of Modules GUI
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

public class ModuleBreakdown extends javax.swing.JFrame
{

	// Variables declaration - do not modify
	MBreakdownTemp mTemp = new MBreakdownTemp();
	//true (table columns 1-3 editable only), false (table column 3 editable only)
	private String moduleStartDate;
	
	public boolean cellEditableState = mTemp.cellEditableState;
	public String[] inStaffNames;
	public String[][] internalStaff, externalStaff;
	
	private String[] moduleInfo; 
	private String[] paperNames;
	private String module_code;
	private String module_name, formSelected;
	
	private javax.swing.Box.Filler filler1;
	// assessment paper specific variables
	private javax.swing.JLabel assessmentPaperLbl;
	private javax.swing.JComboBox<String> assessmentPaperComboBox;
	private javax.swing.JButton openFormButton;
	private javax.swing.JScrollPane formTableScrollPane1;
	private javax.swing.JTable formTable;
	// general buttons
	private javax.swing.JButton mainMenuButton, logOutButton;
	private javax.swing.JButton saveChangesButton, sendEmailButton;
	// general module info labels
	private javax.swing.JLabel windowTitleLbl;
	private javax.swing.JLabel modNameLbl, modNameValue;
	private javax.swing.JLabel modCodeLbl, modCodeValue;
	private javax.swing.JLabel modLeaderLbl;
	private javax.swing.JComboBox<String> modLeaderComboBox;
	
	private DefaultTableModel formTableModel;
	
	byte nextState;
	// End of variables declaration

	public static void main(String args[]) 
	{
		
		/* Set the Nimbus look and feel */
		//<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
		/* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
		 * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
		 */
		try
		{
			
			// look and feel - do not modify
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) 
			{
				if ("Nimbus".equals(info.getName())) 
				{
					
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
					
				}
				
			}
			
		} 
		catch (ClassNotFoundException ex) 
		{
			java.util.logging.Logger.getLogger(ModuleBreakdown.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} 
		catch (InstantiationException ex) 
		{
			java.util.logging.Logger.getLogger(ModuleBreakdown.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} 
		catch (IllegalAccessException ex) 
		{
			java.util.logging.Logger.getLogger(ModuleBreakdown.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} 
		catch (javax.swing.UnsupportedLookAndFeelException ex) 
		{
			java.util.logging.Logger.getLogger(ModuleBreakdown.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
		
		//Create and display the form 
		java.awt.EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				new ModuleBreakdown("cm2304").setVisible(true);
			}
		});
		
	}

	public ModuleBreakdown(String mCode) 
	{
		module_code = mCode;
		//module_code = "cm2300";
		
		// retrieve and sort information from text files
		mTemp.retrieveStaff();
		inStaffNames = mTemp.inStaffNames;
		internalStaff = mTemp.internalStaff;
		externalStaff = mTemp.externalStaff;
		
		retrieveModule();
		paperNames = mTemp.getFormNames(module_code, moduleInfo);
		
		setExistingModuleLeader();
		
		// initialise and display window
		initComponents();
		arrangeComponents();
		
		this.setTitle("Module Forms Information");
		
		JOptionPane.showMessageDialog(null," Exiting this application by closing the Application window will cause any unsaved changes to be lost.",
				"Warning", JOptionPane.WARNING_MESSAGE);
		
	}
	
	
	
	// set state to match log out state
	private void logOutButtonActionPerformed(java.awt.event.ActionEvent evt) 
	{
		
		if( displayWindowExitMessage() == true ) nextState = -1;
		
	}
	
	
	private boolean displayWindowExitMessage()
	{
		
		Object[] options = {"Exit Application","No. Return to application."};
		int n = JOptionPane.showOptionDialog(this, "Exiting this application will cause any unsaved changes to be lost.\n"
				+"Do you wish to proceed?","Exit Application",JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE,null, options,options[1]);
		
		if( n == JOptionPane.YES_OPTION ) return true;
		else return false;
		
	}
	
	// set state to match main menu state
	private void mainMenuButtonActionPerformed(java.awt.event.ActionEvent evt) 
	{
		
		if( displayWindowExitMessage() == true ) nextState = 1;
		else return;
		
	}
	
	// set state to match main menu state
	private void sendEmailButtonActionPerformed(java.awt.event.ActionEvent evt)
	{
		
		if( displayWindowExitMessage() == true ) nextState = 4;
		else return;
		
	}
	
	// selects the assessment paper by name
	// retrieves the corresponding information from form table
	private void openFormButtonActionPerformed() 
	{
		// empties the table of any prior form content
		while ( formTableModel.getRowCount() != 0 )
		{
			formTableModel.removeRow(0);
		}
		
		formSelected = (String)assessmentPaperComboBox.getSelectedItem();
		if ( formSelected.equals("select a paper") ) return;
		
		// gets the correct form from the list of all forms
		String[] form = mTemp.getMatchingForm(formSelected);
		
		String PW_name = "";
		String IM_name = "";
		String EM_name = "";
		
		// retrieves staff names from internal and external staff lists 
		if( !form[12].equals("") )
		{
			
			for ( int x=0 ; x<internalStaff.length ; x++ )
			{
				
				if( internalStaff[x][0].equals(form[12]) )
				{
					
					PW_name = internalStaff[x][1];
					
				}
				
			}
		}
		
		if ( !form[6].equals("") )
		{
			
			for ( int w=0; w<externalStaff.length; w++)
			{
				
				if( externalStaff[w][0].equals(form[6]))
				{
					
					EM_name = externalStaff[w][1];
					
				}
				
			}
			
		}
		
		if ( !form[7].equals("") )
		{
			
			for ( int v=0; v<internalStaff.length; v++)
			{
				
				if( internalStaff[v][0].equals(form[7]))
				{
					
					IM_name = internalStaff[v][1];
				
				}
			
			}
			
		}		
		
		// displays only selected entries for a ASSESSMENT PAPER FORM depending on
		// whether the MODULE has a weighting greater than or equal to 50% towards degree
		// do not change the order of the table rows added below:
		formTableModel.addRow(new Object[]{"Paper Written",PW_name,form[13],form[14]});
		formTableModel.addRow( new Object[]{"Internal Moderation", IM_name, form[5], form[11]} );
		formTableModel.addRow( new Object[]{"Reviewed by Module Leader", moduleInfo[5], form[4], form[10]});
		
		if(moduleInfo[6].equals("50"))
		{
			formTableModel.addRow( new Object[]{"External Moderation", EM_name, form[3], form[9]});
		}
		
		formTableModel.addRow( new Object[]{"Final Deadline for Checking", "E.L.O.", form[2], form[8]});
		
		formTable.setModel( formTableModel );
		
		JOptionPane.showMessageDialog(null, mTemp.getWarningMessage(), "Form Table Input", JOptionPane.INFORMATION_MESSAGE);
		
		return;
		
	}
	
	// Performs validation on table
	//if successful all table information is saved to file
	private void saveChangesButtonActionPerformed()
	{
		
		String message = validateResponse();
		displaySaveChangesMessage(message);
		
	}
	
	/**
	 * Validates all row-col entries of table
	 * @return message 		empty if no errors found, else return list of errors.
	 */
	private String validateResponse()
	{
		
		String message = "";
		
		formTableModel.fireTableDataChanged();
		
		// validate names in db
		if( mTemp.validateNames(formTable, moduleInfo[6]) != "" )
		{
			message = message.concat(mTemp.validateNames(formTable,moduleInfo[6]));
			message = message.concat("\nError: Name validation failed." );
		
			return message;
		
		}
		
		if( cellEditableState == false )
		{
			
			// check if no dates have been entered, otherwise carry out validation
			if( mTemp.deadlineEntered(formTable) == false )
			{
				return message;
			}
			
			// false if some error found with date formats for one or more table cells
			if( mTemp.validateDateFormat(formTable,2) == false )
			{

				message = message.concat("\nAll deadlines must be input at the same time."
						+"\nDates must be of the form dd/MM/yyyy. For example: 30/01/2014");
				
				return message;
				
			}
			
			if( mTemp.validDateCompleteFormat(formTable)==false)
			{
				message = message.concat("\nIncorrect date complete format entered.");
				
				return message;
				
			}
			
		}
		
		// validate date entries in correct date order
		if( mTemp.validateDeadlineOrder(formTable) == false )
		{
			message = message.concat("\nAll deadlines must be input at the same time."
					+ "\nPlease note: Deadlines must occur in order.");
		
			return message;
		
		}
		
		return message;
		
	}
	
	private void displaySaveChangesMessage(String message)
	{
		
		if( !message.equals("") )
		{
		
			JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.INFORMATION_MESSAGE);
		}
		else
		{
		
			mTemp.saveFormTableInfo(formTable,moduleInfo,formSelected);
			
			JOptionPane.showMessageDialog(null, module_code.toUpperCase()+":"+formSelected+":"+ " form saved." ,
					"Form Saved", JOptionPane.INFORMATION_MESSAGE);
			
			resetComboSelection();
			
		}
		
		return;
	}
	
	private void resetComboSelection()
	{
	
		assessmentPaperComboBox.setSelectedIndex(0);
		openFormButtonActionPerformed();
	
	}
	
	
	// gets info for module which has matching module code
	private void retrieveModule()
	{
		
		ModuleDatabase modulesDB = new ModuleDatabase();
		modulesDB.initDatabase("modules.txt");
		
		moduleInfo = modulesDB.getModule( "module code", module_code );
		module_name = moduleInfo[0];
		
		int year = Calendar.getInstance().get(Calendar.YEAR);
		
		//set module start date depending on choice of module term
		if( moduleInfo[7].equals("autumn") )
		{
			
			moduleStartDate = "30/09".concat("/"+Integer.toString(year));
			
		}
		else if( moduleInfo[7].equals("spring") )
		{
			
			moduleStartDate = "25/01".concat("/"+Integer.toString(year+1));
		
		}
		else
		{
			
			//if no term has been stated - default is autumn
			moduleStartDate = "30/09".concat("/"+Integer.toString(year));
			
		}
		
		modulesDB.close();
		
	}
	
	
	// saves new module leader
	private void saveNewModLeader()
	{
	
		String nameSelected = (String)modLeaderComboBox.getSelectedItem();
		String staffID = mTemp.getStaffID(nameSelected,internalStaff);
		
		/*System.out.println("SAVE NEW MOD LEADER METHOD ###START");
		System.out.println("OLD MODULE LEADER ID: "+moduleInfo[5]);
		System.out.println("staffID: "+staffID+"\n");*/
		if( !moduleInfo[5].equals(staffID) )
		{
		
			moduleInfo[5] = staffID;
			/*System.out.println("NEW MODULE LEADER ID: "+moduleInfo[5]);*/
		
		}
		
		ModuleDatabase md = new ModuleDatabase();
		md.initDatabase("modules.txt");
		
		md.deleteModule("module code",module_code);
		md.insertModule(moduleInfo[0],moduleInfo[1],moduleInfo[2],
				moduleInfo[3],moduleInfo[4],moduleInfo[5],moduleInfo[6],moduleInfo[7]);
		
		md.close();
		
		JOptionPane.showMessageDialog(null, nameSelected+" has been saved as new Module Leader",
				"Module Changed", JOptionPane.INFORMATION_MESSAGE);
		/*System.out.println("SAVE NEW MOD LEADER METHOD ###END");*/
		
		return;
		
	}
	
	private void setExistingModuleLeader()
	{
		
		System.out.println("BEFORE: INTERNAL STAFF LIST[0]:"+internalStaff[0][0]+","+internalStaff[0][1]);
		
		int arrLocation = 0;
		String[] leaderInfo = new String[2];
		String currentLeaderID = moduleInfo[5];
		// get current leader details from table.
		for( int i=0 ; i<internalStaff.length ; i++ )
		{
			if( currentLeaderID.equals(internalStaff[i][0]) )
			{
				System.out.println("currID: "+currentLeaderID+","+internalStaff[i][0]);
				arrLocation = i;
				leaderInfo[0] = internalStaff[i][0];
				leaderInfo[1] = internalStaff[i][1];
				System.out.println("leaderinfo: "+leaderInfo[0]+","+leaderInfo[1]);
			}
			
		}
		
		String[] temp = new String[2];
		temp[0] = internalStaff[0][0];
		temp[1] = internalStaff[0][1];
		
		internalStaff[0][0] = leaderInfo[0];
		internalStaff[0][1] = leaderInfo[1];
		
		internalStaff[arrLocation][0] = temp[0];
		internalStaff[arrLocation][1] = temp[1];
		
		inStaffNames[0] = internalStaff[0][1];
		inStaffNames[arrLocation] = internalStaff[arrLocation][1];
 		
		
		System.out.println("FIRST LOCATION IN LIST: "+internalStaff[0][0]+","+internalStaff[0][1]);
		System.out.println("MOVED LOCATION: "+arrLocation+","+internalStaff[arrLocation][0]+","+internalStaff[arrLocation][1]);
		
		System.out.println("AFTER: INTERNAL STAFF LIST[0]:"+internalStaff[0][0]+","+internalStaff[0][1]);
		
	}
	
	private void initComponents() 
	{
		
		// Main Menu Button
		mainMenuButton = new javax.swing.JButton();
		mainMenuButton.setText("Main Menu");
		mainMenuButton.addActionListener(new java.awt.event.ActionListener() 
		{
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				mainMenuButtonActionPerformed(evt);
			}
		});
		
		// Log Out Button
		logOutButton = new javax.swing.JButton();
		logOutButton.setText("Log Out");
		logOutButton.addActionListener(new java.awt.event.ActionListener() 
		{
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				logOutButtonActionPerformed(evt);
			}
		});
		
		// Save Changes Button - added actionPerformed method
		saveChangesButton = new javax.swing.JButton();
		saveChangesButton.setText("Save Changes to Form");
		saveChangesButton.addActionListener(new java.awt.event.ActionListener() 
		{
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				saveChangesButtonActionPerformed();
			}
		});
		
		// Send Email Button
		sendEmailButton = new javax.swing.JButton();
		sendEmailButton.setText("Send Email");
		sendEmailButton.addActionListener(new java.awt.event.ActionListener() 
		{
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				sendEmailButtonActionPerformed(evt);
			}
		});
		
		// Open Assessment Paper Form (Table) Button - added actionPerformed method
		openFormButton = new javax.swing.JButton();
		openFormButton.setText("OPEN FORM");
		openFormButton.addActionListener(new java.awt.event.ActionListener() 
		{
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				openFormButtonActionPerformed();
				formTableModel.fireTableDataChanged();
			}
		});

		// Labels:
		windowTitleLbl = new javax.swing.JLabel();
		windowTitleLbl.setFont(new java.awt.Font("Tahoma", 1, 18)); 
		windowTitleLbl.setText("Module Information");
		
		modCodeLbl = new javax.swing.JLabel();
		modCodeLbl.setText("Module Code:");
		
		modCodeValue = new javax.swing.JLabel();
		modCodeValue.setText(module_code.toUpperCase());

		modNameLbl = new javax.swing.JLabel();
		modNameLbl.setText("Module Name:");

		modNameValue = new javax.swing.JLabel();
		modNameValue.setText(module_name);
		
		modLeaderLbl = new javax.swing.JLabel();
		modLeaderLbl.setText("Module Leader:");
		
		modLeaderComboBox = new javax.swing.JComboBox<String>(inStaffNames);
		modLeaderComboBox.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e){
				saveNewModLeader();
			}
		});
		
		assessmentPaperLbl = new javax.swing.JLabel();
		assessmentPaperLbl.setText("Assessment Paper:");
		
		assessmentPaperComboBox = new javax.swing.JComboBox<String>(paperNames);
		
		// default Table
		formTableModel = new javax.swing.table.DefaultTableModel(
				new Object [][] {},
				new String [] {"Form Stage", "Staff Name", "Deadline", "Date Completed"})
		{
			//@Override 
			public boolean isCellEditable(int row, int column){
				return( mTemp.setCellEditable(row,column,moduleStartDate ) );
			}
		};
		formTableModel.addRow(new Object[] {"example","First Last","01/02/2014","02/02/2014"});
		
		// Form (Table) layout structure
		formTable = new javax.swing.JTable();
		formTable.getTableHeader().setReorderingAllowed(false);
		formTableScrollPane1 = new javax.swing.JScrollPane();
		
		formTable.setToolTipText("Dates for Deadlines and Date Completed Columns must have the form: 'dd/mm/yyyy' ");
		formTable.setModel( formTableModel );
		formTableScrollPane1.setViewportView(formTable);
		// End of Form Table layout structure
		
		
		filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 20), new java.awt.Dimension(0, 20), new java.awt.Dimension(32767, 20));
		
	}
	
	private void arrangeComponents() 
	{
		
		// frame settings
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setBounds(new java.awt.Rectangle(0, 0, 0, 0));
		setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
		setPreferredSize(new java.awt.Dimension(800, 600));
		setResizable(false);
		
		// sets up the layout for all components
		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(layout.createSequentialGroup()
										.addGap(56, 56, 56)
										.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
												.addComponent(assessmentPaperLbl)
												.addComponent(modLeaderLbl)
												.addComponent(modNameLbl)
												.addComponent(modCodeLbl)))
												.addGroup(layout.createSequentialGroup()
														.addContainerGap()
														.addComponent(windowTitleLbl)))
														.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
														.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
																.addGroup(layout.createSequentialGroup()
																		.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
																				.addComponent(modCodeValue, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
																				.addComponent(modNameValue, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
																				.addGap(120, 120, 120))
																				.addGroup(layout.createSequentialGroup()
																						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
																								.addComponent(assessmentPaperComboBox, 0, 345, Short.MAX_VALUE)
																								.addComponent(modLeaderComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
																								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																								.addComponent(openFormButton)
																								.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
																								.addGroup(layout.createSequentialGroup()
																										.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
																												.addGroup(layout.createSequentialGroup()
																														.addComponent(mainMenuButton)
																														.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																														.addComponent(logOutButton, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
																														.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
																																.addGroup(layout.createSequentialGroup()
																																		.addGap(261, 261, 261)
																																		.addComponent(filler1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
																																		.addGroup(layout.createSequentialGroup()
																																				.addGap(27, 27, 27)
																																				.addComponent(formTableScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 748, javax.swing.GroupLayout.PREFERRED_SIZE))))
																																				.addGap(0, 25, Short.MAX_VALUE))
																																				.addGroup(layout.createSequentialGroup()
																																						.addGap(251, 251, 251)
																																						.addComponent(saveChangesButton)
																																						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																																						.addComponent(sendEmailButton, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
																																						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				);
		layout.setVerticalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addGap(16, 16, 16)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(windowTitleLbl)
								.addComponent(mainMenuButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(logOutButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
								.addGap(18, 18, 18)
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(modCodeLbl)
										.addComponent(modCodeValue))
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(modNameLbl)
												.addComponent(modNameValue))
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
												.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(modLeaderLbl)
														.addComponent(modLeaderComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
														.addGap(18, 18, 18)
														.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
																.addComponent(assessmentPaperLbl)
																.addComponent(assessmentPaperComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
																.addComponent(openFormButton))
																.addGap(18, 18, 18)
																.addComponent(filler1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
																.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																.addComponent(formTableScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
																.addGap(27, 27, 27)
																.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
																		.addComponent(saveChangesButton)
																		.addComponent(sendEmailButton))
																		.addGap(141, 141, 141))
				);

		pack();
		
	}

}
