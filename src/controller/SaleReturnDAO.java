package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.SaleVO;

public class SaleReturnDAO {
	// 상태가 판매일시 테이블에 등록하는 메소드
	public boolean insertSale_return(int c_code, String p_code, String e_code, int sr_total, String sr_state, int sr_ea, String returnReason, String bulid_date) throws Exception{
		
		String sql = "insert into sale_return values(sale_return_seq.nextval, ?, ?, ?, ?, ?, ?, 0, ?, ?)";
		Connection con = null;
		PreparedStatement pstmt = null;
		
		// 등록 성공 판단 변수
		boolean insertResult = false;
		try {

			// DB연동
			con = DBUtil.getConnection();
			// sql문을 담아줄 그릇
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, c_code);
			pstmt.setString(2, p_code);
			pstmt.setString(3, e_code);
			pstmt.setInt(4, sr_total);
			pstmt.setString(5, sr_state);
			pstmt.setInt(6, sr_ea);
			pstmt.setString(7, returnReason);
			pstmt.setString(8, bulid_date);
			// insert문이 성공적으로 입력되면 1을 반환
			int i = pstmt.executeUpdate();

			if (i == 1) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("판매 등록 성공");
				alert.setHeaderText("판매 입력 등록 성공");
				alert.setContentText("수고링");
				alert.showAndWait();
				// 등록성공 판단변수 true
				insertResult = true;
			} else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("판매 입력 등록");
				alert.setHeaderText("판매정보를 똑바로 입력하십시오");
				alert.setContentText("");
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

