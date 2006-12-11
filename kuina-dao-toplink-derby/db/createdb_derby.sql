DROP TABLE MANYTOONEOWNER;
DROP TABLE ONETOMANYINVERSE;
DROP TABLE ONETOONEOWNER;
DROP TABLE ONETOONEINVERSE;
DROP TABLE MANYTOMANYOWNER_MANYTOMANYINVERSE;
DROP TABLE MANYTOMANYOWNER;
DROP TABLE MANYTOMANYINVERSE;
DROP TABLE SEQUENCE;

CREATE TABLE OneToManyInverse (Id INTEGER NOT NULL PRIMARY KEY, Name VARCHAR(255), Version INTEGER DEFAULT 1);
CREATE TABLE ManyToOneOwner (Id INTEGER NOT NULL PRIMARY KEY, Name VARCHAR(255), Height INTEGER, Weight INTEGER, Email VARCHAR(255), HireFiscalYear INTEGER, Birthday DATE, Birthtime TIME, BirthTimestamp TIMESTAMP, EmploymentDate DATE, WeddingDay DATE, BloodType VARCHAR(255), EmployeeStatus INTEGER, SalaryRate VARCHAR(255), Retired SMALLINT DEFAULT 0, Version INTEGER DEFAULT 1, OneToManyInverse_Id INTEGER NOT NULL, SubOneToManyInverse_Id INTEGER NOT NULL, FOREIGN KEY (OneToManyInverse_Id) REFERENCES OneToManyInverse (Id), FOREIGN KEY (SubOneToManyInverse_Id) REFERENCES OneToManyInverse (Id));
CREATE TABLE OneToOneInverse (Id INTEGER NOT NULL PRIMARY KEY, Name VARCHAR(255), Version INTEGER DEFAULT 1);
CREATE TABLE OneToOneOwner (Id INTEGER NOT NULL PRIMARY KEY, Name VARCHAR(255), Height INTEGER, Weight INTEGER, Email VARCHAR(255), HireFiscalYear INTEGER, Birthday DATE,  Birthtime TIME, BirthTimestamp TIMESTAMP, EmploymentDate DATE, WeddingDay DATE, BloodType VARCHAR(255), EmployeeStatus INTEGER, SalaryRate VARCHAR(255), Retired SMALLINT DEFAULT 0, Version INTEGER DEFAULT 1, OneToOneInverse_Id INTEGER NOT NULL, UNIQUE (OneToOneInverse_Id), FOREIGN KEY (OneToOneInverse_Id) REFERENCES OneToOneInverse (Id));
CREATE TABLE ManyToManyInverse (Id INTEGER NOT NULL PRIMARY KEY, Name VARCHAR(255), Version INTEGER DEFAULT 1);
CREATE TABLE ManyToManyOwner (Id INTEGER NOT NULL PRIMARY KEY, Name VARCHAR(255), Height INTEGER, Weight INTEGER, Email VARCHAR(255), HireFiscalYear INTEGER, Birthday DATE,  Birthtime TIME, BirthTimestamp TIMESTAMP, EmploymentDate DATE, WeddingDay DATE, BloodType VARCHAR(255), EmployeeStatus INTEGER, SalaryRate VARCHAR(255), Retired SMALLINT DEFAULT 0, Version INTEGER DEFAULT 1);
CREATE TABLE ManyToManyOwner_ManyToManyInverse (ManyToManyOwners_Id INTEGER NOT NULL,ManyToManyInverses_Id INTEGER NOT NULL, PRIMARY KEY (ManyToManyOwners_Id, ManyToManyInverses_Id), FOREIGN KEY (ManyToManyOwners_Id) REFERENCES ManyToManyOwner (Id), FOREIGN KEY (ManyToManyInverses_Id) REFERENCES ManyToManyInverse (Id));

CREATE TABLE SEQUENCE (SEQ_NAME VARCHAR(50) NOT NULL, SEQ_COUNT DECIMAL, PRIMARY KEY (SEQ_NAME));

