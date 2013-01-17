/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package calendar;

import javax.swing.JButton;

//Prototype, feel free to change as needed
public class MyButton extends JButton{
    private String day;
    
    public MyButton(int day){
        if(day < 9){
        this.day = '0' + Integer.toString(day+1);
        }   
        
        else{
            this.day = Integer.toString(day+1);
        }
        
        super.setText(Integer.toString(day+1));
    }
    
    public String getDay(){
        return day;
    }
    
}
