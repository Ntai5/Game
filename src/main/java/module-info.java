module com.example.ntai {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens com.example.ntai to javafx.fxml;
    exports com.example.ntai;
}