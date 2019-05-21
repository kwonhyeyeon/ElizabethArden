package controller;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.EmployeeVO;
import model.StoreVO;

public class EmployeeRegisteController implements Initializable {

	@FXML
	private TextField txtEmployeeCode; // 직원 코드
	@FXML
	private TextField txtEmployeeName; // 직원명
	@FXML
	private TextField txtEmployeePhone; // 핸드폰번호
	@FXML
	private TextField txtEmpolyeeAddress; // 주소
	@FXML
	private DatePicker dpEmployeeBirth; // 생년월일
	@FXML
	private ComboBox<String> cbxEmployeeRank; // 직원등급
	@FXML
	private DatePicker dpEmployeeHiredate; // 입사일

	@FXML
	private Button btnCancle; // 취소 버튼
	@FXML
	private Button btnRegiste; // 등록 버튼

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		txtEmployeeCode.setDisable(true);

		// 직원 코드 자동 입력
		EmployeeVO evo = null;
		EmployeeDAO edao = null;

		edao = new EmployeeDAO();
		try {
			txtEmployeeCode.setText(edao.getEmployeeSequence(evo));
		} catch (Exception e) {
		}
		
		// 직원 등급 콤보박스 설정
		cbxEmployeeRank.setItems(FXCollections.observableArrayList("매니저", "부매니저", "사원"));

		// 엔터키 이벤트 적용
		txtEmployeeName.setOnKeyPressed(event -> handlerEmployeeNamePressed(event));
		txtEmployeePhone.setOnKeyPressed(event -> handlerEmployeePhonePressed(event));
		txtEmpolyeeAddress.setOnKeyPressed(event -> handlerEmployeeAdrsPressed(event));
		dpEmployeeBirth.setOnKeyPressed(event -> handlerEmployeeBirthPressed(event));
		cbxEmployeeRank.setOnKeyPressed(event -> handlerEmployeeRankPressed(event));

		// 등록 버튼 이벤트 핸들러
		btnRegiste.setOnAction(event -> handlerBtnRegisteAction(event));
		// 닫기 버튼 이벤트 핸들러
		btnCancle.setOnAction(event -> handlerBtnCancleAction(event));

	}

	// 등록 메소드
	public void employeeRegiste() {

		EmployeeVO evo = new EmployeeVO();
		EmployeeDAO edao = new EmployeeDAO();
		boolean registeResult = false;

		// 필드 미입력 오류
		try {
			if (txtEmployeeName.getText().trim().equals("")) { // 직원명 입력
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("직원 등록");
				alert.setHeaderText("직원명 미입력");
				alert.setContentText("다시 입력해주세요");
				alert.showAndWait();
			} else if(txtEmployeePhone.getText().trim().equals("")) { 
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("직원 등록");
				alert.setHeaderText("직원 핸드폰번호 미입력");
				alert.setContentText("다시 입력해주세요");
				alert.showAndWait();
			} else if(dpEmployeeBirth.getValue() == null) { 
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("직원 등록");
				alert.setHeaderText("직원 생년월일 미입력");
				alert.setContentText("다시 입력해주세요");
				alert.showAndWait();
			} else if(txtEmpolyeeAddress.getText().trim().equals("")) { 
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("직원 등록");
				alert.setHeaderText("직원 주소 미입력");
				alert.setContentText("다시 입력해주세요");
				alert.showAndWait();
			} else if(dpEmployeeHiredate.getValue() == null) { 
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("직원 등록");
				alert.setHeaderText("직원 입사일 미입력");
				alert.setContentText("다시 입력해주세요");
				alert.showAndWait();
			} else if(cbxEmployeeRank.getSelectionModel().getSelectedItem() == null) { 
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("직원 등록");
				alert.setHeaderText("직원 등급 미선택");
				alert.setContentText("다시 선택해주세요");
				alert.showAndWait();
			}
			 
		} catch (NumberFormatException e) {
			e.printStackTrace();
			System.out.println("넘버");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// JoinVO에 입력받은 id, ps, name을 공백제거후 넣어준다
		evo = new EmployeeVO(txtEmployeeCode.getText().trim(), txtEmployeeName.getText().trim(),
				txtEmployeePhone.getText().trim(), txtEmpolyeeAddress.getText().trim(),
				dpEmployeeBirth.getValue().toString(), cbxEmployeeRank.getSelectionModel().getSelectedItem().toString(),
				dpEmployeeHiredate.getValue().toString());
		// 인스턴스화
		edao = new EmployeeDAO();

		try {
			// JoinDAO에서 managerRegiste메소드를 호출하여 등록후 성공여부를 확인한다.
			registeResult = edao.getEmployeeRegiste(evo);

			// 등록이 성공하였을 경우
			if (registeResult) {
				// 닫기 버튼을 호출하여 창을 닫아준다
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("직원 등록");
				alert.setHeaderText("직원 등록 성공");
				alert.setContentText(evo.getE_name() + "직원이 등록되었습니다");
				alert.showAndWait();

				Stage stage = (Stage) btnRegiste.getScene().getWindow();
				stage.close();
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}

	// 닫기 버튼 이벤트 메소드
	public void handlerBtnCancleAction(ActionEvent event) {
		Stage stage = (Stage) btnCancle.getScene().getWindow();
		stage.close();
	}

	// 등록 버튼 이벤트 메소드
	private void handlerBtnRegisteAction(ActionEvent event) {
		employeeRegiste();
	}

	public void handlerEmployeeRankPressed(KeyEvent event) {
		// 엔터키가 발생할경우
		if (event.getCode() == KeyCode.ENTER) {
			// 등록 메소드 호출
			employeeRegiste();
		}
	}

	public void handlerEmployeeBirthPressed(KeyEvent event) {
		// 엔터키가 발생할경우
		if (event.getCode() == KeyCode.ENTER) {
			// 직원등급으로 포커스를 준다.
			cbxEmployeeRank.requestFocus();
		}
	}

	public void handlerEmployeeAdrsPressed(KeyEvent event) {
		// 엔터키가 발생할경우
		if (event.getCode() == KeyCode.ENTER) {
			// 생년월일으로 포커스를 준다.
			dpEmployeeBirth.requestFocus();
		}
	}

	public void handlerEmployeePhonePressed(KeyEvent event) {
		// 엔터키가 발생할경우
		if (event.getCode() == KeyCode.ENTER) {
			// 주소창으로 포커스를 준다.
			txtEmpolyeeAddress.requestFocus();
		}

	}

	public void handlerEmployeeNamePressed(KeyEvent event) {
		// 엔터키가 발생할경우
		if (event.getCode() == KeyCode.ENTER) {
			// 핸드폰번호창으로 포커스를 준다.
			txtEmployeePhone.requestFocus();
		}
	}

}
