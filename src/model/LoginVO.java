package model;

public class LoginVO {
	private String s_code; // 매장 코드

	// 디폴트 생성자
	public LoginVO() {
		super();
	}

	public LoginVO(String s_code) {
		super();
		this.s_code = s_code;
	}

	public String getS_code() {
		return s_code;
	}

	public void setS_code(String s_code) {
		this.s_code = s_code;
	}

	@Override
	public String toString() {
		return "LoginVO [s_code=" + s_code + "]";
	}

}
