package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.OrderVO;
import model.ProductVO;
import model.SaleVO;

public class OrderDAO {

	// 주문, 반품 시퀀스
	public int getOrderSeq() throws Exception {

		String sql = "select order_return_seq.nextval from dual";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		int orderNo = 0;

		try {
			// DB연동
			con = DBUtil.getConnection();
			// sql문을 담아줄 그릇
			pstmt = con.prepareStatement(sql);
			// jvo에서 변수들을 가져와서 sql문에 넣어준다.
			rs = pstmt.executeQuery();
			// sql을 날리고 불러온 값이 있으면 로그인결과변수 true

			if (rs.next()) {
				orderNo = rs.getInt("nextval");
			}
		} catch (SQLException e) {
			System.out.println(e);
		} catch (Exception e) {
			System.out.println(e);
		} finally {

			try {
				// 데이터베이스와의 연결에 사용되었던 오브젝트를 해제
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
			}
		}

		return orderNo;

	}

	// 주문 등록
	public ArrayList<OrderVO> getOrderInsert(ArrayList<OrderVO> selectedItem) throws Exception {

		ArrayList<OrderVO> orderProduct = new ArrayList<OrderVO>();
		LoginController lc = new LoginController();
		String storeCode = lc.loginStoreCode;
		String sql = "insert into order_return values (sale_return_seq.nextval, ?, ?, ?, ?, '주문', ?, sysdate)";
		Connection con = null;
		PreparedStatement pstmt = null;

		// 등록 성공 판단 변수
		boolean insertResult = false;

		try {

			for (int index = 0; index < selectedItem.size(); index++) {
				// DB연동
				con = DBUtil.getConnection();
				// sql문을 담아줄 그릇
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, selectedItem.get(index).getP_code());
				pstmt.setString(2, storeCode);
				pstmt.setInt(3, selectedItem.get(index).getOr_ea());
				pstmt.setInt(4, selectedItem.get(index).getOr_total());
				pstmt.setString(5, selectedItem.get(index).getOr_bad());
			}
			
			// insert문이 성공적으로 입력되면 1을 반환
			int i = pstmt.executeUpdate();

			if (i == 1) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("주문 등록");
				alert.setHeaderText("주문 등록 성공");
				alert.setContentText("주문 등록이 완료되었습니다");
				alert.showAndWait();
				// 등록성공 판단변수 true
				insertResult = true;
			} else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("주문 등록");
				alert.setHeaderText("주문 등록 실패");
				alert.setContentText("주문 등록을 다시 하세요");
				alert.showAndWait();
			}

		} catch (SQLException e) {
			System.out.println("e=[" + e + "]");
		} catch (Exception e) {
			System.out.println("e=[" + e + "]");
		} finally {
			try {
				// 데이터베이스와의 연결에 사용되었던 오브젝트를 해제
				if (pstmt != null) {
					pstmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				System.out.println(e);
			}
		}

		return orderProduct;

	}
	
	// 등록된 날짜로 주문현황 가져오는 메소드
	public ArrayList<OrderVO> getOrderDate(String or_date) throws Exception {

		ArrayList<OrderVO> list = new ArrayList<>();

		String sql = "select e.p_code, e.or_ea, e.or_total, p.p_name, p.p_price" + 
				" from(select distinct(p_code) p_code, sum(or_ea) or_ea, sum(or_total) or_total" + 
				" from order_return" + 
				" where to_char(or_date, 'yyyy-mm-dd') = ?" + 
				" group by p_code) e, product p" + 
				" where e.p_code = p.p_code";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		OrderVO ovo = null;

		try {

			con = DBUtil.getConnection(); // DBUtil 연결

			pstmt = con.prepareStatement(sql); // sql문을 prepareStatement로 실행한다
			pstmt.setString(1, or_date);
			rs = pstmt.executeQuery(); // 쿼리 실행

			while (rs.next()) {
				ovo = new OrderVO();
				ovo.setP_code(rs.getString("p_code"));
				ovo.setP_name(rs.getString("p_name"));
				ovo.setOr_ea(rs.getInt("or_ea"));
				ovo.setPrice(rs.getInt("p_price"));
				ovo.setOr_total(rs.getInt("or_total"));
				
				list.add(ovo);
				
			}

			if (ovo == null) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("날짜 검색");
				alert.setHeaderText(or_date + " 에 등록된 데이터가 없습니다.");
				alert.setContentText("");
				alert.showAndWait();
				// 등록성공 판단변수 true
			}

		} catch (SQLException se) {
			System.out.println(se);
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				// 데이터베이스와의 연결에 사용되었던 오브젝트를 해제
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException se) {
			}
		}
		return list;

	}

}
