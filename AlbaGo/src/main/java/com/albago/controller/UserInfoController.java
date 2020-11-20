package com.albago.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.albago.domain.EmailAuthVO;
import com.albago.domain.EmailDTO;
import com.albago.domain.UserInfoVO;
import com.albago.service.EmailAuthService;
import com.albago.service.EmailService;
import com.albago.service.UserInfoService;
import com.albago.util.MakeRandomCode;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@RestController
@RequestMapping("/userInfo")
@Log4j
@AllArgsConstructor
public class UserInfoController {

	private UserInfoService userInfoService;
	private EmailAuthService emailAuthService;
	@Inject
	private EmailService emailService;

	// 아이디 중복 체크
	@GetMapping(value = "/idCheck/{u_id}", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public int idCheck(@PathVariable("u_id") String u_id) {
		log.info("idCheck 호출.........................");

		return userInfoService.idCheck(u_id); // db에 중복되는 아이디 개수 반환
	}

	// 회원가입
	@GetMapping(value = "/insert/{u_id}/{u_pw}/{u_name}/{u_email:.+}/{u_birth:.+}/{u_gender}/{u_addr}/{u_phone:.+}/{u_position}", produces = {
			MediaType.APPLICATION_JSON_UTF8_VALUE })
	public int register(@PathVariable("u_id") String u_id, @PathVariable("u_pw") String u_pw,
			@PathVariable("u_name") String u_name, @PathVariable("u_email") String u_email,
			@PathVariable("u_birth") String u_birth, @PathVariable("u_gender") int u_gender,
			@PathVariable("u_addr") String u_addr, @PathVariable("u_phone") String u_phone,
			@PathVariable("u_position") char u_position) {

		log.info("register 호출.....");

		UserInfoVO userInfo = new UserInfoVO();

		userInfo.setU_id(u_id);
		userInfo.setU_pw(u_pw);
		userInfo.setU_name(u_name);
		userInfo.setU_email(u_email);
		userInfo.setU_birth(u_birth);
		userInfo.setU_gender(u_gender);
		userInfo.setU_addr(u_addr);
		userInfo.setU_phone(u_phone);
		userInfo.setU_position(u_position);

		log.info(userInfo);

		return userInfoService.register(userInfo); // 정상적으로 db에 insert 된 갯수 반환
													// 1일 경우 정상, 그 이외엔 비정상
	}

	// 회원가입 시 이메일 인증, URI의 끝에 이메일이 오는 경우 '.com'등으로 인해 확장자로 인식함. 슬래시로 닫아줘야함
	@GetMapping(value = "/emailAuth/forRegister/{u_email}", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public int sendEmailAuthForRegister(@PathVariable("u_email") String u_email) {

		log.info("sendEmailAuthForRegister 호출");

		MakeRandomCode makeCode = new MakeRandomCode(); // 난수 발생을 위한 클래스
		String a_code = Integer.toString(makeCode.makeRandomNumber()); // 난수 생성

		EmailDTO email = new EmailDTO(); // 이메일 발송에 필요한 내용 담을 객체 생성

		email.setSenderName("AlbaGo.noReply"); // 발신자 이름
		email.setSenderMail("albago.noreply@gmail.com"); // 발신자 이메일 주소
		email.setReceiveMail(u_email); // 수신자 이메일 주소
		email.setSubject("알바고에서 인증번호를 요청했습니다."); // 이메일 제목
		email.setMessage("인증번호는 " + a_code + " 입니다."); // 이메일 내용

		emailService.sendMail(email); // 이메일 발송

		EmailAuthVO emailAuth = new EmailAuthVO(); // 이메일 인증에 필요한 내용 담을 객체 생성

		emailAuth.setU_email(u_email);
		emailAuth.setA_code(a_code);

		return emailAuthService.makeAuthCode(emailAuth); // emailAuth테이블에 입력된 데이터 개수 출력
															// 1이면 정상
	}

	// 이메일 인증번호 확인
	@GetMapping(value = "/emailAuth/match/{u_email}/{a_code}", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public int matchEmailAuth(@PathVariable("u_email") String u_email, @PathVariable("a_code") String a_code) {

		log.info("matchEmailAuth");

		EmailAuthVO emailAuth = new EmailAuthVO(); // 이메일 인증에 필요한 내용 담을 객체 생성

		emailAuth.setU_email(u_email);
		emailAuth.setA_code(a_code);

		return emailAuthService.matchCode(emailAuth); // emailAuth테이블에서 일치하는 데이터 개수 반환
														// 1이면 정상
	}

	// 회원 정보 가져오기
	@GetMapping(value = "/get/{u_id}", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public UserInfoVO get(@PathVariable("u_id") String u_id) {
		log.info("get 호출.......................");

		return userInfoService.get(u_id); // json 데이터 출력
	}

	// 로그인한 상태의 회원 정보 가져오기
	@GetMapping(value = "/get/session", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public UserInfoVO sessionGet(HttpServletRequest request) {
		log.info("session get 호출.......................");

		HttpSession session = request.getSession();
		String u_id = (String) session.getAttribute("u_id");
		log.info("u_id : " + u_id);

		return userInfoService.get(u_id); // json 데이터 출력
	}

	// 회원 이름 가져오기
	@GetMapping(value = "/getName", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseEntity<String> getName(HttpServletRequest request) {
		log.info("getName 호출.......................");

		HttpSession session = request.getSession();
		String u_id = (String) session.getAttribute("u_id");
		if (u_id == null || u_id.trim().isEmpty()) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		String st = userInfoService.get(u_id).getU_name();

		return new ResponseEntity<>(st, HttpStatus.OK); // String 데이터 출력
	}

	// 로그인
	@GetMapping(value = "/login/{u_id}/{u_pw}", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public int login(@PathVariable("u_id") String u_id, @PathVariable("u_pw") String u_pw, HttpServletRequest request) {
		log.info("login 호출......................");

		UserInfoVO userInfo = new UserInfoVO();

		userInfo.setU_id(u_id);
		userInfo.setU_pw(u_pw);

		int result = userInfoService.login(userInfo);

		if (result == 1) {
			HttpSession session = request.getSession();
			session.setAttribute("u_id", u_id);
			log.info(session.getId());
		}

		return result; // userInfo테이블에서 일치하는 데이터 개수 반환
		// 1이면 정상
	}

	// 아이디 찾기, URI의 끝에 이메일이 오는 경우 '.com'등으로 인해 확장자로 인식함. 슬래시로 닫아줘야함
	@GetMapping(value = "/find/id/{u_name}/{u_email:.+}", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public int beforeFindId(@PathVariable("u_name") String u_name, @PathVariable("u_email") String u_email) {
		log.info("findId 호출............" + u_name + " " + u_email);

		UserInfoVO userInfo = new UserInfoVO();

		userInfo.setU_name(u_name);
		userInfo.setU_email(u_email);

		int result = userInfoService.beforeFindId(userInfo); // userInfo테이블에서 일치하는 데이터 개수

		if (result == 1) { // 1이면 정상, 이메일로 아이디 발송

			String find_id = userInfoService.findId(userInfo); // userInfo테이블에서 입력받은 데이터와 일치하는 아이디 반환
			String find_id_hide = find_id.substring(0, find_id.length() - 3); // 아이디 뒷 세자리 *처리

			EmailDTO email = new EmailDTO();

			email.setSenderName("AlbaGo.noReply");
			email.setSenderMail("albago.noreply@gmail.com");
			email.setReceiveMail(u_email);
			email.setSubject("알바고에서 아이디 찾기 결과를 발송합니다.");
			email.setMessage("회원님의 아이디는 " + find_id_hide + "*** 입니다.");

			emailService.sendMail(email);
		}

		return result; // userInfo테이블에서 일치하는 데이터 개수 반환
	}

	// 비밀번호 재설정 전 정보 인증, URI의 끝에 이메일이 오는 경우 '.com'등으로 인해 확장자로 인식함. 슬래시로 닫아줘야함
	@GetMapping(value = "/find/pw/{u_name}/{u_id}/{u_email:.+}", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public int beforeFindPw(@PathVariable("u_name") String u_name, @PathVariable("u_id") String u_id,
			@PathVariable("u_email") String u_email) {
		log.info("findPw 호출........................" + u_name + " " + u_id + " " + u_email);

		UserInfoVO userInfo = new UserInfoVO();

		userInfo.setU_name(u_name);
		userInfo.setU_id(u_id);
		userInfo.setU_email(u_email);

		int result = userInfoService.beforeFindPw(userInfo); // userInfo테이블에서 일치하는 데이터 개수

		if (result == 1) { // 1이면 정상, 이메일로 인증번호 발송
			MakeRandomCode makeCode = new MakeRandomCode(); // 난수 발생을 위한 클래스
			String a_code = Integer.toString(makeCode.makeRandomNumber()); // 난수 생성

			EmailDTO email = new EmailDTO(); // 이메일 발송에 필요한 내용 담을 객체 생성

			email.setSenderName("AlbaGo.noReply");
			email.setSenderMail("albago.noreply@gmail.com");
			email.setReceiveMail(u_email);
			email.setSubject("알바고에서 인증번호를 요청했습니다.");
			email.setMessage("인증번호는 " + a_code + " 입니다.");

			emailService.sendMail(email);

			EmailAuthVO emailAuth = new EmailAuthVO(); // 이메일 인증에 필요한 내용 담을 객체 생성

			emailAuth.setU_email(u_email);
			emailAuth.setA_code(a_code);

			emailAuthService.makeAuthCode(emailAuth);
		}

		return result;
	}

	// 비밀번호 재설정
	@GetMapping(value = "/reset/pw/{u_id}/{u_pw}", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public int resetPw(@PathVariable("u_id") String u_id, @PathVariable("u_pw") String u_pw) {
		log.info("resetPw 호출 ..............................");

		UserInfoVO userInfo = new UserInfoVO();

		userInfo.setU_id(u_id);
		userInfo.setU_pw(u_pw);

		return userInfoService.resetPw(userInfo); // 비밀번호 재설정 결과 반환, 1이면 정상
	}

	// 계정 삭제
	@GetMapping(value = "/delete", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseEntity<Integer> deleteUser(HttpServletRequest request) {
		log.info("deleteUser 호출.....................");

		HttpSession session = request.getSession();
		String u_id = (String) session.getAttribute("u_id");
		if (u_id == null || u_id.trim().isEmpty()) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		return new ResponseEntity<>(userInfoService.deleteUser(u_id), HttpStatus.OK); // 계정 삭제 결과 반환, 1이면 정상
	}
}