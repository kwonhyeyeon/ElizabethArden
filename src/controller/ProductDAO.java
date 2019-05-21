package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import model.ProductVO;

public class ProductDAO {

	// 상품 전체 목록을 가져오는 메소드
	public ArrayList<ProductVO> getProductTotalList() throws Exception {
		// ArrayList배열 생성
		ArrayList<ProductVO> list = new ArrayList<>();
		// lesson테이블에 있는 모든 정보를 일련번호로 정렬해서 가져오는 sql문
		String sql = "select p_code, p_name, p_ea, p_price, p_ea*p_price as p_total, p_price/100 as p_point from product";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ProductVO pvo = new ProductVO();
				pvo.setP_code(rs.getString("p_code"));
				pvo.setP_name(rs.getString("p_name"));
				pvo.setP_ea(rs.getInt("p_ea"));
				pvo.setP_price(rs.getInt("p_price"));
				pvo.setP_total(rs.getInt("p_total"));
				pvo.setP_point(rs.getInt("p_point"));

				list.add(pvo);
			}
		} catch (SQLException se) {
			System.out.println(se);
			System.out.println("sql문 에러?");
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

	// 데이터베이스에서 학생 테이블의 컬럼의 갯수
	public ArrayList<String> getProductColumnName() throws Exception {

		ArrayList<String> columnName = new ArrayList<String>();

		String sql = "select p_code, p_name, p_ea, p_price, p_ea*p_price as p_total, p_price/100 as p_point from product order by P_no";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		// ResultSetMetaData 객체 변수 선언
		ResultSetMetaData rsmd = null;

		try {
			con = DBUtil.getConnection(); // DBUtil 연결
			pstmt = con.prepareStatement(sql); // sql문을 prepareStatement로 실행한다
			rs = pstmt.executeQuery(); // 쿼리 실행
			rsmd = rs.getMetaData();

			int cols = rsmd.getColumnCount();

			for (int i = 1; i <= cols; i++) {
				columnName.add(rsmd.getCatalogName(i));
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
		return columnName;
	}
}