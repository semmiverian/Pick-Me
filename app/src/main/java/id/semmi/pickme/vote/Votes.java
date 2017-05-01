package id.semmi.pickme.vote;

import java.util.HashMap;
import java.util.List;

import id.semmi.pickme.model.User;
import id.semmi.pickme.vote.add_vote.Vote;

/**
 * Created by Semmiverian on 4/29/17.
 */

public class Votes {

    private String name;
    private String text;
    private List<Vote> votes;
    private String endDate;
    private HashMap<String, User> pendingVoteUsers;

    public Votes() {


    }

    public Votes(String name, String text, List<Vote> votes, String endDate) {

        this.name = name;
        this.text = text;
        this.votes = votes;
        this.endDate = endDate;
    }

    public Votes(String name, String text, List<Vote> votes, String endDate, HashMap<String, User> pendingVoteUsers) {

        this.name = name;
        this.text = text;
        this.votes = votes;
        this.endDate = endDate;
        this.pendingVoteUsers = pendingVoteUsers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<Vote> getVotes() {
        return votes;
    }

    public void setVotes(List<Vote> votes) {
        this.votes = votes;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public HashMap<String, User> getPendingVoteUsers() {
        return pendingVoteUsers;
    }

    public void setPendingVoteUsers(HashMap<String, User> pendingVoteUsers) {
        this.pendingVoteUsers = pendingVoteUsers;
    }
}