INSERT INTO OneToManyInverse VALUES (1,'Business', 1);
INSERT INTO OneToManyInverse VALUES (2,'General Administration', 1);
INSERT INTO OneToManyInverse VALUES (3,'Personnel', 1);
INSERT INTO OneToManyInverse VALUES (4,'Account', 1);
INSERT INTO OneToManyInverse VALUES (5,'Sales', 1);
INSERT INTO OneToManyInverse VALUES (6,'Purchase', 1);
INSERT INTO SEQUENCE(SEQ_NAME, SEQ_COUNT) values ('OneToManyInverse_Id_Sequence2', 6);

INSERT INTO MANYTOONEOWNER VALUES (1,'simagoro',168,72,'simagoro@nekoyasudo',NULL,'1953-10-01','01:00:00','1953-10-01 01:00:00.0','1983-04-01',NULL,'A',0,'JUNIOR',0,1,1,2);
INSERT INTO MANYTOONEOWNER VALUES (2,'gochin',161,60,'gochin@nekoyasudo',1984,'1950-12-25','02:00:00','1950-12-25 02:00:00.0','1984-04-01',NULL,'B',1,'JUNIOR',1,1,1,2);
INSERT INTO MANYTOONEOWNER VALUES (3,'maki',155,52,'maki@nekoyasudo',1984,'1955-08-16','03:00:00','1955-08-16 03:00:00.0','1984-04-01','1998-01-15','O',2,'MANAGER',0,1,1,2);
INSERT INTO MANYTOONEOWNER VALUES (4,'maru',158,45,'maru@nekoyasudo',1984,'1951-01-12','04:00:00','1951-01-12 04:00:00.0','1984-04-01',NULL,'AB',0,'JUNIOR',0,1,1,2);
INSERT INTO MANYTOONEOWNER VALUES (5,'michiro',170,70,'michiro@nekoyasudo',1984,'1962-02-14','05:00:00','1962-02-14 05:00:00.0','1984-04-01',NULL,'A',1,'SENIOR',0,1,1,2);
INSERT INTO MANYTOONEOWNER VALUES (6,'coo',173,76,'coo@nekoyasudo',1984,'1961-09-15','06:00:00','1961-09-15 06:00:00.0','1984-04-01',NULL,'A',2,'JUNIOR',0,1,1,2);
INSERT INTO MANYTOONEOWNER VALUES (7,'sara',162,50,'sara@nekoyasudo',1985,'1957-10-28','07:00:00','1957-10-28 07:00:00.0','1985-04-01',NULL,'A',0,'JUNIOR',0,1,1,2);
INSERT INTO MANYTOONEOWNER VALUES (8,'minami',153,42,'minami@nekoyasudo',1985,'1959-09-03','08:00:00','1959-09-03 08:00:00.0','1985-04-01',NULL,'O',1,'JUNIOR',0,1,1,2);
INSERT INTO MANYTOONEOWNER VALUES (9,'prin',148,48,'prin@nekoyasudo',1985,'1965-03-14','09:00:00','1965-03-14 09:00:00.0','1985-04-01',NULL,'B',2,'JUNIOR',0,1,1,2);
INSERT INTO MANYTOONEOWNER VALUES (10,'pko',156,52,'pko@nekoyasudo',1985,'1968-11-13','10:00:00','1968-11-13 10:00:00.0','1985-04-01',NULL,'O',0,'SENIOR',0,1,1,2);
INSERT INTO MANYTOONEOWNER VALUES (11,'goma',180,78,'goma@nekoyasudo',1985,'1968-04-15','01:00:00','1968-04-15 01:00:00.0','1985-04-01',NULL,'O',1,'JUNIOR',0,1,2,3);
INSERT INTO MANYTOONEOWNER VALUES (12,'panda',185,95,'panda@nekoyasudo',1985,'1968-06-01','02:00:00','1968-06-01 02:00:00.0','1985-04-01','1988-09-13','B',2,'JUNIOR',0,1,2,3);
INSERT INTO MANYTOONEOWNER VALUES (13,'nekomaru',172,80,'nekomaru@nekoyasudo',1986,'1969-01-15','03:00:00','1969-01-15 03:00:00.0','1986-04-01','1988-08-30','B',0,'MANAGER',0,1,3,4);
INSERT INTO MANYTOONEOWNER VALUES (14,'nyantaro',178,58,'nyantaro@nekoyasudo',1986,'1969-02-03','04:00:00','1969-02-03 04:00:00.0','1986-04-01',NULL,'B',1,'JUNIOR',0,1,3,4);
INSERT INTO MANYTOONEOWNER VALUES (15,'monchi',169,55,'monchi@nekoyasudo',1988,'1970-05-06','05:00:00','1970-05-06 05:00:00.0','1988-04-01',NULL,'A',2,'SENIOR',0,1,3,4);
INSERT INTO MANYTOONEOWNER VALUES (16,'piyo',155,62,'piyo@nekoyasudo',1988,'1970-07-14','06:00:00','1970-07-14 06:00:00.0','1988-04-01',NULL,'A',0,'JUNIOR',0,1,4,5);
INSERT INTO MANYTOONEOWNER VALUES (17,'rasukal',165,51,'rasukal@nekoyasudo',1989,'1971-04-10','07:00:00','1971-04-10 07:00:00.0','1989-04-01',NULL,'AB',1,'JUNIOR',0,1,4,5);
INSERT INTO MANYTOONEOWNER VALUES (18,'kuma',190,115,'kuma@nekoyasudo',1989,'1967-03-03','08:00:00','1967-03-03 08:00:00.0','1989-04-01',NULL,'O',2,'JUNIOR',0,1,4,5);
INSERT INTO MANYTOONEOWNER VALUES (19,'gon',176,78,'gon@nekoyasudo',1990,'1972-11-16','09:00:00','1972-11-16 09:00:00.0','1990-04-01',NULL,'O',0,'JUNIOR',0,1,4,5);
INSERT INTO MANYTOONEOWNER VALUES (20,'q',169,56,'q@nekoyasudo',1991,'1973-05-25','10:00:00','1973-05-25 10:00:00.0','1991-04-01','1993-03-03','O',1,'SENIOR',0,1,5,6);
INSERT INTO MANYTOONEOWNER VALUES (21,'tasuke',164,50,'tasuke@nekoyasudo',1993,'1975-10-03','01:00:00','1975-10-03 01:00:00.0','1993-04-01',NULL,'B',2,'JUNIOR',0,1,5,6);
INSERT INTO MANYTOONEOWNER VALUES (22,'tonton',155,45,'tonton@nekoyasudo',1993,'1975-12-25','02:00:00','1975-12-25 02:00:00.0','1993-04-01',NULL,'B',0,'JUNIOR',0,1,5,6);
INSERT INTO MANYTOONEOWNER VALUES (23,'ma',158,44,'ma@nekoyasudo',1994,'1976-04-03','03:00:00','1976-04-03 03:00:00.0','1994-04-01','1997-03-15','A',1,'MANAGER',0,1,5,6);
INSERT INTO MANYTOONEOWNER VALUES (24,'sary',149,38,'sary@nekoyasudo',1996,'1978-02-02','04:00:00','1978-02-02 04:00:00.0','1996-04-01',NULL,'A',2,'JUNIOR',0,1,5,6);
INSERT INTO MANYTOONEOWNER VALUES (25,'usa',161,43,'usa@nekoyasudo',1996,'1978-08-08','05:00:00','1978-08-08 05:00:00.0','1996-04-01',NULL,'A',0,'SENIOR',0,1,6,1);
INSERT INTO MANYTOONEOWNER VALUES (26,'uta',170,63,'uta@nekoyasudo',1998,'1980-03-26','06:00:00','1980-03-26 06:00:00.0','1998-04-01',NULL,'A',1,'JUNIOR',0,1,6,1);
INSERT INTO MANYTOONEOWNER VALUES (27,'roly',147,40,'roly@nekoyasudo',2000,'1982-05-26','07:00:00','1982-05-26 07:00:00.0','2000-04-01',NULL,'A',2,'JUNIOR',0,1,6,1);
INSERT INTO MANYTOONEOWNER VALUES (28,'mikel',160,49,'mikel@nekoyasudo',2001,'1983-06-30','08:00:00','1983-06-30 08:00:00.0','2001-04-01',NULL,'AB',0,'JUNIOR',0,1,6,1);
INSERT INTO MANYTOONEOWNER VALUES (29,'su',163,52,'su@nekoyasudo',2003,'1985-09-08','09:00:00','1985-09-08 09:00:00.0','2003-04-01',NULL,'O',1,'JUNIOR',0,1,6,1);
INSERT INTO MANYTOONEOWNER VALUES (30,'miya',168,51,'miya@nekoyasudo',2004,'1986-07-07','10:00:00','1986-07-07 10:00:00.0','2004-04-01',NULL,'B',2,'SENIOR',0,1,6,1);
INSERT INTO SEQUENCE(SEQ_NAME, SEQ_COUNT) values ('ManyToOneOwner_Id_Sequence2', 30);

