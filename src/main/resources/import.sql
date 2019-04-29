--select * from client;
--insert into client(id,name,phone) values(0,'-','');
insert into client(id,name,phone) values(0,'Иванов И.И.','+79124567891'),(1,'Петров К.А.','+7902474546789');
insert into account(id,client_id,balance,name) values (0,0,0.00,'Карта'),(1,0,0.00,'Банковский'),(2,1,0.00,'Карта');
insert into operation(id,src_account_id,dst_account_id,amount,ddate,comment) values (0,1,2,50.00,'2018-03-01','');
insert into operation(id,src_account_id,dst_account_id,amount,ddate,comment) values (1,2,1,150.00,'2018-03-10','');
