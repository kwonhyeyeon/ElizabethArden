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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import model.CustomerVO;
import model.SaleVO;

public class CustomerTabController implements Initializable {

   // 고객 등록
   @FXML
   private TextField txtCustomerCode; // 고객 코드
   @FXML
   private TextField txtCustomerName; // 고객 이름
   @FXML
   private DatePicker dpCustomerBirth; // 고객 생년월일
   @FXML
   private TextField txtCustomerCodePhone; // 고객 핸드폰 번호
   @FXML
   private TextField txtCustomerAddress; // 고객 주소
   @FXML
   private TextField txtCustomerEmail; // 고객 이메일
   @FXML
   private TextArea txtAreaEtc; // 고객 비고
   @FXML
   private Button btnCancle; // 취소버튼
   @FXML
   private Button btnRegiste; // 등록버튼
   @FXML
   private Button btnEdit; // 수정 버튼

   // 고객 조회
   @FXML
   private TextField txtC_name; // 고객명
   @FXML
   private Button btnC_search; // 고객 검색 버튼
   @FXML
   private TableView<CustomerVO> tableCustomer; // 고객 조회 테이블
   @FXML
   private TableView<SaleVO> tableBuyList; // 구매 내역 테이블
   @FXML
   private Label lblTotal; // 고객이 총 구매한 금액, 적립포인트

   ObservableList<CustomerVO> customerDataList = FXCollections.observableArrayList(); // 고객조회 테이블
   ObservableList<SaleVO> buyDataList = FXCollections.observableArrayList(); // 구매내역 테이블

   ObservableList<CustomerVO> selectCustomer = null; // 고객조회 테이블에서 선택한 정보 저장
   int selectedCustomerIndex; // 고객조회 테이블에서 선택한 상품 정보 인덱스 저장

   ObservableList<SaleVO> selectBuy = null; // 고객조회 테이블에서 선택한 정보 저장
   int selectedBuyIndex; // 고객조회 테이블에서 선택한 상품 정보 인덱스 저장

