/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dev.stekos.cashregister.views;

import dev.stekos.cashregister.controllers.exceptions.NonexistentEntityException;
import dev.stekos.cashregister.dao.CategoryDAO;
import dev.stekos.cashregister.dao.ProductDAO;
import dev.stekos.cashregister.models.Category;
import dev.stekos.cashregister.models.Product;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Stekos
 */
public class ProductForm extends javax.swing.JPanel {
    private final ProductDAO productDAO = new ProductDAO();

    /**
     * Creates new form ProductPane
     */
    public ProductForm() {
        initComponents();
        idTxt.setEditable(false);
        fillCategoriesComboBox(categoriesCb);
        fillCategoriesComboBox(catSearchCb);
        addBtn.setEnabled(false);
        editBtn.setEnabled(false);
        cancelBtn.setEnabled(false);
        deleteBtn.setEnabled(false);
        productsTable.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
            if (productsTable.getSelectedRow() != -1) {
                try {
                    addBtn.setEnabled(false);
                    editBtn.setEnabled(true);
                    cancelBtn.setEnabled(true);
                    deleteBtn.setEnabled(true);
                    int selectedRow = productsTable.getSelectedRow();
                    idTxt.setText((productsTable.getValueAt(selectedRow, 0).toString()));
                    categoriesCb.setSelectedItem((productsTable.getValueAt(selectedRow, 1).toString()));
                    nameTxt.setText((productsTable.getValueAt(selectedRow, 2).toString()));
                    priceTxt.setText((productsTable.getValueAt(selectedRow, 3).toString()));
                    quantityTxt.setText((productsTable.getValueAt(selectedRow, 4).toString()));
                    descTxt.setText((productsTable.getValueAt(selectedRow, 5).toString()));
                    Date date = new SimpleDateFormat("dd-MM-yyyy").parse((productsTable.getValueAt(selectedRow, 6)).toString());
                    addDc.setDate(date);
                } catch (ParseException ex) {
                    Logger.getLogger(ProductForm.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        fillProductsTable();
    }
    
    private void fillProductsTable() {
        List<Product> products = productDAO.getAll();
        int rows = products.size();
        Object[][] table = new Object[rows][7];
        for (int i = 0; i < rows; i++) {
            table[i][0] = products.get(i).getId();
            Category category = (new CategoryDAO()).getById(products.get(i).getCategoryId());
            table[i][1] = category.getType();
            table[i][2] = products.get(i).getName();
            table[i][3] = products.get(i).getPrice();
            table[i][4] = products.get(i).getQuantity();
            table[i][5] = products.get(i).getDescription();
            DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            table[i][6] = format.format(products.get(i).getUpdatedAt());
        }
        DefaultTableModel model = new DefaultTableModel(table, new String[]{
            "Id", "Catégories", "Nom Prod.", "Prix", "Quantité", "Description", "Date Ajout"
        });
        productsTable.setModel(model);
    }
    
    private void fillProductsTable(List<Product> products) {
        int rows = products.size();
        Object[][] table = new Object[rows][7];
        for (int i = 0; i < rows; i++) {
            table[i][0] = products.get(i).getId();
            Category category = (new CategoryDAO()).getById(products.get(i).getCategoryId());
            table[i][1] = category.getType();
            table[i][2] = products.get(i).getName();
            table[i][3] = products.get(i).getPrice();
            table[i][4] = products.get(i).getQuantity();
            table[i][5] = products.get(i).getDescription();
            DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            table[i][6] = format.format(products.get(i).getUpdatedAt());
        }
        DefaultTableModel model = new DefaultTableModel(table, new String[]{
            "Id", "Catégories", "Nom Prod.", "Prix", "Quantité", "Description", "Date Ajout"
        });
        productsTable.setModel(model);
    }

    private void fillCategoriesComboBox(JComboBox comboBox) {
        List<Category> categories = (new CategoryDAO()).getAll();
        comboBox.removeAllItems();
        comboBox.addItem("");
        categories.forEach((category) -> {
            comboBox.addItem(category.getType());
        });
    }

    private void enableAddButton() {
        if ("".equals(idTxt.getText())) {
            addBtn.setEnabled(true);
        }
    }
    
    private boolean isDouble(String value) {
        try {
            Double.parseDouble(value);
        } catch(NumberFormatException | NullPointerException e) {
            return false;
        }
        return true;
    }
    
    private void switchPanels(JPanel panel) {
        searchLyP.removeAll();
        searchLyP.add(panel);
        searchLyP.repaint();
        searchLyP.revalidate();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        addProdPane = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        descTxt = new javax.swing.JTextField();
        quantityTxt = new javax.swing.JTextField();
        priceTxt = new javax.swing.JTextField();
        nameTxt = new javax.swing.JTextField();
        categoriesCb = new javax.swing.JComboBox<>();
        idTxt = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        addDc = new com.toedter.calendar.JDateChooser();
        actionsPane = new javax.swing.JPanel();
        addBtn = new javax.swing.JButton();
        editBtn = new javax.swing.JButton();
        cancelBtn = new javax.swing.JButton();
        deleteBtn = new javax.swing.JButton();
        searchPane = new javax.swing.JPanel();
        categorieBtn = new javax.swing.JButton();
        nameBtn = new javax.swing.JButton();
        searchLyP = new javax.swing.JLayeredPane();
        catSearchPane = new javax.swing.JPanel();
        catSearchBtn = new javax.swing.JButton();
        catSearchCb = new javax.swing.JComboBox<>();
        nameSearchPane = new javax.swing.JPanel();
        nameSearchTxt = new javax.swing.JTextField();
        nameSearchBtn = new javax.swing.JButton();
        productsPane2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        productsTable = new javax.swing.JTable();

        setBackground(new java.awt.Color(204, 204, 255));

        addProdPane.setBackground(new java.awt.Color(204, 204, 255));
        addProdPane.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Ajouter un produit", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Semibold", 0, 14))); // NOI18N
        addProdPane.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N

        jLabel2.setText("Id");

        jLabel3.setText("Catégorie");

        jLabel5.setText("Nom");

        jLabel6.setText("Prix");

        jLabel7.setText("Quantité");

        jLabel8.setText("Description");

        descTxt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                descTxtFocusGained(evt);
            }
        });

        quantityTxt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                quantityTxtFocusGained(evt);
            }
        });

        priceTxt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                priceTxtFocusGained(evt);
            }
        });

        nameTxt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                nameTxtFocusGained(evt);
            }
        });

        categoriesCb.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        categoriesCb.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                categoriesCbFocusGained(evt);
            }
        });

        idTxt.setToolTipText("Ce champ n'est pas éditable");

        jLabel1.setText("Date Ajout");

        javax.swing.GroupLayout addProdPaneLayout = new javax.swing.GroupLayout(addProdPane);
        addProdPane.setLayout(addProdPaneLayout);
        addProdPaneLayout.setHorizontalGroup(
            addProdPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addProdPaneLayout.createSequentialGroup()
                .addGroup(addProdPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(addProdPaneLayout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(addProdPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2))
                        .addGap(77, 77, 77)
                        .addGroup(addProdPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(categoriesCb, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(idTxt)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, addProdPaneLayout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(addProdPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8)
                            .addComponent(jLabel1))
                        .addGap(77, 77, 77)
                        .addGroup(addProdPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(priceTxt, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(quantityTxt)
                            .addComponent(descTxt, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(addDc, javax.swing.GroupLayout.DEFAULT_SIZE, 221, Short.MAX_VALUE)
                            .addComponent(nameTxt, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addContainerGap())
        );
        addProdPaneLayout.setVerticalGroup(
            addProdPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addProdPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(addProdPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(idTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(9, 9, 9)
                .addGroup(addProdPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(categoriesCb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(addProdPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(addProdPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(priceTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(addProdPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(quantityTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(addProdPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(descTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(addProdPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(addDc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        actionsPane.setBackground(new java.awt.Color(204, 204, 255));
        actionsPane.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Actions", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Semibold", 0, 14))); // NOI18N
        actionsPane.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N

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
            .addGroup(actionsPaneLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(actionsPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(addBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cancelBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(actionsPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(editBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(deleteBtn))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        actionsPaneLayout.setVerticalGroup(
            actionsPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(actionsPaneLayout.createSequentialGroup()
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
                .addGap(18, 18, 18))
        );

        searchPane.setBackground(new java.awt.Color(204, 204, 255));
        searchPane.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Rechercher", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Semibold", 0, 14))); // NOI18N
        searchPane.setFont(new java.awt.Font("Segoe UI Historic", 0, 12)); // NOI18N

        categorieBtn.setText("Catégorie");
        categorieBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                categorieBtnActionPerformed(evt);
            }
        });

        nameBtn.setText("Nom");
        nameBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nameBtnActionPerformed(evt);
            }
        });

        searchLyP.setLayout(new java.awt.CardLayout());

        catSearchBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dev/stekos/cashregister/icons/iconfinder_Find_132785.png"))); // NOI18N
        catSearchBtn.setText("Rechercher");
        catSearchBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                catSearchBtnActionPerformed(evt);
            }
        });

        catSearchCb.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        catSearchCb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                catSearchCbActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout catSearchPaneLayout = new javax.swing.GroupLayout(catSearchPane);
        catSearchPane.setLayout(catSearchPaneLayout);
        catSearchPaneLayout.setHorizontalGroup(
            catSearchPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(catSearchPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(catSearchCb, 0, 116, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(catSearchBtn)
                .addContainerGap())
        );
        catSearchPaneLayout.setVerticalGroup(
            catSearchPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, catSearchPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(catSearchPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(catSearchBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                    .addComponent(catSearchCb))
                .addContainerGap())
        );

        searchLyP.add(catSearchPane, "card2");

        nameSearchBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dev/stekos/cashregister/icons/iconfinder_Find_132785.png"))); // NOI18N
        nameSearchBtn.setText("Rechercher");
        nameSearchBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nameSearchBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout nameSearchPaneLayout = new javax.swing.GroupLayout(nameSearchPane);
        nameSearchPane.setLayout(nameSearchPaneLayout);
        nameSearchPaneLayout.setHorizontalGroup(
            nameSearchPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(nameSearchPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(nameSearchTxt, javax.swing.GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nameSearchBtn)
                .addContainerGap())
        );
        nameSearchPaneLayout.setVerticalGroup(
            nameSearchPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, nameSearchPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(nameSearchPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nameSearchTxt)
                    .addComponent(nameSearchBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE))
                .addContainerGap())
        );

        searchLyP.add(nameSearchPane, "card2");

        javax.swing.GroupLayout searchPaneLayout = new javax.swing.GroupLayout(searchPane);
        searchPane.setLayout(searchPaneLayout);
        searchPaneLayout.setHorizontalGroup(
            searchPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(searchPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(searchPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(searchLyP)
                    .addGroup(searchPaneLayout.createSequentialGroup()
                        .addComponent(categorieBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(nameBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        searchPaneLayout.setVerticalGroup(
            searchPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(searchPaneLayout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addGroup(searchPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(categorieBtn)
                    .addComponent(nameBtn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(searchLyP)
                .addContainerGap())
        );

        productsPane2.setBackground(new java.awt.Color(204, 204, 255));
        productsPane2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Liste des produits", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Semibold", 1, 14))); // NOI18N

        productsTable.setAutoCreateRowSorter(true);
        productsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane3.setViewportView(productsTable);

        javax.swing.GroupLayout productsPane2Layout = new javax.swing.GroupLayout(productsPane2);
        productsPane2.setLayout(productsPane2Layout);
        productsPane2Layout.setHorizontalGroup(
            productsPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(productsPane2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3)
                .addContainerGap())
        );
        productsPane2Layout.setVerticalGroup(
            productsPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, productsPane2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(addProdPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(actionsPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(searchPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(productsPane2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(actionsPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(searchPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(addProdPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(productsPane2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void descTxtFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_descTxtFocusGained
        enableAddButton();
    }//GEN-LAST:event_descTxtFocusGained

    private void quantityTxtFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_quantityTxtFocusGained
        enableAddButton();
    }//GEN-LAST:event_quantityTxtFocusGained

    private void priceTxtFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_priceTxtFocusGained
        enableAddButton();
    }//GEN-LAST:event_priceTxtFocusGained

    private void nameTxtFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_nameTxtFocusGained
        enableAddButton();
    }//GEN-LAST:event_nameTxtFocusGained

    private void addBtnFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_addBtnFocusLost
        this.fillProductsTable();
    }//GEN-LAST:event_addBtnFocusLost

    private void addBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addBtnActionPerformed
        try {
            if ("".equals(nameTxt.getText()) || "".equals(descTxt.getText())
                || "".equals(priceTxt.getText()) || "".equals(quantityTxt.getText()) 
                    || "".equals(categoriesCb.getSelectedItem())) {
                JOptionPane.showMessageDialog(this, "Oups ! Un champs est toujours vide, Veuillez la remplir.");
            } else {
                Product product = new Product();
                product.setName(nameTxt.getText());
                if(isDouble(priceTxt.getText())) {
                    product.setPrice(Double.parseDouble(priceTxt.getText()));
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Le champs Prix doit contenir un nombre réel",
                            "Alerte", JOptionPane.WARNING_MESSAGE
                    );
                }
                
                if(isDouble(quantityTxt.getText())) {
                    product.setQuantity(Double.parseDouble(quantityTxt.getText()));
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Le champs Quantité doit contenir un nombre réel",
                            "Alerte", JOptionPane.WARNING_MESSAGE
                    );
                }
                
                Category category = (new CategoryDAO()).getByType(categoriesCb.getSelectedItem().toString());
                product.setCategoryId(category.getId());
                product.setDescription(descTxt.getText());
                Date date = addDc.getDate();
                product.setCreatedAt(date);
                product.setUpdatedAt(date);
                productDAO.add(product);
                nameTxt.setText("");
                priceTxt.setText("");
                quantityTxt.setText("");
                descTxt.setText("");
                addBtn.setEnabled(false);
            }
        } catch (Exception ex) {
            Logger.getLogger(ProductForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_addBtnActionPerformed

    private void editBtnFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_editBtnFocusLost
        this.fillProductsTable();
    }//GEN-LAST:event_editBtnFocusLost

    private void editBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editBtnActionPerformed
        try {
            Product product = productDAO.getById(Integer.parseInt(idTxt.getText()));
            product.setName(nameTxt.getText());
            product.setPrice(Double.parseDouble(priceTxt.getText()));
            product.setQuantity(Double.parseDouble(quantityTxt.getText()));
            product.setDescription(descTxt.getText());
            product.setUpdatedAt(addDc.getDate());
            Category category = (new CategoryDAO()).getByType(categoriesCb.getSelectedItem().toString());
            product.setCategoryId(category.getId());
            productDAO.edit(product);
        } catch (Exception ex) {
            Logger.getLogger(ProductForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.cancelBtnActionPerformed(evt);
    }//GEN-LAST:event_editBtnActionPerformed

    private void cancelBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelBtnActionPerformed
        idTxt.setText("");
        categoriesCb.setSelectedItem("");
        nameTxt.setText("");
        priceTxt.setText("");
        quantityTxt.setText("");
        descTxt.setText("");
        addDc.setCalendar(null);
        addBtn.setEnabled(true);
        editBtn.setEnabled(false);
        cancelBtn.setEnabled(false);
        deleteBtn.setEnabled(false);
    }//GEN-LAST:event_cancelBtnActionPerformed

    private void deleteBtnFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_deleteBtnFocusLost
        this.fillProductsTable();
    }//GEN-LAST:event_deleteBtnFocusLost

    private void deleteBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteBtnActionPerformed
        int i = JOptionPane.showConfirmDialog(this, "Voulez-vous supprimer ce prdouit ?");
        if (i == 0) {
            try {
                productDAO.remove(Integer.parseInt(idTxt.getText()));
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(ProductForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_deleteBtnActionPerformed

    private void categoriesCbFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_categoriesCbFocusGained
        enableAddButton();
    }//GEN-LAST:event_categoriesCbFocusGained

    private void catSearchBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_catSearchBtnActionPerformed
        if(!("".equals(catSearchCb.getSelectedItem().toString()))){
            Category category = (new CategoryDAO()).getByType(catSearchCb.getSelectedItem().toString());
            List<Product> products = productDAO.getByCategoryId(category.getId());
            fillProductsTable(products);
            addBtn.setEnabled(false);
            editBtn.setEnabled(true);
            cancelBtn.setEnabled(true);
            deleteBtn.setEnabled(true);
        } else {
            JOptionPane.showMessageDialog(this, "Choississez une categorie", "Alerte", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_catSearchBtnActionPerformed

    private void catSearchCbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_catSearchCbActionPerformed
//        catSearchBtnActionPerformed(evt);
    }//GEN-LAST:event_catSearchCbActionPerformed

    private void categorieBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_categorieBtnActionPerformed
        switchPanels(catSearchPane);
    }//GEN-LAST:event_categorieBtnActionPerformed

    private void nameBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nameBtnActionPerformed
        switchPanels(nameSearchPane);
    }//GEN-LAST:event_nameBtnActionPerformed

    private void nameSearchBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nameSearchBtnActionPerformed
        Product product = productDAO.getByName(nameSearchTxt.getText());
        idTxt.setText(product.getId().toString());
        Category category = (new CategoryDAO()).getById(product.getCategoryId());
        categoriesCb.setSelectedItem(category.getType());
        nameTxt.setText(product.getName());
        priceTxt.setText(Double.toString((product.getPrice())));
        quantityTxt.setText(Double.toString((product.getQuantity())));
        descTxt.setText(product.getDescription());
        addDc.setDate(product.getUpdatedAt());
        addBtn.setEnabled(false);
        editBtn.setEnabled(true);
        cancelBtn.setEnabled(true);
        deleteBtn.setEnabled(true);
    }//GEN-LAST:event_nameSearchBtnActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel actionsPane;
    private javax.swing.JButton addBtn;
    private com.toedter.calendar.JDateChooser addDc;
    private javax.swing.JPanel addProdPane;
    private javax.swing.JButton cancelBtn;
    private javax.swing.JButton catSearchBtn;
    private javax.swing.JComboBox<String> catSearchCb;
    private javax.swing.JPanel catSearchPane;
    private javax.swing.JButton categorieBtn;
    private javax.swing.JComboBox<String> categoriesCb;
    private javax.swing.JButton deleteBtn;
    private javax.swing.JTextField descTxt;
    private javax.swing.JButton editBtn;
    private javax.swing.JTextField idTxt;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JButton nameBtn;
    private javax.swing.JButton nameSearchBtn;
    private javax.swing.JPanel nameSearchPane;
    private javax.swing.JTextField nameSearchTxt;
    private javax.swing.JTextField nameTxt;
    private javax.swing.JTextField priceTxt;
    private javax.swing.JPanel productsPane2;
    private javax.swing.JTable productsTable;
    private javax.swing.JTextField quantityTxt;
    private javax.swing.JLayeredPane searchLyP;
    private javax.swing.JPanel searchPane;
    // End of variables declaration//GEN-END:variables
}
