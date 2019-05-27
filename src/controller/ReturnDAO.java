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

	// 반품등록하는 insert문
	public boolean insertOrder_Return(String rp_code, int or_Ea, int or_total, String rp_bad)throws Exception{
		
		LoginController lc = new LoginController();
		String storeCode = lc.loginStoreCode;
		String sql = "insert into order_return values (order_return_seq.nextval, ?, ?, ?, ?, '반품', ?, sysdate)";
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
	
	
	// 등록된 날짜로 주문현황 가져오는 메소드
		public ArrayList<ReturnVO> getOrderDate(String or_date) throws Exception {

			ArrayList<ReturnVO> list = new ArrayList<>();

			String sql = "select e.p_code, e.or_ea, e.or_total, p.p_name, p.p_price" + 
					" from(select distinct(p_code) p_code, sum(or_ea) or_ea, sum(or_total) or_total" + 
					" from order_return" + 
					" where to_char(or_date, 'yyyy-mm-dd') = ? AND OR_STATE = '반품'" + 
					" group by p_code) e, product p" + 
					" where e.p_code = p.p_code";
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
					
					list.add(rvo);
					
				}

				if (rvo == null) {
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
		
		// 판매등록후 재고의 수량을 변경해주는 메소드
		public boolean updateProductTable(int sr_ea, String p_code) throws Exception{
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
	
