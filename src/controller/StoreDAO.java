package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.StoreVO;

public class StoreDAO {

	// 매장코드를 가져오는 메소드
	public String getStoreSequence(StoreVO svo) throws Exception {
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

	// 매장등록하는 메소드
	public boolean getStoreRegiste(StoreVO svo) throws Exception {
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

	// 매장코드로 등록된 정보를 가져오는 메소드
	public ArrayList<StoreVO> getStoreCode() throws Exception {

		ArrayList<StoreVO> list = new ArrayList<>();

		String sql = "select * from store where s_code = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		// 로그인 컨트롤러에서 로그인된 코드를 저장하고 호출하여 사용.
		LoginController sc = new LoginController();
		// sc에서 static변수에 저장된 로그인 코드를 가져옴
		String scode = sc.loginStoreCode;

		StoreVO svo = null;

		try {
			// DB연동
			con = DBUtil.getConnection();
			// sql문을 담아줄 그릇
			pstmt = con.prepareStatement(sql);
			// 매장코드를 가져와 sql문에 넣어준다
			pstmt.setString(1, scode);
			// sql을 날리고 결과를 담음
			rs = pstmt.executeQuery();

			while (rs.next()) { // 쿼리가 실행 후
				svo = new StoreVO(); // StoreVO객체 생성
				// 해당 테이블 컬럼에서 값을 받아 svo객체에 설정
				svo.setS_address(rs.getString("s_address"));
				svo.setS_name(rs.getString("s_name"));
				svo.setS_pw(rs.getInt("s_pw"));
				svo.setS_phonenumber(rs.getInt("s_phonenumber"));

				list.add(svo);
			}
		} catch (SQLException e) {
			System.out.println("sql에러 : " + e);
		} catch (Exception e) {
			System.out.println("에러 : " + e);
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
		// list배열 반환
		return list;
	}

	// 매장 정보수정후 저장하는 메소드
	public boolean InfoEdit(StoreVO svo) {

		String sql = "update store set s_address = ?, s_name = ?, s_pw = ?, s_phonenumber = ? where s_code = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		boolean storeUpdateSucess = false;
		// 로그인 컨트롤러에서 로그인된 코드를 저장하고 호출하여 사용.
		LoginController sc = new LoginController();
		// sc에서 static변수에 저장된 로그인 코드를 가져옴
		String scode = sc.loginStoreCode;
		try {
			con = DBUtil.getConnection(); // DBUtil 연결
			pstmt = con.prepareStatement(sql); // sql문을 prepareStatement로 실행한다
			// sql문에 값을 넣어줌
			pstmt.setString(1, svo.getS_address());
			pstmt.setString(2, svo.getS_name());
			pstmt.setInt(3, svo.getS_pw());
			pstmt.setInt(4, svo.getS_phonenumber());
			pstmt.setString(5, scode);
			// 업데이트 성공시 1을 반환
			int i = pstmt.executeUpdate();

			if (i == 1) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("매장 정보 수정");
				alert.setHeaderText("매장 정보 수정 완료");
				alert.setContentText("");
				alert.showAndWait();
				storeUpdateSucess = true;
			} else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("매장 정보 수정");
				alert.setHeaderText("매장 정보 수정 실패..");
				alert.setContentText("다시 입력하십시오..");
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
			}
		}
		return storeUpdateSucess;

	}
}
