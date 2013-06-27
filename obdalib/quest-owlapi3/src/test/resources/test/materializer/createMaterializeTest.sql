CREATE TABLE T1
(
  id INT NOT NULL,
  p1 VARCHAR(10),
  CONSTRAINT r_pk1 PRIMARY KEY (id )
);

INSERT INTO T1 VALUES (1, 'aa');
INSERT INTO T1 VALUES (2, 'bb');
INSERT INTO T1 VALUES (3, 'cc');

CREATE TABLE T2
(
  id INT NOT NULL,
  p1 INTEGER,
  CONSTRAINT r_pk2 PRIMARY KEY (id )
);

INSERT INTO T2 VALUES (1, 11);
INSERT INTO T2 VALUES (2, 22);
INSERT INTO T2 VALUES (3, 33);

CREATE TABLE T3
(
  id INT NOT NULL,
  p1 DECIMAL,
  CONSTRAINT r_pk3 PRIMARY KEY (id )
);

INSERT INTO T3 VALUES (1, 101);
INSERT INTO T3 VALUES (2, 202);
INSERT INTO T3 VALUES (3, 303);

CREATE TABLE T4
(
  id INT NOT NULL,
  p1 FLOAT,
  CONSTRAINT r_pk4 PRIMARY KEY (id )
);

INSERT INTO T4 VALUES (1, 1.01);
INSERT INTO T4 VALUES (2, 2.02);
INSERT INTO T4 VALUES (3, 3.03);

CREATE TABLE T5
(
  id INT NOT NULL,
  p1 TIMESTAMP,
  CONSTRAINT r_pk5 PRIMARY KEY (id )
);

INSERT INTO T5 VALUES (1, '2012-05-01');
INSERT INTO T5 VALUES (2, '2013-06-02');
INSERT INTO T5 VALUES (3, '2011-07-07');

CREATE TABLE T6
(
  id INT NOT NULL,
  p1 BOOLEAN,
  CONSTRAINT r_pk6 PRIMARY KEY (id )
);

INSERT INTO T6 VALUES (1, 1);
INSERT INTO T6 VALUES (2, 0);
INSERT INTO T6 VALUES (3, 1);

CREATE TABLE T7
(
  id INT NOT NULL,
  p1 VARCHAR(10),
  CONSTRAINT r_pk7 PRIMARY KEY (id )
);

INSERT INTO T7 VALUES (1, 'aaa');
INSERT INTO T7 VALUES (2, 'bbb');
INSERT INTO T7 VALUES (3, 'ccc');

CREATE TABLE T8
(
  id INT NOT NULL,
  p1 VARCHAR(10),
  p2 VARCHAR(2),
  CONSTRAINT r_pk8 PRIMARY KEY (id )
);

INSERT INTO T8 VALUES (1, 'aaa', 'en');
INSERT INTO T8 VALUES (2, 'bbb', 'es');
INSERT INTO T8 VALUES (3, 'ccc', 'fr');

CREATE TABLE T9
(
  id INT NOT NULL,
  id2 INT NOT NULL,  
  CONSTRAINT r_pk9 PRIMARY KEY (id, id2 )
);

INSERT INTO T9 VALUES (1, 3);
INSERT INTO T9 VALUES (2, 2);
INSERT INTO T9 VALUES (3, 1);