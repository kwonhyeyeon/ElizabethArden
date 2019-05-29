package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.EmployeeVO;
import model.SaleVO;

public class StatusTabControllder implements Initializable {

	@FXML
	private TextField productName; // 상품명 입력
	@FXML
	private Button btnSearch; // 상품명 검색 버튼
	@FXML
	private TableView<SaleVO> productSalesStatus = new TableView<>(); // 제품별 판매현황 테이블
	@FXML
	private ComboBox<EmployeeVO> empName; // 직원명 콤보박스
	@FXML
	private ComboBox<String> month; // 월 콤보박스
	@FXML
	private Button btnMonSearch; // 직원별 월별 검색 버튼
	@FXML
	private TableView<SaleVO> empMonthSalesStatus = new TableView<>(); // 직원별 월별 판매현황 테이블

	ObservableList<SaleVO> productSaleData = FXCollections.observableArrayList(); // 제품별 판매현황 테이블 데이터
	ObservableList<SaleVO> empMonthData = FXCollections.observableArrayList(); // 직원별 월별 판매현황 테이블 데이터

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		employeeName(); // 직원 콤보박스 설정

		// 상태 콤보박스 설정
		month.setItems(FXCollections.observableArrayList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10",
				"11", "12"));

		// 제품별 판매현황 테이블 컬럼 지정
		TableColumn colProductCode = new TableColumn("상품코드");
		colProductCode.setPrefWidth(120);
		colProductCode.setStyle("-fx-alignment:CENTER");
		colProductCode.setCellValueFactory(new PropertyValueFactory<>("p_code"));

		TableColumn colProductName = new TableColumn("상품명");
		colProductName.setPrefWidth(450);
		colProductName.setStyle("-fx-alignment:CENTER_LEFT");
		colProductName.setCellValueFactory(new PropertyValueFactory<>("p_name"));

		TableColumn colProductEa = new TableColumn("수량");
		colProductEa.setPrefWidth(97);
		colProductEa.setStyle("-fx-alignment:CENTER");
		colProductEa.setCellValueFactory(new PropertyValueFactory<>("sr_ea"));

		TableColumn colProductPrice = new TableColumn("단가");
		colProductPrice.setPrefWidth(200);
		colProductPrice.setStyle("-fx-alignment:CENTER");
		colProductPrice.setCellValueFactory(new PropertyValueFactory<>("p_price"));

		TableColumn colProductTotal = new TableColumn("총액");
		colProductTotal.setPrefWidth(250);
		colProductTotal.setStyle("-fx-alignment:CENTER");
		colProductTotal.setCellValueFactory(new PropertyValueFactory<>("sr_total"));

		TableColumn colProductDate = new TableColumn("등록일");
		colProductDate.setPrefWidth(250);
		colProductDate.setStyle("-fx-alignment:CENTER");
		colProductDate.setCellValueFactory(new PropertyValueFactory<>("build_date"));

		TableColumn colProductStatus = new TableColumn("상태");
		colProductStatus.setPrefWidth(120);
		colProductStatus.setStyle("-fx-alignment:CENTER");
		colProductStatus.setCellValueFactory(new PropertyValueFactory<>("sr_state"));

		productSalesStatus.setItems(productSaleData);
		productSalesStatus.getColumns().addAll(colProductCode, colProductName, colProductEa, colProductPrice,
				colProductTotal, colProductDate, colProductStatus);

		// 직원별 월별 판매현황 테이블 컬럼 지정
		TableColumn colEmpPCode = new TableColumn("상품코드");
		colEmpPCode.setPrefWidth(150);
		colEmpPCode.setStyle("-fx-alignment:CENTER");
		colEmpPCode.setCellValueFactory(new PropertyValueFactory<>("p_code"));

		TableColumn colEmpPName = new TableColumn("상품명");
		colEmpPName.setPrefWidth(550);
		colEmpPName.setStyle("-fx-alignment:CENTER_LEFT");
		colEmpPName.setCellValueFactory(new PropertyValueFactory<>("p_name"));

		TableColumn colEmpPEa = new TableColumn("수량");
		colEmpPEa.setPrefWidth(120);
		colEmpPEa.setStyle("-fx-alignment:CENTER");
		colEmpPEa.setCellValueFactory(new PropertyValueFactory<>("sr_ea"));

		TableColumn colEmpPPrice = new TableColumn("단가");
		colEmpPPrice.setPrefWidth(200);
		colEmpPPrice.setStyle("-fx-alignment:CENTER");
		colEmpPPrice.setCellValueFactory(new PropertyValueFactory<>("p_price"));

		TableColumn colEmpPTotal = new TableColumn("총액");
		colEmpPTotal.setPrefWidth(250);
		colEmpPTotal.setStyle("-fx-alignment:CENTER");
		colEmpPTotal.setCellValueFactory(new PropertyValueFactory<>("sr_total"));

		TableColumn colEmpPStatus = new TableColumn("상태");
		colEmpPStatus.setPrefWidth(120);
		colEmpPStatus.setStyle("-fx-alignment:CENTER");
		colEmpPStatus.setCellValueFactory(new PropertyValueFactory<>("sr_state"));

		empMonthSalesStatus.setItems(empMonthData);
		empMonthSalesStatus.getColumns().addAll(colEmpPCode, colEmpPName, colEmpPEa, colEmpPPrice, colEmpPTotal,
				colEmpPStatus);

		// 상품명 검색 버튼 이벤트 핸들러
		btnSearch.setOnAction(event -> handlerBtnSearchAction(event));

		// 엔터키 적용
		productName.setOnKeyPressed(event -> handlerProductNamePressed(event));

	}

	public void handlerProductNamePressed(KeyEvent event) {
		// 엔터키가 발생할경우
		if (event.getCode() == KeyCode.ENTER) {
			productSearch(); // 검색 버튼 이벤트 메소드 호출
		}
	}

	// 상품명 검색 버튼 이벤트
	public void productSearch() {

		String p_name = productName.getText().trim(); // productName에서 검색한 상품명

		if (p_name.equals("")) { // 상품명이 공백으로 들어왔을 경우
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("제품별 판매현황");
			alert.setHeaderText("제품명 미입력");
			alert.setContentText("제품명을 다시 입력하세요");
			alert.showAndWait(); // 확인 창 누르기 전까지 대기
		} else {

			productSaleData.removeAll(productSaleData); // 제품별 판매현황 테이블의 정보를 지워준다

			ArrayList<SaleVO> productSale = new ArrayList<SaleVO>();

			SaleDAO sdao = null;
			SaleVO svo = null;

			sdao = new SaleDAO();
			productSale = sdao.getProductDate(p_name);

			if (productSale != null) {
				int rowCount = productSale.size();
				productName.clear();

				for (int index = 0; index < rowCount; index++) {
					svo = productSale.get(index);
					productSaleData.add(svo);
				}
			}

		}

	}

	// 상품명 검색 버튼 이벤트 메소드
	public void handlerBtnSearchAction(ActionEvent event) {
		productSearch();
	}

	// 직원명 가져오기
	public void employeeName() {

		EmployeeDAO edao = new EmployeeDAO();
		ArrayList employeeName = new ArrayList<>();

		try {
			employeeName = edao.getEmployeeTotalList();
			empName.setItems(FXCollections.observableArrayList(employeeName));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
