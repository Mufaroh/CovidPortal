package com.portal.CovidPortal.View;

import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//import java.sql.* ;

public class Register {
    private JPanel Main;
    private JTextField txtNamTextField;
    private JTextField txtNum;
    private JComboBox<String> cmbCond;
    private JComboBox<String> cmbAge;
    private JTable table1;
    private JButton btnSave;
    private JButton editPatientButton;
    private JButton viewPatientButton;
    private JButton deletePatientButton;
    private JTextField txtId;

    public final void ArrayC()
    {
        String [] cond={"--Select Condition--", "Mild","Severe", "Chronic"};
        cmbCond.setModel(new javax.swing.DefaultComboBoxModel<>(cond));

        String [] ag={"--Select Age--", "18 and Younger","19-35", "36 and Older"};
        cmbAge.setModel(new javax.swing.DefaultComboBoxModel<>(ag));
    }
    public static void main(String[] args)
    {
        JFrame frame = new JFrame("Register");
        frame.setContentPane(new Register().Main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
    PreparedStatement pst;

    void table_load()
    {
        try
        {
            ResultSet rs= pst.executeQuery();
            table1.setModel(DbUtils.resultSetToTableModel(rs));

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }


    public Register()
    {
        ArrayC();
        table_load();
        btnSave.addActionListener(e -> {
            String name, contact, cond ="", age="" ;

            name = txtNamTextField.getText();
            contact = txtNum.getText();
            if(e.getSource()==cmbCond)
            {
                cond = cmbCond.getSelectedItem().toString();
            }
            else if (e.getSource()==cmbAge)
            {
                age= cmbAge.getSelectedItem().toString();
            }

            try {
                pst.setString(1, name);
                pst.setString(2, contact);
                pst.setString(3, cond);
                pst.setString(4,age );
                pst.executeUpdate();

                JOptionPane.showMessageDialog(null, "Patient's Record Added!!");
                //table_load();
                txtNamTextField.setText("");
                txtNum.setText("");
                //set 2 combo boxes empty selection:
                cmbCond.setSelectedIndex(0);
                cmbAge.setSelectedIndex(0);
                txtNamTextField.requestFocus();

            } catch (SQLException er) {
                er.printStackTrace();
            }




        });

        //Edit
        editPatientButton.addActionListener(e -> {
            String name, contact, cond="" , age="",Id ;

            name = txtNamTextField.getText();
            contact = txtNum.getText();
            Id= txtNum.getText();

            if(e.getSource()==cmbCond)
            {
                cond = cmbCond.getSelectedItem().toString();
            }
            else if (e.getSource()==cmbAge)
            {
                age= cmbAge.getSelectedItem().toString();
            }

            try
            {
                pst.setString(1, name);
                pst.setString(2, contact);
                pst.setString(3, cond);
                pst.setString(4,age );
                pst.setString(5,Id );
                pst.executeUpdate();

                JOptionPane.showMessageDialog(null, "Patient's Record Updated!!");
                table_load();
                txtNamTextField.setText("");
                txtNum.setText("");
                cmbCond.setSelectedIndex(0);
                cmbAge.setSelectedIndex(0);
                txtNamTextField.requestFocus();
                txtId.setText("");

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
        //View

        viewPatientButton.addActionListener(e -> {
            try {
                String PId = txtId.getText();
                pst.setString(1, PId);
                ResultSet rs = pst.executeQuery(); //parameters on it and then call executeQuery() method directly without re-passing the SQL string.

                if (rs.next == true)
                {
                    String name = rs.getString(1);
                    String contact = rs.getString(2);
                    String cond = rs.getString(3);
                    String age = rs.getString(4);

                    txtNamTextField.setText(name);
                    txtNum.setText(contact);
                    cmbCond.setSelectedItem(cond);
                    cmbAge.setSelectedItem(age);
                }
                else
                {
                    txtNamTextField.setText("");
                    txtNum.setText("");
                    cmbCond.setSelectedIndex(0);
                    cmbAge.setSelectedIndex(0);
                    JOptionPane.showMessageDialog(null, "Invalid Patient Number!!");
                }

            } catch (SQLException ex)
            {
                ex.printStackTrace();
            }
        });

        //Delete
        deletePatientButton.addActionListener(e -> { String Id = txtId.getText();

            try {
                pst.setString(1, Id);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            try {
                ResultSet rs = pst.executeQuery();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            JOptionPane.showMessageDialog(null, " Patient Deleted !!");
            table_load();
            txtNamTextField.setText("");
            txtNum.setText("");
            cmbCond.setSelectedIndex(0);
            cmbAge.setSelectedIndex(0);
            txtNamTextField.requestFocus();
        });
    }
}
