use xxx; -- �л����ݿ�
go

create table post -- ������ҳ��
(
    id int identity primary key, -- �������
    title varchar(200) not null, -- ����
    content varchar(2000) not null, -- ����
    author varchar(200) not null, -- ����
    created datetime default getdate(), -- ����ʱ��
	cover varchar(200) not null, -- ͼƬ
	likes int default 0 -- ��������
);
create table comment -- ���ͺ�̨��
(
    id int identity primary key, -- �������
    postid int, -- ĳƪ����
    content varchar(1000) not null, -- ��������
    author varchar(200) not null, -- ��������
    created datetime default getdate(), -- ����ʱ��
);
go

select * from post;
select * from comment;

