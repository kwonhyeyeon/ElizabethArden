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

	// 주문 등록 메소드
	public boolean getOrderInsert(String p_code, int or_ea, int or_total, String or_bad) throws Exception {

		LoginController lc = new LoginController();
		String storeCode = lc.loginStoreCode;

		String sql = "insert into order_return values (sale_return_seq.nextval, ?, ?, ?, ?, '주문', ?, sysdate, 'N')";
		Connection con = null;
		PreparedStatement pstmt = null;

		// 등록 성공 판단 변수
		boolean insertResult = false;

		try {

			// DB연동
			con = DBUtil.getConnection();
			// sql문을 담아줄 그릇
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, p_code);
			pstmt.setString(2, storeCode);
			pstmt.setInt(3, or_ea);
			pstmt.setInt(4, or_total);
			pstmt.setString(5, or_bad);

			// insert문이 성공적으로 입력되면 1을 반환
			int i = pstmt.executeUpdate();

			if (i == 1) {
				// 등록성공 판단변수 true
				insertResult = true;
			} else {
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

		return insertResult;

	}

	// 등록된 날짜로 주문현황 가져오는 메소드
	public ArrayList<OrderVO> getOrderDate(String or_date) throws Exception {

		ArrayList<OrderVO> list = new ArrayList<>();

		String sql = "select e.p_code, e.or_ea, e.or_total, p.p_name, p.p_price, e.in_out"
				+ " from (select p_code, or_ea, or_total, in_out from order_return where to_char(or_date, 'yyyy-mm-dd') = ? and or_state = '주문') e, product p"
				+ " where e.p_code = p.p_code";
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
				ovo.setIn_out(rs.getString("in_out"));

				list.add(ovo);
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

	// 추가 주문 등록할 경우 상품명 중복 검사
	public boolean getOverLapProductName(String or_date, String p_code) throws Exception {

		ArrayList<OrderVO> list = new ArrayList();

		boolean overlap = false; // 중복검사 결과(중복된 값 없음)
		String sql = "select p_code from order_return where to_char(or_date, 'yyyy-mm-dd') = ? and p_code = ? and or_state = '주문'";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		OrderVO ovo = null;

		try {

			con = DBUtil.getConnection(); // DBUtil 연결

			pstmt = con.prepareStatement(sql); // sql문을 prepareStatement로 실행한다
			pstmt.setString(1, or_date);
			pstmt.setString(2, p_code);
			rs = pstmt.executeQuery(); // 쿼리 실행

			while (rs.next()) {
				ovo = new OrderVO();
				ovo.setP_code(rs.getString("p_code"));

				list.add(ovo);
			}

			if (ovo != null) {
				overlap = true; // 중복된 값 있으면 true
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

		return overlap;

	}

	// 중복된 값이 있을 경우 수량 변경
	public void updateEa(String or_date, String p_code, int or_ea) {
		String sql = "update order_return set or_ea = or_ea + ? where p_code = ? and to_char(or_date, 'yyyy-mm-dd') = ? and or_state = '주문'";
		Connection con = null;
		PreparedStatement pstmt = null;
		// boolean eaUpdateResult = false;

		try {
			con = DBUtil.getConnection(); // DBUtil 연결
			pstmt = con.prepareStatement(sql); // sql문을 prepareStatement로 실행한다
			pstmt.setInt(1, or_ea);
			pstmt.setString(2, p_code);
			pstmt.setString(3, or_date);

			int i = pstmt.executeUpdate();

			if (i == 1) {
				System.out.println(p_code + "의 수량변경 성공");
			} else {
				System.out.println(p_code + "의 수량변경 실패");
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
			}
		}

	}

	// 주문현황 전체리스트 가져오는 메소드
	public ArrayList<OrderVO> getOrderTotalList(String or_date) throws Exception {

		ArrayList<OrderVO> list = new ArrayList<>();

		String sql = "select e.p_code, e.or_ea, e.or_total, p.p_name, p.p_price, e.in_out"
				+ " from (select p_code, or_ea, or_total, in_out from order_return where to_char(or_date, 'yyyy-mm-dd') = ? and or_state = '주문') e, product p"
				+ " where e.p_code = p.p_code";
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
				ovo.setIn_out(rs.getString("in_out"));

				list.add(ovo);
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

	// 입고 확인 후 재고의 수량을 변경해주는 메소드
	public boolean setProductTable(int or_ea, String p_code) throws Exception {
		String sql = "update product set p_ea = p_ea + ? where p_code = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		boolean eaUpdate = false;

		try {
			con = DBUtil.getConnection(); // DBUtil 연결
			pstmt = con.prepareStatement(sql); // sql문을 prepareStatement로 실행한다
			pstmt.setInt(1, or_ea);
			pstmt.setString(2, p_code);

			int i = pstmt.executeUpdate();

			if (i == 1) {
				System.out.println("재고변경 완료");
				eaUpdate = true;
			} else {
				System.out.println("재고변경 실패");
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
			}
		}

		return eaUpdate;

	}

	// 출고버튼 이벤트 발생후 y로 변경
	public void setY(String today, String p_code) {
		String sql = "UPDATE ORDER_RETURN SET IN_OUT = 'Y' WHERE P_CODE = ? AND TO_CHAR(OR_DATE, 'YYYY-MM-DD') = ? AND OR_STATE = '주문'";
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = DBUtil.getConnection(); // DBUtil 연결
			pstmt = con.prepareStatement(sql); // sql문을 prepareStatement로 실행한다
			pstmt.setString(1, p_code);
			pstmt.setString(2, today);

			int i = pstmt.executeUpdate();

			if (i == 1) {
				System.out.println("Y변경 완료");
			} else {
				System.out.println("Y변경 실패");
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
			}
		}

	}

}