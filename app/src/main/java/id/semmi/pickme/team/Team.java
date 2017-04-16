package id.semmi.pickme.team;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import id.semmi.pickme.team.addteam.UserChip;

/**
 * Created by Semmiverian on 4/16/17.
 */

public class Team {

    private String name;
    private String description;
    private List<UserChip> member;

    public Team() {
    }

    public Team(String name, String description) {
        this.name = name;
        this.description = description;
        this.member = member;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<UserChip> getMember() {
        return member;
    }

    public void setMember(List<UserChip> member) {
        this.member = member;
    }

    public Map<String, Object> toMap () {
        Map<String, Object> map = new HashMap<>();
        map.put("name", getName());
        map.put("description", getDescription());

        return map;
    }
}
