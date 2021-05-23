/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import civitas.CivitasJuego;
import java.util.ArrayList;

/**
 *
 * @author adri
 */
public class TestP5 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        CivitasView vista = new CivitasView();
        Dado.createInstance(vista);
        
        CapturaNombres cap=new CapturaNombres(vista, true);
        ArrayList<String> nombres=cap.getNombres();
        
        CivitasJuego juego=new CivitasJuego(nombres);
        Controlador ctrl=new Controlador(juego, vista);
        
        vista.setCivitasJuego(juego);
        ctrl.juega();
    }
    
}
