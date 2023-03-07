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
  sem INTEGER NOT NULL,
  batch varchar(200) NOT NULL,

  PRIMARY KEY(CourseId,year),
  FOREIGN KEY (CourseId) REFERENCES CourseCatalog(CourseId)
);

 insert into users values('Admin','Admin','Admin',2008);
 insert into info values(2022,1,1);





