# spring-boot-migration-practice

https://stackoverflow.com/questions/20215744/how-to-create-a-mysql-hierarchical-recursive-query

http://dus815.tistory.com/entry/Space-is-not-allowed-after-parameter-prefix-%ED%95%98%EC%9D%B4%EB%B2%84%EB%84%A4%EC%9D%B4%ED%8A%B8-%EC%97%90%EB%9F%AC





docker exec mysql_mysqldb2_1 /usr/bin/mysqldump --host localhost -u root --password=password test_test category > test_category.sql
docker exec mysql_mysqldb2_1 /usr/bin/mysqldump --host localhost -u root --password=password test_test program > test_program.sql

cat test_category.sql | docker exec -i mysql_mysqldb2_1 /usr/bin/mysql --host localhost --port 3308 -u root --password=password test_test
cat test_program.sql | docker exec -i mysql_mysqldb2_1 /usr/bin/mysql --host localhost --port 3308 -u root --password=password test_test




select name, parent, count(*) from category where id>260 group by name, parent order by count(*) desc;

select distinct id, name, parent, full_path, full_path_name
from category where id>260 and full_path_name not like 'PROGRAM|%';
