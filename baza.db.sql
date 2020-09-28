BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "departments" (
	"department_id"	INTEGER,
	"department_name"	TEXT,
	"manager_id"	INTEGER,
	"location_id"	INTEGER,
	PRIMARY KEY("department_id"),
	FOREIGN KEY("manager_id") REFERENCES "employees"("employee_id"),
	FOREIGN KEY("location_id") REFERENCES "locations"("location_id")
);
CREATE TABLE IF NOT EXISTS "employees" (
	"employee_id"	INTEGER,
	"employee_name"	TEXT,
	"email"	TEXT,
	"hire_date"	DATE,
	"department_id"	INTEGER,
	"manager_id"	INTEGER,
	"job_id"	INTEGER,
	"salary"	INTEGER,
	"cmp"	REAL,
	PRIMARY KEY("employee_id"),
	FOREIGN KEY("department_id") REFERENCES "departments"("department_id"),
	FOREIGN KEY("manager_id") REFERENCES "employees"("employee_id"),
	FOREIGN KEY("job_id") REFERENCES "jobs"("job_id")
);
CREATE TABLE IF NOT EXISTS "jobs" (
	"job_id"	INTEGER,
	"job_title"	TEXT,
	"min_salary"	INTEGER,
	"max_salary"	INTEGER,
	PRIMARY KEY("job_id")
);
CREATE TABLE IF NOT EXISTS "locations" (
	"location_id"	INTEGER,
	"city"	TEXT,
	PRIMARY KEY("location_id")
);
INSERT INTO "departments" VALUES (1,'Restoran',1,1);
INSERT INTO "departments" VALUES (2,'Kafić',4,2);
INSERT INTO "employees" VALUES (1,'Amina Sabanovic','asabanovi6@etf.unsa.ba','05.10.2010.',1,NULL,1,1500,NULL);
INSERT INTO "employees" VALUES (2,'Hana Veladzic','hanaveladzic@gmail.com','10.10.2011.',1,1,2,1000,0.2);
INSERT INTO "employees" VALUES (3,'Adnan Tomic ','adnant98@live.com','18.09.2011.',1,1,3,1200,0.1);
INSERT INTO "employees" VALUES (4,'Nina Skopljak','snina1@etf.unsa.ba','15.03.2015.',2,NULL,1,1700,0.1);
INSERT INTO "employees" VALUES (5,'Davor Sekulic','dsekulic3@etf.unsa.ba','18.10.2019.',2,4,3,1300,NULL);
INSERT INTO "employees" VALUES (6,'Sandra Zec','zecsandra1@gmail.com','18.10.2019.',2,4,3,1300,0.1);
INSERT INTO "employees" VALUES (7,'Mario Drmac','dmario1@gmail.com','20.10.2019.',2,4,4,1000,NULL);
INSERT INTO "jobs" VALUES (1,'Menadžer',1200,2000);
INSERT INTO "jobs" VALUES (2,'Kuhar',1000,1700);
INSERT INTO "jobs" VALUES (3,'Šanker',1000,1500);
INSERT INTO "jobs" VALUES (4,'Čistać',900,1300);
INSERT INTO "locations" VALUES (1,'Sarajevo');
INSERT INTO "locations" VALUES (2,'Mostar');
COMMIT;
