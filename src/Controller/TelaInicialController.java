/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Controller.Helpers.TelaInicialHelper;
import Model.Dao.MusicaDAO;
import Model.Musica;
import View.TelaInicial;
//import View.TelaMusica;
import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Samuel
 */
public class TelaInicialController {

    private final TelaInicial view;
    private final File destino;
    private final FileNameExtensionFilter filtro;
    private final TelaInicialHelper helper;
    private final MusicaDAO dao;
    private final Musica model;
    private Tocar tocar;
    private final File diretorio;
    //private final TelaMusica view2;

    public TelaInicialController(TelaInicial view) throws FileNotFoundException {

        this.view = view;
        this.helper = new TelaInicialHelper(view);
        this.destino = new File(System.getProperty("user.home") + "/desktop/Musicas_Mp3");
        this.filtro = new FileNameExtensionFilter("audios", "mp3");
        this.model = new Musica();
        this.dao = new MusicaDAO(model);
        this.tocar = new Tocar();
        // this.view2= new TelaMusica();
        this.diretorio = new File(System.getProperty("user.home") + "/desktop/Musicas_Mp3");

    }

    public void criarDiretorio() {

        if (diretorio.exists()) {
          
        } else {
            diretorio.mkdir();
            JOptionPane.showMessageDialog(null, "Uma pasta para suas musicas foi criada na sua area de trabalho  \n"
                    + "Nome : Musicas_Mp3");
        }
    }

    public void acessarYouTube() {

        try {

            String busca = JOptionPane.showInputDialog(null, "Qual video deseja acessar?", "Digite Aqui...");
            String buscar = busca.replaceAll(" ", "+");

            URI link = new URI("https://www.youtube.com/results?search_query=" + buscar);

            Desktop.getDesktop().browse(link);

        } catch (URISyntaxException | IOException ex) {
            JOptionPane.showMessageDialog(null, "Nao foi possivel acessar o YouTube: " + ex);
        }

    }

    public void acessarDownload() {

        try {

            URI link = new URI("https://ytmp3.cc/en13/");

            Desktop.getDesktop().browse(link);

        } catch (URISyntaxException | IOException ex) {
            JOptionPane.showMessageDialog(null, "Nao foi possivel acessar o Downloader: " + ex);
        }
    }

    public void iniciar() {

        if (diretorio.exists()) {

            if (vazio()) {

                JOptionPane.showMessageDialog(null, "A Pasta "+diretorio.getName()+" está vazias faça o Download De alguma Musica no app");

            
        } else {

            iniciarCombobox();
            dao.iniciar();
            musicaInicial();

        }
        }     
    }

    public void iniciarCombobox() {

        helper.limparComboBox();

        File[] arquivos = destino.listFiles();
        ArrayList<Musica> musicas = new ArrayList<>();

        int cont = 0;
        DefaultComboBoxModel comboBox = (DefaultComboBoxModel) view.getjComboBoxMusicas().getModel();

        for (File arq : arquivos) {

            // System.out.println(arquivos[cont].getName());

            /* musicas.get(cont).setNome(arquivos[cont].getName());
            musicas.get(cont).setId(cont);*/
            Musica musica = new Musica(arquivos[cont].getName());
            musica.setId(cont);

            musicas.add(musica);

            comboBox.addElement(musicas.get(cont));

            // System.out.println(musicas.get(cont).getId() + " / " + musicas.get(cont).getNome());
            cont++;
        }

    }


    /*public int quantidadeDeMusica() {
    File[] arquivos = destino.listFiles();
    int cont = 0;
    
    for (File arq : arquivos) {
    
    cont++;
    
    }
    System.out.println(cont);
    
    return cont;
    }*/
    public ArrayList<Musica> getMusicasFromFiles() {

        /* 
        int cont = 0;
        ArrayList<Musica> musicas = new ArrayList<>();
        File[] arquivos = destino.listFiles();

        for (File arq : arquivos) {

            Musica musica = new Musica();

            musica.setId(cont);
            musica.setNome(arquivos[cont].getName());

            musicas.add(musica);

            cont++;
        }

        return musicas;
         */
        return dao.getMusicasFromFiles();
    }

