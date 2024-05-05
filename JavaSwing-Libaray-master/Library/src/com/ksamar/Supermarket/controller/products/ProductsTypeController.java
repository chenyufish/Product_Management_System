package com.ksamar.Supermarket.controller.products;

import com.ksamar.Supermarket.entity.ProductsType;
import com.ksamar.Supermarket.tools.sql.SqlConnect;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 图书种类控制器
 *
 * @author fishman
 * @version 1.0
 */
public class ProductsTypeController {

    /**
     * 获取图书种类
     *
     * @return 图书种类信息
     */
    public static List<ProductsType> getType() {
        List<ProductsType> productsTypes = new ArrayList<>();

        // 获取图书种类语句
        String getTypeSql = "select * from typelist";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = SqlConnect.getConnection().prepareStatement(getTypeSql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ProductsType productsType = new ProductsType();
                productsType.setTypeName(resultSet.getString("type_name"));
                productsTypes.add(productsType);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return productsTypes;
    }

    /**
     * 搜索商品种类
     *
     * @param type 商品种类
     * @return 商品种类
     */
    public static ProductsType searchBookTypeByName(String type) {
        ProductsType productsType = null;

        // 添加图书种类语句
        String selectBookTypeSql = "select * from typelist where type_name = ?";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = SqlConnect.getConnection().prepareStatement(selectBookTypeSql);
            preparedStatement.setString(1, type);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                productsType = new ProductsType();
                productsType.setTypeName(resultSet.getString("type_name"));
                productsType.setTypeId(resultSet.getString("type_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return productsType;
    }

    /**
     * 添加商品种类
     * 0 - 添加失败
     * 1 - 添加成功
     * 2 - 种类存在
     *
     * @return 添加状态
     */
    public static int addType(ProductsType productsType) {
        int result = 0;

        // 判断商品种类是否存在
        ProductsType tempProductsType = searchBookTypeByName(productsType.getTypeName());
        if (tempProductsType != null) {
            return 2;
        }

        // 添加商品种类语句
        String insertBookTypeSql = "insert into typelist (type_name, type_id) VALUE (?, ?)";
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = SqlConnect.getConnection().prepareStatement(insertBookTypeSql);
            preparedStatement.setString(1, productsType.getTypeName());
            preparedStatement.setString(2, productsType.getTypeId());
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }

    /**
     * 删除商品种类
     * 0 - 删除失败
     * 1 - 删除成功
     *
     * @param typeName 商品种类名
     * @return 删除状态
     */
    public static int deleteType(String typeName) {
        int result = 0;

        // 删除图书种类语句
        String deleteBookTypeSql = "delete from typelist where type_name = ?";
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = SqlConnect.getConnection().prepareStatement(deleteBookTypeSql);
            preparedStatement.setString(1, typeName);
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }

    /**
     * 获取商品种类数量
     *
     * @return 商品种类数量
     */
    public static int getTypeCount() {
        int count = 0;

        // 获取商品种类语句
        String getTypeCountSql = "select * from typelist";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = SqlConnect.getConnection().prepareStatement(getTypeCountSql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                count += 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return count;
    }
}
