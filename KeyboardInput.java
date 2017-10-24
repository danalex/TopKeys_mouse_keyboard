package com.melloware;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardInput implements KeyListener {
    private static final int KEY_COUNT = 256;
    private boolean[] currentKeys = null;
    private KeyboardInput.KeyState[] keys = null;

    public KeyboardInput() {
        this.currentKeys = new boolean[256];
        this.keys = new KeyboardInput.KeyState[256];

        for(int i = 0; i < 256; ++i) {
            this.keys[i] = KeyboardInput.KeyState.RELEASED;
        }

    }

    public synchronized void poll() {
        for(int i = 0; i < 256; ++i) {
            if(this.currentKeys[i]) {
                if(this.keys[i] == KeyboardInput.KeyState.RELEASED) {
                    this.keys[i] = KeyboardInput.KeyState.ONCE;
                } else {
                    this.keys[i] = KeyboardInput.KeyState.PRESSED;
                }
            } else {
                this.keys[i] = KeyboardInput.KeyState.RELEASED;
            }
        }

    }

    public boolean keyDown(int keyCode) {
        return this.keys[keyCode] == KeyboardInput.KeyState.ONCE || this.keys[keyCode] == KeyboardInput.KeyState.PRESSED;
    }

    public boolean keyJustPressed(int keyCode) {
        return this.keys[keyCode] == KeyboardInput.KeyState.PRESSED;
    }

    public boolean keyDownOnce(int keyCode) {
        return this.keys[keyCode] == KeyboardInput.KeyState.ONCE;
    }

    public synchronized void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if(keyCode >= 0 && keyCode < 256) {
            this.currentKeys[keyCode] = true;
        }

    }

    public synchronized void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if(keyCode >= 0 && keyCode < 256) {
            this.currentKeys[keyCode] = false;
        }

    }

    public void keyTyped(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if(keyCode >= 0 && keyCode < 256) {
            this.currentKeys[keyCode] = true;
        }

    }

    private static enum KeyState {
        RELEASED,
        PRESSED,
        ONCE;

        private KeyState() {
        }
    }
}