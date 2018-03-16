package gffx.gui.comp;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class CounterPane extends HBox {
    private IntegerProperty value, minValue, maxValue;
    private Button btnInc, btnDec;
    private Text txtVal, txtTitle;
    private StringProperty title;

    public CounterPane() {
        this(0);
    }

    public CounterPane(int spacing) {
        this(spacing, 0);
    }

    public CounterPane(int spacing, int initial) {
        this(spacing, initial, 0);
    }

    public CounterPane(int spacing, int initial, int minVal) {
        this(spacing, initial, minVal, Integer.MAX_VALUE);
    }

    public CounterPane(int spacing, int initial, int minVal, int maxVal) {
        this("", spacing, initial, minVal, maxVal, false);
    }

    public CounterPane(String title) {
        this(title, 0);
    }

    public CounterPane(String title, int spacing) {
        this(title, spacing, 0);
    }

    public CounterPane(String title, int spacing, int initial) {
        this(title, spacing, initial, 0);
    }

    public CounterPane(String title, int spacing, int initial, int minVal) {
        this(title, spacing, initial, minVal, Integer.MAX_VALUE);
    }

    public CounterPane(String title, int spacing, int initial, int minVal, int maxVal) {
        this(title, spacing, initial, minVal, maxVal, false);
    }
    
    public CounterPane(String title, int spacing, int initial, int minVal, int maxVal, boolean titleLeft) {
        super(spacing);

        value = new SimpleIntegerProperty();        
        minValue = new SimpleIntegerProperty(minVal);
        maxValue = new SimpleIntegerProperty(maxVal);
        this.title = new SimpleStringProperty(title);

        btnInc = new Button("+") {{
            setOnAction(e -> {
                if(value.get() < maxValue.get())
                    value.set(value.add(1).get());
            });
        }};
        btnDec = new Button("-") {{
            setOnAction(e -> {
                if(value.get() > minValue.get())
                    value.set(value.subtract(1).get());
            });
        }};
        txtVal = new Text() {{
            textProperty().bind(value.asString());
        }};
        txtTitle = new Text() {{
            textProperty().bind(CounterPane.this.title);
        }};

        value.addListener((obj) -> {
            btnInc.setDisable(value.get() >= maxValue.get());
            btnDec.setDisable(value.get() <= minValue.get());
        });
        value.set(initial);

        disableProperty().addListener((obj, oldVal, newVal) -> {
            if(newVal) {
                btnInc.setDisable(true);
                btnDec.setDisable(true);
            } else {
                // HACK (triggers listeners)
                value.set(value.get()+1);
                value.set(value.get()-1);
            }
        });

        if(titleLeft) getChildren().addAll(txtTitle, btnDec, txtVal, btnInc);
        else getChildren().addAll(btnDec, txtVal, btnInc, txtTitle);
    }

    public StringProperty titleProperty() {
        return title;
    }

    public IntegerProperty valueProperty() {
        return value;
    }

    public IntegerProperty minValueProperty() {
        return minValue;
    }

    public IntegerProperty maxValueProperty() {
        return maxValue;
    }

}