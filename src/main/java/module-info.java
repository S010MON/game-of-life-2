module com.example.gol2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens game to javafx.fxml;
    exports game;
}