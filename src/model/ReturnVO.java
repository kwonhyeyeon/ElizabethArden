package model;

public class ReturnVO {

	private String rp_code; // 반품 상품코드
	private String rp_name; // 반품 상품명
	private int rp_ea; // 반품 상품수량
	private int rp_price; // 반품 상품 단가
	private int rp_total; // 반품 상품 총액
	private String rp_date; // 반품등록일
	private String rp_bad; // 불량여부
	private String rp_state; // 등록유형
	private boolean beReleased; // 출고확인

	// 디폴트 생성자
	public ReturnVO() {
		super();
	}

	
	
	
	public ReturnVO(String rp_code, String rp_name, int rp_ea, int rp_price, int rp_total,
			String rp_date, boolean beReleased) {
		super();
		this.rp_code = rp_code;
		this.rp_name = rp_name;
		this.rp_ea = rp_ea;
		this.rp_price = rp_price;
		this.rp_total = rp_total;
		this.rp_date = rp_date;
		this.beReleased = beReleased;
	}



	// 전체필드 생성자
	public ReturnVO(String rp_code, String rp_name, int rp_ea, int rp_price, int rp_total, String rp_date,
			String rp_bad, String rp_state, boolean beReleased) {
		super();
		this.rp_code = rp_code;
		this.rp_name = rp_name;
		this.rp_ea = rp_ea;
		this.rp_price = rp_price;
		this.rp_total = rp_total;
		this.rp_date = rp_date;
		this.rp_bad = rp_bad;
		this.rp_state = rp_state;
		this.beReleased = beReleased;
	}

	// 출고를 제외한 생성자
	public ReturnVO(String rp_code, String rp_name, int rp_ea, int rp_price, int rp_total, String rp_date,
			String rp_bad, String rp_state) {
		super();
		this.rp_code = rp_code;
		this.rp_name = rp_name;
		this.rp_ea = rp_ea;
		this.rp_price = rp_price;
		this.rp_total = rp_total;
		this.rp_date = rp_date;
		this.rp_bad = rp_bad;
		this.rp_state = rp_state;
	}

	// 등록일을 제외한 생성자
	public ReturnVO(String rp_code, String rp_name, int rp_ea, int rp_price, int rp_total, String rp_bad,
			String rp_state) {
		super();
		this.rp_code = rp_code;
		this.rp_name = rp_name;
		this.rp_ea = rp_ea;
		this.rp_price = rp_price;
		this.rp_total = rp_total;
		this.rp_bad = rp_bad;
		this.rp_state = rp_state;
	}




	public String getRp_code() {
		return rp_code;
	}




	public void setRp_code(String rp_code) {
		this.rp_code = rp_code;
	}




	public String getRp_name() {
		return rp_name;
	}




	public void setRp_name(String rp_name) {
		this.rp_name = rp_name;
	}




	public int getRp_ea() {
		return rp_ea;
	}




	public void setRp_ea(int rp_ea) {
		this.rp_ea = rp_ea;
	}




	public int getRp_price() {
		return rp_price;
	}




	public void setRp_price(int rp_price) {
		this.rp_price = rp_price;
	}




	public int getRp_total() {
		return rp_total;
	}




	public void setRp_total(int rp_total) {
		this.rp_total = rp_total;
	}




	public String getRp_date() {
		return rp_date;
	}




	public void setRp_date(String rp_date) {
		this.rp_date = rp_date;
	}




	public String getRp_bad() {
		return rp_bad;
	}




	public void setRp_bad(String rp_bad) {
		this.rp_bad = rp_bad;
	}




	public String getRp_state() {
		return rp_state;
	}




	public void setRp_state(String rp_state) {
		this.rp_state = rp_state;
	}




	public boolean getBeReleased() {
		return beReleased;
	}




	public void setBeReleased(boolean beReleased) {
		this.beReleased = beReleased;
	}


}