    public void addMusica() {

        JFileChooser escolhido = new JFileChooser();

        escolhido.setDialogTitle("Procurar Musicas >.+");
        escolhido.setFileFilter(filtro);

        int retorno = escolhido.showOpenDialog(null);

        if (retorno == escolhido.APPROVE_OPTION) {

            File arquivo = escolhido.getSelectedFile();

            dao.addMusica(arquivo);

            /*
            boolean sucesso = arquivo.renameTo(new File(destino, arquivo.getName()));

            if (sucesso) {

                JOptionPane.showMessageDialog(null, "O arquivo: (" + arquivo.getName() + ") foi movido para Pasta (" + destino.getName() + ") com sucesso");
            } else {

                JOptionPane.showMessageDialog(null, "Erro ao mover o Arquivo: (" + arquivo.getName() + ") para Pasta :" + destino.getName());

            }*/
        }

        //  iniciarCombobox();
    }

    public void playMusicaInicial(Musica musica) {

        tocar.musica = musica;

        if (tocar.musica.isTocando() == true) {

            tocar.interrupt();

        }
        helper.setMusicaInical(musica);
        tocar.start();
        tocar.setPriority(Thread.MAX_PRIORITY);

    }

    public void musicaInicial() {
        // iniciarCombobox();

        Random r = new Random();

        int cont = 0;

        int rand = dao.quantidadeDeMusica();
        int musicaInicial = r.nextInt(rand);

        Musica musicaInicia = new Musica();

        ArrayList<Musica> musicas = dao.getMusicasFromFiles();

        // System.out.println(musicaInicial + "  cachorro");
        musicaInicia.setNome(musicas.get(musicaInicial).getNome());
        musicaInicia.setId(musicas.get(musicaInicial).getId());

        playMusicaInicial(musicaInicia);
    }

    public void pauser() {

        Musica musica = dao.getMusicaTocando();
        // System.out.println("-----------------------------------------------------");
        // System.out.println(musica.getNome() + "   teste    " + musica.isTocando());
        // System.out.println("-----------------------------------------------------");
        if (musica.isTocando()) {

            dao.setMusicaTocando(musica, false);
            pausar(musica);

        } else {
            dao.setMusicaTocando(musica, true);
            retomar(musica);
        }

        // JOptionPane.showMessageDialog(null, "Nenhuma Musica Tocando");
    }

    public Musica getMusicaTocando() {

        /*ArrayList<Musica> musicas = getMusicas2();
        
        int cont = 0;
        Musica musicaTocando = new Musica();

        for (Musica music : musicas) {

            if (musicas.get(cont).isTocando() == true) {

                musicaTocando.setNome(musicas.get(cont).getNome());
                musicaTocando.setId(cont);
            }
        }

        return musicaTocando;
         */
        return dao.getMusicaTocando();
    }

    private void pausar(Musica musica) {

        /* synchronized(tocar){
        try {
        tocar.pause = true;
        tocar.wait();
        // tocar.musicaP.close();
        tocar.pausado = tocar.musicaP.getPosition();
        dao.setMusicaTocando(musica, false);
        } catch (InterruptedException ex) {
        Logger.getLogger(TelaInicialController.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
         */
        tocar.pausar();
    }

    private void retomar(Musica musica) {

        /*     synchronized(tocar){
        //  try {
        tocar.pause = true;
        tocar.start();
        System.out.println("pao");
        dao.setMusicaTocando(musica, true);
        /*} catch (JavaLayerException ex) {
        System.out.println(ex);
        }*/
        tocar.resumir();

        //}         
    }

    public void TocarSelectedMusica() {

        Musica musicaSe = new Musica();

        musicaSe.setNome(helper.getSelectedMusic());
        //  System.out.println(musicaSe.getNome() + "_____________________:");
        play(musicaSe);
        //  System.out.println("musiaca");

        helper.setMusicaInical(musicaSe);

//        if (view2.isVisible() == false) {
//          view2.setVisible(true);
        //      }
    }