   @Override
   public void initialize(URL arg0, ResourceBundle arg1) {

      // 버튼 비활성화
      btnEdit.setDisable(true);

      CustomerDAO cdao = new CustomerDAO();

      try {
         txtCustomerCode.setText(cdao.getCustomerCode() + "");
      } catch (Exception e) {
      }

      // 고객 조회 테이블 컬럼 지정
      TableColumn colCustomerCode = new TableColumn("고객 코드");
      colCustomerCode.setPrefWidth(100);
      colCustomerCode.setStyle("-fx-alignment:CENTER");
      colCustomerCode.setCellValueFactory(new PropertyValueFactory<>("c_code"));

      TableColumn colCustomerName = new TableColumn("고객명");
      colCustomerName.setPrefWidth(120);
      colCustomerName.setStyle("-fx-alignment:CENTER");
      colCustomerName.setCellValueFactory(new PropertyValueFactory<>("c_name"));

      TableColumn colCustomerBirth = new TableColumn("생년월일");
      colCustomerBirth.setPrefWidth(150);
      colCustomerBirth.setStyle("-fx-alignment:CENTER");
      colCustomerBirth.setCellValueFactory(new PropertyValueFactory<>("c_birth"));

      TableColumn colCustomerPhone = new TableColumn("핸드폰번호");
      colCustomerPhone.setPrefWidth(150);
      colCustomerPhone.setStyle("-fx-alignment:CENTER");
      colCustomerPhone.setCellValueFactory(new PropertyValueFactory<>("c_phoneNumber"));

      TableColumn colCustomerEmail = new TableColumn("이메일");
      colCustomerEmail.setPrefWidth(195);
      colCustomerEmail.setStyle("-fx-alignment:CENTER");
      colCustomerEmail.setCellValueFactory(new PropertyValueFactory<>("c_email"));

      TableColumn colCustomerAdrs = new TableColumn("주소");
      colCustomerAdrs.setPrefWidth(250);
      colCustomerAdrs.setStyle("-fx-alignment:CENTER");
      colCustomerAdrs.setCellValueFactory(new PropertyValueFactory<>("c_address"));

      tableCustomer.setItems(customerDataList);
      tableCustomer.getColumns().addAll(colCustomerCode, colCustomerName, colCustomerBirth, colCustomerPhone,
            colCustomerEmail, colCustomerAdrs);

      // 구매 내역 테이블 컬럼 지정
      TableColumn colProductCode = new TableColumn("상품 코드");
      colProductCode.setPrefWidth(100);
      colProductCode.setStyle("-fx-alignment:CENTER");
      colProductCode.setCellValueFactory(new PropertyValueFactory<>("p_code"));

      TableColumn colProductName = new TableColumn("상품명");
      colProductName.setPrefWidth(170);
      colProductName.setStyle("-fx-alignment:CENTER");
      colProductName.setCellValueFactory(new PropertyValueFactory<>("p_name"));

      TableColumn colState = new TableColumn("상태");
      colState.setPrefWidth(80);
      colState.setStyle("-fx-alignment:CENTER");
      colState.setCellValueFactory(new PropertyValueFactory<>("sr_state"));

      TableColumn colEa = new TableColumn("수량");
      colEa.setPrefWidth(80);
      colEa.setStyle("-fx-alignment:CENTER");
      colEa.setCellValueFactory(new PropertyValueFactory<>("sr_ea"));

      TableColumn colPrice = new TableColumn("단가");
      colPrice.setPrefWidth(100);
      colPrice.setStyle("-fx-alignment:CENTER");
      colPrice.setCellValueFactory(new PropertyValueFactory<>("p_price"));

      TableColumn colTotal = new TableColumn("총액");
      colTotal.setPrefWidth(120);
      colTotal.setStyle("-fx-alignment:CENTER");
      colTotal.setCellValueFactory(new PropertyValueFactory<>("sr_total"));

      TableColumn colPoint = new TableColumn("포인트");
      colPoint.setPrefWidth(100);
      colPoint.setStyle("-fx-alignment:CENTER");
      colPoint.setCellValueFactory(new PropertyValueFactory<>("p_point"));

      TableColumn colUsedPoint = new TableColumn("포인트 사용 금액");
      colUsedPoint.setPrefWidth(100);
      colUsedPoint.setStyle("-fx-alignment:CENTER");
      colUsedPoint.setCellValueFactory(new PropertyValueFactory<>("sr_used_point"));

      TableColumn colBuyDate = new TableColumn("구매일자");
      colBuyDate.setPrefWidth(120);
      colBuyDate.setStyle("-fx-alignment:CENTER");
      colBuyDate.setCellValueFactory(new PropertyValueFactory<>("build_date"));

      tableBuyList.setItems(buyDataList);
      tableBuyList.getColumns().addAll(colProductCode, colProductName, colState, colEa, colPrice, colTotal, colPoint,
            colUsedPoint, colBuyDate);

      txtCustomerCode.setDisable(true); // 고객코드 텍스트필드 비활성화

      // 등록 버튼 이벤트 핸들러
      btnRegiste.setOnAction(event -> handlerBtnRegisteAction(event));
      // 취소 버튼 이벤트 핸들러
      btnCancle.setOnAction(event -> handlerBtnCancleAction(event));
      // 수정 버튼 이벤트 핸들러
      btnEdit.setOnAction(event -> handlerBtnEditAction(event));

      // 고객 검색 버튼 이벤트 핸들러
      btnC_search.setOnAction(event -> handlerBtnCSearchAction(event));
      // 고객명 엔터키 이벤트 핸들러
      txtC_name.setOnKeyPressed(event -> handlerTxtCNamePressed(event));

      // 고객 리스트 더블 클릭시 이벤트 핸들러
      tableCustomer.setOnMouseClicked(event -> handlerTableCustomerAction(event));

   }

