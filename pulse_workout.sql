DROP SEQUENCE seqWorkout;
DROP TABLE tblWorkout;

CREATE SEQUENCE seqWorkout;
CREATE TABLE tblWorkout (
	workoutSeq	        NUMBER			NOT NULL, -- 운동 기록 번호(PK)
	accountId			VARCHAR2(100)	NOT NULL, -- 아이디(FK)
	trackData			CLOB			NOT NULL, -- JSON 파일(.gpx)
	totalTime			NUMBER			NOT NULL, -- 운동 시간
	totalDistance		NUMBER			NOT NULL, -- 운동 거리
	totalCalories		NUMBER			NOT NULL, -- 소모 칼로리
	workoutDate			DATE			NOT NULL, -- 운동 날짜
	avgPace				NUMBER			NOT NULL, -- 평균 페이스
	attachment			VARCHAR2(200)	NULL,     -- 첨부파일(사진 등)
	exerciseComment		VARCHAR2(500)	NULL      -- 사용자 코멘트(운동 기록에 대한)
);

ALTER TABLE tblWorkout ADD CONSTRAINT "PK_TBLWORKOUT" PRIMARY KEY (workoutSeq);

ALTER TABLE tblWorkout ADD CONSTRAINT "FK_tblAccountInfo_TO_tblWorkout_1"
FOREIGN KEY (accountId)
REFERENCES tblAccountInfo (accountId);
select * from tblWorkout;