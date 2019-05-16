package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.StoreJoinVO;

public class StoreJoinDAO {

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
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("매장 등록");
			alert.setHeaderText("매장등록 실패..");
			alert.setContentText("다시 입력하십시오");
			alert.showAndWait();
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("매장 등록");
			alert.setHeaderText("매장등록 실패..");
			alert.setContentText("다시 입력하십시오");
			alert.showAndWait();
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
