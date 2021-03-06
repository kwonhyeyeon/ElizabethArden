package model;

public class EmployeeVO {

	private String e_code; // 직원 코드
	private String e_name; // 직원명
	private String e_phonenumber; // 핸드폰번호
	private String e_address; // 주소
	private String e_birth; // 생년월일
	private String e_rank; // 직원등급
	private String e_hiredate; // 입사일
	private String s_code; // 매장 코드

	public EmployeeVO() {
		super();
	}

	public EmployeeVO(String e_name) {
		super();
		this.e_name = e_name;
	}

	// 매장코드 제외 생성자
	public EmployeeVO(String e_code, String e_name, String e_phonenumber, String e_address, String e_birth, String e_rank,
			String e_hiredate) {
		super();
		this.e_code = e_code;
		this.e_name = e_name;
		this.e_phonenumber = e_phonenumber;
		this.e_address = e_address;
		this.e_birth = e_birth;
		this.e_rank = e_rank;
		this.e_hiredate = e_hiredate;
	}

	// 전체 생성자
	public EmployeeVO(String e_code, String e_name, String e_phonenumber, String e_address, String e_birth, String e_rank,
			String e_hiredate, String s_code) {
		super();
		this.e_code = e_code;
		this.e_name = e_name;
		this.e_phonenumber = e_phonenumber;
		this.e_address = e_address;
		this.e_birth = e_birth;
		this.e_rank = e_rank;
		this.e_hiredate = e_hiredate;
		this.s_code = s_code;
	}

	// 이름, 핸드폰번호, 주소, 직원등급 생성자
	public EmployeeVO(String e_name, String e_phonenumber, String e_address, String e_rank) {
		super();
		this.e_name = e_name;
		this.e_phonenumber = e_phonenumber;
		this.e_address = e_address;
		this.e_rank = e_rank;
	}

	public EmployeeVO(String e_phonenumber, String e_address, String e_rank) {
		super();
		this.e_phonenumber = e_phonenumber;
		this.e_address = e_address;
		this.e_rank = e_rank;
	}

	public String getE_code() {
		return e_code;
	}

	public void setE_code(String e_code) {
		this.e_code = e_code;
	}

	public String getE_name() {
		return e_name;
	}

	public void setE_name(String e_name) {
		this.e_name = e_name;
	}

	public String getE_phonenumber() {
		return e_phonenumber;
	}

	public void setE_phonenumber(String e_phonenumber) {
		this.e_phonenumber = e_phonenumber;
	}

	public String getE_address() {
		return e_address;
	}

	public void setE_address(String e_address) {
		this.e_address = e_address;
	}

	public String getE_birth() {
		return e_birth;
	}

	public void setE_birth(String e_birth) {
		this.e_birth = e_birth;
	}

	public String getE_rank() {
		return e_rank;
	}

	public void setE_rank(String e_rank) {
		this.e_rank = e_rank;
	}

	public String getE_hiredate() {
		return e_hiredate;
	}

	public void setE_hiredate(String e_hiredate) {
		this.e_hiredate = e_hiredate;
	}

	public String getS_code() {
		return s_code;
	}

	public void setS_code(String s_code) {
		this.s_code = s_code;
	}

	@Override
	public String toString() {
		return e_name;
	}

}
