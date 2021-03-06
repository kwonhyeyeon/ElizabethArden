package controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.SelectionModel;
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
import javafx.stage.Window;
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
   private TableView<ProductVO> tableSaleInsert = new TableView<>(); // 판매입력 테이블
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
   @FXML
   private Label lblCInfo;
   @FXML
   private Label lblDateSale;

   private static int customer_point;

   private int customerNo; // 선택된 고객의 고객코드

   ObservableList<ProductVO> productDataList = FXCollections.observableArrayList(); // 재고현황 테이블
   ObservableList<ProductVO> saleInsertDataList = FXCollections.observableArrayList(); // 판매입력 테이블
   ObservableList<SaleVO> saleListDataList = FXCollections.observableArrayList(); // 판매내역 테이블

   ObservableList<ProductVO> selectProduct = null; // 재고 테이블에서 선택한 정보 저장
   int selectedIndex; // 재고 테이블에서 선택한 상품 정보 인덱스 저장

   ObservableList<ProductVO> selectInsert = null; // 판매입력 테이블에서 선택한 정보 저장
   int selectedSaleIndex; // 판매입력 테이블에서 선택한 상품 정보 인덱스 저장

   @Override
   public void initialize(URL arg0, ResourceBundle arg1) {

      // 수량입력에 숫자만 입력할수 있게해줌
      p_ea.textProperty().addListener(new ChangeListener<String>() {

         @Override
         public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            if (!newValue.matches("\\d*")) {
               p_ea.setText(newValue.replaceAll("[^\\d]", ""));
            }
         }

      });
      // 포인트 사용금액에 숫자만 입력할수 있게 해줌
      txtUsedPoint.textProperty().addListener(new ChangeListener<String>() {

         @Override
         public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            if (!newValue.matches("\\d*")) {
               txtUsedPoint.setText(newValue.replaceAll("[^\\d]", ""));
            }
         }

      });
      try {
         productTotalList(); // 재고 현황 리스트
         employeeName(); // 직원명 콤보박스

         // 콤보박스 설정
         cbxState.setItems(FXCollections.observableArrayList("판매", "반품", "포인트 사용"));
         cbxReturnReason.setItems(FXCollections.observableArrayList("제조날짜초과", "변질", "트러블", "기타"));

         cbxState.setDisable(true); // 상태 콤보박스 비활성화
         txtUsedPoint.setDisable(true); // 포인트 사용금액 비활성화
         cbxReturnReason.setDisable(true); // 반품사유 콤보박스 비활성화
         p_ea.setDisable(false); // 수량 텍스트필드 비활성화
         btnP_regi.setDisable(false); // 등록버튼 비활성화
         txtC_name.setText("FREE"); // 고객명 텍스트상자 초기값 free로 설정
         txtC_name.setDisable(true); // 고객명 텍스트상자 비활성화
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

      // 판매 입력 테이블 컬럼 지정
      TableColumn colInsertP_Code = new TableColumn("상품코드");
      colInsertP_Code.setPrefWidth(120);
      colInsertP_Code.setStyle("-fx-alignment:CENTER");
      colInsertP_Code.setCellValueFactory(new PropertyValueFactory<>("p_code"));

      TableColumn colInsertP_Name = new TableColumn("상품명");
      colInsertP_Name.setPrefWidth(420);
      colInsertP_Name.setStyle("-fx-alignment:CENTER");
      colInsertP_Name.setCellValueFactory(new PropertyValueFactory<>("p_name"));

      TableColumn colInsertP_ea = new TableColumn("수량");
      colInsertP_ea.setPrefWidth(50);
      colInsertP_ea.setStyle("-fx-alignment:CENTER");
      colInsertP_ea.setCellValueFactory(new PropertyValueFactory<>("p_ea"));

      TableColumn colInsertP_Price = new TableColumn("단가");
      colInsertP_Price.setPrefWidth(110);
      colInsertP_Price.setStyle("-fx-alignment:CENTER");
      colInsertP_Price.setCellValueFactory(new PropertyValueFactory<>("p_price"));

      TableColumn colInsertP_total = new TableColumn("총액");
      colInsertP_total.setPrefWidth(150);
      colInsertP_total.setStyle("-fx-alignment:CENTER");
      colInsertP_total.setCellValueFactory(new PropertyValueFactory<>("p_total"));

      TableColumn colInsertP_Point = new TableColumn("포인트");
      colInsertP_Point.setPrefWidth(100);
      colInsertP_Point.setStyle("-fx-alignment:CENTER");
      colInsertP_Point.setCellValueFactory(new PropertyValueFactory<>("p_point"));

      tableSaleInsert.setItems(saleInsertDataList);
      tableSaleInsert.getColumns().addAll(colInsertP_Code, colInsertP_Name, colInsertP_ea, colInsertP_Price,
            colInsertP_total, colInsertP_Point);

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

      TableColumn colListBuild_Date = new TableColumn("판매 일자");
      colListBuild_Date.setPrefWidth(100);
      colListBuild_Date.setStyle("-fx-alignment:CENTER");
      colListBuild_Date.setCellValueFactory(new PropertyValueFactory<>("build_date"));

      tableSaleList.setItems(saleListDataList);
      tableSaleList.getColumns().addAll(colListNo, colListP_Code, colListP_Name, colListSr_State, colListSr_ea,
            colListP_Price, colListSr_Total, colListP_Point, colListSr_Used_Point, colListBuild_Date);

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
      // 달력에서 클릭 선택 이벤트 핸들러
      dpDate.setOnAction(event -> handlerdpDateAction(event));

   }

   // 달력에서 마우스 이벤트 발생시
   public void handlerdpDateAction(ActionEvent event) {

      // 해당 날짜의 데이터를 받는 배열 생성
      ArrayList<SaleVO> list = new ArrayList();
      // 인스턴스 생성
      SaleReturnDAO sdao = new SaleReturnDAO();
      try {
         // 달력에서 선택된 일을 저장하는 변수
         String buildDate = dpDate.getValue().toString();
         // 선택된 날짜에 판매및 반품현황을 가져오는 메소드에 입력된 날짜를 넣어준다
         list = sdao.getSaleReturndpdate(buildDate);
         // 테이블에 있던 데이터를 지운다
         saleListDataList.removeAll(saleListDataList);
         // 라벨에 선택된일로 설정
         lblDateSale.setText("[" + buildDate + "]" + " 판매내역");

         // 리턴받아온 배열의 사이즈만큼 반복문 실행
         for (int index = 0; index < list.size(); index++) {
            // 리턴받아온 배열의 데이터를 객체에 담아준다
            SaleVO svo = new SaleVO(list.get(index).getNo(), list.get(index).getP_code(),
                  list.get(index).getP_name(), list.get(index).getSr_state(), list.get(index).getSr_ea(),
                  list.get(index).getP_price(), list.get(index).getSr_total(), list.get(index).getP_point(),
                  list.get(index).getSr_used_point(), list.get(index).getBuild_date());
            // 객체에 담긴 데이터를 테이블에 추가시킨다.
            saleListDataList.add(svo);
         }

      } catch (Exception e) {
         System.out.println(e);
      }
   }

   // 등록 버튼 이벤트 메소드
   public void handlerBtnPRegiAction(ActionEvent event) {
      try {
         if (cbxE_name.getSelectionModel().getSelectedItem() == null) {
            // 직원선택을 안하고 했을경우
            Alert alert;
            alert = new Alert(AlertType.WARNING);
            alert.setTitle("판매 등록안내");
            alert.setHeaderText("직원을 선택하십시오");
            alert.setContentText("");
            // 경고창 크기설정 불가
            alert.setResizable(false);
            // 경고창을 보여주고 기다린다
            alert.showAndWait();

         } else if (cbxState.getSelectionModel().getSelectedItem() == null) {
            // 상태 콤보박스가 null일 경우
            Alert alert;
            alert = new Alert(AlertType.WARNING);
            alert.setTitle("판매 등록오류");
            alert.setHeaderText("판매 유형을 선택하십시오..");
            alert.setContentText("");
            // 경고창 크기설정 불가
            alert.setResizable(false);
            // 경고창을 보여주고 기다린다
            alert.showAndWait();
         } else {
            // 콤보박스의 값들이 전부 설정되었을경우

            int index = tableSaleInsert.getSelectionModel().getSelectedIndex(); // 선택한 행의 인덱스저장

            // 테이블에서 선택한 정보를 selectSubject에 저장
            selectInsert = tableSaleInsert.getSelectionModel().getSelectedItems();
            selectedSaleIndex = selectInsert.get(0).getP_no();
            selectInsert.get(0).getP_code();
            selectInsert.get(0).getP_ea();
            selectInsert.get(0).getP_price();
            selectInsert.get(0).getP_point();
            selectInsert.get(0).getP_total();
            selectInsert.get(0).getP_name();

            // 인스턴스 생성
            CustomerDAO cdao = new CustomerDAO();
            // 배열생성
            ArrayList<CustomerVO> list = new ArrayList<>();
            // 콤보박스에서 설정된 직원명 저장
            String e_name = cbxE_name.getSelectionModel().getSelectedItem().toString();

            // 직원코드, 판매유형변수 선언
            String e_code;
            String sr_state;
            // 고객검색 메소드에 고객명 텍스트상자에 입력된 데이터를 공백제거후 넣어준다.
            // 리스트배열 반환후 저장
            list = cdao.getCustomerSearch(txtC_name.getText().trim());

            // 선택된 행의 고객코드 저장
            int c_code = list.get(0).getC_code();

            // 텍스트에 입력된 수량을 int타입으로 변환후 단가와 곱하고 총액으로 저장
            int sr_total = Integer.parseInt(p_ea.getText()) * selectInsert.get(0).getP_price();
            // 총액에 100을 나눠주고 포인트로 저장
            int sr_point = sr_total / 100;
            // 사용포인트 0으로 저장
            int used_point = 0;

            // 인스턴스 생성
            EmployeeDAO edao = new EmployeeDAO();
            SaleReturnDAO srdao = new SaleReturnDAO();

            // 시퀀스번호를 불러와 저장
            int saleRetrunNo = srdao.getSale_returnNO();

            // 직원명을 공백제거후 매개변수로 넣어주고 직원코드를 반환받아서 저장
            e_code = edao.getEmployeeCode(e_name.trim());
            // 콤보박스에서 설정된 Item 저장
            sr_state = cbxState.getSelectionModel().getSelectedItem().toString();
            // 달력에서 선택된 날짜 저장
            String buildDate = dpDate.getValue().toString();
            // 반품사유 콤보박스에서 선택된 Item저장
            String returnReason = cbxReturnReason.getSelectionModel().getSelectedItem();

            // 콤보박스에서 선택된 Item을 저장하는 변수가 판매일경우
            if (sr_state.equals("판매")) {
               // 선택되었던 행의 데이터를 가져와 SaleVO객체에 담는다
               SaleVO svo = new SaleVO(saleRetrunNo, selectInsert.get(0).getP_code(),
                     selectInsert.get(0).getP_name(), sr_state.trim(), Integer.parseInt(p_ea.getText()),
                     selectInsert.get(0).getP_price(), sr_total, sr_point, 0, buildDate);

               // 판매햔황테이블에 담는다.
               saleListDataList.add(svo);

               // 판매일경우 반품사유 없음으로 설정
               returnReason = "없음";
               // 판매등록후 테이블에 값 입력
               srdao.insertSale_return(c_code, selectInsert.get(0).getP_code(), e_code, sr_total, sr_state,
                     Integer.parseInt(p_ea.getText()), returnReason, buildDate);
               // 판매등록후 재고테이블 수량변경
               srdao.setProductTable(Integer.parseInt(p_ea.getText()), selectInsert.get(0).getP_code());
               // 재고테이블 새로고침
               productTotalList();
               // 판매등록후 고객 포인트 변경
               srdao.setCustomerPoint(sr_point, c_code);

               // 선택한 행의 정보 삭제
               saleInsertDataList.remove(index);

            } else if (sr_state.equals("반품")) {
               // 콤보박스에서 선택된 Item을 저장하는 변수가 반품일경우
               if (returnReason == null) {
                  // 반품사유가 null일경우
                  Alert alert;
                  alert = new Alert(AlertType.WARNING);
                  alert.setTitle("반품 등록 오류");
                  alert.setHeaderText("반품사유를 입력하십시오");
                  alert.setContentText("");
                  // 경고창 크기설정 불가
                  alert.setResizable(false);
                  // 경고창을 보여주고 기다린다
                  alert.showAndWait();

               } else {
                  // 반품사유까지 설정되었을경우 실행.

                  // 선택된 행의 데이터를 객체에 담는다.
                  SaleVO svo = new SaleVO(saleRetrunNo, selectInsert.get(0).getP_code(),
                        selectInsert.get(0).getP_name(), sr_state.trim(), Integer.parseInt(p_ea.getText()),
                        selectInsert.get(0).getP_price(), sr_total, sr_point, 0, buildDate);
                  // 판매현황 테이블에 데이터 추가
                  saleListDataList.add(svo);

                  // 판매등록후 테이블에 값 입력
                  srdao.insertSale_return(c_code, selectInsert.get(0).getP_code(), e_code, sr_total, sr_state,
                        Integer.parseInt(p_ea.getText()), returnReason, buildDate);
                  // 판매등록후 재고테이블 수량변경
                  srdao.setProductTable(-Integer.parseInt(p_ea.getText()), selectInsert.get(0).getP_code());
                  // 재고테이블 새로고침
                  productTotalList();
                  // 판매등록후 고객 포인트 변경
                  srdao.setCustomerPoint(-sr_point, c_code);

                  // 선택한 행의 정보 삭제
                  saleInsertDataList.remove(index);
               }

            } else if (sr_state.equals("포인트 사용")) {
               // 콤보박스에서 선택된 Item을 저장하는 변수가 포인트 사용 일경우

               if (txtC_name.getText().equals("FREE")) {
                  // 고객명 초기값인 FREE는 포인트 사용으로 등록할수 없음.
                  Alert alert;
                  alert = new Alert(AlertType.WARNING);
                  alert.setTitle("포인트 사용 오류");
                  alert.setHeaderText("FREE고객은 포인트를 사용할수 없습니다.");
                  alert.setContentText("");
                  // 경고창 크기설정 불가
                  alert.setResizable(false);
                  // 경고창을 보여주고 기다린다
                  alert.showAndWait();
               } else if (txtUsedPoint.getText().length() == 0) {
                  // 고객명이 FREE가 아닌데 입력된 사용포인트의 길이가 0일경우

                  Alert alert;
                  alert = new Alert(AlertType.WARNING);
                  alert.setTitle("포인트 오류");
                  alert.setHeaderText("포인트 사용 금액을 입력하십시오..");
                  alert.setContentText("");
                  // 경고창 크기설정 불가
                  alert.setResizable(false);
                  // 경고창을 보여주고 기다린다
                  alert.showAndWait();
               } else {
                  // 고객명과 포인트 사용금액이 정상적으로 입력되었을 경우 실행.

                  // 포인트 사용금액 텍스트상자에 입력된 값을 int로 변환후 저장
                  int uspoint = Integer.parseInt(txtUsedPoint.getText().trim());

                  // 고객명이 설정될경우 고객코드로 검색하여 고객의 포인트를 가져와 static변수에 담아주고
                  // 포인트 사용시 가져와서 비교한다.
                  if (customer_point < uspoint) {
                     // 고객의 포인트가 사용하려는 포인트보다 적을경우
                     Alert alert;
                     alert = new Alert(AlertType.WARNING);
                     alert.setTitle("포인트 부족");
                     alert.setHeaderText("포인트가 부족합니다..");
                     alert.setContentText("");
                     // 경고창 크기설정 불가
                     alert.setResizable(false);
                     // 경고창을 보여주고 기다린다
                     alert.showAndWait();
                  } else {
                     // 고객의 포인트가 사용하려는 금액보다 많을경우.
                     if (uspoint <= sr_total) {
                        // 사용포인트가 구매 총액보다 작을경우.

                        returnReason = "없음"; // 반품사유 없음으로 설정
                        sr_total = sr_total - uspoint; // 총액에 사용한 포인트만큼 차감
                        sr_point = sr_total / 100; // 차감된 총액에서 100을 나눠준후 적립될 포인트로 설정

                        // 선택된 행의 데이터를 객체에 담는다.
                        SaleVO svo = new SaleVO(saleRetrunNo, selectInsert.get(0).getP_code(),
                              selectInsert.get(0).getP_name(), sr_state.trim(),
                              Integer.parseInt(p_ea.getText()), selectInsert.get(0).getP_price(), sr_total,
                              sr_point, uspoint, buildDate);

                        // 객체에 담긴 데이터를 판매현황 테이블에 추가한다.
                        saleListDataList.add(svo);

                        // 판매등록후 테이블에 값 입력
                        srdao.insertSale_return_used_point(c_code, selectInsert.get(0).getP_code(), e_code,
                              sr_total, sr_state, Integer.parseInt(p_ea.getText()), returnReason, uspoint,
                              buildDate);
                        // 판매등록후 재고테이블 수량변경
                        srdao.setProductTable(Integer.parseInt(p_ea.getText()),
                              selectInsert.get(0).getP_code());
                        // 재고테이블 새로고침
                        productTotalList();
                        // 판매등록후 고객 포인트 변경
                        srdao.setCustomerPoint(sr_point, c_code);
                        // 사용한 포인트만큼 차감
                        srdao.setCustomerPoint(-uspoint, c_code);

                        // 선택한 행의 정보 삭제
                        saleInsertDataList.remove(index);
                     } else {
                        // 포인트 사용 금액이 총액보다 클경우
                        returnReason = "없음";
                        // 사용하려는 포인트를 총액과 같은 값으로 설정.
                        uspoint = sr_total;
                        sr_total = 0; // 총액에 사용한 포인트만큼 차감
                        sr_point = 0; // 적립될 포인트 0으로 설정

                        // 선택된 행의 데이터를 객체에 담는다
                        SaleVO svo = new SaleVO(saleRetrunNo, selectInsert.get(0).getP_code(),
                              selectInsert.get(0).getP_name(), sr_state.trim(),
                              Integer.parseInt(p_ea.getText()), selectInsert.get(0).getP_price(), sr_total,
                              sr_point, uspoint, buildDate);

                        // 객체에 담긴 데이터를 테이블에 추가한다.
                        saleListDataList.add(svo);

                        // 판매등록후 테이블에 값 입력
                        srdao.insertSale_return_used_point(c_code, selectInsert.get(0).getP_code(), e_code,
                              sr_total, sr_state, Integer.parseInt(p_ea.getText()), returnReason, uspoint,
                              buildDate);
                        // 판매등록후 재고테이블 수량변경
                        srdao.setProductTable(-Integer.parseInt(p_ea.getText()),
                              selectInsert.get(0).getP_code());
                        // 재고테이블 새로고침
                        productTotalList();
                        // 사용한 포인트만큼 차감
                        srdao.setCustomerPoint(-uspoint, c_code);

                        // 선택한 행의 정보 삭제
                        saleInsertDataList.remove(index);
                     }
                  }
               }
            }
            // tableCustomerList.setOnMouseClicked(event);
            System.out.println(txtC_name.getText());
            System.out.println(customerNo);
            if (!(txtC_name.getText().equals("FREE"))) {
               ArrayList<CustomerVO> list2 = new ArrayList();
               list2 = cdao.customerInfo(customerNo);

               lblCInfo.setText("주소 : " + list2.get(0).getC_address() + "\t생년월일 : " + list2.get(0).getC_birth()
                     + "\t핸드폰번호 : " + list2.get(0).getC_phoneNumber() + "\t포인트 : " + list2.get(0).getC_point());

            }
         }
      } catch (Exception e) {

         Alert alert;
         alert = new Alert(AlertType.WARNING);
         alert.setTitle("정보가 없습니다");
         alert.setHeaderText("판매등록할 정보를 선택하십시오");
         alert.setContentText("");
         // 경고창 크기설정 불가
         alert.setResizable(false);
         // 경고창을 보여주고 기다린다
         alert.showAndWait();
      }
   }

   // 판매 입력 클릭 이벤트 메소드
   public void handlerTableSaleInsertAction(MouseEvent event) {

      if (event.getClickCount() == 1) { // 판매 입력 클릭시
         try {

            cbxState.setDisable(false); // 상태 콤보박스 활성화

         } catch (Exception e) {
            e.printStackTrace();
         }
      }

   }

   // 상태 콤보박스 이벤트 메소드
   public void handlerCbxStateAction(ActionEvent event) {
      // 상태 콤보박스에서 선택된 Item저장
      String state = cbxState.getSelectionModel().getSelectedItem();

      if (state.equals("판매")) {
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
      employeeName();
      if (event.getClickCount() == 2) { // 더블클릭시
         try {

            // 테이블에서 선택한 정보를 selectSubject에 저장
            selectProduct = tableProduct.getSelectionModel().getSelectedItems();

            // 선택된 행의 데이터를 변수에 저장
            String selectedP_code = selectProduct.get(0).getP_code();
            String selectedP_name = selectProduct.get(0).getP_name();
            int selecetedP_ea = selectProduct.get(0).getP_ea();
            int selectedP_price = selectProduct.get(0).getP_price();
            int selectedP_total = selecetedP_ea * selectedP_price; // 수량 * 단가로 총액 설정
            int selectedP_point = selectedP_total / 100; // 총액 /100 으로 포인트 설정

            // 인스턴스 생성
            EmployeeDAO edao = new EmployeeDAO();

            // 선택된 행의 데이터를 객체에 담는다
            ProductVO pvo = new ProductVO(selectedP_code, selectedP_name, selecetedP_ea, selectedP_price,
                  selectedP_total, selectedP_point);

            // 객체에 담긴 데이터를 테이블에 추가한다.
            saleInsertDataList.add(pvo);

         } catch (Exception e) {
            e.printStackTrace();
         }
      }

   }

   // 고객명 검색 메소드
   public void customerSearch() {

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

            // 여기하는중
            tableCustomerList.setOnMouseClicked(e -> {
               if (e.getClickCount() == 2) {
                  // Stage stage = (Stage) btnSearch.getScene().getWindow();
                  String selectedCustomerName = tableCustomerList.getSelectionModel().getSelectedItem()
                        .getC_name();
                  String selectedCustomerAddress = tableCustomerList.getSelectionModel().getSelectedItem()
                        .getC_address();
                  int selectedCustomerCode = tableCustomerList.getSelectionModel().getSelectedItem().getC_code();
                  String selectedCustomerBirth = tableCustomerList.getSelectionModel().getSelectedItem()
                        .getC_birth();
                  String selectedCustomerPhonenumber = tableCustomerList.getSelectionModel().getSelectedItem()
                        .getC_phoneNumber();
                  String seletedCustomerEtc = tableCustomerList.getSelectionModel().getSelectedItem().getC_etc();

                  int selectedCustomerPoint = tableCustomerList.getSelectionModel().getSelectedItem()
                        .getC_point();
                  customerNo = tableCustomerList.getSelectionModel().getSelectedItem().getC_code();
                  if (!(selectedCustomerName.contentEquals(""))) {
                     dialog.close();

                     txtC_name.setText(selectedCustomerName);
                     // 선택된 고객정보로 라벨과 텍스트상자 설정
                     lblCInfo.setText("주소 : " + selectedCustomerAddress + "\t생년월일 : " + selectedCustomerBirth
                           + "\t핸드폰번호 : " + selectedCustomerPhonenumber + "\t포인트 : " + selectedCustomerPoint);
                     /*
                      * txtC_name.setText(selectedCustomerName);
                      * txtBirth.setText(selectedCustomerBirth);
                      * txtPhone.setText(selectedCustomerPhonenumber);
                      * txtAddress.setText(selectedCustomerAddress);
                      */
                     taBigo.setText(seletedCustomerEtc);

                  }
               }
            });

            // 고객 전체 리스트를 테이블뷰에 보임
            CustomerDAO cdao = new CustomerDAO();
            CustomerVO cvo = null;

            // 배열생성
            ArrayList<CustomerVO> list;

            // 고객전체 정보를 가져온다.
            list = cdao.getCustomerTotalList();
            // 배열의 사이즈 저장
            int rowCount = list.size();
            // 배열의 사이즈만큼 반복문 실행
            for (int index = 0; index < rowCount; index++) {
               // 객체에 list배열의 index번쨰 데이터를 담는다
               cvo = list.get(index);
               // 객체에 담긴 데이터 테이블에 추가
               customerDataList.add(cvo);
            }

            // 검색 버튼 이벤트
            btnSearch.setOnAction(e -> {

               try {
                  if (c_name.getText().equals("")) {
                     // 고객명 텍스트상자가 공백일경우

                     // 고객테이블 비운다.
                     customerDataList.removeAll(customerDataList);

                     // 인스턴스 생성
                     CustomerDAO cDao = new CustomerDAO();
                     CustomerVO cVo = null;

                     // 배열생성
                     ArrayList<CustomerVO> emptyList;

                     // 고객정보저장
                     emptyList = cDao.getCustomerTotalList();
                     // 배열 사이즈 저장
                     int emptyRowCount = list.size();
                     // 배열의 사이즈만큼 반복문 실행
                     for (int index = 0; index < emptyRowCount; index++) {
                        // 배열의 데이터를 객체에 담는다
                        cVo = emptyList.get(index);
                        // 객체에 담긴 데이터 테이블에 추가
                        customerDataList.add(cVo);
                     }

                  } else {
                     // 테이블을 비운다.
                     customerDataList.removeAll(customerDataList);

                     // 배열생성
                     ArrayList<CustomerVO> newSearchList = new ArrayList<CustomerVO>();
                     // 인스턴스 선언
                     CustomerVO cVo = null;
                     CustomerDAO cDao = null;
                     boolean newSearchResult = false; // 검색 결과

                     // 입력받은 고객명 저장
                     String getCname = c_name.getText().trim();

                     // 인스턴스 생성
                     cDao = new CustomerDAO();
                     // 고객명으로 검색해서 데이터를 가져옴
                     newSearchList = cDao.getCustomerSearch(getCname);

                     if (newSearchList != null) {
                        int emptyRowCount = newSearchList.size();
                        c_name.clear();

                        for (int index = 0; index < emptyRowCount; index++) {
                           cVo = newSearchList.get(index);
                           customerDataList.add(cVo);
                           newSearchResult = true;
                        }
                     }

                     if (!newSearchResult) {
                        c_name.clear(); // 검색 텍스트 필드의 값을 지운다
                        Alert alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("고객 검색");
                        alert.setHeaderText(getCname + "고객이 리스트에 없습니다");
                        alert.setContentText("고객명을 다시 입력하세요");
                        alert.showAndWait(); // 확인 창 누르기 전까지 대기

                        customerDataList.removeAll(customerDataList);

                        ArrayList<CustomerVO> emptyList;

                        emptyList = cDao.getCustomerTotalList();
                        int emptyRowCount = list.size();

                        for (int index = 0; index < emptyRowCount; index++) {
                           cVo = list.get(index);
                           customerDataList.add(cVo);
                        }
                     }
                  }
               } catch (Exception e1) {
                  e1.printStackTrace();
               }

            });

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

            customerDataList.removeAll(customerDataList);

            ArrayList<CustomerVO> searchList = new ArrayList<CustomerVO>();
            CustomerVO cvo = null;
            CustomerDAO cdao = null;
            boolean searchResult = false; // 검색 결과
            cdao = new CustomerDAO();
            searchList = cdao.getCustomerSearch("");

            // 여기다 복붙해
            tableCustomerList.setOnMouseClicked(e -> {
               if (e.getClickCount() == 2) {
                  // Stage stage = (Stage) btnSearch.getScene().getWindow();
                  String selectedCustomerName = tableCustomerList.getSelectionModel().getSelectedItem()
                        .getC_name();
                  String selectedCustomerAddress = tableCustomerList.getSelectionModel().getSelectedItem()
                        .getC_address();
                  int selectedCustomerCode = tableCustomerList.getSelectionModel().getSelectedItem().getC_code();
                  String selectedCustomerBirth = tableCustomerList.getSelectionModel().getSelectedItem()
                        .getC_birth();
                  String selectedCustomerPhonenumber = tableCustomerList.getSelectionModel().getSelectedItem()
                        .getC_phoneNumber();
                  String seletedCustomerEtc = tableCustomerList.getSelectionModel().getSelectedItem().getC_etc();
                  int selectedCustomerPoint = tableCustomerList.getSelectionModel().getSelectedItem()
                        .getC_point();
                  customerNo = tableCustomerList.getSelectionModel().getSelectedItem().getC_code();
                  CustomerDAO cdao2 = new CustomerDAO();
                  ArrayList<CustomerVO> list2 = new ArrayList();
                  list2 = cdao2.customerInfo(customerNo);
                  customer_point = list2.get(0).getC_point();
                  if (!(selectedCustomerName.contentEquals(""))) {
                     dialog.close();

                     txtC_name.setText(selectedCustomerName);
                     // 선택된 고객정보로 라벨과 텍스트상자 설정
                     lblCInfo.setText("주소 : " + selectedCustomerAddress + "\t생년월일 : " + selectedCustomerBirth
                           + "\t핸드폰번호 : " + selectedCustomerPhonenumber + "\t포인트 : " + customer_point);

                     taBigo.setText(list2.get(0).getC_etc());

                  }
               }
            });

            if (searchList != null) {
               int rowCount = searchList.size();
               c_name.clear();

               for (int index = 0; index < rowCount; index++) {
                  cvo = searchList.get(index);
                  customerDataList.add(cvo);
                  searchResult = true;
               }
            }

            if (!searchResult) {
               c_name.clear(); // 검색 텍스트 필드의 값을 지운다
               Alert alert = new Alert(AlertType.INFORMATION);
               alert.setTitle("고객 검색");
               alert.setHeaderText(SearchName + "고객이 리스트에 없습니다");
               alert.setContentText("고객명을 다시 입력하세요");
               alert.showAndWait(); // 확인 창 누르기 전까지 대기

               customerDataList.removeAll(customerDataList);

               CustomerDAO cDao = new CustomerDAO();
               CustomerVO cVo = null;

               ArrayList<String> title;
               ArrayList<CustomerVO> list;

               title = cDao.getCustomerColumnName();
               int columnCount = title.size();

               list = cDao.getCustomerTotalList();
               int rowCount = list.size();

               for (int index = 0; index < rowCount; index++) {
                  cVo = list.get(index);
                  customerDataList.add(cVo);
                  searchResult = false;
               }
            }

            // 검색 버튼 이벤트
            btnSearch.setOnAction(e -> {

               try {
                  if (c_name.getText().equals("")) {

                     customerDataList.removeAll(customerDataList);

                     CustomerDAO cDao = new CustomerDAO();
                     CustomerVO cVo = null;

                     ArrayList<String> title;
                     ArrayList<CustomerVO> list;

                     title = cDao.getCustomerColumnName();
                     int columnCount = title.size();

                     list = cDao.getCustomerTotalList();
                     int rowCount = list.size();

                     for (int index = 0; index < rowCount; index++) {
                        cVo = list.get(index);
                        customerDataList.add(cVo);
                     }

                  } else {
                     customerDataList.removeAll(customerDataList);

                     ArrayList<CustomerVO> newSearchList = new ArrayList<CustomerVO>();
                     CustomerVO cVo = null;
                     CustomerDAO cDao = null;
                     boolean newSearchResult = false; // 검색 결과
                     String getCname = c_name.getText().trim();

                     cDao = new CustomerDAO();
                     newSearchList = cDao.getCustomerSearch(getCname);

                     if (newSearchList != null) {
                        int rowCount = newSearchList.size();
                        c_name.clear();

                        for (int index = 0; index < rowCount; index++) {
                           cVo = newSearchList.get(index);
                           customerDataList.add(cVo);
                           newSearchResult = true;
                        }
                     }

                     if (!newSearchResult) {
                        c_name.clear(); // 검색 텍스트 필드의 값을 지운다
                        Alert alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("고객 검색");
                        alert.setHeaderText(getCname + "고객이 리스트에 없습니다");
                        alert.setContentText("고객명을 다시 입력하세요");
                        alert.showAndWait(); // 확인 창 누르기 전까지 대기

                        customerDataList.removeAll(customerDataList);

                        ArrayList<String> title;
                        ArrayList<CustomerVO> list;

                        title = cDao.getCustomerColumnName();
                        int columnCount = title.size();

                        list = cDao.getCustomerTotalList();
                        int rowCount = list.size();

                        for (int index = 0; index < rowCount; index++) {
                           cVo = list.get(index);
                           customerDataList.add(cVo);
                        }
                     }
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

   // 고객명 검색 버튼 이벤트 메소드
   public void handlerBtnCSearchAction(ActionEvent event) {
      customerSearch();
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

   // 판매 내역 리스트
   public void saleReturnTotalList() throws Exception {
      saleListDataList.removeAll(productDataList);
      SaleReturnDAO sdao = new SaleReturnDAO();
      SaleVO svo = null;

      ArrayList<SaleVO> list;

      list = sdao.getSaleReturndpdate(dpDate.getValue().toString());
      int rowCount = list.size();

      for (int index = 0; index < rowCount; index++) {
         svo = list.get(index);
         saleListDataList.add(svo);
      }

   }

   // 직원명 가져오기
   public void employeeName() {

      EmployeeDAO edao = new EmployeeDAO();
      ArrayList employeeName = new ArrayList<>();

      try {
         employeeName = edao.getEmployeeTotalList();
         System.out.println("123");
         cbxE_name.setItems(FXCollections.observableArrayList(employeeName));
      } catch (Exception e) {
         e.printStackTrace();
      }

   }

}