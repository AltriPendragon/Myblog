/*
Navicat MySQL Data Transfer

Source Server         : 47.106.74.95
Source Server Version : 50642
Source Host           : 47.106.74.95:3306
Source Database       : myblog

Target Server Type    : MYSQL
Target Server Version : 50642
File Encoding         : 65001

Date: 2019-07-08 10:44:51
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for article
-- ----------------------------
DROP TABLE IF EXISTS `article`;
CREATE TABLE `article` (
  `article_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `author` varchar(255) DEFAULT NULL,
  `create_time` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `summary` varchar(255) DEFAULT NULL,
  `content` longtext,
  `type` varchar(255) DEFAULT NULL,
  `category` varchar(255) DEFAULT NULL,
  `tags` varchar(255) DEFAULT NULL,
  `read` tinyint(4) DEFAULT NULL,
  `comment` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`article_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `comment_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `article_id` bigint(20) DEFAULT NULL,
  `remarker_id` int(11) DEFAULT NULL,
  `responsor_id` int(11) DEFAULT NULL,
  `p_id` bigint(20) DEFAULT NULL,
  `comment_content` varchar(255) DEFAULT NULL,
  `likes` int(11) DEFAULT NULL,
  `comment_date` varchar(20) DEFAULT NULL,
  `is_read` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`comment_id`),
  KEY `comment_article` (`article_id`),
  CONSTRAINT `comment_article` FOREIGN KEY (`article_id`) REFERENCES `article` (`article_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for comment_likes_record
-- ----------------------------
DROP TABLE IF EXISTS `comment_likes_record`;
CREATE TABLE `comment_likes_record` (
  `record_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `article_id` bigint(20) DEFAULT NULL,
  `comment_id` bigint(20) DEFAULT NULL,
  `liker_id` bigint(20) DEFAULT NULL,
  `flag` tinyint(4) DEFAULT NULL,
  `date` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`record_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for leave_message
-- ----------------------------
DROP TABLE IF EXISTS `leave_message`;
CREATE TABLE `leave_message` (
  `message_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `remarker_id` bigint(20) DEFAULT NULL,
  `responsor_id` bigint(20) DEFAULT NULL,
  `p_id` bigint(20) DEFAULT NULL,
  `message` varchar(255) DEFAULT NULL,
  `likes` int(11) DEFAULT NULL,
  `message_date` varchar(20) DEFAULT NULL,
  `is_read` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`message_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for leave_message_likes_record
-- ----------------------------
DROP TABLE IF EXISTS `leave_message_likes_record`;
CREATE TABLE `leave_message_likes_record` (
  `record_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `message_id` bigint(20) DEFAULT NULL,
  `liker_id` bigint(20) DEFAULT NULL,
  `flag` tinyint(4) DEFAULT NULL,
  `date` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`record_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for link
-- ----------------------------
DROP TABLE IF EXISTS `link`;
CREATE TABLE `link` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(24) DEFAULT NULL,
  `introduce` varchar(48) DEFAULT NULL,
  `url` varchar(24) DEFAULT NULL,
  `headImg` varchar(255) DEFAULT NULL,
  `status` tinyint(2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for link_message
-- ----------------------------
DROP TABLE IF EXISTS `link_message`;
CREATE TABLE `link_message` (
  `link_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `remarker_id` bigint(20) DEFAULT NULL,
  `responsor_id` bigint(20) DEFAULT NULL,
  `p_id` bigint(20) DEFAULT NULL,
  `message` varchar(255) DEFAULT NULL,
  `likes` int(11) DEFAULT NULL,
  `message_date` varchar(20) DEFAULT NULL,
  `is_read` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`link_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for link_message_likes_record
-- ----------------------------
DROP TABLE IF EXISTS `link_message_likes_record`;
CREATE TABLE `link_message_likes_record` (
  `record_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `link_id` bigint(20) DEFAULT NULL,
  `liker_id` bigint(20) DEFAULT NULL,
  `flag` tinyint(4) DEFAULT NULL,
  `date` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`record_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `provider_id` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `gender` tinyint(4) DEFAULT NULL,
  `info` varchar(255) DEFAULT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `recent_login_date` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for UserConnection
-- ----------------------------
DROP TABLE IF EXISTS `UserConnection`;
CREATE TABLE `UserConnection` (
  `userId` varchar(255) NOT NULL,
  `providerId` varchar(255) NOT NULL,
  `providerUserId` varchar(255) NOT NULL,
  `rank` int(11) NOT NULL,
  `displayName` varchar(255) DEFAULT NULL,
  `profileUrl` varchar(512) DEFAULT NULL,
  `imageUrl` varchar(512) DEFAULT NULL,
  `accessToken` varchar(512) NOT NULL,
  `secret` varchar(512) DEFAULT NULL,
  `refreshToken` varchar(512) DEFAULT NULL,
  `expireTime` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`userId`,`providerId`,`providerUserId`),
  UNIQUE KEY `UserConnectionRank` (`userId`,`providerId`,`rank`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
