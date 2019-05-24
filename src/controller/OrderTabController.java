package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.OrderVO;
import model.ProductVO;

public class OrderTabController implements Initializable {

	@FXML
	TableView<ProductVO> tableProduct; // 재고현황 테이블
	@FXML
	TableView<ProductVO> tableOrder; // 주문 등록 테이블

	ObservableList<ProductVO> productDataList = FXCollections.observableArrayList(); // 재고현황 테이블
	ObservableList<ProductVO> orderDataList = FXCollections.observableArrayList(); // 주문 등록 테이블

	ObservableList<ProductVO> selectProduct = null; // 재고 테이블에서 선택한 정보 저장
	int selectedIndex; // 재고 테이블에서 선택한 상품 정보 인덱스 저장

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// 재고 현황 테이블 컬럼 지정
		TableColumn colProductCode = new TableColumn("상품코드");
		colProductCode.setPrefWidth(80);
		colProductCode.setStyle("-fx-alignment:CENTER");
		colProductCode.setCellValueFactory(new PropertyValueFactory<>("p_code"));

		TableColumn colProductName = new TableColumn("상품명");
		colProductName.setPrefWidth(175);
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
		colProductTotal.setPrefWidth(130);
		colProductTotal.setStyle("-fx-alignment:CENTER");
		colProductTotal.setCellValueFactory(new PropertyValueFactory<>("p_total"));

		tableProduct.setItems(productDataList);
		tableProduct.getColumns().addAll(colProductCode, colProductName, colProductEa, colProductPrice, colProductTotal);

		// 주문 등록 테이블 컬럼 지정
		TableColumn colOrderPCode = new TableColumn("상품코드");
		colOrderPCode.setPrefWidth(100);
		colOrderPCode.setStyle("-fx-alignment:CENTER");
		colOrderPCode.setCellValueFactory(new PropertyValueFactory<>("p_code"));

		TableColumn colOrderPName = new TableColumn("상품명");
		colOrderPName.setPrefWidth(480);
		colOrderPName.setStyle("-fx-alignment:CENTER");
		colOrderPName.setCellValueFactory(new PropertyValueFactory<>("p_name"));

		TableColumn colOrderEa = new TableColumn("수량");
		colOrderEa.setPrefWidth(100);
		colOrderEa.setStyle("-fx-alignment:CENTER");
		colOrderEa.setCellValueFactory(new PropertyValueFactory<>("p_ea"));

		TableColumn colOrderPrice = new TableColumn("단가");
		colOrderPrice.setPrefWidth(120);
		colOrderPrice.setStyle("-fx-alignment:CENTER");
		colOrderPrice.setCellValueFactory(new PropertyValueFactory<>("p_price"));

		TableColumn colOrderTotal = new TableColumn("총액");
		colOrderTotal.setPrefWidth(150);
		colOrderTotal.setStyle("-fx-alignment:CENTER");
		colOrderTotal.setCellValueFactory(new PropertyValueFactory<>("p_total"));

		tableOrder.setItems(orderDataList);
		tableOrder.getColumns().addAll(colOrderPCode, colOrderPName, colOrderEa, colOrderPrice,
				colOrderTotal);

		// 재고 테이블뷰 더블 클릭 선택 이벤트 핸들러
		tableProduct.setOnMouseClicked(event -> handlerTableProductAction(event));

	}

	// 재고 테이블뷰 더블 클릭 이벤트 메소드
	public void handlerTableProductAction(MouseEvent event) {

		if (event.getClickCount() == 2) { // 더블클릭시
			
			try {

				// 테이블에서 선택한 정보를 selectProduct에 저장
				selectProduct = tableProduct.getSelectionModel().getSelectedItems();
				selectedIndex = selectProduct.get(0).getP_no();

				String selectedP_code = selectProduct.get(0).getP_code();
				String selectedP_name = selectProduct.get(0).getP_name();
				int selecetedP_ea = selectProduct.get(0).getP_ea();
				int selectedP_price = selectProduct.get(0).getP_price();
				int selectedP_total = selecetedP_ea * selectedP_price;
				
				ProductVO pvo = new ProductVO(selectedP_code, selectedP_name, 1, selectedP_price,
						selectedP_total);
				
				orderDataList.add(pvo);

			} catch (Exception e) {
				e.printStackTrace();
			}
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

}
