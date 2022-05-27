/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80025
 Source Host           : localhost:3306
 Source Schema         : oauth2_1

 Target Server Type    : MySQL
 Target Server Version : 80025
 File Encoding         : 65001

 Date: 27/05/2022 18:31:40
*/

-- ----------------------------
-- Records of authorities
-- ----------------------------
INSERT INTO `authorities` VALUES ('user', 'USER');

-- ----------------------------
-- Records of oauth2_registered_client
-- ----------------------------
INSERT INTO `oauth2_registered_client` VALUES ('9c1e87e1-dd0e-4290-aeb7-8a4dec9919da', 'messaging-client', '2022-05-27 18:26:37', '{noop}secret', NULL, '9c1e87e1-dd0e-4290-aeb7-8a4dec9919da', 'client_secret_basic', 'refresh_token,client_credentials,authorization_code', 'http://127.0.0.1:1401/login/oauth2/code/messaging-client-oidc,http://127.0.0.1:1401/authorized', 'openid,message.read,message.write', '{\"@class\":\"java.util.Collections$UnmodifiableMap\",\"settings.client.require-proof-key\":false,\"settings.client.require-authorization-consent\":true}', '{\"@class\":\"java.util.Collections$UnmodifiableMap\",\"settings.token.reuse-refresh-tokens\":true,\"settings.token.id-token-signature-algorithm\":[\"org.springframework.security.oauth2.jose.jws.SignatureAlgorithm\",\"RS256\"],\"settings.token.access-token-time-to-live\":[\"java.time.Duration\",300.000000000],\"settings.token.access-token-format\":{\"@class\":\"org.springframework.security.oauth2.core.OAuth2TokenFormat\",\"value\":\"self-contained\"},\"settings.token.refresh-token-time-to-live\":[\"java.time.Duration\",3600.000000000]}');
INSERT INTO `oauth2_registered_client` VALUES ('b9dc4dba-9455-4d3f-abd9-2d5055967749', 'xuxiaowei_client_id', '2022-05-27 18:26:37', '{noop}xuxiaowei_client_secret', NULL, 'b9dc4dba-9455-4d3f-abd9-2d5055967749', 'client_secret_basic', 'refresh_token,implicit,client_credentials,authorization_code', 'http://127.0.0.1:1401/code', 'snsapi_base', '{\"@class\":\"java.util.Collections$UnmodifiableMap\",\"settings.client.require-proof-key\":false,\"settings.client.require-authorization-consent\":true}', '{\"@class\":\"java.util.Collections$UnmodifiableMap\",\"settings.token.reuse-refresh-tokens\":true,\"settings.token.id-token-signature-algorithm\":[\"org.springframework.security.oauth2.jose.jws.SignatureAlgorithm\",\"RS256\"],\"settings.token.access-token-time-to-live\":[\"java.time.Duration\",300.000000000],\"settings.token.access-token-format\":{\"@class\":\"org.springframework.security.oauth2.core.OAuth2TokenFormat\",\"value\":\"self-contained\"},\"settings.token.refresh-token-time-to-live\":[\"java.time.Duration\",3600.000000000]}');

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES ('user', '{bcrypt}$2a$10$UZ03njp0Y3AY.2Rvf46CWO.9NTC5y3/yHX6b3Ha9r4by6tPTIhpre', 1);
