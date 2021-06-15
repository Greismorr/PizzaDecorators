package window;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.swing.AbstractListModel;
import javax.swing.DefaultListModel;

public class MainWindow extends javax.swing.JFrame {
	private Collection<String> decorators;
	private ActionListener prepareButtonListener;
	
    public MainWindow(Collection<String> decorators, ActionListener prepareButtonListener) {
    	this.setDecorators(decorators);
    	this.setPrepareButtonListener(prepareButtonListener);
        initComponents();
    }

    private void initComponents() {
    	String[] decoratorNames = this.decorators.toArray(new String[decorators.size()]);
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        availableDecorators = new javax.swing.JList<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        selectedDecorators = new javax.swing.JList<>();
        jPanel1 = new javax.swing.JPanel();
        insertButton = new javax.swing.JButton();
        removeButton = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        upButton = new javax.swing.JButton();
        downButton = new javax.swing.JButton();
        prepareButton = new javax.swing.JButton();
    	List<String> selectedDecoratorsNames = new ArrayList<String>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridLayout());

        availableDecorators.setModel(new AbstractListModel<String>() {
            public int getSize() { return decoratorNames.length; }
            public String getElementAt(int i) { return decoratorNames[i]; }
        });
        
        selectedDecorators.setModel(new DefaultListModel<String>() {
            public int getSize() { return selectedDecoratorsNames.size(); }
            public String getElementAt(int i) { return selectedDecoratorsNames.get(i); }
        });
        
        jScrollPane1.setViewportView(availableDecorators);

        jScrollPane2.setViewportView(selectedDecorators);

        jPanel1.setLayout(new java.awt.GridLayout(2, 1));

        insertButton.setText(">");
        insertButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		javax.swing.DefaultListModel<String> selectedDecoratorsModel = (DefaultListModel)selectedDecorators.getModel();
        		javax.swing.AbstractListModel<String> availableDecoratorsModel = (AbstractListModel) availableDecorators.getModel();
        		int index = availableDecorators.getSelectedIndex();

        		if(index >= 0) {
        			System.out.println("Adicionando " + availableDecoratorsModel.getElementAt(index) + " index" + index);
        			selectedDecoratorsModel.addElement(availableDecoratorsModel.getElementAt(index));
            		selectedDecoratorsNames.add((availableDecoratorsModel.getElementAt(index)));
        		}
        	}
        });
        jPanel1.add(insertButton);

        removeButton.setText("<");
        removeButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		javax.swing.DefaultListModel<String> selectedDecoratorsModel = (DefaultListModel)selectedDecorators.getModel();
        		int index = selectedDecorators.getSelectedIndex();

        		if(index >= 0) {
        			System.out.println("Removendo " + selectedDecoratorsModel.getElementAt(index) + " index" + index);
        			selectedDecoratorsModel.removeElement(selectedDecoratorsModel.getElementAt(index));
            		selectedDecoratorsNames.remove(selectedDecoratorsModel.getElementAt(index));
        		}
        	}
        });
        jPanel1.add(removeButton);

        jPanel2.setLayout(new java.awt.GridLayout(2, 1));

        upButton.setText("Subir");
        upButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		javax.swing.DefaultListModel<String> selectedDecoratorsModel = (DefaultListModel)selectedDecorators.getModel();
        		int index = selectedDecorators.getSelectedIndex();

        		if(index > 0) {
        			String temp = selectedDecoratorsModel.getElementAt(index - 1);
        			String tempString = selectedDecoratorsNames.get(index - 1);
        			
        			System.out.println("Subindo " + selectedDecorators.getModel().getElementAt(index) + " para index" + (index - 1));
        			selectedDecoratorsModel.set(index - 1, selectedDecoratorsModel.getElementAt(index));
        			selectedDecoratorsModel.set(index, temp);
        			selectedDecoratorsNames.set(index - 1, selectedDecoratorsNames.get(index));
        			selectedDecoratorsNames.set(index, tempString);
        		}
        	}
        });
        jPanel2.add(upButton);
        
        downButton.setText("Descer");
        downButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		javax.swing.DefaultListModel<String> selectedDecoratorsModel = (DefaultListModel)selectedDecorators.getModel();
        		int index = selectedDecorators.getSelectedIndex();

        		if(index < selectedDecoratorsModel.getSize() - 1) {
        			String temp = selectedDecoratorsModel.getElementAt(index + 1);
        			String tempString = selectedDecoratorsNames.get(index + 1);
        			
        			System.out.println("Descendo " + selectedDecorators.getModel().getElementAt(index) + " para index" + (index + 1));
        			selectedDecoratorsModel.set(index + 1, selectedDecoratorsModel.getElementAt(index));
        			selectedDecoratorsModel.set(index, temp);
        			selectedDecoratorsNames.set(index + 1, selectedDecoratorsNames.get(index));
        			selectedDecoratorsNames.set(index, tempString);
        		}
        	}
        });
        jPanel2.add(downButton);

        prepareButton.setText("Preparar");
        prepareButton.putClientProperty("selectedDecoratorsNames", selectedDecoratorsNames);
        prepareButton.addActionListener(this.prepareButtonListener);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(prepareButton)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 112, Short.MAX_VALUE)
                .addComponent(prepareButton)
                .addContainerGap())
        );

        getContentPane().add(jPanel3);

        pack();
    }
    
    public void setDecorators(Collection<String> decorators) {
    	this.decorators = decorators;
    }
    
    public void setPrepareButtonListener(ActionListener prepareButtonListener) {
    	this.prepareButtonListener = prepareButtonListener;
    }
    
    public static void createWindow(Collection decorators, ActionListener prepareButtonListener) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
            	MainWindow gui = new MainWindow(decorators, prepareButtonListener);
            	
                gui.setVisible(true);
            }
        });
    }
    
    private javax.swing.JList<String> availableDecorators;
    private javax.swing.JButton downButton;
    private javax.swing.JButton insertButton;
    private javax.swing.JButton prepareButton;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton removeButton;
    private javax.swing.JList<String> selectedDecorators;
    private javax.swing.JButton upButton;
}
