package gffx.gui.view;

import com.sun.javafx.geom.BaseBounds;
import com.sun.javafx.geom.transform.BaseTransform;
import com.sun.javafx.jmx.MXNodeAlgorithm;
import com.sun.javafx.jmx.MXNodeAlgorithmContext;
import com.sun.javafx.sg.prism.NGNode;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class OptionsDialog extends Stage {
    private BorderPane bdp_content;
    private HBox hbx_options;

    public OptionsDialog() {
        this("");
    }

    public OptionsDialog(String title) {
        this(null, title);
    }

    public OptionsDialog(Stage parent, String title) {
        this(parent, title, "");
    }

    public OptionsDialog(String title, String message, Button... options) {
        this(null, title, message, options);
    }

    public OptionsDialog(String title, Node content, Button... options) {
        this(null, title, content, options);
    }

    public OptionsDialog(Stage parent, String title, String message, Button... options) {
        this(parent, title, new Text(message) {{ setTextAlignment(TextAlignment.CENTER); }}, options);
    }

    public OptionsDialog(Stage parent, String title, Node content, Button... options) {
        bdp_content = new BorderPane();
        hbx_options = new HBox();

        setContent(content);
        bdp_content.setBottom(hbx_options);

        for(Button option : options)
            addOption(option);

        if(parent != null) {
            initOwner(parent);
            initModality(Modality.WINDOW_MODAL);
            initStyle(StageStyle.UNDECORATED);
            setWidth(parent.getWidth()/2);
            setHeight(parent.getHeight()/2);
            setX(parent.getX()+parent.getWidth()/4);
            setY(parent.getY()+parent.getHeight()/4);
            setResizable(false);
            setTitle(parent.getTitle() + " - " + title);
        } else {
            setMinWidth(Screen.getPrimary().getVisualBounds().getWidth()/4);
            setMinHeight(Screen.getPrimary().getVisualBounds().getHeight()/4);
            setTitle(title);
            centerOnScreen();
        }

        setScene(new Scene(bdp_content));
    }

    public void addOption(Button option) {
        option.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(option, Priority.SOMETIMES);
        hbx_options.getChildren().add(option);
    }

    public void addOptions(Button... options) {
        for(Button option : options)
            addOption(option);
    }

    public void setContent(Node content) {
        bdp_content.setCenter(content);;
    }

    public void setContent(String message) {
        setContent(new Text(message) {{ setTextAlignment(TextAlignment.CENTER); }});
    }

    public Node getContent() {
        return bdp_content.getCenter();
    }

    public Button getOption(int i) {
        // for now the hbox will only contain Buttons so it's fine
        return (Button)hbx_options.getChildren().get(i);
    }

    public ObservableList<Node> getOptions() {
        return hbx_options.getChildren();
    }
}