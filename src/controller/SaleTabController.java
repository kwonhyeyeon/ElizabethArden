package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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
	private TableView<SaleVO> tableProduct2 = new TableView<>(); // 판매입력 테이블
	@FXML
	private TableView<SaleVO> tableProductList = new TableView<>(); // 판매현황 테이블
	@FXML
	private TextArea taBigo; // 비고
	SaleDAO sdao = new SaleDAO();

	ObservableList<ProductVO> productDataList = FXCollections.observableArrayList();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

		tableProduct.setEditable(false);

		@SuppressWarnings("rawtypes") // 제네릭을 사용하는 클래스 매개 변수가 불특정일 때의 경고 억제
		TableColumn colProductCode = new TableColumn("상품코드");
		colProductCode.setPrefWidth(70);
		colProductCode.setStyle("-fx-alignment:CENTER");
		colProductCode.setCellValueFactory(new PropertyValueFactory<>("p_code"));

		TableColumn colProductName = new TableColumn("상품명");
		colProductName.setPrefWidth(70);
		colProductName.setStyle("-fx-alignment:CENTER");
		colProductName.setCellValueFactory(new PropertyValueFactory<>("p_name"));

		TableColumn colProductEa = new TableColumn("재고");
		colProductEa.setPrefWidth(50);
		colProductEa.setStyle("-fx-alignment:CENTER");
		colProductEa.setCellValueFactory(new PropertyValueFactory<>("p_ea"));

		TableColumn colProductPrice = new TableColumn("단가");
		colProductPrice.setPrefWidth(50);
		colProductPrice.setStyle("-fx-alignment:CENTER");
		colProductPrice.setCellValueFactory(new PropertyValueFactory<>("p_price"));

		TableColumn colProductTotal = new TableColumn("총액");
		colProductTotal.setPrefWidth(50);
		colProductTotal.setStyle("-fx-alignment:CENTER");
		colProductTotal.setCellValueFactory(new PropertyValueFactory<>("p_total"));

		TableColumn colProductPoint = new TableColumn("포인트");
		colProductPoint.setPrefWidth(50);
		colProductPoint.setStyle("-fx-alignment:CENTER");
		colProductPoint.setCellValueFactory(new PropertyValueFactory<>("p_point"));

		tableProduct.setItems(productDataList);
		tableProduct.getColumns().addAll(colProductCode, colProductName, colProductEa, colProductPrice, colProductTotal,
				colProductPoint);
	}

	// 상품 전체 목록
	public void productTotalList() throws Exception {

		productDataList.removeAll(productDataList);
		SaleDAO sDao = new SaleDAO();
		ProductVO pVo = null;

		ArrayList<String> title;
		ArrayList<ProductVO> list;

		title = sDao.getProductColumnName();
		int columnCount = title.size();

		list = sDao.getProductTotalList();
		int rowCount = list.size();

		for (int index = 0; index < rowCount; index++) {
			pVo = list.get(index);
			productDataList.add(pVo);
			
		}

	}

}
