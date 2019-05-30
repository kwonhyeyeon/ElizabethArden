package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.CustomerVO;
import model.ProductVO;

public class CustomerDAO {

	// 고객 등록 메소드
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
			// CustomerVO에서 변수들을 가져와서 sql문에 넣어준다.
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

	// 고객 조회 메소드
	public ArrayList<CustomerVO> getCustomerSearch(String c_name) throws Exception {

		// CustomerVO Arraylist배열 생성
		ArrayList<CustomerVO> list = new ArrayList<>();

		// 고객 조회를 위한 sql문
		String sql = "select c_code,  c_name, to_char(c_birth, 'YYYY/MM/DD') as c_birth, c_phonenumber, c_email, c_address from customer where c_name like ?";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		CustomerVO cvo = null;

		try {
			// DB 연결
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%" + c_name + "%");
			rs = pstmt.executeQuery();

			while (rs.next()) { // 받아온 값을 VO에 값을 설정해준 후
				cvo = new CustomerVO();
				cvo.setC_code(rs.getInt("c_code"));
				cvo.setC_name(rs.getString("c_name"));
				cvo.setC_birth(rs.getString("c_birth"));
				cvo.setC_phoneNumber(rs.getString("c_phonenumber"));
				cvo.setC_email(rs.getString("c_email"));
				cvo.setC_address(rs.getString("c_address"));

				// list에 해당 값들을 넣어준다
				list.add(cvo);
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

	// 고객코드 시퀀스를 불러와 반환하는 메소드
	public int getCustomerCode() throws Exception {

		// 고객코드 시퀀스를 불러오는 메소드
		String sql = "select customer_seq.nextval from dual";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int customerCode = 0; // 고객코드 초기화
		
		try {

			con = DBUtil.getConnection(); // DBUtil 연결

			pstmt = con.prepareStatement(sql);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				customerCode = rs.getInt("nextval");
			}

		} catch (SQLException e) {
			System.out.println("SQL 오류 " + e);
		} catch (Exception e) {
			System.out.println("잉?");
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
		return customerCode;
	}

	// 고객테이블의 컬럼명을 가져오는 메소드
	public ArrayList<String> getCustomerColumnName() throws Exception {

		// ArrayList배열 생성
		ArrayList<String> columnName = new ArrayList<String>();

		// 고객테이블의 모든 정보를 가져오는 sql문
		String sql = "select * from customer";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		// ResultSetMetaData 객체 변수 선언
		ResultSetMetaData rsmd = null;

		try {
			// DB연동
			con = DBUtil.getConnection();
			// sql문을 담을 그릇
			pstmt = con.prepareStatement(sql);
			// 쿼리문을 날리고 결과를 담는다
			rs = pstmt.executeQuery();
			// 컬럼명을 가져와서 담아준다
			rsmd = rs.getMetaData();
			// 컬럼의 갯수를 담아주는 변수
			int cols = rsmd.getColumnCount();
			// rsmd의 컬럼명을 컬럼갯수만큼 가져와서 ArrayList배열 columnName에 넣어준다.
			for (int i = 1; i <= cols; i++) {
				columnName.add(rsmd.getColumnName(i));
			}

		} catch (SQLException se) {
			System.out.println(se);
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				// DB연결 해제
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException se) {
			}
		}
		// 컬럼명배열 반환
		return columnName;
	}

	// 고객 전체 목록을 가져오는 메소드
	public ArrayList<CustomerVO> getCustomerTotalList() throws Exception {
		// ArrayList배열 생성
		ArrayList<CustomerVO> list = new ArrayList<>();
		// 고객의 전체 목록을 가져오는 sql문
		String sql = "select c_code, c_name, c_phonenumber, c_address, to_char(c_birth, 'YYYY/MM/DD') as c_birth, c_email, c_point, c_etc from customer";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				CustomerVO cvo = new CustomerVO();
				cvo.setC_code(rs.getInt("c_code"));
				cvo.setC_name(rs.getString("c_name"));
				cvo.setC_phoneNumber(rs.getString("c_phonenumber"));
				cvo.setC_address(rs.getString("c_address"));
				cvo.setC_birth(rs.getString("c_birth"));
				cvo.setC_email(rs.getString("c_email"));
				cvo.setC_point(rs.getInt("c_point"));
				cvo.setC_etc(rs.getString("c_etc"));

				list.add(cvo);
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

	// 고객정보 수정 메소드
	public boolean getCustomerUpdate(String c_phoneNumber, String c_address, String c_email, String c_etc, int c_code)
			throws Exception {

		String sql = "update customer set c_phoneNumber = ?, c_address = ?, c_email = ?, c_etc = ? where c_code = ?";

		Connection con = null;
		PreparedStatement pstmt = null;
		boolean customerUpdateSucess = false; // 정보 수정 결과

		try {
			con = DBUtil.getConnection(); // DBUtil 연결
			pstmt = con.prepareStatement(sql); // sql문을 prepareStatement로 실행한다
			pstmt.setString(1, c_phoneNumber);
			pstmt.setString(2, c_address);
			pstmt.setString(3, c_email);
			pstmt.setString(4, c_etc);
			pstmt.setInt(5, c_code);

			int i = pstmt.executeUpdate();

			if (i == 1) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("고객 정보 수정");
				alert.setHeaderText("고객 정보 수정 완료");
				alert.setContentText("성공적으로 고객 정보를 수정했습니다");
				alert.showAndWait();
				customerUpdateSucess = true;
			} else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("고객 정보 수정");
				alert.setHeaderText("고객 정보 수정 실패");
				alert.setContentText("다시 입력해주세요");
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

		return customerUpdateSucess;

	}

}
