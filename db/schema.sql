-- Database Schema
-- Executing this SQL script results in all table drop

DROP TABLE IF EXISTS `learn`;
DROP TABLE IF EXISTS `work`;
DROP TABLE IF EXISTS `meeting`;
DROP TABLE IF EXISTS `marketing`;
DROP TABLE IF EXISTS `client`;
DROP TABLE IF EXISTS `attachment`;
DROP TABLE IF EXISTS `work_record`;
DROP TABLE IF EXISTS `level_record`;
DROP TABLE IF EXISTS `train_record`;
DROP TABLE IF EXISTS `assessment_record`;
DROP TABLE IF EXISTS `reward_punishment_record`;
DROP TABLE IF EXISTS `change_record`;
DROP TABLE IF EXISTS `annual_performance`;
DROP TABLE IF EXISTS `certificate`;
DROP TABLE IF EXISTS `manager`;
DROP TABLE IF EXISTS `user`;

CREATE TABLE `user`
(
    id                INTEGER                NOT NULL AUTO_INCREMENT PRIMARY KEY UNIQUE,
    username          VARCHAR(64)            NOT NULL UNIQUE,
    password          BINARY(60)             NOT NULL, -- We chose bcrypt here
    password_question TEXT,
    password_answer   TEXT,
    ic_num            VARCHAR(18) UNIQUE,
    nickname          NVARCHAR(64),
    email             VARCHAR(64),
    last_login_time   DATETIME,
    enabled           BOOLEAN                NOT NULL DEFAULT TRUE,
    registration_time DATETIME               NOT NULL DEFAULT NOW(),
    role              ENUM ('admin', 'user') NOT NULL DEFAULT 'user'
);

CREATE TABLE `manager`
(
    id                      INTEGER                 NOT NULL PRIMARY KEY UNIQUE,
    user_id                 INTEGER UNIQUE,
    name                    NVARCHAR(16)            NOT NULL,
    sex                     ENUM ('male', 'female') NOT NULL,
    birthday                DATE                    NOT NULL,
    ic_num                  VARCHAR(18) UNIQUE      NOT NULL,
    ethnic                  NVARCHAR(8),
    political_status        NVARCHAR(8),
    native_place            NVARCHAR(256),
    photo                   MEDIUMBLOB, -- Possible Performance Impact. TODO: Use CDN/Static/Separated Table
    education               NVARCHAR(8),
    graduated               NVARCHAR(4),
    school                  NVARCHAR(64),
    professional_title      NVARCHAR(8)                           DEFAULT "无",
    manager_level           NVARCHAR(16)                          DEFAULT "无",
    unit                    NVARCHAR(64),
    dept                    NVARCHAR(32),
    business_line           ENUM ("business","personal"),
    job                     NVARCHAR(32),
    hired_date              DATE,       -- 参加工作时间
    entered_date            DATE,       -- 入行时间
    financial_age_limit     INTEGER,
    manager_age_limit       INTEGER,
    -- total_credits           REAL, -- Runtime query
    -- year_credits            REAL, -- Runtime query
    exit_date               DATE,       -- 退出时间
    last_year_assessment    NVARCHAR(32),
    qualification_cert_id   VARCHAR(32),
    qualification_cert_date DATE,
    job_cert_id             VARCHAR(32),
    job_cert_date           DATE,
    mobile                  VARCHAR(16),
    office_tel              VARCHAR(16)             NOT NULL,
    manager_status          ENUM ("in-service", "out-of-service") DEFAULT "in-service",
    last_update             DATETIME                NOT NULL      DEFAULT NOW() ON UPDATE NOW()
    -- FOREIGN KEY (id) REFERENCES user (id)
);

