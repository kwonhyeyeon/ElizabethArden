package model;

public class SaleVO {

	private String e_name; // 직원명
	private String c_name; // 고객명
	private int c_code; // 고객코드
	private String e_code; // 직원코드
	private int no; // 상품 일련번호
	private String p_code; // 상품코드
	private String p_name; // 상품명
	private String sr_state; // 상태
	private int sr_ea; // 수량
	private int p_price; // 단가
	private int sr_total; // 총액
	private int p_point; // 포인트
	private int sr_used_point; // 포인트사용금액
	private String sr_return_reason; // 반품 사유
	private String build_date; // 판매일자
	
	public SaleVO() {
		super();
	}
	
	public SaleVO(int c_code, String e_code, String p_code, String p_name, String sr_state, int sr_ea, int p_price,
			int sr_total, int p_point) {
		super();
		this.c_code = c_code;
		this.e_code = e_code;
		this.p_code = p_code;
		this.p_name = p_name;
		this.sr_state = sr_state;
		this.sr_ea = sr_ea;
		this.p_price = p_price;
		this.sr_total = sr_total;
		this.p_point = p_point;
	} 
	
	
	public SaleVO(String e_name, String c_name, int c_code, String e_code, int no, String p_code, String p_name,
			String sr_state, int sr_ea, int p_price, int sr_total, int p_point, int sr_used_point,
			String sr_return_reason, String build_date) {
		super();
		this.e_name = e_name;
		this.c_name = c_name;
		this.c_code = c_code;
		this.e_code = e_code;
		this.no = no;
		this.p_code = p_code;
		this.p_name = p_name;
		this.sr_state = sr_state;
		this.sr_ea = sr_ea;
		this.p_price = p_price;
		this.sr_total = sr_total;
		this.p_point = p_point;
		this.sr_used_point = sr_used_point;
		this.sr_return_reason = sr_return_reason;
		this.build_date = build_date;
	}

	// sr_return 테이블 생성자
	public SaleVO(int c_code, String e_code, int no, String p_code, String sr_state, int sr_ea, int sr_used_point,
			String sr_return_reason) {
		super();
		this.c_code = c_code;
		this.e_code = e_code;
		this.no = no;
		this.p_code = p_code;
		this.sr_state = sr_state;
		this.sr_ea = sr_ea;
		this.sr_used_point = sr_used_point;
		this.sr_return_reason = sr_return_reason;
	}
	
	// 반품 사유 제외 생성자
	public SaleVO(String e_name, String c_name, int c_code, String e_code, int no, String p_code, String p_name,
			String sr_state, int sr_ea, int p_price, int sr_total, int p_point, int sr_used_point,
			String build_date) {
		super();
		this.e_name = e_name;
		this.c_name = c_name;
		this.c_code = c_code;
		this.e_code = e_code;
		this.no = no;
		this.p_code = p_code;
		this.p_name = p_name;
		this.sr_state = sr_state;
		this.sr_ea = sr_ea;
		this.p_price = p_price;
		this.sr_total = sr_total;
		this.p_point = p_point;
		this.sr_used_point = sr_used_point;
		this.build_date = build_date;
	}	

	// 상품코드, 상품명, 단가, 포인트 생성자
	public SaleVO(String p_code, String p_name, int sr_ea, int p_price, int sr_total, int p_point) {
		super();
		this.p_code = p_code;
		this.p_name = p_name;
		this.sr_ea = sr_ea;
		this.p_price = p_price;
		this.sr_total = sr_total;
		this.p_point = p_point;
	}
	
	// 고객 조회시 구매 내역 생성자
	public SaleVO(String p_code, String p_name, String sr_state, int sr_ea, int p_price, int sr_total, int p_point,
			int sr_used_point, String build_date) {
		super();
		this.p_code = p_code;
		this.p_name = p_name;
		this.sr_state = sr_state;
		this.sr_ea = sr_ea;
		this.p_price = p_price;
		this.sr_total = sr_total;
		this.p_point = p_point;
		this.sr_used_point = sr_used_point;
		this.build_date = build_date;
	}

	public String getE_name() {
		return e_name;
	}

	public void setE_name(String e_name) {
		this.e_name = e_name;
	}

	public String getC_name() {
		return c_name;
	}

	public void setC_name(String c_name) {
		this.c_name = c_name;
	}

	public int getC_code() {
		return c_code;
	}

	public void setC_code(int c_code) {
		this.c_code = c_code;
	}

	public String getE_code() {
		return e_code;
	}

	public void setE_code(String e_code) {
		this.e_code = e_code;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getP_code() {
		return p_code;
	}

	public void setP_code(String p_code) {
		this.p_code = p_code;
	}

	public String getP_name() {
		return p_name;
	}

	public void setP_name(String p_name) {
		this.p_name = p_name;
	}

	public String getSr_state() {
		return sr_state;
	}

	public void setSr_state(String sr_state) {
		this.sr_state = sr_state;
	}

	public int getSr_ea() {
		return sr_ea;
	}

	public void setSr_ea(int sr_ea) {
		this.sr_ea = sr_ea;
	}

	public int getP_price() {
		return p_price;
	}

	public void setP_price(int p_price) {
		this.p_price = p_price;
	}

	public int getSr_total() {
		return sr_total;
	}

	public void setSr_total(int sr_total) {
		this.sr_total = sr_total;
	}

	public int getP_point() {
		return p_point;
	}

	public void setP_point(int p_point) {
		this.p_point = p_point;
	}

	public int getSr_used_point() {
		return sr_used_point;
	}

	public void setSr_used_point(int sr_used_point) {
		this.sr_used_point = sr_used_point;
	}

	public String getSr_return_reason() {
		return sr_return_reason;
	}

	public void setSr_return_reason(String sr_return_reason) {
		this.sr_return_reason = sr_return_reason;
	}

	public String getBuild_date() {
		return build_date;
	}

	public void setBuild_date(String build_date) {
		this.build_date = build_date;
	}

	@Override
	public String toString() {
		return "SaleVO [e_name=" + e_name + ", c_name=" + c_name + ", no=" + no + ", p_code=" + p_code + ", p_name="
				+ p_name + ", sr_state=" + sr_state + ", sr_ea=" + sr_ea + ", p_price=" + p_price + ", sr_total="
				+ sr_total + ", p_point=" + p_point + ", sr_used_point=" + sr_used_point + ", sr_return_reason="
				+ sr_return_reason + ", build_date=" + build_date + "]";
	}
	
}
