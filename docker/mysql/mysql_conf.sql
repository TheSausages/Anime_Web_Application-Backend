#Create an additional user and database for anime data
CREATE USER 'backendUser'@'localhost' IDENTIFIED BY 'backendPassword2';
CREATE USER 'backendUser'@'%' IDENTIFIED BY 'backendPassword2';

CREATE DATABASE IF NOT EXISTS data;
GRANT ALL ON data.* TO 'backendUser'@'localhost';
GRANT ALL ON data.* TO 'backendUser'@'%';