-- Assistant tables
CREATE TABLE `work_record`
(
    id          INTEGER  NOT NULL AUTO_INCREMENT PRIMARY KEY UNIQUE,
    manager_id  INTEGER  NOT NULL,
    office_date DATE,
    position    NVARCHAR(16),
    unit        NVARCHAR(64),
    work_detail TEXT,
    attachment  CHAR(36),
    last_update DATETIME NOT NULL DEFAULT NOW() ON UPDATE NOW(),
    update_user INTEGER  NOT NULL,
    FOREIGN KEY (update_user) REFERENCES user (id),
    FOREIGN KEY (manager_id) REFERENCES manager (id)
);

CREATE TABLE `level_record`
(
    id          INTEGER  NOT NULL AUTO_INCREMENT PRIMARY KEY UNIQUE,
    manager_id  INTEGER  NOT NULL,
    date        DATE,
    level       NVARCHAR(32),
    type        NVARCHAR(32),
    attachment  CHAR(36),
    last_update DATETIME NOT NULL DEFAULT NOW() ON UPDATE NOW(),
    update_user INTEGER  NOT NULL,
    FOREIGN KEY (update_user) REFERENCES user (id),
    FOREIGN KEY (manager_id) REFERENCES manager (id)
);

CREATE TABLE `train_record`
(
    id          INTEGER  NOT NULL AUTO_INCREMENT PRIMARY KEY UNIQUE,
    manager_id  INTEGER  NOT NULL,
    date        DATE,
    subject     NVARCHAR(32),
    unit        NVARCHAR(64),
    start_date  DATE,
    end_date    DATE,
    hours       INTEGER,
    credit      REAL,
    score       REAL,
    attachment  CHAR(36),
    last_update DATETIME NOT NULL DEFAULT NOW() ON UPDATE NOW(),
    update_user INTEGER  NOT NULL,
    FOREIGN KEY (update_user) REFERENCES user (id),
    FOREIGN KEY (manager_id) REFERENCES manager (id)
);

CREATE TABLE `assessment_record`
(
    id          INTEGER  NOT NULL AUTO_INCREMENT PRIMARY KEY UNIQUE,
    manager_id  INTEGER  NOT NULL,
    date        DATE,
    context     NVARCHAR(128),
    period NVARCHAR (128),
    result      NVARCHAR(128),
    evaluation  TEXT,
    unit        NVARCHAR(64),
    attachment  CHAR(36),
    last_update DATETIME NOT NULL DEFAULT NOW() ON UPDATE NOW(),
    update_user INTEGER  NOT NULL,
    FOREIGN KEY (update_user) REFERENCES user (id),
    FOREIGN KEY (manager_id) REFERENCES manager (id)
);

CREATE TABLE `reward_punishment_record`
(
    id              INTEGER  NOT NULL AUTO_INCREMENT PRIMARY KEY UNIQUE,
    manager_id      INTEGER  NOT NULL,
    date            DATE,
    type            NVARCHAR(32),
    context         NVARCHAR(128),
    unit            NVARCHAR(64),
    person          NVARCHAR(16),
    withdraw_date   DATE,
    withdraw_reason NVARCHAR(64),
    attachment      CHAR(36),
    last_update     DATETIME NOT NULL DEFAULT NOW() ON UPDATE NOW(),
    update_user     INTEGER  NOT NULL,
    FOREIGN KEY (update_user) REFERENCES user (id),
    FOREIGN KEY (manager_id) REFERENCES manager (id)
);

CREATE TABLE `change_record`
(
    id          INTEGER  NOT NULL AUTO_INCREMENT PRIMARY KEY UNIQUE,
    manager_id  INTEGER  NOT NULL,
    date        DATE,
    unit_from   NVARCHAR(64),
    unit_to     NVARCHAR(64),
    dept_from   NVARCHAR(32),
    dept_to     NVARCHAR(32),
    comment     TEXT,
    attachment  CHAR(36),
    last_update DATETIME NOT NULL DEFAULT NOW() ON UPDATE NOW(),
    update_user INTEGER  NOT NULL,
    FOREIGN KEY (update_user) REFERENCES user (id),
    FOREIGN KEY (manager_id) REFERENCES manager (id)
);


