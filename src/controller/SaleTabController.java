package controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.CustomerVO;
import model.EmployeeVO;
import model.ProductVO;
import model.SaleVO;

public class SaleTabController implements Initializable {

	@FXML
	private TableView<ProductVO> tableProduct = new TableView<>(); // 재고 테이블
	@FXML
	private DatePicker dpDate; // 입력 날짜
	@FXML
	private ComboBox<EmployeeVO> cbxE_name; // 직원명 콤보박스
	@FXML
	private TextField txtC_name; // 고객명
	@FXML
	private Button btnC_search; // 검색 버튼
	@FXML
	private Label lblBirth; // 고객 생일 라벨
	@FXML
	private Label lblPhone; // 고객 폰번
	@FXML
	private Label lblAddress; // 고객주소
	@FXML
	private TableView<SaleVO> tableSaleInsert = new TableView<>(); // 판매입력 테이블
	@FXML
	private TableView<SaleVO> tableSaleList = new TableView<>(); // 판매현황 테이블
	@FXML
	private TextArea taBigo; // 비고
	@FXML
	private Button btnP_regi; // 등록 버튼
	@FXML
	private TextField p_ea; // 수량
	@FXML
	private ComboBox<String> cbxState; // 상태 콤보박스
	@FXML
	private TextField txtUsedPoint; // 포인트 사용금액
	@FXML
	private ComboBox<String> cbxReturnReason; // 반품사유 콤보박스

	ObservableList<ProductVO> productDataList = FXCollections.observableArrayList(); // 재고현황 테이블
	ObservableList<SaleVO> saleInsertDataList = FXCollections.observableArrayList(); // 판매입력 테이블
	ObservableList<SaleVO> saleListDataList = FXCollections.observableArrayList(); // 판매내역 테이블

	ObservableList<ProductVO> selectProduct = null; // 재고 테이블에서 선택한 정보 저장
	int selectedIndex; // 재고 테이블에서 선택한 상품 정보 인덱스 저장

	ObservableList<SaleVO> selectInsert = null; // 판매입력 테이블에서 선택한 정보 저장
	int selectedSaleIndex; // 판매입력 테이블에서 선택한 상품 정보 인덱스 저장

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		try {
			// tableSaleInsert.editingCellProperty(); // 테이블 수정
			productTotalList(); // 재고 현황 리스트
			employeeName(); // 직원명 콤보박스

			// 콤보박스 설정
			cbxState.setItems(FXCollections.observableArrayList("판매", "반품", "포인트 사용"));
			cbxReturnReason.setItems(FXCollections.observableArrayList("제조날짜초과", "변질", "트러블", "기타"));

			cbxState.setDisable(true); // 상태 콤보박스 비활성화
			txtUsedPoint.setDisable(true); // 포인트 사용금액 비활성화
			cbxReturnReason.setDisable(true); // 반품사유 콤보박스 비활성화
		} catch (Exception e) {
			e.printStackTrace();
		}

		dpDate.setValue(LocalDate.now()); // 오늘 날짜로 설정
		tableProduct.setEditable(false); // 재고현황 테이블 수정 금지

		// 재고 현황 테이블 컬럼 지정
		TableColumn colProductCode = new TableColumn("상품코드");
		colProductCode.setPrefWidth(80);
		colProductCode.setStyle("-fx-alignment:CENTER");
		colProductCode.setCellValueFactory(new PropertyValueFactory<>("p_code"));

		TableColumn colProductName = new TableColumn("상품명");
		colProductName.setPrefWidth(200);
		colProductName.setStyle("-fx-alignment:CENTER_LEFT");
		colProductName.setCellValueFactory(new PropertyValueFactory<>("p_name"));

		TableColumn colProductEa = new TableColumn("재고");
		colProductEa.setPrefWidth(50);
		colProductEa.setStyle("-fx-alignment:CENTER");
		colProductEa.setCellValueFactory(new PropertyValueFactory<>("p_ea"));

		TableColumn colProductPrice = new TableColumn("단가");
		colProductPrice.setPrefWidth(80);
		colProductPrice.setStyle("-fx-alignment:CENTER");
		colProductPrice.setCellValueFactory(new PropertyValueFactory<>("p_price"));

		TableColumn colProductTotal = new TableColumn("총액");
		colProductTotal.setPrefWidth(80);
		colProductTotal.setStyle("-fx-alignment:CENTER");
		colProductTotal.setCellValueFactory(new PropertyValueFactory<>("p_total"));

