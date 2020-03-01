create table comment(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    parent_id BIGINT NOT NULL,
    type int NOT NULL,
    commentator INT NOT NULL,
    gmt_create BIGINT NOT NULL,
    gmt_modified BIGINT NOT NULL,
    like_count BIGINT NOT NULL DEFAULT 0,
    content varchar(1024) NOT NULL ,
    comment_count int NOT NULL default 0
)