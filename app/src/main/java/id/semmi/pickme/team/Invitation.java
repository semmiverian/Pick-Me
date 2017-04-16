package id.semmi.pickme.team;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Semmiverian on 4/16/17.
 */

public class Invitation {

    private String team;
    private String description;

    public Invitation() {
    }

    public Invitation (String team, String description) {
        this.team = team;
        this.description = description;
    }

    public String getTeam() {
        return team;
    }

    public String getDescription() {
        return description;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String, Object> invitationToMap () {
        Map<String, Object> mapped = new HashMap<>();
        mapped.put("team", getTeam());
        mapped.put("description", getDescription());

        return mapped;
    }

}
