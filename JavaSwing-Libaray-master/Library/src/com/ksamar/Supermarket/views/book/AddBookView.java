package com.ksamar.Supermarket.views.book;

import com.ksamar.Supermarket.controller.products.ProductsController;
import com.ksamar.Supermarket.entity.Products;
import com.ksamar.Supermarket.tools.form.CheckFormTool;
import com.ksamar.Supermarket.views.message.Message;

/**
 * 添加商品窗口
 *
 * @author fishman
 * @version 1.0
 */
public class AddBookView extends BookFormView {

    /**
     * 添加商品窗口
     */
    private static final AddBookView INSTANCE = new AddBookView();

    /**
     * 添加商品窗口
     */
    private AddBookView() {
        // 设置表单标题
        bookFormText = "添加商品";
        bookFormTextLabel.setText(bookFormText);

        // 添加事件
        bookFormButton.setText("添加");
        bookFormButton.addActionListener(e -> addBook());

        // 设置标题
        setTitle(bookFormText);
    }

    /**
     * 商品
     */
    private void addBook() {
        if (CheckFormTool.checkBookForm(getInstance())) {
            Products products = new Products();
            products.setGroups(String.valueOf(groupsComboBox.getSelectedItem()));
            products.setName(bookNameField.getText());
            //products.setAuthor(bookAuthorField.getText());
            products.setPress(bookPressField.getText());
            products.setPrice(Double.valueOf(bookPriceField.getText()));
            products.setQuantity(Integer.valueOf(bookCountField.getText()));
            products.setIsbn(bookIsbnField.getText());

            int result = ProductsController.addBook(products);

            if (result == 0) {
                Message.showMessage("添加失败", "错误", 0);
            } else if (result == 1) {
                dispose();
                resetForm();
                Message.showMessage("添加成功", "信息", 1);
            } else if (result == 2) {
                Message.showMessage("添加失败，ISBN号码存在", "错误", 0);
            }
        }
    }

    /**
     * 获取添加图书窗体
     *
     * @return 添加图书窗体
     */
    public static AddBookView getInstance() {
        return INSTANCE;
    }
}
