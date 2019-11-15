/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dev.stekos.cashregister.views;

import dev.stekos.cashregister.controllers.exceptions.NonexistentEntityException;
import dev.stekos.cashregister.dao.SupplierDAO;
import dev.stekos.cashregister.models.Supplier;
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
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Stekos
 */
public class SupplierForm extends javax.swing.JPanel {
    private final SupplierDAO supplierDAO = new SupplierDAO();

    /**
     * Creates new form SupplierForm
     */
    public SupplierForm() {
        initComponents();
        idTxt.setEditable(false);
        addBtn.setEnabled(false);
        editBtn.setEnabled(false);
        cancelBtn.setEnabled(false);
        deleteBtn.setEnabled(false);
        suppliersTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        suppliersTable.getTableHeader().setOpaque(false);
        suppliersTable.getTableHeader().setBackground(new Color(32, 136, 203));
        suppliersTable.getTableHeader().setForeground(new Color(255, 255, 255));
        suppliersTable.setRowHeight(25);
        suppliersTable.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
            if(suppliersTable.getSelectedRow() != -1) {
                try {
                    addBtn.setEnabled(false);
                    editBtn.setEnabled(true);
                    cancelBtn.setEnabled(true);
                    deleteBtn.setEnabled(true);
                    int selectedRow = suppliersTable.getSelectedRow();
                    idTxt.setText((suppliersTable.getValueAt(selectedRow, 0).toString()));
                    nameTxt.setText((suppliersTable.getValueAt(selectedRow, 1).toString()));
                    phoneTxt.setText(suppliersTable.getValueAt(selectedRow, 2).toString());
                    addressTxt.setText(suppliersTable.getValueAt(selectedRow, 3).toString());
                    Date date = new SimpleDateFormat("dd-MM-yyyy").parse((suppliersTable.getValueAt(selectedRow, 4)).toString());
                    addDc.setDate(date);
                } catch (ParseException ex) {
                    Logger.getLogger(SupplierForm.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        fillSuppliersTable();
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
    
    private void fillSuppliersTable() {
        List<Supplier> suppliers = supplierDAO.getAll();
        int rows = suppliers.size();
        Object[][] table = new Object[rows][5];
        for (int i = 0; i < rows; i++) {
            table[i][0] = suppliers.get(i).getId();
            table[i][1] = suppliers.get(i).getName();
            table[i][2] = suppliers.get(i).getPhone();
            table[i][3] = suppliers.get(i).getAddress();
            DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            table[i][4] = format.format(suppliers.get(i).getUpdatedAt());
        }
        DefaultTableModel model = new DefaultTableModel(table, new String[] {
            "Id", "Nom", "Téléphone", "Adresse", "Date Ajout"
        });
        suppliersTable.setModel(model);
    }
    
    private void enableAddButton() {
        if("".equals(idTxt.getText())) {
            addBtn.setEnabled(true);
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

        actionsPane = new javax.swing.JPanel();
        addBtn = new javax.swing.JButton();
        editBtn = new javax.swing.JButton();
        cancelBtn = new javax.swing.JButton();
        deleteBtn = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        idTxt = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        nameTxt = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        phoneTxt = new javax.swing.JTextField();
        addDc = new com.toedter.calendar.JDateChooser();
        jLabel4 = new javax.swing.JLabel();
        addressTxt = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        searchTxt = new javax.swing.JTextField();
        searchBtn = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        suppliersTable = new javax.swing.JTable();

        actionsPane.setBackground(new java.awt.Color(204, 204, 204, 80));
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
                .addGap(83, 83, 83)
                .addGroup(actionsPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(addBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cancelBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(actionsPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(editBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(deleteBtn))
                .addContainerGap(71, Short.MAX_VALUE))
        );
        actionsPaneLayout.setVerticalGroup(
            actionsPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(actionsPaneLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(actionsPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(actionsPaneLayout.createSequentialGroup()
                        .addComponent(editBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(deleteBtn))
                    .addGroup(actionsPaneLayout.createSequentialGroup()
                        .addComponent(addBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cancelBtn)))
                .addContainerGap(26, Short.MAX_VALUE))
        );

        jPanel1.setBackground(new java.awt.Color(204, 204, 204, 80));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Ajouter un fournisseur", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Semibold", 0, 14))); // NOI18N

        jLabel1.setText("Id");

        jLabel2.setText("Nom");

        nameTxt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                nameTxtFocusGained(evt);
            }
        });

        jLabel3.setText("Téléphone");

        phoneTxt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                phoneTxtFocusGained(evt);
            }
        });

        jLabel4.setText("Adresse");

        addressTxt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                addressTxtFocusGained(evt);
            }
        });

        jLabel5.setText("Date Ajout");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addComponent(jLabel1))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addGap(38, 38, 38)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(addressTxt)
                    .addComponent(phoneTxt)
                    .addComponent(nameTxt)
                    .addComponent(idTxt)
                    .addComponent(addDc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(idTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(nameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(phoneTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(addressTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(addDc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addContainerGap(44, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(204, 204, 204, 80));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Rechercher", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Semibold", 0, 14))); // NOI18N

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

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(searchTxt)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(searchBtn)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchBtn))
                .addContainerGap(33, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(204, 204, 204, 80));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Liste des fournisseurs", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Semibold", 0, 14))); // NOI18N

        suppliersTable.setAutoCreateRowSorter(true);
        suppliersTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        suppliersTable.setFocusable(false);
        suppliersTable.setIntercellSpacing(new java.awt.Dimension(0, 0));
        suppliersTable.setRowHeight(25);
        suppliersTable.setSelectionBackground(new java.awt.Color(232, 57, 95));
        jScrollPane1.setViewportView(suppliersTable);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 686, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jScrollPane1)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(actionsPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(actionsPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void addBtnFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_addBtnFocusLost
        // fillsuppliersTable();
    }//GEN-LAST:event_addBtnFocusLost

    private void addBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addBtnActionPerformed
        try {
            if ("".equals(nameTxt.getText()) || "".equals(phoneTxt.getText())
                || "".equals(addressTxt.getText())) {
                JOptionPane.showMessageDialog(this, "Oups ! Un champs est toujours vide, Veuillez la remplir.");
            } else {
                Supplier supplier = new Supplier();
                supplier.setName(nameTxt.getText());
                supplier.setPhone(phoneTxt.getText());
                supplier.setAddress(addressTxt.getText());
                if(addDc.getDate() == null) {
                    Date date = new Date();
                    supplier.setCreatedAt(date);
                    supplier.setUpdatedAt(date);
                } else {
                    supplier.setCreatedAt(addDc.getDate());
                    supplier.setUpdatedAt(addDc.getDate());
                }
                supplierDAO.add(supplier);
                fillSuppliersTable();
                cancelBtnActionPerformed(evt);
            }
        } catch (Exception ex) {
            Logger.getLogger(SupplierForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_addBtnActionPerformed

    private void editBtnFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_editBtnFocusLost
        this.fillSuppliersTable();
    }//GEN-LAST:event_editBtnFocusLost

    private void editBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editBtnActionPerformed
        try {
            Supplier supplier = supplierDAO.getById(Integer.parseInt(idTxt.getText()));
            supplier.setName(nameTxt.getText());
            supplier.setPhone(phoneTxt.getText());
            supplier.setAddress(addressTxt.getText());
            if(addDc.getDate() == null) {
                JOptionPane.showMessageDialog(this, "Veuillez entrer une date valide", "Alerte", JOptionPane.WARNING_MESSAGE);
            } else {
                supplier.setUpdatedAt(addDc.getDate());
                supplierDAO.edit(supplier);
                fillSuppliersTable();
                this.cancelBtnActionPerformed(evt);
            }
            
        } catch (Exception ex) {
            Logger.getLogger(SupplierForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_editBtnActionPerformed

    private void cancelBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelBtnActionPerformed
        idTxt.setText("");
        nameTxt.setText("");
        phoneTxt.setText("");
        addressTxt.setText("");
        addDc.setCalendar(null);
        addBtn.setEnabled(true);
        editBtn.setEnabled(false);
        cancelBtn.setEnabled(false);
        deleteBtn.setEnabled(false);
    }//GEN-LAST:event_cancelBtnActionPerformed

    private void deleteBtnFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_deleteBtnFocusLost
        //this.fillSuppliersTable();
    }//GEN-LAST:event_deleteBtnFocusLost

    private void deleteBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteBtnActionPerformed
        int i = JOptionPane.showConfirmDialog(this, "Voulez-vous supprimer ce prdouit ?");
        if (i == 0) {
            try {
                supplierDAO.remove(Integer.parseInt(idTxt.getText()));
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(SupplierForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        fillSuppliersTable();
        cancelBtnActionPerformed(evt);
    }//GEN-LAST:event_deleteBtnActionPerformed

    private void nameTxtFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_nameTxtFocusGained
        enableAddButton();
    }//GEN-LAST:event_nameTxtFocusGained

    private void phoneTxtFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_phoneTxtFocusGained
        enableAddButton();
    }//GEN-LAST:event_phoneTxtFocusGained

    private void addressTxtFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_addressTxtFocusGained
        enableAddButton();
    }//GEN-LAST:event_addressTxtFocusGained

    private void searchBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchBtnActionPerformed
        Supplier supplier = supplierDAO.getByName(searchTxt.getText());
        idTxt.setText(supplier.getId().toString());
        nameTxt.setText(supplier.getName());
        phoneTxt.setText(supplier.getPhone());
        addressTxt.setText(supplier.getAddress());
        addDc.setDate(supplier.getUpdatedAt());
        addBtn.setEnabled(false);
        editBtn.setEnabled(true);
        cancelBtn.setEnabled(true);
        deleteBtn.setEnabled(true);
    }//GEN-LAST:event_searchBtnActionPerformed

    private void searchTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchTxtActionPerformed
        searchBtnActionPerformed(evt);
    }//GEN-LAST:event_searchTxtActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel actionsPane;
    private javax.swing.JButton addBtn;
    private com.toedter.calendar.JDateChooser addDc;
    private javax.swing.JTextField addressTxt;
    private javax.swing.JButton cancelBtn;
    private javax.swing.JButton deleteBtn;
    private javax.swing.JButton editBtn;
    private javax.swing.JTextField idTxt;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField nameTxt;
    private javax.swing.JTextField phoneTxt;
    private javax.swing.JButton searchBtn;
    private javax.swing.JTextField searchTxt;
    private javax.swing.JTable suppliersTable;
    // End of variables declaration//GEN-END:variables
}
