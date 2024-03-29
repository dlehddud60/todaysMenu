CREATE TABLE TM_FREE_BOARD
(       TFB_SEQ         NUMBER(10) NOT NULL
,       TFB_TITLE_NM    VARCHAR2(300)
,       TFB_TEXT        VARCHAR2(4000)
,       TFB_INPUT_TY    VARCHAR2(300)
,       TFB_INPUT_NM    VARCHAR2(300)
,       TFB_INPUT_DT    DATE
,       TFB_INPUT_IP    VARCHAR2(300)
,       TFB_MODER_TY    VARCHAR2(300)
,       TFB_MODER_NM    VARCHAR2(300)
,       TFB_MODER_DT    DATE
,       TFB_MODER_IP    VARCHAR2(300)
,       PRIMARY KEY(TFB_SEQ)
);


COMMENT ON TABLE TM_FREE_BOARD IS '자유게시판';
COMMENT ON COLUMN TM_FREE_BOARD.TFB_SEQ IS '게시글번호';
COMMENT ON COLUMN TM_FREE_BOARD.TFB_TITLE_NM IS '제목';
COMMENT ON COLUMN TM_FREE_BOARD.TFB_TEXT IS '내용';
COMMENT ON COLUMN TM_FREE_BOARD.TFB_INPUT_TY IS '작성자타입';
COMMENT ON COLUMN TM_FREE_BOARD.TFB_INPUT_NM IS '작성자이름';
COMMENT ON COLUMN TM_FREE_BOARD.TFB_INPUT_DT IS '작성일';
COMMENT ON COLUMN TM_FREE_BOARD.TFB_INPUT_IP IS '수정자IP';
COMMENT ON COLUMN TM_FREE_BOARD.TFB_MODER_TY IS '수정자타입';
COMMENT ON COLUMN TM_FREE_BOARD.TFB_MODER_NM IS '수정자이름';
COMMENT ON COLUMN TM_FREE_BOARD.TFB_MODER_DT IS '수정일';
COMMENT ON COLUMN TM_FREE_BOARD.TFB_MODER_IP IS '수정자IP';


CREATE SEQUENCE "TM_TFB_SEQ" MINVALUE 1 MAXVALUE 999999999999999999999999 INCREMENT BY 1 START WITH 1000000 NOCACHE NOORDER NOCYCLE;




CREATE TABLE TM_BOARD_REPLY_TBL
(   TBRT_SEQ                 NUMBER(20) NOT NULL
,   TBRT_PARENT_SEQ          NUMBER(20) NOT NULL
,   TBRT_BOARD_SEQ           NUMBER(20) NOT NULL
,   TBRT_REPLY_TEXT          VARCHAR2(3000) NOT NULL
,   TBRT_MEM_PROFILE         VARCHAR2(300)
,   TBRT_DELETE_YN           VARCHAR2(20)
,   TBRT_INPUT_TY            VARCHAR2(300)
,   TBRT_INPUT_NM            VARCHAR2(300)
,   TBRT_INPUT_ID            VARCHAR2(300)
,   TBRT_INPUT_IP            VARCHAR2(300)
,   TBRT_INPUT_DT            DATE
,   TBRT_MODER_TY            VARCHAR2(300)
,   TBRT_MODER_NM            VARCHAR2(300)
,   TBRT_MODER_ID            VARCHAR2(300)
,   TBRT_MODER_IP            VARCHAR2(300)
,   TBRT_MODER_DT            DATE
,   PRIMARY KEY(TBRT_SEQ)
,   CONSTRAINT TBRT_FK FOREIGN KEY (TBRT_BOARD_SEQ) REFERENCES TM_FREE_BOARD (TFB_SEQ) ON DELETE CASCADE
);

