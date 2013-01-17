
/**
 * Name: Christina Black, Kelly Hutchison, Nicole Hart
 * Date: 11/6/2012
 * Section: 002
 * Project: Calendar
 */

package calendar;

import calendar.MyButton;
import java.awt.BorderLayout;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

/**
 * An event holds the date, time, and location of a user's
 * desired Event
 * @author Christina Black <cblack at psu.edu>
 */

public class Event extends JPanel implements ActionListener, ItemListener {
    
    private static final String hours[] = {"", "24", "23", "22", "21", "20", "19", "18", "17", "16", "15",
    "14", "13", "12", "11", "10", "9", "8", "7", "6", "5", "4", "3", "2", "1"};
    private static final String mins[] = {"","00","15","30","45"};
    private JPanel days = new JPanel();
    private JButton ok;
    private JFrame frame = new JFrame();
    protected JPanel textField = new JPanel();
    private JTextField message;
    private JComboBox startHour = new JComboBox(hours);
    private JComboBox startMin = new JComboBox(mins);
    private JComboBox endHour = new JComboBox(hours);
    private JComboBox endMin = new JComboBox(mins);
    protected String sHour = "";
    protected String sMin = "";
    protected String eHour = "";
    protected String eMin = "";
    protected String location;
    private MyButton dayIndex;
    private int curMonth;
    private int year;
    
    //Default Constructor
    public Event(MyButton dayIndex, int curMonth, int year) {
        frame.setLocation(430, 300);  
        textField.setBorder(BorderFactory.createEmptyBorder(20,0,0,0));
        days.setBorder(BorderFactory.createEmptyBorder(10,5,10,5));
        BorderLayout layout = new BorderLayout(10, 20);
        setLayout(layout);
        
        this.dayIndex = dayIndex;
        this.curMonth = curMonth;
        this.year = year;
        
        message = new JTextField(25);
        message.setText("Enter your event name here");
        message.selectAll();
        textField.add(message);
        frame.add(textField, BorderLayout.NORTH);
        
        
        days.add(new JLabel("Select the start time of your event"));
        startHour.addItemListener(this);
        startMin.addItemListener(this);
        days.add(startHour); 
        days.add(startMin);
        
        
        days.add(new JLabel("Select the end time of your event"));
        endHour.addItemListener(this);
        endMin.addItemListener(this);
        days.add(endHour);
        days.add(endMin);
        
        frame.add(days, BorderLayout.CENTER);
        ok = new JButton("OK");
        ok.addActionListener(this);
        frame.add(ok, BorderLayout.SOUTH);
        
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(400, 300);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e){

        if (e.getSource() == ok) {
            try{
                if("".equals(sHour) || "".equals(eHour) || "".equals(sMin) || "".equals(eMin)){
                    JOptionPane.showMessageDialog(null, "You must enter a time for your event"
                            + "to start at!");
                }
                else if(Integer.parseInt(sHour) > Integer.parseInt(eHour)) {
                    throw new EventException("");
                }

                location = message.getText();
                frame.setVisible(false);
                frame.dispose();

                // Store the alarm
                DBManager.connect();
                Connection myConnection = DBManager.getConnection();
                Statement stmt = myConnection.createStatement();
                stmt.execute("insert into event (name, startTime, endTime, datetime) values('" + location + "', '" + sHour + ":" + sMin + "', '"
                        + eHour + ":" + eMin + "', '" + curMonth + dayIndex.getDay() + year + "')");
                stmt.close();

            } catch (SQLException ex) {
                ex.printStackTrace();
            } catch (EventException ee) {
                JOptionPane.showMessageDialog(null, "You must enter a start time"
                        + " after your end time.");
            }
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        
        if(e.getSource() == startHour){
            sHour = (String) startHour.getSelectedItem();
        }
        
        if(e.getSource() == endHour){
            eHour = (String) endHour.getSelectedItem();
        }
        
        if(e.getSource() == startMin){
            sMin = (String) startMin.getSelectedItem();
        }
        
        if(e.getSource() == endMin){
            eMin = (String) endMin.getSelectedItem();
        }
    }
    
    @Override
    public String toString(){
        return("EVENT: " + location + " at " + sHour + ":" + sMin + " to " + eHour + ":" + eMin);
    }
    
}
