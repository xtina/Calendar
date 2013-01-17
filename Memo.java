/**
 * Name: Christina Black, Kelly Hutchison, Nicole Hart 
 * Date: 11/6/2012 
 * Section:002 
 * Project: Calendar
 */
package calendar;

import java.awt.BorderLayout;
import java.awt.event.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;

/**
 * An event holds the date, time, and location of a user's desired Event
 */
public class Memo extends JPanel implements ActionListener {

    private JPanel days = new JPanel();
    private JButton okayButton;
    private JFrame frame = new JFrame();
    private JPanel textField = new JPanel();
    private JTextArea message;
    private JLabel memoLabel;
    private MyButton dayClicked;
    private int curMonth;
    private int curYear;
    private String memo;
    private String oldMemo = " ";

    //Default Constructor
    /**
     *
     * @param memoEvents
     * @param dayIndex
     * @param eventIndex
     * @throws LineUnavailableException
     * @throws IOException
     */
    public Memo(MyButton dayClicked, int curMonth, int curYear) throws LineUnavailableException, IOException {
        super();
        textField.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        days.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
        BorderLayout layout = new BorderLayout(100, 200);
        setLayout(layout);

        this.dayClicked = dayClicked;
        this.curMonth = curMonth;
        this.curYear = curYear;

        memoLabel = new JLabel("Memo: ");
        frame.add(memoLabel, BorderLayout.CENTER);

        message = new JTextArea();
        message.setSize(100, 300);
        message.setText("Enter your memo here");
        message.selectAll();
        frame.add(message, BorderLayout.CENTER);

        okayButton = new JButton("OKAY");
        okayButton.addActionListener(this);
        frame.add(okayButton, BorderLayout.SOUTH);

        frame.setResizable(false);
        frame.setSize(400, 300);
        frame.setLocation(400, 250);
        frame.setVisible(true);
    }

    //Action listener
    @Override
    public void actionPerformed(ActionEvent e) {
        memo = message.getText();
        if (e.getSource() == okayButton) {
            try {
                if ("Enter your memo here".equals(message.getText())) {
                    JOptionPane.showMessageDialog(null, "You must enter a message");
                } else {
                    // Store the alarm
                    Connection myConnection = DBManager.getConnection();
                    Statement stmt = myConnection.createStatement();
                    ResultSet results;
                    results = stmt.executeQuery("select memo from calendar where datetime = '" + curMonth + dayClicked.getDay() + curYear + "'");
                    if (results.next()) {
                        if (results.getString("memo") != null) {
                            oldMemo = results.getString("memo");
                        }
                    }

                    if (" ".equals(oldMemo)) {
                        stmt.execute("update calendar "
                                + "set memo = '" + memo + "' "
                                + "where datetime = '" + curMonth + dayClicked.getDay() + curYear + "'");
                    } else {
                        stmt.execute("update calendar "
                                + "set memo = '" + memo + '\n' + "                  " + oldMemo + "' "
                                + "where datetime = '" + curMonth + dayClicked.getDay() + curYear + "'");
                    }

                    stmt.close();
                    frame.setVisible(false);
                    frame.dispose();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        }
    }
}