COMMENT ON TABLE TM_BOARD_REPLY_TBL IS '게시판 댓글 테이블';
COMMENT ON COLUMN TM_BOARD_REPLY_TBL.TBRT_SEQ IS '게시글번호';
COMMENT ON COLUMN TM_BOARD_REPLY_TBL.TBRT_PARENT_SEQ IS '댓글 그룹';
COMMENT ON COLUMN TM_BOARD_REPLY_TBL.TBRT_BOARD_SEQ IS '게시글 번호';
COMMENT ON COLUMN TM_BOARD_REPLY_TBL.TBRT_REPLY_TEXT IS '댓글 내용';
COMMENT ON COLUMN TM_BOARD_REPLY_TBL.TBRT_MEM_PROFILE IS '회원 프로필';
COMMENT ON COLUMN TM_BOARD_REPLY_TBL.TBRT_DELETE_YN IS '댓글 삭제여부';
COMMENT ON COLUMN TM_BOARD_REPLY_TBL.TBRT_INPUT_TY IS '작성자 타입';
COMMENT ON COLUMN TM_BOARD_REPLY_TBL.TBRT_INPUT_NM IS '작성자 이름';
COMMENT ON COLUMN TM_BOARD_REPLY_TBL.TBRT_INPUT_ID IS '작성자 아이디';
COMMENT ON COLUMN TM_BOARD_REPLY_TBL.TBRT_INPUT_DT IS '작자성일';
COMMENT ON COLUMN TM_BOARD_REPLY_TBL.TBRT_INPUT_IP IS '작성자 아이피';
COMMENT ON COLUMN TM_BOARD_REPLY_TBL.TBRT_MODER_TY IS '수정자 타입';
COMMENT ON COLUMN TM_BOARD_REPLY_TBL.TBRT_MODER_NM IS '수정자 이름';
COMMENT ON COLUMN TM_BOARD_REPLY_TBL.TBRT_MODER_ID IS '수정자 아이디';
COMMENT ON COLUMN TM_BOARD_REPLY_TBL.TBRT_MODER_DT IS '수성일';
COMMENT ON COLUMN TM_BOARD_REPLY_TBL.TBRT_MODER_IP IS '수정자 아이피';

CREATE SEQUENCE "TBRT_SEQ" MINVALUE 1 MAXVALUE 999999999999999999999999 INCREMENT BY 1 START WITH 1000000 NOCACHE NOORDER NOCYCLE;



CREATE TABLE TM_MEMBER_TBL
(   TMT_SEQ                     NUMBER(20) NOT NULL
,   TMT_LOGIN_ID                VARCHAR2(20) NOT NULL
,   TMT_PASS_WORD               VARCHAR2(200) NOT NULL
,   TMT_MEMB_NAME               VARCHAR2(20) NOT NULL
,   TMT_MEMB_AGE                NUMBER(20)
,   TMT_MEMB_GENDER             VARCHAR2(20)
,   TMT_MEMB_EMAIL              VARCHAR2(200)
,   TMT_MEMB_BIRTH_DAY          DATE
,   TMT_MEMB_FILE               VARCHAR2(20)
,   TMT_USER_TYPE               VARCHAR2(20) DEFAULT 'user'
,   TMT_INPUT_DATE              DATE NOT NULL
,   TMT_MODER_DATE              DATE
,   PRIMARY KEY(TMT_SEQ,TMT_LOGIN_ID)
);
COMMENT ON TABLE TM_MEMBER_TBL IS '회원';
COMMENT ON COLUMN TM_MEMBER_TBL.TMT_SEQ IS '회원 번호 (PK)';
COMMENT ON COLUMN TM_MEMBER_TBL.TMT_LOGIN_ID IS '로그인 ID';
COMMENT ON COLUMN TM_MEMBER_TBL.TMT_PASS_WORD IS '비밀번호';
COMMENT ON COLUMN TM_MEMBER_TBL.TMT_MEMB_NAME IS '이름';
COMMENT ON COLUMN TM_MEMBER_TBL.TMT_MEMB_AGE IS '나이';
COMMENT ON COLUMN TM_MEMBER_TBL.TMT_MEMB_GENDER IS '성별';
COMMENT ON COLUMN TM_MEMBER_TBL.TMT_MEMB_EMAIL IS '이메일';
COMMENT ON COLUMN TM_MEMBER_TBL.TMT_MEMB_BIRTH_DAY IS '생년월일';
COMMENT ON COLUMN TM_MEMBER_TBL.TMT_MEMB_FILE IS '프로필';
COMMENT ON COLUMN TM_MEMBER_TBL.TMT_INPUT_DATE IS '생성일시';
COMMENT ON COLUMN TM_MEMBER_TBL.TMT_MODER_DATE IS '최종 수정일시';
COMMENT ON COLUMN TM_MEMBER_TBL.TMT_USER_TYPE IS '회원 등급';


ALTER TABLE TM_MEMBER_TBL ADD TMT_USER_TYPE VARCHAR2(20);

CREATE SEQUENCE "TM_TMT_SEQ" MINVALUE 1 MAXVALUE 999999999999999999999999 INCREMENT BY 1 START WITH 1000000 NOCACHE NOORDER NOCYCLE;





