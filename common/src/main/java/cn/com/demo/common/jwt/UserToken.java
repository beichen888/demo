package cn.com.demo.common.jwt;

import java.io.Serializable;
import java.util.Calendar;

public class UserToken implements Serializable {
    private Long id;
    private String username;
    private String name;
    private String role;
    private long datetime;

    public UserToken(long id, String username, String name, String role){
        this.id = id;
        this.username = username;
        this.name = name;
        this.role = role;
        this.datetime = Calendar.getInstance().getTimeInMillis();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public long getDatetime() {
        return datetime;
    }

    public void setDatetime(long datetime) {
        this.datetime = datetime;
    }
}