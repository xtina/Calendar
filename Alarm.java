/**
 * Name: Christina Black, Kelly Hutchison, Nicole Hart Date: 10/13/2012 Section:
 * 002 Project: Calendar
 */
package calendar;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.sql.*;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;

/**
 * This class will sound an alarm at the specified time
 *
 * @author Nicole Hart <nicole10hart at gmail.>
 */
public class Alarm extends JPanel implements ActionListener, ItemListener {

    private String hour = "";  //hour for the alarm
    private String min;   //minute for the alarm
    private String alarmName = "ALARM! "; // String for the alarm
    private JLabel titleLabel;
    private JLabel timeLabel;
    private String HOURS[] = {" ", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
    private JComboBox hourPart;
    private JLabel colonLabel;
    String MINUTES[] = {"", "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12",
        "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28",
        "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44",
        "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59"};
    private JComboBox minutePart;
    private JLabel militaryLabel;
    private JLabel repeatLabel;
    private JTextField labelText;
    private JPanel panelZero = new JPanel();
    private JPanel panelOne = new JPanel();
    private JPanel panelTwo = new JPanel();
    private JPanel panelThree = new JPanel();
    private JPanel panelFour = new JPanel();
    private JButton buttonOkay;
    private GridLayout layout;                 // layout of applet
    private JFrame window = new JFrame();
    private JLabel dateStartLabel;
    private JLabel dateEndLabel;
    private String[] DAY = {"", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15",
        "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
    private JComboBox dayStartPart;
    private JComboBox dayEndPart;
    private String dayStart;
    private String dayEnd;
    private int curYear;
    private int curMonth;
    private JLabel endYear;

    public Alarm(MyButton dayClicked, int curMonth, int curYear) throws LineUnavailableException, UnsupportedAudioFileException, IOException {
        layout = new GridLayout(5, 1);
        window.setLayout(layout);

        this.curMonth = curMonth;
        this.curYear = curYear;
        dayStart = dayClicked.getDay();
        dayEnd = dayClicked.getDay();


        titleLabel = new JLabel("ALARM");
        timeLabel = new JLabel("Time: ");
        hourPart = new JComboBox(HOURS);
        colonLabel = new JLabel(": ");
        minutePart = new JComboBox(MINUTES);
        militaryLabel = new JLabel("(Military time)");
        repeatLabel = new JLabel("Repeat: ");
        dateStartLabel = new JLabel("Start date: " + curMonth + "/");
        dayStartPart = new JComboBox(DAY);
        dateEndLabel = new JLabel("/" + curYear + " End date: " + curMonth + "/");
        dayEndPart = new JComboBox(DAY);
        endYear = new JLabel("/" + curYear);
        labelText = new JTextField(20);
        labelText.setText(alarmName);
        buttonOkay = new JButton("OKAY");

        dayStartPart.addItemListener(this);
        dayEndPart.addItemListener(this);
        hourPart.addItemListener(this);
        minutePart.addItemListener(this);
        buttonOkay.addActionListener(this);

        panelZero.add(titleLabel);
        panelOne.add(timeLabel);
        panelOne.add(hourPart);
        panelOne.add(colonLabel);
        panelOne.add(minutePart);
        panelOne.add(militaryLabel);
        panelTwo.add(repeatLabel);
        panelTwo.add(dateStartLabel);
        panelTwo.add(dayStartPart);
        panelTwo.add(dateEndLabel);
        panelTwo.add(dayEndPart);
        panelTwo.add(endYear);
        panelThree.add(labelText);
        panelFour.add(buttonOkay);

        panelZero.setBackground(Color.pink);
        panelOne.setBackground(Color.cyan);
        panelTwo.setBackground(Color.pink);
        panelThree.setBackground(Color.cyan);
        panelFour.setBackground(Color.pink);

        window.add(panelZero);
        window.add(panelOne);
        window.add(panelTwo);
        window.add(panelThree);
        window.add(panelFour);

        window.setBackground(Color.white);
        window.setResizable(false);
        window.setLocation(478, 245);
        window.setSize(500, 500);
        window.setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == buttonOkay) {
            try {
                if ("".equals(hour) || "".equals(min)) {
                    JOptionPane.showMessageDialog(null, "You must enter a time for your alarm"
                            + " to start at!");
                } else if (Integer.parseInt(dayEnd) < Integer.parseInt(dayStart)) {

                    throw new EventException("");
                } else {
                    // Store the alarm
                    DBManager.connect();
                    Connection myConnection = DBManager.getConnection();
                    Statement stmt = myConnection.createStatement();


                    for (int dayParts = Integer.parseInt(dayStart); dayParts <= Integer.parseInt(dayEnd); dayParts++) {
                        if (dayParts < 10) {
                            String newDayParts = "0" + dayParts;
                            stmt.execute("insert into alarm (name, starttime, datetime) values('" + alarmName + "', '"
                                    + hour + ":" + min + "', '" + curMonth + newDayParts + curYear + "')");
                        } else {
                            stmt.execute("insert into alarm (name, starttime, datetime) values('" + alarmName + "', '"
                                    + hour + ":" + min + "', '" + curMonth + dayParts + curYear + "')");
                        }

                    }

                    stmt.close();
                    window.setVisible(false);
                    window.dispose();

                }
            } catch (SQLException ex) {
                System.out.println("Exception");
                ex.printStackTrace();
            } catch (EventException ee) {
                JOptionPane.showMessageDialog(null, "You must enter a start date"
                        + " before your end date.");
            }


        }

    }

    @Override
    public void itemStateChanged(ItemEvent e) {

        if (e.getSource() == hourPart) {
            hour = (String) hourPart.getSelectedItem();

        }
        if (e.getSource() == minutePart) {
            min = (String) minutePart.getSelectedItem();
        }
        if (e.getSource() == dayStartPart) {
            dayStart = (String) dayStartPart.getSelectedItem();
        }
        if (e.getSource() == dayEndPart) {
            dayEnd = (String) dayEndPart.getSelectedItem();
        }
    }
}
