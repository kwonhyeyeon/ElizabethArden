package controller;

import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.EmployeeVO;

public class EmployeeEditController implements Initializable {

	@FXML
	private ComboBox<String> cbxEmployeeName; // 직원명
	@FXML
	private TextField txtEmployeePhone; // 직원 핸드폰번호
	@FXML
	private TextField txtEmployeeAddress; // 직원 주소
	@FXML
	private ComboBox<String> cbxEmployeeRank; // 직원 등급
	@FXML
	private Button btnCancle; // 취소 버튼
	@FXML
	private Button btnEdit; // 변경 버튼
	
	private String selectedEmployeeName; // 선택한 직원명

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		employeeName(); // 직원명 가져오는 메소드

		// 직원 등급 콤보박스 설정
		cbxEmployeeRank.setItems(FXCollections.observableArrayList("매니저", "부매니저", "사원"));

		// 엔터키 이벤트 적용
		cbxEmployeeName.setOnKeyPressed(event -> handlerEmployeeNamePressed(event));
		txtEmployeePhone.setOnKeyPressed(event -> handlerEmployeePhonePressed(event));
		txtEmployeeAddress.setOnKeyPressed(event -> handlerEmployeeAdrsPressed(event));
		cbxEmployeeRank.setOnKeyPressed(event -> handlerEmployeeRankPressed(event));

		// 등록 버튼 이벤트 핸들러
		btnEdit.setOnAction(event -> handlerBtnEditAction(event));
		// 닫기 버튼 이벤트 핸들러
		btnCancle.setOnAction(event -> handlerBtnCancleAction(event));
		// 직원명 콤보박스 이벤트 핸들러
		cbxEmployeeName.setOnAction(event -> handlerCbxEmployeeNameAction(event));
	}

	// 직원명 콤보박스 이벤트 메소드
	public void handlerCbxEmployeeNameAction(ActionEvent event) {
		
		//ArrayList<EmployeeVO> list = new ArrayList();
		
		EmployeeDAO edao = new EmployeeDAO();
		EmployeeVO evo = new EmployeeVO();
		//String a = "이인섭";
		try {	
			selectedEmployeeName = edao.getEmployeePhone(cbxEmployeeName.getSelectionModel().getSelectedItem() + "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		txtEmployeePhone.setText(evo.getE_phonenumber()+ "");
		txtEmployeeAddress.setText(evo.getE_address());
		cbxEmployeeRank.setValue(evo.getE_rank());
		
	}

	// 직원명 가져오기
	public void employeeName() {

		EmployeeDAO edao = new EmployeeDAO();
		ArrayList employeeName = new ArrayList<>();

		try {
			employeeName = edao.getEmployeeTotalList();
			cbxEmployeeName.setItems(FXCollections.observableArrayList(employeeName));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// 등록 버튼 이벤트 메소드
	public void handlerBtnEditAction(ActionEvent event) {
		btnRegiste(); // 등록 메소드 호출
	}

	// 등록 메소드
	public void btnRegiste() {

		EmployeeVO evo = new EmployeeVO();
		EmployeeDAO edao = new EmployeeDAO();
		boolean employeeUpdateSucess = false; // 정보 변경 성공 여부

		try {

			if (cbxEmployeeName.getSelectionModel().getSelectedItem() == null) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("직원 정보 수정");
				alert.setHeaderText("직원명 미선택");
				alert.setContentText("다시 선택해주세요");
				alert.showAndWait();
			} else if (txtEmployeePhone.getText().equals("")) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("직원 정보 수정");
				alert.setHeaderText("직원 핸드폰번호 미입력");
				alert.setContentText("다시 입력해주세요");
				alert.showAndWait();
			} else if (txtEmployeeAddress.getText().equals("")) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("직원 정보 수정");
				alert.setHeaderText("직원 주소 미입력");
				alert.setContentText("다시 입력해주세요");
				alert.showAndWait();
			} else if (cbxEmployeeRank.getSelectionModel().getSelectedItem() == null) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("직원 정보 수정");
				alert.setHeaderText("직원 등급 미선택");
				alert.setContentText("다시 선택해주세요");
				alert.showAndWait();
			}

			// JoinDAO에서 getEmployeeRegiste메소드를 호출하여 등록후 성공여부를 확인한다.
			employeeUpdateSucess = edao.getEmployeeRegiste(evo);

			// 등록이 성공하였을 경우
			if (employeeUpdateSucess) {
				// 닫기 버튼을 호출하여 창을 닫아준다
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("직원 정보 수정");
				alert.setHeaderText("직원 정보 수정 성공");
				alert.setContentText(evo.getE_name() + "직원의 정보가 수정되었습니다");
				alert.showAndWait();

				Stage stage = (Stage) btnEdit.getScene().getWindow();
				stage.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// 닫기 버튼 이벤트 메소드
	public void handlerBtnCancleAction(ActionEvent event) {
		Stage stage = (Stage) btnCancle.getScene().getWindow();
		stage.close();
	}

	public void handlerEmployeeRankPressed(KeyEvent event) {
		// 엔터키가 발생할경우
		if (event.getCode() == KeyCode.ENTER) {
			btnRegiste(); // 등록 메소드 호출
		}
	}

	public void handlerEmployeeAdrsPressed(KeyEvent event) {
		// 엔터키가 발생할경우
		if (event.getCode() == KeyCode.ENTER) {
			// 직원등급창으로 포커스를 준다.
			cbxEmployeeRank.requestFocus();
		}
	}

	public void handlerEmployeePhonePressed(KeyEvent event) {
		// 엔터키가 발생할경우
		if (event.getCode() == KeyCode.ENTER) {
			// 주소창으로 포커스를 준다.
			txtEmployeeAddress.requestFocus();
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
