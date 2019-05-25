package controller;

import java.net.URL;
import java.nio.channels.Selector;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.OrderVO;
import model.ProductVO;

public class OrderTabController implements Initializable {

	@FXML
	private TableView<ProductVO> tableProduct; // 재고현황 테이블
	@FXML
	private TableView<OrderVO> tableOrder; // 주문 등록 테이블
	@FXML
	private TextField or_ea; // 수량
	@FXML
	private Button btnRegiste; // 등록 버튼
	@FXML
	private Button btnDelete; // 삭제 버튼
	@FXML
	private Button btnEdit; // 수량 수정 버튼
	@FXML
	private TableView<OrderVO> tableOrderList; // 주문 현황 테이블
	@FXML
	private DatePicker dpDate; // 등록일자
	@FXML
	private Button btnWearing; // 입고 확인 버튼

	// 재고테이블 클릭시 가져온 상품의 정보
	private static ArrayList<OrderVO> selectedItem = new ArrayList();
	
	ObservableList<ProductVO> productDataList = FXCollections.observableArrayList(); // 재고현황 테이블
	ObservableList<OrderVO> orderDataList = FXCollections.observableArrayList(); // 주문 등록 테이블
	ObservableList<OrderVO> OrderList = FXCollections.observableArrayList(); // 주문 현황 테이블

	ObservableList<ProductVO> selectProduct = null; // 재고 테이블에서 선택한 정보 저장
	int selectedIndex; // 재고 테이블에서 선택한 상품 정보 인덱스 저장

	ObservableList<OrderVO> selectOrder = null; // 주문 등록 테이블에서 선택한 정보 저장
	int selectedOrderIndex; // 주문 등록 테이블에서 선택한 상품 정보 인덱스 저장

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// 수량, 수정, 등록 비활성화
		or_ea.setDisable(true);
		btnRegiste.setDisable(true);
		btnDelete.setDisable(true);
		btnEdit.setDisable(true);

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
		tableProduct.getColumns().addAll(colProductCode, colProductName, colProductEa, colProductPrice,
				colProductTotal);

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
		colOrderEa.setCellValueFactory(new PropertyValueFactory<>("or_ea"));

		TableColumn colOrderPrice = new TableColumn("단가");
		colOrderPrice.setPrefWidth(120);
		colOrderPrice.setStyle("-fx-alignment:CENTER");
		colOrderPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

		TableColumn colOrderTotal = new TableColumn("총액");
		colOrderTotal.setPrefWidth(150);
		colOrderTotal.setStyle("-fx-alignment:CENTER");
		colOrderTotal.setCellValueFactory(new PropertyValueFactory<>("or_total"));

		tableOrder.setItems(orderDataList);
		tableOrder.getColumns().addAll(colOrderPCode, colOrderPName, colOrderEa, colOrderPrice, colOrderTotal);

		// 주문 현황 테이블 컬럼 지정
		TableColumn colListPCode = new TableColumn("상품코드");
		colListPCode.setPrefWidth(100);
		colListPCode.setStyle("-fx-alignment:CENTER");
		colListPCode.setCellValueFactory(new PropertyValueFactory<>("p_code"));

		TableColumn colListPName = new TableColumn("상품명");
		colListPName.setPrefWidth(480);
		colListPName.setStyle("-fx-alignment:CENTER");
		colListPName.setCellValueFactory(new PropertyValueFactory<>("p_name"));

		TableColumn colListEa = new TableColumn("수량");
		colListEa.setPrefWidth(100);
		colListEa.setStyle("-fx-alignment:CENTER");
		colListEa.setCellValueFactory(new PropertyValueFactory<>("or_ea"));

		TableColumn colListPrice = new TableColumn("단가");
		colListPrice.setPrefWidth(120);
		colListPrice.setStyle("-fx-alignment:CENTER");
		colListPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

		TableColumn colListTotal = new TableColumn("총액");
		colListTotal.setPrefWidth(150);
		colListTotal.setStyle("-fx-alignment:CENTER");
		colListTotal.setCellValueFactory(new PropertyValueFactory<>("or_total"));

		tableOrderList.setItems(OrderList);
		tableOrderList.getColumns().addAll(colListPCode, colListPName, colListEa, colListPrice, colListTotal);

		// 재고 테이블뷰 더블 클릭 선택 이벤트 핸들러
		tableProduct.setOnMouseClicked(event -> handlerTableProductPressed(event));
		// 주문 등록 테이블 뷰 클릭 선택 이벤트 핸들러
		tableOrder.setOnMouseClicked(event -> handlerTableOrderPressed(event));