CREATE TABLE `certificate`
(
    id           INTEGER  NOT NULL AUTO_INCREMENT PRIMARY KEY UNIQUE,
    manager_id   INTEGER  NOT NULL,
    cert_name    NVARCHAR(64),
    cert_id      NVARCHAR(64),
    type         NVARCHAR(16),
    issuer       NVARCHAR(64),
    issue_date   DATE,
    start_date   DATE,
    end_date     DATE,
    valid        BOOLEAN,
    invalid_mark BOOLEAN,
    attachment   CHAR(36),
    last_update  DATETIME NOT NULL DEFAULT NOW() ON UPDATE NOW(),
    update_user  INTEGER  NOT NULL,
    FOREIGN KEY (update_user) REFERENCES user (id),
    FOREIGN KEY (manager_id) REFERENCES manager (id)
);

CREATE TABLE `annual_performance`
(
    id          INTEGER  NOT NULL AUTO_INCREMENT PRIMARY KEY UNIQUE,
    manager_id  INTEGER  NOT NULL,
    year        INTEGER,
    performance TEXT,
    attachment  CHAR(36),
    last_update DATETIME NOT NULL DEFAULT NOW() ON UPDATE NOW(),
    update_user INTEGER  NOT NULL,
    FOREIGN KEY (update_user) REFERENCES user (id),
    FOREIGN KEY (manager_id) REFERENCES manager (id)
);

-- Attachment

CREATE TABLE `attachment`
(
    id       INTEGER      NOT NULL AUTO_INCREMENT PRIMARY KEY UNIQUE,
    -- Actually it should be hash... But I'm tired
    -- Using hash also required uuid to allow existence of different filenames
    uuid     CHAR(36)     NOT NULL UNIQUE,
    filename NVARCHAR(64) NOT NULL
);

-- Client and Marketing
CREATE TABLE `client`
(
    id              INTEGER      NOT NULL AUTO_INCREMENT PRIMARY KEY UNIQUE,
    name            NVARCHAR(16) NOT NULL,
    ic_num          VARCHAR(18)  NOT NULL,
    mobile          VARCHAR(16)  NOT NULL,
    income_per_year REAL, -- In 10k
    asset           REAL, -- In 10k
    debt            REAL, -- In 10k
    company         NVARCHAR(32),
    address         TEXT,
    business        NVARCHAR(32),
    manager_id      INTEGER      NOT NULL,
    FOREIGN KEY (manager_id) REFERENCES manager (id)
);

CREATE TABLE `marketing`
(
    id          INTEGER      NOT NULL AUTO_INCREMENT PRIMARY KEY UNIQUE,
    name        NVARCHAR(16) NOT NULL,
    status      NVARCHAR(16),
    requirement NVARCHAR(16),
    report      CHAR(36),
    recommend   CHAR(36),
    evaluation  CHAR(36),
    date        DATE
);

-- Work management
CREATE TABLE `meeting`
(
    id           INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY UNIQUE,
    date         DATE    NOT NULL,
    context      NVARCHAR(64),
    participants TEXT,
    attachment   CHAR(36)
);

CREATE TABLE `work`
(
    id         INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY UNIQUE,
    date       DATE DEFAULT NOW(),
    client     TEXT,
    info       TEXT,
    after      TEXT,
    risk       TEXT,
    problem    TEXT,
    suggestion TEXT
);


-- Learning management
CREATE TABLE `learn`
(
    id          INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY UNIQUE,
    name        NVARCHAR(32),
    type        NVARCHAR(32),
    description TEXT,
    upload_date DATE DEFAULT NOW(),
    attachment  CHAR(36),
    uploader    INTEGER NOT NULL,
    FOREIGN KEY (uploader) REFERENCES user (id)
);
