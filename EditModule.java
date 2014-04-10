/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *@author Letsibogo Ramadi
 */

package windows;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class EditModule extends javax.swing.JDialog {

    private static String modTitle, modLeader, modTerm;
    private static String modCode, modCredits, exams, courseworks, weighting;
        
    //boolean flag array that is initially false
    private final boolean[] flags = new boolean[7];
    

    
    //no-parameter constructor
    EditModule(ArrayList arr) {
       initComponents();
       myInitComponents();
       System.out.println("Array size = "+arr.size());
       modTitle = (String)arr.get(0);
       modTitleTextField.setText(modTitle);
       
       modCode = (String) arr.get(1);
       modCodeTextField.setText((String)modCode);
       
       modCredits = (String) arr.get(2);
       creditsTextField.setText(String.valueOf(modCredits));
       
       courseworks = (String) arr.get(3);
       numCWTextfield.setText(String.valueOf(courseworks));
       
       
       exams = (String) arr.get(4);
       numExamsTextfield.setText(String.valueOf(exams));
       
       modLeader = (String)arr.get(5);
       modLeaderTextfield.setText(modLeader);
       
       weighting =  (String) arr.get(6);
       weightTextField.setText(weighting);
       
      //set input validation flags to true 
        Arrays.fill(flags, true);
    }

   
    /**
     * This method is called from within the constructor to initialise the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        modCodeLabel = new javax.swing.JLabel();
        fnameLabel = new javax.swing.JLabel();
        numExamsLabel = new javax.swing.JLabel();
        creditsLabel = new javax.swing.JLabel();
        numCWLabel = new javax.swing.JLabel();
        modLeaderLabel = new javax.swing.JLabel();
        modLeaderTextfield = new javax.swing.JTextField();
        weightingLabel = new javax.swing.JLabel();
        weightTextField = new javax.swing.JTextField();
        modTermComboBox = new javax.swing.JComboBox();
        termLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("EDIT MODULE DETAILS");
        setAlwaysOnTop(true);
        setResizable(false);

        modCodeLabel.setText("Module Code :");

        SaveChangesButton.setText("SAVE CHANGES");
        SaveChangesButton.setToolTipText("Click to save changes and go back to modules list");
        SaveChangesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SaveChangesButtonActionPerformed(evt);
            }
        });

        fnameLabel.setText("Module Title :");

        modTitleTextField.setToolTipText("Enter surname containing letters only , e.g: 'Williams'");
        modTitleTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                modTitleTextFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                modTitleTextFieldFocusLost(evt);
            }
        });

        modCodeTextField.setToolTipText("Enter firstname containing letters only, e.g: 'John'");
        modCodeTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                modCodeTextFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                modCodeTextFieldFocusLost(evt);
            }
        });

        numExamsLabel.setText("Number of Exams :");

        creditsLabel.setText("Module Credits :");

        numCWLabel.setText("Number of Courseworks :");

        creditsTextField.setToolTipText("Enter valid email , e.g: 'jameswhite1994@hotmail.com'");
        creditsTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                creditsTextFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                creditsTextFieldFocusLost(evt);
            }
        });

        numExamsTextfield.setToolTipText("Enter valid 10/11 digit phone number , e.g: landline: 02082990482 or mobile: 07700420030");
        numExamsTextfield.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                numExamsTextfieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                numExamsTextfieldFocusLost(evt);
            }
        });

        numExamsTextfield.setToolTipText("Enter valid 10/11 digit phone number , e.g: landline: 02082990482 or mobile: 07700420030");
        numCWTextfield.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                numCWTextfieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                numCWTextfieldFocusLost(evt);
            }
        });

        modLeaderLabel.setText("Module Leader :");

        modLeaderTextfield.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                modLeaderTextfieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                modLeaderTextfieldFocusLost(evt);
            }
        });

        weightingLabel.setText("Weighting :");

        weightTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                weightTextFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                weightTextFieldFocusLost(evt);
            }
        });

        modTermComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Autumn", "Spring" }));
        modTermComboBox.setToolTipText("Select whether module is an autumn/spring semester module");

        termLabel.setText("Term :");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(fnameLabel)
                            .addComponent(modCodeLabel))
                        .addGap(24, 24, 24)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(modCodeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(modTitleTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 484, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(265, 265, 265)
                        .addComponent(SaveChangesButton)))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(creditsLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(creditsTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(numCWLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(numCWTextfield, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(numExamsLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(numExamsTextfield, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(modLeaderLabel)
                            .addComponent(weightingLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(modLeaderTextfield, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(weightTextField)
                                .addGap(41, 41, 41)
                                .addComponent(termLabel)
                                .addGap(18, 18, 18)
                                .addComponent(modTermComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(230, 230, 230)))))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(modTitleTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fnameLabel))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(modCodeLabel)
                    .addComponent(modCodeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(modLeaderLabel)
                    .addComponent(modLeaderTextfield, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(weightingLabel)
                    .addComponent(weightTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(modTermComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(termLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(creditsLabel)
                    .addComponent(creditsTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(numCWLabel)
                    .addComponent(numCWTextfield, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(numExamsLabel)
                    .addComponent(numExamsTextfield, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addComponent(SaveChangesButton)
                .addGap(20, 20, 20))
        );

        pack();
    }// </editor-fold>                        

    //MY BACK-END CODE
    //######################################################################################################
    public final void myInitComponents() {
        setLocationRelativeTo(null);
}
    
    private void SaveChangesButtonActionPerformed(java.awt.event.ActionEvent evt) {                                                  
       
        if(inputsValid(flags)){           
            
            //get all information entered in textfields and add to database
            modTitle = modTitleTextField.getText().toLowerCase();
            modTitle = modTitle.substring(0, 1).toUpperCase() + modTitle.substring(1);
            
            modLeader = modLeaderTextfield.getText();           
            
            modCredits = creditsTextField.getText();
            exams = numExamsTextfield.getText();            
            courseworks = numCWTextfield.getText(); 
            weighting = weightTextField.getText();
            modTerm = (String)modTermComboBox.getSelectedItem();
        
            modCode = modCodeTextField.getText().toLowerCase();     

            //open database            
            ModuleDatabase db = new ModuleDatabase();
            db.initDatabase("modules.txt");
            db.insertModule(modTitle, modCode, modCredits, courseworks, exams, modLeader, weighting,modTerm);

            //close the dialog box and return to 'Manage Users' window       
            dispose();
        }else{
            String invalidInput = "Please enter correct input in the highlighted fields(red)."
                                 +"\n Check that every field is filled before proceeding";
            JOptionPane.showMessageDialog(null, invalidInput, "Invalid Input", JOptionPane.PLAIN_MESSAGE);
        }
               
        
        
    }                                                 

      
    
    private void modTitleTextFieldFocusLost(java.awt.event.FocusEvent evt) {                                            
        String titleRegex = "^[\\p{L} .'-]+$";
        if((modTitleTextField.getText().matches(titleRegex))){                        
            flags[0] = true; 
        }else{            
            modTitleTextField.setForeground(Color.RED);
            flags[0] = false;
        }
      
    }                                           

    private void modCodeTextFieldFocusLost(java.awt.event.FocusEvent evt) {                                           
        String modCodeRegex = "[a-zA-Z]{2}\\d{4}";
        if((modCodeTextField.getText().matches(modCodeRegex))){                       
            flags[1] = true;
        }else{            
            modCodeTextField.setForeground(Color.RED); 
            flags[1] = false;
        }
        
    }                                          

    private void creditsTextFieldFocusLost(java.awt.event.FocusEvent evt) {                                           
        String credsRegex = "\\d{2}";
        if((creditsTextField.getText().matches(credsRegex))){                        
            flags[2] = true;
        }else{            
            creditsTextField.setForeground(Color.RED);
            flags[2] = false;
        }        
    }                                          

    private void numExamsTextfieldFocusLost(java.awt.event.FocusEvent evt) {                                            
        String NumRegex = "\\d{2}";
        if((numExamsTextfield.getText().matches(NumRegex))){
            flags[3] = true;
        }else{            
            numExamsTextfield.setForeground(Color.RED);
            flags[3] = false;
        }
        
    }                                           
 
    private void modTitleTextFieldFocusGained(java.awt.event.FocusEvent evt) {                                              
        modTitleTextField.setForeground(Color.BLACK);
        SwingUtilities.invokeLater( new Runnable() {
                @Override
                public void run() {
                        modTitleTextField.selectAll();		
                }});
    }                                             

    private void modCodeTextFieldFocusGained(java.awt.event.FocusEvent evt) {                                             
        modCodeTextField.setForeground(Color.BLACK); 
        SwingUtilities.invokeLater( new Runnable() {
                @Override
                public void run() {
                        modCodeTextField.selectAll();		
                }});
    }                                            

    private void creditsTextFieldFocusGained(java.awt.event.FocusEvent evt) {                                             
        creditsTextField.setForeground(Color.BLACK);
        SwingUtilities.invokeLater( new Runnable() {
                @Override
                public void run() {
                        creditsTextField.selectAll();		
                }});
    }                                            

    private void numExamsTextfieldFocusGained(java.awt.event.FocusEvent evt) {                                              
        numExamsTextfield.setForeground(Color.BLACK); 
        SwingUtilities.invokeLater( new Runnable() {
                @Override
                public void run() {
                        numExamsTextfield.selectAll();		
                }});
    }                                             

    private void numCWTextfieldFocusGained(java.awt.event.FocusEvent evt) {                                           
        numCWTextfield.setForeground(Color.BLACK);
        SwingUtilities.invokeLater( new Runnable() {
                @Override
                public void run() {
                        numCWTextfield.selectAll();		
                }});
    }                                          

    private void numCWTextfieldFocusLost(java.awt.event.FocusEvent evt) {                                         
         String numCWRegex = "\\d{2}";
        if((numCWTextfield.getText().matches(numCWRegex))){
            flags[4] = true;
        }else{            
            numCWTextfield.setForeground(Color.RED);
            flags[4] = false;
        }
    }                                        

    private void modLeaderTextfieldFocusGained(java.awt.event.FocusEvent evt) {                                               
         modLeaderTextfield.setForeground(Color.BLACK);
        SwingUtilities.invokeLater( new Runnable() {
                @Override
                public void run() {
                        modLeaderTextfield.selectAll();		
                }});
    }                                              

    private void modLeaderTextfieldFocusLost(java.awt.event.FocusEvent evt) {                                             
        String modLeadRegex = "^[\\p{L} .'-]+$";
        if((modLeaderTextfield.getText().matches(modLeadRegex))){                        
            flags[5] = true; 
        }else{            
            modLeaderTextfield.setForeground(Color.RED);
            flags[5] = false;
        }
    }                                            

    private void weightTextFieldFocusLost(java.awt.event.FocusEvent evt) {                                          
        String credsRegex = "\\d{2}";
        if((weightTextField.getText().matches(credsRegex))){                        
            flags[6] = true;
        }else{            
            weightTextField.setForeground(Color.RED);
            flags[6] = false;
        }        
    }                                         

    private void weightTextFieldFocusGained(java.awt.event.FocusEvent evt) {                                            
        weightTextField.setForeground(Color.BLACK);
        SwingUtilities.invokeLater( new Runnable() {
                @Override
                public void run() {
                        weightTextField.selectAll();		
                }});
    }                                           

    /**checks if all inputs are valid
     * @param flags array containing boolean values for the input fields
     * @return True if all are valid False otherwise
     */
    public boolean inputsValid(boolean[] flags){
        boolean b = true;
        for(boolean flag : flags){
            if(flag == false){
                b = false;
                break;
            }
        }
        return b;    
    } 
    
     
//#############################################################################################################
    
  
    // Variables declaration - do not modify                     
    private final javax.swing.JButton SaveChangesButton = new javax.swing.JButton();
    private javax.swing.JLabel creditsLabel;
    private final javax.swing.JTextField creditsTextField = new javax.swing.JTextField();
    private javax.swing.JLabel fnameLabel;
    private javax.swing.JLabel modCodeLabel;
    private final javax.swing.JTextField modCodeTextField = new javax.swing.JTextField();
    private javax.swing.JLabel modLeaderLabel;
    private javax.swing.JTextField modLeaderTextfield;
    private javax.swing.JComboBox modTermComboBox;
    private final javax.swing.JTextField modTitleTextField = new javax.swing.JTextField();
    private javax.swing.JLabel numCWLabel;
    private final javax.swing.JTextField numCWTextfield = new javax.swing.JTextField();
    private javax.swing.JLabel numExamsLabel;
    private final javax.swing.JTextField numExamsTextfield = new javax.swing.JTextField();
    private javax.swing.JLabel termLabel;
    private javax.swing.JTextField weightTextField;
    private javax.swing.JLabel weightingLabel;
    // End of variables declaration                   
}
