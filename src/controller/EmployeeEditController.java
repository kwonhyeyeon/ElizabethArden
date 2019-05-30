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

	private static String selectedName; // 선택된 직원명

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

		try {

			EmployeeDAO edao = new EmployeeDAO();
			// 선택된 직원명 인덱스값 
			int selectedNameIndex = cbxEmployeeName.getSelectionModel().getSelectedIndex();
			// 배열 생성
			ArrayList<EmployeeVO> list = new ArrayList();
			// 직원명을 가져오는 메소드를 list에 저장
			list = edao.getEmployeeTotalList();

			// 선택된 직원명
			selectedName = list.remove(selectedNameIndex).toString();

			EmployeeVO evo = new EmployeeVO();

			list = edao.getEmployeeInfo(selectedName); // 직원명을 배열에 넣어준다

			// 핸드폰, 주소, 직원 등급의 값을 가져와 설정해준다
			txtEmployeePhone.setText(list.get(0).getE_phonenumber());
			txtEmployeeAddress.setText(list.get(0).getE_address());
			cbxEmployeeRank.setValue(list.get(0).getE_rank());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 직원명 가져오기
	public void employeeName() {

		EmployeeDAO edao = new EmployeeDAO();
		ArrayList employeeName = new ArrayList<>();

		try {
			employeeName = edao.getEmployeeTotalList(); // 직원 전체 목록을 가져와 저장
			// 콤보박스에 이름을 설정해준다
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
			// 미입력시 오류
			if (cbxEmployeeName.getSelectionModel().getSelectedItem() == null) { // 직원명 미선택
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("직원 정보 수정");
				alert.setHeaderText("직원명 미선택");
				alert.setContentText("다시 선택해주세요");
				alert.showAndWait();
			} else if (txtEmployeePhone.getText().equals("")) { // 핸드폰 번호 미입력
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("직원 정보 수정");
				alert.setHeaderText("직원 핸드폰번호 미입력");
				alert.setContentText("다시 입력해주세요");
				alert.showAndWait();
			} else if (txtEmployeeAddress.getText().equals("")) { // 주소 미입력
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("직원 정보 수정");
				alert.setHeaderText("직원 주소 미입력");
				alert.setContentText("다시 입력해주세요");
				alert.showAndWait();
			} else if (cbxEmployeeRank.getSelectionModel().getSelectedItem() == null) { // 직원등급 미선택
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("직원 정보 수정");
				alert.setHeaderText("직원 등급 미선택");
				alert.setContentText("다시 선택해주세요");
				alert.showAndWait();
			}
			
			// 받아온 값 evo 객체에 저장
			evo.setE_name(selectedName);
			evo.setE_phonenumber(txtEmployeePhone.getText());
			evo.setE_address(txtEmployeeAddress.getText());
			evo.setE_rank(cbxEmployeeRank.getSelectionModel().getSelectedItem());
			
			// JoinDAO에서 getEmployeeRegiste메소드를 호출하여 등록후 성공여부를 확인한다.
			employeeUpdateSucess = edao.getEmployeeUpdate(evo.getE_name(), evo.getE_phonenumber(), evo.getE_address(), evo.getE_rank());
			
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
