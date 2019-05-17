package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.StoreJoinVO;

public class StoreJoinDAO {

	// 매장코드를 가져오는 메소드
	public String getStoreSequence(StoreJoinVO svo) throws Exception {
		String sql = "select STORE_SEQ.nextval from dual";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String scode = null;
		try {
			// DB연동
			con = DBUtil.getConnection();
			// sql문을 담아줄 그릇
			pstmt = con.prepareStatement(sql);
			// jvo에서 변수들을 가져와서 sql문에 넣어준다.
			rs = pstmt.executeQuery();
			// sql을 날리고 불러온 값이 있으면 로그인결과변수 true
			if (rs.next()) {
				String seq = rs.getString("nextval");
				scode = "AD" + seq;
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
		return scode;
	}

	public boolean getStoreRegiste(StoreJoinVO svo) throws Exception {
		// 매장등록하는 sql문
		String sql = "insert into store values(?, ?, ?, ?, ?)";
		Connection con = null;
		PreparedStatement pstmt = null;

		// 등록성공 판단 변수
		boolean joinSucess = false;
		try {
			// DB연동
			con = DBUtil.getConnection();
			// sql문을 담아줄 그릇
			pstmt = con.prepareStatement(sql);
			// jvo에서 변수들을 가져와서 sql문에 넣어준다.
			pstmt.setString(1, svo.getS_code());
			pstmt.setString(2, svo.getS_address());
			pstmt.setString(3, svo.getS_name());
			pstmt.setInt(4, svo.getS_pw());
			pstmt.setInt(5, svo.getS_phonenumber());
			// insert문이 성공적으로 입력되면 1을 반환
			int i = pstmt.executeUpdate();

			if (i == 1) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("매장등록");
				alert.setHeaderText("매장이 등록되었습니다.");
				alert.setContentText("");
				alert.showAndWait();
				// 등록성공 판단변수 true
				joinSucess = true;
			} else {

				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("매장 등록");
				alert.setHeaderText("매장등록 실패..");
				alert.setContentText("다시 입력하십시오");
				alert.showAndWait();
			}

		} catch (SQLException e) {
		} catch (Exception e) {
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
			}
		}
		return joinSucess;
	}
}
