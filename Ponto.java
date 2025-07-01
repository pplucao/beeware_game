package Aula;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Ponto extends JPanel implements KeyListener, Runnable{
    protected int x;
    protected int y;
    private boolean cima, baixo, esquerda, direita;
    protected boolean pausado = false;
    protected boolean start = false;
    protected float velocidade;

    public Ponto(int x, int y, float velocidade) {
        this.x = x;
        this.y = y;
        this.velocidade = velocidade;
    }

    public Ponto (int x, int y) {
        this.x = x;
        this.y = y;
    }

    void atualizarPosicao() {
        if(!pausado && start) { //só move se o jogo estiver ativo e nao estiver pausado
            if (cima) y -= velocidade;
            if (baixo) y += velocidade;
            if (esquerda) x -= velocidade;
            if (direita) x += velocidade;
            
            // Limita aos bordas do painel
            x = Math.max(0, Math.min(Tela.largura - 20, x));
            y = Math.max(0, Math.min(Tela.altura - 20, y));
        }
    }

    public void resetarControles() {
    this.cima = false;
    this.baixo = false;
    this.esquerda = false;
    this.direita = false;
    this.pausado = false;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            pausado = !pausado; // Alterna entre pausado/despausado
            return;
        }

        // Se estiver pausado, ignora outros comandos
        if (pausado) return;

        if(e.getKeyCode() == KeyEvent.VK_ENTER && !start) {
            start = true;
            pausado = false;
            repaint();
        }

        if(!pausado && start) {
            switch (e.getKeyCode()) { //Só processa movimento se o jogo estiver ativo
                case KeyEvent.VK_W: cima = true; break;
                case KeyEvent.VK_S: baixo = true; break;
                case KeyEvent.VK_A: esquerda = true; break;
                case KeyEvent.VK_D: direita = true; break;
            }
        }
    }

    public boolean isPausado() {
        return pausado;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W: cima = false; break;
            case KeyEvent.VK_S: baixo = false; break;
            case KeyEvent.VK_A: esquerda = false; break;
            case KeyEvent.VK_D: direita = false; break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void run() {}
}