		// 삭제 버튼 이벤트 핸들러
		btnDelete.setOnAction(event -> handlerBtnDeleteAction(event));
		// 등록 버튼 이벤트 핸들러
		btnRegiste.setOnAction(event -> handlerBtnRegiAction(event));
		// 수량 수정 버튼 이벤트 핸들러
		btnEdit.setOnAction(event -> handlerBtnEaEditAction(event));
		// 입고 확인 버튼 이벤트 핸들러
		btnWearing.setOnAction(event -> handlerBtnWearingAction(event));
		// 등록날짜 이벤트 핸들러
		dpDate.setOnAction(event -> handlerDpDateAction(event));

	}

	// 등록날짜 이벤트 메소드
	public void handlerDpDateAction(ActionEvent event) {
		
		ArrayList<OrderVO> list = new ArrayList();
		OrderDAO odao = new OrderDAO();
		
		try {
			
			String orderDate = dpDate.getValue().toString(); // 선택한 날짜
			System.out.println(orderDate);
			list = odao.getOrderDate(orderDate);
			
			OrderList.removeAll(OrderList);
			
			OrderVO ovo = null;
			for(int index = 0; index < list.size(); index++) {
				ovo = new OrderVO(list.get(index).getP_code(), list.get(index).getP_name(),
						list.get(index).getOr_ea(), list.get(index).getPrice(), list.get(index).getOr_total());
				OrderList.addAll(ovo);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

	// 입고 확인 버튼 이벤트 메소드, 수정
	public void handlerBtnWearingAction(ActionEvent event) {

		try {
			
			SaleReturnDAO srdao = new SaleReturnDAO();
			// 판매등록후 재고테이블 수량변경
			srdao.setProductTable(-Integer.parseInt(or_ea.getText()), OrderList.get(0).getP_code());
			// 재고테이블 새로고침
			productTotalList();
			
			// 주문현황 테이블에서 선택한 행의 값 삭제
			int index = tableOrderList.getSelectionModel().getSelectedIndex();
			OrderList.remove(index);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}

	// 수량 수정 버튼 이벤트 메소드
	public void handlerBtnEaEditAction(ActionEvent event) {
		
		// 주문 등록 테이블에서 선택한 행의 수량을 or_ea에서 받은 값으로 설정해준다
		tableOrder.getSelectionModel().getSelectedItem().setOr_ea(Integer.parseInt(or_ea.getText()));
		// 수량만큼 총액 변경
		tableOrder.getSelectionModel().getSelectedItem().setOr_total(
				Integer.parseInt(or_ea.getText()) * tableOrder.getSelectionModel().getSelectedItem().getPrice());
		// 테이블 데이터리스트 삭제
		orderDataList.removeAll(orderDataList);
		OrderVO ovo = null;
		
		for(int i = 0; i < selectedItem.size(); i++) {
			ovo = selectedItem.get(i);
			orderDataList.add(ovo);
		}
		
		selectedItem.removeAll(selectedItem);
		for(int i = 0; i < orderDataList.size(); i++) {
			ovo = orderDataList.get(i);
			selectedItem.add(ovo);
		}

	}

	// 주문 등록 테이블 클릭 이벤트 메소드
	public void handlerTableOrderPressed(MouseEvent event) {

		if (event.getClickCount() == 1) {
			// 수량, 등록, 삭제, 수정 활성화
			or_ea.setDisable(false);
			btnRegiste.setDisable(false);
			btnDelete.setDisable(false);
			btnEdit.setDisable(false);
		}

		// 수량입력에 숫자만 입력할수 있게해줌
		or_ea.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

				if (!newValue.matches("\\d*")) {
					or_ea.setText(newValue.replaceAll("[^\\d]", ""));
				}

			}

		});

	}

	// 등록버튼 이벤트 메소드
	public void handlerBtnRegiAction(ActionEvent event) {
		
		boolean insertResult = false; // 등록 성공 여부

		if (or_ea.getText().equals("") || or_ea.getText() == null) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("주문 등록");
			alert.setHeaderText("주문 등록 실패");
			alert.setContentText("수량을 입력하세요");
			alert.showAndWait();
			
			insertResult = false;
		} else {
			
			OrderDAO odao = new OrderDAO();
			OrderVO ovo = new OrderVO();
			try {
				odao.getOrderInsert(selectedItem);
				OrderList.addAll(selectedItem);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			orderDataList.removeAll(orderDataList);
			selectedItem.removeAll(selectedItem);
			insertResult = true;	
			
		}

	}

	// 삭제 버튼 이벤트 메소드
	public void handlerBtnDeleteAction(ActionEvent event) {

		selectedOrderIndex = tableOrder.getSelectionModel().getSelectedIndex();
		orderDataList.remove(selectedOrderIndex);
		// 삭제시 선택했던 행을 selectedItem에서도 삭제해준다
		selectedItem.remove(selectedOrderIndex);

	}

	// 재고 테이블뷰 더블 클릭 이벤트 메소드
	public void handlerTableProductPressed(MouseEvent event) {

		if (event.getClickCount() == 2) { // 더블클릭시

			try {

				// 테이블에서 선택한 정보를 selectProduct에 저장
				selectProduct = tableProduct.getSelectionModel().getSelectedItems();
				selectedIndex = selectProduct.get(0).getP_no();

				String selectedP_code = selectProduct.get(0).getP_code();
				String selectedP_name = selectProduct.get(0).getP_name();
				//int selecetedP_ea = selectProduct.get(0).getP_ea();
				int selecetedP_ea = 1;
				int selectedP_price = selectProduct.get(0).getP_price();
				int selectedP_total = selecetedP_ea * selectedP_price;

				OrderVO ovo = new OrderVO(selectedP_code, selectedP_name, selecetedP_ea, selectedP_price, selectedP_total);

				orderDataList.add(ovo);
				selectedItem.add(ovo);

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
