package id.semmi.pickme.vote.add_vote;

import java.util.HashMap;
import java.util.List;

import id.semmi.pickme.model.User;

/**
 * Created by Semmiverian on 4/29/17.
 */

public class Vote {

    private String text;

    private HashMap<String, User> users;
    private Long percentage;

    public Vote() {

    }

    public Vote(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


    public HashMap<String, User> getUsers() {
        return users;
    }

    public void setUsers(HashMap<String, User> users) {
        this.users = users;
    }

    public Long getPercentage() {
        return percentage;
    }

    public void setPercentage(Long percentage) {
        this.percentage = percentage;
    }
}
