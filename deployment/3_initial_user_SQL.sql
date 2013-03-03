insert into GENERIC_OID_SEQ values(500);
insert into ITEM_OID_SEQ values(500);

insert into CTL_PARAM values('CTRL','PER_PAGE_RECORDS','10');


insert into USER_PROFILE values(
1,'系统管理员','系统管理员','A',null,null,'M','admin','202cb962ac59075b964b07152d234b70','A',0);

insert into ROLE_PROFILE values(
1,'系统管理员');
insert into ROLE_PROFILE values(
2,'普通用户');

insert into MODULE values(
'0100','系统管理',1,null,'Y',0100,null);
insert into MODULE values(
'0110','家庭管理',2,'0100','Y',0110,'/initFamily.action');
insert into MODULE values(
'0120','用户管理',2,'0100','Y',0120,'/initUser.action');

insert into MODULE values(
'0200','日常消费管理',1,null,'Y',0200,null);
insert into MODULE values(
'0210','类别管理',2,'0200','Y',0210,'initCategory.action');
insert into MODULE values(
'0220','账单管理',2,'0200','Y',0220,'initItem.action');

insert into MODULE values(
'0300','存款管理',1,null,'Y',0300,null);
insert into MODULE values(
'0310','银行卡管理',2,'0300','Y',0310,'/initBankCard.action');
insert into MODULE values(
'0320','信用卡管理',2,'0300','Y',0320,'/initCreditCard.action');

insert into MODULE values(
'0400','工资管理',1,null,'Y',0400,null);
insert into MODULE values(
'0410','工资记录',2,'0400','Y',0410,'/initSalaryRecord.action');


insert into MODULE values(
'0500','统计报表',1,null,'Y',0500,null);
insert into MODULE values(
'0510','消费查询',2,'0500','Y',0510,'/initReportItem.action');
insert into MODULE values(
'0520','收入查询',2,'0500','Y',0520,'/initReportSalary.action');

insert into USER_ROLE values(
1,1);

insert into ROLE_MODULE values(
1,'0110');
insert into ROLE_MODULE values(
1,'0120');
insert into ROLE_MODULE values(
2,'0210');
insert into ROLE_MODULE values(
2,'0220');
insert into ROLE_MODULE values(
2,'0310');
insert into ROLE_MODULE values(
2,'0320');
insert into ROLE_MODULE values(
2,'0410');
insert into ROLE_MODULE values(
2,'0510');
insert into ROLE_MODULE values(
2,'0520');
