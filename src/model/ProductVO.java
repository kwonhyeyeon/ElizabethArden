package model;

public class ProductVO {
	private String p_code; // 상품 코드
	private int p_no; // 상품 일련번호
	private String p_name; // 상품명
	private int p_ea; // 상품 수량
	private int p_price; // 상품 단가
	private int p_total; // 상품 총액
	private int p_point; // 포인트

	// 디폴트 생성자
	public ProductVO() {
		super();
	}

	// 일련번호, 총액, 포인트를 제외한 생성자
	public ProductVO(String p_code, String p_name, int p_ea, int p_price) {
		super();
		this.p_code = p_code;
		this.p_name = p_name;
		this.p_ea = p_ea;
		this.p_price = p_price;
	}

	// 일련번호를 제외한 생성자
	public ProductVO(String p_code, String p_name, int p_ea, int p_price, int p_total, int p_point) {
		super();
		this.p_code = p_code;
		this.p_name = p_name;
		this.p_ea = p_ea;
		this.p_price = p_price;
		this.p_total = p_total;
		this.p_point = p_point;
	}

	// 총액과 포인트를 제외한 생성자
	public ProductVO(String p_code, int p_no, String p_name, int p_ea, int p_price) {
		super();
		this.p_code = p_code;
		this.p_no = p_no;
		this.p_name = p_name;
		this.p_ea = p_ea;
		this.p_price = p_price;
	}

	// 전체필드 생성자
	public ProductVO(String p_code, int p_no, String p_name, int p_ea, int p_price, int p_total, int p_point) {
		super();
		this.p_code = p_code;
		this.p_no = p_no;
		this.p_name = p_name;
		this.p_ea = p_ea;
		this.p_price = p_price;
		this.p_total = p_total;
		this.p_point = p_point;
	}
	//get,set
	public String getP_code() {
		return p_code;
	}

	public void setP_code(String p_code) {
		this.p_code = p_code;
	}

	public int getP_no() {
		return p_no;
	}

	public void setP_no(int p_no) {
		this.p_no = p_no;
	}

	public String getP_name() {
		return p_name;
	}

	public void setP_name(String p_name) {
		this.p_name = p_name;
	}

	public int getP_ea() {
		return p_ea;
	}

	public void setP_ea(int p_ea) {
		this.p_ea = p_ea;
	}

	public int getP_price() {
		return p_price;
	}

	public void setP_price(int p_price) {
		this.p_price = p_price;
	}

	public int getP_total() {
		return p_total;
	}

	public void setP_total(int p_total) {
		this.p_total = p_total;
	}

	public int getP_point() {
		return p_point;
	}

	public void setP_point(int p_point) {
		this.p_point = p_point;
	}

}
