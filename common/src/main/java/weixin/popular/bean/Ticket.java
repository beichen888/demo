package weixin.popular.bean;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by demo
 */
public class Ticket implements Serializable {
    private String ticket;
    private int expires_in;
    private long createdDate;

    public Ticket(){
        this.createdDate = Calendar.getInstance().getTimeInMillis()/1000;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public boolean isValid(){
        long now = Calendar.getInstance().getTimeInMillis()/1000;
        return ticket!=null && (now-createdDate) < expires_in;
    }
}
