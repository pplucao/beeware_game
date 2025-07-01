package Aula;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.List;
import java.awt.Rectangle;
import java.util.Random;

import javax.swing.ImageIcon;

public class Inimigo extends Ponto{ //herda x, y e velocidade
    private Color cor;
    private int tempoDirecao = 0;
    private int intervalo_mudanca = 30; //frequencia da mudança de direção
    private int dx = 0;
    private int dy = 0;
    private ImageIcon inimigoGif;
    Random rand = new Random(); //objeto que cria numeros aleatorios

    public Inimigo (int x, int y){
        super(x,y, 2);
        //this.cor = Color.WHITE;


        try {
            inimigoGif = new ImageIcon(getClass().getResource("../gif/bee.gif"));
            // Redimensiona mantendo a animação
            inimigoGif = new ImageIcon(inimigoGif.getImage().getScaledInstance(
                80, 
                80, 
                Image.SCALE_DEFAULT
            )); 
        } catch (Exception e) {
            System.out.println("Gif não carregado");
            inimigoGif = null;
        }

        // Define uma direção inicial
        this.dx = rand.nextInt(3) - 1;
        this.dy = rand.nextInt(3) - 1;
    }
    
    public void desenhar(Graphics g) {
        if(inimigoGif != null) {
            inimigoGif.paintIcon(null, g, x, y);
        }
    }

    public void moverAleatoriamente (int larguraTela, int alturaTela) {

        if (tempoDirecao <= 0) { //só muda de direção quando tempo de direção for = 0
            this.dx = rand.nextInt(3) - 1; // gera novas direções aleatórias
            this.dy = rand.nextInt(3) - 1; 
            tempoDirecao = intervalo_mudanca; //reinicia o contador
        }
        tempoDirecao--;

        x += dx * (velocidade);  //se velocidade = 5 e dx = 1, o objeto move 5 pixels para direita
        y += dy * (velocidade);

        // Mantém dentro da tela
        x = Math.max(0, Math.min(x, larguraTela - 80)); // X entre 0 e larguraTela - 80
        y = Math.max(0, Math.min(y, alturaTela - 80));
    }

    public Rectangle getBounds() { //colisão
    return new Rectangle(x, y, 60, 60);
    }

    public float getVelocidade() {
        return velocidade;
    }

    public void setVelocidade(float velocidade) {
        this.velocidade = velocidade;
    }
}