INSERT INTO OneToOneInverse VALUES (1,'Business', 1);
INSERT INTO OneToOneInverse VALUES (2,'General Administration', 1);
INSERT INTO OneToOneInverse VALUES (3,'Personnel', 1);
INSERT INTO OneToOneInverse VALUES (4,'Account', 1);
INSERT INTO OneToOneInverse VALUES (5,'Sales', 1);
INSERT INTO OneToOneInverse VALUES (6,'Purchase', 1);
INSERT INTO SEQUENCE(SEQ_NAME, SEQ_COUNT) values ('OneToOneInverse_Id_Sequence2', 6);

INSERT INTO OneToOneOwner VALUES (1,'simagoro',168,72,'simagoro@nekoyasudo',NULL,'1953-10-01','01:00:00','1953-10-01 01:00:00.0',NULL,NULL,'A',0,'JUNIOR',0,1,1);
INSERT INTO OneToOneOwner VALUES (2,'gochin',161,60,'gochin@nekoyasudo',1984,'1950-12-25','02:00:00','1950-12-25 02:00:00.0','1984-04-01',NULL,'B',1,'JUNIOR',1,1,2);
INSERT INTO OneToOneOwner VALUES (3,'maki',155,52,'maki@nekoyasudo',1984,'1955-08-16','03:00:00','1955-08-16 03:00:00.0','1984-04-01','1998-01-15','O',2,'MANAGER',0,1,3);
INSERT INTO OneToOneOwner VALUES (4,'maru',158,45,'maru@nekoyasudo',1984,'1951-01-12','04:00:00','1951-01-12 04:00:00.0','1984-04-01',NULL,'AB',0,'JUNIOR',0,1,4);
INSERT INTO OneToOneOwner VALUES (5,'michiro',170,70,'michiro@nekoyasudo',1984,'1962-02-14','05:00:00','1962-02-14 05:00:00.0','1984-04-01',NULL,'A',1,'SENIOR',0,1,5);
INSERT INTO OneToOneOwner VALUES (6,'coo',173,76,'coo@nekoyasudo',1984,'1961-09-15','06:00:00','1961-09-15 06:00:00.0','1984-04-01',NULL,'A',2,'JUNIOR',0,1,6);
INSERT INTO SEQUENCE(SEQ_NAME, SEQ_COUNT) values ('OneToOneOwner_Id_Sequence2', 6);

