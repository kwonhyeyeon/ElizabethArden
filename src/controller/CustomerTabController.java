package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import model.CustomerVO;

public class CustomerTabController implements Initializable {
	@FXML
	private TextField txtCustomerCode; // 고객 코드
	@FXML
	private TextField txtCustomerName; // 고객 이름
	@FXML
	private DatePicker dpCustomerBirth; // 고객 생년월일
	@FXML
	private TextField txtCustomerCodePhone; // 고객 핸드폰 번호
	@FXML
	private TextField txtCustomerAddress; // 고객 주소
	@FXML
	private TextField txtCustomerEmail; // 고객 이메일
	@FXML
	private TextArea txtAreaEtc; // 고객 비고
	@FXML
	private Button btnCancle; // 취소버튼
	@FXML
	private Button btnRegiste; // 등록버튼

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		CustomerDAO cdao = new CustomerDAO();

		try {
			txtCustomerCode.setText(cdao.getCustomerCode() + "");
		} catch (Exception e) {
		}
		
		txtCustomerCode.setDisable(true);
		// 등록 버튼 이벤트 핸들러
		btnRegiste.setOnAction(event -> handlerBtnRegisteAction(event));
		// 취소 버튼 이벤트 핸들러
		btnCancle.setOnAction(event -> handlerBtnCancleAction(event));

	}

	// 등록버튼 이벤트 핸들러
	public void handlerBtnRegisteAction(ActionEvent event) {
		CustomerDAO cdao = new CustomerDAO();
		CustomerVO cvo = null;
		boolean ok = false;
		try {
			// 필드 미입력 오류
				if (txtCustomerName.getText().trim().equals("") || txtCustomerName.getText().length() > 5) { // 직원명 입력
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("고객 등록");
					alert.setHeaderText("고객명 오류");
					alert.setContentText("다시 입력해주세요");
					alert.showAndWait();
				} else if(dpCustomerBirth.getValue() == null) { 
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("고객 등록");
					alert.setHeaderText("고객 생년월일");
					alert.setContentText("다시 입력해주세요");
					alert.showAndWait();
				} else if(txtCustomerCodePhone.getText().trim().equals("")) { 
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("고객 등록");
					alert.setHeaderText("고객 핸드폰 번호 미입력");
					alert.setContentText("다시 입력해주세요");
					alert.showAndWait();
				} else if(txtCustomerAddress.getText().trim().equals("")) { 
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("고객 등록");
					alert.setHeaderText("고객 주소 미입력");
					alert.setContentText("다시 입력해주세요");
					alert.showAndWait();
				} else {
			cvo = new CustomerVO(Integer.parseInt(txtCustomerCode.getText().trim()), txtCustomerName.getText().trim(), txtCustomerCodePhone.getText().trim(), 
					txtCustomerAddress.getText().trim(), dpCustomerBirth.getValue().toString(), txtCustomerEmail.getText().trim(), txtAreaEtc.getText());
			ok = cdao.customerRegiste(cvo);
				}
			if(ok) {
				handlerBtnCancleAction(event);
				txtCustomerCode.setText(cdao.getCustomerCode() + "");
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 취소 버튼 이벤트 핸들러
	public void handlerBtnCancleAction(ActionEvent event) {
		// TODO Auto-generated method stub

		// 모든 텍스트 상자 초기화
		txtCustomerName.clear();
		txtCustomerCodePhone.clear();
		txtCustomerAddress.clear();
		txtCustomerEmail.clear();
		txtAreaEtc.clear();
	}

}
