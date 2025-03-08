select name,isbn,year,author,book_category_id,description from books
Where id = 36165;
select *
from books;
select * from books;
select full_name,email,password,status,start_date,end_date,address from users
where id = 22254;
select B.name as book_name, isbn, BC.name as category_name, year, author from books B join book_categories BC on B.book_category_id = BC.id where B.id = 36809;

select isbn, B.name, author, BC.name , year from books B join book_categories BC on B.book_category_id = BC.id where B.id = 36809;
