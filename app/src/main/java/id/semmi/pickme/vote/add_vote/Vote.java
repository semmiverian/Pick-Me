package id.semmi.pickme.vote.add_vote;

import java.util.List;

/**
 * Created by Semmiverian on 4/29/17.
 */

public class Vote {

    private String text;

    public Vote(String text) {

        this.text = text;
    }



    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
