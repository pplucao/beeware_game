package Aula;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.List;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import javax.sound.sampled.*;

import javax.swing.Timer;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Tela extends JPanel{

    public static final int largura = 800;  // Tamanho fixo definido aqui
    public static final int altura = 600;
    private Timer timer;
    private Jogador jogador; 
    private Objetivo objetivo;
    private Fase faseAtual;
    private Image imagemFundo;
    private ImageIcon gifStart;
    private Clip musicaFundo;

    public Tela() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        setPreferredSize(new Dimension(largura, altura)); // Define o tamanho do painel para 800x600

        carregarMusica();
        
        //Define nascimento dos elementos
        this.faseAtual = new Fase(1); 
        this.jogador = new Jogador(80, 80);
        this.objetivo = new Objetivo(largura, altura);

        setFocusable(true);
        addKeyListener(jogador); //pois jogador extende de ponto que tem keylistener

        //Carrega gif do inicio
        try {
            gifStart = new ImageIcon(getClass().getResource("/gif/beeMenu.gif"));
        } catch (Exception e) {
            System.out.println("Erro ao carregar gif");
            e.printStackTrace();
        }

        // Carrega a imagem de fundo
        try {
            imagemFundo = ImageIO.read(getClass().getResource("/img/flores.jpeg"));
        } catch (Exception e) {
            System.out.println("Imagem não carregada: " + e.getMessage());
            imagemFundo = null;
        }

        timer = new Timer(16, e -> {
            if (!jogador.isPausado() && jogador.start) { // Só atualiza se não estiver pausado e o jogo começado
                jogador.atualizarPosicao();
                jogador.atualizarAnimacao();
                faseAtual.getInimigos().forEach(inimigo -> inimigo.moverAleatoriamente(largura, altura));
            }

            // Verifica colisão com o objetivo
            if (jogador.getBounds().intersects(objetivo.getBounds())) {
                proximaFase();
            }

            //Verifica colisão com os inimigos e reinicia jogo
            for (Inimigo inimigo : faseAtual.getInimigos()) {
                if(jogador.getBounds().intersects(inimigo.getBounds())) {
                    timer.stop();

                    jogador.resetarControles();
                    jogador.setPosicao(50, 50);
                    jogador.setVelocidade(2);

                    faseAtual.reiniciar();
                    timer.start();
                }
            }
            repaint();
        });
        timer.start();
    }

    private void carregarMusica() throws UnsupportedAudioFileException, IOException, LineUnavailableException {

        InputStream audioStream = getClass().getResourceAsStream("/music/musica.wav"); //chama diretório da música

        if(audioStream != null) {
            // Carrega o arquivo de áudio
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(audioStream);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInput);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } else {
            System.err.println("Arquivo de música não encontrado!");
        }
    }

    private void proximaFase(){
        faseAtual.aumentarVelocidade(jogador);
        faseAtual = new Fase(faseAtual.getFase() + 1);
        jogador.setPosicao(50, 50); //reseta posição do jogador
        objetivo.reposicionar(largura, altura);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        //desenha gif do inicio apenas se tiver um gif e se nao tiver começado jogo
        if(!jogador.start && gifStart != null)  { 
            gifStart.paintIcon(this, g, 0, 0);
            return;
        }

        // desenha o fundo proporcional
        if (imagemFundo != null) {
            g.drawImage(imagemFundo, 0, 0, getWidth(), getHeight(), this);
        } else {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, largura, altura);
        }

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 24));
        g.drawString("Fase: " + faseAtual.getFase(), 20, 30);

        //2. desenha elementos
        faseAtual.getInimigos().forEach(inimigo -> inimigo.desenhar(g));
        jogador.desenhar(g);
        objetivo.desenhar(g);

        // Mostra mensagem de pausa
        if (jogador.isPausado()) {

            g.setColor(new Color(255, 255, 255, 200));  // Branco semi-transparente (cor fundo)
            g.fillRect(0, 0, getWidth(), getHeight());  // Fundo

            g.setColor(Color.RED); //cor letra
            g.setFont(new Font("Arial", Font.BOLD, 30));
            g.drawString("PAUSADO", getWidth()/2 - 100, getHeight()/2 - 20);

            g.setFont(new Font("Arial", Font.PLAIN, 24));
            g.drawString("[ESPAÇO] PARA DESPAUSAR", getWidth()/2 - 170, getHeight()/2 + 30);

        }
         
    }

    public void atualizar () {
        jogador.atualizarPosicao();
        repaint();
    }

}