		TableColumn colProductPoint = new TableColumn("포인트");
		colProductPoint.setPrefWidth(50);
		colProductPoint.setStyle("-fx-alignment:CENTER");
		colProductPoint.setCellValueFactory(new PropertyValueFactory<>("p_point"));

		tableProduct.setItems(productDataList);
		tableProduct.getColumns().addAll(colProductCode, colProductName, colProductEa, colProductPrice, colProductTotal,
				colProductPoint);

		// 판매 입력 테이블 컬럼 지정
		TableColumn colInsertP_Code = new TableColumn("상품코드");
		colInsertP_Code.setPrefWidth(100);
		colInsertP_Code.setStyle("-fx-alignment:CENTER");
		colInsertP_Code.setCellValueFactory(new PropertyValueFactory<>("p_code"));

		TableColumn colInsertP_Name = new TableColumn("상품명");
		colInsertP_Name.setPrefWidth(230);
		colInsertP_Name.setStyle("-fx-alignment:CENTER");
		colInsertP_Name.setCellValueFactory(new PropertyValueFactory<>("p_name"));

		TableColumn colInsertSr_State = new TableColumn("상태");
		colInsertSr_State.setPrefWidth(80);
		colInsertSr_State.setStyle("-fx-alignment:CENTER");
		colInsertSr_State.setCellValueFactory(new PropertyValueFactory<>("sr_state"));

		TableColumn colInsertSr_ea = new TableColumn("수량");
		colInsertSr_ea.setPrefWidth(50);
		colInsertSr_ea.setStyle("-fx-alignment:CENTER");
		colInsertSr_ea.setCellValueFactory(new PropertyValueFactory<>("sr_ea"));

		TableColumn colInsertP_Price = new TableColumn("단가");
		colInsertP_Price.setPrefWidth(90);
		colInsertP_Price.setStyle("-fx-alignment:CENTER");
		colInsertP_Price.setCellValueFactory(new PropertyValueFactory<>("p_price"));

		TableColumn colInsertSr_Total = new TableColumn("총액");
		colInsertSr_Total.setPrefWidth(90);
		colInsertSr_Total.setStyle("-fx-alignment:CENTER");
		colInsertSr_Total.setCellValueFactory(new PropertyValueFactory<>("sr_total"));

		TableColumn colInsertP_Point = new TableColumn("포인트");
		colInsertP_Point.setPrefWidth(90);
		colInsertP_Point.setStyle("-fx-alignment:CENTER");
		colInsertP_Point.setCellValueFactory(new PropertyValueFactory<>("p_point"));

		TableColumn colInsertSr_Used_Point = new TableColumn("포인트 사용금액");
		colInsertSr_Used_Point.setPrefWidth(120);
		colInsertSr_Used_Point.setStyle("-fx-alignment:CENTER");
		colInsertSr_Used_Point.setCellValueFactory(new PropertyValueFactory<>("sr_used_point"));

		TableColumn colInsertSr_Return_Reason = new TableColumn("반품 사유");
		colInsertSr_Return_Reason.setPrefWidth(100);
		colInsertSr_Return_Reason.setStyle("-fx-alignment:CENTER");
		colInsertSr_Return_Reason.setCellValueFactory(new PropertyValueFactory<>("sr_return_reason"));

		tableSaleInsert.setItems(saleInsertDataList);
		tableSaleInsert.getColumns().addAll(colInsertP_Code, colInsertP_Name, colInsertSr_State, colInsertSr_ea,
				colInsertP_Price, colInsertSr_Total, colInsertP_Point, colInsertSr_Used_Point,
				colInsertSr_Return_Reason);

		// 판매 내역 테이블 컬럼 지정
		TableColumn colListNo = new TableColumn("NO");
		colListNo.setPrefWidth(30);
		colListNo.setStyle("-fx-alignment:CENTER");
		colListNo.setCellValueFactory(new PropertyValueFactory<>("no"));

		TableColumn colListP_Code = new TableColumn("상품코드");
		colListP_Code.setPrefWidth(100);
		colListP_Code.setStyle("-fx-alignment:CENTER");
		colListP_Code.setCellValueFactory(new PropertyValueFactory<>("p_code"));

		TableColumn colListP_Name = new TableColumn("상품명");
		colListP_Name.setPrefWidth(200);
		colListP_Name.setStyle("-fx-alignment:CENTER");
		colListP_Name.setCellValueFactory(new PropertyValueFactory<>("p_name"));

