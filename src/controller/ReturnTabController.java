package controller;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.ProductVO;
import model.ReturnVO;

public class ReturnTabController implements Initializable {

	@FXML
	private TableView<ProductVO> tableProduct = new TableView<>(); // 재고 테이블
	@FXML
	private TableView<ReturnVO> tableReturn = new TableView<>(); // 반품 등록 테이블
	@FXML
	private Button btnRegiste; // 등록버튼
	@FXML
	private Button btnDelete; // 삭제 버튼
	@FXML
	private TextField or_ea; // 수량 입력상자
	@FXML
	private DatePicker dpDate; // 달력
	@FXML
	private TableView<ReturnVO> tableReturnState = new TableView<>(); // 반품현황 테이블
	@FXML
	private Button btnRelease; // 출고 확인 버튼
	@FXML
	private ComboBox<String> cbxbad; // 불량여부 콤보박스
	@FXML
	private Button btnEdit; // 수정버튼
	private static String today; // 오늘날짜를 저장하는 변수
	private ArrayList<ReturnVO> selectedItem = new ArrayList(); // 2 * 재고테이블에서 선택한 행의 정보만 담는 배열 생성

	ObservableList<ProductVO> productDataList = FXCollections.observableArrayList(); // 재고현황 테이블
	ObservableList<ReturnVO> insertReturn = FXCollections.observableArrayList(); // 반품등록 테이블
	ObservableList<ReturnVO> ReturnState = FXCollections.observableArrayList(); // 반품 현황 테이블
	ObservableList<ProductVO> selectProduct = null; // 재고 테이블에서 선택한 정보 저장

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

