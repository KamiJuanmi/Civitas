/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import civitas.Jugador;
import civitas.TituloPropiedad;
import java.util.ArrayList;

/**
 *
 * @author adri
 */
public class JugadorPanel extends javax.swing.JPanel {

    private Jugador jugador;
   
    public JugadorPanel() {
        initComponents();
    }
    
    public void setJugador(Jugador jugador)
    {
        this.jugador=jugador;
        
        nombre.setText(this.jugador.getNombre());
        saldo.setText(Float.toString(this.jugador.getSaldo()));
        carcel.setText(this.jugador.isEncarcelado() ? "Si" : "No");
        especulador.setText(this.jugador.isEspeculador() ? "Si" : "No");
        
        rellenaPropiedades(this.jugador.getPropiedades());
        
        repaint();
    }
    
    private void rellenaPropiedades(ArrayList<TituloPropiedad> lista){
        propiedades.removeAll();
        for(TituloPropiedad t : lista){
            PropiedadPanel vistaPropiedad = new PropiedadPanel();
            vistaPropiedad.setPropiedad(t);
            
            propiedades.add(vistaPropiedad);
        }
        repaint();
        revalidate();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        saldo = new javax.swing.JTextField();
        carcel = new javax.swing.JTextField();
        nombre = new javax.swing.JTextField();
        especulador = new javax.swing.JTextField();
        propiedades = new javax.swing.JPanel();

        jLabel1.setText("jLabel1");

        setName(""); // NOI18N
        setPreferredSize(new java.awt.Dimension(414, 262));

        jLabel2.setText("Nombre:");
        jLabel2.setEnabled(false);

        jLabel3.setText("Saldo:");
        jLabel3.setEnabled(false);

        jLabel4.setText("Encarcelado:");
        jLabel4.setEnabled(false);

        jLabel5.setText("Especulador:");
        jLabel5.setEnabled(false);

        saldo.setText("jTextField2");
        saldo.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        saldo.setEnabled(false);

        carcel.setText("jTextField3");
        carcel.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        carcel.setEnabled(false);

        nombre.setText("nombre");
        nombre.setToolTipText("");
        nombre.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        nombre.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        nombre.setEnabled(false);

        especulador.setText("jTextField4");
        especulador.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        especulador.setEnabled(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(35, 35, 35)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(saldo)
                            .addComponent(nombre)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(especulador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(carcel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 58, Short.MAX_VALUE)
                .addComponent(propiedades, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(saldo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(carcel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(especulador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(propiedades, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(25, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField carcel;
    private javax.swing.JTextField especulador;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JTextField nombre;
    private javax.swing.JPanel propiedades;
    private javax.swing.JTextField saldo;
    // End of variables declaration//GEN-END:variables

}
