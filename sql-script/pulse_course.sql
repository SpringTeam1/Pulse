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

select * from tblCourse order by courseSeq desc;
select * from tblCourse where courseSeq=12;
select
    courseSeq, courseName, round(courseLength, 2) as courseLength, startAddress, endAddress, accountId, 0 as favoriteCount
from tblCourse
where courseApproval='승인'
order by courseSeq desc;

update tblCourse set courseApproval ='승인' where courseSeq>9;
update tblCourse set courseName = '종로 추천 코스' where courseSeq=22;
update tblCourse set courseApproval ='승인' where courseSeq=23;

commit;



-- 즐겨찾기 테이블(tblScrapCourse)
create sequence seqScrapCourse;
CREATE TABLE tblScrapCourse (
	scrapCourseSeq	NUMBER		NOT NULL,
	courseSeq	    NUMBER		NOT NULL,
	accountId	    VARCHAR2(100)		NOT NULL
);

ALTER TABLE tblScrapCourse ADD CONSTRAINT "PK_TBLSCRAPCOURSE" PRIMARY KEY (scrapCourseSeq);

ALTER TABLE tblScrapCourse ADD CONSTRAINT "FK_tblCourse_TO_tblScrapCourse_1" FOREIGN KEY (courseSeq)
REFERENCES tblCourse (courseSeq);

ALTER TABLE tblScrapCourse ADD CONSTRAINT "FK_tblAccountInfo_TO_tblScrapCourse_1" FOREIGN KEY (accountId)
REFERENCES tblAccountInfo (accountId);

insert into tblScrapCourse(scrapCourseSeq, courseSeq, accountId) VALUES (seqScrapCourse.nextval, 23, 'wjswoaks123');
insert into tblScrapCourse(scrapCourseSeq, courseSeq, accountId) VALUES (seqScrapCourse.nextval, 22, 'wjswoaks123');
insert into tblScrapCourse(scrapCourseSeq, courseSeq, accountId) VALUES (seqScrapCourse.nextval, 20, 'wjswoaks123');
insert into tblScrapCourse(scrapCourseSeq, courseSeq, accountId) VALUES (seqScrapCourse.nextval, 19, 'wjswoaks123');
insert into tblScrapCourse(scrapCourseSeq, courseSeq, accountId) VALUES (seqScrapCourse.nextval, 18, 'wjswoaks123');
insert into tblScrapCourse(scrapCourseSeq, courseSeq, accountId) VALUES (seqScrapCourse.nextval, 17, 'wjswoaks123');

insert into tblScrapCourse(scrapCourseSeq, courseSeq, accountId) VALUES (seqScrapCourse.nextval, 23, 'test2@naver.com');
insert into tblScrapCourse(scrapCourseSeq, courseSeq, accountId) VALUES (seqScrapCourse.nextval, 22, 'test2@naver.com');
insert into tblScrapCourse(scrapCourseSeq, courseSeq, accountId) VALUES (seqScrapCourse.nextval, 18, 'test2@naver.com');
insert into tblScrapCourse(scrapCourseSeq, courseSeq, accountId) VALUES (seqScrapCourse.nextval, 17, 'test2@naver.com');

insert into tblScrapCourse(scrapCourseSeq, courseSeq, accountId) VALUES (seqScrapCourse.nextval, 23, 'test@gmail.com');
insert into tblScrapCourse(scrapCourseSeq, courseSeq, accountId) VALUES (seqScrapCourse.nextval, 22, 'test@gmail.com');
insert into tblScrapCourse(scrapCourseSeq, courseSeq, accountId) VALUES (seqScrapCourse.nextval, 20, 'test@gmail.com');
insert into tblScrapCourse(scrapCourseSeq, courseSeq, accountId) VALUES (seqScrapCourse.nextval, 19, 'test@gmail.com');
insert into tblScrapCourse(scrapCourseSeq, courseSeq, accountId) VALUES (seqScrapCourse.nextval, 18, 'test@gmail.com');
insert into tblScrapCourse(scrapCourseSeq, courseSeq, accountId) VALUES (seqScrapCourse.nextval, 17, 'test@gmail.com');

select courseSeq, count(*) as scrapCount from tblScrapCourse group by courseSeq order by courseSeq asc;
commit;