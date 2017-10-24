
package com.melloware;

import com.melloware.KeyboardInput;
import com.melloware.jintellitype.HotkeyListener;
import com.melloware.jintellitype.IntellitypeListener;
import com.melloware.jintellitype.JIntellitype;
import java.awt.AWTException;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Robot;
import java.awt.Toolkit;
import javax.swing.JDialog;

public class JIntellitypeTester extends JDialog implements HotkeyListener, IntellitypeListener {
    boolean rasta;
    boolean getcapsword = false;
    public int width;
    public int height;
    public int sppressed;
    public int xpressed;
    public int mpressed;
    public int npressed;
    public int mwppressed;
    public int mwnpressed;
    public int flagbobx;
    public int flagboby;
    KeyboardInput keyboard = new KeyboardInput();
    Canvas canvas;
    JIntellitypeTester.Bob bob = new JIntellitypeTester.Bob();
    private static JIntellitypeTester mainFrame;
    private static final int ALT_SPACE = 32;
    private static final int ADJUSTROUND = 14;

    public JIntellitypeTester() {
        JIntellitype.getInstance().registerHotKey(32, 1, 84);
        this.canvas = new Canvas();
        Toolkit t = Toolkit.getDefaultToolkit();
        Dimension d = t.getScreenSize();
        this.width = d.width;
        this.height = d.height;
        this.canvas.setSize(this.width, this.height);
        this.canvas.setVisible(false);
        this.add(this.canvas);
        this.pack();
        this.addKeyListener(this.keyboard);
        this.canvas.addKeyListener(this.keyboard);
        this.bob.x = this.width / 2;
        this.bob.y = this.height / 2;
        this.bob.dx = this.bob.dy = 1;
        this.bob.w = this.bob.h = 5;
    }

    public void run() {
        while(true) {
            this.sppressed = this.xpressed = this.mpressed = this.npressed = this.mwppressed = this.mwnpressed = 1;
            this.setAlwaysOnTop(true);
            if(this.getcapsword) {
                if(this.rasta) {
                    try {
                        Robot robotrasta = new Robot();
                        robotrasta.mousePress(16);
                        robotrasta.mouseRelease(16);
                    } catch (AWTException var2) {
                        ;
                    }
                }

                this.rasta = false;
            } else {
                this.rasta = true;
                this.setAlwaysOnTop(true);
                this.toFront();
                this.requestFocus();
                if(this.bob.x == this.flagbobx && this.bob.y == this.flagboby) {
                    this.bob.dx = this.bob.dy = 1;
                }

                this.flagbobx = this.bob.x;
                this.flagboby = this.bob.y;
                this.keyboard.poll();
                if(this.keyboard.keyDownOnce(27)) {
                    return;
                }

                this.processInput();
                this.processKeys();

                try {
                    Thread.sleep(10L);
                } catch (InterruptedException var3) {
                    ;
                }
            }
        }
    }

    protected void processKeys() {
        try {
            Robot robot = new Robot();
            robot.mouseMove(this.bob.x, this.bob.y);
            if(this.mpressed == 2) {
                robot.mousePress(16);
            }

            if(this.npressed == 2) {
                robot.mouseRelease(16);
            }

            if(this.xpressed == 2) {
                robot.mousePress(4);
                robot.mouseRelease(4);
            }

            if(this.sppressed == 2) {
                robot.mousePress(16);
                robot.mouseRelease(16);
            }
        } catch (AWTException var2) {
            ;
        }

    }

    protected void processInput() {
        if(this.keyboard.keyDown(65)) {
            this.bob.y += this.bob.dy;
            ++this.bob.dy;
            this.bob.dx = 14;
            if(this.bob.y + this.bob.h > this.height - 1) {
                this.bob.y = 0;
            }
        }

        if(this.keyboard.keyDown(81)) {
            this.bob.y -= this.bob.dy;
            ++this.bob.dy;
            this.bob.dx = 14;
            if(this.bob.y < 0) {
                this.bob.y = this.height;
            }
        }

        if(this.keyboard.keyDown(79)) {
            this.bob.x -= this.bob.dx;
            ++this.bob.dx;
            this.bob.dy = 15;
            if(this.bob.x < 0) {
                this.bob.x = this.width;
            }
        }

        if(this.keyboard.keyDown(80)) {
            this.bob.x += this.bob.dx;
            ++this.bob.dx;
            this.bob.dy = 15;
            if(this.bob.x + this.bob.w > this.width - 1) {
                this.bob.x = 0;
            }
        }

        if(this.keyboard.keyDownOnce(77)) {
            ++this.mpressed;
        }

        if(this.keyboard.keyDownOnce(88)) {
            ++this.xpressed;
        }

        if(this.keyboard.keyDownOnce(32)) {
            ++this.sppressed;
        }

        if(this.keyboard.keyDownOnce(87)) {
            ++this.mwppressed;
            System.out.println("mwppressed: " + this.mwppressed);
        }

        if(this.keyboard.keyDownOnce(83)) {
            ++this.mwnpressed;
            System.out.println("mwnpressed: " + this.mwnpressed);
        }

        if(this.keyboard.keyDownOnce(78)) {
            ++this.npressed;
        }

    }

    public static void main(String[] args) {
        mainFrame = new JIntellitypeTester();
        mainFrame.setVisible(true);
        mainFrame.setTitle("3Spaces");
        mainFrame.setResizable(false);
        mainFrame.setLocation(0, -1000);
        mainFrame.initJIntellitype();
        mainFrame.run();
        JIntellitype.getInstance().cleanUp();
        System.exit(0);
    }

    public void onHotKey(int aIdentifier) {
        this.getcapsword = !this.getcapsword;
        this.setAlwaysOnTop(true);
        this.toFront();
        this.requestFocus();
    }

    public void onIntellitype(int aCommand) {
    }

    public void initJIntellitype() {
        try {
            JIntellitype.getInstance().addHotKeyListener(this);
            JIntellitype.getInstance().addIntellitypeListener(this);
        } catch (RuntimeException var2) {
            ;
        }

    }

    class Bob {
        int x;
        int y;
        int w;
        int h;
        int dx;
        int dy;

        Bob() {
        }
    }
}
