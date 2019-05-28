package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.OrderVO;
import model.ReturnVO;

public class ReturnDAO {
	// 출고확인후 in_out컬럼값 y로 변경
	public void updateIn_Out(String time, String p_code) {
		String sql = "update order_return set IN_OUT = 'Y' where p_code = ? and to_char(or_date, 'yyyy-mm-dd') =  ? and or_state = '반품'";
		Connection con = null;
		PreparedStatement pstmt = null;
		boolean employeeUpdateSucess = false;

		try {
			con = DBUtil.getConnection(); // DBUtil 연결
			pstmt = con.prepareStatement(sql); // sql문을 prepareStatement로 실행한다
			pstmt.setString(1, p_code);
			pstmt.setString(2, time);

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

	// 중복검사에서 걸러졌을경우 수량 변경
	public void updateOverp_code(String time, String p_cdoe, int or_ea) {
		String sql = "update order_return set or_ea = or_ea + ? where p_code = ? and to_char(or_date, 'yyyy-mm-dd') =  ? and or_state = '반품'";
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DBUtil.getConnection(); // DBUtil 연결
			pstmt = con.prepareStatement(sql); // sql문을 prepareStatement로 실행한다
			pstmt.setInt(1, or_ea);
			pstmt.setString(2, p_cdoe);
			pstmt.setString(3, time);

			int i = pstmt.executeUpdate();

			if (i == 1) {
				System.out.println(p_cdoe + "의 수량변경 성공");
			} else {
				System.out.println(p_cdoe + "의 수량변경 실패");

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

	// 해당날짜에 중복된 상품을 반품등록할경우
	public boolean overlapP_code(String time, String p_code) {

		ArrayList<ReturnVO> list = new ArrayList<>();

		boolean overlap = false;

		String sql = "select p_code from order_return where to_char(or_date, 'YYYY-MM-DD') = ? AND P_CODE = ? and or_state = '반품'";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ReturnVO rvo = null;

		try {

			con = DBUtil.getConnection(); // DBUtil 연결

			pstmt = con.prepareStatement(sql); // sql문을 prepareStatement로 실행한다
			pstmt.setString(1, time);
			pstmt.setString(2, p_code);
			rs = pstmt.executeQuery(); // 쿼리 실행
			if (rs.next()) {
				overlap = true;
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

	// 반품등록하는 insert문
	public boolean insertOrder_Return(String rp_code, int or_Ea, int or_total, String rp_bad) throws Exception {

		LoginController lc = new LoginController();
		String storeCode = lc.loginStoreCode;
		String sql = "insert into order_return values (order_return_seq.nextval, ?, ?, ?, ?, '반품', ?, sysdate, 'N')";
		Connection con = null;
		PreparedStatement pstmt = null;

		// 등록 성공 판단 변수
		boolean insertResult = false;
		try {
			// DB연동
			con = DBUtil.getConnection();
			// sql문을 담아줄 그릇
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, rp_code);
			pstmt.setString(2, storeCode);
			pstmt.setInt(3, or_Ea);
			pstmt.setInt(4, or_total);
			pstmt.setString(5, rp_bad);

			// insert문이 성공적으로 입력되면 1을 반환
			int i = pstmt.executeUpdate();

			if (i == 1) {
				insertResult = true;
			} else {
				insertResult = false;
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

	// 출고버튼 이벤트 발생후 y로 변경
	public void SetY(String today, String p_code) {
		String sql = "UPDATE ORDER_RETURN SET IN_OUT = 'Y' WHERE P_CODE = ? AND TO_CHAR(OR_DATE, 'YYYY-MM-DD') = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = DBUtil.getConnection(); // DBUtil 연결
			pstmt = con.prepareStatement(sql); // sql문을 prepareStatement로 실행한다
			pstmt.setString(1, p_code);
			pstmt.setString(2, today);

			int i = pstmt.executeUpdate();

			if (i == 1) {
				System.out.println("Y변경완료");
			} else {
				System.out.println("Y변경 실패..");
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

	// 출고확인버튼이 눌리면 in_out컬럼값 변경.
	public void setIn_Out(int p_ea, String p_code) {
		String sql = "update product set p_ea = p_ea - ?  where p_code = ?";
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DBUtil.getConnection(); // DBUtil 연결
			pstmt = con.prepareStatement(sql); // sql문을 prepareStatement로 실행한다

			 pstmt.setInt(1, p_ea);
			 pstmt.setString(2, p_code);

			int i = pstmt.executeUpdate();

			if (i == 1) {
				System.out.println("재고변경 완료");
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

	}

	// 등록된 날짜로 주문현황 가져오는 메소드
	public ArrayList<ReturnVO> getOrderDate(String or_date) throws Exception {

		ArrayList<ReturnVO> list = new ArrayList<>();

		String sql = "SELECT e.p_code, p.p_name, e.or_ea, p.p_price, e.or_total, e.or_date, e.in_out, e.or_bad, e.or_state "
				+ "FROM (SELECT P_CODE, OR_EA, OR_TOTAL, IN_OUT, or_date, or_bad, or_state "
				+ "FROM ORDER_RETURN WHERE TO_CHAR(OR_DATE, 'YYYY-MM-DD') = ? and or_state = '반품') e, product p "
				+ "where e.p_code = p.p_code";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ReturnVO rvo = null;

		try {

			con = DBUtil.getConnection(); // DBUtil 연결

			pstmt = con.prepareStatement(sql); // sql문을 prepareStatement로 실행한다
			pstmt.setString(1, or_date);
			rs = pstmt.executeQuery(); // 쿼리 실행

			while (rs.next()) {
				rvo = new ReturnVO();
				rvo.setRp_code(rs.getString("p_code"));
				rvo.setRp_name(rs.getString("p_name"));
				rvo.setRp_ea(rs.getInt("or_ea"));
				rvo.setRp_price(rs.getInt("p_price"));
				rvo.setRp_total(rs.getInt("or_total"));
				rvo.setRp_date(rs.getString("or_Date"));
				rvo.setBeReleased(rs.getString("in_out"));
				rvo.setRp_bad(rs.getString("or_bad"));
				rvo.setRp_state(rs.getString("or_state"));

				list.add(rvo);

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

	// 판매등록후 재고의 수량을 변경해주는 메소드
	public boolean updateProductTable(int sr_ea, String p_code) throws Exception {
		String sql = "update product set p_ea = p_ea - ? where p_code = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		boolean employeeUpdateSucess = false;

		try {
			con = DBUtil.getConnection(); // DBUtil 연결
			pstmt = con.prepareStatement(sql); // sql문을 prepareStatement로 실행한다
			pstmt.setInt(1, sr_ea);
			pstmt.setString(2, p_code);

			int i = pstmt.executeUpdate();

			if (i == 1) {
				System.out.println("재고변경 완료");
				employeeUpdateSucess = true;
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

		return employeeUpdateSucess;

	}

}