    private void play(Musica musicaSe) {

        /* if(tocar.status.troca == false){
            tocar.status.stopStatus();
            }else{
            tocar.status.retomarStatus();
            }*/
        Musica musicaSelected = dao.getMusica(musicaSe);

        //  System.out.println(musicaSelected.getNome() + "PAO");
        tocar.musicaPlayer.stop();
        //tocar.setMusica(musicaSelected);
        //  tocar.playMusic(tocar.preparar(musicaSelected));
        tocar.iniciarPlayer(musicaSelected);
        //tocar.musicaPlayer.play();
        //System.out.println("saindo");
    }

    public TelaInicial getView() {
        return view;
    }

    public File getDestino() {
        return destino;
    }

    public FileNameExtensionFilter getFiltro() {
        return filtro;
    }

    public TelaInicialHelper getHelper() {
        return helper;
    }

    public MusicaDAO getDao() {
        return dao;
    }

    public Musica getModel() {
        return model;
    }

    private boolean vazio() {

        File[] arquivos = diretorio.listFiles();

        if (arquivos.length == 0) {
            return true;
        } else {
            return false;
        }

    }

    public class Tocar extends Thread {

        StatusMusica status;
        Musica musica;
        InputStream input;
        Media media;
        MediaPlayer musicaPlayer;
        int pausado;
        boolean pause = false;
        String endereco;

        Duration duracaoTotal;
        Duration tempoAutal;

        public Tocar(Musica musica) throws FileNotFoundException {
            this.musica = musica;
            this.input = new FileInputStream(destino + "/" + musica.getNome());
            System.out.println(musica.getNome() + "     peixada");
            this.status = new StatusMusica(this);
            this.pausado = 0;
        }

        public Tocar() throws FileNotFoundException {

            this.status = new StatusMusica(this);
            this.pausado = 0;

//            this.input = new FileInputStream(destino + "/" + musica.getNome());
            //     this.musicaP = new Player(getInput());
        }

        public void run() {

            // while (pause == false)
            iniciarPlayer(musica);
            status.start();
            status.status();

            if (tempoAutal == duracaoTotal) {

                dao.setMusicaTocando(musica, false);
                musica.setTocando(false);
            }

            //}
        }

        public void retomarStatus() {
            status.notify();
        }

        public void pausar() {

            musicaPlayer.pause();
            dao.setMusicaTocando(musica, false);
            //tocar.pause = true;

            //synchronized(this){
            // tocar.musicaP.close();
            // tocar.musicaP.close();
            //  tocar.pausado = tocar.musicaP.getPosition();
            //  }
        }

        public void resumir() {

            dao.setMusicaTocando(musica, true);
            musicaPlayer.play();
            //tocar.pause = false;

//    synchronized(tocar){
            //try {
            //tocar.musicaP.play();
            /* System.out.println("pao");
        tocar.musicaP.play();
        System.out.println("pao");
       
       } catch (JavaLayerException ex) {
        System.out.println(ex);
        } */
        }
        //  }

        private void playMusic(String endereco1) {

            media = new Media(endereco1);
            //  System.out.println("UNKNOW______"+media.getDuration());
            //  System.out.println(endereco1 + "---------");

            musicaPlayer = new MediaPlayer(media);

            //this.musicaP = new Player(input);
        }

        private String preparar(Musica musicaA) {

            try {
                /*  synchronized(this){
                    
                    while (pausado == true){
                    
                    wait();
                    
                    }
                    }*/
                this.input = new FileInputStream(destino + "/" + musicaA.getNome());

            } catch (FileNotFoundException ex) {
                Logger.getLogger(TelaInicialController.class.getName()).log(Level.SEVERE, null, ex);
            }

            JFXPanel p = new JFXPanel();

            File arquivo = new File(destino + "/" + musicaA.getNome());
            String endereco = arquivo.toURI().toString();

            return endereco;
        }