   // 수정 버튼 이벤트 메소드
   public void handlerBtnEditAction(ActionEvent event) {

      CustomerVO cvo = new CustomerVO();
      CustomerDAO cdao = new CustomerDAO();
      boolean customerUpdateSucess = false;

      try {

         cvo.setC_phoneNumber(txtCustomerCodePhone.getText());
         cvo.setC_address(txtCustomerAddress.getText());
         cvo.setC_email(txtCustomerEmail.getText());
         cvo.setC_etc(txtAreaEtc.getText());
         cvo.setC_code(Integer.parseInt(txtCustomerCode.getText()));

         customerUpdateSucess = cdao.getCustomerUpdate(cvo.getC_phoneNumber(), cvo.getC_address(), cvo.getC_email(),
               cvo.getC_etc(), cvo.getC_code());

         // 등록이 성공하였을 경우
         if (customerUpdateSucess) {
            // 닫기 버튼을 호출하여 창을 닫아준다
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("고객 정보 수정");
            alert.setHeaderText("고객 정보 수정 성공");
            alert.setContentText(txtCustomerName.getText() + "고객의 정보가 수정되었습니다");
            alert.showAndWait();
         }
         
         // 고객조회, 구매내역 테이블 초기화
         customerDataList.removeAll(customerDataList);
         buyDataList.removeAll(buyDataList);
         
         // 모든 텍스트 상자 초기화
         txtCustomerCode.clear();
         handlerBtnCancleAction(event);
         
         txtCustomerName.setDisable(false);
         dpCustomerBirth.setDisable(false);
         
         txtCustomerCode.setText(cdao.getCustomerCode() + "");
         
         btnEdit.setDisable(true);
         
      } catch (Exception e) {
         e.printStackTrace();
      }

   }

   // 고객 리스트 더블 클릭 이벤트
   public void handlerTableCustomerAction(MouseEvent event) {

      if (event.getClickCount() == 2) { // 더블 클릭시

         ArrayList<SaleVO> buyList = new ArrayList<SaleVO>();

         selectCustomer = tableCustomer.getSelectionModel().getSelectedItems();
         selectedCustomerIndex = selectCustomer.get(0).getC_code();

         SaleDAO sdao = new SaleDAO();

         try {

            buyList = sdao.getBuyList(selectedCustomerIndex);

            buyDataList.removeAll(buyDataList);

            SaleVO svo = null;
            CustomerVO cvo = null;

            for (int index = 0; index < buyList.size(); index++) {
               svo = new SaleVO(buyList.get(index).getP_code(), buyList.get(index).getP_name(),
                     buyList.get(index).getSr_state(), buyList.get(index).getSr_ea(),
                     buyList.get(index).getP_price(), buyList.get(index).getSr_total(),
                     buyList.get(index).getP_point(), buyList.get(index).getSr_used_point(),
                     buyList.get(index).getBuild_date());
               buyDataList.add(svo);
            }

            int totalPrice = 0;
            int totalPoint = 0;
            for (int i = 0; i < buyList.size(); i++) {
               totalPrice += buyList.get(i).getP_price();
               totalPoint += buyList.get(i).getP_point();
            }

            lblTotal.setText("총 구매 금액 : " + totalPrice + "\t\t적립 포인트 : " + totalPoint + "\t\t");

            // 수정
            txtCustomerName.setDisable(true);
            dpCustomerBirth.setDisable(true);
            txtCustomerCode.setText(selectCustomer.get(0).getC_code() + "");
            txtCustomerName.setText(selectCustomer.get(0).getC_name());
            txtCustomerCodePhone.setText(selectCustomer.get(0).getC_phoneNumber());
            txtCustomerAddress.setText(selectCustomer.get(0).getC_address());
            txtCustomerEmail.setText(selectCustomer.get(0).getC_email());
            txtAreaEtc.setText(selectCustomer.get(0).getC_etc());
            
            btnEdit.setDisable(false);
            btnRegiste.setDisable(true);

         } catch (Exception e1) {
            e1.printStackTrace();
         }

      }

   }

   // 고객명 엔터키 이벤트 메소드
   public void handlerTxtCNamePressed(KeyEvent event) {
      if (event.getCode() == KeyCode.ENTER) {
         customerSearch();
      }
   }

   // 고객 검색 버튼 이벤트 메소드
   public void handlerBtnCSearchAction(ActionEvent event) {
      customerSearch();
   }