INSERT INTO ManyToManyInverse VALUES (1,'Business', 1);
INSERT INTO ManyToManyInverse VALUES (2,'General Administration', 1);
INSERT INTO ManyToManyInverse VALUES (3,'Personnel', 1);
INSERT INTO ManyToManyInverse VALUES (4,'Account', 1);
INSERT INTO ManyToManyInverse VALUES (5,'Sales', 1);
INSERT INTO ManyToManyInverse VALUES (6,'Purchase', 1);
INSERT INTO SEQUENCE(SEQ_NAME, SEQ_COUNT) values ('ManyToManyInverse_Id_Sequence2', 6);

INSERT INTO ManyToManyOwner VALUES (1,'simagoro',168,72,'simagoro@nekoyasudo',NULL,'1953-10-01','01:00:00','1953-10-01 01:00:00.0',NULL,NULL,'A',0,'JUNIOR',0,1);
INSERT INTO ManyToManyOwner VALUES (2,'gochin',161,60,'gochin@nekoyasudo',1984,'1950-12-25','02:00:00','1950-12-25 02:00:00.0','1984-04-01',NULL,'B',1,'JUNIOR',1,1);
INSERT INTO ManyToManyOwner VALUES (3,'maki',155,52,'maki@nekoyasudo',1984,'1955-08-16','03:00:00','1955-08-16 03:00:00.0','1984-04-01','1998-01-15','O',2,'MANAGER',0,1);
INSERT INTO ManyToManyOwner VALUES (4,'maru',158,45,'maru@nekoyasudo',1984,'1951-01-12','04:00:00','1951-01-12 04:00:00.0','1984-04-01',NULL,'AB',0,'JUNIOR',0,1);
INSERT INTO ManyToManyOwner VALUES (5,'michiro',170,70,'michiro@nekoyasudo',1984,'1962-02-14','05:00:00','1962-02-14 05:00:00.0','1984-04-01',NULL,'A',1,'SENIOR',0,1);
INSERT INTO ManyToManyOwner VALUES (6,'coo',173,76,'coo@nekoyasudo',1984,'1961-09-15','06:00:00','1961-09-15 06:00:00.0','1984-04-01',NULL,'A',2,'JUNIOR',0,1);
INSERT INTO SEQUENCE(SEQ_NAME, SEQ_COUNT) values ('ManyToManyOwner_Id_Sequence2', 6);