        private void iniciarPlayer(Musica musicaSelected) {

            // preparar(); 
            this.musica = musicaSelected;

            playMusic(preparar(musicaSelected));
            //   System.out.println(endereco + "+_+_+_+_+_+_+_+_+_+");
            //if (pause == true){

            musicaPlayer.play();
            // System.out.println( musicaPlayer.getCycleDuration()+"()");

            musica.setTocando(true);
            dao.setMusicaTocando(musica, true);

            try {
                // musicaP.play();
                Thread.sleep(200);
            } catch (InterruptedException ex) {
                Logger.getLogger(TelaInicialController.class.getName()).log(Level.SEVERE, null, ex);
            }
            duracaoTotal = musicaPlayer.getTotalDuration();
            // System.out.println(musicaPlayer.getTotalDuration());

            // System.out.println("_____________________________"+duracaoTotal+"++++++++++++++++++++++++++++++++++++++++++++++++++++");
            tempoAutal = musicaPlayer.getCurrentTime();
            System.out.println(tempoAutal);

        }

        public void setStatus(StatusMusica status) {
            this.status = status;
        }

        public void setMusica(Musica musica) {
            this.musica = musica;
        }

        public void setInput(InputStream input) {
            this.input = input;
        }

        public Media getMedia() {
            return media;
        }

        public void setMedia(Media media) {
            this.media = media;
        }

        public MediaPlayer getMusicaPlayer() {
            return musicaPlayer;
        }

        public void setMusicaPlayer(MediaPlayer musicaPlayer) {
            this.musicaPlayer = musicaPlayer;
        }

        public int getPausado() {
            return pausado;
        }

        public void setPausado(int pausado) {
            this.pausado = pausado;
        }

        public boolean isPause() {
            return pause;
        }

        public void setPause(boolean pause) {
            this.pause = pause;
        }

        public Duration getDuracaoTotal() {
            return duracaoTotal;
        }

        public void setDuracaoTotal(Duration duracaoTotal) {
            this.duracaoTotal = duracaoTotal;
        }

        public Duration getTempoAutal() {
            return tempoAutal;
        }

        public void setTempoAutal(Duration tempoAutal) {
            this.tempoAutal = tempoAutal;
        }

        public Musica getMusica() {
            return musica;
        }

        public StatusMusica getStatus() {
            return status;
        }

        public InputStream getInput() {
            return input;
        }

    }

    public class StatusMusica extends Thread {

        Tocar tocar;
        //  boolean troca = false;

        public StatusMusica(Tocar tocar) {

            this.tocar = tocar;
        }

        public void run() {

        }

        public void status() {

            // System.out.println(troca+"    troquido");
            while (tocar.tempoAutal != tocar.duracaoTotal) {

                try {
                    sleep(999);
                } catch (InterruptedException ex) {
                    Logger.getLogger(TelaInicialController.class.getName()).log(Level.SEVERE, null, ex);
                }

                Duration temp = tocar.musicaPlayer.getCurrentTime();
                double seg = temp.toSeconds();
                double tempTot = tocar.duracaoTotal.toSeconds();
                // if (troca==false){
                /* int min = tocar.musicaP.getPosition() / 60000;
                int seg = tocar.musicaP.getPosition() / 1000;*/

                // System.out.println(seg);
                try {
                    //   try {
                    helper.setTempoMusica(seg, tempTot);

                    //  } catch (ParseException ex) {
                    //      Logger.getLogger(TelaInicialController.class.getName()).log(Level.SEVERE, null, ex);
                    //   }
                    /*
                    System.out.println(min + ":" + seg + "     peixe");
                    System.out.println(tocar.musicaP.isComplete());
                     */
                    // if (tocar.musicaP.isComplete() == true) {
                    //}
                } catch (ParseException ex) {
                    Logger.getLogger(TelaInicialController.class.getName()).log(Level.SEVERE, null, ex);
                }
                /* }else{
                break;
                }*/
            }

        }

        public void stopStatus() {

            try {
                //   troca = true;
                wait();

            } catch (InterruptedException ex) {
                Logger.getLogger(TelaInicialController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        /*        public void retomarStatus(){
        
        //     troca = false;
        }*/
    }

}
