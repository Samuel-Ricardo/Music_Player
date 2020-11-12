/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Helpers;

import Model.Musica;
import View.TelaInicial;
import java.awt.Color;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author Samuel
 */
public class TelaInicialHelper {

    private final TelaInicial view;
    
    public TelaInicialHelper(TelaInicial view) {
       
        this.view = view;
    }

    public void setMusicaInical(Musica musica) {
    
        view.getjLabelMusicaAtual().setText(musica.getNome());
    
    }

    public void limparComboBox() {
  
        view.getjComboBoxMusicas().removeAllItems();
    
    }

    public void setTempoMusica(double seg, double tempoTotal) throws ParseException{
    
        
       
        
        SimpleDateFormat tempoFormatado = new SimpleDateFormat("mm:ss");
      //  SimpleDateFormat segundos = new SimpleDateFormat("ss");
                
        String temp = 0+":"+seg;
        String tempTot = 0+":"+tempoTotal;
     //   String segs = ""+seg;
        
        Date tempo = tempoFormatado.parse(temp);
        Date tempoTot = tempoFormatado.parse(tempTot);
       // Date segun = segundos.parse(segs);
        
        
       // System.out.println(tempoFormatado.format(tempo));
     //   String progresso = segundos.format(segun);//+" / "+tempoFormatado.format(tempoTot);
        String temporizador = tempoFormatado.format(tempo)+" / "+tempoFormatado.format(tempoTot);
         
        
      //  int prog = Integer.parseInt(progresso);
        int segAtual = (int) seg;
        
     //   String atual = ""+seg;
        int totT = (int) tempoTotal;
        
       view.getjProgressBarVideoTempo().setBorderPainted(true);
        
      // view.getjProgressBarVideoTempo().setForeground(Color.black);
      // view.getjProgressBarVideoTempo().setBackground(Color.red);
       view.getjProgressBarVideoTempo().setString(temporizador);
      
       view.getjProgressBarVideoTempo().setMaximum(totT);
       view.getjProgressBarVideoTempo().setValue(segAtual);
       
       
        view.getjLabelTempoMusica().setText(tempoFormatado.format(tempo)+" / "+tempoFormatado.format(tempoTot));
    
        
        
    }

    public String getSelectedMusic() {
   
        DefaultComboBoxModel combo = (DefaultComboBoxModel) view.getjComboBoxMusicas().getModel();
        
        String s = combo.getSelectedItem().toString();
       // System.out.println("S__________"+combo.getSelectedItem().toString());
                
        
        return s;
    }
    
    
}