CREATE TABLE TM_REST_TBL
(    TRT_SEQ                NUMBER(20) NOT NULL
,    TRT_COUNT              NUMBER NOT NULL DEFAULT 0
,    TRT_REST_NAME          VARCHAR2(300) NOT NULL
,    TRT_REGI               VARCHAR2(100) NOT NULL
,    TRT_ADDR               VARCHAR2(300) NOT NULL
,    TRT_FOOD_TYPE          VARCHAR2(100) NOT NULL
,    TRT_REST_TEXT          VARCHAR2(4000) NOT NULL
,    TRT_INPUT_TY           VARCHAR2(300)
,    TRT_INPUT_NM           VARCHAR2(300)
,    TRT_INPUT_ID           VARCHAR2(300)
,    TRT_INPUT_IP           VARCHAR2(300)
,    TRT_INPUT_DT           DATE
,    TRT_MODER_TY           VARCHAR2(300)
,    TRT_MODER_NM           VARCHAR2(300)
,    TRT_MODER_ID           VARCHAR2(300)
,    TRT_MODER_IP           VARCHAR2(300)
,    TRT_MODER_DT           DATE

,   PRIMARY KEY(TRT_SEQ)
);


COMMENT ON TABLE TM_REST_TBL IS '식당테이블';
COMMENT ON COLUMN TM_REST_TBL.TRT_SEQ IS '식당테이블 시퀀스';
COMMENT ON COLUMN TM_REST_TBL.TRT_COUNT IS '조회수';
COMMENT ON COLUMN TM_REST_TBL.TRT_REST_NAME IS '식명당명';
COMMENT ON COLUMN TM_REST_TBL.TRT_REGI IS '지역';
COMMENT ON COLUMN TM_REST_TBL.TRT_ADDR IS '주소';
COMMENT ON COLUMN TM_REST_TBL.TRT_FOOD_TYPE IS '식당타입';
COMMENT ON COLUMN TM_REST_TBL.TRT_REST_TEXT IS '식당내용';
COMMENT ON COLUMN TM_REST_TBL.TRT_INPUT_TY IS '작성자 타입';
COMMENT ON COLUMN TM_REST_TBL.TRT_INPUT_NM IS '작성자 이름';
COMMENT ON COLUMN TM_REST_TBL.TRT_INPUT_ID IS '작성자 아이디';
COMMENT ON COLUMN TM_REST_TBL.TRT_INPUT_DT IS '작자성일';
COMMENT ON COLUMN TM_REST_TBL.TRT_INPUT_IP IS '작성자 아이피';
COMMENT ON COLUMN TM_REST_TBL.TRT_MODER_TY IS '수정자 타입';
COMMENT ON COLUMN TM_REST_TBL.TRT_MODER_NM IS '수정자 이름';
COMMENT ON COLUMN TM_REST_TBL.TRT_MODER_ID IS '수정자 아이디';
COMMENT ON COLUMN TM_REST_TBL.TRT_MODER_DT IS '수성일';
COMMENT ON COLUMN TM_REST_TBL.TRT_MODER_IP IS '수정자 아이피';


CREATE SEQUENCE "TRT_SEQ" MINVALUE 1 MAXVALUE 999999999999999999999999 INCREMENT BY 1 START WITH 1000000 NOCACHE NOORDER NOCYCLE;


INSERT INTO TM_REST_TBL
(   TRT_SEQ
,   TRT_REST_NAME
,   TRT_REGI
,   TRT_ADDR
,   TRT_FOOD_TYPE
,   TRT_REST_TEXT
,   TRT_INPUT_TY
,   TRT_INPUT_NM
,   TRT_INPUT_ID
,   TRT_INPUT_IP
,   TRT_INPUT_DT
,   TRT_MODER_TY
,   TRT_MODER_NM
,   TRT_MODER_ID
,   TRT_MODER_IP
,   TRT_MODER_DT
)
VALUES
    (   TRT_SEQ.nextval
    ,   '식당'
    ,   '서울'
    ,   '서울시 서대문구 연세로20길 7'
    ,   '양식'
    ,  	'맛있는 식당'
    ,   '#{trt_input_ty}'
    ,   '#{trt_input_nm}'
    ,   '#{trt_input_id}'
    ,   '#{trt_input_ip}'
    ,   SYSDATE
    ,   '#{trt_moder_ty}'
    ,   '#{trt_moder_nm}'
    ,   '#{trt_moder_id}'
    ,   '#{trt_moder_ip}'
    ,   SYSDATE
    );


