package com.bsodsoftware.raycastmeadream.engine;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Camera implements KeyListener {
   public double xPos;
   public double yPos;
   public double xDir;
   public double yDir;
   public double xPlane;
   public double yPlane;
   public boolean left;
   public boolean right;
   public boolean forward;
   public boolean back;
   public final double MOVE_SPEED = 0.09D;
   public final double ROTATION_SPEED = 0.06D;

   public Camera(double x, double y, double xd, double yd, double xp, double yp) {
      this.xPos = x;
      this.yPos = y;
      this.xDir = xd;
      this.yDir = yd;
      this.xPlane = xp;
      this.yPlane = yp;
   }

   public void update(int[][] map) {
      if (this.forward) {
         if (map[(int)(this.xPos + this.xDir * MOVE_SPEED)][(int)this.yPos] == 0) {
            this.xPos += this.xDir * MOVE_SPEED;
         }

         if (map[(int)this.xPos][(int)(this.yPos + this.yDir * MOVE_SPEED)] == 0) {
            this.yPos += this.yDir * MOVE_SPEED;
         }
      }

      if (this.back) {
         if (map[(int)(this.xPos - this.xDir * MOVE_SPEED)][(int)this.yPos] == 0) {
            this.xPos -= this.xDir * MOVE_SPEED;
         }

         if (map[(int)this.xPos][(int)(this.yPos - this.yDir * MOVE_SPEED)] == 0) {
            this.yPos -= this.yDir * MOVE_SPEED;
         }
      }

      double oldxDir;
      double oldxPlane;
      if (this.right) {
         oldxDir = this.xDir;
         this.xDir = this.xDir * Math.cos(-ROTATION_SPEED) - this.yDir * Math.sin(-ROTATION_SPEED);
         this.yDir = oldxDir * Math.sin(-ROTATION_SPEED) + this.yDir * Math.cos(-ROTATION_SPEED);
         oldxPlane = this.xPlane;
         this.xPlane = this.xPlane * Math.cos(-ROTATION_SPEED) - this.yPlane * Math.sin(-ROTATION_SPEED);
         this.yPlane = oldxPlane * Math.sin(-ROTATION_SPEED) + this.yPlane * Math.cos(-ROTATION_SPEED);
      }

      if (this.left) {
         oldxDir = this.xDir;
         this.xDir = this.xDir * Math.cos(ROTATION_SPEED) - this.yDir * Math.sin(0.06D);
         this.yDir = oldxDir * Math.sin(ROTATION_SPEED) + this.yDir * Math.cos(0.06D);
         oldxPlane = this.xPlane;
         this.xPlane = this.xPlane * Math.cos(ROTATION_SPEED) - this.yPlane * Math.sin(ROTATION_SPEED);
         this.yPlane = oldxPlane * Math.sin(ROTATION_SPEED) + this.yPlane * Math.cos(ROTATION_SPEED);
      }
      System.out.println("xDir: " + this.xDir + " - yDir: " + this.yDir + " - xPos: " + this.xPos + " - yPos: " + this.yPos);
   }

   public void keyPressed(KeyEvent key) {
      if (key.getKeyCode() == 37) {
         this.left = true;
      }

      if (key.getKeyCode() == 39) {
         this.right = true;
      }

      if (key.getKeyCode() == 38) {
         this.forward = true;
      }

      if (key.getKeyCode() == 40) {
         this.back = true;
      }

   }

   public void keyReleased(KeyEvent key) {
      if (key.getKeyCode() == 37) {
         this.left = false;
      }

      if (key.getKeyCode() == 39) {
         this.right = false;
      }

      if (key.getKeyCode() == 38) {
         this.forward = false;
      }

      if (key.getKeyCode() == 40) {
         this.back = false;
      }

   }

   public void keyTyped(KeyEvent key) {
   }

   public class CameraValues {
      public static final double STARTING_LOCATION_X = 4.5D;
      public static final double STARTING_LOCATION_Y = 4.5D;
      public static final double FOV = -0.66D;
   }
}
