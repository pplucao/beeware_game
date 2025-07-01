package Aula;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Objetivo extends Ponto {

    private Image portal;
    private ImageIcon portalGif;
    private Random rand = new Random();

    
    public Objetivo(int largura, int altura) {
        super(0, 0);
        
        //vai pegar os valores da classe tela (que é o tamanho da janela)
        reposicionar(largura, altura); 

        try {
            portalGif = new ImageIcon(getClass().getResource("../gif/portal4.gif"));
            // Redimensiona mantendo a animação
            portalGif = new ImageIcon(portalGif.getImage().getScaledInstance(
                50, 
                50, 
                Image.SCALE_DEFAULT
            )); 
        } catch (Exception e) {
            System.out.println("Gif não carregado");
            portalGif = null;
        }
    }

    public void reposicionar(int largura, int altura) {
        // Gera novas coordenadas aleatórias, considerando o tamanho do portal
        this.x = rand.nextInt(largura - 70); // gera valor entre 0 e 740 (800 de largura)
        this.y = rand.nextInt(altura - 70); // gera valor entre 0 e 540 (600 de altura)
    }

    public void desenhar(Graphics g) {
        // Desenha a imagem do portal se ela foi carregada
        /*if (portal != null) {
            g.drawImage(portal, x, y, 60, 60, null);
        } else {
            // Fallback: desenha um retângulo verde se a imagem não carregar
            g.setColor(Color.GREEN);
            g.fillRect(x, y, 30, 30);
        }*/

        if(portalGif != null) {
            portalGif.paintIcon(null, g, x, y);
        }
    }

    public Rectangle getBounds() {
    return new Rectangle(x, y, 50, 50);
    }

    
}
