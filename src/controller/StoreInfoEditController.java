package controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.StoreVO;

public class StoreInfoEditController implements Initializable {

	@FXML
	private TextField txtStoreName; // 매장명 수정상자
	@FXML
	private PasswordField txtPwEdit; // 매장비밀번호 수정상자
	@FXML
	private TextField txtStoreTel; // 매장번호 수정상자
	@FXML
	private TextField txtStoreAddress; // 매장주소 수정상자
	@FXML
	private Button btnCancle; // 취소버튼
	@FXML
	private Button btnEdit; // 변경버튼

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

		// 매장 전화번호에 숫자만 입력되게 적용
		txtStoreTel.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d*")) {
					txtStoreTel.setText(newValue.replaceAll("[^\\d]", ""));
				}
			}

		});

		// 매장클래스 인스턴스화
		StoreVO store = new StoreVO();
		// 리스트 배열 생성
		ArrayList<StoreVO> list = new ArrayList<>();
		// 매장DAO 인스턴스화
		StoreDAO sdao = new StoreDAO();

		try {
			// 매장코드로 검색하여 등록된 정보를 가져오는 메소드 리스트 배열로 반환
			list = sdao.getStoreCode();
			// 정보변경창을 열었을때 매장코드로 조회하여 등록된 정보를 가져와 텍스트박스값을 설정해줌
			txtStoreName.setText(list.get(0).getS_name() + "");
			txtPwEdit.setText(list.get(0).getS_pw() + "");
			txtStoreTel.setText(list.get(0).getS_phonenumber() + "");
			txtStoreAddress.setText(list.get(0).getS_address() + "");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		}

		// 버튼 이벤트
		btnEdit.setOnAction(event -> handlerBtnEditAction(event)); // 변경 버튼 이벤트 메소드
		btnCancle.setOnAction(event -> handlerBtnCancelAction(event)); // 수정창 닫기 이벤트 메소드

		// btnEdit.setDisable(true); // 변경 버튼 비활성화

	}

	// 변경버튼 이벤트 메소드
	public void handlerBtnEditAction(ActionEvent event) {
		// TODO Auto-generated method stub
		// sdao인스턴스 생성
		StoreDAO sdao = new StoreDAO();
		// svo객체 인스턴스 생성
		StoreVO svo = new StoreVO();
		try {
			// 비어있는 상자마다 다른 경고창을 출력하기 위해 조건문을 나누었다.
			// 2개 이상의 텍스트 상자에서 조건문에 걸릴시 경고창이 한번만 보여주기 위해 else if를 사용.

			if (txtStoreName.getText().trim().equals("")) {
				// 매장명 텍스트 상자가 비어있을경우
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("매장 정보 변경 실패");
				alert.setHeaderText("매장명 미입력");
				alert.setContentText("매장명을 입력하십시오");
				alert.showAndWait();
			} else if (txtPwEdit.getText().trim().length() > 4 || txtPwEdit.getText().trim().equals("")) { // 매장명이 공백이면
				// 비밀번호 텍스트 상자의 길이가 4보다 크거나, 비어있을경우
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("매장 정보 변경 실패");
				alert.setHeaderText("패스워드입력 오류");
				alert.setContentText("숫자 4자리로 입력하십시오");
				alert.showAndWait();
			} else if (txtStoreTel.getText().trim().equals("")) { // 매장주소가 공백이면
				// 매장번호 텍스트 상자가 비어있을경우
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("매장 정보 변경 실패");
				alert.setHeaderText("매장번호 미입력");
				alert.setContentText("매장번호를 입력해주세요");
				alert.showAndWait();
			} else if (txtStoreAddress.getText().trim().equals("")) { // 매장번호가 공백이면
				// 매장주소가 비어있을경우
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("매장 정보 변경 실패");
				alert.setHeaderText("매장주소 미입력");
				alert.setContentText("매장주소를 입력해주세요");
				alert.showAndWait();
			}
			// 비밀번호 텍스트 상자가 4보다 크지않고 비어있지 않을경우
			else if (!(txtPwEdit.getText().trim().length() > 4 && txtPwEdit.getText().trim().equals(""))) {
				// 객체에 입력된 정보들을 담는다
				svo = new StoreVO(txtStoreAddress.getText().trim(), txtStoreName.getText().trim(),
						Integer.parseInt(txtPwEdit.getText().trim()), Integer.parseInt(txtStoreTel.getText().trim()));
				// sdao의 update메소드에 입력된 정보가 담겨진 객체를 넣어준다.
				sdao.InfoEdit(svo);

				// 변경후 등록창닫기 이벤트 호출
				handlerBtnCancelAction(event);
			}

		} catch (Exception e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("매장 정보 변경 실패");
			alert.setHeaderText("비밀번호는 숫자 4자리로만 입력하십시오..");
			alert.setContentText("");
			alert.showAndWait();
		}
	}

	// 수정창 닫기 이벤트 메소드
	public void handlerBtnCancelAction(ActionEvent event) {
		// Cancle버튼에 이벤트 발생시 현재 스테이지를 현재 모달창을 스테이지에 넣어준다.
		Stage stage = (Stage) btnCancle.getScene().getWindow();
		// 스테이지를 닫아준다.
		stage.close();
	}

}
