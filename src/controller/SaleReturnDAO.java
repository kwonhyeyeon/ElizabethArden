package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.EmployeeVO;
import model.SaleVO;

public class SaleReturnDAO {

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
	
}
