/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dev.stekos.cashregister.views;

import dev.stekos.cashregister.controllers.exceptions.NonexistentEntityException;
import dev.stekos.cashregister.dao.ProductDAO;
import dev.stekos.cashregister.dao.PurchaseDAO;
import dev.stekos.cashregister.dao.SubCategoryDAO;
import dev.stekos.cashregister.dao.SupplierDAO;
import dev.stekos.cashregister.dao.UserDAO;
import dev.stekos.cashregister.models.Product;
import dev.stekos.cashregister.models.Purchase;
import dev.stekos.cashregister.models.SubCategory;
import dev.stekos.cashregister.models.Supplier;
import dev.stekos.cashregister.models.User;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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
    private final PurchaseDAO purchaseDAO = new PurchaseDAO();

    /**
     * Creates new form ProductPane
     */
    public ProductForm() {
        initComponents();
        idTxt.setEditable(false);
        fillSubCategoriesComboBox(subCategoriesCb);
        fillSubCategoriesComboBox(subCatSearchCb);
        fillSuppliersComboBox(suppliersCb);
        fillSuppliersComboBox(supplierSearchCb);
        addBtn.setEnabled(false);
        editBtn.setEnabled(false);
        cancelBtn.setEnabled(false);
        deleteBtn.setEnabled(false);
        productsTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        productsTable.getTableHeader().setOpaque(false);
        productsTable.getTableHeader().setBackground(new Color(32, 136, 203));
        productsTable.getTableHeader().setForeground(new Color(255, 255, 255));
        productsTable.setRowHeight(25);
        productsTable.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
            if (productsTable.getSelectedRow() != -1) {
                try {
                    addBtn.setEnabled(false);
                    editBtn.setEnabled(true);
                    cancelBtn.setEnabled(true);
                    deleteBtn.setEnabled(true);
                    int selectedRow = productsTable.getSelectedRow();
                    idTxt.setText((productsTable.getValueAt(selectedRow, 0).toString()));
                    subCategoriesCb.setSelectedItem((productsTable.getValueAt(selectedRow, 1).toString()));
                    suppliersCb.setSelectedItem(productsTable.getValueAt(selectedRow, 2).toString());
                    nameTxt.setText((productsTable.getValueAt(selectedRow, 3).toString()));
                    buyingPriceTxt.setText((productsTable.getValueAt(selectedRow, 4).toString()));
                    quantityTxt.setText((productsTable.getValueAt(selectedRow, 5).toString()));
                    descTxt.setText((productsTable.getValueAt(selectedRow, 6).toString()));
                    Date date = new SimpleDateFormat("dd-MM-yyyy").parse((productsTable.getValueAt(selectedRow, 7)).toString());
                    addDc.setDate(date);
                } catch (ParseException ex) {
                    Logger.getLogger(ProductForm.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        fillProductsTable();
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
    
    private void setProductsTable(List<Product> products, int rows) {
        Object[][] table = new Object[rows][8];
        for (int i = 0; i < rows; i++) {
            table[i][0] = products.get(i).getId();
            SubCategory subCategory = (new SubCategoryDAO()).getById(products.get(i).getSubCategoryId());
            table[i][1] = subCategory.getLabel();
            Supplier supplier = (new SupplierDAO()).getById(products.get(i).getSupplierId());
            table[i][2] = supplier.getName();
            table[i][3] = products.get(i).getName();
            table[i][4] = products.get(i).getSellingPrice();
            table[i][5] = products.get(i).getQuantity();
            table[i][6] = products.get(i).getDescription();
            DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            table[i][7] = format.format(products.get(i).getUpdatedAt());
        }
        DefaultTableModel model = new DefaultTableModel(table, new String[]{
            "Id", "Sous Catégorie", "Fournisseur", "Nom Prod.", "Prix", "Quantité", "Description", "Date Ajout"
        });
        productsTable.setModel(model);
    }
    
    private void fillProductsTable() {
        List<Product> products = productDAO.getAll();
        int rows = products.size();
        setProductsTable(products, rows);
    }
    
    private void fillProductsTable(List<Product> products) {
        int rows = products.size();
        setProductsTable(products, rows);
    }
    
    private void setComboBox(JComboBox comboBox) {
        comboBox.removeAllItems();
        comboBox.addItem("");
    }

    private void fillSubCategoriesComboBox(JComboBox comboBox) {
        List<SubCategory> subCategories = (new SubCategoryDAO()).getAll();
        setComboBox(comboBox);
        subCategories.forEach((category) -> {
            comboBox.addItem(category.getLabel());
        });
    }
    
    private void fillSuppliersComboBox (JComboBox comboBox) {
        List<Supplier> suppliers = (new SupplierDAO()).getAll();
        setComboBox(comboBox);
        suppliers.forEach((supplier) -> {
            comboBox.addItem(supplier.getName());
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
        buyingPriceTxt = new javax.swing.JTextField();
        nameTxt = new javax.swing.JTextField();
        subCategoriesCb = new javax.swing.JComboBox<>();
        idTxt = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        addDc = new com.toedter.calendar.JDateChooser();
        suppliersCb = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        sellingPriceTxt = new javax.swing.JTextField();
        actionsPane = new javax.swing.JPanel();
        addBtn = new javax.swing.JButton();
        editBtn = new javax.swing.JButton();
        cancelBtn = new javax.swing.JButton();
        deleteBtn = new javax.swing.JButton();
        searchPane = new javax.swing.JPanel();
        categorieBtn = new javax.swing.JButton();
        nameBtn = new javax.swing.JButton();
        supplierBtn = new javax.swing.JButton();
        searchLyP = new javax.swing.JLayeredPane();
        catSearchPane = new javax.swing.JPanel();
        subCatSearchBtn = new javax.swing.JButton();
        subCatSearchCb = new javax.swing.JComboBox<>();
        supplierSearchPane = new javax.swing.JPanel();
        supplierSearchBtn = new javax.swing.JButton();
        supplierSearchCb = new javax.swing.JComboBox<>();
        nameSearchPane = new javax.swing.JPanel();
        nameSearchTxt = new javax.swing.JTextField();
        nameSearchBtn = new javax.swing.JButton();
        productsPane2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        productsTable = new javax.swing.JTable();

        addProdPane.setBackground(new java.awt.Color(204, 204, 204, 80));
        addProdPane.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Ajouter un produit", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Semibold", 0, 14))); // NOI18N
        addProdPane.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N

        jLabel2.setText("Id");

        jLabel3.setText("Sous Cat.");

        jLabel5.setText("Nom");

        jLabel6.setText("Prix T. Achat");

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

        buyingPriceTxt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                buyingPriceTxtFocusGained(evt);
            }
        });

        nameTxt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                nameTxtFocusGained(evt);
            }
        });

        subCategoriesCb.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        subCategoriesCb.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                subCategoriesCbFocusGained(evt);
            }
        });

        idTxt.setToolTipText("Ce champ n'est pas éditable");

        jLabel1.setText("Date Ajout");

        suppliersCb.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel4.setText("Fournisseur");

        jLabel9.setText("Prix Vente");

        sellingPriceTxt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                sellingPriceTxtFocusGained(evt);
            }
        });

        javax.swing.GroupLayout addProdPaneLayout = new javax.swing.GroupLayout(addProdPane);
        addProdPane.setLayout(addProdPaneLayout);
        addProdPaneLayout.setHorizontalGroup(
            addProdPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addProdPaneLayout.createSequentialGroup()
                .addGroup(addProdPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, addProdPaneLayout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addGroup(addProdPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2))
                        .addGap(74, 74, 74)
                        .addGroup(addProdPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(subCategoriesCb, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(idTxt)))
                    .addGroup(addProdPaneLayout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(addProdPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8)
                            .addComponent(jLabel1)
                            .addComponent(jLabel4)
                            .addComponent(jLabel9))
                        .addGap(74, 74, 74)
                        .addGroup(addProdPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(sellingPriceTxt)
                            .addComponent(descTxt, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(quantityTxt, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(buyingPriceTxt, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(nameTxt, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(suppliersCb, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(addDc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
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
                    .addComponent(subCategoriesCb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(addProdPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(suppliersCb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(addProdPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(addProdPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(quantityTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(addProdPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buyingPriceTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(addProdPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sellingPriceTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(addProdPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(descTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(addProdPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(addDc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18))
        );

        actionsPane.setBackground(new java.awt.Color(204, 204, 204, 80));
        actionsPane.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Actions", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Semibold", 0, 14))); // NOI18N
        actionsPane.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N

        addBtn.setBackground(new java.awt.Color(204, 204, 255, 40));
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

        editBtn.setBackground(new java.awt.Color(204, 204, 255, 40));
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

        cancelBtn.setBackground(new java.awt.Color(204, 204, 255, 40));
        cancelBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dev/stekos/cashregister/icons/cancel.png"))); // NOI18N
        cancelBtn.setText("Annuler");
        cancelBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelBtnActionPerformed(evt);
            }
        });

        deleteBtn.setBackground(new java.awt.Color(204, 204, 255, 40));
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
                .addGap(83, 83, 83)
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
                .addGroup(actionsPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(actionsPaneLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(deleteBtn))
                    .addGroup(actionsPaneLayout.createSequentialGroup()
                        .addGroup(actionsPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(addBtn)
                            .addComponent(editBtn))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cancelBtn)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(18, 18, 18))
        );

        searchPane.setBackground(new java.awt.Color(204, 204, 204, 80));
        searchPane.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Rechercher", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Semibold", 0, 14))); // NOI18N
        searchPane.setFont(new java.awt.Font("Segoe UI Historic", 0, 12)); // NOI18N

        categorieBtn.setBackground(new java.awt.Color(204, 204, 255));
        categorieBtn.setText("Sous cat.");
        categorieBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                categorieBtnActionPerformed(evt);
            }
        });

        nameBtn.setBackground(new java.awt.Color(204, 204, 255));
        nameBtn.setText("Nom");
        nameBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nameBtnActionPerformed(evt);
            }
        });

        supplierBtn.setBackground(new java.awt.Color(204, 204, 255));
        supplierBtn.setText("Fournisseur");
        supplierBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                supplierBtnActionPerformed(evt);
            }
        });

        searchLyP.setLayout(new java.awt.CardLayout());

        catSearchPane.setBackground(new java.awt.Color(204, 204, 204, 80));

        subCatSearchBtn.setBackground(new java.awt.Color(204, 204, 255));
        subCatSearchBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dev/stekos/cashregister/icons/iconfinder_Find_132785.png"))); // NOI18N
        subCatSearchBtn.setText("Rechercher");
        subCatSearchBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subCatSearchBtnActionPerformed(evt);
            }
        });

        subCatSearchCb.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        subCatSearchCb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subCatSearchCbActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout catSearchPaneLayout = new javax.swing.GroupLayout(catSearchPane);
        catSearchPane.setLayout(catSearchPaneLayout);
        catSearchPaneLayout.setHorizontalGroup(
            catSearchPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(catSearchPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(subCatSearchCb, 0, 233, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(subCatSearchBtn)
                .addContainerGap())
        );
        catSearchPaneLayout.setVerticalGroup(
            catSearchPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, catSearchPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(catSearchPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(subCatSearchBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(subCatSearchCb))
                .addContainerGap())
        );

        searchLyP.add(catSearchPane, "card2");

        supplierSearchPane.setBackground(new java.awt.Color(204, 204, 204, 80));

        supplierSearchBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dev/stekos/cashregister/icons/iconfinder_Find_132785.png"))); // NOI18N
        supplierSearchBtn.setText("Rechercher");
        supplierSearchBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                supplierSearchBtnActionPerformed(evt);
            }
        });

        supplierSearchCb.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        supplierSearchCb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                supplierSearchCbActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout supplierSearchPaneLayout = new javax.swing.GroupLayout(supplierSearchPane);
        supplierSearchPane.setLayout(supplierSearchPaneLayout);
        supplierSearchPaneLayout.setHorizontalGroup(
            supplierSearchPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(supplierSearchPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(supplierSearchCb, 0, 233, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(supplierSearchBtn)
                .addContainerGap())
        );
        supplierSearchPaneLayout.setVerticalGroup(
            supplierSearchPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, supplierSearchPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(supplierSearchPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(supplierSearchBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(supplierSearchCb))
                .addContainerGap())
        );

        searchLyP.add(supplierSearchPane, "card2");

        nameSearchPane.setBackground(new java.awt.Color(204, 204, 204, 80));

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
                .addComponent(nameSearchTxt, javax.swing.GroupLayout.DEFAULT_SIZE, 233, Short.MAX_VALUE)
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
                    .addComponent(nameSearchBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                        .addGap(11, 11, 11)
                        .addComponent(categorieBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(43, 43, 43)
                        .addComponent(supplierBtn)
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
                    .addComponent(nameBtn)
                    .addComponent(supplierBtn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(searchLyP))
        );

        productsPane2.setBackground(new java.awt.Color(204, 204, 204, 80));
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
        productsTable.setFocusable(false);
        productsTable.setIntercellSpacing(new java.awt.Dimension(0, 0));
        productsTable.setRowHeight(25);
        productsTable.setSelectionBackground(new java.awt.Color(232, 57, 95));
        jScrollPane3.setViewportView(productsTable);

        javax.swing.GroupLayout productsPane2Layout = new javax.swing.GroupLayout(productsPane2);
        productsPane2.setLayout(productsPane2Layout);
        productsPane2Layout.setHorizontalGroup(
            productsPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(productsPane2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 674, Short.MAX_VALUE)
                .addContainerGap())
        );
        productsPane2Layout.setVerticalGroup(
            productsPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, productsPane2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 490, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(actionsPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(addProdPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(searchPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(productsPane2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(productsPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(searchPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(addProdPane, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(actionsPane, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void descTxtFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_descTxtFocusGained
        enableAddButton();
    }//GEN-LAST:event_descTxtFocusGained

    private void quantityTxtFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_quantityTxtFocusGained
        enableAddButton();
    }//GEN-LAST:event_quantityTxtFocusGained

    private void buyingPriceTxtFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_buyingPriceTxtFocusGained
        enableAddButton();
    }//GEN-LAST:event_buyingPriceTxtFocusGained

    private void nameTxtFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_nameTxtFocusGained
        enableAddButton();
    }//GEN-LAST:event_nameTxtFocusGained

    private void addBtnFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_addBtnFocusLost
        this.fillProductsTable();
    }//GEN-LAST:event_addBtnFocusLost

    private void addBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addBtnActionPerformed
        try {
            if ("".equals(nameTxt.getText()) || "".equals(descTxt.getText()) || "".equals(buyingPriceTxt.getText())
                || "".equals(sellingPriceTxt.getText()) || "".equals(quantityTxt.getText()) 
                    || "".equals(subCategoriesCb.getSelectedItem()) || "".equals(suppliersCb.getSelectedItem())) {
                JOptionPane.showMessageDialog(this, "Oups ! Un champs est toujours vide, Veuillez la remplir.");
            } else {
                Product product = new Product();
                product.setName(nameTxt.getText());
                
                Purchase purchase = new Purchase();
                User user = new UserDAO().getById(1);
                System.out.println(user);
                purchase.setUserId(user.getId());
                
                if(isDouble(buyingPriceTxt.getText())) {
                    purchase.setBuyingPrice(Double.parseDouble(buyingPriceTxt.getText()));
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Le champs Prix de vente doit contenir un nombre réel",
                            "Alerte", JOptionPane.WARNING_MESSAGE
                    );
                }
                
                if(isDouble(sellingPriceTxt.getText())) {
                    product.setSellingPrice(Double.parseDouble(sellingPriceTxt.getText()));
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Le champs Prix doit contenir un nombre réel",
                            "Alerte", JOptionPane.WARNING_MESSAGE
                    );
                }
                
                if(isDouble(quantityTxt.getText())) {
                    product.setQuantity(Double.parseDouble(quantityTxt.getText()));
                    purchase.setQuantity(Integer.parseInt(quantityTxt.getText()));
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Le champs Quantité doit contenir un nombre réel",
                            "Alerte", JOptionPane.WARNING_MESSAGE
                    );
                }
                
                SubCategory subCategory = (new SubCategoryDAO()).getByName(subCategoriesCb.getSelectedItem().toString());
                product.setSubCategoryId(subCategory.getId());
                Supplier supplier = (new SupplierDAO()).getByName(suppliersCb.getSelectedItem().toString());
                product.setSupplierId(supplier.getId());
                purchase.setSupplierId(supplier.getId());
                product.setDescription(descTxt.getText());
                
                if (addDc.getDate() == null) {
                    Date date = new Date();
                    product.setCreatedAt(date);
                    product.setUpdatedAt(date);
                    purchase.setCreatedAt(date);
                    purchase.setUpdatedAt(date);
                } else {
                    product.setCreatedAt(addDc.getDate());
                    product.setUpdatedAt(addDc.getDate());
                    purchase.setCreatedAt(addDc.getDate());
                    purchase.setUpdatedAt(addDc.getDate());
                }
                
                productDAO.add(product);
                Product p = productDAO.getByName(nameTxt.getText());
                purchase.setProductId(p.getId());
                System.out.println(purchase.getUserId());
                System.out.println(purchase.getSupplierId());
                System.out.println(purchase.getProductId());
                System.out.println(purchase.getQuantity());
                System.out.println(purchase.getBuyingPrice());
                System.out.println(purchase.getCreatedAt());
                System.out.println(purchase.getUpdatedAt());
                purchaseDAO.add(purchase);
                fillProductsTable();
                cancelBtnActionPerformed(evt);
            }
        } catch (Exception ex) {
            Logger.getLogger(ProductForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_addBtnActionPerformed

    private void editBtnFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_editBtnFocusLost
        //this.fillProductsTable();
    }//GEN-LAST:event_editBtnFocusLost

    private void editBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editBtnActionPerformed
        try {
            Product product = productDAO.getById(Integer.parseInt(idTxt.getText()));
            
            if ("".equals(nameTxt.getText()) || "".equals(descTxt.getText())
                || "".equals(buyingPriceTxt.getText()) || "".equals(quantityTxt.getText()) 
                    || "".equals(subCategoriesCb.getSelectedItem()) || "".equals(suppliersCb.getSelectedItem())) {
                JOptionPane.showMessageDialog(this, "Oups ! Un champs est toujours vide, Veuillez la remplir.");
            } else {
                product.setName(nameTxt.getText());
                product.setDescription(descTxt.getText());
                SubCategory subCategory = (new SubCategoryDAO()).getByName(subCategoriesCb.getSelectedItem().toString());
                product.setSubCategoryId(subCategory.getId());
                Supplier supplier = (new SupplierDAO()).getByName(suppliersCb.getSelectedItem().toString());
                product.setSupplierId(supplier.getId());
            }
            
            if(isDouble(buyingPriceTxt.getText())) {
                product.setSellingPrice(Double.parseDouble(buyingPriceTxt.getText()));
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
            
            if (addDc.getDate() != null) {
                product.setUpdatedAt(addDc.getDate());
            } else {
                JOptionPane.showMessageDialog(this,
                        "Veuillez entrer une date valide",
                        "Alerte", JOptionPane.WARNING_MESSAGE
                );
            }
            
            productDAO.edit(product);
            fillProductsTable();
            this.cancelBtnActionPerformed(evt);
        } catch (Exception ex) {
            Logger.getLogger(ProductForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_editBtnActionPerformed

    private void cancelBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelBtnActionPerformed
        idTxt.setText("");
        subCategoriesCb.setSelectedItem("");
        suppliersCb.setSelectedItem("");
        nameTxt.setText("");
        buyingPriceTxt.setText("");
        quantityTxt.setText("");
        descTxt.setText("");
        addDc.setCalendar(null);
        addBtn.setEnabled(false);
        editBtn.setEnabled(false);
        cancelBtn.setEnabled(false);
        deleteBtn.setEnabled(false);
    }//GEN-LAST:event_cancelBtnActionPerformed

    private void deleteBtnFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_deleteBtnFocusLost
        //this.fillProductsTable();
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
        fillProductsTable();
    }//GEN-LAST:event_deleteBtnActionPerformed

    private void subCategoriesCbFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_subCategoriesCbFocusGained
        enableAddButton();
    }//GEN-LAST:event_subCategoriesCbFocusGained

    private void subCatSearchBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subCatSearchBtnActionPerformed
        if(!("".equals(subCatSearchCb.getSelectedItem().toString()))){
            SubCategory subCategory = (new SubCategoryDAO()).getByName(subCatSearchCb.getSelectedItem().toString());
            List<Product> products = productDAO.getBySubCategoryId(subCategory.getId());
            fillProductsTable(products);
            addBtn.setEnabled(false);
            editBtn.setEnabled(true);
            cancelBtn.setEnabled(true);
            deleteBtn.setEnabled(true);
        } else {
            JOptionPane.showMessageDialog(this, "Choississez une sous-categorie", "Alerte", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_subCatSearchBtnActionPerformed

    private void subCatSearchCbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subCatSearchCbActionPerformed
//        catSearchBtnActionPerformed(evt);
    }//GEN-LAST:event_subCatSearchCbActionPerformed

    private void categorieBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_categorieBtnActionPerformed
        switchPanels(catSearchPane);
    }//GEN-LAST:event_categorieBtnActionPerformed

    private void nameBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nameBtnActionPerformed
        switchPanels(nameSearchPane);
    }//GEN-LAST:event_nameBtnActionPerformed

    private void nameSearchBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nameSearchBtnActionPerformed
        Product product = productDAO.getByName(nameSearchTxt.getText());
        idTxt.setText(product.getId().toString());
        SubCategory subCategory = (new SubCategoryDAO()).getByName(subCategoriesCb.getSelectedItem().toString());
        Supplier supplier = (new SupplierDAO()).getByName(suppliersCb.getSelectedItem().toString());
        subCategoriesCb.setSelectedItem(subCategory.getLabel());
        suppliersCb.setSelectedItem(supplier.getName());
        nameTxt.setText(product.getName());
        buyingPriceTxt.setText(Double.toString((product.getSellingPrice())));
        quantityTxt.setText(Double.toString((product.getQuantity())));
        descTxt.setText(product.getDescription());
        addDc.setDate(product.getUpdatedAt());
        addBtn.setEnabled(false);
        editBtn.setEnabled(true);
        cancelBtn.setEnabled(true);
        deleteBtn.setEnabled(true);
    }//GEN-LAST:event_nameSearchBtnActionPerformed

    private void supplierSearchBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_supplierSearchBtnActionPerformed
        if(!("".equals(supplierSearchCb.getSelectedItem().toString()))){
            Supplier supplier = (new SupplierDAO()).getByName(supplierSearchCb.getSelectedItem().toString());
            List<Product> products = productDAO.getBySubCategoryId(supplier.getId());
            fillProductsTable(products);
            addBtn.setEnabled(false);
            editBtn.setEnabled(true);
            cancelBtn.setEnabled(true);
            deleteBtn.setEnabled(true);
        } else {
            JOptionPane.showMessageDialog(this, "Choississez un fournisseur", "Alerte", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_supplierSearchBtnActionPerformed

    private void supplierSearchCbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_supplierSearchCbActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_supplierSearchCbActionPerformed

    private void supplierBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_supplierBtnActionPerformed
        switchPanels(supplierSearchPane);
    }//GEN-LAST:event_supplierBtnActionPerformed

    private void sellingPriceTxtFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sellingPriceTxtFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_sellingPriceTxtFocusGained


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel actionsPane;
    private javax.swing.JButton addBtn;
    private com.toedter.calendar.JDateChooser addDc;
    private javax.swing.JPanel addProdPane;
    private javax.swing.JTextField buyingPriceTxt;
    private javax.swing.JButton cancelBtn;
    private javax.swing.JPanel catSearchPane;
    private javax.swing.JButton categorieBtn;
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
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JButton nameBtn;
    private javax.swing.JButton nameSearchBtn;
    private javax.swing.JPanel nameSearchPane;
    private javax.swing.JTextField nameSearchTxt;
    private javax.swing.JTextField nameTxt;
    private javax.swing.JPanel productsPane2;
    private javax.swing.JTable productsTable;
    private javax.swing.JTextField quantityTxt;
    private javax.swing.JLayeredPane searchLyP;
    private javax.swing.JPanel searchPane;
    private javax.swing.JTextField sellingPriceTxt;
    private javax.swing.JButton subCatSearchBtn;
    private javax.swing.JComboBox<String> subCatSearchCb;
    private javax.swing.JComboBox<String> subCategoriesCb;
    private javax.swing.JButton supplierBtn;
    private javax.swing.JButton supplierSearchBtn;
    private javax.swing.JComboBox<String> supplierSearchCb;
    private javax.swing.JPanel supplierSearchPane;
    private javax.swing.JComboBox<String> suppliersCb;
    // End of variables declaration//GEN-END:variables
}
