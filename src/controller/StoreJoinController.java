package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
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
		// TODO Auto-generated method stub

		btnRegiste.setOnAction(event -> handlerBtnRegisteAction(event)); // 관리자 등록 이벤트
		btnCancle.setOnAction(event -> handlerBtnCancelAction(event)); // 등록창 닫기 이벤트
	}

	// 매장등록 메소드
	public void handlerBtnRegisteAction(ActionEvent event) {
		// 인스턴스 생성
		StoreJoinVO sjvo = null;
		StoreJoinDAO sjdao = null;
		// 등록성공여부 판단변수
		boolean joinSucess = false;

		// JoinVO에 입력받은 id, ps, name을 공백제거후 넣어준다
		sjvo = new StoreJoinVO(txtStoreCode.getText().trim(), txtStoreAddress.getText().trim(),
				txtStoreName.getText().trim(), Integer.parseInt(txtPassword.getText().trim()),
				Integer.parseInt(txtStoreTel.getText().trim()));
		// 인스턴스화
		sjdao = new StoreJoinDAO();

		try {
			// JoinDAO에서 managerRegiste메소드를 호출하여 등록후 성공여부를 확인한다.
			joinSucess = sjdao.getStoreRegiste(sjvo);

			// 등록이 성공하였을 경우
			if (joinSucess) {
				// 취소버튼을 호출하여 창을 닫아준다
				handlerBtnCancelAction(event);
			} else { // 등록실패

				txtPassword.clear(); // 비밀번호를 지워준다

				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("패스워드, 이름 확인");
				alert.setHeaderText("패스워드, 이름 확인 검사에 오류가 발생");
				alert.setContentText("수고링~");
				alert.showAndWait();

			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}

	// 등록창 닫기 메소드
	public void handlerBtnCancelAction(ActionEvent event) {
		// TODO Auto-generated method stub
		// 등록창을 닫고 로그인 화면을 불러와서 띄운다.
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login.fxml"));

			Parent mainView = (Parent) loader.load();
			Scene scane = new Scene(mainView);
			Stage mainMtage = new Stage();
			mainMtage.setTitle("매장 로그인");
			mainMtage.setScene(scane);
			Stage oldStage = (Stage) btnCancle.getScene().getWindow();
			oldStage.close();
			mainMtage.show();

		} catch (IOException e) {
			System.out.println("오류 " + e);
		}

	}

}
