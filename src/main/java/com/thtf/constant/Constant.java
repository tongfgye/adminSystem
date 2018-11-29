package com.thtf.constant;

import javax.persistence.Column;

public class Constant {
	
	@Column
	private char reState;// 0 审核中 1审核通过 2 审核拒绝
	public static final char RESTATE_TODO='0';
	public static final char RESTATE_APPROVE='1';
	public static final char RESTATE_REFUSE='2';
	public static final char STATE_NEW='0';
	public static final char STATE_UPDATE='1';
	public static final char STATE_DELETE='2';
	public static final char STATE_VALID='3';
	
}
