package demo;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * @author Jasper Potts
 */
public class GoogleMapsJavaFxApplication extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }

    private Timeline locationUpdateTimeline;

    @Override
    public void start(Stage stage) {
        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();
        webEngine.load(getClass().getResource("/google-maps.html").toString());

        ToggleButton road = new ToggleButton("Road");
        ToggleButton satellite = new ToggleButton("Satellite");
        ToggleButton hybrid = new ToggleButton("Hybrid");
        ToggleButton terrain = new ToggleButton("Terrain");
        buildMapTypeGroup(webEngine, road, satellite, hybrid, terrain);

        TextField search = buildSearch(webEngine);

        ToolBar toolbar = new ToolBar();
        toolbar.getStyleClass().add("map-toolbar");
        toolbar.getItems().addAll(
                road, satellite, hybrid, terrain,
                buildSpacer(),
                buildSpacer(),
                new Label("Location:"),
                search,
                buildZoomInButton(webEngine),
                buildZoomOutButton(webEngine));

        BorderPane root = new BorderPane();
        root.getStyleClass().add("map");
        root.setCenter(webView);
        root.setTop(toolbar);

        stage.setTitle("Google Maps JavaFx Application");
        stage.setMaximized(true);

        Scene scene = new Scene(root, Color.web("#666970"));
        stage.setScene(scene);
        scene.getStylesheets().add("/google-maps.css");

        stage.show();
    }

    private TextField buildSearch(WebEngine webEngine) {
        TextField search = new TextField("");
        search.setPrefColumnCount(12);
        search.setPromptText("Search");
        search.textProperty().addListener((observable, oldValue, newValue) -> {
            if (locationUpdateTimeline != null) {
                locationUpdateTimeline.stop();
            }
            locationUpdateTimeline = new Timeline();
            locationUpdateTimeline.getKeyFrames().add(
                    new KeyFrame(
                            new Duration(1000),
                            actionEvent -> webEngine.executeScript("document.goToLocation(\"" + search.getText() + "\")")
                    )
            );
            locationUpdateTimeline.play();
        });
        return search;
    }

    private void buildMapTypeGroup(WebEngine webEngine, ToggleButton road, ToggleButton satellite, ToggleButton hybrid, ToggleButton terrain) {
        ToggleGroup mapTypeGroup = new ToggleGroup();

        road.setToggleGroup(mapTypeGroup);
        satellite.setToggleGroup(mapTypeGroup);
        hybrid.setToggleGroup(mapTypeGroup);
        terrain.setToggleGroup(mapTypeGroup);

        road.setSelected(true);
        mapTypeGroup.selectToggle(road);

        mapTypeGroup.selectedToggleProperty().addListener((observableValue, oldValue, newValue) -> {
            if (road.isSelected()) {
                webEngine.executeScript("document.setMapTypeRoad()");
            } else if (satellite.isSelected()) {
                webEngine.executeScript("document.setMapTypeSatellite()");
            } else if (hybrid.isSelected()) {
                webEngine.executeScript("document.setMapTypeHybrid()");
            } else if (terrain.isSelected()) {
                webEngine.executeScript("document.setMapTypeTerrain()");
            }
        });
    }

    private Button buildZoomInButton(WebEngine webEngine) {
        Button zoomIn = new Button("Zoom In");
        zoomIn.setOnAction(actionEvent -> webEngine.executeScript("document.zoomIn()"));
        return zoomIn;
    }

    private Button buildZoomOutButton(WebEngine webEngine) {
        Button zoomOut = new Button("Zoom Out");
        zoomOut.setOnAction(actionEvent -> webEngine.executeScript("document.zoomOut()"));
        return zoomOut;
    }

    private Node buildSpacer() {
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        return spacer;
    }
}