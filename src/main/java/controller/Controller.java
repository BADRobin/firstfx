package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import org.w3c.dom.html.HTMLButtonElement;

import java.net.URL;
import java.sql.*;
import java.util.Optional;
import java.util.ResourceBundle;


public class Controller  {
    @FXML   private TextArea textArea;
    @FXML   private Label outputLabel;
    @FXML   private Button button;
    @FXML   private TextField textField;

    private Connection connection;

    @FXML
    public void initialize() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql", "root", "");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void buttonClick(ActionEvent actionEvent) {
        String text = textField.getText() ;

        outputLabel.setText("Hello, " + text);
    }

    public void hello() {
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.NO, ButtonType.YES);
        alert.setContentText("И чЁ ты смотришь, "+ textField.getText() + "?!");
        alert.setTitle("Данунах!!!");
        alert.setHeaderText("Удалить его?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get().equals(ButtonType.YES)){
//            textField.setText("");
            String text = queryToDB();
            textArea.setText(text);
        }
    }

    private String queryToDB() {
        StringBuilder builder = new  StringBuilder();
        try (Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery("select * from student");
                while (resultSet.next()){
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String lastname = resultSet.getString("lastname");
                    int age = resultSet.getInt("age");
                    builder.append(String.format("%3|-%30|-%50|-%3\n", id, name, lastname, age));
                }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            return builder.toString();
        }
    }

    public void checkChenged(InputMethodEvent inputMethodEvent) {

    }

    public void checkText(KeyEvent keyEvent) {
        button.setDisable(textField.getText().isEmpty());
    }


}