		// 오늘날짜를 가져온다.
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		Date time = new Date();
		// 가져온값을 today변수에 저장
		today = format1.format(time);
		// 수량입력에 숫자만 입력할수 있게해줌
		or_ea.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d*")) {
					or_ea.setText(newValue.replaceAll("[^\\d]", ""));
				}
			}

		});
		// dpDate 오늘날짜로 설정
		dpDate.setValue(LocalDate.now());

		// 재고테이블 설정
		productTotalList();
		// 반품현환테이블 설정
		ReturnStateTotalList();
		// 상태 콤보박스 설정
		cbxbad.setItems(FXCollections.observableArrayList("Y", "N"));

		// 재고 현황 테이블 컬럼 지정
		TableColumn colProductCode = new TableColumn("상품코드");
		colProductCode.setPrefWidth(80);
		colProductCode.setStyle("-fx-alignment:CENTER");
		colProductCode.setCellValueFactory(new PropertyValueFactory<>("p_code"));

		TableColumn colProductName = new TableColumn("상품명");
		colProductName.setPrefWidth(188);
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

		// 반품 입력 테이블 컬럼 지정
		TableColumn colInsertP_Code = new TableColumn("상품코드");
		colInsertP_Code.setPrefWidth(120);
		colInsertP_Code.setStyle("-fx-alignment:CENTER");
		colInsertP_Code.setCellValueFactory(new PropertyValueFactory<>("rp_code"));

		TableColumn colInsertP_Name = new TableColumn("상품명");
		colInsertP_Name.setPrefWidth(420);
		colInsertP_Name.setStyle("-fx-alignment:CENTER");
		colInsertP_Name.setCellValueFactory(new PropertyValueFactory<>("rp_name"));

		TableColumn colInsertP_ea = new TableColumn("수량");
		colInsertP_ea.setPrefWidth(50);
		colInsertP_ea.setStyle("-fx-alignment:CENTER");
		colInsertP_ea.setCellValueFactory(new PropertyValueFactory<>("rp_ea"));

		TableColumn colInsertP_Price = new TableColumn("단가");
		colInsertP_Price.setPrefWidth(110);
		colInsertP_Price.setStyle("-fx-alignment:CENTER");
		colInsertP_Price.setCellValueFactory(new PropertyValueFactory<>("rp_price"));

		TableColumn colInsertP_total = new TableColumn("총액");
		colInsertP_total.setPrefWidth(150);
		colInsertP_total.setStyle("-fx-alignment:CENTER");
		colInsertP_total.setCellValueFactory(new PropertyValueFactory<>("rp_total"));

		TableColumn colInsertRp_bad = new TableColumn("불량여부");
		colInsertRp_bad.setPrefWidth(100);
		colInsertRp_bad.setStyle("-fx-alignment:CENTER");
		colInsertRp_bad.setCellValueFactory(new PropertyValueFactory<>("rp_bad"));

		tableReturn.setItems(insertReturn);
		tableReturn.getColumns().addAll(colInsertP_Code, colInsertP_Name, colInsertP_ea, colInsertP_Price,
				colInsertP_total, colInsertRp_bad);

		// 반품 현황 테이블 컬럼 지정

		TableColumn colInsertP_Code2 = new TableColumn("상품코드");
		colInsertP_Code2.setPrefWidth(100);
		colInsertP_Code2.setStyle("-fx-alignment:CENTER");
		colInsertP_Code2.setCellValueFactory(new PropertyValueFactory<>("rp_code"));

		TableColumn colInsertP_Name2 = new TableColumn("상품명");
		colInsertP_Name2.setPrefWidth(350);
		colInsertP_Name2.setStyle("-fx-alignment:CENTER");
		colInsertP_Name2.setCellValueFactory(new PropertyValueFactory<>("rp_name"));

		TableColumn colInsertP_ea2 = new TableColumn("수량");
		colInsertP_ea2.setPrefWidth(50);
		colInsertP_ea2.setStyle("-fx-alignment:CENTER");
		colInsertP_ea2.setCellValueFactory(new PropertyValueFactory<>("rp_ea"));

		TableColumn colInsertP_Price2 = new TableColumn("단가");
		colInsertP_Price2.setPrefWidth(90);
		colInsertP_Price2.setStyle("-fx-alignment:CENTER");
		colInsertP_Price2.setCellValueFactory(new PropertyValueFactory<>("rp_price"));

		TableColumn colInsertP_total2 = new TableColumn("총액");
		colInsertP_total2.setPrefWidth(150);
		colInsertP_total2.setStyle("-fx-alignment:CENTER");
		colInsertP_total2.setCellValueFactory(new PropertyValueFactory<>("rp_total"));

		TableColumn colInsertRp_date = new TableColumn("등록일");
		colInsertRp_date.setPrefWidth(100);
		colInsertRp_date.setStyle("-fx-alignment:CENTER");
		colInsertRp_date.setCellValueFactory(new PropertyValueFactory<>("rp_date"));

		TableColumn colInsertbeReleased = new TableColumn("출고");
		colInsertbeReleased.setPrefWidth(90);
		colInsertbeReleased.setStyle("-fx-alignment:CENTER");
		colInsertbeReleased.setCellValueFactory(new PropertyValueFactory<>("beReleased"));

		tableReturnState.setItems(ReturnState);
		tableReturnState.getColumns().addAll(colInsertP_Code2, colInsertP_Name2, colInsertP_ea2, colInsertP_Price2,
				colInsertP_total2, colInsertRp_date, colInsertbeReleased);

		// 재고 테이블뷰 더블 클릭 선택 이벤트 핸들러
		tableProduct.setOnMouseClicked(event -> handlerTableProductAction(event));
		// 등록 버튼 이벤트 핸들러
		btnRegiste.setOnAction(event -> handlerBtnPRegiAction(event));
		// 수정버튼 이벤트 핸들러
		btnEdit.setOnAction(event -> handlerBtnEditAction(event));
		// 삭제버튼 이벤트 핸들러
		btnDelete.setOnAction(event -> handlerBtnDeleteAction(event));
		// 출고확인버튼 이벤트 핸들러
		btnRelease.setOnAction(event -> handlerBtnRelease(event));
		// 달력에서 클릭 선택 이벤트 핸들러
		dpDate.setOnAction(event -> handlerdpDateAction(event));

		// 인스턴스 생성
		ReturnDAO rdao = new ReturnDAO();
		// 배열생성
		ArrayList<ReturnVO> list = new ArrayList();

		// 프로그램 실행시 그날의 반품등록한 리스트를 보여준다.
		try {
			list = rdao.getOrderDate(today);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 반품현황테이블을 지워준후
		ReturnState.removeAll(ReturnState);

		// 메소드를 반품현황 테이블의 데이터를 추가한다.
		ReturnStateTotalList();

	}

	// 달력에서 클릭 선택 이벤트 핸들러
	public void handlerdpDateAction(ActionEvent event) {
		// TODO Auto-generated method stub

		// 인스턴스 생성
		ReturnDAO rdao = new ReturnDAO();
		ReturnVO rvo = new ReturnVO();

		// dpDate에서 설정된 날짜를 저장
		String selectedDay = dpDate.getValue().toString();

		// 배열생성
		ArrayList<ReturnVO> list = null;

		// 반품현황테이블을 비운다.
		ReturnState.removeAll(ReturnState);

		try {
			// 선택된 날짜를 매개변수에 넣어주고 해당 데이터를 가져온다.
			list = rdao.getOrderDate(selectedDay);
			// 배열의 사이즈 저장
			int rowCount = list.size();

			// 가져온 데이터가 없을경우
			if (rowCount == 0) {
				Alert alert;
				alert = new Alert(AlertType.WARNING);
				alert.setTitle("출고안내");
				alert.setHeaderText("[ " + selectedDay + " ] 에 등록된 상품이 없습니다.");
				alert.setContentText("");
				// 경고창 크기설정 불가
				alert.setResizable(false);
				// 경고창을 보여주고 기다린다
				alert.showAndWait();
			} else {
				// 데이터가 있을경우 배열의 사이즈만큼 반복문 실행
				for (int index = 0; index < rowCount; index++) {
					// 테이블에 추가한다.
					rvo = list.get(index);
					ReturnState.add(rvo);
				}
			}
		} catch (Exception e) {
		}
	}

	// 출고확인버튼 이벤트 핸들러
	public void handlerBtnRelease(ActionEvent event) {

		ReturnVO rvo2 = new ReturnVO();
		ReturnDAO rdao = new ReturnDAO();

		// 버튼 이벤트 발생시 테이블에 선택된 행의 정보 저장
		rvo2 = tableReturnState.getSelectionModel().getSelectedItem();

		if (rvo2 == null) {
			// 선택된 행의 정보가 없을경우
			Alert alert;
			alert = new Alert(AlertType.WARNING);
			alert.setTitle("출고안내");
			alert.setHeaderText("출고등록할 상품을 선택하십시오.");
			alert.setContentText("");
			// 경고창 크기설정 불가
			alert.setResizable(false);
			// 경고창을 보여주고 기다린다
			alert.showAndWait();
		} else {
			// 선택된 행의 출고값 저장
			String xy = tableReturnState.getSelectionModel().getSelectedItem().getBeReleased();
			// 선택된 행의 상품명 저장
			String selectedP_name = tableReturnState.getSelectionModel().getSelectedItem().getRp_name();

			if (xy.equals("Y")) {
				// 선택된 행의 출고값이 Y일 경우

				Alert alert;
				alert = new Alert(AlertType.WARNING);
				alert.setTitle("출고안내");
				alert.setHeaderText(selectedP_name + "");
				alert.setContentText("해당 상품은 이미 출고확인이 되었습니다.");
				// 경고창 크기설정 불가
				alert.setResizable(false);
				// 경고창을 보여주고 기다린다
				alert.showAndWait();

			} else {
				// Y가 아닐경우

				// 선택된 행의 상품코드 저장
				String selectedP_code = tableReturnState.getSelectionModel().getSelectedItem().getRp_code();
				// 선택된 행의 수량 저장
				int selectedP_ea = tableReturnState.getSelectionModel().getSelectedItem().getRp_ea();

				// 오늘날짜와 선택된 행의 상품코드를 매개변수로 넣고 출고값 y로 변경
				rdao.SetY(today, selectedP_code);

				// 선택된 행의 수량과 상품코드를 가져와 해당 상품의 수량 변경
				rdao.setIn_Out(selectedP_ea, selectedP_code);

				// 테이블 새로고침
				ReturnStateTotalList();
				productTotalList();

			}
			try {

			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	// 삭제버튼 이벤트 핸들러
	public void handlerBtnDeleteAction(ActionEvent event) {
		// TODO Auto-generated method stub
		ReturnVO rvo = new ReturnVO();
		// 선택된 행을 객체로 저장
		rvo = tableReturn.getSelectionModel().getSelectedItem();

		if (rvo == null) {
			// 저장된 객체가 null일경우
			Alert alert;
			alert = new Alert(AlertType.WARNING);
			alert.setTitle("삭제 안내");
			alert.setHeaderText("삭제할 데이터가 없습니다.");
			alert.setContentText("삭제할 데이터를 선택하십시오.");
			// 경고창 크기설정 불가
			alert.setResizable(false);
			// 경고창을 보여주고 기다린다
			alert.showAndWait();
		} else {
			// 선택된 행의 index 저장
			int index = tableReturn.getSelectionModel().getSelectedIndex();
			// index번째의 데이터 삭제
			insertReturn.remove(index);
			selectedItem.remove(index);

			// 테이블에 있던 정보 삭제
			insertReturn.removeAll(insertReturn);
			// 테이블 새로고침
			for (int i = 0; i < selectedItem.size(); i++) {
				rvo = selectedItem.get(i);
				insertReturn.add(rvo);
			}
			// 복사본도 새로고침
			selectedItem.removeAll(selectedItem);
			for (int i = 0; i < insertReturn.size(); i++) {
				rvo = insertReturn.get(i);
				selectedItem.add(rvo);
			}
		}
	}

	// 수정버튼 이벤트 핸들러
	public void handlerBtnEditAction(ActionEvent event) {
		// TODO Auto-generated method stub

		// 인스턴스 생성
		ReturnVO rvo = new ReturnVO();
		// 선택된 행의 데이터 객체로 저장
		rvo = tableReturn.getSelectionModel().getSelectedItem();

		if (selectedItem.size() == 0 || rvo == null) {
			// 저장된 객체가 null일 경우
			Alert alert;
			alert = new Alert(AlertType.WARNING);
			alert.setTitle("수정할 정보가 없습니다.");
			alert.setHeaderText("등록할 정보를 선택하십시오.");
			alert.setContentText("");
			// 경고창 크기설정 불가
			alert.setResizable(false);
			// 경고창을 보여주고 기다린다
			alert.showAndWait();
		} else if (or_ea.getText().trim().equals("")) {
			// 수량입력 텍스트 상자가 비어있을경우
			Alert alert;
			alert = new Alert(AlertType.WARNING);
			alert.setTitle("수정 오류");
			alert.setHeaderText("상품의 수량을 입력하십시오.");
			alert.setContentText("");
			// 경고창 크기설정 불가
			alert.setResizable(false);
			// 경고창을 보여주고 기다린다
			alert.showAndWait();
		} else if (cbxbad.getSelectionModel().getSelectedItem() == null) {
			// 불령여부 콤보박스가 선택되지 않았을 경우

			Alert alert;
			alert = new Alert(AlertType.WARNING);
			alert.setTitle("수정 오류");
			alert.setHeaderText("상품의 불량여부를 선택하십시오.");
			alert.setContentText("");
			// 경고창 크기설정 불가
			alert.setResizable(false);
			// 경고창을 보여주고 기다린다
			alert.showAndWait();
		} else {

			// 테이블에서 수량변경
			tableReturn.getSelectionModel().getSelectedItem().setRp_ea(Integer.parseInt(or_ea.getText()));
			// 테이블에서 불량여부 변경
			tableReturn.getSelectionModel().getSelectedItem().setRp_bad(cbxbad.getSelectionModel().getSelectedItem());
			// 테이블에서 총액 변경
			tableReturn.getSelectionModel().getSelectedItem()
					.setRp_total(tableReturn.getSelectionModel().getSelectedItem().getRp_ea()
							* tableReturn.getSelectionModel().getSelectedItem().getRp_price());
			// 테이블에 있던 정보 삭제
			insertReturn.removeAll(insertReturn);
			// 테이블 새로고침
			for (int i = 0; i < selectedItem.size(); i++) {
				rvo = selectedItem.get(i);
				insertReturn.add(rvo);
			}
			// 복사본 새로고침
			selectedItem.removeAll(selectedItem);
			for (int i = 0; i < insertReturn.size(); i++) {
				rvo = insertReturn.get(i);
				selectedItem.add(rvo);
			}
		}

	}

	// 등록버튼 이벤트 메소드
	public void handlerBtnPRegiAction(ActionEvent event) {
		boolean editEa = false; // 수량 수정여부
		boolean overlap = false; // 중복여부

		// 인스턴스 생성
		ReturnDAO rdao = new ReturnDAO();
		// boolean변수 선언
		boolean ok = false;
		try {

			// 반품등록 테이블의 사이즈만큼 반복
			for (int index = 0; index < insertReturn.size(); index++) {
				// 입력된 수량 저장
				int ea = insertReturn.get(index).getRp_ea();

				// 반품 입력 테이블에 등록된 수량중 0이 있거나 0보다 작은값이 있을경우 true로 설정
				if (ea == 0 || ea < 0) {
					editEa = true;
				}

			}
			// 수량 수정 변수가 true일 경우
			if (editEa) {
				Alert alert;
				alert = new Alert(AlertType.WARNING);
				alert.setTitle("등록 오류");
				alert.setHeaderText("반품등록한 상품중 \n수량입력이 안된 상품이 있습니다");
				alert.setContentText("");
				// 경고창 크기설정 불가
				alert.setResizable(false);
				// 경고창을 보여주고 기다린다
				alert.showAndWait();
			} else {
				// 반품등록 사이즈만큼 반복
				for (int index = 0; index < insertReturn.size(); index++) {
					// 오늘날짜, 상품코드로 검색해서 중복된 상품이 있는지 boolean값 반환
					overlap = rdao.overlapP_code(today, insertReturn.get(index).getRp_code());

					// 중복검사에서 true일경우
					if (overlap) {

						// 상품코드 저장
						String p_code = insertReturn.get(index).getRp_code();
						// 수량저장
						int or_ea = insertReturn.get(index).getRp_ea();
						// 오늘날짜와 상품코드로 검색해서 수량변경
						rdao.updateOverp_code(today, p_code, or_ea);
						// 중복검사 false로 설정
						overlap = false;
					} else {
						// 중복검사에서 false일경우 반품현황테이블에 새로 등록시켜준다.
						// 인스턴스 생성
						ReturnDAO rdao2 = new ReturnDAO();

						// 반품입력 테이블의 레코드 갯수만큼 반복하여 iesert문에 넣어주고 DB에 저장한다.
						ok = rdao2.insertOrder_Return(insertReturn.get(index).getRp_code(),
								insertReturn.get(index).getRp_ea(), insertReturn.get(index).getRp_total(),
								insertReturn.get(index).getRp_bad());

					}
				}
				// 등록후 반환된 boolean값이 true일경우
				if (ok) {
					Alert alert;
					alert = new Alert(AlertType.WARNING);
					alert.setTitle("등록 완료");
					alert.setHeaderText("반품등록이 완료되었습니다.");
					alert.setContentText("");
					// 경고창 크기설정 불가
					alert.setResizable(false);
					// 경고창을 보여주고 기다린다
					alert.showAndWait();

				}

				ReturnStateTotalList(); // 테이블 새로고침

				// 반품등록 테이블을 비운다.
				insertReturn.removeAll(insertReturn);
				selectedItem.removeAll(selectedItem);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	// 반품현황 리스트
	public void ReturnStateTotalList() {
		// 반품현황 테이블을 비운다.
		ReturnState.removeAll(ReturnState);

		// 인스턴스 생성
		ReturnDAO rdao = new ReturnDAO();
		ReturnVO rvo = null;

		// 배열생성
		ArrayList<ReturnVO> list;
		try {
			// 오늘날짜로 검색해서 얻어온 데이터배열 반환
			list = rdao.getOrderDate(today);
			int rowCount = list.size();

			// 반환된 배열의 사이즈만큼 반복
			for (int index = 0; index < rowCount; index++) {
				// 테이블값 설정
				rvo = list.get(index);
				ReturnState.add(rvo);
			}

		} catch (Exception e) {
		}

	}

	// 재고 전체 리스트
	public void productTotalList() {
		// TODO Auto-generated method stub
		try {

			// 재고테이블을 비운다.
			productDataList.removeAll(productDataList);
			// 인스턴스 생성
			ProductDAO pDao = new ProductDAO();
			ProductVO pVo = null;

			// 배열 생성
			ArrayList<ProductVO> list;

			// 재고리스트 반환
			list = pDao.getProductTotalList();
			int rowCount = list.size();

			// 배열의 사이즈만큼 반복
			for (int index = 0; index < rowCount; index++) {
				// 얻어온 데이터로 테이블 설정
				pVo = list.get(index);
				productDataList.add(pVo);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	// 재고 테이블뷰 더블 클릭 이벤트 메소드
	public void handlerTableProductAction(MouseEvent event) {

		if (event.getClickCount() == 2) { // 더블클릭시
			try {
				// 테이블에서 선택한 정보를 selectSubject에 저장
				selectProduct = tableProduct.getSelectionModel().getSelectedItems();

				// 선택된 행의 데이터 저장
				String selectedP_code = selectProduct.get(0).getP_code();
				String selectedP_name = selectProduct.get(0).getP_name();
				int selecetedP_ea = 0; // 수량 0으로 설정
				int selectedP_price = selectProduct.get(0).getP_price();
				int selectedP_total = selecetedP_ea * selectedP_price; // 총액 = 수량 * 단가로 설정

				// 선택된행의 데이터 객체로 저장
				ReturnVO rvo = new ReturnVO(selectedP_code, selectedP_name, selecetedP_ea, selectedP_price,
						selectedP_total, "N", "반품");

				// 테이블에 추가
				insertReturn.add(rvo);
				// 복사본도 저장
				
				selectedItem.add(rvo);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}