CREATE TABLE TM_REST_MENU_TBL
(   TRMT_SEQ                NUMBER(20) NOT NULL
,   TRT_SEQ                 NUMBER(20) NOT NULL
,   TRMT_MENU_NAME          VARCHAR2(300) NOT NULL
,   TRMT_PRICE              NUMBER(20) NOT NULL
,   TRMT_MENU_TEXT          VARCHAR2(4000) NOT NULL
,   TRMT_INPUT_TY           VARCHAR2(300)
,   TRMT_INPUT_NM           VARCHAR2(300)
,   TRMT_INPUT_ID           VARCHAR2(300)
,   TRMT_INPUT_IP           VARCHAR2(300)
,   TRMT_INPUT_DT           DATE
,   TRMT_MODER_TY           VARCHAR2(300)
,   TRMT_MODER_NM           VARCHAR2(300)
,   TRMT_MODER_ID           VARCHAR2(300)
,   TRMT_MODER_IP           VARCHAR2(300)
,   TRMT_MODER_DT           DATE
,   PRIMARY KEY(TRMT_SEQ)
);

COMMENT ON TABLE TM_REST_MENU_TBL IS '메뉴테이블';
COMMENT ON COLUMN TM_REST_MENU_TBL.TRMT_SEQ IS '메뉴테이블 시퀀스';
COMMENT ON COLUMN TM_REST_MENU_TBL.TRT_SEQ IS '부모테이블 시퀀스';
COMMENT ON COLUMN TM_REST_MENU_TBL.TRMT_MENU_NAME IS '메뉴명';
COMMENT ON COLUMN TM_REST_MENU_TBL.TRMT_PRICE IS '가격';
COMMENT ON COLUMN TM_REST_MENU_TBL.TRMT_MENU_TEXT IS '내용';
COMMENT ON COLUMN TM_REST_MENU_TBL.TRMT_INPUT_TY IS '작성자 타입';
COMMENT ON COLUMN TM_REST_MENU_TBL.TRMT_INPUT_NM IS '작성자 이름';
COMMENT ON COLUMN TM_REST_MENU_TBL.TRMT_INPUT_ID IS '작성자 아이디';
COMMENT ON COLUMN TM_REST_MENU_TBL.TRMT_INPUT_DT IS '작자성일';
COMMENT ON COLUMN TM_REST_MENU_TBL.TRMT_INPUT_IP IS '작성자 아이피';
COMMENT ON COLUMN TM_REST_MENU_TBL.TRMT_MODER_TY IS '수정자 타입';
COMMENT ON COLUMN TM_REST_MENU_TBL.TRMT_MODER_NM IS '수정자 이름';
COMMENT ON COLUMN TM_REST_MENU_TBL.TRMT_MODER_ID IS '수정자 아이디';
COMMENT ON COLUMN TM_REST_MENU_TBL.TRMT_MODER_DT IS '수성일';
COMMENT ON COLUMN TM_REST_MENU_TBL.TRMT_MODER_IP IS '수정자 아이피';

CREATE SEQUENCE "TM_TRMT_SEQ" MINVALUE 1 MAXVALUE 999999999999999999999999 INCREMENT BY 1 START WITH 1000000 NOCACHE NOORDER NOCYCLE;


INSERT INTO TM_REST_MENU_TBL
(   TRMT_SEQ
,   TRT_SEQ
,   TRMT_MENU_NAME
,   TRMT_PRICE
,   TRMT_MENU_TEXT
,   TRMT_INPUT_TY
,   TRMT_INPUT_NM
,   TRMT_INPUT_ID
,   TRMT_INPUT_IP
,   TRMT_INPUT_DT
)
VALUES
    (   TM_TRMT_SEQ.nextval
    ,   1000038
    ,   '서울시 서대문구 연세로20길 7'
    ,   9000
    ,  	'맛있는 식당'
    ,   'master'
    ,   '마스터'
    ,   'master'
    ,   '192.168.0.1'
    ,   SYSDATE
    );






CREATE TABLE TM_REST_STAR_TBL
(   TRST_SEQ                NUMBER(20) NOT NULL
,   TRST_PARENT_SEQ         NUMBER(20) NOT NULL
,   TRST_PARENT_TYPE        VARCHAR2(100) NOT NULL
,   TRST_STAR_SCORE         NUMBER(10) NOT NULL
,   TRST_TODAY_YMD          DATE
,   TRST_INPUT_TY           VARCHAR2(300)
,   TRST_INPUT_NM           VARCHAR2(300)
,   TRST_INPUT_ID           VARCHAR2(300)
,   TRST_INPUT_IP           VARCHAR2(300)
,   TRST_INPUT_DT           DATE
,   TRST_MODER_TY           VARCHAR2(300)
,   TRST_MODER_NM           VARCHAR2(300)
,   TRST_MODER_ID           VARCHAR2(300)
,   TRST_MODER_IP           VARCHAR2(300)
,   TRST_MODER_DT           DATE
,   PRIMARY KEY(TRST_SEQ)
);