INSERT INTO ManyToManyOwner_ManyToManyInverse VALUES (1, 1);
INSERT INTO ManyToManyOwner_ManyToManyInverse VALUES (2, 1);
INSERT INTO ManyToManyOwner_ManyToManyInverse VALUES (2, 2);
INSERT INTO ManyToManyOwner_ManyToManyInverse VALUES (3, 1);
INSERT INTO ManyToManyOwner_ManyToManyInverse VALUES (3, 2);
INSERT INTO ManyToManyOwner_ManyToManyInverse VALUES (3, 3);
INSERT INTO ManyToManyOwner_ManyToManyInverse VALUES (4, 1);
INSERT INTO ManyToManyOwner_ManyToManyInverse VALUES (4, 2);
INSERT INTO ManyToManyOwner_ManyToManyInverse VALUES (4, 3);
INSERT INTO ManyToManyOwner_ManyToManyInverse VALUES (4, 4);
INSERT INTO ManyToManyOwner_ManyToManyInverse VALUES (5, 1);
INSERT INTO ManyToManyOwner_ManyToManyInverse VALUES (5, 2);
INSERT INTO ManyToManyOwner_ManyToManyInverse VALUES (5, 3);
INSERT INTO ManyToManyOwner_ManyToManyInverse VALUES (5, 4);
INSERT INTO ManyToManyOwner_ManyToManyInverse VALUES (5, 5);
INSERT INTO ManyToManyOwner_ManyToManyInverse VALUES (6, 1);
INSERT INTO ManyToManyOwner_ManyToManyInverse VALUES (6, 2);
INSERT INTO ManyToManyOwner_ManyToManyInverse VALUES (6, 3);
INSERT INTO ManyToManyOwner_ManyToManyInverse VALUES (6, 4);
INSERT INTO ManyToManyOwner_ManyToManyInverse VALUES (6, 5);
INSERT INTO ManyToManyOwner_ManyToManyInverse VALUES (6, 6);
