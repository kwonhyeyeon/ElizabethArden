package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.CustomerVO;

public class CustomerDAO {
	// 직원 등록 메소드
	public boolean customerRegiste(CustomerVO ct) throws Exception {

		// 고객 등록을 위한 sql문
		String sql = "insert into CUSTOMER values(?, ?, ?, ?, ?, ?, 0, ?)";
		Connection con = null;
		PreparedStatement pstmt = null;
		// 등록 성공 판단 변수
		boolean registeResult = false;
		try {
			
			// DB연동
			con = DBUtil.getConnection();
			// sql문을 담아줄 그릇
			pstmt = con.prepareStatement(sql);
			// jvo에서 변수들을 가져와서 sql문에 넣어준다.
			pstmt.setInt(1, ct.getC_code());
			pstmt.setString(2, ct.getC_name());
			pstmt.setString(3, ct.getC_phoneNumber());
			pstmt.setString(4, ct.getC_address());
			pstmt.setString(5, ct.getC_birth());
			pstmt.setString(6, ct.getC_email());
			pstmt.setString(7, ct.getC_etc());
			// 등록 성공시 1을 반환
			int i = pstmt.executeUpdate();

			if (i == 1) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("고객 등록");
				alert.setHeaderText("고객 등록 성공");
				alert.setContentText(ct.getC_name() + "고객이 등록 되었습니다");
				alert.showAndWait();
				// 등록성공 판단변수 true
				registeResult = true;
			} else {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("고객 등록");
				alert.setHeaderText("고객 등록 실패..");
				alert.setContentText("");
				alert.showAndWait();
			}

		} catch (SQLException e) {
			System.out.println(e);
		} catch (Exception e) {
			System.out.println(e);
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
		return registeResult;
	}
	
	
	// 고객코드시퀀스를 불러와 반환하는 메소드
	public int getCustomerCode() throws Exception{
		
		// 고객코드 시퀀스를 불러오는 메소드
		String sql = "select customer_seq.nextval from dual";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int customerCode = 0;
		try {

			con = DBUtil.getConnection(); // DBUtil 연결

			pstmt = con.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				customerCode = rs.getInt("nextval");
			}
		
		}catch(SQLException e) {
			System.out.println("오라클 에러" + e);
		}catch(Exception e) {
			System.out.println("잉?");
		}finally {
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
	return customerCode;
	
	}
	
}