COMMENT ON TABLE TM_REST_STAR_TBL IS '별점 테이블';
COMMENT ON COLUMN TM_REST_STAR_TBL.TRST_SEQ IS '별점 테이블시퀀스';
COMMENT ON COLUMN TM_REST_STAR_TBL.TRST_PARENT_SEQ IS '부모시퀀스';
COMMENT ON COLUMN TM_REST_STAR_TBL.TRST_PARENT_TYPE IS '부모타입';
COMMENT ON COLUMN TM_REST_STAR_TBL.TRST_STAR_SCORE IS '별점';
COMMENT ON COLUMN TM_REST_STAR_TBL.TRST_TODAY_YMD IS '먹은일자';

COMMENT ON COLUMN TM_REST_STAR_TBL.TRST_INPUT_TY IS '작성자 타입';
COMMENT ON COLUMN TM_REST_STAR_TBL.TRST_INPUT_NM IS '작성자 이름';
COMMENT ON COLUMN TM_REST_STAR_TBL.TRST_INPUT_ID IS '작성자 아이디';
COMMENT ON COLUMN TM_REST_STAR_TBL.TRST_INPUT_DT IS '작자성일';
COMMENT ON COLUMN TM_REST_STAR_TBL.TRST_INPUT_IP IS '작성자 아이피';
COMMENT ON COLUMN TM_REST_STAR_TBL.TRST_MODER_TY IS '수정자 타입';
COMMENT ON COLUMN TM_REST_STAR_TBL.TRST_MODER_NM IS '수정자 이름';
COMMENT ON COLUMN TM_REST_STAR_TBL.TRST_MODER_ID IS '수정자 아이디';
COMMENT ON COLUMN TM_REST_STAR_TBL.TRST_MODER_DT IS '수성일';
COMMENT ON COLUMN TM_REST_STAR_TBL.TRST_MODER_IP IS '수정자 아이피';

CREATE SEQUENCE "TM_TRST_SEQ" MINVALUE 1 MAXVALUE 999999999999999999999999 INCREMENT BY 1 START WITH 1000000 NOCACHE NOORDER NOCYCLE;



INSERT INTO TM_REST_STAR_TBL
(   TRST_SEQ
,   TRST_PARENT_SEQ
,   TRST_PARENT_TYPE
,   TRST_STAR_SCORE
,   TRST_TODAY_YMD
,   TRST_INPUT_TY
,   TRST_INPUT_NM
,   TRST_INPUT_ID
,   TRST_INPUT_IP
,   TRST_INPUT_DT
)
VALUES
    (   TM_TRST_SEQ.nextval
    ,   1000038
    ,   'MENU'
    ,   5
    ,  	SYSDATE
    ,   'master'
    ,   '마스터'
    ,   'master'
    ,   '192.168.0.1'
    ,   SYSDATE
    );



CREATE TABLE TM_MEM_FILE_TBL
(   TMFT_SEQ                 NUMBER(20) NOT NULL PRIMARY KEY
,   TMFT_PARENT_SEQ          NUMBER(20) NOT NULL
,   TMFT_ORIGIN_FILE_NAME    VARCHAR2(300) NOT NULL
,   TMFT_CHANGE_FINE_NAME    VARCHAR2(300) NOT NULL
,   TMFT_INPUT_TY            VARCHAR2(300)
,   TMFT_INPUT_NM            VARCHAR2(300)
,   TMFT_INPUT_ID            VARCHAR2(300)
,   TMFT_INPUT_IP            VARCHAR2(300)
,   TMFT_INPUT_DT            DATE
,   TMFT_MODER_TY            VARCHAR2(300)
,   TMFT_MODER_NM            VARCHAR2(300)
,   TMFT_MODER_ID            VARCHAR2(300)
,   TMFT_MODER_IP            VARCHAR2(300)
,   TMFT_MODER_DT            DATE
,   PRIMARY KEY(TMFT_SEQ)
,   CONSTRAINT MEM_FILE_FK FOREIGN KEY (TMFT_PARENT_SEQ)
    REFERENCES TM_MEMBER_TBL (TMT_SEQ)
    ON DELETE CASCADE
);


