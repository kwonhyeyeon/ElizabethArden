package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.EmployeeVO;
import model.StoreVO;

public class EmployeeDAO {

	// 직원 등록 메소드
	public boolean getEmployeeRegiste(EmployeeVO evo) throws Exception {

		// 직원 등록 쿼리문
		String sql = "insert into employee values(?, ?, ?, ?, ?, ?, ?, ?)";
		Connection con = null;
		PreparedStatement pstmt = null;

		// 등록 성공 판단 변수
		boolean registeResult = false;
		try {
			LoginController sc = new LoginController();
			// DB연동
			con = DBUtil.getConnection();
			// sql문을 담아줄 그릇
			pstmt = con.prepareStatement(sql);
			// jvo에서 변수들을 가져와서 sql문에 넣어준다.
			pstmt.setString(1, evo.getE_code());
			pstmt.setString(2, evo.getE_name());
			pstmt.setInt(3, evo.getE_phonenumber());
			pstmt.setString(4, evo.getE_address());
			pstmt.setString(5, evo.getE_birth());
			pstmt.setString(6, evo.getE_rank());
			pstmt.setString(7, evo.getE_hiredate());
			pstmt.setString(8, sc.loginStoreCode);
			// insert문이 성공적으로 입력되면 1을 반환
			int i = pstmt.executeUpdate();

			if (i == 1) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("직원 등록");
				alert.setHeaderText("직원 등록 성공");
				alert.setContentText(evo.getE_name() + "직원이 등록되었습니다");
				alert.showAndWait();
				// 등록성공 판단변수 true
				registeResult = true;
			} else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("직원 등록");
				alert.setHeaderText("직원 등록 실패");
				alert.setContentText("직원의 정보를 다시 입력해주세요");
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

		return registeResult;

	}

	// 직원 정보 수정 메소드
	public boolean getEmployeeUpdate(String e_name, int e_phonenumber, String e_address, String e_rank)
			throws Exception {

		String sql = "update employee set e_phonenumber = ?, e_address = ?, e_rank = ? where e_name = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		boolean employeeUpdateSucess = false;

		try {
			con = DBUtil.getConnection(); // DBUtil 연결
			pstmt = con.prepareStatement(sql); // sql문을 prepareStatement로 실행한다
			pstmt.setInt(1, e_phonenumber);
			pstmt.setString(2, e_address);
			pstmt.setString(3, e_rank);
			pstmt.setString(4, e_name);

			int i = pstmt.executeUpdate();

			if (i == 1) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("직원 정보 수정");
				alert.setHeaderText(e_name + " 직원 정보 수정 완료");
				alert.setContentText("성공적으로 직원 정보를 수정했습니다");
				alert.showAndWait();
				employeeUpdateSucess = true;
			} else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("직원 정보 수정");
				alert.setHeaderText(e_name + " 직원 정보 수정 실패");
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

		return employeeUpdateSucess;

	}

	// 직원명 가져오는 메소드(직원 정보 변경)
	public ArrayList<EmployeeVO> getEmployeeTotalList() throws Exception {

		ArrayList<EmployeeVO> list = new ArrayList<>();

		String sql = "select e_name from employee where s_code = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		EmployeeVO evo = null;

		try {
			LoginController sc = new LoginController(); // 매장 코드
			
			con = DBUtil.getConnection(); // DBUtil 연결
			
			pstmt = con.prepareStatement(sql); // sql문을 prepareStatement로 실행한다
			pstmt.setString(1, sc.loginStoreCode);
			rs = pstmt.executeQuery(); // 쿼리 실행

			while (rs.next()) {
				evo = new EmployeeVO();
				evo.setE_name(rs.getString("e_name"));
				list.add(evo);
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

	// 직원코드 가져오기
	public String getEmployeeSequence(EmployeeVO evo) throws Exception {
		String sql = "select employee_seq.nextval from dual";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String hiredateYear = ""; // 입사년도 뒤 2자리
		String ecode = null; // 직원코드
		try {
			// DB연동
			con = DBUtil.getConnection();
			// sql문을 담아줄 그릇
			pstmt = con.prepareStatement(sql);
			// jvo에서 변수들을 가져와서 sql문에 넣어준다.
			rs = pstmt.executeQuery();
			// sql을 날리고 불러온 값이 있으면 로그인결과변수 true
			
			// 입사년도 뒤 2자리 가져오기
			SimpleDateFormat df = new SimpleDateFormat("yy");
			hiredateYear = df.format(new Date());
			
			if(rs.next()) {
				String seq = rs.getString("nextval");
				ecode = hiredateYear + seq; // 직원코드 = 입사년도 뒤 2자리 + 시퀀스 3자리
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
		
		return ecode;
	}
	
	// 직원명 선택시 직원 정보 가져오기
	public String getEmployeePhone(String e_name) throws Exception {
		
		String sql = "select e_phonenumber from employee where e_name = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		EmployeeVO evo = null;
		
		try {
			// DB연동
			con = DBUtil.getConnection();
			// sql문을 담아줄 그릇
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, e_name);
			// jvo에서 변수들을 가져와서 sql문에 넣어준다.
			rs = pstmt.executeQuery();
			// sql을 날리고 불러온 값이 있으면 로그인결과변수 true
			while(rs.next()) {
				evo = new EmployeeVO();
				evo.setE_phonenumber(rs.getInt("e_phonenumber"));
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
		
		return e_name;
		
	}

}
