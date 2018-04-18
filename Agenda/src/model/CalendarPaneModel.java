package model;

import javafx.scene.layout.Pane;

public class CalendarPaneModel extends Pane {
    private int day;

    public int getDay() {
        return this.day;
    }

    public void setDay(int day) {
        this.day = day;
    }
}
