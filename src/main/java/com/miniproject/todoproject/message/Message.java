package com.miniproject.todoproject.message;

public class Message {
	// ERROR MESSAGE
	public static final String NOT_EXIST_CARD = "해당 카드가 존재하지 않습니다.";
	public static final String NOT_EXIST_COMMENT = "해당 댓글이 존재하지 않습니다.";
	public static final String NOT_EXIST_USER = "해당 유저가 존재하지 않습니다.";
	public static final String NOT_WRITER = "해당 작성자가 아닙니다.";
	public static final String DUPLICATE_USERNAME = "이름이 중복되셨습니다.";
	public static final String NOT_MATCH_PASSWORD = "비밀번호가 일치하지 않습니다.";

	// INVALIDATE MESSAGE
	public static final String INVALIDATE_USERNAME = "최소 4자 이상, 10자 이하, 알파벳 소문자와 숫자로 이름을 구성해야합니다.";
	public static final String INVALIDATE_PASSWORD = "최소 8자 이상, 15자 이하, 알파벳 대소문자와 숫자로 번호를 구성해야합니다.";

	// SUCCESS MESSAGE
	public static final String SUCCESS_SIGNUP = "회원가입에 성공하셨습니다.";
	public static final String SUCCESS_LOGIN = "로그인에 성공하셨습니다.";

	// COMMENT MESSAGE
	public static final String CREATE_COMMENT = "댓글을 작성하셨습니다.";
	public static final String UPDATE_COMMENT = "댓글을 수정하셨습니다.";
	public static final String DELETE_COMMENT = "댓글을 삭제하셨습니다.";

	// CARD MESSAGE
	public static final String CREATE_CARD = "할 일 카드를 생성하셨습니다.";
	public static final String READ_CARDS = "카드 목록들을 조회합니다.";
	public static final String READ_CARD = "카드 정보를 조회합니다.";
	public static final String UPDATE_CARD = "카드를 수정합니다.";
	public static final String COMPLETE_CARD = "카드를 완료하였습니다.";
	public static final String DELETE_CARD = "카드를 삭제하였습니다.";

}
