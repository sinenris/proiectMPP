package utils;

import domain.Excursie;

/**
 * Created by grigo on 11/16/16.
 */
public class ExcursieEvent implements Event {
    private STEType type;
    private Excursie data, oldData;

    public ExcursieEvent(STEType type, Excursie data) {
        this.type = type;
        this.data = data;
    }
    public ExcursieEvent(STEType type, Excursie data, Excursie oldData) {
        this.type = type;
        this.data = data;
        this.oldData=oldData;
    }

    public STEType getType() {
        return type;
    }

    public Excursie getData() {
        return data;
    }

    public Excursie getOldData() {
        return oldData;
    }
}