   // 검색 이벤트
   public void customerSearch() {
      // 고객명 검색 텍스트 필드 값
      String SearchName = "";
      SearchName = txtC_name.getText().trim();

      if (SearchName.equals("")) { // 고객명 텍스트 필드 값이 비어있을 때

         customerDataList.removeAll(customerDataList);

         try {
            CustomerVO cvo = null;
            CustomerDAO cdao = new CustomerDAO();

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
         } catch (Exception e) {
            System.out.println(e);
         }

      } else {
         customerDataList.removeAll(customerDataList);

         ArrayList<CustomerVO> searchList = new ArrayList<CustomerVO>();
         CustomerVO cvo = null;
         CustomerDAO cdao = null;
         boolean searchResult = false; // 검색 결과

         try {
            cdao = new CustomerDAO();
            searchList = cdao.getCustomerSearch(SearchName);

            if (searchList != null) {
               int rowCount = searchList.size();
               txtC_name.clear();

               for (int index = 0; index < rowCount; index++) {
                  cvo = searchList.get(index);
                  customerDataList.add(cvo);
                  searchResult = true;
               }
            }

            if (!searchResult) {
               customerDataList.clear(); // 검색 텍스트 필드의 값을 지운다
               Alert alert = new Alert(AlertType.INFORMATION);
               alert.setTitle("고객 검색");
               alert.setHeaderText(SearchName + "고객이 리스트에 없습니다");
               alert.setContentText("고객명을 다시 입력하세요");
               alert.showAndWait(); // 확인 창 누르기 전까지 대기
            }
         } catch (Exception e) {
            e.printStackTrace();
         }

      }
   }
   
   // 등록버튼 이벤트 핸들러
   public void handlerBtnRegisteAction(ActionEvent event) {
      CustomerDAO cdao = new CustomerDAO();
      CustomerVO cvo = null;
      boolean ok = false;
      try {
         // 필드 미입력 오류
         if (txtCustomerName.getText().trim().equals("") || txtCustomerName.getText().length() > 5) { // 직원명 입력
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("고객 등록");
            alert.setHeaderText("고객명 오류");
            alert.setContentText("다시 입력해주세요");
            alert.showAndWait();
         } else if (dpCustomerBirth.getValue() == null) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("고객 등록");
            alert.setHeaderText("고객 생년월일");
            alert.setContentText("다시 입력해주세요");
            alert.showAndWait();
         } else if (txtCustomerCodePhone.getText().trim().equals("")) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("고객 등록");
            alert.setHeaderText("고객 핸드폰 번호 미입력");
            alert.setContentText("다시 입력해주세요");
            alert.showAndWait();
         } else if (txtCustomerAddress.getText().trim().equals("")) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("고객 등록");
            alert.setHeaderText("고객 주소 미입력");
            alert.setContentText("다시 입력해주세요");
            alert.showAndWait();
         } else {
            cvo = new CustomerVO(Integer.parseInt(txtCustomerCode.getText().trim()),
                  txtCustomerName.getText().trim(), txtCustomerCodePhone.getText().trim(),
                  txtCustomerAddress.getText().trim(), dpCustomerBirth.getValue().toString(),
                  txtCustomerEmail.getText().trim(), txtAreaEtc.getText());
            ok = cdao.customerRegiste(cvo);
         }
         if (ok) {
            handlerBtnCancleAction(event);
            txtCustomerCode.setText(cdao.getCustomerCode() + "");
         }
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   // 취소 버튼 이벤트 핸들러
   public void handlerBtnCancleAction(ActionEvent event) {
      // 모든 텍스트 상자 초기화
      txtCustomerName.clear();
      txtCustomerCodePhone.clear();
      txtCustomerAddress.clear();
      txtCustomerEmail.clear();
      txtAreaEtc.clear();
      dpCustomerBirth.setValue(null);
      
      btnEdit.setDisable(true); // 수정 버튼 비활성화
      dpCustomerBirth.setDisable(false);
      txtCustomerName.setDisable(false);
      btnRegiste.setDisable(false);
      
      try {
    	  CustomerDAO cdao = new CustomerDAO();
          txtCustomerCode.setText(cdao.getCustomerCode() + "");
	} catch (Exception e) {
		System.out.println(e);
	}
      
   }

}