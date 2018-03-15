package gffx.gui.comp;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class CounterPane extends HBox {
    private IntegerProperty value;
    private Button btnInc, btnDec;
    private Text txtVal;

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
        super(spacing);

        value = new SimpleIntegerProperty();        
        btnInc = new Button("+") {{
            setOnAction(e -> {
                if(value.get() < maxVal)
                    value.set(value.add(1).get());
            });
        }};
        btnDec = new Button("-") {{
            setOnAction(e -> {
                if(value.get() > minVal)
                    value.set(value.subtract(1).get());
            });
        }};
        txtVal = new Text() {{
            textProperty().bind(value.asString());
        }};

        value.addListener((obj) -> {
            btnInc.setDisable(value.get() >= maxVal);
            btnDec.setDisable(value.get() <= minVal);
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

        getChildren().addAll(btnDec, txtVal, btnInc);
        setAlignment(Pos.CENTER);
    }

    public ReadOnlyIntegerProperty valueProperty() {
        return value;
    }
}