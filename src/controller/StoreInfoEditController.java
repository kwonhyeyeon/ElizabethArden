package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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

		
		btnEdit.setDisable(true); // 변경 버튼 비활성화
		
	}
	// 수정창 닫기 이벤트 메소드
	public void handlerBtnCancelAction(ActionEvent event) {
		Stage stage = (Stage) btnCancle.getScene().getWindow();
		stage.close();
	}

}
