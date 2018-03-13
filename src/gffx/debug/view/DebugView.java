package gffx.debug;

import java.util.List;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringExpression;
import javafx.beans.property.StringProperty;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class DebugView extends Stage {
    private VBox vbx_rows;

    public DebugView(StringExpression... messages) {
        if(messages != null) {
            for(StringExpression message : messages)
                addRow(message);
        }

        vbx_rows = new VBox();

        setTitle("Debug");
        setX(Screen.getPrimary().getVisualBounds().getMinX());
        setY(Screen.getPrimary().getVisualBounds().getMinY());
        setWidth(Screen.getPrimary().getVisualBounds().getWidth()/4);
        setHeight(Screen.getPrimary().getVisualBounds().getHeight());
        setScene(new Scene(new BorderPane(vbx_rows)));
    }

    public void addRow(StringExpression row) {
        vbx_rows.getChildren().add(new Text() {{
            textProperty().bind(row);
            setDisabled(true);
        }});
    }
}