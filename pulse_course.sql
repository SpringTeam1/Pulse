drop sequence seqCourse;
create sequence seqCourse;

DROP TABLE tblCourse;

CREATE TABLE tblCourse (
	courseSeq	    NUMBER		        NOT NULL,
	courseName	    VARCHAR2(100)		NOT NULL,
	accountId	    VARCHAR2(100)		NOT NULL,
	courseApproval	VARCHAR2(20)	    DEFAULT '대기'	NOT NULL,
	description 	VARCHAR2(2000)		NULL,
	courseLength	NUMBER		        NOT NULL,
	startAddress	VARCHAR2(300)		NOT NULL,
	endAddress	    VARCHAR2(300)		NOT NULL,
	trackData	    CLOB		        NOT NULL,
	regdate	        DATE	            DEFAULT sysdate	NOT NULL
);

ALTER TABLE tblCourse ADD CONSTRAINT "PK_TBLCOURSE" PRIMARY KEY (courseSeq);

ALTER TABLE tblCourse ADD CONSTRAINT "FK_tblAccountInfo_TO_tblCourse_1"
    FOREIGN KEY (accountId) REFERENCES tblAccountInfo (accountId);

ALTER TABLE tblCourse ADD CONSTRAINT "CHK_course_trackData" CHECK (trackData IS JSON);

select * from tblCourse order by courseSeq desc;

-- 더미데이터
insert into tblCourse
    (courseSeq, courseName, accountId, courseApproval, description, courseLength, startAddress, endAddress, trackData, regdate)
VALUES
    (seqCourse.nextval,
     '테스트2',
     'hong',
     default,
     '코스 insert 테스트',
     2.0,
     '서울시 강남구 역삼동',
     '서울시 강남구 대치동',
     '[{"lat":37.5013068,"lon":127.0396567},{"lat":37.502124,"lon":127.042231},{"lat":37.502398,"lon":127.043423}]',
     default
);

INSERT INTO tblCourse
    (courseSeq, courseName, accountId, courseApproval, description, courseLength, startAddress, endAddress, trackData, regdate)
VALUES
    (seqCourse.nextval,
     '여의도 공원 한 바퀴',
     'hong',
     '승인',
     '여의도 공원 산책 코스입니다.',
     2.5,
     '서울 영등포구 여의도공원로 68',
     '서울 영등포구 여의도공원로 68',
     '[{"lat":37.5250,"lon":126.9180},{"lat":37.5265,"lon":126.9200},{"lat":37.5240,"lon":126.9220},{"lat":37.5250,"lon":126.9180}]',
     default
);

INSERT INTO tblCourse
    (courseSeq, courseName, accountId, courseApproval, description, courseLength, startAddress, endAddress, trackData, regdate)
VALUES
    (seqCourse.nextval,
     '잠실 한강공원 런',
     'hong',
     '승인',
     '잠실 한강공원에서 잠실대교 남단까지',
     1.2,
     '서울 송파구 한가람로 65',
     '서울 송파구 신천동 (잠실대교 남단)',
     '[{"lat":37.5140,"lon":127.0850},{"lat":37.5135,"lon":127.0900},{"lat":37.5130,"lon":127.0950}]',
     default
);

select * from tblCourse;


commit;