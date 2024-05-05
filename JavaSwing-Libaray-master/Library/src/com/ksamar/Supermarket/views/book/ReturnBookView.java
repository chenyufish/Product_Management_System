package com.ksamar.Supermarket.views.book;

import com.ksamar.Supermarket.controller.products.ProductsController;
import com.ksamar.Supermarket.tools.color.Colour;
import com.ksamar.Supermarket.tools.image.Images;
import com.ksamar.Supermarket.views.message.Message;

import java.util.Vector;

/**
 * 归还商品窗口
 *
 * @author fishman
 * @version 1.0
 */
public class ReturnBookView extends Message {

    /**
     * 商品回退窗口
     */
    private static final ReturnBookView INSTANCE = new ReturnBookView();

    /**
     * 商品回退窗口
     */
    private ReturnBookView() {

        // 归还商品面板标题
        titleText = "商品退回";
        messageIconLabel.setIcon(Images.errorIcon);
        messageTitleLabel.setText(titleText);

        // 归还商品事件按钮
        confirmButton.setBackground(Colour.CE54D52);
        confirmButton.addActionListener(e -> returnBook());
    }

    /**
     * 设置信息
     */
    public void setValue(Vector vector) {
        setVisible(true);
        setLocationRelativeTo(null);
        messageTextLabel.setText("确认退回 " + vector.get(1).toString() + " 该商品吗？");
        confirmButton.setName(vector.get(0).toString());
    }

    /**
     * 归还商品
     */
    public void returnBook() {
        dispose();
        int id = Integer.parseInt(confirmButton.getName());
        int result = ProductsController.returnBook(id);
        if (result == 0) {
            Message.showMessage("入库失败", "错误", 0);
        } else if (result == 1) {
            Message.showMessage("入库成功", "信息", 1);
        }
    }

    /**
     * 获取归还商品窗口
     *
     * @return 归还商品窗口
     */
    public static ReturnBookView getInstance() {
        return INSTANCE;
    }
}
