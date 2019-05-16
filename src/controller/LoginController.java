package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoginController implements Initializable {

	// 로그인 메뉴
	@FXML
	private MenuItem menuExit; // 프로그램 종료
	@FXML
	private MenuItem menuStoreRegiste; // 매장 등록

	@FXML
	private TextField txtStoreCode; // 매장 코드
	@FXML
	private PasswordField txtPassword; // 비밀번호
	@FXML
	private Button btnLogin; // 로그인 버튼

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		// 홈 메뉴 이벤트 등록
		menuExit.setOnAction(event -> handlerMenuExitAction(event));
		menuStoreRegiste.setOnAction(event -> handlerMenuStoreRegisteAction(event));

		// 아이디 입력에서 엔터키 이벤트 적용
		txtStoreCode.setOnKeyPressed(event -> handerTxtIdKeyPressed(event));
		// 패스워드 입력에서 엔터키 이벤트 적용
		txtPassword.setOnKeyPressed(event -> handerTxtPasswordKeyPressed(event));
		// 로그인버튼 이벤트
		btnLogin.setOnAction(event -> handlerBtnLoginAction(event));

	}

	// 매장 등록 이벤트 메소드
	public void handlerMenuStoreRegisteAction(ActionEvent event) {
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/storeRegiste.fxml")); // 레이아웃 불러오기		
			Parent mainView = (Parent)loader.load(); // 부모창을 login.fxml로 로드
			Scene scene = new Scene(mainView); // Scene 객체 생성
			Stage mainStage = new Stage(); // Stage 객체 생성
			mainStage.setTitle("매장 등록"); // 타이틀 설정
			mainStage.initModality(Modality.WINDOW_MODAL);
			mainStage.initOwner(btnLogin.getScene().getWindow());
			mainStage.setResizable(false); // 리사이즈 불가
			mainStage.setScene(scene); // 씬 설정
			mainStage.show(); // 로그인 창 열기
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	// 프로그램 종료 메뉴 이벤트 메소드
	public void handlerMenuExitAction(ActionEvent event) {
		Platform.exit();
	}

	// 로그인버튼 이벤트
	public void handlerBtnLoginAction(ActionEvent event) {
		login(); // 로그인 메소드 호출
	}

	// 로그인 메소드
	public void login() {
		// LoginDAO 인스턴스화
		LoginDAO login = new LoginDAO();
		// 로그인 성공여부 변수
		boolean sucess = false;
		
		try {
			
			// LoginDAO에서 getLogin메소드에 아이디와 비밀번호를 공백제거후 넣어주고 성공여부를 반환받는다
			sucess = login.getLogin(txtStoreCode.getText().trim(), txtPassword.getText().trim());
			
			if(sucess) {
				// 로그인 성공여부가 true일 경우
				try {
					// 메인뷰를 불러온다
					FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/mainView.fxml"));
					// 부모창을 login.fxml로 로드
					Parent mainView = (Parent) loader.load();
					// Scene 객체 생성
					Scene scene = new Scene(mainView);
					// Stage 객체 생성
					Stage mainStage = new Stage();
					// 타이틀 설정
					mainStage.setTitle("엘리자베스 아덴 매장관리 프로그램");
					// 사이즈 재설정 불가
					mainStage.setResizable(false);
					// 씬설정
					mainStage.setScene(scene);
					// 그전에 있던창 oldStage로 저장
					Stage oldStage = (Stage) btnLogin.getScene().getWindow();
					// 그전에 있던 창을 닫음
					oldStage.close();
					// 등록창 열기
					mainStage.show();
				} catch (Exception e) {
					System.out.println("오류 : " + e);
				}
			} else if(txtStoreCode.getText().equals("") || txtPassword.getText().equals("")) {
				// 경고창을 보여준다
				Alert alert;
				alert = new Alert(AlertType.WARNING);
				alert.setTitle("로그인 실패");
				alert.setHeaderText("아이디 또는 비밀번호 미입력");
				alert.setContentText("아이디, 비밀번호를 입력하세요");
				// 경고창 크기설정 불가
				alert.setResizable(false);
				// 경고창을 보여주고 기다린다
				alert.showAndWait();
				// 입력한 아이디와 비밀번호를 지워준다
				txtStoreCode.clear();
				txtPassword.clear();
			} else if(!sucess){
				// 로그인 성공여부가 false일 경우
				// 경고창을 보여준다
				Alert alert;
				alert = new Alert(AlertType.WARNING);
				alert.setTitle("로그인 실패");
				alert.setHeaderText("아이디와 비밀번호 불일치");
				alert.setContentText("아이디와 비밀번호가 일치하지 않습니다");
				// 경고창 크기설정 불가
				alert.setResizable(false);
				// 경고창을 보여주고 기다린다
				alert.showAndWait();
				// 입력한 아이디와 비밀번호를 지워준다
				txtStoreCode.clear();
				txtPassword.clear();
			}

		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}

	// 패스워드 입력에서 엔터키 이벤트 적용
	public void handerTxtPasswordKeyPressed(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			// 로그인 메소드 호출
			login();
		}

	}

	// 아이디 입력에서 엔터키 이벤트 적용
	public void handerTxtIdKeyPressed(KeyEvent event) {
		// 엔터키가 발생할경우
		if (event.getCode() == KeyCode.ENTER) {
			// 비밀번호창으로 포커스를 준다.
			txtPassword.requestFocus();
		}
	}

}
