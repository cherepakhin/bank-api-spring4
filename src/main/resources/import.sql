--select * from client;
--insert into client(id,name,phone) values(0,'-','');
insert into client(id,name,phone) values(0,'NAME_0','PHONE_0'),(1,'NAME_1','PHONE_1');
insert into account(id,client_id,balance,name) values (0,0,0.00,'ACCOUNT_0_0'),(1,0,0.00,'ACCOUNT_0_1'),(2,1,0.00,'ACCOUNT_1_0');
insert into operation(id,src_account_id,dst_account_id,amount,ddate,comment) values (0,1,2,50.00,'2018-03-01','');
insert into operation(id,src_account_id,dst_account_id,amount,ddate,comment) values (1,2,1,150.00,'2018-03-10','');
