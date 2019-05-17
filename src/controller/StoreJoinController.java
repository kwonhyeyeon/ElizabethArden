package controller;

import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.Window;
import model.StoreJoinVO;

public class StoreJoinController implements Initializable {
	@FXML
	private TextField txtStoreCode; // 매장코드 텍스트상자
	@FXML
	private PasswordField txtPassword; // 매장비밀번호 텍스트상자
	@FXML
	private TextField txtStoreName; // 매장명 텍스트상자
	@FXML
	private TextField txtStoreAddress; // 매장주소 텍스트 상자
	@FXML
	private TextField txtStoreTel; // 매장번호 텍스트 상자
	@FXML
	private Button btnCancle; // 취소버튼
	@FXML
	private Button btnRegiste; // 등록버튼

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		// 매장 전화번호에 숫자만 입력되게 적용
		txtStoreTel.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d*")) {
					txtStoreTel.setText(newValue.replaceAll("[^\\d]", ""));
				}
			}

		});

		btnRegiste.setOnAction(event -> handlerBtnRegisteAction(event)); // 관리자 등록 이벤트
		btnCancle.setOnAction(event -> handlerBtnCancelAction(event)); // 등록창 닫기 이벤트
		// 아이디 입력에서 엔터키 이벤트 적용
		txtStoreCode.setOnKeyPressed(event -> handerTxtIdKeyPressed(event));
		// 패스워드 입력에서 엔터키 이벤트 적용
		txtPassword.setOnKeyPressed(event -> handerTxtPasswordKeyPressed(event));
		// 매장명 입력에서 엔터키 이벤트 적용
		txtStoreName.setOnKeyPressed(event -> handerTxtNameKeyPressed(event));
		// 매장주소 입력에서 엔터키 이벤트 적용
		txtStoreAddress.setOnKeyPressed(event -> handerTxtAddressKeyPressed(event));

		// 매장코드가져오기
		StoreJoinVO sjvo = null;
		StoreJoinDAO sjdao = null;

		sjdao = new StoreJoinDAO();
		try {
			txtStoreCode.setText(sjdao.getStoreSequence(sjvo));
		} catch (Exception e) {
		}

		txtStoreCode.setEditable(false); // 매장코드 수정불가
		txtStoreCode.setDisable(true);
	}

	// 매장등록 메소드
	public void handlerBtnRegisteAction(ActionEvent event) {
		// 인스턴스 생성
		StoreJoinVO sjvo = null;
		StoreJoinDAO sjdao = null;
		// 등록성공여부 판단변수
		boolean joinSuccess = false;

		try {

			if (txtPassword.getText().trim().length() > 4 || txtPassword.getText().trim().equals("")) {
				// 비밀번호 크기가 4보다 크거나 공백일 때
				txtPassword.clear(); // 비밀번호를 지워준다

				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("매장 등록 실패");
				alert.setHeaderText("패스워드 오류");
				alert.setContentText("패스워드는 숫자 4자리로 입력해주세요");
				alert.showAndWait();
			} else if (txtStoreName.getText().trim().equals("")) { // 매장명이 공백이면
				txtPassword.clear(); // 비밀번호를 지워준다

				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("매장 등록 실패");
				alert.setHeaderText("매장명 미입력");
				alert.setContentText("매장명을 입력해주세요");
				alert.showAndWait();
			} else if (txtStoreAddress.getText().trim().equals("")) { // 매장주소가 공백이면
				txtPassword.clear(); // 비밀번호를 지워준다

				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("매장 등록 실패");
				alert.setHeaderText("매장주소 미입력");
				alert.setContentText("매장주소를 입력해주세요");
				alert.showAndWait();
			} else if (txtStoreTel.getText().trim().equals("")) { // 매장번호가 공백이면
				txtPassword.clear(); // 비밀번호를 지워준다

				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("매장 등록 실패");
				alert.setHeaderText("매장번호 미입력");
				alert.setContentText("매장번호를 입력해주세요");
				alert.showAndWait();
			}

		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}

	// 등록창 닫기 메소드
	public void handlerBtnCancelAction(ActionEvent event) {
		Stage stage = (Stage) btnCancle.getScene().getWindow();
		stage.close();
	}

	// 매장주소 입력에서 엔터키 이벤트 적용
	public void handerTxtAddressKeyPressed(KeyEvent event) {
		// 엔터키가 발생할경우
		if (event.getCode() == KeyCode.ENTER) {
			// 비밀번호창으로 포커스를 준다.
			txtStoreTel.requestFocus();
		}
	}

	// 매장명 입력에서 엔터키 이벤트 적용
	public void handerTxtNameKeyPressed(KeyEvent event) {
		// 엔터키가 발생할경우
		if (event.getCode() == KeyCode.ENTER) {
			// 비밀번호창으로 포커스를 준다.
			txtStoreAddress.requestFocus();
		}
	}

	// 패스워드 입력에서 엔터키 이벤트 적용
	public void handerTxtPasswordKeyPressed(KeyEvent event) {
		// 엔터키가 발생할경우
		if (event.getCode() == KeyCode.ENTER) {
			// 비밀번호창으로 포커스를 준다.
			txtStoreName.requestFocus();
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
