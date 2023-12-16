SET NAMES 'UTF8MB4';
SET TIME_ZONE = 'Asia/Calcutta';
SET TIME_ZONE = '+5:30';

CREATE SCHEMA IF NOT EXISTS securecapita;

USE securecapita;

CREATE TABLE IF NOT EXISTS Users
(
        user_id         BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
        first_name VARCHAR(50) NOT NULL,
        last_name  VARCHAR(50) NOT NULL,
        email      VARCHAR(100) NOT NULL,
        password   VARCHAR(255) DEFAULT NULL,
        address    VARCHAR(255) DEFAULT NULL,
        phone      VARCHAR(30) DEFAULT NULL,
        title      VARCHAR(50) DEFAULT NULL,
        bio        VARCHAR(255) DEFAULT NULL,
        enabled    BOOLEAN DEFAULT FALSE,
        non_locked BOOLEAN DEFAULT TRUE,
        using_mfa  BOOLEAN DEFAULT FALSE,
        created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
        image_url  VARCHAR(255) DEFAULT 'https://cdn-icons-png.flaticon.com/512/149/149071.png',
        CONSTRAINT UQ_Users_Email UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS Roles
(
    role_id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    permissions VARCHAR(50) NOT NULL,
    CONSTRAINT UQ_Roles_Name UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS Events
(
    event_id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    type VARCHAR(50) NOT NULL CHECK(type IN ('LOGIN_ATTEMPT', 'LOGIN_FAILURE_ATTEMPT')),
    description VARCHAR(255) NOT NULL,
    CONSTRAINT UQ_Events_Type UNIQUE(type)
);

CREATE TABLE IF NOT EXISTS UserEvents
(
    user_event_id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    users_user_id BIGINT UNSIGNED NOT NULL,
    roles_role_id BIGINT UNSIGNED NOT NULL,
    device VARCHAR(50) DEFAULT NULL,
    ip_address VARCHAR(50) DEFAULT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (users_user_id) REFERENCES Users (user_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (roles_role_id) REFERENCES Roles (role_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS AccountVerifications
(
    account_verification_id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    users_user_id BIGINT UNSIGNED NOT NULL,
    --date DATETIME NOT NULL,
    url VARCHAR(255) NOT NULL,
    FOREIGN KEY (users_user_id) REFERENCES Users (user_id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT UQ_AccountVerifications_users_user_id UNIQUE(users_user_id),
    CONSTRAINT UQ_AccountVerifications_Url UNIQUE(url)
);

CREATE TABLE IF NOT EXISTS ResetPasswordVerifications
(
    reset_password_verification_id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    users_user_id BIGINT UNSIGNED NOT NULL,
    expiration_date DATETIME NOT NULL,
    url VARCHAR(255) NOT NULL,
    FOREIGN KEY (users_user_id) REFERENCES Users (user_id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT UQ_ResetPasswordVerifications_users_user_id UNIQUE(users_user_id),
    CONSTRAINT UQ_ResetPasswordVerifications_Url UNIQUE(url)
);

CREATE TABLE IF NOT EXISTS TwoFactorVerifications
(
    two_factor_verification_id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    users_user_id BIGINT UNSIGNED NOT NULL,
    expiration_date DATETIME NOT NULL,
    code VARCHAR(10) NOT NULL,
    FOREIGN KEY (users_user_id) REFERENCES Users (user_id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT UQ_TwoFactorVerifications_users_user_id UNIQUE(users_user_id),
    CONSTRAINT UQ_TwoFactorVerifications_Code UNIQUE(code)
);