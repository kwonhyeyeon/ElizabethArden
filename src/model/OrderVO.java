package model;

import java.util.ArrayList;

public class OrderVO {

	int no; // 주문, 반품 일련번호
	String p_code; // 상품코드
	String p_name; // 상품명
	int or_ea; // 수량
	int price; // 단가
	int or_total; // 총액
	String or_bad; // 불량 여부
	String or_state; // 주문, 반품 상태
	String or_date; // 주문 등록일자
	String in_out; // 입출고
	
	public OrderVO() {
		super();
	}

	public OrderVO(int no, String p_code, String p_name, int or_ea, int price, int or_total, String or_bad,
			String or_state, String or_date, String in_out) {
		super();
		this.no = no;
		this.p_code = p_code;
		this.p_name = p_name;
		this.or_ea = or_ea;
		this.price = price;
		this.or_total = or_total;
		this.or_bad = or_bad;
		this.or_state = or_state;
		this.or_date = or_date;
		this.in_out = in_out;
	}	

	public OrderVO(String p_code) {
		super();
		this.p_code = p_code;
	}

	// 상품코드, 상품명, 수량, 단가, 총액
	public OrderVO(String p_code, String p_name, int or_ea, int price, int or_total) {
		super();
		this.p_code = p_code;
		this.p_name = p_name;
		this.or_ea = or_ea;
		this.price = price;
		this.or_total = or_total;
	}	

	public OrderVO(String p_code, String p_name, int or_ea, int price, String or_bad, String or_date, String in_out) {
		super();
		this.p_code = p_code;
		this.p_name = p_name;
		this.or_ea = or_ea;
		this.price = price;
		this.or_bad = or_bad;
		this.or_date = or_date;
		this.in_out = in_out;
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

	public int getOr_ea() {
		return or_ea;
	}

	public void setOr_ea(int or_ea) {
		this.or_ea = or_ea;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getOr_total() {
		return or_total;
	}

	public void setOr_total(int or_total) {
		this.or_total = or_total;
	}

	public String getOr_bad() {
		return or_bad;
	}

	public void setOr_bad(String or_bad) {
		this.or_bad = or_bad;
	}

	public String getOr_state() {
		return or_state;
	}

	public void setOr_state(String or_state) {
		this.or_state = or_state;
	}

	public String getOr_date() {
		return or_date;
	}

	public void setOr_date(String or_date) {
		this.or_date = or_date;
	}

	public String getIn_out() {
		return in_out;
	}

	public void setIn_out(String in_out) {
		this.in_out = in_out;
	}

	@Override
	public String toString() {
		return "OrderVO [no=" + no + ", p_code=" + p_code + ", p_name=" + p_name + ", or_ea=" + or_ea + ", price="
				+ price + ", or_total=" + or_total + ", or_bad=" + or_bad + ", or_state=" + or_state + ", or_date="
				+ or_date + ", in_out=" + in_out + "]";
	}
	
}
