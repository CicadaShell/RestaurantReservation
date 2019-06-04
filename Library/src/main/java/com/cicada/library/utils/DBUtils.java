package com.cicada.library.utils;

import android.content.Context;

import com.litesuits.orm.LiteOrm;
import com.litesuits.orm.db.DataBaseConfig;
import com.litesuits.orm.db.assit.QueryBuilder;
import com.litesuits.orm.db.assit.WhereBuilder;
import com.litesuits.orm.db.model.ConflictAlgorithm;

import java.util.List;

/**
 * @Author guocongcong
 * @Date 2018/7/30
 * @Describe 数据库操作工具类
 */

public class DBUtils {

    private static LiteOrm liteOrm;

    public static void createDb(Context mContext, String dbName, int dbVersion) {
        DataBaseConfig config = new DataBaseConfig(mContext);
        config.dbName = dbName;
        config.dbVersion = dbVersion;
        config.debugged = true;
        liteOrm = LiteOrm.newCascadeInstance(config);
    }

    public static LiteOrm getLiteOrm() {
        return liteOrm;
    }

    /**
     * 插入一条记录
     *
     * @param t
     */
    public static <T> void insert(T t) {
        liteOrm.save(t);
    }

    /**
     * 插入所有记录
     *
     * @param list
     */
    public static <T> void insertAll(List<T> list) {
        liteOrm.save(list);
    }

    /**
     * 查询所有
     *
     * @param cla
     * @return
     */
    public static <T> List<T> getQueryAll(Class<T> cla) {
        return liteOrm.query(cla);
    }

    /**
     * 根据主键id查询
     *
     * @param cla
     * @param id
     * @return
     */
    public static <T> T getQueryById(Class<T> cla, String id) {
        return liteOrm.queryById(id, cla);
    }

    /**
     * 查询  某字段 模糊查询
     *
     * @param cla
     * @param field
     * @param value
     * @return
     */
    public static <T> List<T> getQueryByWhere(Class<T> cla, String field, String[] value) {
        return liteOrm.<T>query(new QueryBuilder(cla).where(field, value));
    }

    /**
     * 查询  某字段 等于 Value的值
     *
     * @param cla
     * @param field
     * @param value
     * @return
     */
    public static <T> List<T> getQueryByWhere(Class<T> cla, String field, String value) {
        if (liteOrm != null) {
            return liteOrm.<T>query(new QueryBuilder(cla).whereEquals(field, value));
        } else {
            return null;
        }
    }

    /**
     * 多条件查询  某字段 等于 Value的值
     *
     * @param cla
     * @param field
     * @param value
     * @return
     */
    public static <T> List<T> getQueryByWhere(Class<T> cla, String[] field, String[] value) {
        if (liteOrm != null && field != null && value != null && field.length > 1 && field.length == value.length) {
            QueryBuilder<T> builder = new QueryBuilder(cla);
            builder.whereEquals(field[0], value[0]);
            for (int i = 1; i < field.length; i++) {
                builder.whereAppendAnd().whereEquals(field[i], value[i]);
            }
            return liteOrm.query(builder);
        }
        return null;
    }

    /**
     * 查询数量
     *
     * @param cla
     * @param field
     * @param value
     * @return
     */
    public static <T> long getQuerySizeByWhere(Class<T> cla, String[] field, String[] value) {
        if (liteOrm != null && field != null && value != null && field.length > 1 && field.length == value.length) {
            QueryBuilder<T> builder = new QueryBuilder(cla);
            builder.whereEquals(field[0], value[0]);
            for (int i = 1; i < field.length; i++) {
                builder.whereAppendAnd().whereEquals(field[i], value[i]);
            }
            return liteOrm.queryCount(builder);
        }
        return 0;
    }

    /**
     * 查询  可以指定从1-20，就是分页
     *
     * @param cla
     * @param start
     * @param length
     * @return
     */
    public static <T> List<T> getQueryByLength(Class<T> cla, int start, int length) {
        return liteOrm.<T>query(new QueryBuilder(cla).limit(start, length));
    }

    /**
     * 删除(by 实体)
     *
     * @param t
     */
    public static <T> void delete(T t) {
        liteOrm.delete(t);
    }

    /**
     * 删除(by 主键)
     *
     * @param cla
     * @param field
     * @param value
     * @param <T>
     */
    public static <T> void deleteById(Class<T> cla, String field, String value) {
        liteOrm.delete(new WhereBuilder(cla).where(field + "=?", new String[]{value}));
    }

    /**
     * 删除所有
     *
     * @param cla
     */
    public static <T> void deleteAll(Class<T> cla) {
        liteOrm.deleteAll(cla);
    }

    /**
     * 仅在以存在时更新
     *
     * @param t
     */
    public static <T> void update(T t) {
        liteOrm.update(t, ConflictAlgorithm.Replace);
    }


    public static <T> void updateALL(List<T> list) {
        liteOrm.update(list);
    }
}
