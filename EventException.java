package calendar;

/**
 * Name: Christina Black
 * Date:
 * Section: 002
 * Project:
 */

/** Custom exception class that displays when the user inputs an invalid time
 * @author Christina Black <cblack at psu.edu> 
 * 
 * */
public class EventException extends Exception {

    public EventException() {
    }
    
    public EventException(String msg){
        super(msg);
    }

}
