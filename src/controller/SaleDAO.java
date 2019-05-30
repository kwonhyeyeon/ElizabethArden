package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import model.ProductVO;
import model.SaleVO;

public class SaleDAO {

	// 판매일때 총수량과 총액을 뽑아내는 메소드
	public ArrayList<SaleVO> getReturnDate(String p_code) {
		// ArrayList배열 생성
		ArrayList<SaleVO> list = new ArrayList<>();
		// lesson테이블에 있는 모든 정보를 일련번호로 정렬해서 가져오는 sql문
		String sql = "select sum(sr.sr_Ea) sr_ea, sum(sr.sr_total) sr_total "
				+ "from (select * from sale_return where sr_state = '반품') sr "
				+ "group by sr.p_code having sr.p_code = ?";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, p_code);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				SaleVO svo = new SaleVO();
				svo.setSr_ea(rs.getInt("sr_Ea"));
				svo.setSr_total(rs.getInt("sr_total"));

				list.add(svo);
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

	// 판매일때 총수량과 총액을 뽑아내는 메소드
	public ArrayList<SaleVO> getUsed_pointDate(String p_code) {
		// ArrayList배열 생성
		ArrayList<SaleVO> list = new ArrayList<>();
		// lesson테이블에 있는 모든 정보를 일련번호로 정렬해서 가져오는 sql문
		String sql = "select sum(sr.sr_Ea) sr_ea, sum(sr.sr_total) sr_total "
				+ "from (select * from sale_return where sr_state = '포인트 사용') sr "
				+ "group by sr.p_code having sr.p_code = ?";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, p_code);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				SaleVO svo = new SaleVO();
				svo.setSr_ea(rs.getInt("sr_Ea"));
				svo.setSr_total(rs.getInt("sr_total"));

				list.add(svo);
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

	// 판매일때 총수량과 총액을 뽑아내는 메소드
	public ArrayList<SaleVO> getSaleDate(String p_code) {
		// ArrayList배열 생성
		ArrayList<SaleVO> list = new ArrayList<>();
		// lesson테이블에 있는 모든 정보를 일련번호로 정렬해서 가져오는 sql문
		String sql = "select sum(sr.sr_Ea) sr_ea, sum(sr.sr_total) sr_total "
				+ "from (select * from sale_return where sr_state = '판매') sr "
				+ "group by sr.p_code having sr.p_code = ?";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, p_code);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				SaleVO svo = new SaleVO();
				svo.setSr_ea(rs.getInt("sr_Ea"));
				svo.setSr_total(rs.getInt("sr_total"));

				list.add(svo);
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

	// 제품별 판매현황을 가져오는 메소드
	public ArrayList<SaleVO> getProductDate(String p_name) {
		// ArrayList배열 생성
		ArrayList<SaleVO> list = new ArrayList<>();
		// lesson테이블에 있는 모든 정보를 일련번호로 정렬해서 가져오는 sql문
		String sql = "select sr.p_code, p.p_name, sr.sr_state, sr.build_date, sr.sr_Ea, p.p_price, sr.sr_total "
				+ "from sale_return sr, product p where sr.p_code = p.p_code and p.p_name like '%'||?||'%'";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, p_name);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				SaleVO svo = new SaleVO();
				svo.setP_code(rs.getString("p_code"));
				svo.setP_name(rs.getString("p_name"));
				svo.setSr_state(rs.getString("sr_state"));
				svo.setBuild_date(rs.getString("build_date"));
				svo.setSr_ea(rs.getInt("sr_Ea"));
				svo.setP_price(rs.getInt("p_price"));
				svo.setSr_total(rs.getInt("sr_total"));

				list.add(svo);
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

