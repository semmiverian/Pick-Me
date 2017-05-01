package id.semmi.pickme.vote.add_vote;

import java.util.List;

import id.semmi.pickme.model.User;

/**
 * Created by Semmiverian on 4/29/17.
 */

public class Vote {

    private String text;
    private List<User> users;

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

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
