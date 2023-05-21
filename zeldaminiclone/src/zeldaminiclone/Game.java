package zeldaminiclone;

//IMPORTA AS BIBLIOTECAS
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

//EXTENDE A CLASSE GAME PARA A CLASSE CANVAS E IMPLEMENTA A CLASSE RUNNABLE
public class Game extends Canvas implements Runnable, KeyListener{
	//LARGURA E ALTURA DA TELA
	public static int WIDTH = 640, HEIGHT = 480;
	public static int SCALE = 3;
	public static Player player;
	public World world;
	
	public List<Inimigo> inimigos = new ArrayList<Inimigo>();
	
	//CLASSE GAME
	public Game() {
		this.addKeyListener(this);
		//SETA A PREFERENCIA DA TELA (LARGURA, ALTURA)
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		new Spritesheet();
		//INSTANCIA DE UM NOVO PLAYER
		player = new Player(64, 64);
		world = new World();
		
		inimigos.add(new Inimigo(32, 32));
	}
	
	//PARTE LOGICA
	public void tick() {
		player.tick();
		
		for(int i = 0; i < inimigos.size(); i++) {
			inimigos.get(i).tick();
		}
	}
	
	//RENDERIZACAO DOS GRAFICOS
	public void render() {
		//RECEBE UM BUFFER STRATEGY SE CASO JA EXISTA
		BufferStrategy bs = this.getBufferStrategy();
		//VERIFICA SE TEM UM BUFFER STRATEGY SE CASO NAO CRIA UM NOVO
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		//RECEBE O BUFFER STRATEGY PARA DESENHAR OS GRAFICOS NELE
		Graphics g = bs.getDrawGraphics();
		//CRIA UM FUNDO PRETO
		g.setColor(new Color(0, 135, 13));
		g.fillRect(0, 0, WIDTH*SCALE, HEIGHT*SCALE);
		//DESENHA O PLAYER
		player.render(g);
		for(int i = 0; i < inimigos.size(); i++) {
			inimigos.get(i).render(g);
		}
		world.render(g);
		
		//RENDERIZA TUDO
		bs.show();
	}
	
	//MAIN
	public static void main(String[] args) {
		//INSTANCIA DA CLASSE GAME
		Game game = new Game();
		//INSTANCIA DA CLASSE JFRAME
		JFrame frame = new JFrame();
		
		//ADICIONA O CANVA/CLASSE GAME A JANELA
		frame.add(game);
		//SETA O TITULO DA JANELA
		frame.setTitle("Mini Zelda");
		//CHAMA E CONFIGURA A JANELA DE ACORDO COM AS PREFERENCIAS
		frame.pack();
		//CENTRALIZA A JANELA
		frame.setLocationRelativeTo(null);
		//DETERMINA A FECHADA DE TODAS AS OPERACOES DO PROGRAMA
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//TORNA A JANELA VISIVEL
		frame.setVisible(true);
		//CRIA UM NOVO PROCESSO INICIANDO A APLICACAO E O LOOPING
		new Thread(game).start();
	}
	
	//AQUI ONDE O GAME ACONTECE
	@Override
	public void run() {
		//LOOP DE RENDERIZAÇÃO
		while(true) {
			tick();
			render();
			//DEFINE OS FRAMES POR SEGUNDO DO GAME (60FPS)
			try {
				Thread.sleep(1000/60);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	//EVENTO DE TECLA
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	//EVENTO DE TECLAS QUANDO PRESSIONADA
	@Override
	public void keyPressed(KeyEvent e) {
		
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			player.right = true;
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			player.left = true;
		}
		
		if (e.getKeyCode() == KeyEvent.VK_Z) {
			player.shoot = true;
		}
		
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			player.up = true;
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			player.down = true;
		}
		
	}
	
	//EVENTO DE TECLAS QUANDO SOLTAS
	@Override
	public void keyReleased(KeyEvent e) {
		
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			player.right = false;
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			player.left = false;
		}
		
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			player.up = false;
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			player.down = false;
		}
		
	}
}
