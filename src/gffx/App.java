package gffx;

import gffx.game.Game2D;
import gffx.game.entity.Player;
import gffx.game.world.Field2D;
import gffx.game.world.Symbol;
import gffx.gui.view.Game2DView;
import gffx.gui.view.OptionsDialog;
import gffx.util.Locale;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import tictactoe.TicTacToe;
import tictactoe.entity.TicTacToeAI;

public class App extends Application {
    private final String APP_TITLE = "Game Enginxe x2#34";
    private final int FIELD_WDT = 4;
    private final int FIELD_HGT = 4;

    private Game2D game;
    private Game2DView gameView;
    private OptionsDialog mainDialog;

    public static void main(String[] args) {
        Locale.setLang("de");
        launch(args);
    }

    @Override
    public void start(Stage ps) {
        initApp();
        mainDialog.show();
    }

    private void initApp() {
        initTicTacToe();
        initMainDialog();
    }

    private void initTicTacToe() {
        Field2D field = new Field2D(FIELD_WDT, FIELD_HGT);
        Player[] players = new Player[] {
            new Player(Symbol.CROSS),
            new TicTacToeAI(Symbol.CIRCLE, 2)
        };

        try {
            game = new TicTacToe(players, field);
        } catch(Exception e) {
            e.printStackTrace();
        }
    } 

    private void initMainDialog() {
        if(game == null) return;
        mainDialog = new OptionsDialog(APP_TITLE);
        gameView = new Game2DView(mainDialog, game);

        VBox settings = new VBox();
        CheckBox cbx_ai = new CheckBox(Locale.get("pva"));
        ChoiceBox<String> cox_lang = new ChoiceBox<>(FXCollections.observableArrayList(Locale.getLanguages()));
        cox_lang.setOnAction(e -> Locale.setLang(cox_lang.getSelectionModel().getSelectedItem()));
        
        settings.setPadding(new Insets(10, 10, 10, 10));
        settings.getChildren().addAll(cbx_ai, cox_lang);
        mainDialog.setContent(settings);
        mainDialog.addOptions(
            new Button(Locale.get("new_game")) {{
                setOnAction(e -> {
                    mainDialog.hide();
                    gameView.show();
                    //new Game2DView(mainDialog, game).show();
                });
            }},
            new Button(Locale.get("quit")) {{
                setOnAction(e -> mainDialog.fireEvent(new WindowEvent(mainDialog, WindowEvent.WINDOW_CLOSE_REQUEST)));
            }}
        );

        /*
        for(Button option : options)
            mainDialog.addOption(option);
        */
    }
}