package entity;

import java.io.Serializable;

/**
 * Created by dona on 07.06.16.
 */
public class BaseEntity implements Serializable{
    private int ID;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