COMMENT ON TABLE TM_MEM_FILE_TBL IS '회원 파일 테이블';
COMMENT ON COLUMN TM_MEM_FILE_TBL.TMFT_SEQ IS '파일 테이블시퀀스';
COMMENT ON COLUMN TM_MEM_FILE_TBL.TMFT_PARENT_SEQ IS '부모 테이블 시퀀스';
COMMENT ON COLUMN TM_MEM_FILE_TBL.TMFT_ORIGIN_FILE_NAME IS '파일 오리지널 이름';
COMMENT ON COLUMN TM_MEM_FILE_TBL.TMFT_CHANGE_FINE_NAME IS '파일 업로드 이름';

COMMENT ON COLUMN TM_MEM_FILE_TBL.TMFT_INPUT_TY IS '작성자 타입';
COMMENT ON COLUMN TM_MEM_FILE_TBL.TMFT_INPUT_NM IS '작성자 이름';
COMMENT ON COLUMN TM_MEM_FILE_TBL.TMFT_INPUT_ID IS '작성자 아이디';
COMMENT ON COLUMN TM_MEM_FILE_TBL.TMFT_INPUT_DT IS '작자성일';
COMMENT ON COLUMN TM_MEM_FILE_TBL.TMFT_INPUT_IP IS '작성자 아이피';
COMMENT ON COLUMN TM_MEM_FILE_TBL.TMFT_MODER_TY IS '수정자 타입';
COMMENT ON COLUMN TM_MEM_FILE_TBL.TMFT_MODER_NM IS '수정자 이름';
COMMENT ON COLUMN TM_MEM_FILE_TBL.TMFT_MODER_ID IS '수정자 아이디';
COMMENT ON COLUMN TM_MEM_FILE_TBL.TMFT_MODER_DT IS '수성일';
COMMENT ON COLUMN TM_MEM_FILE_TBL.TMFT_MODER_IP IS '수정자 아이피';

CREATE SEQUENCE "TM_TMFT_SEQ" MINVALUE 1 MAXVALUE 999999999999999999999999 INCREMENT BY 1 START WITH 1000000 NOCACHE NOORDER NOCYCLE;
ALTER TABLE TM_MEMBER_TBL ADD CONSTRAINT PK PRIMARY KEY(TMT_SEQ);
ALTER TABLE TM_MEMBER_TBL DROP CONSTRAINT;
SELECT * FROM USER_CONS_COLUMNS WHERE TABLE_NAME = 'TM_MEMBER_TBL';










CREATE TABLE TM_COMMON_FILE_TBL
(   TCFT_SEQ                 NUMBER(20) NOT NULL
,   TCFT_PARENT_SEQ          NUMBER(20) NOT NULL
,   TCFT_PARENT_TABLE_TYPE    VARCHAR2(300) NOT NULL
,   TCFT_ORIGIN_FILE_NAME    VARCHAR2(300) NOT NULL
,   TCFT_CHANGE_FINE_NAME    VARCHAR2(300) NOT NULL
,   TCFT_INPUT_TY            VARCHAR2(300)
,   TCFT_INPUT_NM            VARCHAR2(300)
,   TCFT_INPUT_ID            VARCHAR2(300)
,   TCFT_INPUT_IP            VARCHAR2(300)
,   TCFT_INPUT_DT            DATE
,   TCFT_MODER_TY            VARCHAR2(300)
,   TCFT_MODER_NM            VARCHAR2(300)
,   TCFT_MODER_ID            VARCHAR2(300)
,   TCFT_MODER_IP            VARCHAR2(300)
,   TCFT_MODER_DT            DATE
,   PRIMARY KEY(TCFT_SEQ)
,   CONSTRAINT TFB_FK FOREIGN KEY (TCFT_PARENT_SEQ) REFERENCES TM_FREE_BOARD (TFB_SEQ) ON DELETE CASCADE
,   CONSTRAINT TMT_FK FOREIGN KEY (TCFT_PARENT_SEQ) REFERENCES TM_MEMBER_TBL (TMT_SEQ) ON DELETE CASCADE
,   CONSTRAINT TRT_FK FOREIGN KEY (TCFT_PARENT_SEQ) REFERENCES TM_REST_TBL (TRT_SEQ) ON DELETE CASCADE
,   CONSTRAINT TRMT_FK FOREIGN KEY (TCFT_PARENT_SEQ) REFERENCES TM_REST_MENU_TBL (TRMT_SEQ) ON DELETE CASCADE
);

