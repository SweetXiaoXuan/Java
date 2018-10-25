package com.rent.merchant.common.utils;

/**
 * MIT License
 *
 * Copyright (c) 2018 liumengwei
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 * @author  liumengwei
 * @since   V1.0
 * @date 2018/10/25
 */

import com.rent.merchant.daoBean.Merchant;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * Sql stitching tool class.
 *
 */
public class SqlUtil {
    private static Map<String, String> sqlMap = new HashMap<>();
    static {
        sqlMap.put("select", "select %s from %s where 1 = 1");
        sqlMap.put("insert", "insert into %s%s vales(");
        sqlMap.put("update", "update %s set ");
        sqlMap.put("delete", "delete from %s where 1 = 1");
        sqlMap.put("count", "select count(%s) from %s where 1 = 1");
        sqlMap.put("and", " and ");
    }

    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>();
        map.put("name", "aaa");
        map.put("pass", "aaaaa");
        System.out.println(conditionalQuery(map, Merchant.class, "select"));


        System.out.println(conditionalDeletion(map, Merchant.class, "delete"));


        System.out.println(conditionalModify(map, Merchant.class, "update"));



        System.out.println(insert(new ArrayList<>(map.keySet()), new ArrayList<>(map.values()), Merchant.class, "insert"));
    }

    /**
     * Multi-condition query and get total sql stitching
     * @see com.rent.merchant.common.utils.SqlUtil#sqlMap select, count
     * @param param conditional parameter(can be empty)
     * @param clazz class to be queried(not null)
     * @param operating sql type
     * @param <K> the element type
     * @param <V> the element type
     * @return java.lang.String
     * @see java.util.Map
     * @see java.lang.Class
     * @see java.lang.NullPointerException
     */
    public static <K, V> String conditionalQuery(Map<K, V> param, Class clazz, String operating) {
        if (clazz == null) throw new NullPointerException();
        StringBuffer sql = new StringBuffer(sqlMap.get(operating));
        Set<K> cols = param.keySet();
        param.entrySet().forEach(
                map -> sql.append(sqlMap.get("and")).append(map.getKey()).append(" = ").append(map.getValue())
        );
        String str = StringUtils.join(cols.toArray(), ",");
        return String.format(sql.toString(), isNull(cols) ? "*" : str, clazz.getName());
    }

    /**
     * Judge empty
     * @param t param
     * @param <T> the element type
     * @return boolean
     * @see boolean
     * @see java.util.List
     * @see List#size()
     */
    private static <T> boolean isNull(Set<T> t) {
        return t == null || t.size() < 1;
    }

    /**
     * Delete sql stitching according to conditions
     * @see com.rent.merchant.common.utils.SqlUtil#sqlMap delete
     * @param param conditional parameter(can be empty)
     * @param clazz class to be queried(not null)
     * @param operating sql type
     * @param <K> the element type
     * @param <V> the element type
     * @return java.lang.String
     * @see java.util.Map
     * @see java.lang.Class
     * @see java.lang.NullPointerException
     */
    public static <K, V> String conditionalDeletion(Map<K, V> param, Class clazz, String operating) {
        if (clazz == null) throw new NullPointerException();
        StringBuffer sql = new StringBuffer(sqlMap.get(operating));
        param.entrySet().forEach(
                map -> sql.append(sqlMap.get("and")).append(map.getKey()).append(" = ").append(map.getValue())
        );
        return String.format(sql.toString(), clazz.getName());
    }

    /**
     * Condition modification sql stitching
     * @see com.rent.merchant.common.utils.SqlUtil#sqlMap update
     * @param param conditional parameter(can be empty)
     * @param clazz class to be queried(not null)
     * @param operating sql type
     * @param <K> the element type
     * @param <V> the element type
     * @return java.lang.String
     * @see java.util.Map
     * @see java.lang.Class
     * @see java.lang.NullPointerException
     */
    public static <K, V> String conditionalModify(Map<K, V> param, Class clazz, String operating) {
        if (clazz == null) throw new NullPointerException();
        StringBuffer sql = new StringBuffer(sqlMap.get(operating));
        param.entrySet().forEach(
                map -> sql.append(map.getKey()).append(" = ").append(map.getValue()).append(", ")
        );
        String sqlSub = sql.substring(0, sql.length() - 1);
        return String.format(sqlSub + ")", clazz.getName());
    }

    /**
     * Splice insert sql statement
     * @see com.rent.merchant.common.utils.SqlUtil#sqlMap insert
     * @param key clos name
     * @param value column corresponding value
     * @param clazz class to be queried(not null)
     * @param operating sql type
     * @return java.lang.String
     * @see java.util.List
     * @see java.lang.Class
     * @see java.lang.NullPointerException
     */
    public static String insert(List<String> key, List<String> value, Class clazz, String operating) {
        if (clazz == null) throw new NullPointerException();
        if (key == null || key.size() < 1 || value == null || value.size() < 1) return null;
        StringBuffer sql = new StringBuffer(sqlMap.get(operating)).append(String.join(", ", value)).append(")");
        StringBuffer clos = new StringBuffer("(").append(String.join(", ", key)).append(")");
        return String.format(sql.toString(), clos, clazz.getName());
    }
}
