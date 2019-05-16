package model;

public class StoreJoinVO {
	private String s_code; // 매장코드
	private String s_address; // 매장주소
	private String s_name; // 매장명
	private int s_pw; // 매장비밀번호
	private int s_phonenumber; // 매장번호

	// 디폴트 생성자
	public StoreJoinVO() {
		super();
	}

	// 매장코드와 비밀번호만 받는 생성자
	public StoreJoinVO(String s_code, int s_pw) {
		super();
		this.s_code = s_code;
		this.s_pw = s_pw;
	}

	// 전체 필드 생성자
	public StoreJoinVO(String s_code, String s_address, String s_name, int s_pw, int s_phonenumber) {
		super();
		this.s_code = s_code;
		this.s_address = s_address;
		this.s_name = s_name;
		this.s_pw = s_pw;
		this.s_phonenumber = s_phonenumber;
	}

	// get, set
	public String getS_code() {
		return s_code;
	}

	public void setS_code(String s_code) {
		this.s_code = s_code;
	}

	public String getS_address() {
		return s_address;
	}

	public void setS_address(String s_address) {
		this.s_address = s_address;
	}

	public String getS_name() {
		return s_name;
	}

	public void setS_name(String s_name) {
		this.s_name = s_name;
	}

	public int getS_pw() {
		return s_pw;
	}

	public void setS_pw(int s_pw) {
		this.s_pw = s_pw;
	}

	public int getS_phonenumber() {
		return s_phonenumber;
	}

	public void setS_phonenumber(int s_phonenumber) {
		this.s_phonenumber = s_phonenumber;
	}

}
