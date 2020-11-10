-- password in plaintext: "password"
INSERT INTO T_USER (user_id, password, email, username, name, last_name, active)
VALUES
  (1, '$2a$06$OAPObzhRdRXBCbk7Hj/ot.jY3zPwR8n7/mfLtKIgTzdJa4.6TwsIm', 'lester@mail.com', 'arnold', 'Cronin', 'Arnold',
   1);
-- password in plaintext: "password"
INSERT INTO T_USER (user_id, password, email, username, name, last_name, active)
VALUES
  (2, '$2a$06$OAPObzhRdRXBCbk7Hj/ot.jY3zPwR8n7/mfLtKIgTzdJa4.6TwsIm', 'owen@gmail.com', 'rodney', 'Cecillia', 'Rodney', 1);
-- password in plaintext: "password"
INSERT INTO T_USER (user_id, password, email, username, name, last_name, active)
VALUES (3, '$2a$06$OAPObzhRdRXBCbk7Hj/ot.jY3zPwR8n7/mfLtKIgTzdJa4.6TwsIm', 'carter@gmail.com', 'james ', 'Hosea', 'James', 1);

INSERT INTO T_ROLE (role_id, role)
VALUES (1, 'ROLE_ADMIN');
INSERT INTO T_ROLE (role_id, role)
VALUES (2, 'ROLE_USER');

INSERT INTO USER_ROLE (user_id, role_id)
VALUES (1, 1);
INSERT INTO USER_ROLE (user_id, role_id)
VALUES (1, 2);
INSERT INTO USER_ROLE (user_id, role_id)
VALUES (2, 2);
INSERT INTO USER_ROLE (user_id, role_id)
VALUES (3, 2);

INSERT INTO T_PRODUCT (name, description, quantity, price)
VALUES ('Java核心技术 卷I 基础知识', 'Core Java 第11版', 1, 98);
INSERT INTO T_PRODUCT (name, description, quantity, price)
VALUES ('深入理解Java虚拟机：JVM高级特性与最佳实践（第3版）', '周志明虚拟机新作，第3版新增内容近50%', 5, 89);
INSERT INTO T_PRODUCT (name, description, quantity, price)
VALUES ('Java编程思想（第4版）', 'Java学习必读经典,殿堂级著作！', 3, 70.20);
INSERT INTO T_PRODUCT (name, description, quantity, price)
VALUES ('Head First Java（中文版）', '10年畅销经典，累计印刷30多次。', 40, 54.50);
INSERT INTO T_PRODUCT (name, description, quantity, price)
VALUES ('Java开发手册（码出高效Java开发手册+阿里巴巴Java开发手册）', '引爆技术圈，全球瞩目的中国计算机民族图书。', 80, 92.40);
INSERT INTO T_PRODUCT (name, description, quantity, price)
VALUES ('Java并发编程的艺术', '阿里技术专家/Java并发编程领域领军人物撰写。', 800, 38.80);
INSERT INTO T_PRODUCT (name, description, quantity, price)
VALUES ('Effective Java中文版（原书第3版）', 'Java之父鼎力推荐', 700, 87.30);
INSERT INTO T_PRODUCT (name, description, quantity, price)
VALUES ('Java项目开发实战入门（全彩版）', '一本让初学者通过项目实战开发学会编程的图书', 500, 29.90);
INSERT INTO T_PRODUCT (name, description, quantity, price)
VALUES ('Java精彩编程200例（全彩版）', '全彩图书，精选200个场景应用实例。', 1000, 39.90);
INSERT INTO T_PRODUCT (name, description, quantity, price)
VALUES ('Java 8函数式编程', 'Java编程思想从此向函数式编程转型', 10, 30.80);