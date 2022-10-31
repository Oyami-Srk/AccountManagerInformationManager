-- Database Schema
-- Executing this SQL script results in all table drop

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

CREATE TABLE `user` (
    id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY UNIQUE,
    username VARCHAR(64) NOT NULL UNIQUE,
    password BINARY(60) NOT NULL, -- We chose bcrypt here
    password_question TEXT,
    password_answer TEXT,
    ic_num VARCHAR(18) UNIQUE,
    nickname NVARCHAR(64),
    email VARCHAR(64),
    last_login_time DATETIME,
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    registration_time DATETIME NOT NULL DEFAULT NOW(),
    role ENUM('admin', 'user') NOT NULL DEFAULT 'user'
);

CREATE TABLE `manager` (
    id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY UNIQUE,
    user_id INTEGER NOT NULL UNIQUE,
    name NVARCHAR(16) NOT NULL,
    sex ENUM('male', 'female') NOT NULL,
    birthday DATE NOT NULL,
    ethnic ENUM("汉族","蒙古族","回族","藏族","维吾尔族","苗族","彝族","壮族","布依族","朝鲜族","满族","侗族","瑶族","白族","土家族","哈尼族","哈萨克族","傣族","黎族","僳僳族","佤族","畲族","高山族","拉祜族","水族","东乡族","纳西族","景颇族","柯尔克孜族","土族","达斡尔族","仫佬族","羌族","布朗族","撒拉族","毛南族","仡佬族","锡伯族","阿昌族","普米族","塔吉克族","怒族","乌孜 别克族","俄罗斯族","鄂温克族","德昂族","保安族","裕固族","京族","塔塔尔族","独龙族","鄂伦春族","赫哲族","门巴族","珞巴族","基诺族", "其他") DEFAULT "其他",
    political_status ENUM("中共党员","中共预备党员","共青团员","民革会员","民盟盟员","民建会员","民进会员","农工党党员","致公党党员","九三学社社员","台盟盟员","无党派民主人士","群众") DEFAULT "群众",
    native_place NVARCHAR(256),
    photo VARCHAR(256), -- photo path
    education ENUM("小学","初中","高中","中专","高职","专科","本科","硕士研究生","博士研究生"),
    graduated ENUM("学士","硕士","博士"),
    professional_title ENUM("高级工程师","工程师","助理工程师","高级经济师","经济师","助理经济师","高级会计师","会计师","助理会计师","高级统计师","统计师","助理统计师","高级审计师","审计师","助理审计师","高级政工师","政工师","助理政工师","其他","无") DEFAULT "无",
    manager_level ENUM("高级专家级客户经理","专家级客户经理","资深客户经理","高级客户经理","客户经理","客户经理助理","无") DEFAULT "无",
    unit NVARCHAR(64),
    dept NVARCHAR(32),
        -- "公司业务部","机构业务部","房地产信贷部","国际业务部","结算与现金管理部","农村产业金融部","个人金融部"
        -- "客户部","网点"
    business_line ENUM("对公","个人"),
    job NVARCHAR(32),
    hired_date DATE, -- 参加工作时间
    entered_date DATE, -- 入行时间
    financial_age_limit INTEGER,
    manager_age_limit INTEGER,
    total_credits REAL,
    year_credits REAL,
    exit_date DATE, -- 退出时间
    last_year_assessment NVARCHAR(32),
    qualification_cert_id VARCHAR(32),
    qualification_cert_date DATE,
    job_cert_id VARCHAR(32),
    job_cert_date DATE,
    mobile VARCHAR(16),
    office_tel VARCHAR(16) NOT NULL,
    manager_status ENUM("在职", "退出") DEFAULT "在职",
    last_update DATETIME NOT NULL DEFAULT NOW() ON UPDATE NOW(),
    FOREIGN KEY (id) REFERENCES user(id)
);

-- Assistant tables
CREATE TABLE `work_record` (
    id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY UNIQUE,
    manager_id INTEGER NOT NULL,
    office_date DATE,
    position NVARCHAR(16),
    unit NVARCHAR(64),
    work_detail TEXT,
    attachment VARCHAR(256), -- attachment path
    last_update DATETIME NOT NULL DEFAULT NOW() ON UPDATE NOW(),
    FOREIGN KEY (manager_id) REFERENCES manager(id)
);

CREATE TABLE `level_record` (
    id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY UNIQUE,
    manager_id INTEGER NOT NULL,
    date DATE,
    level NVARCHAR(32),
    type NVARCHAR(32),
    attachment VARCHAR(256),
    last_update DATETIME NOT NULL DEFAULT NOW() ON UPDATE NOW(),
    FOREIGN KEY (manager_id) REFERENCES manager(id)
);

CREATE TABLE `train_record` (
    id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY UNIQUE,
    manager_id INTEGER NOT NULL,
    date DATE,
    subject NVARCHAR(32),
    unit NVARCHAR(64),
    start_date DATE,
    end_date DATE,
    credit REAL,
    score REAL,
    attachment VARCHAR(256),
    last_update DATETIME NOT NULL DEFAULT NOW() ON UPDATE NOW(),
    FOREIGN KEY (manager_id) REFERENCES manager(id)
);

CREATE TABLE `assessment_record` (
    id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY UNIQUE,
    manager_id INTEGER NOT NULL,
    date DATE,
    context NVARCHAR(128),
    period NVARCHAR(128),
    result NVARCHAR(128),
    unit NVARCHAR(64),
    attachment VARCHAR(256),
    last_update DATETIME NOT NULL DEFAULT NOW() ON UPDATE NOW(),
    FOREIGN KEY (manager_id) REFERENCES manager(id)
);

CREATE TABLE `reward_punishment_record` (
    id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY UNIQUE,
    manager_id INTEGER NOT NULL,
    date DATE,
    type ENUM("奖励", "惩罚"),
    context NVARCHAR(128),
    unit NVARCHAR(64),
    person NVARCHAR(16),
    attachment VARCHAR(256),
    last_update DATETIME NOT NULL DEFAULT NOW() ON UPDATE NOW(),
    FOREIGN KEY (manager_id) REFERENCES manager(id)
);

CREATE TABLE `change_record` (
    id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY UNIQUE,
    manager_id INTEGER NOT NULL,
    date DATE,
    unit_from NVARCHAR(64),
    unit_to NVARCHAR(64),
    dept_from NVARCHAR(32),
    dept_to NVARCHAR(32),
    comment TEXT,
    attachment VARCHAR(256),
    last_update DATETIME NOT NULL DEFAULT NOW() ON UPDATE NOW(),
    FOREIGN KEY (manager_id) REFERENCES manager(id)
);


CREATE TABLE `certificate` (
    id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY UNIQUE,
    manager_id INTEGER NOT NULL,
    name NVARCHAR(64),
    type NVARCHAR(16),
    issuer NVARCHAR(64),
    issue_date DATE,
    start_date DATE,
    end_date DATE,
    valid BOOLEAN,
    attachment VARCHAR(256),
    last_update DATETIME NOT NULL DEFAULT NOW() ON UPDATE NOW(),
    FOREIGN KEY (manager_id) REFERENCES manager(id)
);

CREATE TABLE `annual_performance` (
    id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY UNIQUE,
    manager_id INTEGER NOT NULL,
    year INTEGER,
    performance TEXT,
    attachment VARCHAR(256),
    last_update DATETIME NOT NULL DEFAULT NOW() ON UPDATE NOW(),
    FOREIGN KEY (manager_id) REFERENCES manager(id)
);
