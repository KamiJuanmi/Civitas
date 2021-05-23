
package GUI;

import civitas.CivitasJuego;
import civitas.Jugador;
import civitas.OperacionesJuego;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

public class CivitasView extends javax.swing.JFrame {
    
    private CivitasJuego juego;
    private JugadorPanel jugadorPanel;
    private GestionarDialog gestionarD;
    
    public CivitasView() {
        initComponents();
        
        this.jugadorPanel= new JugadorPanel();
        infoJugador.add(jugadorPanel);
        
        this.gestionarD = new GestionarDialog(this);
        this.gestionarD.setVisible(false);
        
        repaint();
        revalidate();
    }
    
    public void setCivitasJuego(CivitasJuego juego)
    {
        this.juego=juego;
        this.setVisible(true);
    }
    
    public void gestionar()
    {        
        this.gestionarD.gestionar(juego.getJugadorActual());
        
        this.gestionarD.pack();
        this.gestionarD.repaint();
        this.gestionarD.revalidate();
        
        this.gestionarD.setVisible(true);
    }
    
    public int salirCarcel()
    {
        String[] opciones = {"Pagando", "Tirando"};
        
        int respuesta = JOptionPane.showOptionDialog(null, "¿Como quieres salir de la carcel?", "Salir de la carcel", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);
        
        return respuesta;
    }
    
    public void actualizaVista()
    {
        jScrollPane2.setVisible(false);
        label_ranking.setVisible(false);
        this.jugadorPanel.setJugador(juego.getJugadorActual());
        casilla.setText(juego.getCasillaActual().toString());
        if(juego.finalDelJuego())
        {
            String rnk="";
            jScrollPane2.setVisible(true);
            for(Jugador j : juego.ranking()){
                rnk+=j.toString();
            }
            Ranking.setText(rnk);
            label_ranking.setVisible(true);
            repaint();
            revalidate();
        }
    }
    
    public int getGestion(){
        return this.gestionarD.getGestion();
    }
    
    public int getPropiedad(){
        
        return this.gestionarD.getPropiedad();
    }
    
    public void mostrarSiguienteOperacion(OperacionesJuego siguiente){
        
        siguienteOperacion.setText(siguiente.toString());
        actualizaVista();
        
    }
    
    public void mostrarEventos(){
        DiarioDialog diarioD= new DiarioDialog(this); 
    }
    
    public Respuestas comprar(){
        int opcion= JOptionPane.showConfirmDialog(null,"¿Quieres comprar la calle actual?","Comprar",JOptionPane.YES_NO_OPTION);
        if(opcion==1)
        {
            return Respuestas.NO;
        }else
        {
            return Respuestas.SI;
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        infoJugador = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        casilla = new javax.swing.JTextArea();
        jLabel3 = new javax.swing.JLabel();
        siguienteOperacion = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        label_ranking = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        Ranking = new javax.swing.JTextArea();

        jLabel2.setText("jLabel2");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Civitas");
        jLabel1.setEnabled(false);

        infoJugador.setName("InfoJugador"); // NOI18N

        jLabel4.setText("Jugador");
        infoJugador.add(jLabel4);

        casilla.setColumns(20);
        casilla.setRows(5);
        casilla.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        casilla.setEnabled(false);
        jScrollPane1.setViewportView(casilla);

        jLabel3.setText("Casilla");

        siguienteOperacion.setText("jTextField1");
        siguienteOperacion.setDisabledTextColor(new java.awt.Color(1, 1, 1));
        siguienteOperacion.setEnabled(false);

        jLabel5.setText("Siguiente Operacion");

        label_ranking.setText("Ranking");

        Ranking.setColumns(20);
        Ranking.setRows(5);
        Ranking.setEnabled(false);
        jScrollPane2.setViewportView(Ranking);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1)
                            .addComponent(infoJugador, javax.swing.GroupLayout.PREFERRED_SIZE, 469, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(136, 136, 136)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 314, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(174, 174, 174)
                        .addComponent(jLabel3))
                    .addComponent(siguienteOperacion, javax.swing.GroupLayout.PREFERRED_SIZE, 389, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 389, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(89, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(256, 256, 256)
                .addComponent(label_ranking)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addGap(227, 227, 227))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1))
                    .addComponent(infoJugador, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(label_ranking)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(siguienteOperacion, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

   
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CivitasView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CivitasView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CivitasView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CivitasView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CivitasView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea Ranking;
    private javax.swing.JTextArea casilla;
    private javax.swing.JPanel infoJugador;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel label_ranking;
    private javax.swing.JTextField siguienteOperacion;
    // End of variables declaration//GEN-END:variables
}
