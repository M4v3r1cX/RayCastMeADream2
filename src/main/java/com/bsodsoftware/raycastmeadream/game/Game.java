package com.bsodsoftware.raycastmeadream.game;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.image.ImageObserver;
import java.util.List;
import javax.swing.JFrame;

import com.bsodsoftware.raycastmeadream.engine.Camera;
import com.bsodsoftware.raycastmeadream.engine.Maps;
import com.bsodsoftware.raycastmeadream.engine.Screen;
import com.bsodsoftware.raycastmeadream.engine.Texture;

public class Game extends JFrame implements Runnable {
   private static final long serialVersionUID = 1L;
   public int mapWidth = 15;
   public int mapHeight = 15;
   private Thread thread;
   private boolean running;
   private BufferedImage image;
   public int[] pixels;
   public static int[][] map = Maps.getE1M1();
   public List<Texture> textures;
   public Camera camera;
   public Screen screen;
   private int WINDOW_WIDTH = 1152;
   private int WINDOW_HEIGHT = 864;

   public Game() {
      this.setThread();
      this.setWindow();
      this.setEngine();
      this.start();
   }

   private void setThread() {
      this.thread = new Thread(this);
   }

   private void setWindow() {
      this.image = new BufferedImage(this.WINDOW_WIDTH, this.WINDOW_HEIGHT, 1);
      this.pixels = ((DataBufferInt)this.image.getRaster().getDataBuffer()).getData();
      this.setSize(this.WINDOW_WIDTH, this.WINDOW_HEIGHT);
      this.setResizable(false);
      this.setTitle("RayCast Me a Dream");
      this.setDefaultCloseOperation(3);
      this.setBackground(Color.BLACK);
      this.setLocationRelativeTo((Component)null);
      this.setVisible(true);
   }

   private void setEngine() {
      this.textures = Texture.getAvailableTextures();
      this.camera = new Camera(4.5D, 4.5D, 1.0D, 0.0D, 0.0D, -0.66D);
      this.addKeyListener(this.camera);
      this.screen = new Screen(map, this.textures, this.mapWidth, this.mapHeight, this.WINDOW_WIDTH, this.WINDOW_HEIGHT);
   }

   private synchronized void start() {
      this.running = true;
      this.thread.start();
   }

   public synchronized void stop() {
      this.running = false;

      try {
         this.thread.join();
      } catch (InterruptedException ex) {
         ex.printStackTrace();
      }

   }

   public void render() {
      BufferStrategy bs = this.getBufferStrategy();
      if (bs == null) {
         this.createBufferStrategy(3);
      } else {
         Graphics g = bs.getDrawGraphics();
         g.drawImage(this.image, 0, 0, this.image.getWidth(), this.image.getHeight(), (ImageObserver)null);
         bs.show();
      }
   }

   public void run() {
      long lastTime = System.nanoTime();
      double ns = 1.6666666666666666E7D;
      double delta = 0.0D;
      this.requestFocus();

      while(this.running) {
         long now = System.nanoTime();
         delta += (double)(now - lastTime) / 1.6666666666666666E7D;

         for(lastTime = now; delta > 1.0D; --delta) {
            this.screen.update(this.camera, this.pixels);
            this.camera.update(map);
         }

         this.render();
      }

   }
}
