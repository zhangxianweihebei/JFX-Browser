package controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;


import org.controlsfx.control.Notifications;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import database.SqliteConnection;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author Segp-Group 3
 */
public class LoginController extends Application implements Initializable {

	@FXML
	private Pane loginPane;

	@FXML
	private JFXTextField user;

	@FXML
	private JFXPasswordField password;

	@FXML
	private JFXButton login;

	@FXML
	private JFXButton signup;

	public JFXButton getSingUp() {
		return signup;
	}

	public JFXButton getLogin() {
		return login;
	}

	public Pane getLoginPane() {
		return loginPane;
	}

	// SqliteConnection class obj
	Connection connection;

	// As in Sqlite section we are returing null so to handle null value
	// below we just handle that with system.exit to stop the system.

	public LoginController() {
		connection = SqliteConnection.Connector();
		if (connection == null) {
			System.exit(1);
		}
	}

/*	public boolean isDbConnecited() {
		try {
			return !connection.isClosed();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}*/

	public boolean isLogin(String username, String password) {
		PreparedStatement prepareStatment = null;
		ResultSet resultSet = null;
		String query = "Select * from login where username = ? and password = ?";
		try {
			prepareStatment = connection.prepareStatement(query);
			prepareStatment.setString(1, username);
			prepareStatment.setString(2, password);
			
			resultSet = prepareStatment.executeQuery();
			if (resultSet.next()) {
				String a = resultSet.getString(1);
				String b = resultSet.getString(2);
				System.out.println(a + "\n" + b);

				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			System.out.println("Exception in Login:" + e);
			return false;
			// TODO: handle exception
		} finally {
			try {
				prepareStatment.close();
				resultSet.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void initialize(URL url, ResourceBundle res) {
		// TODO Auto-generated method stub
		login.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {

			boolean flag = getUser().getText().isEmpty() | getPassword().getText().isEmpty();

			if (flag) {

				Notifications noti = Notifications.create()
						.title("Empty Field")
						.text("Please fill the empty field!")
						// .graphic(new ImageView(null))
						.hideAfter(Duration.seconds(5)).position(Pos.TOP_RIGHT);
				noti.showError();
				
			}else{
				if(isLogin(getUser().getText(), getPassword().getText())){
					Notifications noti = Notifications.create()
						.title("Successfull")
						.text("Congratulation! You successfully login. ")
						// .graphic(new ImageView(null))
						.hideAfter(Duration.seconds(3)).position(Pos.TOP_RIGHT);
				noti.showInformation();
				}else{
					Notifications noti = Notifications.create()
							.title("Username and Password Incorrect!")
							.text("Please give your valid username or password. ")
							// .graphic(new ImageView(null))
							.hideAfter(Duration.seconds(3)).position(Pos.TOP_RIGHT);
					noti.showError();
				}
				
			}
		});

	}

	public JFXTextField getUser() {
		return user;
	}

	public void setUser(JFXTextField user) {
		this.user = user;
	}

	public JFXPasswordField getPassword() {
		return password;
	}

	public void setPassword(JFXPasswordField password) {
		this.password = password;
	}

	public JFXButton getSignup() {
		return signup;
	}

	public void setSignup(JFXButton signup) {
		this.signup = signup;
	}

	public void setLogin(JFXButton login) {
		this.login = login;
	}
	
	public static void main(String args[]){
		launch(args);
	}
	@Override
	public void start(Stage primaryStage) throws Exception {
		//System.out.println("yes");
		// TODO Auto-generated method stub
		Parent root = FXMLLoader.load(getClass().getResource("/ui/Login.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}

}
