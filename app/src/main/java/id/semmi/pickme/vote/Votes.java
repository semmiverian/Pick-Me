package id.semmi.pickme.vote;

import java.util.List;

import id.semmi.pickme.vote.add_vote.Vote;

/**
 * Created by Semmiverian on 4/29/17.
 */

public class Votes {

    private String name;
    private String text;
    private List<Vote> votes;
    private String endDate;

    public Votes() {


    }

    public Votes(String name, String text, List<Vote> votes, String endDate) {

        this.name = name;
        this.text = text;
        this.votes = votes;
        this.endDate = endDate;
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
}


