package Aula;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Jogador extends Ponto{
    private Color cor;
    private ImageIcon jogadorGif;
    private ArrayList<Image> spritesAndando;   
    private int frameAtual = 0;
    private int contador = 0;
    private int frameImagem = 10;

    public Jogador (int x, int y) {
        super(x,y, 2);
        carregarSprites();
    }

    private void carregarSprites(){
        spritesAndando = new ArrayList<>();

        for(int i =1 ; i<= 5; i++) {
            try { //os nomes estão consistentes e numerados, entao assim já carrega as 5 imagens
                Image img = new ImageIcon(getClass().getResource("/img/jogadormov" + i + ".png")).getImage();
                spritesAndando.add(img);
            } catch (Exception e) {
                System.out.println("JOgador não carregado!");
            }
        }
    }

    public void atualizarAnimacao() {
        contador ++;
        //quando contador chega em 5
        if(contador >= frameImagem) {
            frameAtual = (frameAtual + 1) % spritesAndando.size(); // Avança para o próximo frame (0→1→2→3→4→0...)
            contador = 0; //reseta contador
        }
    }

    public void desenhar(Graphics g) {
        if(!spritesAndando.isEmpty()&& spritesAndando.get(frameAtual) != null){
            g.drawImage(spritesAndando.get(frameAtual), x, y, 40, 40, null);
        }
    }

    public Rectangle getBounds() { //identificar colisao
    return new Rectangle(x, y, 40, 40);
    }

    public float getVelocidade() {
        return velocidade;
    }

    public void setVelocidade(float velocidade) {
        this.velocidade = velocidade;
    }

    public void setPosicao(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
