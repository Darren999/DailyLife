package view.darren.com.dailylife.db.cache;

import io.realm.RealmObject;

public class SelectUnFollow extends RealmObject {
    private int id;
    private String name;
    private int index;
    private String logo;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getLogo() {
        return logo;
    }
}
