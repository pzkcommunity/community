create table user(
  id bigint primary key auto_increment,
  account_id varchar(100),
  name varchar(50),
  token varchar(36),
  gmt_create bigint,
  gmt_modified bigint,
  bio varchar(256)
)