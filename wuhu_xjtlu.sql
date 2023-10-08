/*
 Navicat Premium Data Transfer

 Source Server         : zyh
 Source Server Type    : MySQL
 Source Server Version : 80023
 Source Host           : localhost:3306
 Source Schema         : wuhu_xjtlu

 Target Server Type    : MySQL
 Target Server Version : 80023
 File Encoding         : 65001

 Date: 03/06/2021 22:15:35
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for bms_billboard
-- ----------------------------
DROP TABLE IF EXISTS `bms_billboard`;
CREATE TABLE `bms_billboard`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '公告',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '公告时间',
  `show` tinyint(1) NULL DEFAULT NULL COMMENT '1：展示中，0：过期',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '全站公告' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of bms_billboard
-- ----------------------------
INSERT INTO `bms_billboard` VALUES (2, 'R1.0 开始已实现护眼模式 ,妈妈再也不用担心我的眼睛了。', '2020-11-19 17:16:19', 0);
INSERT INTO `bms_billboard` VALUES (4, '系统已更新至最新版1.0.1', NULL, 1);

-- ----------------------------
-- Table structure for bms_comment
-- ----------------------------
DROP TABLE IF EXISTS `bms_comment`;
CREATE TABLE `bms_comment`  (
  `id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `content` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '内容',
  `user_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '作者ID',
  `topic_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'topic_id',
  `create_time` datetime(0) NOT NULL COMMENT '发布时间',
  `modify_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '评论表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of bms_comment
-- ----------------------------
INSERT INTO `bms_comment` VALUES ('1389123947684470785', 'kokoko', '1387773089956798465', '1389111068289683458', '2021-05-03 15:45:32', NULL);
INSERT INTO `bms_comment` VALUES ('1389124003531628545', 'kokoko00', '1387773089956798465', '1389111068289683458', '2021-05-03 15:45:46', NULL);
INSERT INTO `bms_comment` VALUES ('1389124013178527746', 'kokoko008', '1387773089956798465', '1389111068289683458', '2021-05-03 15:45:48', NULL);
INSERT INTO `bms_comment` VALUES ('1389132795140939777', 'comment', '1387773089956798465', '1389111068289683458', '2021-05-03 16:20:42', NULL);
INSERT INTO `bms_comment` VALUES ('1389136135732097025', 'hhhh', '1387773089956798465', '1389111068289683458', '2021-05-03 16:33:58', NULL);
INSERT INTO `bms_comment` VALUES ('1389136188467081217', 'hhhhll', '1387773089956798465', '1389111068289683458', '2021-05-03 16:34:11', NULL);
INSERT INTO `bms_comment` VALUES ('1389140980887203842', 'hello', '1387773089956798465', '1389111068289683458', '2021-05-03 16:53:14', NULL);
INSERT INTO `bms_comment` VALUES ('1389141024491188226', 'hello world', '1387773089956798465', '1389111068289683458', '2021-05-03 16:53:24', NULL);
INSERT INTO `bms_comment` VALUES ('1389175163936329729', 'commont', '1387773089956798465', '1389111068289683458', '2021-05-03 19:09:03', NULL);
INSERT INTO `bms_comment` VALUES ('1389175215798898689', 'commont y', '1387773089956798465', '1389111068289683458', '2021-05-03 19:09:16', NULL);
INSERT INTO `bms_comment` VALUES ('1389185423669481474', 'comment', '1387773089956798465', '1389111068289683458', '2021-05-03 19:49:49', NULL);
INSERT INTO `bms_comment` VALUES ('1389205027116593154', 'comefutudyf', '1389203322035548162', '1389111068289683458', '2021-05-03 21:07:43', NULL);
INSERT INTO `bms_comment` VALUES ('1389205066333335554', 'comefutudyf dryuu', '1389203322035548162', '1389111068289683458', '2021-05-03 21:07:53', NULL);

-- ----------------------------
-- Table structure for bms_follow
-- ----------------------------
DROP TABLE IF EXISTS `bms_follow`;
CREATE TABLE `bms_follow`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `parent_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '被关注人ID',
  `follower_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '关注人ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 129 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户关注' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of bms_follow
-- ----------------------------
INSERT INTO `bms_follow` VALUES (65, '1329723594994229250', '1317498859501797378');
INSERT INTO `bms_follow` VALUES (85, '1332912847614083073', '1332636310897664002');
INSERT INTO `bms_follow` VALUES (129, '1349290158897311745', '1349618748226658305');

-- ----------------------------
-- Table structure for bms_post
-- ----------------------------
DROP TABLE IF EXISTS `bms_post`;
CREATE TABLE `bms_post`  (
  `id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '标题',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT 'markdown内容',
  `user_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '作者ID',
  `comments` int(0) NOT NULL DEFAULT 0 COMMENT '评论统计',
  `collects` int(0) NOT NULL DEFAULT 0 COMMENT '收藏统计',
  `view` int(0) NOT NULL DEFAULT 0 COMMENT '浏览统计',
  `top` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否置顶，1-是，0-否',
  `essence` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否加精，1-是，0-否',
  `section_id` int(0) NULL DEFAULT 0 COMMENT '专栏ID',
  `create_time` datetime(0) NOT NULL COMMENT '发布时间',
  `modify_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `likes` int(0) NOT NULL DEFAULT 0,
  UNIQUE INDEX `title`(`title`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `create_time`(`create_time`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '话题表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of bms_post
-- ----------------------------
INSERT INTO `bms_post` VALUES ('1333695976536748034', 'Bytedance big data development engineer(internship)', '[Job Responsibilities]: 1. Responsible for the development of byte-beating e-commerce-related business data warehouses, building e-commerce data centers; 2. Building data warehouses based on platforms such as Hive/Flink, real-time data warehouse construction, and ETL development; 3. Responsible for the implementation of big data capabilities in product functions, promote product digitization and intelligence, and empower business; 4. Abstract business logic and build an industry-leading big data center.[Job requirements]: 1. Computer-related major, solid foundation, strong enthusiasm for learning new technologies; 2. Proficiency in at least one of C++/Java/python/Go, and good programming habits. 3. Familiar with SQL language, at least familiar with one of the common relational databases such as MySQL, Oracle and DB2. 4. Good logical thinking ability, strong data analysis and problem-solving ability; 5. Internship location: Beijing-Zhongguancun-Internet Finance Center Internship time: 6 months[Benefits] 400 yuan/day, free three meals a day + afternoon tea, subsidies for renting a house near the company, 1500 yuan/month, free gym[Resume Delivery] If you are interested, please send your resume to lilei.lee@bytedance.com, fill in your name and delivery position in the subject of the email', '1387724493781831682', 0, 0, 46, b'1', b'1', 0, '2020-12-01 16:54:34', '2020-12-01 17:05:00', 0);
INSERT INTO `bms_post` VALUES ('1333676096156528641', 'Bytedance video intern', 'Description of job 1. Responsible for all kinds of short video advertisement editing, planning and daily performance;2. Able to quickly understand the content of the shooting script, and actively cooperate with the director for video shooting;3. Finish video editing and other post-production work on time and efficiently to ensure video quality;4. Assist the team\'s internal video script planning and shooting work.Job Requirements 1. Bachelor degree or above, major in broadcasting and hosting, director, digital media and other video related majors are preferred;2. Familiar with the short video creation process, proficient in PR, Final and other editing software, and have certain planning or shooting capabilities;3. Cheerful personality, strong communication skills and execution ability, with a high sense of responsibility and teamwork spirit;4. Love fashion, outstanding aesthetics, good image and temperament, and appealing;5. At least 3 months of internship and more than 3 days of attendance per week.\n', '1387724493781831682', 0, 0, 40, b'0', b'0', 0, '2020-12-01 15:35:34', NULL, 0);
INSERT INTO `bms_post` VALUES ('1333668258587750401', 'Free trial speaking and writing lessons!', 'Dear students, we have organized speaking and writing IELTS lessons for you to experience on Sunday. Delicious snacks will be offered! Location: XXXX', '1387724493781831682', 0, 0, 7, b'0', b'0', 0, '2020-12-01 15:04:26', '2020-12-01 16:49:14', 0);
INSERT INTO `bms_post` VALUES ('1333447953558765569', 'Group Membership recruitment', 'For now, we have 5 group members (2 IMIS students+3 ICS students). We would like to recruit 4 members into our family. If you are good at coding, and have a sense of responsibility, please contact me and join us!!!Wechat: 00233333333 (Please note the major and name when adding)\n', '1387724493781831682', 0, 0, 78, b'0', b'0', 0, '2020-12-01 00:29:01', '2020-12-03 23:56:51', 0);
INSERT INTO `bms_post` VALUES ('1349631541260595202', 'Head of Front-end Development(internship)', 'Description of job1. Responsible for the design and development of the company\'s commercial monetization business, complete the front-end architecture design of the project, and lead the team to complete the specific business landing;2. Continuously improve the company\'s front-end engineering system, and promote front-end automation and tooling;3. Establish a clear technological development route for the team, and build a unified enterprise-level front-end operational framework and component library;4. Excellent technical acumen, keep up with technological development, and apply the results to the organization;5. Create a good front-end technology atmosphere, lead and promote team growth;6. Responsible for solving various pain points in the front-end development process and leading technical research.Job Requirements 1. Bachelor degree or above, more than five years of front-end research and development experience, and team management experience;2. Proficient in various web front-end technologies, familiar with common technology development modes such as object-oriented and functional programming;3. Understand the latest Web standards and conversion strategies, and have a practical understanding and practical experience of ES6 usability related knowledge;4. Have a deep understanding of the front-end MV* framework, and at least one large-scale project experience in a modern library or framework (such as: Backbone, Angular, React, Vue);5. Practical experience in front-end architecture design, performance optimization, componentization and modularization;6. Familiar with front-end engineering tools, such as Fis3, Webpack, Rollup, etc.;7. Familiar with a server-side language and have certain development experience (such as: Node, Python, PHP, Java);8. Have a certain understanding and practice of code quality optimization methods such as automated testing, unit testing, and integration testing;9. Enthusiastic about learning and sharing, like reading technical documents and books, and own open source community projects or technical blogs.', '1387724493781831682', 0, 0, 37, b'0', b'0', 0, '2021-01-14 16:16:49', '2021-01-14 16:18:53', 0);
INSERT INTO `bms_post` VALUES ('1332681213400817665', 'IELTS reference books selling', 'I have passed the IELTS exam and i want to sell some reference books which are useful from my perspective, for those who are interested in these books, feel free to contact with me, my Wechat is 23875964978.', '1387724493781831682', 0, 0, 55, b'0', b'0', 1, '2020-11-28 21:42:16', '2020-11-29 15:00:42', 1);
INSERT INTO `bms_post` VALUES ('1335149981733449729', 'IntelliJ IDEA environmental problem', 'Please follow step by step the direction given in Lab0.pdf and Lab1.pdfIn Lab1.pdf you do not run the program,but Open/Import the skeleton file first,and then run the test cases using JUnit .Additionally, if you want run the Hailstone program (to test it), you will need a main method.You can follow the lecture for doing that, we gave a demo.However, this is not advisable.It\'s better to test your program using a separate test class with JUnit testing.', '1387724493781831682', 0, 0, 45, b'0', b'0', 0, '2020-12-05 17:12:16', '2021-01-14 13:06:16', 25);
INSERT INTO `bms_post` VALUES ('1332682473151635458', 'Summer GRE classes are opening', 'Do you want to improve your GRE grades in order to get into a good university? We have 5% discount for students who participate into our classes by the end of this month. Contact me wtih 987654321. There is no time like the present!', '1387724493781831682', 0, 0, 95, b'0', b'0', 2, '2020-11-28 21:47:16', '2020-11-30 19:40:22', 0);
INSERT INTO `bms_post` VALUES ('1332650453142827009', '[CPT204] Notes from lectures', 'Hey guys, i have summarized all the notes and key points from our lectures and made a brief knowledge list these days. I\'m glad to share it with you,  just click the link and download it. (附上链接)Good luck with your final exam!', '1387724493781831682', 0, 0, 29, b'0', b'0', 1, '2020-11-28 19:40:02', '2020-11-28 19:46:39', 0);
INSERT INTO `bms_post` VALUES ('1349362401438392322', '[Lost and Found] Found an Airpods in SC176', 'I found an Airpods in SC176 at 6 p.m. today and i gave it to the security guard, please go there to find it  : )', '1387724493781831682', 0, 0, 27, b'0', b'0', 0, '2021-01-13 22:27:21', '2021-01-14 17:30:13', 0);
INSERT INTO `bms_post` VALUES ('1334481725322297346', '[Lost and Found] Lost a basketball ', ' I lost a basketball beside our playground this afternoon, which is grey and the brand is Spalding. People who find it please contact with me. My phone number is 11011912090 , add my Wechat or just call me, thanks!  （可以放张图）', '1387724493781831682', 0, 0, 47, b'0', b'0', 0, '2020-12-03 20:56:51', NULL, 7);

-- ----------------------------
-- Table structure for bms_post_tag
-- ----------------------------
DROP TABLE IF EXISTS `bms_post_tag`;
CREATE TABLE `bms_post_tag`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `tag_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '标签ID',
  `topic_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '话题ID',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `tag_id`(`tag_id`) USING BTREE,
  INDEX `topic_id`(`topic_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 85 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '话题-标签 中间表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of bms_post_tag
-- ----------------------------
INSERT INTO `bms_post_tag` VALUES (1, '1349362401597775874', '1334481725322297346');
INSERT INTO `bms_post_tag` VALUES (2, '1349362401597775874', '1349362401438392322');
INSERT INTO `bms_post_tag` VALUES (3, '1332681213568589825', '1332650453142827009');
INSERT INTO `bms_post_tag` VALUES (4, '1349362401597775879', '1332682473151635458');
INSERT INTO `bms_post_tag` VALUES (5, '1332681213568589825', '1335149981733449729');
INSERT INTO `bms_post_tag` VALUES (6, '1332682473218744321', '1332681213400817665');
INSERT INTO `bms_post_tag` VALUES (7, '1332681213631504385', '1349631541260595202');
INSERT INTO `bms_post_tag` VALUES (8, '1332681213568589825', '1333447953558765569');
INSERT INTO `bms_post_tag` VALUES (9, '1349362401597775879', '1333668258587750401');
INSERT INTO `bms_post_tag` VALUES (10, '1332681213631504385', '1333676096156528641');
INSERT INTO `bms_post_tag` VALUES (11, '1332681213631504385', '1333695976536748034');
INSERT INTO `bms_post_tag` VALUES (68, '1389111068419706881', '1389111068289683458');

-- ----------------------------
-- Table structure for bms_post_user_like
-- ----------------------------
DROP TABLE IF EXISTS `bms_post_user_like`;
CREATE TABLE `bms_post_user_like`  (
  `id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `post_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '帖子ID',
  `user_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户ID(用户名)',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `post_id`(`post_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '帖子-用户-点赞记录 中间表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of bms_post_user_like
-- ----------------------------
INSERT INTO `bms_post_user_like` VALUES ('1385578152716718082', '1335149981733449729', '1385485731014193153');
INSERT INTO `bms_post_user_like` VALUES ('1386173852999503873', '1334481725322297346', '1385485731014193153');
INSERT INTO `bms_post_user_like` VALUES ('1389211952315207681', '1387730356760334337', '1387773089956798465');
INSERT INTO `bms_post_user_like` VALUES ('1395339777359261697', '1389212575915937793', '1395334946146021378');

-- ----------------------------
-- Table structure for bms_promotion
-- ----------------------------
DROP TABLE IF EXISTS `bms_promotion`;
CREATE TABLE `bms_promotion`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '广告标题',
  `link` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '广告(图片)链接',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '说明',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '广告推广表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of bms_promotion
-- ----------------------------
INSERT INTO `bms_promotion` VALUES (1, '开发者头条', 'https://juejin.cn/', '开发者头条');
INSERT INTO `bms_promotion` VALUES (2, '并发编程网', 'https://juejin.cn/', '并发编程网');
INSERT INTO `bms_promotion` VALUES (3, '掘金', 'https://juejin.cn/', '掘金');

-- ----------------------------
-- Table structure for bms_tag
-- ----------------------------
DROP TABLE IF EXISTS `bms_tag`;
CREATE TABLE `bms_tag`  (
  `id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '标签ID',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '标签',
  `topic_count` int(0) NOT NULL DEFAULT 0 COMMENT '关联话题',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name`(`name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '标签表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of bms_tag
-- ----------------------------
INSERT INTO `bms_tag` VALUES ('1332681213568589825', 'academic', 2);
INSERT INTO `bms_tag` VALUES ('1332681213631504385', 'internship', 1);
INSERT INTO `bms_tag` VALUES ('1332682473218744321', 'secondhand', 1);
INSERT INTO `bms_tag` VALUES ('1333432347107143681', 'entertainment', 1);
INSERT INTO `bms_tag` VALUES ('1349362401597775874', 'lost and found', 1);
INSERT INTO `bms_tag` VALUES ('1349362401597775879', 'studyabroad', 4);
INSERT INTO `bms_tag` VALUES ('1387992303678263297', 'lostandfound', 2);
INSERT INTO `bms_tag` VALUES ('1389099634717638657', 'tag', 5);
INSERT INTO `bms_tag` VALUES ('1389212439240347650', 'sadf', 1);
INSERT INTO `bms_tag` VALUES ('1389397264198873090', 'asdf', 1);
INSERT INTO `bms_tag` VALUES ('1395323430923411459', 'd', 1);
INSERT INTO `bms_tag` VALUES ('1395338783174995970', 'Study Abroad', 1);
INSERT INTO `bms_tag` VALUES ('1395338862711582721', 'Academic source', 1);

-- ----------------------------
-- Table structure for bms_tip
-- ----------------------------
DROP TABLE IF EXISTS `bms_tip`;
CREATE TABLE `bms_tip`  (
  `id` int(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `content` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '内容',
  `author` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '作者',
  `type` tinyint(0) NOT NULL COMMENT '1：使用，0：过期',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '每日赠言' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of bms_tip
-- ----------------------------
INSERT INTO `bms_tip` VALUES (1, '多锉出快锯，多做长知识。', '佚名', 1);
INSERT INTO `bms_tip` VALUES (2, '未来总留着什么给对它抱有信心的人。', '佚名', 1);
INSERT INTO `bms_tip` VALUES (3, '一个人的智慧不够用，两个人的智慧用不完。', '谚语', 1);
INSERT INTO `bms_tip` VALUES (4, '十个指头按不住十个跳蚤', '傣族', 1);
INSERT INTO `bms_tip` VALUES (5, '言不信者，行不果。', '墨子', 1);
INSERT INTO `bms_tip` VALUES (6, '攀援而登，箕踞而遨，则几数州之土壤，皆在衽席之下。', '柳宗元', 1);
INSERT INTO `bms_tip` VALUES (7, '美德大都包含在良好的习惯之内。', '帕利克', 1);
INSERT INTO `bms_tip` VALUES (8, '人有不及，可以情恕。', '《晋书》', 1);
INSERT INTO `bms_tip` VALUES (9, '明·吴惟顺', '法不传六耳', 1);
INSERT INTO `bms_tip` VALUES (10, '真正的朋友应该说真话，不管那话多么尖锐。', '奥斯特洛夫斯基', 1);
INSERT INTO `bms_tip` VALUES (11, '时间是一切财富中最宝贵的财富。', '德奥弗拉斯多', 1);
INSERT INTO `bms_tip` VALUES (12, '看人下菜碟', '民谚', 1);
INSERT INTO `bms_tip` VALUES (13, '如果不是怕别人反感，女人决不会保持完整的严肃。', '拉罗什福科', 1);
INSERT INTO `bms_tip` VALUES (14, '爱是春暖花开时对你满满的笑意', '佚名', 1);
INSERT INTO `bms_tip` VALUES (15, '希望是坚韧的拐杖，忍耐是旅行袋，携带它们，人可以登上永恒之旅。', '罗素', 1);
INSERT INTO `bms_tip` VALUES (18, '天国般的幸福，存在于对真爱的希望。', '佚名', 1);
INSERT INTO `bms_tip` VALUES (19, '我们现在必须完全保持党的纪律，否则一切都会陷入污泥中。', '马克思', 1);
INSERT INTO `bms_tip` VALUES (20, '在科学上没有平坦的大道，只有不畏劳苦沿着陡峭山路攀登的人，才有希望达到光辉的顶点。', '马克思', 1);
INSERT INTO `bms_tip` VALUES (21, '懒惰的马嫌路远', '蒙古', 1);
INSERT INTO `bms_tip` VALUES (22, '别忘记热水是由冷水烧成的', '非洲', 1);

-- ----------------------------
-- Table structure for bms_verification
-- ----------------------------
DROP TABLE IF EXISTS `bms_verification`;
CREATE TABLE `bms_verification`  (
  `id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `verification` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '验证码',
  `user_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户ID',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `post_id`(`verification`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `create_time`(`create_time`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '邮箱验证码表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of bms_verification
-- ----------------------------
INSERT INTO `bms_verification` VALUES ('1389210570153574402', '2774', '1389210570128408577', '2021-05-03 21:29:45');
INSERT INTO `bms_verification` VALUES ('1392731293757308929', '2000', '1392731293618896898', '2021-05-13 14:39:51');
INSERT INTO `bms_verification` VALUES ('1395334946221518849', '2598', '1395334946146021378', '2021-05-20 19:05:50');

-- ----------------------------
-- Table structure for ums_user
-- ----------------------------
DROP TABLE IF EXISTS `ums_user`;
CREATE TABLE `ums_user`  (
  `id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户ID',
  `username` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '用户名',
  `alias` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户昵称',
  `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '密码',
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像',
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机',
  `score` int(0) NOT NULL DEFAULT 0 COMMENT '积分',
  `token` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT 'token',
  `bio` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '个人简介',
  `active` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否激活，1：是，0：否',
  `status` bit(1) NULL DEFAULT b'1' COMMENT '状态，1：使用，0：停用',
  `role_id` int(0) NULL DEFAULT NULL COMMENT '用户角色',
  `create_time` datetime(0) NOT NULL COMMENT '加入时间',
  `modify_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `user_name`(`username`) USING BTREE,
  INDEX `user_email`(`email`) USING BTREE,
  INDEX `user_create_time`(`create_time`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ums_user
-- ----------------------------
INSERT INTO `ums_user` VALUES ('1385227599084085249', 'qwer', 'qwertyyy', 'af618939e8ee1a24042e46c229f43800', '4', 'qwer@student.xjtlu.edu.cn', NULL, 0, '', 'Student', b'1', b'1', NULL, '2021-04-22 21:42:51', NULL);
INSERT INTO `ums_user` VALUES ('1385485731014193153', 'test', 'testhhh', '098f6bcd4621d373cade4e832627b4f6', '9', 'test@student.xjtlu.edu.cn', NULL, 3, '', 'Student', b'1', b'1', NULL, '2021-04-23 14:48:34', NULL);
INSERT INTO `ums_user` VALUES ('1387724493781831682', 'bob', 'bob', 'e10adc3949ba59abbe56e057f20f883e', 'abc34a98-a32b-4629-8a0a-c5f0ab8ddab0.jpg', '143514@student.xjtlu.edu.cn', NULL, 2, '', 'Student', b'1', b'1', NULL, '2021-04-29 19:04:37', NULL);
INSERT INTO `ums_user` VALUES ('1389395159060918273', 'user1', 'user2', 'e10adc3949ba59abbe56e057f20f883e', '', 'user1@student.xjtlu.edu.cn', NULL, 2, '', 'Student', b'1', b'1', NULL, '2021-05-04 09:43:14', NULL);
INSERT INTO `ums_user` VALUES ('1392731293618896898', 'akm', 'new_alias', 'fae0b27c451c728867a567e8c1bb4e53', 'null', 'akm@student.xjtlu.edu.cn', NULL, 0, '', '学生', b'1', b'1', NULL, '2021-05-13 14:39:51', NULL);
INSERT INTO `ums_user` VALUES ('1395334946146021378', 'asdf', 'asdf', '912ec803b2ce49e4a541068d495ab570', '635d1406-f2d4-446f-b90d-b2b7554e99a5.jpg', 'asdf@student.xjtlu.edu.cn', NULL, 2, '', '学生', b'1', b'1', NULL, '2021-05-20 19:05:50', NULL);
INSERT INTO `ums_user` VALUES ('1400453821875388417', 'admin', 'admin', 'e10adc3949ba59abbe56e057f20f883e', NULL, 'yihan.zhang18@student.xjtlu.edu.cn', NULL, 0, '', '学生', b'1', b'1', NULL, '2021-06-03 22:06:25', NULL);

SET FOREIGN_KEY_CHECKS = 1;
