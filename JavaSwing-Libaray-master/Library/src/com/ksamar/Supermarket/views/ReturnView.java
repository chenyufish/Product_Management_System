package com.ksamar.Supermarket.views;

import com.ksamar.Supermarket.controller.products.ProductsController;
import com.ksamar.Supermarket.entity.Borrow;
import com.ksamar.Supermarket.tools.border.Borders;
import com.ksamar.Supermarket.tools.color.Colour;
import com.ksamar.Supermarket.tools.font.Fonts;
import com.ksamar.Supermarket.tools.placeholder.Placeholder;
import com.ksamar.Supermarket.tools.table.LibraryTableModel;
import com.ksamar.Supermarket.views.book.ReturnBookView;
import com.ksamar.Supermarket.views.message.Message;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Vector;

/**
 * 归还图书面板
 *
 * @author fishman
 * @version 1.0
 */
public class ReturnView extends JPanel {

    /**
     * 属性
     */
    private String searchPlaceholder = "请输入商品卡号";
    private int tableRow = -1;

    /**
     * 输入框
     */
    private JTextField searchFiled = new JTextField();

    /**
     * 按钮
     */
    private JButton searchButton = new JButton();
    private JButton returnBookViewButton = new JButton();


    /**
     * 表格
     */
    private JTable bookTable;

    /**
     * 面板
     */
    private JPanel operationPanel = new JPanel();
    private JPanel bookOperationsPanel = new JPanel();
    private JScrollPane bookScrollPane = new JScrollPane();

    /**
     * 表格模型
     */
    private String[] name = {"Id", "商品名", "GTIN号码", "收银员", "工号", "出库时间", "归还时间"};
    private Object[][] tableDate = new Object[0][0];
    private LibraryTableModel tableModel = new LibraryTableModel(tableDate, name);

    /**
     * 图书归还面板
     */
    public ReturnView() {

        // 搜索栏
        searchFiled.setBounds(180, 0, 450, 32);
        searchFiled.setBorder(Borders.searchFiledBorder);
        searchFiled.setFont(Fonts.textField);
        searchFiled.addFocusListener(Placeholder.focusListener(searchFiled, searchPlaceholder));
        Placeholder.setPlaceholder(searchFiled, searchPlaceholder, Color.LIGHT_GRAY);

        // 搜索按钮
        searchButton.setText("搜索");
        searchButton.setBounds(630, 0, 150, 32);
        searchButton.setHorizontalAlignment(SwingConstants.CENTER);
        searchButton.setForeground(Color.WHITE);
        searchButton.setFont(Fonts.button);
        searchButton.setBackground(Colour.C3C8CE7);
        searchButton.setFocusPainted(false);
        searchButton.setBorder(null);
        searchButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        searchButton.addActionListener(e -> searchBookButtonEvent());

        // 操作面板
        operationPanel.setLayout(null);
        operationPanel.setBounds(16, 16, 928, 32);
        operationPanel.setBackground(Color.WHITE);
        operationPanel.add(searchFiled);
        operationPanel.add(searchButton);

        // 图书信息表格
        bookTable = new JTable(tableModel);
        bookTable.setRowHeight(32);
        bookTable.getTableHeader().setReorderingAllowed(false);
        bookTable.getTableHeader().setResizingAllowed(false);
        bookTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                tableRow = bookTable.getSelectedRow();
            }
        });

        // 图书信息面版
        bookScrollPane.setBounds(16, 84, 928, 550);
        bookScrollPane.setViewportView(bookTable);

        // 编辑图书按钮
        returnBookViewButton.setText("退回");
        returnBookViewButton.setBounds(0, 0, 100, 32);
        returnBookViewButton.setHorizontalAlignment(SwingConstants.CENTER);
        returnBookViewButton.setForeground(Color.WHITE);
        returnBookViewButton.setFont(Fonts.button);
        returnBookViewButton.setBackground(Colour.C3C8CE7);
        returnBookViewButton.setFocusPainted(false);
        returnBookViewButton.setBorder(null);
        returnBookViewButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        returnBookViewButton.addActionListener(e -> showReturnBookView());

        // 图书操作面板
        bookOperationsPanel.setLayout(null);
        bookOperationsPanel.setBounds(16, 650, 928, 32);
        bookOperationsPanel.setBackground(Color.WHITE);
        bookOperationsPanel.add(returnBookViewButton);

        // 添加组件
        add(operationPanel);
        add(bookScrollPane);
        add(bookOperationsPanel);

        // 窗体设置
        setSize(960, 768);
        setLayout(null);
        setBackground(Color.WHITE);
        setVisible(true);
    }

    /**
     * 搜索图书按钮事件
     */
    private void searchBookButtonEvent() {
        String text = searchFiled.getText();
        if (!"".equals(text) && !searchPlaceholder.equals(text)) {
            searchBook();
        } else {
            Message.showMessage("请输入要搜索的信息", "错误", 0);
        }
    }

    /**
     * 搜索图书
     */
    private void searchBook() {
        // 设置表格数据
        tableModel.setDataVector(tableDate, name);
        String idCard = searchFiled.getText();
        List<Borrow> borrowArrayList = ProductsController.searchBorrowByIdCard(idCard);
        tableModel.addBorrowRow(borrowArrayList);
    }

    /**
     * 显示编辑删除窗口
     */
    private void showReturnBookView() {
        if (tableRow >= 0) {
            // 获取行数据
            Vector<Object> vector = (Vector) tableModel.getDataVector().elementAt(tableRow);
            ReturnBookView.getInstance().setValue(vector);
            tableRow = -1;
        } else {
            Message.showMessage("请选择需要回退的商品", "错误", 0);
        }
    }
}
