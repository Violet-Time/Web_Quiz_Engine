package engine.model;

import javax.persistence.*;

@Entity
public class Option {
    @Id @GeneratedValue
    private int id;
    private String name;
    private boolean isTrue;

    public Option() {
    }

    public Option(String name) {
        this.name = name;
    }

    public Option(String name, boolean isTrue) {
        this.name = name;
        this.isTrue = isTrue;
    }

    public Option(int id, String name, boolean isTrue) {
        this.id = id;
        this.name = name;
        this.isTrue = isTrue;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTrue(boolean aTrue) {
        isTrue = aTrue;
    }

    public boolean isTrue() {
        return isTrue;
    }

    @Override
    public String toString() {
        return "Option{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", isTrue=" + isTrue +
                '}';
    }
}