COMMENT ON TABLE TM_COMMON_FILE_TBL IS '공통 파일 테이블';
COMMENT ON COLUMN TM_COMMON_FILE_TBL.TCFT_SEQ IS '파일 테이블시퀀스';
COMMENT ON COLUMN TM_COMMON_FILE_TBL.TCFT_PARENT_SEQ IS '부모 테이블 시퀀스';
COMMENT ON COLUMN TM_COMMON_FILE_TBL.TCFT_PARENT_TABLE_TYPE IS '부모 테이블 타입';
COMMENT ON COLUMN TM_COMMON_FILE_TBL.TCFT_ORIGIN_FILE_NAME IS '파일 오리지널 이름';
COMMENT ON COLUMN TM_COMMON_FILE_TBL.TCFT_CHANGE_FINE_NAME IS '파일 업로드 이름';
COMMENT ON COLUMN TM_COMMON_FILE_TBL.TCFT_INPUT_TY IS '작성자 타입';
COMMENT ON COLUMN TM_COMMON_FILE_TBL.TCFT_INPUT_NM IS '작성자 이름';
COMMENT ON COLUMN TM_COMMON_FILE_TBL.TCFT_INPUT_ID IS '작성자 아이디';
COMMENT ON COLUMN TM_COMMON_FILE_TBL.TCFT_INPUT_DT IS '작자성일';
COMMENT ON COLUMN TM_COMMON_FILE_TBL.TCFT_INPUT_IP IS '작성자 아이피';
COMMENT ON COLUMN TM_COMMON_FILE_TBL.TCFT_MODER_TY IS '수정자 타입';
COMMENT ON COLUMN TM_COMMON_FILE_TBL.TCFT_MODER_NM IS '수정자 이름';
COMMENT ON COLUMN TM_COMMON_FILE_TBL.TCFT_MODER_ID IS '수정자 아이디';
COMMENT ON COLUMN TM_COMMON_FILE_TBL.TCFT_MODER_DT IS '수성일';
COMMENT ON COLUMN TM_COMMON_FILE_TBL.TCFT_MODER_IP IS '수정자 아이피';

CREATE SEQUENCE "TM_TCFT_SEQ" MINVALUE 1 MAXVALUE 999999999999999999999999 INCREMENT BY 1 START WITH 1000000 NOCACHE NOORDER NOCYCLE;



CREATE TABLE TM_REST_FILE_TBL
(   TRFT_SEQ                 NUMBER(20) NOT NULL
,   TRFT_PARENT_SEQ          NUMBER(20) NOT NULL
,   TRFT_PARENT_TABLE_TYPE    VARCHAR2(300) NOT NULL
,   TRFT_ORIGIN_FILE_NAME    VARCHAR2(300) NOT NULL
,   TRFT_CHANGE_FILE_NAME    VARCHAR2(300) NOT NULL
,   TRFT_INPUT_TY            VARCHAR2(300)
,   TRFT_INPUT_NM            VARCHAR2(300)
,   TRFT_INPUT_ID            VARCHAR2(300)
,   TRFT_INPUT_IP            VARCHAR2(300)
,   TRFT_INPUT_DT            DATE
,   TRFT_MODER_TY            VARCHAR2(300)
,   TRFT_MODER_NM            VARCHAR2(300)
,   TRFT_MODER_ID            VARCHAR2(300)
,   TRFT_MODER_IP            VARCHAR2(300)
,   TRFT_MODER_DT            DATE
,   PRIMARY KEY(TRFT_SEQ)
,   CONSTRAINT TRT_FK FOREIGN KEY (TRFT_PARENT_SEQ) REFERENCES TM_REST_TBL (TRT_SEQ) ON DELETE CASCADE
);

