package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

}
