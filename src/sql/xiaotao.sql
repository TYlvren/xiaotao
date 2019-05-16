/*
 Navicat MySQL Data Transfer

 Source Server         : admin
 Source Server Type    : MySQL
 Source Server Version : 80011
 Source Host           : localhost:3306
 Source Schema         : xiaotao

 Target Server Type    : MySQL
 Target Server Version : 80011
 File Encoding         : 65001

 Date: 15/05/2019 18:04:30
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for category_t
-- ----------------------------
DROP TABLE IF EXISTS `category_t`;
CREATE TABLE `category_t`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `category_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of category_t
-- ----------------------------
INSERT INTO `category_t` VALUES (1, '书籍');
INSERT INTO `category_t` VALUES (2, '服装');
INSERT INTO `category_t` VALUES (3, '鞋靴');
INSERT INTO `category_t` VALUES (4, '手机数码');
INSERT INTO `category_t` VALUES (5, '箱包');
INSERT INTO `category_t` VALUES (6, '桌椅');
INSERT INTO `category_t` VALUES (7, '其他');

-- ----------------------------
-- Table structure for comment_t
-- ----------------------------
DROP TABLE IF EXISTS `comment_t`;
CREATE TABLE `comment_t`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `user_id` int(10) UNSIGNED NOT NULL,
  `goods_id` int(11) NOT NULL,
  `created_date` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 30 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of comment_t
-- ----------------------------
INSERT INTO `comment_t` VALUES (26, 'aaa', 3, 24, '2019-05-12 13:58:16');
INSERT INTO `comment_t` VALUES (27, '新的评论', 3, 24, '2019-05-15 10:27:12');
INSERT INTO `comment_t` VALUES (28, '评论错误', 3, 24, '2019-05-15 10:28:05');
INSERT INTO `comment_t` VALUES (29, '错误时因为有404', 3, 24, '2019-05-15 10:29:16');

-- ----------------------------
-- Table structure for concern_t
-- ----------------------------
DROP TABLE IF EXISTS `concern_t`;
CREATE TABLE `concern_t`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` int(10) UNSIGNED NOT NULL,
  `goods_id` int(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 29 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of concern_t
-- ----------------------------
INSERT INTO `concern_t` VALUES (24, 3, 24);
INSERT INTO `concern_t` VALUES (28, 3, 23);
INSERT INTO `concern_t` VALUES (30, 3, 25);

-- ----------------------------
-- Table structure for goods_t
-- ----------------------------
DROP TABLE IF EXISTS `goods_t`;
CREATE TABLE `goods_t`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `goods_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `image_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `desc` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `concern_count` int(10) UNSIGNED ZEROFILL NULL DEFAULT 0000000000,
  `comment_count` int(10) UNSIGNED ZEROFILL NULL DEFAULT 0000000000,
  `expected_price` float(10, 2) UNSIGNED NULL DEFAULT NULL,
  `created_date` datetime(0) NOT NULL,
  `user_id` int(11) UNSIGNED NOT NULL,
  `category_id` int(10) UNSIGNED NOT NULL,
  `sold` int(1) UNSIGNED ZEROFILL NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of goods_t
-- ----------------------------
INSERT INTO `goods_t` VALUES (25, '分享3', 'https://xiaotaoshare.oss-cn-beijing.aliyuncs.com/2019-05-11/e23f6ac1-5bd2-4a5c-a34c-3bce2747bce7_Chrysanthemum.jpg?x-oss-process=image/resize,w_120,h_100', '123', 0000000001, 0000000000, 223.00, '2019-05-11 15:41:18', 4, 7, 0);

-- ----------------------------
-- Table structure for message_t
-- ----------------------------
DROP TABLE IF EXISTS `message_t`;
CREATE TABLE `message_t`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `from_id` int(50) NOT NULL,
  `to_id` int(50) NOT NULL,
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `has_read` int(1) NULL DEFAULT NULL,
  `created_date` datetime(0) NOT NULL,
  `conversation_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 121 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of message_t
-- ----------------------------
INSERT INTO `message_t` VALUES (101, 0, 4, '用户admin关注了你的资讯:分享3，路径：http://localhost:80/goods/25', 1, '2019-05-12 13:38:10', '0_4');
INSERT INTO `message_t` VALUES (116, 3, 4, '你好啊', 1, '2019-05-12 17:57:14', '3_4');
INSERT INTO `message_t` VALUES (117, 0, 4, '用户admin关注了你的资讯:分享3，路径：http://localhost:80/goods/25', 0, '2019-05-12 20:31:30', '0_4');
INSERT INTO `message_t` VALUES (118, 0, 4, '用户admin关注了你的资讯:分享3，路径：http://localhost:80/goods/25', 0, '2019-05-12 20:31:31', '0_4');
INSERT INTO `message_t` VALUES (119, 0, 4, '用户admin关注了你的资讯:分享3，路径：http://localhost:80/goods/25', 0, '2019-05-14 16:39:05', '0_4');
INSERT INTO `message_t` VALUES (121, 3, 4, '发送的时候应该会有问题', 0, '2019-05-15 11:48:34', '3_4');
INSERT INTO `message_t` VALUES (122, 0, 4, '用户admin关注了你的资讯:分享3，路径：http://localhost:80/goods/25', 0, '2019-05-15 16:39:18', '0_4');
INSERT INTO `message_t` VALUES (123, 0, 4, '用户admin关注了你的资讯:分享3，路径：http://localhost:80/goods/25', 0, '2019-05-15 16:39:21', '0_4');

-- ----------------------------
-- Table structure for user_t
-- ----------------------------
DROP TABLE IF EXISTS `user_t`;
CREATE TABLE `user_t`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `password` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `salt` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `head_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_t
-- ----------------------------
INSERT INTO `user_t` VALUES (0, 'system', '0', '0', 'https://xiaotaoshare.oss-cn-beijing.aliyuncs.com/system.jpg?x-oss-process=image/resize,w_60,h_50');
INSERT INTO `user_t` VALUES (3, 'admin', '012afc5969da79cbde760077122fa85e', '20', 'http://images.nowcoder.com/head/990t.png');
INSERT INTO `user_t` VALUES (4, 'root', 'fb24f65363d473c5d870fa710c29a258', '1a', 'http://images.nowcoder.com/head/612t.png');

SET FOREIGN_KEY_CHECKS = 1;
