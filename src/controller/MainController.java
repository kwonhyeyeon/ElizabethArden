package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainController implements Initializable {

	// 메뉴
	@FXML
	private MenuItem menuLogout; // 로그아웃
	@FXML
	private MenuItem menuExit; // 종료
	@FXML
	private MenuItem menuStoreInfoEdit; // 매장 정보 변경
	@FXML
	private MenuItem menuEmployeeRegiste; // 직원 등록
	@FXML
	private MenuItem menuEmployeeInfoEdit; // 직원 정보 변경

	// 탭
	@FXML
	private TabPane mainPane; // 메인탭
	@FXML
	private Tab sale; // 판매 탭
	@FXML
	// 참조변수명 부여 방법 : include시 명시한 id + "Controller"
	private SaleTabController saleTabController;
	@FXML
	private Tab order; // 주문 탭
	@FXML
	private OrderTabController orderTabController;
	@FXML
	private Tab rtrn; // 반품 탭
	@FXML
	private ReturnTabController returnTabController;
	@FXML
	private Tab customer; // 고객 탭
	@FXML
	private CustomerTabController customerTabController;
	@FXML
	private Tab salesStatus; // 판매현황 탭
	@FXML
	private StatusTabControllder statusTabControllder;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// 탭 설정
		try {

			mainPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {

				@Override
				public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) {

					if (newValue == sale) { // 판매 탭일 경우

						try {
							// 판매 탭 재고 전체 리스트
							saleTabController.productTotalList();
						} catch (Exception e) {
							e.printStackTrace();
						}

					} else if (newValue == order) { // 주문 탭일 경우

						try {
							// 주문 탭 재고 전체 리스트
							orderTabController.productTotalList();
						} catch (Exception e) {
							e.printStackTrace();
						}

					} else if (newValue == rtrn) { // 반품 탭일 경우

						try {
							// 반품 탭 재고 전체 리스트
							returnTabController.productTotalList();
						} catch (Exception e) {
							e.printStackTrace();
						}

					} else if (newValue == customer) { // 고객 탭일 경우

						try {

						} catch (Exception e) {
							e.printStackTrace();
						}

					} else if (newValue == salesStatus) { // 판매현황 탭일 경우

						try {

						} catch (Exception e) {
							e.printStackTrace();
						}

					}

				}
			});

			// 홈 메뉴 이벤트 등록
			menuLogout.setOnAction(event -> handlerMenuLogoutAction(event));
			menuExit.setOnAction(event -> handlerMenuExitAction(event));

			// 정보 메뉴 이벤트 등록
			menuStoreInfoEdit.setOnAction(event -> handlerMenuStoreInfoEditAction(event));
			menuEmployeeRegiste.setOnAction(event -> handlerMenuEmployeeRegisteAction(event));
			menuEmployeeInfoEdit.setOnAction(event -> handlerMenuEmployeeInfoEdit(event));
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	// 직원 정보 변경 이벤트 메소드
	public void handlerMenuEmployeeInfoEdit(ActionEvent event) {

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/employeeInfoEdit.fxml")); // 레이아웃 불러오기
			Parent mainView = (Parent) loader.load(); // 부모창을 login.fxml로 로드
			Scene scene = new Scene(mainView); // Scene 객체 생성
			Stage mainStage = new Stage(); // Stage 객체 생성
			mainStage.setTitle("직원 정보 변경"); // 타이틀 설정
			mainStage.initModality(Modality.WINDOW_MODAL);
			mainStage.initOwner(mainPane.getScene().getWindow());
			mainStage.setResizable(false); // 리사이즈 불가
			mainStage.setScene(scene); // 씬 설정
			mainStage.show(); // 로그인 창 열기

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// 직원 등록 이벤트 메소드
	public void handlerMenuEmployeeRegisteAction(ActionEvent event) {

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/employeeRegiste.fxml")); // 레이아웃 불러오기
			Parent mainView = (Parent) loader.load(); // 부모창을 login.fxml로 로드
			Scene scene = new Scene(mainView); // Scene 객체 생성
			Stage mainStage = new Stage(); // Stage 객체 생성
			mainStage.setTitle("직원 등록"); // 타이틀 설정
			mainStage.initModality(Modality.WINDOW_MODAL);
			mainStage.initOwner(mainPane.getScene().getWindow());
			mainStage.setResizable(false); // 리사이즈 불가
			mainStage.setScene(scene); // 씬 설정
			mainStage.show(); // 로그인 창 열기

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// 매장 정보 변경 이벤트 메소드
	public void handlerMenuStoreInfoEditAction(ActionEvent event) {

		try {


			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/storeInfoEdit.fxml")); // 레이아웃 불러오기
			Parent mainView = (Parent) loader.load(); // 부모창을 login.fxml로 로드
			Scene scene = new Scene(mainView); // Scene 객체 생성
			Stage mainStage = new Stage(); // Stage 객체 생성
			mainStage.setTitle("매장 정보 변경"); // 타이틀 설정
			mainStage.initModality(Modality.WINDOW_MODAL);
			mainStage.initOwner(mainPane.getScene().getWindow());
			mainStage.setResizable(false); // 리사이즈 불가
			mainStage.setScene(scene); // 씬 설정
			mainStage.show(); // 로그인 창 열기

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// 프로그램 종료 이벤트 메소드
	public void handlerMenuExitAction(ActionEvent event) {
		Platform.exit();
		// 로그인한 매장코드를 가져오는방법
		LoginController lc = new LoginController();
		System.out.println(lc.loginStoreCode);
	
	}

	// 로그아웃 이벤트 메소드
	public void handlerMenuLogoutAction(ActionEvent event) {

		try {
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login.fxml")); // 레이아웃 불러오기
			Parent mainView = (Parent) loader.load(); // 부모창을 login.fxml로 로드
			Scene scene = new Scene(mainView); // Scene 객체 생성
			Stage mainStage = new Stage(); // Stage 객체 생성
			mainStage.setTitle("엘리자베스아덴 매장 로그인"); // 타이틀 설정
			mainStage.setResizable(false); // 리사이즈 불가
			mainStage.setScene(scene); // 씬 설정
			Stage oldStage = (Stage) mainPane.getScene().getWindow(); // 새 스테이지(탭) 추가
			oldStage.close(); // 탭 창 닫음
			mainStage.show(); // 로그인 창 열기

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
