package gffx.gui;

import java.util.HashMap;
import java.util.Map;

import gffx.game.Game2D;
import gffx.game.entity.Player;
import gffx.game.ressource.Sprite;
import gffx.game.world.Field2D;
import gffx.gui.comp.CounterPane;
import gffx.gui.view.Game2DView;
import gffx.gui.view.OptionsDialog;
import gffx.util.GameManager;
import gffx.util.Locale;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.WindowEvent;
import tictactoe.TicTacToe;
import tictactoe.entity.TicTacToeAI;

public class SetupStage {
    private static final String APP_TITLE = "GameFrameFX";
    private static final OptionsDialog mainDialog = new OptionsDialog(APP_TITLE);

    private static String gameTitle;
    private static IntegerProperty fieldWidth = new SimpleIntegerProperty();
    private static IntegerProperty fieldHeight = new SimpleIntegerProperty();
    private static IntegerProperty aiLevel = new SimpleIntegerProperty();

    private static CheckBox cbx_ai;
    private static ChoiceBox<String> chx_lang, chx_game;
    private static CounterPane ctp_level, ctp_width, ctp_height;
    private static Map<String, StringProperty> textMap;

    static {
        GameManager.add("TicTacToe", () -> {
            Player[] players = new Player[2];
            players[0] = new Player(new Sprite("res/ttt/image/sprite/cross_cracked_blue.png"));
            players[1] = cbx_ai.isSelected() ? new TicTacToeAI(new Sprite("res/ttt/image/sprite/circle_cracked_red.png"), aiLevel.get())
                : new Player(new Sprite("res/ttt/image/sprite/circle_cracked_red.png"));

            try {
                return new TicTacToe(players, new Field2D(fieldWidth.get(), fieldHeight.get()));
            } catch(Exception e) {
                e.printStackTrace();
            }

            return null;
        });

        init();
        build();
    }

    public static void show() {
        mainDialog.show();
    }

    public static void init() {
        fieldWidth = new SimpleIntegerProperty();
        fieldHeight = new SimpleIntegerProperty();
        aiLevel = new SimpleIntegerProperty();

        cbx_ai = new CheckBox();
        chx_lang = new ChoiceBox<>(FXCollections.observableArrayList(Locale.getLanguages()));
        chx_game = new ChoiceBox<>(FXCollections.observableArrayList(GameManager.getAllTitles()));
        ctp_level = new CounterPane(3, 1, 1, 10);
        ctp_width = new CounterPane(3, 3, 1, 99);
        ctp_height = new CounterPane(3, 3, 1, 99);
        textMap = new HashMap<>();

        textMap.put("lang", new SimpleStringProperty(Locale.get("lang")));
        textMap.put("game", new SimpleStringProperty(Locale.get("game")));
        textMap.put("pva", new SimpleStringProperty(Locale.get("pva")));
        textMap.put("field", new SimpleStringProperty(Locale.get("field")));
        textMap.put("width", new SimpleStringProperty(Locale.get("width")));
        textMap.put("height", new SimpleStringProperty(Locale.get("height")));
        textMap.put("maincfg", new SimpleStringProperty(Locale.get("maincfg")));
        textMap.put("gamecfg", new SimpleStringProperty(Locale.get("gamecfg")));
        textMap.put("new_game", new SimpleStringProperty(Locale.get("new_game")));
        textMap.put("level", new SimpleStringProperty(Locale.get("level")));
        textMap.put("quit", new SimpleStringProperty(Locale.get("quit")));
    }

    public static void build() {
        VBox main_pane = new VBox(5);
        VBox main_config = new VBox(5);
        VBox game_config = new VBox(5);
        GridPane main_grid = new GridPane();

        main_grid.addRow(0, new Text() {{ textProperty().bind(textMap.get("lang")); }}, chx_lang);
        main_grid.addRow(1, new Text() {{ textProperty().bind(textMap.get("game")); }}, chx_game);
        main_grid.addRow(2, cbx_ai, new Text() {{ textProperty().bind(textMap.get("level")); }},  ctp_level);
        main_grid.addRow(3,
            new Text() {{ textProperty().bind(textMap.get("field")); }},
            new Text() {{ textProperty().bind(textMap.get("width")); }},
            ctp_width,
            new Text() {{ textProperty().bind(textMap.get("height")); }},
            ctp_height
        );

        RowConstraints rconts = new RowConstraints();
        ColumnConstraints cconts = new ColumnConstraints();

        rconts.setPercentHeight(100);
        cconts.setPercentWidth(100);

        main_grid.getRowConstraints().addAll(rconts, rconts, rconts, rconts);
        main_grid.getColumnConstraints().addAll(cconts, cconts, cconts, cconts, cconts);
        main_grid.setPadding(new Insets(10, 10, 10, 10));
        main_grid.getChildren().forEach(c -> main_grid.setMargin(c, new Insets(0, 5, 0, 5)));
        // main_grid.setGridLinesVisible(true);

        // events/bindings
        chx_lang.setOnAction(e -> {
            Locale.setLang(chx_lang.getSelectionModel().getSelectedItem());
            textMap.keySet().forEach(key -> textMap.get(key).set(Locale.get(key)));
        });
        chx_game.setOnAction(e -> gameTitle = chx_game.getSelectionModel().getSelectedItem());

        ctp_level.disableProperty().bind(cbx_ai.selectedProperty().not());
        aiLevel.bind(ctp_level.valueProperty());
        fieldWidth.bind(ctp_width.valueProperty());
        fieldHeight.bind(ctp_height.valueProperty());
        //////////////////
        
        // initial setup
        cbx_ai.textProperty().bind(textMap.get("pva"));
        chx_lang.getSelectionModel().selectFirst();
        chx_game.getSelectionModel().selectFirst();
        ////////////////

        mainDialog.setContent(main_grid);
        mainDialog.addOptions(
            new Button() {{
                textProperty().bind(textMap.get("new_game"));
                setOnAction(e -> {
                    mainDialog.hide();
                    GameManager.init(gameTitle);
                    new Game2DView(mainDialog, (Game2D)GameManager.get(gameTitle)).show();
                });
            }},
            new Button() {{
                textProperty().bind(textMap.get("quit"));
                setOnAction(e ->
                    mainDialog.fireEvent(new WindowEvent(mainDialog, WindowEvent.WINDOW_CLOSE_REQUEST))
                );
            }}
        );
    }
}