		TableColumn colListSr_State = new TableColumn("상태");
		colListSr_State.setPrefWidth(80);
		colListSr_State.setStyle("-fx-alignment:CENTER");
		colListSr_State.setCellValueFactory(new PropertyValueFactory<>("sr_state"));

		TableColumn colListSr_ea = new TableColumn("수량");
		colListSr_ea.setPrefWidth(50);
		colListSr_ea.setStyle("-fx-alignment:CENTER");
		colListSr_ea.setCellValueFactory(new PropertyValueFactory<>("sr_ea"));

		TableColumn colListP_Price = new TableColumn("단가");
		colListP_Price.setPrefWidth(90);
		colListP_Price.setStyle("-fx-alignment:CENTER");
		colListP_Price.setCellValueFactory(new PropertyValueFactory<>("p_price"));

		TableColumn colListSr_Total = new TableColumn("총액");
		colListSr_Total.setPrefWidth(90);
		colListSr_Total.setStyle("-fx-alignment:CENTER");
		colListSr_Total.setCellValueFactory(new PropertyValueFactory<>("sr_total"));

		TableColumn colListP_Point = new TableColumn("포인트");
		colListP_Point.setPrefWidth(90);
		colListP_Point.setStyle("-fx-alignment:CENTER");
		colListP_Point.setCellValueFactory(new PropertyValueFactory<>("p_point"));

		TableColumn colListSr_Used_Point = new TableColumn("포인트 사용금액");
		colListSr_Used_Point.setPrefWidth(120);
		colListSr_Used_Point.setStyle("-fx-alignment:CENTER");
		colListSr_Used_Point.setCellValueFactory(new PropertyValueFactory<>("sr_used_point"));

		TableColumn colListP_Build_Date = new TableColumn("판매 일자");
		colListP_Build_Date.setPrefWidth(100);
		colListP_Build_Date.setStyle("-fx-alignment:CENTER");
		colListP_Build_Date.setCellValueFactory(new PropertyValueFactory<>("p_build_date"));

		tableSaleList.setItems(saleListDataList);
		tableSaleList.getColumns().addAll(colListNo, colListP_Code, colListP_Name, colListSr_State, colListSr_ea,
				colListP_Price, colListSr_Total, colListP_Point, colListSr_Used_Point, colListP_Build_Date);

		// 재고 테이블뷰 더블 클릭 선택 이벤트 핸들러
		tableProduct.setOnMouseClicked(event -> handlerTableProductAction(event));
		// 판매 입력 테이블뷰 클릭 선택 이벤트 핸들러
		tableSaleInsert.setOnMouseClicked(event -> handlerTableSaleInsertAction(event));

