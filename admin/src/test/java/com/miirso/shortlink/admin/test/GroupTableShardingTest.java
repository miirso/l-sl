package com.miirso.shortlink.admin.test;

/**
 * @Package com.miirso.shortlink.admin.test
 * @Author miirso
 * @Date 2024/10/14 11:39
 *
 *
 */
public class GroupTableShardingTest {
    public static void main(String[] args) {
        final String SQL = "CREATE TABLE `t_group_%d` (\n" +
                "    id          BIGINT AUTO_INCREMENT COMMENT 'ID' PRIMARY KEY,\n" +
                "    gid         VARCHAR(32)  NULL COMMENT '分组标识',\n" +
                "    name        VARCHAR(64)  NULL COMMENT '分组名称',\n" +
                "    username    VARCHAR(256) NULL COMMENT '创建分组用户名',\n" +
                "    sort_order  INT          NULL COMMENT '分组排序',\n" +
                "    create_time DATETIME     NULL COMMENT '创建时间',\n" +
                "    update_time DATETIME     NULL COMMENT '修改时间',\n" +
                "    del_flag    TINYINT(1)   NULL COMMENT '删除标识 0：未删除 1：已删除',\n" +
                "    CONSTRAINT idx_unique_username_gid UNIQUE (gid, username)\n" +
                ")\n" +
                "CHARACTER SET utf8mb4\n" +
                "COLLATE utf8mb4_bin;\n";
                // KEYPOINT utf8mb4_bin √ utf8mb4 X


        for (int i = 0; i < 16; i++) {
            System.out.printf(String.format((SQL) + "%n", i));
        }

    }
}
