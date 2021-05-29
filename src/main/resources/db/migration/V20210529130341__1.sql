CREATE TABLE IF NOT EXISTS user(
    `id`                INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增主鍵',
    `username`          VARCHAR(64) NOT NULL COMMENT '使用者名稱',
    `password`          VARCHAR(64) NOT NULL COMMENT '密碼',
    `email`             VARCHAR(64) NOT NULL COMMENT 'Email',
    `phone`             VARCHAR(10) COMMENT '電話',
    `last_login_time`   DATETIME COMMENT '上次登入時間',
    `status`            TINYINT(1) DEFAULT 1 COMMENT '0 帳號凍結/ 1 正常',
    PRIMARY KEY(id),
    UNIQUE KEY index_user_username(username)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT COMMENT ='使用者';