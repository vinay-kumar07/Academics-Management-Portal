create table Users
(
  userId varchar(25) NOT NULL,
  userType varchar(25) NOT NULL,
  Password varchar(15) NOT NULL,
  enrollyear INTEGER NOT NULL,

  PRIMARY KEY(userId)
);

create table info
(
  curryear INTEGER NOT NULL,
  cursem INTEGER NOT NULL,
  addwithdraw INTEGER NOT NULL,

  PRIMARY KEY(curryear,cursem,addwithdraw)
);

create table CourseCatalog
(
  courseId varchar(50) NOT NULL,
  L INTEGER NOT NULL,
  T INTEGER NOT NULL,
  P INTEGER NOT NULL,
  Prereq varchar(200) NOT NULL,
  core varchar(200) NOT NULL,
  elective varchar(200) NOT NULL,

  PRIMARY KEY(courseId)
);

create table CourseOffering
(
  CourseId varchar(50) NOT NULL,
  facultyid varchar(50) NOT NULL,
  cgcriteria float NOT NULL,
  year INTEGER NOT NULL,
  batch varchar(200) NOT NULL,

  PRIMARY KEY(CourseId,year),
  FOREIGN KEY (CourseId) REFERENCES CourseCatalog(CourseId)
);

-- insert into users values('2020csb1141','Student','123');
-- insert into users values('2020csb1135','Student','123');
-- insert into users values('CSF1990','Instructor','123');
-- insert into users values('Admin','Admin','123');

-- insert into CourseCatalog values('CS304',3,0,0,'CS201,GE103');
-- insert into CourseCatalog values('CS305',3,0,0,'CS301');
-- insert into CourseCatalog values('CS306',3,0,0,'');

-- insert into CourseOffering values('dr a',7.2,'CS304');
-- insert into CourseOffering values('dr b',7.3,'CS305');
-- insert into CourseOffering values('dr c',7.0,'CS306');

-- select * from users;
-- select * from CourseCatalog;
-- select * from CourseOffering;

-- create table Student2020csb1141(CourseId varchar(50) NOT NULL, year INTEGER NOT NULL, sem INTEGER NOT NULL, grade INTEGER NOT NULL, PRIMARY KEY(CourseId,year,sem));

-- select * from Student2020csb1141;




