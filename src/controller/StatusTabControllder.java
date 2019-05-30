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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
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
	private ComboBox<EmployeeVO> empName; // 직원명 콤보박스 <직원객체타입>
	@FXML
	private ComboBox<String> month; // 월 콤보박스
	@FXML
	private Button btnMonSearch; // 직원별 월별 검색 버튼
	@FXML
	private TableView<SaleVO> empMonthSalesStatus = new TableView<>(); // 직원별 월별 판매현황 테이블
	@FXML
	private Label productname; // 상품명
	@FXML
	private Label productStatus; // 제품별 총 수량, 총액 라벨(판매, 포인트사용)
	@FXML
	private Label productStatus2; // 제품별 총 수량, 총액 라벨(반품)
	@FXML
	private Label empMonthStatus; // 직원별 월별 총 수량, 총액 라벨(판매, 포인트사용)
	@FXML
	private Label empMonthStatus2; // 직원별 월별 총 수량, 총액 라벨(반품)

	ObservableList<SaleVO> productSaleData = FXCollections.observableArrayList(); // 제품별 판매현황 테이블 데이터 <SaleVO타입>
	ObservableList<SaleVO> empMonthData = FXCollections.observableArrayList(); // 직원별 월별 판매현황 테이블 데이터 <SaleVO타입>

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		employeeName(); // 직원 콤보박스 설정

		// 상태 콤보박스 설정
		// select문에 월을 입력할때 mm을 두자리로 입력해야하기에 01, 02~로 설정.
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
		// 테이블에 SaleVO타입의 배열을 넣어준다.
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
		// 테이블에 SaleVO타입의 배열을 넣어준다.
		empMonthSalesStatus.setItems(empMonthData);
		empMonthSalesStatus.getColumns().addAll(colEmpPCode, colEmpPName, colEmpPEa, colEmpPPrice, colEmpPTotal,
				colEmpPStatus);

		// 상품명 검색 버튼 이벤트 핸들러
		btnSearch.setOnAction(event -> handlerBtnSearchAction(event));

		// 엔터키 적용
		productName.setOnKeyPressed(event -> handlerProductNamePressed(event));

		// 직원별 월별 검색 버튼이벤트 메소드
		btnMonSearch.setOnAction(event -> handlerbtnMonSearchAction(event));
		// 상품 판매현황 테이블 클릭 선택 이벤트 핸들러
		productSalesStatus.setOnMouseClicked(event -> handlerproductSalesStatusAction(event));

	}

	// 상품 판매현황 테이블 클릭 선택 이벤트 핸들러
	public void handlerproductSalesStatusAction(MouseEvent event) {
		// TODO Auto-generated method stub
		// 클릭이벤트가 1번 발생했을경우
		if (event.getClickCount() == 1) {
			// 클릭한곳의 인덱스번호를 가져온다.
			int seletedIndex = productSalesStatus.getSelectionModel().getSelectedIndex();
			// 클릭한곳이 비어있을경우 index번호는 -1이므로 아무일도 일어나지 않는다.
			if (seletedIndex >= 0) {
				// 클릭한곳의 인덱스번호가 0이랑 같거나 클경우

				// 인스턴스 생성
				SaleVO svo = new SaleVO();
				SaleDAO sdao = new SaleDAO();
				int sale_ea = 0; // 판매및 포인트사용의 수량
				int sale_total = 0; // 판매및 포인트 사용의 총액

				int return_ea = 0; // 반품 수량
				int return_total = 0; // 반품총액
				// 배열생성
				ArrayList<SaleVO> list = new ArrayList();
				// 클릭한행의 p_code를 가져온다
				String selectedP_code = productSalesStatus.getSelectionModel().getSelectedItem().getP_code();
				// 클릭한행의 p_name을 가져온다
				String selectedP_name = productSalesStatus.getSelectionModel().getSelectedItem().getP_name();

				// 가져온 p_code를 총판매갯수와 총액을 select해오는 메소드에 넣어주고 검색한다.
				// 리턴값 ArrayList배열
				list = sdao.getSaleDate(selectedP_code);

				// 리턴받아온 배열의 사이즈가 0이 아닐경우
				if (list.size() != 0) {
					// 결과값이 있어 리턴받아올경우 사이즈는 1이다.

					// 리턴받아온 배열의 인덱스번호0번쨰의 수량을 판매수량 변수에 넣어준다
					sale_ea = list.get(0).getSr_ea();
					// 리턴받아온 배열의 인덱스번호 0번째의 총액을 변수에 넣어준다.
					sale_total = list.get(0).getSr_total();

					// 배열을 재활용하기위해 담겨져 있던 정보를 비워준다.
					list.remove(0);
				}

				// 포인트 사용도 위와 같은방법
				list = sdao.getUsed_pointDate(selectedP_code);
				if (list.size() != 0) {
					// 리턴받아온 수량을 판매수량에 더해서 변수를 설정해준다.
					sale_ea = sale_ea + list.get(0).getSr_ea();
					// 리턴받아온 총액을 판매총액에 더해서 변수에 설정해준다.
					sale_total = sale_total + list.get(0).getSr_total();
					// 배열의 재활용을 위해 지워준다.
					list.remove(0);
				}
				// 반품도 같은 방법
				list = sdao.getReturnDate(selectedP_code);

				if (list.size() != 0) {
					// 리턴받아온 반품수량은 판매와는 다른 라벨에 출력하기 위해 다른 변수에 넣어준다
					return_ea = list.get(0).getSr_ea();
					// 리턴받아온 반품총액은 판매와는 다른 라벨에 출력하기 위해 다른 변수에 넣어준다
					return_total = list.get(0).getSr_total();

					list.remove(0);
				}
				// 상품명 라벨, 판매, 반품 라벨에 변수에 저장된 정보들로 설정해준다.
				productname.setText("[ " + selectedP_name + " ]");
				productStatus.setText("판매 : 수량 : " + sale_ea + "개             총액 : " + sale_total + "원");
				productStatus2.setText("반품 : 수량 : " + return_ea + "개             총액 : " + return_total + "원");

			}

		}
	}

	// 직원별 월별 검색 버튼이벤트 메소드
	public void handlerbtnMonSearchAction(ActionEvent event) {
		// 콤보박스값이 설정되지 않고 이벤트가 발생했을경우
		if (empName.getValue() == null || month.getValue() == null) {
			// 담겨져있던 정보들을 지운다.
			empMonthData.removeAll(empMonthData);
			Alert alert;
			alert = new Alert(AlertType.WARNING);
			alert.setTitle("입력오류");
			alert.setHeaderText("검색값을 설정하십시오.");
			alert.setContentText("");
			// 경고창 크기설정 불가
			alert.setResizable(false);
			// 경고창을 보여주고 기다린다
			alert.showAndWait();
		} else {
			// 콤보박스에 값이 설정된후 검색버튼 이벤트가 발생했을경우.
			try {
				// 인스턴스 생성
				EmployeeDAO edao = new EmployeeDAO();
				SaleDAO sdao = new SaleDAO();
				SaleVO svo = new SaleVO();
				// 배열생성
				ArrayList<SaleVO> list = new ArrayList();
				// 콤보박스에서 선택된 직원명을 메소드의 매개변수로 넣고 직원코드를 반환받는다
				String e_code = edao.getEmployeeCode(empName.getValue().toString());

				// 콤보박스에서 선택된 월과 반환받은 직원코드를 매개변수에 넣고 리턴된 배열 list를 새로 생성한 배열에 넣어준다.
				list = sdao.getEmployeeMonthDate(month.getValue().toString(), e_code);
				if (list.size() == 0) {
					// 배열의 사이즈가 0일경우 (리턴된 데이터가 없다)
					Alert alert;
					alert = new Alert(AlertType.WARNING);
					alert.setTitle("검색완료");
					alert.setHeaderText("입력된 데이터가 없습니다");
					alert.setContentText("");
					// 경고창 크기설정 불가
					alert.setResizable(false);
					// 경고창을 보여주고 기다린다
					alert.showAndWait();
				} else {
					// 리턴된 데이터가 있을경우

					// 테이블에 있던 데이터를 지운다.
					empMonthData.removeAll(empMonthData);

					int totalEa = 0; // 판매, 포인트 사용 수량
					int totalPrice = 0; // 판매, 포인트 사용 총액
					int totalReturnEa = 0; // 반품 사용 수량
					int totalReturnPrice = 0; // 반품 총액

					// 리턴받아온 배열의 사이즈만큼 반복문 실행
					for (int index = 0; index < list.size(); index++) {
						// 배열의 사이즈만큼 list<SaleVO>의 데이터를 객체에 넣어준다.
						svo = list.get(index);
						// 리스트배열에서 받은 데이터를 테이블에 추가시킨다.
						empMonthData.add(svo);

						int ea = empMonthData.get(index).getSr_ea(); // 수량
						int price = empMonthData.get(index).getSr_total(); // 총액
						String status = empMonthData.get(index).getSr_state().trim(); // 판매유형

						// 판매유형이 반품일경우
						if (status.equals("반품")) {
							// 반품 총 수량을 구해준다
							totalReturnEa += ea;
							// 반품 총액을 구해준다.
							totalReturnPrice += price;
						} else {
							// 판매유형이 반품이 아닐경우.

							// 판매및 포인트 사용의 총 수량을 구해준다
							totalEa += ea;
							// 판매및 포인트 사용의 총액을 구해준다.
							totalPrice += price;
						}

					}
					// 라벨에 계산된 총 수량과 총액으로 설정해준다.
					empMonthStatus2.setText("반품 총 수량 : " + totalReturnEa + "개" + "\t총액 : " + totalReturnPrice + "원"); // 반품
					empMonthStatus.setText("판매 총 수량 : " + totalEa + "개" + "\t총액 : " + totalPrice + "원"); // 판매,

				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	// 엔터키 이벤트 핸들러
	public void handlerProductNamePressed(KeyEvent event) {
		// 엔터키가 발생할경우
		if (event.getCode() == KeyCode.ENTER) {
			productSearch(); // 검색 버튼 이벤트 메소드 호출
		}
	}

	// 상품명 검색 메소드
	public void productSearch() {
		String p_name = productName.getText().trim(); // productName에서 검색한 상품명

		if (p_name.equals("")) { // 상품명이 공백으로 들어왔을 경우
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("제품별 판매현황");
			alert.setHeaderText("제품명 미입력");
			alert.setContentText("제품명을 다시 입력하세요");
			alert.showAndWait(); // 확인 창 누르기 전까지 대기
		} else {
			// 상품명이 공백이 아닐경우

			// 테이블에 모든 데이터를 지운다.
			productSaleData.removeAll(productSaleData); // 제품별 판매현황 테이블의 정보를 지워준다

			// 배열생성
			ArrayList<SaleVO> productSale = new ArrayList<SaleVO>();

			// 인스턴스 선언
			SaleDAO sdao = null;
			SaleVO svo = null;

			// 인스턴스 생성
			sdao = new SaleDAO();
			// 제품별 판매현황을 가져오는 메소드의 매개변수에 입력받은 상품명을 넣어주고
			// SaleVO타입의 객체배열을 반환받는다.
			productSale = sdao.getProductDate(p_name);

			// 반환된값이 null이 아닐경우
			if (productSale != null) {
				// 배열사이즈 저장
				int rowCount = productSale.size();
				// 상품명 텍스트상자를 비워준다.
				productName.clear();
				// 제품별 판매현황메소드에서 리턴받아온 배열의 사이즈만큼 반복문 실행
				for (int index = 0; index < rowCount; index++) {
					// 객체배열의 index번째를 객체에 담는다
					svo = productSale.get(index);
					// 데이터가 담긴 객체를 테이블에 추가시킨다.
					productSaleData.add(svo);
				}
			}

		}

	}

	// 상품명 검색 버튼 이벤트 메소드
	public void handlerBtnSearchAction(ActionEvent event) {
		// 상품명 검색버튼 메소드 호출
		productSearch();
	}

	// 직원명 가져오기
	public void employeeName() {
		// 인스턴스 생성
		EmployeeDAO edao = new EmployeeDAO();
		// 배열 생성
		ArrayList employeeName = new ArrayList<>();

		try {
			// 전체 직원명을 가져와서 배열에 넣어준다.
			employeeName = edao.getEmployeeTotalList();
			// 직원명 콤보박스에 리턴받아온 배열을 넣어준다.
			empName.setItems(FXCollections.observableArrayList(employeeName));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
