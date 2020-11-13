아이디 중복 체크
  /userInfo/idCheck/{u_id}

회원가입
  /userInfo/insert/{u_id}/{u_pw}/{u_name}/{u_email:.+}/{u_birth:.+}/{u_gender}/{u_addr}/{u_phone:.+}/{u_position}

회원가입 위한 이메일 인증
  /userInfo/emailAuth/forRegister/{u_email}

이메일 인증번호 확인
  /userInfo/emailAuth/match/{u_email}/{a_code}

회원 정보 가져오기
  /userInfo/get/{u_id}

로그인
  /userInfo/login/{u_id}/{u_pw}

아이디 찾기
  /userInfo/find/id/{u_name}/{u_email:.+}

비밀번호 재설정 전 정보 인증
  /userInfo/find/pw/{u_name}/{u_id}/{u_email:.+}

비밀번호 재설정
  /userInfo/reset/pw/{u_id}/{u_pw}

계정 삭제
  /userInfo/delete/{u_id}
