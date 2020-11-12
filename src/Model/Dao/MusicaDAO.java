/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Dao;

import Model.Musica;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Samuel
 */
public class MusicaDAO {

    private Musica model;
    private final File destino;
    private final FileNameExtensionFilter filtro;
    private ArrayList<Musica> musicas;
    private File[] arquivos;
    

    public MusicaDAO(Musica model) {

        this.model = model;
        this.destino = new File(System.getProperty("user.home") + "/desktop/Musicas_Mp3");
        this.filtro = new FileNameExtensionFilter("audios", "Mp3");
        this.musicas = new ArrayList<>();
        this.arquivos = destino.listFiles();
        
    }

    public int quantidadeDeMusica() {
        File[] arquivos = destino.listFiles();
        int cont = 0;

        for (File arq : arquivos) {

            cont++;

        }
        System.out.println(cont);

        return cont;
    }

    public void addMusica(File arquivo) {
        /*
        JFileChooser escolhido = new JFileChooser();

        escolhido.setDialogTitle("Procurar Musicas -,-");
        escolhido.setFileFilter(filtro);

        int retorno = escolhido.showOpenDialog(null);

        if (retorno == escolhido.APPROVE_OPTION) {

            File arquivo = escolhido.getSelectedFile();
         */

        boolean sucesso = arquivo.renameTo(new File(destino, arquivo.getName()));

        if (sucesso) {

            JOptionPane.showMessageDialog(null, "O arquivo: (" + arquivo.getName() + ") foi movido para Pasta (" + destino.getName() + ") com sucesso");
      
            JOptionPane.showMessageDialog(null, "Reinicie o Player para poder escutar a musica nova >.-");
        } else {

            JOptionPane.showMessageDialog(null, "Erro ao mover o Arquivo: (" + arquivo.getName() + ") para Pasta :" + destino.getName());

        }

        iniciar();
    }

    public ArrayList<Musica> getMusicasFromFiles() {

        int cont = 0;

        for (File arq : arquivos) {

            Musica musica = new Musica();

            musica.setId(cont);
            musica.setNome(arquivos[cont].getName());

            musicas.add(musica);

            cont++;
        }

        return musicas;
    }
    
    
   
    public void setMusicaTocando(Musica musica, boolean tocando) {

        int cont = 0;
        int index = 0;
        String setada="";
        
        for (Musica music : musicas) {
        

            if (musica.getNome() == musicas.get(cont).getNome()) {
                musicas.get(cont).setTocando(tocando);
                index = cont;
                setada = musicas.get(cont).getNome();
            }
            cont++;

        }
        System.out.println("");
        System.out.println("----------------------------------");
        System.out.println("tocando? :" + musicas.get(index).isTocando());
        System.out.println("Musica setada" + setada);
        System.out.println(tocando);
        System.out.println("----------------------------------");
        System.out.println("");

    }

    public void iniciar(/*ArrayList<Musica> musicas2*/) {
  
        musicas = getMusicasFromFiles();
        
        /*int cont = 0;
        
        for(Musica musica : musicas2){
            
            Musica music = new Musica();
            
            music.setNome( musicas2.get(cont).getNome());
            music.setId( musicas2.get(cont).getId());
            music.setTocando(musicas2.get(cont).isTocando());
            music.setAutor(musicas2.get(cont).getAutor());
            
            musicas.add(music);
            cont++;
        }*/
    
    }
    
    /* public Musica getMusicaTocando() {
    
    int cont = 0;
    int index = 98687576;
    String setada="";
    Musica musicaTocando = new Musica();
    
    for (Musica music : musicas) {
    
    
    if (musicas.get(cont).isTocando()==true) {
    
    musicaTocando.setTocando(musicas.get(cont).isTocando());
    musicaTocando.setNome(musicas.get(cont).getNome());
    musicaTocando.setId(musicas.get(cont).getId());
    index = cont;
    setada = musicas.get(cont).getNome();
    }
    cont++;
    
    }
    System.out.println("");
    System.out.println("----------------------------------");
    System.out.println("tocando? :" + musicas.get(index).isTocando());
    System.out.println("Musica getada :" + setada);
    System.out.println("pao   "+musicaTocando.getNome());
    System.out.println("----------------------------------");
    System.out.println("");
    
    return musicaTocando;
    }*/


    public Musica getMusicaTocando() {
    
    int cont = 0;
    Musica musicaTocando = new Musica();
    String tocando = " nada";
    int index =664535;
    
    
    
    for(Musica music : musicas){
    
    System.out.println("HAMBURQUER");
    System.out.println("array: "+ musicas.get(cont).isTocando());
    
    if (musicas.get(cont).isTocando() == true) {
    
    musicaTocando.setId(musicas.get(cont).getId());
    musicaTocando.setNome(musicas.get(cont).getNome());
    musicaTocando.setTocando(musicas.get(cont).isTocando());
    tocando = musicaTocando.getNome();
    index = cont;
    }
    
    System.out.println(cont);
    
    cont++;
    
    }
    
    System.out.println("");
    System.out.println("----------------------------------");
    System.out.println("tocando? :" + musicaTocando.isTocando());
    System.out.println("Musica getada :" + musicaTocando.getNome());
    System.out.println("");
    System.out.println("----------------------------------");
    System.out.println("");
    
    
    return musicaTocando;
    }

    
    public ArrayList<Musica> getMusicas() {
        return musicas;
    }

    public void setMusicas(ArrayList<Musica> musicas) {
        this.musicas = musicas;
    }

    public Musica getModel() {
        return model;
    }

    public void setModel(Musica model) {
        this.model = model;
    }

    public File[] getArquivos() {
        return arquivos;
    }

    public void setArquivos(File[] arquivos) {
        this.arquivos = arquivos;
    }

    public File getDestino() {
        return destino;
    }

    public FileNameExtensionFilter getFiltro() {
        return filtro;
    }

    public Musica getMusica(Musica musicaSe) {
    
        
     //    ArrayList<Musica> musicas = dao.getMusicas();
     int cont = 0;
     Musica musicaSelected = new Musica();
       
     for(Musica music: musicas){
                
         /*System.out.println("ARRAY: "+musicas.get(cont).getNome());
         System.out.println("musica: "+musicaSe.getNome());*/
         
            if(musicas.get(cont).getNome().equals(musicaSe.getNome())){
                
                System.out.println("______________________________----------------__________"+cont);
             musicaSelected.setId(musicas.get(cont).getId());
             musicaSelected.setNome(musicas.get(cont).getNome());
             musicaSelected.setTocando(musicas.get(cont).isTocando());
             
         }
         
         cont++;
     }
        
         System.out.println("c_____________"+musicaSelected.getNome());
    return musicaSelected;
    }
    
    

}
