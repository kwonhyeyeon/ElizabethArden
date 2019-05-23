package model;

public class CustomerVO {

	
	private int c_code; // 고객 코드
	private String c_name; // 고객 이름
	private String c_phoneNumber; // 고객 번호
	private String c_address; // 고객 주소
	private String c_birth; // 고객 생년월일
	private String c_email; // 고객 이메일
	private int c_point; // 고객 포인트
	private String c_etc; // 비고
	
	// 디폴트 생성자
	public CustomerVO() {
		super();
	}
	// 포인트를 제외한 생성자
	public CustomerVO(int c_code, String c_name, String c_phoneNumber, String c_address, String c_birth, String c_email,
			String c_etc) {
		super();
		this.c_code = c_code;
		this.c_name = c_name;
		this.c_phoneNumber = c_phoneNumber;
		this.c_address = c_address;
		this.c_birth = c_birth;
		this.c_email = c_email;
		this.c_etc = c_etc;
	}
	// 전체필드 생성자
	public CustomerVO(int c_code, String c_name, String c_phoneNumber, String c_address, String c_birth, String c_email,
			int c_point, String c_etc) {
		super();
		this.c_code = c_code;
		this.c_name = c_name;
		this.c_phoneNumber = c_phoneNumber;
		this.c_address = c_address;
		this.c_birth = c_birth;
		this.c_email = c_email;
		this.c_point = c_point;
		this.c_etc = c_etc;
	}
	
	// 고객코드, 고객명, 핸드폰번호, 생년월일, 이메일
	public CustomerVO(int c_code, String c_name, String c_phoneNumber, String c_birth, String c_email, String c_address) {
		super();
		this.c_code = c_code;
		this.c_name = c_name;
		this.c_phoneNumber = c_phoneNumber;
		this.c_birth = c_birth;
		this.c_email = c_email;
		this.c_address = c_address;
	}
	// 고객코드, 고객명, 고객 핸드폰번호, 고객 생년월일 생성자
	public CustomerVO(int c_code, String c_name, String c_phoneNumber, String c_birth) {
		super();
		this.c_code = c_code;
		this.c_name = c_name;
		this.c_phoneNumber = c_phoneNumber;
		this.c_birth = c_birth;
	}
	
	// 고객 수정 생성자
	public CustomerVO(String c_phoneNumber, String c_address, String c_email, String c_etc, int c_code) {
		super();
		this.c_phoneNumber = c_phoneNumber;
		this.c_address = c_address;
		this.c_email = c_email;
		this.c_etc = c_etc;
		this.c_code = c_code;
	}
	
	// get,set
	public int getC_code() {
		return c_code;
	}
	public void setC_code(int c_code) {
		this.c_code = c_code;
	}
	public String getC_name() {
		return c_name;
	}
	public void setC_name(String c_name) {
		this.c_name = c_name;
	}
	public String getC_phoneNumber() {
		return c_phoneNumber;
	}
	public void setC_phoneNumber(String c_phoneNumber) {
		this.c_phoneNumber = c_phoneNumber;
	}
	public String getC_address() {
		return c_address;
	}
	public void setC_address(String c_address) {
		this.c_address = c_address;
	}
	public String getC_birth() {
		return c_birth;
	}
	public void setC_birth(String c_birth) {
		this.c_birth = c_birth;
	}
	public String getC_email() {
		return c_email;
	}
	public void setC_email(String c_email) {
		this.c_email = c_email;
	}
	public int getC_point() {
		return c_point;
	}
	public void setC_point(int c_point) {
		this.c_point = c_point;
	}
	public String getC_etc() {
		return c_etc;
	}
	public void setC_etc(String c_etc) {
		this.c_etc = c_etc;
	}
	
	
	
	
}