	// 월별 판매현황을 가져오는 메소드
	public ArrayList<SaleVO> getEmployeeMonthDate(String month, String e_code) {

		// ArrayList배열 생성
		ArrayList<SaleVO> list = new ArrayList<>();
		// lesson테이블에 있는 모든 정보를 일련번호로 정렬해서 가져오는 sql문
		String sql = "select sr.p_code, p.p_name, sr.sr_state, sr.build_date, sr.sr_Ea, p.p_price, sr.sr_total "
				+ "from sale_return sr, product p where sr.p_code = p.p_code and to_char(sr.build_Date, 'yyyy-mm') = ? and sr.e_code = ?";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		SimpleDateFormat format12 = new SimpleDateFormat("yyyy");
		Date time23 = new Date();
		String year = format12.format(time23);

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, year + "-" + month);
			pstmt.setString(2, e_code);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				SaleVO svo = new SaleVO();
				svo.setP_code(rs.getString("p_code"));
				svo.setP_name(rs.getString("p_name"));
				svo.setSr_state(rs.getString("sr_state"));
				svo.setBuild_date(rs.getString("build_date"));
				svo.setSr_ea(rs.getInt("sr_Ea"));
				svo.setP_price(rs.getInt("p_price"));
				svo.setSr_total(rs.getInt("sr_total"));

				list.add(svo);
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

	// 직원코드로 검색하여 직원별 판매현황을 가져오는 메소드
	public ArrayList<SaleVO> getEmployeeSaleState(String e_code) {

		// ArrayList배열 생성
		ArrayList<SaleVO> list = new ArrayList<>();
		// lesson테이블에 있는 모든 정보를 일련번호로 정렬해서 가져오는 sql문
		String sql = "select sr.p_code, p.p_name, sr.sr_state, sr.build_date, sr.sr_Ea, p.p_price, sr.sr_total "
				+ "from sale_return sr, product p where sr.p_code = p.p_code and sr.e_code = ?";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, e_code);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				SaleVO svo = new SaleVO();
				svo.setP_code(rs.getString("p_code"));
				svo.setP_name(rs.getString("p_name"));
				svo.setSr_state(rs.getString("sr_state"));
				svo.setBuild_date(rs.getString("build_date"));
				svo.setSr_ea(rs.getInt("sr_Ea"));
				svo.setP_price(rs.getInt("p_price"));
				svo.setSr_total(rs.getInt("sr_total"));

				list.add(svo);
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

	// 데이터베이스에서 판매 입력 테이블의 컬럼의 갯수
	public ArrayList<String> getProductColumnName() throws Exception {

		ArrayList<String> columnName = new ArrayList<String>();

		String sql = "select p.p_code, p.p_name, sr.sr_state, p.p_ea, p.p_price, p.p_ea * p.p_price as sr_total, p.p_price/100 as p_point, sr.sr_used_point, sr.sr_return_reason "
				+ "from product p, sale_return sr " + "where p.p_code = sr.p_code";
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

	// 고객 조회시 구매 내역
	public ArrayList<SaleVO> getBuyList(int c_code) throws Exception {

		ArrayList<SaleVO> buyList = new ArrayList<SaleVO>();

		String sql = "select sr.p_code, p.p_name, sr.sr_state, sr.sr_ea, p.p_price, sr.sr_total, p.p_price/100 as p_point, sr.sr_used_point, to_char(sr.build_date, 'YYYY/MM/DD') as build_date "
				+ "from sale_return sr, product p " + "where sr.p_code = p.p_code and sr.c_code = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		SaleVO svo = null;

		try {

			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, c_code);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				svo = new SaleVO();
				svo.setP_code(rs.getString("p_code"));
				svo.setP_name(rs.getString("p_name"));
				svo.setSr_state(rs.getString("sr_state"));
				svo.setSr_ea(rs.getInt("sr_ea"));
				svo.setP_price(rs.getInt("p_price"));
				svo.setSr_total(rs.getInt("sr_total"));
				svo.setP_point(rs.getInt("p_point"));
				svo.setSr_used_point(rs.getInt("sr_used_point"));
				svo.setBuild_date(rs.getString("build_date"));

				buyList.add(svo);
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

		return buyList;

	}
}