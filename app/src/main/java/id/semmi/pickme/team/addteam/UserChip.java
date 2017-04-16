package id.semmi.pickme.team.addteam;

import android.graphics.drawable.Drawable;
import android.net.Uri;

import com.pchmn.materialchips.model.ChipInterface;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Semmiverian on 4/16/17.
 */

public class UserChip implements ChipInterface {
    private String uuid;
    private String name;
    private String email;
    private Drawable drawable;

    public UserChip() {
    }


    public UserChip (String uuid, String name, String email, Drawable drawable) {
        this.uuid = uuid;
        this.name = name;
        this.email = email;
        this.drawable = drawable;
    }

    @Override
    public Object getId() {
        return uuid;
    }

    @Override
    public Uri getAvatarUri() {
        return null;
    }

    @Override
    public Drawable getAvatarDrawable() {
        return drawable;
    }

    @Override
    public String getLabel() {
        return getName();
    }

    @Override
    public String getInfo() {
        return email;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }

    public Map<String, Object> userToMap () {
        Map<String, Object> maps = new HashMap<>();
        maps.put("uuid", getUuid());
        maps.put("name", getName());
        maps.put("email", getEmail());
        return maps;
    }
}
