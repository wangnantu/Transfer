package com.gtja.qh.View;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.UIManager;

/**
 *
 * @author Administrator
 */
public class Frame extends JFrame {

    private javax.swing.JButton selectFileButton;
    private javax.swing.JButton generateButton;
    private javax.swing.JCheckBox TxtCB;
    private javax.swing.JCheckBox DbfCB;   
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JTextField filePath;
    private javax.swing.JTextField date;
    
    public Frame() {
        initComponents();
        this.setSizeAndCentralizeMe(450, 300);
        this.setTitle("Excel文件转换工具(结息专用）");
        this.getRootPane().setDefaultButton(generateButton);
        this.setVisible(true);
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {
         String windows="com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
        try {
            UIManager.setLookAndFeel(windows);
        } catch (Exception e) {
            e.printStackTrace();
        } 
        filePath = new javax.swing.JTextField();
        selectFileButton= new javax.swing.JButton();
        TxtCB = new javax.swing.JCheckBox();
        DbfCB = new javax.swing.JCheckBox();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        date = new javax.swing.JTextField();
        generateButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        //filePath.setText("filePath");

        selectFileButton.setText("选择文件");
//        selectFileButton.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                selectFileButtonActionPerformed(evt);
//            }
//        });

        TxtCB.setText("TXT(上期所）");
        TxtCB.setSelected(true);

        DbfCB.setText("DBF（中金所）");
        DbfCB.setSelected(true);
//        DbfCB.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                DbfCBActionPerformed(evt);
//            }
//        });

        jLabel1.setText("源文件：");
        
        jLabel2.setText("输出文件类型：");

        jLabel3.setText("日期（YYYYMMDD）：");

        //date.setText("date");
        Date dt = new Date();
        SimpleDateFormat sdf =new SimpleDateFormat("yyyyMMdd");
        date.setText(sdf.format(dt));

        generateButton.setText("生成");

         javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(filePath, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(selectFileButton))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(TxtCB)
                        .addGap(18, 18, 18)
                        .addComponent(DbfCB))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(33, 33, 33)
                        .addComponent(date, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(generateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(filePath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(selectFileButton))
                .addGap(42, 42, 42)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(TxtCB)
                    .addComponent(DbfCB))
                .addGap(42, 42, 42)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(date, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(generateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        pack();
    }
//    private void selectFileButtonActionPerformed(java.awt.event.ActionEvent evt) {                                         
//        // TODO add your handling code here:
//    }                                        
//
//    private void DbfCBActionPerformed(java.awt.event.ActionEvent evt) {                                           
//        // TODO add your handling code here:
//    }                                          

    public JButton getSelectFileButton() {
        return selectFileButton;
    }

    public JButton getGenerateButton() {
        return generateButton;
    }

    public JCheckBox getTxtCB() {
        return TxtCB;
    }

    public JCheckBox getDbfCB() {
        return DbfCB;
    }

    public JTextField getFilePath() {
        return filePath;
    }

    public JTextField getDate() {
        return date;
    }

    private void setSizeAndCentralizeMe(int width, int height) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize(width, height);
		this.setLocation(screenSize.width / 2 - width / 2, screenSize.height/ 2 - height / 2);
	}
//    public static void main(String args[]) {
//   
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new Frame().setVisible(true);
//            }
//        });
//    }
              
}
