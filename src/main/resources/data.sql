/*Courses*/
	
insert into COURSE(ID, NAME, LAST_UPDATE, CREATION_DATE, IS_DELETED) VALUES(1, 'Spring-Jpa-Hibernate', SYSDATE(), SYSDATE(), FALSE);
insert into COURSE(ID, NAME, LAST_UPDATE, CREATION_DATE, IS_DELETED) VALUES(2, 'Spring Boot', SYSDATE(), SYSDATE(), FALSE);
insert into COURSE(ID, NAME, LAST_UPDATE, CREATION_DATE, IS_DELETED) VALUES(3, 'Spring MVC', SYSDATE(), SYSDATE(), FALSE);
insert into COURSE(ID, NAME, LAST_UPDATE, CREATION_DATE, IS_DELETED) VALUES(4, 'Spring Angular full stack', SYSDATE(), SYSDATE(), FALSE);
insert into COURSE(ID, NAME, LAST_UPDATE, CREATION_DATE, IS_DELETED) VALUES(5, 'Spring React Full Stack', SYSDATE(), SYSDATE(), FALSE);
insert into COURSE(ID, NAME, LAST_UPDATE, CREATION_DATE, IS_DELETED) VALUES(6, 'Spring Rest Api', SYSDATE(), SYSDATE(), FALSE);
insert into COURSE(ID, NAME, LAST_UPDATE, CREATION_DATE, IS_DELETED) VALUES(7, 'Spring Masterclass', SYSDATE(), SYSDATE(), FALSE);
	
/*Passport*/

insert into Passport(ID, NUMBER) VALUES(1, 'RRL29081988');
insert into Passport(ID, NUMBER) VALUES(2, 'JT05101987');
insert into Passport(ID, NUMBER) VALUES(3, 'GHRS26071986');
insert into Passport(ID, NUMBER) VALUES(4, 'JAP22101990');
insert into Passport(ID, NUMBER) VALUES(5, 'RPR12121988');

/*Students*/

insert into Student(ID,NAME, PASSPORT_ID) VALUES(1, 'Raphael Rodrigues Lima', 1);
insert into Student(ID,NAME, PASSPORT_ID) VALUES(2, 'Joaquin Teixeira', 2);
insert into Student(ID,NAME, PASSPORT_ID) VALUES(3, 'Gustavo Henrique Ramos Silva', 3);
insert into Student(ID,NAME, PASSPORT_ID) VALUES(4, 'Jonathan Augusto de Paula', 4);
insert into Student(ID,NAME, PASSPORT_ID) VALUES(5, 'Rodrigo Prado Ribeiro', 5);
	
/*Review*/

insert into Review(ID, DESCRIPTION, RATING, COURSE_ID, STUDENT_ID) VALUES(1, 'Really easy to follow along, an amazing course', 5, 1, 1);
insert into Review(ID, DESCRIPTION, RATING, COURSE_ID, STUDENT_ID) VALUES(2, 'Interesting', 4 , 3, 3);
insert into Review(ID, DESCRIPTION, RATING, COURSE_ID, STUDENT_ID) VALUES(3, 'COOL and Instructive ', 4, 1, 5);
insert into Review(ID, DESCRIPTION, RATING, COURSE_ID, STUDENT_ID) VALUES(4, 'Amazing', 5, 1, 2);
insert into Review(ID, DESCRIPTION, RATING, COURSE_ID, STUDENT_ID) VALUES(5, 'Bad ass', 5, 2, 1);

/*student_course*/
insert into student_course(student_id, course_id) values(1,1);
insert into student_course(student_id, course_id) values(1,2);
insert into student_course(student_id, course_id) values(4,2);
insert into student_course(student_id, course_id) values(3,4);
insert into student_course(student_id, course_id) values(1,4);
insert into student_course(student_id, course_id) values(5,2);

