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
    private static IntegerProperty fieldWidth, fieldHeight, aiLevel, gamePoints;

    private static CheckBox cbx_ai, cbx_gravity;
    private static ChoiceBox<String> chx_lang, chx_game;
    private static CounterPane ctp_level, ctp_width, ctp_height, ctp_points;
    private static Map<String, StringProperty> textMap;

    static {
        GameManager.add("TicTacToe", () -> {
            Player[] players = new Player[2];
            players[0] = new Player(new Sprite("res/ttt/image/sprite/cross_cracked_blue.png"));
            players[1] = cbx_ai.isSelected() ? new TicTacToeAI(new Sprite("res/ttt/image/sprite/circle_cracked_red.png"), aiLevel.get())
                : new Player(new Sprite("res/ttt/image/sprite/circle_cracked_red.png"));

            try {
                return new TicTacToe(players, new Field2D(fieldWidth.get(), fieldHeight.get()), gamePoints.get());
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
        mainDialog.setContent((String)null);
        mainDialog.getOptions().clear();

        fieldWidth = new SimpleIntegerProperty();
        fieldHeight = new SimpleIntegerProperty();
        aiLevel = new SimpleIntegerProperty();
        gamePoints = new SimpleIntegerProperty();

        cbx_ai = new CheckBox();
        cbx_gravity = new CheckBox();
        chx_lang = new ChoiceBox<>(FXCollections.observableArrayList(Locale.getLanguages()));
        chx_game = new ChoiceBox<>(FXCollections.observableArrayList(GameManager.getAllTitles()));
        ctp_points = new CounterPane(3, 3, 1, 3);
        ctp_level = new CounterPane(3, 1, 1, 10);
        ctp_width = new CounterPane(3, 3, 1);
        ctp_height = new CounterPane(3, 3, 1);
        textMap = new HashMap<>();

        textMap.put("lang", new SimpleStringProperty(Locale.get("lang")));
        textMap.put("gravity", new SimpleStringProperty(Locale.get("gravity")));
        textMap.put("game", new SimpleStringProperty(Locale.get("game")));
        textMap.put("pva", new SimpleStringProperty(Locale.get("pva")));
        textMap.put("field", new SimpleStringProperty(Locale.get("field")));
        textMap.put("width", new SimpleStringProperty(Locale.get("width")));
        textMap.put("height", new SimpleStringProperty(Locale.get("height")));
        textMap.put("maincfg", new SimpleStringProperty(Locale.get("maincfg")));
        textMap.put("gamecfg", new SimpleStringProperty(Locale.get("gamecfg")));
        textMap.put("new_game", new SimpleStringProperty(Locale.get("new_game")));
        textMap.put("opponent", new SimpleStringProperty(Locale.get("opponent")));
        textMap.put("level", new SimpleStringProperty(Locale.get("level")));
        textMap.put("points", new SimpleStringProperty(Locale.get("points")));
        textMap.put("rules", new SimpleStringProperty(Locale.get("rules")));
        textMap.put("reset", new SimpleStringProperty(Locale.get("reset")));
        textMap.put("quit", new SimpleStringProperty(Locale.get("quit")));
    }

    public static void build() {
        GridPane main_grid = new GridPane();
        RowConstraints rconts = new RowConstraints();
        RowConstraints rconts2 = new RowConstraints();
        ColumnConstraints cconts = new ColumnConstraints();
        ColumnConstraints cconts2 = new ColumnConstraints();

        // Main confing
        main_grid.addRow(0,
            new Label() {{ textProperty().bind(textMap.get("maincfg")); }},
            new Separator() {{ GridPane.setColumnSpan(this, GridPane.REMAINING); }}
        );
        main_grid.addRow(1, new Text() {{ textProperty().bind(textMap.get("lang")); }}, chx_lang);
        main_grid.addRow(2, new Text() {{ textProperty().bind(textMap.get("game")); }}, chx_game);

        // Game config
        main_grid.addRow(3,
            new Label() {{ textProperty().bind(textMap.get("gamecfg")); }},
            new Separator() {{ GridPane.setColumnSpan(this, GridPane.REMAINING); }}
        );
        main_grid.addRow(4, new Text() {{ textProperty().bind(textMap.get("rules")); }}, ctp_points, cbx_gravity);
        main_grid.addRow(5, new Text() {{ textProperty().bind(textMap.get("opponent")); }}, ctp_level, cbx_ai);
        main_grid.addRow(6, new Text() {{ textProperty().bind(textMap.get("field")); }}, ctp_width, ctp_height);

        rconts.setPercentHeight(100);
        rconts2.setPercentHeight(25);
        cconts.setPercentWidth(100);
        cconts2.setPercentWidth(50);

        //main_grid.setGridLinesVisible(true); // DEBUG
        main_grid.getRowConstraints().addAll(rconts2, rconts, rconts, rconts2, rconts, rconts, rconts);
        main_grid.getColumnConstraints().addAll(cconts2, cconts, cconts);
        main_grid.setPadding(new Insets(10, 10, 10, 10));
        main_grid.getChildren().forEach(c -> {
            GridPane.setMargin(c, new Insets(0, 5, 0, 5));
            GridPane.setHalignment(c, HPos.LEFT);
        });

        // TODO figure out why HBox (from CounterPane) ignores set Halignment of GridPane
        ctp_level.setAlignment(Pos.CENTER_LEFT);
        ctp_points.setAlignment(Pos.CENTER_LEFT);
        ctp_width.setAlignment(Pos.CENTER_LEFT);
        ctp_height.setAlignment(Pos.CENTER_LEFT);
        /////// (temp solution)

        // events/bindings
        chx_lang.setOnAction(e -> {
            Locale.setLang(chx_lang.getSelectionModel().getSelectedItem());
            textMap.keySet().forEach(key -> textMap.get(key).set(Locale.get(key)));
        });
        chx_game.setOnAction(e -> gameTitle = chx_game.getSelectionModel().getSelectedItem());

        ctp_level.disableProperty().bind(cbx_ai.selectedProperty().not());

        // not sure if I like this behaviour (apart from this beeing a mess)
        ctp_width.valueProperty().addListener((obs, oldVal, newVal) -> {
            if(newVal.intValue() < fieldHeight.get()) {
                ctp_points.maxValueProperty().set(newVal.intValue());
                
                if(ctp_points.valueProperty().get() > newVal.intValue())
                    ctp_points.valueProperty().set(newVal.intValue());
            } else
                ctp_points.maxValueProperty().set(fieldHeight.get());
        });
        ctp_height.valueProperty().addListener((obs, oldVal, newVal) -> {
            if(newVal.intValue() < fieldWidth.get()) {
                ctp_points.maxValueProperty().set(newVal.intValue());
                
                if(ctp_points.valueProperty().get() > newVal.intValue())
                    ctp_points.valueProperty().set(newVal.intValue());
            } else
                ctp_points.maxValueProperty().set(fieldWidth.get());
        });

        aiLevel.bind(ctp_level.valueProperty());
        gamePoints.bind(ctp_points.valueProperty());
        fieldWidth.bind(ctp_width.valueProperty());
        fieldHeight.bind(ctp_height.valueProperty());
        //////////////////
        
        // initial setup
        cbx_ai.textProperty().bind(textMap.get("pva"));
        cbx_gravity.textProperty().bind(textMap.get("gravity"));
        ctp_points.titleProperty().bind(textMap.get("points"));
        ctp_level.titleProperty().bind(textMap.get("level"));
        ctp_width.titleProperty().bind(textMap.get("width"));
        ctp_height.titleProperty().bind(textMap.get("height"));

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
                textProperty().bind(textMap.get("reset"));
                setOnAction(e -> {
                    init();
                    build();
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