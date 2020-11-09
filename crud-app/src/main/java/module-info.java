module ua.churkin.javaFX.crud_app {
    requires javafx.controls;
    requires javafx.fxml;
	requires java.sql;
	requires javafx.base;
	requires javafx.graphics;

    opens ua.churkin.javaFX.crud_app to javafx.fxml;
    exports ua.churkin.javaFX.crud_app;
}