		return insertResult;

	}
	
	// 상태가 판매일시 테이블에 등록하는 메소드
		public boolean insertSale_return_used_point(int c_code, String p_code, String e_code, int sr_total, String sr_state, int sr_ea, String returnReason, int uspoint, String bulid_date) throws Exception{
			
			String sql = "insert into sale_return values(sale_return_seq.nextval, ?, ?, ?, ?, ?, ?, 0, ?, ?)";
			Connection con = null;
			PreparedStatement pstmt = null;
			
			// 등록 성공 판단 변수
			boolean insertResult = false;
			try {

				// DB연동
				con = DBUtil.getConnection();
				// sql문을 담아줄 그릇
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, c_code);
				pstmt.setString(2, p_code);
				pstmt.setString(3, e_code);
				pstmt.setInt(4, sr_total);
				pstmt.setString(5, sr_state);
				pstmt.setInt(6, sr_ea);
				pstmt.setString(7, returnReason);
				pstmt.setInt(8, uspoint);
				pstmt.setString(8, bulid_date);
				// insert문이 성공적으로 입력되면 1을 반환
				int i = pstmt.executeUpdate();

				if (i == 1) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("판매 등록 성공");
					alert.setHeaderText("판매 입력 등록 성공");
					alert.setContentText("수고링");
					alert.showAndWait();
					// 등록성공 판단변수 true
					insertResult = true;
				} else {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("판매 입력 등록");
					alert.setHeaderText("판매정보를 똑바로 입력하십시오");
					alert.setContentText("");
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

			return insertResult;

		}
	
	// 판매등록후 재고의 수량을 변경해주는 메소드
	public boolean setProductTable(int sr_ea, String p_code) throws Exception{
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
				System.out.println("재고변경 실패..");
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
	
	// 판매등록후 고객 포인트 변경
	public boolean setCustomerPoint(int c_point, int c_code) throws Exception{
		String sql = "update customer set c_p"
				+ "oint = c_point + ? where c_code = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		boolean setCustomerPoint = false;

		try {
			con = DBUtil.getConnection(); // DBUtil 연결
			pstmt = con.prepareStatement(sql); // sql문을 prepareStatement로 실행한다
			pstmt.setInt(1, c_point);
			pstmt.setInt(2, c_code);

			int i = pstmt.executeUpdate();

			if (i == 1) {
				System.out.println("고객 포인트 변경완료");
				setCustomerPoint = true;
			} else {
				System.out.println("고객 포인트 변경 실패");
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

		return setCustomerPoint;

	}
	
	
	// 판매 입력 메소드
	public boolean getSaleInsert(SaleVO svo) throws Exception {

		// 판매 입력 등록 쿼리문
		String sql = "insert into sale_return values(?, ?, ?, ?, ?, ?, ?, ?)";
		Connection con = null;
		PreparedStatement pstmt = null;

		// 등록 성공 판단 변수
		boolean insertResult = false;

		try {

			// DB연동
			con = DBUtil.getConnection();
			// sql문을 담아줄 그릇
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, svo.getNo());
			pstmt.setInt(2, svo.getC_code());
			pstmt.setString(3, svo.getP_code());
			pstmt.setString(4, svo.getE_code());
			pstmt.setString(5, svo.getSr_state());
			pstmt.setInt(6, svo.getSr_ea());
			pstmt.setInt(7, svo.getSr_used_point());
			pstmt.setString(8, svo.getSr_return_reason());
			// insert문이 성공적으로 입력되면 1을 반환
			int i = pstmt.executeUpdate();

			if (i == 1) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("판매 입력 등록");
				alert.setHeaderText("판매 입력 등록 성공");
				alert.setContentText(svo.getC_name() + "고객의 구매 내역이 등록되었습니다");
				alert.showAndWait();
				// 등록성공 판단변수 true
				insertResult = true;
			} else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("판매 입력 등록");
				alert.setHeaderText("판매 입력 등록 실패");
				alert.setContentText("판매 내역을 다시 입력해주세요");
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

		return insertResult;

	}
	// 입력된 날짜로 데이터를 가져오는 메소드
	public ArrayList<SaleVO> getSaleReturndpdate(String bulid_date) throws Exception {
		
		
		ArrayList<SaleVO> list = new ArrayList<>();
		
		String sql = "select * from product P, sale_return sr where p.p_code = sr.p_code and sr.build_date = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		SaleVO svo = null;
		
		try {

			con = DBUtil.getConnection(); // DBUtil 연결

			pstmt = con.prepareStatement(sql); // sql문을 prepareStatement로 실행한다
			pstmt.setString(1, bulid_date);
			rs = pstmt.executeQuery(); // 쿼리 실행

			while (rs.next()) {
				svo = new SaleVO();
				svo.setNo(rs.getInt("no"));
				svo.setP_name(rs.getString("p_name"));
				svo.setP_code(rs.getString("p_code"));
				svo.setP_price(rs.getInt("p_price"));
				svo.setSr_total(rs.getInt("sr_total"));
				svo.setSr_state(rs.getString("sr_state"));
				svo.setSr_ea(rs.getInt("sr_ea"));
				svo.setSr_used_point(rs.getInt("sr_used_point"));
				//svo.setP_point(rs.getInt("p_point"));
				svo.setBuild_date(rs.getString("build_date"));
				
				svo.setP_point(svo.getP_price() / 100);
				list.add(svo);
			}
			
			if(svo == null) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("날짜 검색");
				alert.setHeaderText(bulid_date + " 에 등록된 데이터가 없습니다.");
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
		
		
	

	// 판매일련번호를 가져오는 메소드
	public int getSale_returnNO() {

		String sql = "select sale_return_SEQ.nextval from dual";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		int saleNo = 0;

		try {
			// DB연동
			con = DBUtil.getConnection();
			// sql문을 담아줄 그릇
			pstmt = con.prepareStatement(sql);
			// jvo에서 변수들을 가져와서 sql문에 넣어준다.
			rs = pstmt.executeQuery();
			// sql을 날리고 불러온 값이 있으면 로그인결과변수 true

			if (rs.next()) {
				saleNo = rs.getInt("nextval");
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

		return saleNo;
	}
	
	
}
