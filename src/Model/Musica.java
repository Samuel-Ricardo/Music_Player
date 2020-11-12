/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Samuel
 */
public class Musica {
    
   private int id; 
   private String nome;
   private String autor;
   private boolean tocando = false;

    public Musica(String nome) {
        this.nome = nome;
    }
    
     public Musica(String nome, boolean tocando) {
        this.nome = nome;
        this.tocando = tocando;
    }
    
    public Musica(){
        
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean isTocando() {
        return tocando;
    }

    public void setTocando(boolean tocando) {
        this.tocando = tocando;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    
    
    @Override
    public String toString(){
        
        return getNome();
    }
    
    
    
}
