package com.ksamar.Supermarket.views.book;

import com.ksamar.Supermarket.controller.products.ProductsController;
import com.ksamar.Supermarket.tools.color.Colour;
import com.ksamar.Supermarket.tools.image.Images;
import com.ksamar.Supermarket.views.message.Message;

import java.util.Vector;

/**
 * 删除商品窗口
 *

 */
public class DeleteBookView extends Message {

    /**
     * 删除商品窗口
     */
    private static final DeleteBookView INSTANCE = new DeleteBookView();

    /**
     * 删除商品窗口
     */
    private DeleteBookView() {
        // 删除商品面板标题
        titleText = "商品出库";
        messageIconLabel.setIcon(Images.errorIcon);
        messageTitleLabel.setText(titleText);

        // 删除商品事件按钮
        confirmButton.setBackground(Colour.CE54D52);
        confirmButton.addActionListener(e -> deleteBook());
    }

    /**
     * 设置信息
     */
    public void setValue(Vector vector) {
        setVisible(true);
        setLocationRelativeTo(null);
        messageTextLabel.setText("确认出库 " + vector.get(2).toString() + " 该商品吗？");
        confirmButton.setName(vector.get(0).toString());
    }

    /**
     * 删除商品
     */
    private void deleteBook() {
        dispose();
        int id = Integer.parseInt(confirmButton.getName());
        int result = ProductsController.deleteBook(id);
        if (result == 0) {
            Message.showMessage("出库失败", "错误", 0);
        } else if (result == 1) {
            Message.showMessage("出库成功", "信息", 1);
        }
    }

    /**
     * 获取删除商品窗口
     *
     * @return 删除商品窗口
     */
    public static DeleteBookView getInstance() {
        return INSTANCE;
    }
}