COMMENT ON TABLE TM_REST_FILE_TBL IS '공통 파일 테이블';
COMMENT ON COLUMN TM_REST_FILE_TBL.TRFT_SEQ IS '파일 테이블시퀀스';
COMMENT ON COLUMN TM_REST_FILE_TBL.TRFT_PARENT_SEQ IS '부모 테이블 시퀀스';
COMMENT ON COLUMN TM_REST_FILE_TBL.TRFT_PARENT_TABLE_TYPE IS '부모 테이블 타입';
COMMENT ON COLUMN TM_REST_FILE_TBL.TRFT_ORIGIN_FILE_NAME IS '파일 오리지널 이름';
COMMENT ON COLUMN TM_REST_FILE_TBL.TRFT_CHANGE_FILE_NAME IS '파일 업로드 이름';
COMMENT ON COLUMN TM_REST_FILE_TBL.TRFT_INPUT_TY IS '작성자 타입';
COMMENT ON COLUMN TM_REST_FILE_TBL.TRFT_INPUT_NM IS '작성자 이름';
COMMENT ON COLUMN TM_REST_FILE_TBL.TRFT_INPUT_ID IS '작성자 아이디';
COMMENT ON COLUMN TM_REST_FILE_TBL.TRFT_INPUT_DT IS '작자성일';
COMMENT ON COLUMN TM_REST_FILE_TBL.TRFT_INPUT_IP IS '작성자 아이피';
COMMENT ON COLUMN TM_REST_FILE_TBL.TRFT_MODER_TY IS '수정자 타입';
COMMENT ON COLUMN TM_REST_FILE_TBL.TRFT_MODER_NM IS '수정자 이름';
COMMENT ON COLUMN TM_REST_FILE_TBL.TRFT_MODER_ID IS '수정자 아이디';
COMMENT ON COLUMN TM_REST_FILE_TBL.TRFT_MODER_DT IS '수성일';
COMMENT ON COLUMN TM_REST_FILE_TBL.TRFT_MODER_IP IS '수정자 아이피';

CREATE SEQUENCE "TM_TRFT_SEQ" MINVALUE 1 MAXVALUE 999999999999999999999999 INCREMENT BY 1 START WITH 1000000 NOCACHE NOORDER NOCYCLE;




CREATE TABLE TM_REST_MENU_KEY_WORD
(   TRMKW_SEQ                NUMBER(20) NOT NULL
,   TRMKW_PARENT_SEQ         NUMBER(20) NOT NULL
,   TRMKW_KEY_WORD            VARCHAR2(300)
,   TRMKW_INPUT_TY            VARCHAR2(300)
,   TRMKW_INPUT_NM            VARCHAR2(300)
,   TRMKW_INPUT_ID            VARCHAR2(300)
,   TRMKW_INPUT_IP            VARCHAR2(300)
,   TRMKW_INPUT_DT            DATE
,   TRMKW_MODER_TY            VARCHAR2(300)
,   TRMKW_MODER_NM            VARCHAR2(300)
,   TRMKW_MODER_ID            VARCHAR2(300)
,   TRMKW_MODER_IP            VARCHAR2(300)
,   TRMKW_MODER_DT            DATE
,   PRIMARY KEY(TRMKW_SEQ)
,   CONSTRAINT TRMKW_FK FOREIGN KEY (TRMKW_PARENT_SEQ) REFERENCES TM_REST_MENU_TBL (TRMT_SEQ) ON DELETE CASCADE
);

COMMENT ON TABLE TM_REST_MENU_KEY_WORD IS '키워드 테이블';
COMMENT ON COLUMN TM_REST_MENU_KEY_WORD.TRMKW_SEQ IS '키워드 테이블시퀀스';
COMMENT ON COLUMN TM_REST_MENU_KEY_WORD.TRMKW_KEY_WORD IS '키워드 부모 테이블 시퀀스';
COMMENT ON COLUMN TM_REST_MENU_KEY_WORD.TRMKW_INPUT_TY IS '작성자 타입';
COMMENT ON COLUMN TM_REST_MENU_KEY_WORD.TRMKW_INPUT_NM IS '작성자 이름';
COMMENT ON COLUMN TM_REST_MENU_KEY_WORD.TRMKW_INPUT_ID IS '작성자 아이디';
COMMENT ON COLUMN TM_REST_MENU_KEY_WORD.TRMKW_INPUT_DT IS '작자성일';
COMMENT ON COLUMN TM_REST_MENU_KEY_WORD.TRMKW_INPUT_IP IS '작성자 아이피';
COMMENT ON COLUMN TM_REST_MENU_KEY_WORD.TRMKW_MODER_TY IS '수정자 타입';
COMMENT ON COLUMN TM_REST_MENU_KEY_WORD.TRMKW_MODER_NM IS '수정자 이름';
COMMENT ON COLUMN TM_REST_MENU_KEY_WORD.TRMKW_MODER_ID IS '수정자 아이디';
COMMENT ON COLUMN TM_REST_MENU_KEY_WORD.TRMKW_MODER_DT IS '수성일';
COMMENT ON COLUMN TM_REST_MENU_KEY_WORD.TRMKW_MODER_IP IS '수정자 아이피';

CREATE SEQUENCE "TM_TRMKW_SEQ" MINVALUE 1 MAXVALUE 999999999999999999999999 INCREMENT BY 1 START WITH 1000000 NOCACHE NOORDER NOCYCLE;
