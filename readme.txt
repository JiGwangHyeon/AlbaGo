userInfoController
  아이디 중복 체크
    /userInfo/idCheck/{u_id}
    - 아이디 입력
    - 일치하는 아이디 개수 출력
    - 반환형 int / 0이면 정상

  회원가입
    /userInfo/insert/{u_id}/{u_pw}/{u_name}/{u_email:.+}/{u_birth:.+}/{u_gender}/{u_addr}/{u_phone:.+}/{u_position}
    - 회원가입에 필요한 정보 입력
    - db에 insert 된 개수 출력
    - 반환형 int / 1이면 정상

  회원가입 위한 이메일 인증
    /userInfo/emailAuth/forRegister/{u_email:.+}/
    - URI의 끝에 email이 오는 경우 슬래시로 닫아줘야함
    - 회원가입에서 입력된 이메일주소 입력
    - db에 insert 된 개수 출력
    - 반환형 int / 1이면 정상

  이메일 인증번호 확인
    /userInfo/emailAuth/match/{u_email:.+}/{a_code}
    - 이메일 주소와 인증번호 입력
    - db에서 입력된 정보와 일치하고, 생성 후 3분이 지나지 않은 데이터 개수 출력
    - 반환형 int / 1이면 정상

  회원 정보 가져오기
    /userInfo/get/{u_id}
    - 회원 아이디 입력
    - 회원 정보 출력
    - 반환형 json

  로그인
    /userInfo/login/{u_id}/{u_pw}
    - 아이디, 비밀번호 입력
    - db에서 일치하는 데이터 개수 출력
    - 반환형 int / 1이면 정상

  아이디 찾기
    /userInfo/find/id/{u_name}/{u_email:.+}/
    - 이름, 이메일 입력
    - db에서 일치하는 데이터 개수 출력
    - db에서 일치하는 데이터 개수가 1일 경우 이메일로 아이디 발송
    - 반환형 int / 1이면 정상

  비밀번호 재설정 전 정보 인증
    /userInfo/find/pw/{u_name}/{u_id}/{u_email:.+}/
    - 이름, 아이디, 이메일 입력
    - db에서 일치하는 데이터 개수 출력
    - db에서 일치하는 데이터 개수가 1일 경우 이메일로 인증번호 발송
    - 반환형 int / 1이면 정상

  비밀번호 재설정
    /userInfo/reset/pw/{u_id}/{u_pw}
    - 아이디, 패스워드 입력
    - db에 update 된 개수 출력
    - 반환형 int / 1이면 정상

  계정 삭제
    /userInfo/delete/{u_id}
    - 아이디 입력
    - db에 delete 된 개수 출력
    - 반환형 int / 1이면 정상
    
  회원 이름 가져오기
    /userInfo/get/name/{u_id}
    - 아이디 입력
    - 회원 이름 출력
    - 반환형 String

NoticeController
  제목 리스트 불러오기
    /notice/getList/{c_code}
    - c_code 입력
    - c_code에 해당하는 공지사항 제목들 json으로 출력
    - 반환형 List<NoticeVO>
    
CompanyController
  회사명 불러오기
    /company/getName/{c_code}
    - c_code 입력
    - 회사 명 출력
    - 반환형 String

ScheduleController
  오늘의 일정 불러오기(직원용)
  /schedule/todays/{c_code}/{u_id}
  - c_code, u_id 입력
  - 오늘의 일정만 담긴 ScheduleVO 출력
  - 반환형 ScheduleVO
  
  주별 일정 불러오기(직원용)
  /schedule/week/{c_code}/{u_id}/{week}
  - 회사코드, 사용자 아이디, 주 입력
  - 주: 0 - 이번주, 음수 - 과거, 양수 - 미래
  - 해당 주의 스케줄 List<ScheduleVO> 로 출력
  - 반환형 List<ScheduleVO>
  
  월별 일정 불러오기(직원용)
  /schedule/month/{c_code}/{u_id}/{year}/{month}
  - 회사코드, 사용자 아이디, 년, 월 입력
  - 년, 월은 실제 년 월 입력
  - 해당 월의 스케줄 List<ScheduleVO> 로 출력
  - 반환형 List<ScheduleVO>