		// 고객명 검색 버튼 이벤트 핸들러
		btnC_search.setOnAction(event -> handlerBtnCSearchAction(event));
		// 상태 콤보박스 이벤트 핸들러
		cbxState.setOnAction(event -> handlerCbxStateAction(event));
		// 등록 버튼 이벤트 핸들러
		btnP_regi.setOnAction(event -> handlerBtnPRegiAction(event));
	}

	// 등록 버튼 이벤트 메소드
	public void handlerBtnPRegiAction(ActionEvent event) {

	}

	// 판매 입력 클릭 이벤트 메소드
	public void handlerTableSaleInsertAction(MouseEvent event) {

		if (event.getClickCount() == 1) { // 판매 입력 클릭시
			try {

				cbxState.setDisable(false); // 상태 콤보박스 활성화

				// 테이블에서 선택한 정보를 selectSubject에 저장
				selectInsert = tableSaleInsert.getSelectionModel().getSelectedItems();
				selectedSaleIndex = selectInsert.get(0).getNo();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	// 상태 콤보박스 이벤트 메소드
	public void handlerCbxStateAction(ActionEvent event) {

		String state = cbxState.getSelectionModel().getSelectedItem();

		if (state.equals("판매")) {
			txtUsedPoint.setDisable(true); // 포인트 사용금액 비활성화
			cbxReturnReason.setDisable(true); // 반품사유 콤보박스 비활성화
		}

		if (state.equals("반품")) {
			txtUsedPoint.setDisable(true); // 포인트 사용금액 비활성화
			cbxReturnReason.setDisable(false); // 반품사유 콤보박스 활성화
		}

		if (state.equals("포인트 사용")) {
			txtUsedPoint.setDisable(false); // 포인트 사용금액 활성화
			cbxReturnReason.setDisable(true); // 반품사유 콤보박스 비활성화
		}

	}

	// 재고 테이블뷰 더블 클릭 이벤트 메소드
	public void handlerTableProductAction(MouseEvent event) {

		if (event.getClickCount() == 2) { // 더블클릭시
			try {

				// 테이블에서 선택한 정보를 selectSubject에 저장
				selectProduct = tableProduct.getSelectionModel().getSelectedItems();
				selectedIndex = selectProduct.get(0).getP_no();

				String selectedP_code = selectProduct.get(0).getP_code();
				String selectedP_name = selectProduct.get(0).getP_name();
				int selecetedP_ea = selectProduct.get(0).getP_ea();
				int selectedP_price = selectProduct.get(0).getP_price();
				int selectedP_total = selectProduct.get(0).getP_total();
				int selectedP_point = selectProduct.get(0).getP_point();
				selecetedP_ea = 1;
				selectedP_total = selecetedP_ea * selectedP_price;
				selectedP_point = selectedP_total / 100;

				// saleInsertDataList.removeAll();
				SaleVO svo = new SaleVO(selectedP_code, selectedP_name, selecetedP_ea, selectedP_price, selectedP_total,
						selectedP_point);

				saleInsertDataList.add(svo);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	// 고객명 검색 버튼 이벤트 메소드
	/**
	 * @param event
	 */
	public void handlerBtnCSearchAction(ActionEvent event) {

		// 고객명 검색 텍스트 필드 값
		String SearchName = "";
		SearchName = txtC_name.getText().trim();

		try {

			if (SearchName.equals("")) { // 검색어 필드 값이 공백일 때
				
				FXMLLoader loader = new FXMLLoader(); // fxml에서 객체를 로드
				loader.setLocation(getClass().getResource("/view/customerSearchList.fxml")); // 수정 모달창을 호출한다

				Stage dialog = new Stage(StageStyle.UTILITY); // 스테이지 스타일을 UTILITY로 설정해 dialog 생성
				dialog.initModality(Modality.WINDOW_MODAL); // 모달형태로 띄운다
				dialog.initOwner(btnC_search.getScene().getWindow()); // 소유자 윈도우를 지정
				dialog.setTitle("고객 검색"); // 타이틀 설정

				Parent parent = (Parent) loader.load();
				Scene scene = new Scene(parent);
				dialog.setScene(scene);
				dialog.setResizable(false);
				dialog.show();

				// customerSearchList.fxml에서 해당하는 id 객체 찾음
				TextField c_name = (TextField) parent.lookup("#c_name");
				c_name.setText(SearchName); // 새 창 고객명에 SearchName 값을 설정해줌
				Button btnSearch = (Button) parent.lookup("#btnSearch");
				TableView<CustomerVO> tableCustomerList = (TableView<CustomerVO>) parent.lookup("#tableCustomerList");

				ObservableList<CustomerVO> customerDataList = FXCollections.observableArrayList();

				// 고객 정보 리스트 컬럼 지정
				TableColumn colCustomerCode = new TableColumn("고객코드");
				colCustomerCode.setPrefWidth(115);
				colCustomerCode.setStyle("-fx-alignment:CENTER");
				colCustomerCode.setCellValueFactory(new PropertyValueFactory<>("c_code"));

				TableColumn colCustomerName = new TableColumn("고객명");
				colCustomerName.setPrefWidth(115);
				colCustomerName.setStyle("-fx-alignment:CENTER");
				colCustomerName.setCellValueFactory(new PropertyValueFactory<>("c_name"));

				TableColumn colCustomerPhone = new TableColumn("핸드폰번호");
				colCustomerPhone.setPrefWidth(115);
				colCustomerPhone.setStyle("-fx-alignment:CENTER");
				colCustomerPhone.setCellValueFactory(new PropertyValueFactory<>("c_phoneNumber"));

				TableColumn colCustomerBirth = new TableColumn("생년월일");
				colCustomerBirth.setPrefWidth(115);
				colCustomerBirth.setStyle("-fx-alignment:CENTER");
				colCustomerBirth.setCellValueFactory(new PropertyValueFactory<>("c_birth"));

				tableCustomerList.setItems(customerDataList);
				tableCustomerList.getColumns().addAll(colCustomerCode, colCustomerName, colCustomerPhone,
						colCustomerBirth);
				
				customerDataList.removeAll(customerDataList);

				// 고객 전체 리스트를 테이블뷰에 보임
				CustomerDAO cdao = new CustomerDAO();
				CustomerVO cvo = null;

				ArrayList<String> title;
				ArrayList<CustomerVO> list;

				title = cdao.getCustomerColumnName();
				int columnCount = title.size();

				list = cdao.getCustomerTotalList();
				int rowCount = list.size();

				for (int index = 0; index < rowCount; index++) {
					cvo = list.get(index);
					customerDataList.add(cvo);
				}
				
			} else {

				FXMLLoader loader = new FXMLLoader(); // fxml에서 객체를 로드
				loader.setLocation(getClass().getResource("/view/customerSearchList.fxml")); // 수정 모달창을 호출한다

				Stage dialog = new Stage(StageStyle.UTILITY); // 스테이지 스타일을 UTILITY로 설정해 dialog 생성
				dialog.initModality(Modality.WINDOW_MODAL); // 모달형태로 띄운다
				dialog.initOwner(btnC_search.getScene().getWindow()); // 소유자 윈도우를 지정
				dialog.setTitle("고객 검색"); // 타이틀 설정

				Parent parent = (Parent) loader.load();
				Scene scene = new Scene(parent);
				dialog.setScene(scene);
				dialog.setResizable(false);
				dialog.show();

				// customerSearchList.fxml에서 해당하는 id 객체 찾음
				TextField c_name = (TextField) parent.lookup("#c_name");
				c_name.setText(SearchName); // 새 창 고객명에 SearchName 값을 설정해줌
				Button btnSearch = (Button) parent.lookup("#btnSearch");
				TableView<CustomerVO> tableCustomerList = (TableView<CustomerVO>) parent.lookup("#tableCustomerList");

				ObservableList<CustomerVO> customerDataList = FXCollections.observableArrayList();

				// 고객 정보 리스트 컬럼 지정
				TableColumn colCustomerCode = new TableColumn("고객코드");
				colCustomerCode.setPrefWidth(115);
				colCustomerCode.setStyle("-fx-alignment:CENTER");
				colCustomerCode.setCellValueFactory(new PropertyValueFactory<>("c_code"));

				TableColumn colCustomerName = new TableColumn("고객명");
				colCustomerName.setPrefWidth(115);
				colCustomerName.setStyle("-fx-alignment:CENTER");
				colCustomerName.setCellValueFactory(new PropertyValueFactory<>("c_name"));

				TableColumn colCustomerPhone = new TableColumn("핸드폰번호");
				colCustomerPhone.setPrefWidth(115);
				colCustomerPhone.setStyle("-fx-alignment:CENTER");
				colCustomerPhone.setCellValueFactory(new PropertyValueFactory<>("c_phoneNumber"));

				TableColumn colCustomerBirth = new TableColumn("생년월일");
				colCustomerBirth.setPrefWidth(115);
				colCustomerBirth.setStyle("-fx-alignment:CENTER");
				colCustomerBirth.setCellValueFactory(new PropertyValueFactory<>("c_birth"));

				tableCustomerList.setItems(customerDataList);
				tableCustomerList.getColumns().addAll(colCustomerCode, colCustomerName, colCustomerPhone,
						colCustomerBirth);

				String selectName = c_name.getText().trim();

				// 검색 버튼 이벤트
				btnSearch.setOnAction(e -> {

					try {
						if (c_name.getText().equals("")) {

							customerDataList.removeAll(customerDataList);

							CustomerDAO cdao = new CustomerDAO();
							CustomerVO cvo = null;

							ArrayList<String> title;
							ArrayList<CustomerVO> list;

							title = cdao.getCustomerColumnName();
							int columnCount = title.size();

							list = cdao.getCustomerTotalList();
							int rowCount = list.size();

							for (int index = 0; index < rowCount; index++) {
								cvo = list.get(index);
								customerDataList.add(cvo);
							}

						} else {

						}
					} catch (Exception e1) {
						e1.printStackTrace();
					}

				});

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// 재고 현황 리스트
	public void productTotalList() throws Exception {

		productDataList.removeAll(productDataList);
		ProductDAO pDao = new ProductDAO();
		ProductVO pVo = null;

		ArrayList<String> title;
		ArrayList<ProductVO> list;

		title = pDao.getProductColumnName();
		int columnCount = title.size();

		list = pDao.getProductTotalList();
		int rowCount = list.size();

		for (int index = 0; index < rowCount; index++) {
			pVo = list.get(index);
			productDataList.add(pVo);
		}

	}

	// 직원명 가져오기
	public void employeeName() {

		EmployeeDAO edao = new EmployeeDAO();
		ArrayList employeeName = new ArrayList<>();

		try {
			employeeName = edao.getEmployeeTotalList();
			cbxE_name.setItems(FXCollections.observableArrayList(employeeName));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
