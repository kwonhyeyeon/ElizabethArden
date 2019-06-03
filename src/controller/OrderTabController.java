package controller;

import java.net.URL;
import java.nio.channels.Selector;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
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
import javafx.scene.control.Label;
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
	private Button btnWearing; // 입고 확인 버튼
	@FXML
	private Label lblorderlist; // 주문현황 라벨
	@FXML
	private DatePicker dpDate; // 주문 등록 날짜

	// 재고테이블 클릭시 가져온 상품의 정보
	private static ArrayList<OrderVO> selectedItem = new ArrayList();
	private static String today; // 오늘 날짜

	ObservableList<ProductVO> productDataList = FXCollections.observableArrayList(); // 재고현황 테이블
	ObservableList<OrderVO> orderDataList = FXCollections.observableArrayList(); // 주문 등록 테이블
	ObservableList<OrderVO> OrderList = FXCollections.observableArrayList(); // 주문 현황 테이블

	ObservableList<ProductVO> selectProduct = null; // 재고 테이블에서 선택한 정보 저장
	int selectedIndex; // 재고 테이블에서 선택한 상품 정보 인덱스 저장

	ObservableList<OrderVO> selectOrder = null; // 주문 등록 테이블에서 선택한 정보 저장
	int selectedOrderIndex; // 주문 등록 테이블에서 선택한 상품 정보 인덱스 저장

	ObservableList<OrderVO> selectOrderList = FXCollections.observableArrayList(); // 주문현황 테이블에서 선택한 정보
	int selectedOrderListIndex; // 주문현황 테이블에서 선택한 정보 인덱스

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		today = sdf.format(date);

		dpDate.setValue(LocalDate.now()); // 주문현황 날짜 현재일

		try {
			productTotalList(); // 재고현황리스트
			orderTotalList(); // 주문현황리스트
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 수량, 수정, 등록, 입고확인 비활성화
		or_ea.setDisable(true);
		btnRegiste.setDisable(true);
		btnDelete.setDisable(true);
		btnEdit.setDisable(true);
		btnWearing.setDisable(true);

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
		colListPName.setPrefWidth(435);
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

		TableColumn colListInOut = new TableColumn("입고");
		colListInOut.setPrefWidth(80);
		colListInOut.setStyle("-fx-alignment:CENTER");
		colListInOut.setCellValueFactory(new PropertyValueFactory<>("in_out"));

		tableOrderList.setItems(OrderList);
		tableOrderList.getColumns().addAll(colListPCode, colListPName, colListEa, colListPrice, colListTotal,
				colListInOut);

		// 재고 테이블뷰 더블 클릭 선택 이벤트 핸들러
		tableProduct.setOnMouseClicked(event -> handlerTableProductPressed(event));
		// 주문 등록 테이블 뷰 클릭 선택 이벤트 핸들러
		tableOrder.setOnMouseClicked(event -> handlerTableOrderPressed(event));
		// 주문 현황 테이블 뷰 클릭 선택 이벤트 핸들러
		tableOrderList.setOnMouseClicked(event -> handlerTableOrderListPressed(event));

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

	// 주문 현황 테이블 뷰 클릭 선택 이벤트
	public void handlerTableOrderListPressed(MouseEvent event) {
		btnWearing.setDisable(false); // 입고 확인 버튼 활성화
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
			for (int index = 0; index < list.size(); index++) {
				ovo = new OrderVO(list.get(index).getP_code(), list.get(index).getP_name(), list.get(index).getOr_ea(),
						list.get(index).getPrice(), list.get(index).getOr_total(), list.get(index).getIn_out());
				OrderList.addAll(ovo);
			}

			if (list.size() == 0) {
				Alert alert;
				alert = new Alert(AlertType.WARNING);
				alert.setTitle("주문 현황");
				alert.setHeaderText("주문 현황 없음");
				alert.setContentText("[ " + orderDate + " ] 에 등록된 상품이 없습니다.");
				// 경고창 크기설정 불가
				alert.setResizable(false);
				// 경고창을 보여주고 기다린다
				alert.showAndWait();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// 입고 확인 버튼 이벤트 메소드
	public void handlerBtnWearingAction(ActionEvent event) {

		String in_pcode = tableOrderList.getSelectionModel().getSelectedItem().getP_code(); // 상품코드
		int in_pea = tableOrderList.getSelectionModel().getSelectedItem().getOr_ea(); // 수량
		String in_out = tableOrderList.getSelectionModel().getSelectedItem().getIn_out(); // 입고 확인
		String selectedP_name = tableOrderList.getSelectionModel().getSelectedItem().getP_name(); // 상품명

		OrderDAO odao = new OrderDAO();
		try {
			if (in_out.equals("Y")) {
				Alert alert;
				alert = new Alert(AlertType.WARNING);
				alert.setTitle("입고 안내");
				alert.setHeaderText(selectedP_name);
				alert.setContentText("해당 상품은 이미 입고 확인이 되었습니다.");
				// 경고창 크기설정 불가
				alert.setResizable(false);
				// 경고창을 보여주고 기다린다
				alert.showAndWait();
			} else {
				Alert alert;
				alert = new Alert(AlertType.WARNING);
				alert.setTitle("입고 안내");
				alert.setHeaderText(selectedP_name);
				alert.setContentText("해당 상품이 입고 되었습니다.");
				// 경고창 크기설정 불가
				alert.setResizable(false);
				// 경고창을 보여주고 기다린다
				alert.showAndWait();
				
				odao.setProductTable(in_pea, in_pcode); // 테이블 수량 변경
				odao.setY(today, in_pcode); // 입고 확인 변경
			}

			productTotalList(); // 재고현황리스트
			orderTotalList(); // 주문현황리스트
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

		or_ea.clear();

		OrderVO ovo = null;

		for (int i = 0; i < selectedItem.size(); i++) {
			ovo = selectedItem.get(i);
			orderDataList.add(ovo);
		}

		selectedItem.removeAll(selectedItem);
		for (int i = 0; i < orderDataList.size(); i++) {
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

		boolean editEa = false; // 수량 수정 결과
		boolean overlap; // 중복된 값 있을 땐 true 반환
		boolean insertResult = false; // 등록 성공 결과

		OrderDAO odao = new OrderDAO();

		try {

			// 수량이 0이거나 음수일 때
			for (int index = 0; index < orderDataList.size(); index++) {
				// 주문현황 테이블에서 수량을 가져와 ea에 저장
				int ea = orderDataList.get(index).getOr_ea();
				
				if (ea == 0 || ea < 0) {
					editEa = true;
				}
			}

			if (editEa) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("주문 등록");
				alert.setHeaderText("주문 등록 실패");
				alert.setContentText("수량을 입력하세요");
				alert.showAndWait();
			} else {
				for (int index = 0; index < orderDataList.size(); index++) {

					// 상품명 중복검사
					overlap = odao.getOverLapProductName(today, orderDataList.get(index).getP_code());

					if (overlap) {
						String p_code = orderDataList.get(index).getP_code(); // 상품코드
						int ea = orderDataList.get(index).getOr_ea(); // 수량
						// 기존 행에서 수량만 수정해준다
						odao.updateEa(today, p_code, ea); // 등록일, 상품코드, 수량을 넣어준다

						Alert alert;
						alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("주문 등록");
						alert.setHeaderText(p_code + "수량 변경 완료");
						alert.setContentText("주문 등록이 완료되었습니다");
						// 경고창 크기설정 불가
						alert.setResizable(false);
						// 경고창을 보여주고 기다린다
						alert.showAndWait();

						overlap = false;
					} else {
						// 주문 등록 테이블의 갯수만큼 반복해 insert문에 넣어준다
						insertResult = odao.getOrderInsert(orderDataList.get(index).getP_code(),
								orderDataList.get(index).getOr_ea(), orderDataList.get(index).getOr_total(),
								orderDataList.get(index).getOr_bad());
					}

				}

				if (insertResult) {
					Alert alert;
					alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("주문 등록");
					alert.setHeaderText("주문 등록 성공");
					alert.setContentText("주문 등록이 완료되었습니다");
					// 경고창 크기설정 불가
					alert.setResizable(false);
					// 경고창을 보여주고 기다린다
					alert.showAndWait();
				}

				orderTotalList(); // 주문 등록 현황 리스트

				orderDataList.removeAll(orderDataList); // 주문등록 현황 리스트 초기화
				selectedItem.removeAll(selectedItem); // 재고테이블 클릭시 가져온 상품의 정보 초기화

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// 삭제 버튼 이벤트 메소드
	public void handlerBtnDeleteAction(ActionEvent event) {

		// 주문 등록 테이블에서 선택한 인덱스 삭제
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

				// 상품코드, 상품명, 수량=0, 단가, 총액을 가져옴
				String selectedP_code = selectProduct.get(0).getP_code();
				String selectedP_name = selectProduct.get(0).getP_name();
				int selecetedP_ea = 0; // 수량 초기값 0으로 설정
				int selectedP_price = selectProduct.get(0).getP_price();
				int selectedP_total = selecetedP_ea * selectedP_price;

				// ovo에 값을 저장한다
				OrderVO ovo = new OrderVO(selectedP_code, selectedP_name, selecetedP_ea, selectedP_price,
						selectedP_total);

				orderDataList.add(ovo); // 주문 등록 창에 추가
				selectedItem.add(ovo); // 선택한 정보에 추가

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	// 주문 현황 리스트
	public void orderTotalList() throws Exception {

		// 주문 현황 테이블 초기화
		OrderList.removeAll(OrderList);
		OrderDAO odao = new OrderDAO();
		OrderVO ovo = new OrderVO();

		ArrayList<OrderVO> list;

		try {
			// 오늘 날짜를 넣어 주문 전체 리스트를 가져온다
			list = odao.getOrderTotalList(today);
			int rowCount = list.size();

			for (int index = 0; index < rowCount; index++) {
				ovo = list.get(index);
				OrderList.add(ovo); // 주문 현황 테이블에 값을 넣어준다
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// 재고 현황 리스트
	public void productTotalList() throws Exception {

		// 재고 현황 테이블 초기화
		productDataList.removeAll(productDataList);
		ProductDAO pDao = new ProductDAO();
		ProductVO pVo = null;

		ArrayList<ProductVO> list;

		list = pDao.getProductTotalList(); // 전체 리스트를 저장
		int rowCount = list.size();

		for (int index = 0; index < rowCount; index++) {
			pVo = list.get(index);
			productDataList.add(pVo); // 재고 현황 테이블에 값을 넣어준다
		}

	}

}
