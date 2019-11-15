/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dev.stekos.cashregister.views;

import dev.stekos.cashregister.dao.CategoryDAO;
import dev.stekos.cashregister.controllers.exceptions.NonexistentEntityException;
import dev.stekos.cashregister.dao.SubCategoryDAO;
import dev.stekos.cashregister.models.Category;
import dev.stekos.cashregister.models.SubCategory;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Stekos
 */
public class CategoryForm extends javax.swing.JPanel {
    private final CategoryDAO categoryDAO = new CategoryDAO();
    private final SubCategoryDAO subCategoryDAO = new SubCategoryDAO();

    /**
     * Creates new form CategoryPane
     */
    public CategoryForm() {
        initComponents();
        idTxt.setEditable(false);
        addBtn.setEnabled(false);
        editBtn.setEnabled(false);;
        cancelBtn.setEnabled(false);
        deleteBtn.setEnabled(false);
        categoriesTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        categoriesTable.getTableHeader().setOpaque(false);
        categoriesTable.getTableHeader().setBackground(new Color(32, 136, 203));
        categoriesTable.getTableHeader().setForeground(new Color(255, 255, 255));
        categoriesTable.setRowHeight(25);
        categoriesTable.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
            if(categoriesTable.getSelectedRow() != -1) {
                try {
                    addBtn.setEnabled(false);
                    editBtn.setEnabled(true);
                    cancelBtn.setEnabled(true);
                    deleteBtn.setEnabled(true);
                    int selectedRow = categoriesTable.getSelectedRow();
                    idTxt.setText((categoriesTable.getValueAt(selectedRow, 0).toString()));
                    typeTxt.setText((categoriesTable.getValueAt(selectedRow, 1).toString()));
                    descTxt.setText(categoriesTable.getValueAt(selectedRow, 2).toString());
                    Date date = new SimpleDateFormat("dd-MM-yyyy").parse((categoriesTable.getValueAt(selectedRow, 3)).toString());
                    addDc.setDate(date);
                    List<SubCategory> subCategories = subCategoryDAO.getByCategoryId(Integer.parseInt(idTxt.getText()));
                    fillSubCategoriesTable(subCategories);
                } catch (ParseException ex) {
                    Logger.getLogger(CategoryForm.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        fillCategoriesTable();
        
        fillCategoriesComboBox();
        subCatIdTxt.setEditable(false);
        subCatAddBtn.setEnabled(false);
        subCatUpdateBtn.setEnabled(false);
        subCatCancelBtn.setEnabled(false);
        subCatDelBtn.setEnabled(false);
        subCategoriesTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        subCategoriesTable.getTableHeader().setOpaque(false);
        subCategoriesTable.getTableHeader().setBackground(new Color(32, 136, 203));
        subCategoriesTable.getTableHeader().setForeground(new Color(255, 255, 255));
        subCategoriesTable.setRowHeight(25);
        subCategoriesTable.getSelectionModel().addListSelectionListener(((e) -> {
            if(subCategoriesTable.getSelectedRow() != -1) {
                try {
                    subCatAddBtn.setEnabled(false);
                    subCatUpdateBtn.setEnabled(true);
                    subCatCancelBtn.setEnabled(true);
                    subCatDelBtn.setEnabled(true);
                    int selectedRow = subCategoriesTable.getSelectedRow();
                    subCatIdTxt.setText((subCategoriesTable.getValueAt(selectedRow, 0)).toString());
                    SubCategory subCategory = subCategoryDAO.getById(Integer.parseInt(subCatIdTxt.getText()));
                    Category category = categoryDAO.getById(subCategory.getCategoryId());
                    subCatCb.setSelectedItem(category.getType());
                    subCatLabelTxt.setText((subCategoriesTable.getValueAt(selectedRow, 1)).toString());
                    subCatDescTxt.setText((subCategoriesTable.getValueAt(selectedRow, 2)).toString());
                    Date date = new SimpleDateFormat("dd-MM-yyyy").parse((subCategoriesTable.getValueAt(selectedRow, 3)).toString());
                    subCatAddDc.setDate(date);
                } catch (ParseException ex) {
                    Logger.getLogger(CategoryForm.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }));
        fillSubCategoriesTable();
    }
    
    @Override
    protected void paintComponent(Graphics graphics) {
        Graphics2D graphics2D = (Graphics2D) graphics;
        int width = getWidth();
        int height = getHeight();
        Color color1 = new Color(71, 120, 197);
        Color color2 = new Color(23, 35, 51);
        GradientPaint gradientPaint = new GradientPaint(0, 0, color1, 180, height, color2);
        graphics2D.setPaint(gradientPaint);
        graphics2D.fillRect(0, 0, width, height);
    }

    private void fillCategoriesTable() {
        List<Category> categories = categoryDAO.getAll();
        int rows = categories.size();
        Object[][] table = new Object[rows][4];
        for (int i = 0; i < rows; i++) {
           table[i][0] = categories.get(i).getId();
           table[i][1] = categories.get(i).getType();
           table[i][2] = categories.get(i).getDescription();
            DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            table[i][3] = format.format(categories.get(i).getUpdatedAt());
        }
        DefaultTableModel model = new DefaultTableModel(table, new String[]{
           "Id", "Type", "Description", "Date Ajout"
        });
        categoriesTable.setModel(model);
    }
    
    private void fillCategoriesComboBox() {
        List<Category> categories = categoryDAO.getAll();
        subCatCb.removeAllItems();
        subCatCb.addItem("");
        categories.forEach((category) -> {
            subCatCb.addItem(category.getType());
        });
    }
    
    private void fillSubCategoriesTable() {
        List<SubCategory> subCategories = subCategoryDAO.getAll();
        int rows = subCategories.size();
        Object[][] table = new Object[rows][4];
        for (int i = 0; i < rows; i++) {
            table[i][0] = subCategories.get(i).getId();
            table[i][1] = subCategories.get(i).getLabel();
            table[i][2] = subCategories.get(i).getDescription();
            DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            System.out.println(subCategories.get(i).getUpdatedAt());
            table[i][3] = format.format(subCategories.get(i).getUpdatedAt());
        }
        DefaultTableModel model = new DefaultTableModel(table, new String[]{
           "Id", "Nom", "Description", "Date Ajout"
        });
        subCategoriesTable.setModel(model);
    }
    
    private void fillSubCategoriesTable(List<SubCategory> subCategories) {
        int rows = subCategories.size();
        Object[][] table = new Object[rows][4];
        for (int i = 0; i < rows; i++) {
            table[i][0] = subCategories.get(i).getId();
            table[i][1] = subCategories.get(i).getLabel();
            table[i][2] = subCategories.get(i).getDescription();
            DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            System.out.println(subCategories.get(i).getUpdatedAt());
            table[i][3] = format.format(subCategories.get(i).getUpdatedAt());
        }
        DefaultTableModel model = new DefaultTableModel(table, new String[]{
           "Id", "Nom", "Description", "Date Ajout"
        });
        subCategoriesTable.setModel(model);
    }
    
    private void enableAddButton() {
        if("".equals(idTxt.getText())) {
            addBtn.setEnabled(true);
        }
    }
    
    private void subCatEnableAddButton() {
        if("".equals(subCatIdTxt.getText())) {
            subCatAddBtn.setEnabled(true);
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        addNewCatPane = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        idTxt = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        typeTxt = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        descTxt = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        addDc = new com.toedter.calendar.JDateChooser();
        actionsPane = new javax.swing.JPanel();
        addBtn = new javax.swing.JButton();
        editBtn = new javax.swing.JButton();
        cancelBtn = new javax.swing.JButton();
        deleteBtn = new javax.swing.JButton();
        catListPane = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        categoriesTable = new javax.swing.JTable();
        searchTxt = new javax.swing.JTextField();
        searchBtn = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        subCatIdTxt = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        subCatCb = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        subCatLabelTxt = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        subCatDescTxt = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        subCatAddDc = new com.toedter.calendar.JDateChooser();
        jPanel2 = new javax.swing.JPanel();
        subCatAddBtn = new javax.swing.JButton();
        subCatCancelBtn = new javax.swing.JButton();
        subCatDelBtn = new javax.swing.JButton();
        subCatUpdateBtn = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        subCategoriesTable = new javax.swing.JTable();
        listSubcatBtn = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        subCatSearchTxt = new javax.swing.JTextField();
        subCatSearchBtn = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(1130, 562));

        addNewCatPane.setBackground(new java.awt.Color(204, 204, 204, 80));
        addNewCatPane.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Ajouter une nouvelle catégorie", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Semibold", 0, 14))); // NOI18N

        jLabel1.setText("Id");

        idTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                idTxtActionPerformed(evt);
            }
        });

        jLabel2.setText("Type");

        typeTxt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                typeTxtFocusGained(evt);
            }
        });

        jLabel3.setText("Description");

        descTxt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                descTxtFocusGained(evt);
            }
        });

        jLabel5.setText("Date Ajout");

        javax.swing.GroupLayout addNewCatPaneLayout = new javax.swing.GroupLayout(addNewCatPane);
        addNewCatPane.setLayout(addNewCatPaneLayout);
        addNewCatPaneLayout.setHorizontalGroup(
            addNewCatPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addNewCatPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(addNewCatPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, addNewCatPaneLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 1, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(addNewCatPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(addNewCatPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(typeTxt)
                    .addComponent(addDc, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(descTxt, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(idTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        addNewCatPaneLayout.setVerticalGroup(
            addNewCatPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addNewCatPaneLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(addNewCatPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(idTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(addNewCatPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(typeTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(addNewCatPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(descTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(addNewCatPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(addDc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        actionsPane.setBackground(new java.awt.Color(204, 204, 204, 80));
        actionsPane.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Actions", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Semibold", 0, 14))); // NOI18N

        addBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dev/stekos/cashregister/icons/add.png"))); // NOI18N
        addBtn.setText("Ajouter");
        addBtn.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                addBtnFocusLost(evt);
            }
        });
        addBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addBtnActionPerformed(evt);
            }
        });

        editBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dev/stekos/cashregister/icons/update.png"))); // NOI18N
        editBtn.setText("Modifier");
        editBtn.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                editBtnFocusLost(evt);
            }
        });
        editBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editBtnActionPerformed(evt);
            }
        });

        cancelBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dev/stekos/cashregister/icons/cancel.png"))); // NOI18N
        cancelBtn.setText("Annuler");
        cancelBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelBtnActionPerformed(evt);
            }
        });

        deleteBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dev/stekos/cashregister/icons/delete.png"))); // NOI18N
        deleteBtn.setText("Supprimer");
        deleteBtn.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                deleteBtnFocusLost(evt);
            }
        });
        deleteBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout actionsPaneLayout = new javax.swing.GroupLayout(actionsPane);
        actionsPane.setLayout(actionsPaneLayout);
        actionsPaneLayout.setHorizontalGroup(
            actionsPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, actionsPaneLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(actionsPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(addBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cancelBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(actionsPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(editBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(deleteBtn))
                .addGap(29, 29, 29))
        );
        actionsPaneLayout.setVerticalGroup(
            actionsPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, actionsPaneLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(actionsPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(actionsPaneLayout.createSequentialGroup()
                        .addComponent(editBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(deleteBtn))
                    .addGroup(actionsPaneLayout.createSequentialGroup()
                        .addComponent(addBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cancelBtn)))
                .addGap(42, 42, 42))
        );

        catListPane.setBackground(new java.awt.Color(204, 204, 204, 80));
        catListPane.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "List des catégories", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Semibold", 0, 14))); // NOI18N

        categoriesTable.setAutoCreateRowSorter(true);
        categoriesTable.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        categoriesTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        categoriesTable.setFocusable(false);
        categoriesTable.setSelectionBackground(new java.awt.Color(232, 57, 95));
        jScrollPane1.setViewportView(categoriesTable);

        searchTxt.setToolTipText("Chercher une catégorie");
        searchTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchTxtActionPerformed(evt);
            }
        });

        searchBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dev/stekos/cashregister/icons/iconfinder_Find_132785.png"))); // NOI18N
        searchBtn.setText("Rechercher");
        searchBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout catListPaneLayout = new javax.swing.GroupLayout(catListPane);
        catListPane.setLayout(catListPaneLayout);
        catListPaneLayout.setHorizontalGroup(
            catListPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(catListPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(catListPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 519, Short.MAX_VALUE)
                    .addGroup(catListPaneLayout.createSequentialGroup()
                        .addComponent(searchTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(searchBtn)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        catListPaneLayout.setVerticalGroup(
            catListPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, catListPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(catListPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(searchBtn)
                    .addGroup(catListPaneLayout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(searchTxt)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 281, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel3.setBackground(new java.awt.Color(204, 204, 204, 80));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Sous Catégories", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Semibold", 0, 14))); // NOI18N

        jPanel1.setBackground(new java.awt.Color(204, 204, 204, 80));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Ajouter une sous cat.", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Semibold", 0, 14))); // NOI18N

        jLabel4.setText("Id");

        jLabel6.setText("Catégorie");

        subCatCb.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        subCatCb.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                subCatCbFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                subCatCbFocusLost(evt);
            }
        });

        jLabel7.setText("Nom");

        subCatLabelTxt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                subCatLabelTxtFocusGained(evt);
            }
        });

        jLabel8.setText("Description");

        subCatDescTxt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                subCatDescTxtFocusGained(evt);
            }
        });

        jLabel9.setText("Date Ajout");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(subCatIdTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(16, 16, 16)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel4)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel8))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel9)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(subCatAddDc, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
                            .addComponent(subCatDescTxt)
                            .addComponent(subCatCb, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(subCatLabelTxt))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(subCatIdTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(subCatCb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(subCatLabelTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(subCatDescTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(subCatAddDc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(204, 204, 204, 80));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Ajouter une sous cat.", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Semibold", 0, 14))); // NOI18N

        subCatAddBtn.setFont(new java.awt.Font("Segoe UI Semibold", 0, 11)); // NOI18N
        subCatAddBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dev/stekos/cashregister/icons/add.png"))); // NOI18N
        subCatAddBtn.setText("Ajouter");
        subCatAddBtn.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                subCatAddBtnFocusLost(evt);
            }
        });
        subCatAddBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subCatAddBtnActionPerformed(evt);
            }
        });

        subCatCancelBtn.setFont(new java.awt.Font("Segoe UI Semibold", 0, 11)); // NOI18N
        subCatCancelBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dev/stekos/cashregister/icons/cancel.png"))); // NOI18N
        subCatCancelBtn.setText("Annuler");
        subCatCancelBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subCatCancelBtnActionPerformed(evt);
            }
        });

        subCatDelBtn.setFont(new java.awt.Font("Segoe UI Semibold", 0, 11)); // NOI18N
        subCatDelBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dev/stekos/cashregister/icons/delete.png"))); // NOI18N
        subCatDelBtn.setText("Supprimer");
        subCatDelBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subCatDelBtnActionPerformed(evt);
            }
        });

        subCatUpdateBtn.setFont(new java.awt.Font("Segoe UI Semibold", 0, 11)); // NOI18N
        subCatUpdateBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dev/stekos/cashregister/icons/update.png"))); // NOI18N
        subCatUpdateBtn.setText("Modifier");
        subCatUpdateBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subCatUpdateBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(subCatCancelBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(subCatDelBtn))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(subCatAddBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(subCatUpdateBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(subCatAddBtn)
                    .addComponent(subCatUpdateBtn))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(subCatCancelBtn)
                    .addComponent(subCatDelBtn))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(204, 204, 204, 80));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Ajouter une sous cat.", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Semibold", 0, 14))); // NOI18N

        subCategoriesTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        subCategoriesTable.setFocusable(false);
        subCategoriesTable.setSelectionBackground(new java.awt.Color(232, 57, 95));
        jScrollPane2.setViewportView(subCategoriesTable);

        listSubcatBtn.setText("Afficher");
        listSubcatBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                listSubcatBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 483, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(listSubcatBtn)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addComponent(listSubcatBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel5.setBackground(new java.awt.Color(204, 204, 204, 80));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Ajouter une sous cat.", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Semibold", 0, 14))); // NOI18N

        subCatSearchTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subCatSearchTxtActionPerformed(evt);
            }
        });

        subCatSearchBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dev/stekos/cashregister/icons/iconfinder_Find_132785.png"))); // NOI18N
        subCatSearchBtn.setText("Rechercher");
        subCatSearchBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subCatSearchBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(subCatSearchTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(subCatSearchBtn)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(subCatSearchBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(subCatSearchTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(addNewCatPane, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(actionsPane, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(catListPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 541, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(addNewCatPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(actionsPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(catListPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void addBtnFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_addBtnFocusLost
        //this.fillCategoriesTable();
    }//GEN-LAST:event_addBtnFocusLost

    private void addBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addBtnActionPerformed
        try {
            if ("".equals(typeTxt.getText()) || "".equals(descTxt.getText())) {
                JOptionPane.showMessageDialog(this, "Oups ! Un champs est toujours vide, Veuillez la remplir.");
            } else {
                Category category = new Category();
                category.setType(typeTxt.getText());
                category.setDescription(descTxt.getText());
                if(addDc.getDate() == null) {
                    Date date = new Date();
                    category.setCreatedAt(date);
                    category.setUpdatedAt(date);
                } else {
                    category.setCreatedAt(addDc.getDate());
                    category.setUpdatedAt(addDc.getDate());
                }
                categoryDAO.add(category);
                JOptionPane.showMessageDialog(this, "Ajout réussi", "Succès", JOptionPane.INFORMATION_MESSAGE);
                fillCategoriesTable();
                cancelBtnActionPerformed(evt);
            }
        } catch (Exception ex) {
            Logger.getLogger(CategoryForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_addBtnActionPerformed

    private void editBtnFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_editBtnFocusLost
        this.fillCategoriesTable();
    }//GEN-LAST:event_editBtnFocusLost

    private void editBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editBtnActionPerformed
        try {
            if ("".equals(typeTxt.getText()) || "".equals(descTxt.getText())) {
                JOptionPane.showMessageDialog(this, "Oups ! Un champs est toujours vide, Veuillez la remplir.");
            } else {
                Category category = categoryDAO.getById(Integer.parseInt(idTxt.getText()));
                category.setType(typeTxt.getText());
                category.setDescription(descTxt.getText());
                if(addDc.getDate() == null) {
                    JOptionPane.showMessageDialog(this, "Veuillez entrer une date valide", "Alerte", JOptionPane.WARNING_MESSAGE);
                } else {
                    category.setUpdatedAt(addDc.getDate());
                    categoryDAO.edit(category);
                    JOptionPane.showMessageDialog(this, "Mise à jour réussie", "Succès", JOptionPane.INFORMATION_MESSAGE);
                    fillCategoriesTable();
                    cancelBtnActionPerformed(evt);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(CategoryForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_editBtnActionPerformed

    private void cancelBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelBtnActionPerformed
        idTxt.setText("");
        typeTxt.setText("");
        descTxt.setText("");
        addDc.setCalendar(null);
        addBtn.setEnabled(true);
        editBtn.setEnabled(false);
        cancelBtn.setEnabled(false);
        deleteBtn.setEnabled(false);
    }//GEN-LAST:event_cancelBtnActionPerformed

    private void deleteBtnFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_deleteBtnFocusLost
        //this.fillCategoriesTable();
    }//GEN-LAST:event_deleteBtnFocusLost

    private void deleteBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteBtnActionPerformed
        int i = JOptionPane.showConfirmDialog(this, "Voulez-vous supprimer cette catégorie de produit ?");
        if(i == 0) {
            try {
                categoryDAO.remove(Integer.parseInt(idTxt.getText()));
                JOptionPane.showMessageDialog(this, "Suppression réussie", "Succès", JOptionPane.INFORMATION_MESSAGE);
                fillCategoriesTable();
                cancelBtnActionPerformed(evt);
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(CategoryForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_deleteBtnActionPerformed

    private void idTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_idTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_idTxtActionPerformed

    private void searchTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchTxtActionPerformed
        Category category = categoryDAO.getByType(searchTxt.getText());
        idTxt.setText(Integer.toString(category.getId()));
        typeTxt.setText(category.getType());
        descTxt.setText(category.getDescription());
        addDc.setDate(category.getUpdatedAt());
        addBtn.setEnabled(false);
        editBtn.setEnabled(true);
        cancelBtn.setEnabled(true);
        deleteBtn.setEnabled(true);

    }//GEN-LAST:event_searchTxtActionPerformed

    private void searchBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchBtnActionPerformed
        searchTxtActionPerformed(evt);
    }//GEN-LAST:event_searchBtnActionPerformed

    private void typeTxtFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_typeTxtFocusGained
        enableAddButton();
    }//GEN-LAST:event_typeTxtFocusGained

    private void descTxtFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_descTxtFocusGained
        enableAddButton();
    }//GEN-LAST:event_descTxtFocusGained

    private void subCatSearchTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subCatSearchTxtActionPerformed
        subCatSearchBtnActionPerformed(evt);
    }//GEN-LAST:event_subCatSearchTxtActionPerformed

    private void subCatCbFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_subCatCbFocusGained
        fillCategoriesComboBox();
        subCatEnableAddButton();
    }//GEN-LAST:event_subCatCbFocusGained

    private void subCatCbFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_subCatCbFocusLost
        //
    }//GEN-LAST:event_subCatCbFocusLost

    private void subCatAddBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subCatAddBtnActionPerformed
        try {
            if ("".equals(subCatCb.getSelectedItem().toString()) || "".equals(subCatLabelTxt.getText()) || "".equals(subCatDescTxt.getText())) {
                JOptionPane.showMessageDialog(this, "Oups ! Un champs est toujours vide, Veuillez la remplir.");
            } else {
                Category category = categoryDAO.getByType(subCatCb.getSelectedItem().toString());
                SubCategory subCategory = new SubCategory();
                subCategory.setCategoryId(category.getId());
                subCategory.setLabel(subCatLabelTxt.getText());
                subCategory.setDescription(subCatDescTxt.getText());
                if(subCatAddDc.getDate()  != null) {
                    subCategory.setCreatedAt(subCatAddDc.getDate());
                    subCategory.setUpdatedAt(subCatAddDc.getDate());
                } else {
                    Date date = new Date();
                    subCategory.setCreatedAt(date);
                    subCategory.setUpdatedAt(date);
                }
                subCategoryDAO.add(subCategory);
                JOptionPane.showMessageDialog(this, "Ajout réussi", "Succès", JOptionPane.INFORMATION_MESSAGE);
                subCatCancelBtnActionPerformed(evt);
                fillSubCategoriesTable();
            }
        } catch (Exception ex) {
            Logger.getLogger(CategoryForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_subCatAddBtnActionPerformed

    private void subCatLabelTxtFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_subCatLabelTxtFocusGained
        subCatEnableAddButton();
    }//GEN-LAST:event_subCatLabelTxtFocusGained

    private void subCatDescTxtFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_subCatDescTxtFocusGained
        subCatEnableAddButton();
    }//GEN-LAST:event_subCatDescTxtFocusGained

    private void subCatAddBtnFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_subCatAddBtnFocusLost
//        fillSubCategoriesTable();
    }//GEN-LAST:event_subCatAddBtnFocusLost

    private void subCatCancelBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subCatCancelBtnActionPerformed
        subCatIdTxt.setText("");
        subCatCb.setSelectedItem("");
        subCatLabelTxt.setText("");
        subCatDescTxt.setText("");
        subCatAddDc.setCalendar(null);
        subCatAddBtn.setEnabled(false);
        subCatUpdateBtn.setEnabled(false);
        subCatDelBtn.setEnabled(false);
        subCatCancelBtn.setEnabled(false);
    }//GEN-LAST:event_subCatCancelBtnActionPerformed

    private void subCatUpdateBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subCatUpdateBtnActionPerformed
        try {
            if ("".equals(subCatCb.getSelectedItem().toString()) || "".equals(subCatLabelTxt.getText()) || "".equals(subCatDescTxt.getText())) {
                JOptionPane.showMessageDialog(this, "Oups ! Un champs est toujours vide, Veuillez la remplir.");
            } else {
                SubCategory subCategory = subCategoryDAO.getById(Integer.parseInt(subCatIdTxt.getText()));
                Category category = categoryDAO.getByType(subCatCb.getSelectedItem().toString());
                subCategory.setCategoryId(category.getId());
                subCategory.setLabel(subCatLabelTxt.getText());
                subCategory.setDescription(subCatDescTxt.getText());
                if(subCatAddDc.getDate() != null) {
                    subCategory.setUpdatedAt(subCatAddDc.getDate());
                    subCategoryDAO.edit(subCategory);
                    JOptionPane.showMessageDialog(this, "Mise à jour réussie", "Succès", JOptionPane.INFORMATION_MESSAGE);
                    fillSubCategoriesTable();
                    subCatCancelBtnActionPerformed(evt);
                } else {
                    JOptionPane.showMessageDialog(this, "Veuillez entrer une date valide", "Alerte", JOptionPane.WARNING_MESSAGE);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(CategoryForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_subCatUpdateBtnActionPerformed

    private void subCatDelBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subCatDelBtnActionPerformed
        int i = JOptionPane.showConfirmDialog(this, "Voulez-vous supprimer cette sous catégorie de produit ?");
        if(i == 0) {
            try {
                categoryDAO.remove(Integer.parseInt(idTxt.getText()));
                JOptionPane.showMessageDialog(this, "Suppression réussie", "Succès", JOptionPane.INFORMATION_MESSAGE);
                fillCategoriesTable();
                cancelBtnActionPerformed(evt);
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(CategoryForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_subCatDelBtnActionPerformed

    private void subCatSearchBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subCatSearchBtnActionPerformed
        if("".equals(subCatSearchTxt.getText())) {
            JOptionPane.showMessageDialog(this, "Veuillez insérer un nom de sous catégorie");
        } else {
            SubCategory subCategory = subCategoryDAO.getByName(subCatSearchTxt.getText());
            subCatIdTxt.setText(subCategory.getId().toString());
            Category category = categoryDAO.getById(subCategory.getCategoryId());
            subCatCb.setSelectedItem(category.getType());
            subCatLabelTxt.setText(subCategory.getLabel());
            subCatDescTxt.setText(subCategory.getDescription());
            subCatAddDc.setDate(subCategory.getUpdatedAt());
            subCatAddBtn.setEnabled(false);
            subCatUpdateBtn.setEnabled(true);
            subCatDelBtn.setEnabled(true);
            subCatCancelBtn.setEnabled(true);
        }
    }//GEN-LAST:event_subCatSearchBtnActionPerformed

    private void listSubcatBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_listSubcatBtnActionPerformed
        fillSubCategoriesTable();
    }//GEN-LAST:event_listSubcatBtnActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel actionsPane;
    private javax.swing.JButton addBtn;
    private com.toedter.calendar.JDateChooser addDc;
    private javax.swing.JPanel addNewCatPane;
    private javax.swing.JButton cancelBtn;
    private javax.swing.JPanel catListPane;
    private javax.swing.JTable categoriesTable;
    private javax.swing.JButton deleteBtn;
    private javax.swing.JTextField descTxt;
    private javax.swing.JButton editBtn;
    private javax.swing.JTextField idTxt;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton listSubcatBtn;
    private javax.swing.JButton searchBtn;
    private javax.swing.JTextField searchTxt;
    private javax.swing.JButton subCatAddBtn;
    private com.toedter.calendar.JDateChooser subCatAddDc;
    private javax.swing.JButton subCatCancelBtn;
    private javax.swing.JComboBox<String> subCatCb;
    private javax.swing.JButton subCatDelBtn;
    private javax.swing.JTextField subCatDescTxt;
    private javax.swing.JTextField subCatIdTxt;
    private javax.swing.JTextField subCatLabelTxt;
    private javax.swing.JButton subCatSearchBtn;
    private javax.swing.JTextField subCatSearchTxt;
    private javax.swing.JButton subCatUpdateBtn;
    private javax.swing.JTable subCategoriesTable;
    private javax.swing.JTextField typeTxt;
    // End of variables declaration//GEN-END:variables
}
