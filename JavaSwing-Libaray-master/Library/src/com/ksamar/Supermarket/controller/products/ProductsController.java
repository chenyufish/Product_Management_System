package com.ksamar.Supermarket.controller.products;

import com.ksamar.Supermarket.controller.log.LogController;
import com.ksamar.Supermarket.controller.user.UserContoller;
import com.ksamar.Supermarket.entity.Products;
import com.ksamar.Supermarket.entity.Borrow;
import com.ksamar.Supermarket.entity.Log;
import com.ksamar.Supermarket.entity.User;
import com.ksamar.Supermarket.tools.sql.SqlConnect;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 图书控制器
 *
 * @author fishman
 * @version 1.0
 */
public class ProductsController {

    /**
     * 获取商品表数据数量
     *
     * @return 商品表数据数量
     */
    public static int getCount() {
        int count = 0;

        // 获取图书表数据数量语句
        String getCountSql = "select * from booklist";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = SqlConnect.getConnection().prepareStatement(getCountSql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                count++;
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

    /**
     * 获取商品数量
     *
     * @return 商品数量
     */
    public static int getBookCount() {
        int bookCount = 0;

        // 获取商品数量语句
        String getBookCountSql = "select * from booklist";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = SqlConnect.getConnection().prepareStatement(getBookCountSql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int temp = resultSet.getInt("quantity");
                bookCount += temp;
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

        return bookCount;
    }

    /**
     * 获取出库商品数量
     *
     * @return 出库商品数量
     */
    public static int getBorrowCount() {
        int borrowCount = 0;

        // 获取借阅图书数量语句
        String getBorrowCountSql = "select * from borrowlist";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = SqlConnect.getConnection().prepareStatement(getBorrowCountSql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                borrowCount++;
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

        return borrowCount;
    }

//    /**
//     * 获取超时图书数量
//     *
//     * @return 超时图书数量
//     */
//    public static int getOvertimeCount() {
//        int overtimeCount = 0;
//
//        // 获取超时图书数量语句
//        String getOvertimeCount = "select * from borrowlist where return_time < NOW()";
//        PreparedStatement preparedStatement = null;
//        ResultSet resultSet = null;
//
//        try {
//            preparedStatement = SqlConnect.getConnection().prepareStatement(getOvertimeCount);
//            resultSet = preparedStatement.executeQuery();
//            while (resultSet.next()) {
//                overtimeCount++;
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            if (preparedStatement != null) {
//                try {
//                    preparedStatement.close();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
//            if (resultSet != null) {
//                try {
//                    resultSet.close();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//
//        return overtimeCount;
//    }

    /**
     * 获取商品信息
     *
     * @param page 页码
     * @param size 大小
     * @return 商品信息列表
     */
    public static List<Products> pageBook(int page, int size) {
        ArrayList<Products> productsArrayList = new ArrayList<>();

        // 获取商品信息语句
        String pageBookSql = "select * from booklist limit ?,?";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = SqlConnect.getConnection().prepareStatement(pageBookSql);
            preparedStatement.setInt(1, (page - 1) * size);
            preparedStatement.setInt(2, size);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Products products = setBookInfo(resultSet);
                productsArrayList.add(products);
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

        return productsArrayList;
    }

    /**
     * 搜索商品信息
     *
     * @param searchType 搜索类型
     * @param text       搜索文本
     * @param page       页码
     * @param size       大小
     */
    public static List<Products> searchBookLike(String searchType, String text, int page, int size) {
        List<Products> productsArrayList = new ArrayList<>();

        // 获取搜索类型
        searchType = getSearchType(searchType);

        // 搜索图书信息语句
        String searchBookLikeSql = "select * from booklist where " + searchType + " like ? limit ?,?";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = SqlConnect.getConnection().prepareStatement(searchBookLikeSql);
            preparedStatement.setString(1, "%" + text + "%");
            preparedStatement.setInt(2, (page - 1) * size);
            preparedStatement.setInt(3, size);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Products products = setBookInfo(resultSet);
                productsArrayList.add(products);
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

        return productsArrayList;
    }

    /**
     * 搜索商品类别长度
     *
     * @param searchType 搜索类型
     * @param text       搜索文本
     * @return 商品类型长度
     */
    public static int searchBookLikeCount(String searchType, String text) {
        int count = 0;

        // 获取搜索类型
        searchType = getSearchType(searchType);

        // 搜索图书类型长度语句
        String searchBookLikeCountSql = "select * from booklist where " + searchType + " like ?";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = SqlConnect.getConnection().prepareStatement(searchBookLikeCountSql);
            preparedStatement.setString(1, "%" + text + "%");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                count++;
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

    /**
     * 添加商品
     * 0 - 添加失败
     * 1 - 添加成功
     * 2 - GTIN 号码存在
     *
     * @param products 商品
     * @return 添加状态
     */
    public static int addBook(Products products) {
        int result = 0;

        // 判断商品 GTIN 号码是否存在
        Products tempProducts = searchBookByIsbn(products.getIsbn());
        if (tempProducts != null) {
            return 2;
        }

        // 插入图书语句
        String insertBookSql = "insert into booklist(groups, name, press, price, quantity, isbn) " +
                "VALUE (?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = SqlConnect.getConnection().prepareStatement(insertBookSql);
            preparedStatement.setString(1, products.getGroups());
            preparedStatement.setString(2, products.getName());
            //preparedStatement.setString(3, products.getAuthor());
            preparedStatement.setString(3, products.getPress());
            preparedStatement.setDouble(4, products.getPrice());
            preparedStatement.setInt(5, products.getQuantity());
            preparedStatement.setString(6, products.getIsbn());
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
     * 编辑商品
     * 0 - 编辑失败
     * 1 - 编辑成功
     * 2 - ISBN 号码存在
     *
     * @param products 商品
     * @return 编辑状态
     */
    public static int editBook(Products products) {
        int result = 0;

        // 判断图书 ISBN 号码是否存在
        String isbn = products.getIsbn();
        Products tempProducts = searchBookByIsbn(isbn);
        if (tempProducts != null && !isbn.equals(tempProducts.getIsbn())) {
            return 2;
        }

        // 编辑图书语句
        String updateBookSql = "update booklist set groups = ?, name = ?, press = ?, price = ?," +
                " quantity = ?, isbn = ? where id = ?";
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = SqlConnect.getConnection().prepareStatement(updateBookSql);
            preparedStatement.setString(1, products.getGroups());
            preparedStatement.setString(2, products.getName());
            //preparedStatement.setString(3, products.getAuthor());
            preparedStatement.setString(3, products.getPress());
            preparedStatement.setDouble(4, products.getPrice());
            preparedStatement.setInt(5, products.getQuantity());
            preparedStatement.setString(6, products.getIsbn());
            preparedStatement.setInt(7, products.getId());
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
     * 删除商品
     * 0 - 删除失败
     * 1 - 删除成功
     *
     * @param id 商品id
     * @return 删除状态
     */
    public static int deleteBook(int id) {
        int result = 0;

        // 删除图书语句
        String deleteBookSql = "delete from booklist where id=?";
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = SqlConnect.getConnection().prepareStatement(deleteBookSql);
            preparedStatement.setInt(1, id);
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
     * 以 ISBN 号码 搜索商品
     *
     * @param isbn ISBN 号码
     * @return Products
     */
    public static Products searchBookByIsbn(String isbn) {
        Products products = null;

        // 搜索图书语句
        String searchBookByIsbn = "select * from booklist where isbn=?";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = SqlConnect.getConnection().prepareStatement(searchBookByIsbn);
            preparedStatement.setString(1, isbn);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                products = setBookInfo(resultSet);
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

        return products;
    }

    /**
     * 购买商品
     * 0 - 购买失败
     * 1 - 购买成功
     * 2 - 商品数量为 0
     * 3 - 用户不存在
     * 4 - 商品出库数量为 0
     *
     * @param borrow 购买信息
     * @return 购买状态
     */
    public static int borrowBook(Borrow borrow) {
        int result = 0;

        // 搜索商品表
        Products products = searchBookByIsbn(borrow.getIsbn());

        // 判断商品是否存在
        if (products == null) {
            return result;
        }
        // 判断商品数量是否为0
        if (products.getQuantity() == 0) {
            return 2;
        }

        // 查询用户信息
        User user = UserContoller.searchUserByUsernameAndIdCardAndPhone(borrow.getUsername(),
                borrow.getIdCard(), borrow.getPhone());

        // 判断用户是否存在
        if (user == null) {
            return 3;
        }
        // 判断用户借书数量是否为0
        if (user.getBookCount() == 0) {
            return 4;
        }
//        Double itemPrice = products.getPrice();  // 假设商品价格存储在book表的price字段中
//        borrow.setItemPrice(itemPrice);

        // 时间
        Calendar calendar = Calendar.getInstance();
        long borrowTimeLong = calendar.getTimeInMillis();
        long returnTimeLong = borrowTimeLong + 604800000L;
        Timestamp borrowTime = new Timestamp(borrowTimeLong);
        Timestamp returnTime = new Timestamp(returnTimeLong);

        // 商品出库语句
        String borrowBookSql = "insert into borrowlist(book_name, isbn, username, id_card, phone" +
                ", borrow_time, return_time) VALUE (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = SqlConnect.getConnection().prepareStatement(borrowBookSql);
            preparedStatement.setString(1, borrow.getBookName());
            preparedStatement.setString(2, borrow.getIsbn());
            preparedStatement.setString(3, borrow.getUsername());
            preparedStatement.setString(4, borrow.getIdCard());
            preparedStatement.setString(5, borrow.getPhone());
            preparedStatement.setTimestamp(6, borrowTime);
            preparedStatement.setTimestamp(7, returnTime);
            //preparedStatement.setDouble(8, borrow.getItemPrice());
            result = preparedStatement.executeUpdate();

            if (result > 0) {
                // 更新图书
                products.setQuantity(products.getQuantity() - 1);
                editBook(products);

                // 更新用户
                user.setBookCount(user.getBookCount() - 1);
                UserContoller.editUser(user);

                // 日志信息
                Log log = new Log();
                log.setTime(borrowTime);
                log.setName(user.getName());
                log.setBookName(borrow.getBookName());
                log.setInfo("出售了 " + borrow.getBookName() + " 商品");
                LogController.addLog(log);
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
        }

        return result;
    }

    /**
     * 搜索商品卡购买的商品信息
     *
     * @param idCard 商品卡号
     * @return 商品卡卡购买的商品信息
     */
    public static List<Borrow> searchBorrowByIdCard(String idCard) {
        List<Borrow> borrowArrayList = new ArrayList<>();

        // 搜索 idCard 借阅图书语句
        String searchBorrowByIdCardSql = "select * from borrowlist where id_card = ?";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = SqlConnect.getConnection().prepareStatement(searchBorrowByIdCardSql);
            preparedStatement.setString(1, idCard);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Borrow borrow = setBorrowInfo(resultSet);
                borrowArrayList.add(borrow);
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

        return borrowArrayList;
    }

//    /**
//     * 搜索借书卡借阅的超时图书信息
//     *
//     * @param idCard 借书卡号
//     * @return 借书卡借阅的超时图书信息
//     */
//    public static List<Borrow> searchBorrowByIdCardAfterNow(String idCard) {
//        List<Borrow> borrowArrayList = new ArrayList<>();
//
//        // 搜索 idCard 借阅图书语句
//        String searchBorrowByIdCardAfterNowSql = "select * from borrowlist where id_card = ? and return_time < NOW()";
//        PreparedStatement preparedStatement = null;
//        ResultSet resultSet = null;
//
//        try {
//            preparedStatement = SqlConnect.getConnection().prepareStatement(searchBorrowByIdCardAfterNowSql);
//            preparedStatement.setString(1, idCard);
//            resultSet = preparedStatement.executeQuery();
//            while (resultSet.next()) {
//                Borrow borrow = setBorrowInfo(resultSet);
//                borrowArrayList.add(borrow);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            if (preparedStatement != null) {
//                try {
//                    preparedStatement.close();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
//            if (resultSet != null) {
//                try {
//                    resultSet.close();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//
//        return borrowArrayList;
//    }

    /**
     * 以 Id 搜索购买信息
     *
     * @param id 购买信息 Id
     * @return Borrow
     */
    private static Borrow searchBorrowById(int id) {
        Borrow borrow = null;

        // 搜索借阅信息语句
        String searchBorrowByIdSql = "select * from borrowlist where id = ?";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = SqlConnect.getConnection().prepareStatement(searchBorrowByIdSql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                borrow = setBorrowInfo(resultSet);
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

        return borrow;
    }

    /**
     * 归还图书
     *
     * @param id 商品ID
     * @return 回退状态
     */
    public static int returnBook(int id) {
        int result = 0;

        // 搜索借阅信息
        Borrow borrow = searchBorrowById(id);
        // 判断是否存在借阅信息
        if (borrow == null) {
            return result;
        }

        // 搜索图书表
        Products products = searchBookByIsbn(borrow.getIsbn());
        // 判断图书是否存在
        if (products == null) {
            return result;
        }

        // 查询用户信息
        User user = UserContoller.searchUserByUsernameAndIdCardAndPhone(borrow.getUsername(),
                borrow.getIdCard(), borrow.getPhone());
        // 判断用户是否存在
        if (user == null) {
            return result;
        }

        // 归还图书语句
        String returnBook = "delete from borrowlist where id = ?";
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = SqlConnect.getConnection().prepareStatement(returnBook);
            preparedStatement.setInt(1, id);
            result = preparedStatement.executeUpdate();

            // 判断是否删除
            if (result > 0) {
                // 更新图书
                products.setQuantity(products.getQuantity() + 1);
                editBook(products);

                // 更新用户
                user.setBookCount(user.getBookCount() + 1);
                UserContoller.editUser(user);

                // 设置时间
                Calendar calendar = Calendar.getInstance();
                long returnTimeLong = calendar.getTimeInMillis();
                Timestamp returnTime = new Timestamp(returnTimeLong);

                // 日志信息
                Log log = new Log();
                log.setTime(returnTime);
                log.setName(user.getName());
                log.setBookName(borrow.getBookName());
                log.setInfo("回退了 " + borrow.getBookName() + " 商品！");
                LogController.addLog(log);
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
        }

        return result;
    }

    /**
     * 设置商品信息
     *
     * @param resultSet 数据库信息
     * @return 商品信息
     * @throws SQLException SQL异常
     */
    public static Products setBookInfo(ResultSet resultSet) throws SQLException {

        // 设置图书信息
        Products products = new Products();
        products.setId(resultSet.getInt("id"));
        products.setGroups(resultSet.getString("groups"));
        products.setName(resultSet.getString("name"));
        //products.setAuthor(resultSet.getString("author"));
        products.setPress(resultSet.getString("press"));
        products.setPrice(resultSet.getDouble("price"));
        products.setQuantity(resultSet.getInt("quantity"));
        products.setIsbn(resultSet.getString("isbn"));

        return products;
    }

    /**
     * 获取搜索类型
     *
     * @param searchType 搜索类型
     * @return 搜索类型
     */
    public static String getSearchType(String searchType) {
        switch (searchType) {
            case "商品名":
                return "name";
//            case "作者":
//                return "author";
            case "厂家":
                return "press";
            case "GTIN 号码":
                return "isbn";
            default:
                return null;
        }
    }

    /**
     * 设置借阅信息
     *
     * @param resultSet 数据库信息
     * @return 借阅信息
     * @throws SQLException SQL异常
     */
    public static Borrow setBorrowInfo(ResultSet resultSet) throws SQLException {
        // 设置借阅信息
        Borrow borrow = new Borrow();
        borrow.setId(resultSet.getInt("id"));
        borrow.setBookName(resultSet.getString("book_name"));
        borrow.setIsbn(resultSet.getString("isbn"));
        borrow.setUsername(resultSet.getString("username"));
        borrow.setIdCard(resultSet.getString("id_card"));
        borrow.setPhone(resultSet.getString("phone"));
        borrow.setBorrowTime(resultSet.getTimestamp("borrow_time"));
        borrow.setReturnTime(resultSet.getTimestamp("return_time"));

        return borrow;
    }
}
