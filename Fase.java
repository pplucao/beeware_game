package Aula;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class Fase {
    private int fase;
    private List<Inimigo> inimigos;
    private Random random;
    private boolean reiniciando = false;

    public Fase (int fase) {
        this.fase = fase;
        this.inimigos = new ArrayList<>();
        this.random = new Random();
        criarInimigos();
    }

    public void criarInimigos(){

        inimigos.clear();

        for(int i= 0; i < fase ; i++  ) {
            int x = random.nextInt(Tela.largura - 100); // Posição aleatória dentro da tela
            int y = random.nextInt(Tela.altura - 100);
            inimigos.add(new Inimigo(x, y));
        }
    }

    public void reiniciar () {
        if(reiniciando) return;

        reiniciando = true;
        inimigos.clear();
        fase = 1;
        criarInimigos();

        SwingUtilities.invokeLater(() -> {
        JOptionPane.showMessageDialog(
            null,
            "Você foi atingido por um inimigo!\nReiniciando fase...",
            "GAME OVER",
            JOptionPane.ERROR_MESSAGE
        );
        reiniciando = false;

        });
    }

    public void aumentarVelocidade(Jogador jogador){
        //Aumenta velocidade do jogador a cada fase
        jogador.setVelocidade(jogador.getVelocidade() + 0.2f);

        //Aumenta velocidade de todos inimigos a cada fase
        for (Inimigo inimigo : inimigos) {
            inimigo.setVelocidade(inimigo.getVelocidade() + 0.2f);
        }
    }

    public List<Inimigo> getInimigos () {
        return inimigos;
    }

    public int getFase(){
        return fase;
    }
}
