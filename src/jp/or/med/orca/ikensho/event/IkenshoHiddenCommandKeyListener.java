package jp.or.med.orca.ikensho.event;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/** TODO <HEAD_IKENSYO> */
public class IkenshoHiddenCommandKeyListener implements KeyListener {
    public static final int STATE_FREE = 0;
    public static final int STATE_PULL = 1;
    public static final int STATE_HOLD = 2;
    public static final int STATE_PUSH = 3;
    private int[] key = new int[128];

    private final int[] hiddenCmd = {
        KeyEvent.VK_UP,
        KeyEvent.VK_UP,
        KeyEvent.VK_DOWN,
        KeyEvent.VK_DOWN,
        KeyEvent.VK_LEFT,
        KeyEvent.VK_RIGHT,
        KeyEvent.VK_LEFT,
        KeyEvent.VK_RIGHT,
        KeyEvent.VK_B,
        KeyEvent.VK_A
    };
    private int hiddenCmdIdx = 0;

    public void keyTyped(KeyEvent e) {
    }
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() < key.length) {
            key[e.getKeyCode()] = updateKeyState(key[e.getKeyCode()], true);
        }
    }
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() < key.length) {
            key[e.getKeyCode()] = updateKeyState(key[e.getKeyCode()], false);
        }
    }
    private int updateKeyState(int key, boolean pressed) {
        for (int i=0; i<this.key.length; i++) {
            if (i != key) {
                if (this.key[i] == STATE_PULL) {
                    this.key[i] = STATE_FREE;
                }
            }
        }

        if (pressed) {
            if (key == STATE_FREE) {
                return STATE_PUSH;
            }
            else {
                return STATE_HOLD;
            }
        }
        else {
            if (key == STATE_HOLD) {
                return STATE_PULL;
            }
            else {
                return STATE_FREE;
            }
        }
    }
    public int[] getKey() {
        return key;
    }

    public boolean checkHiddenCmd(int keyCode) {
        if (keyCode >= getKey().length) {
            return false;
        }

        int[] key = this.getKey();
        if (key[keyCode] == IkenshoHiddenCommandKeyListener.STATE_PUSH) {
            if (keyCode == hiddenCmd[hiddenCmdIdx]) {
                if (hiddenCmdIdx == hiddenCmd.length - 1) {
                    hiddenCmdIdx = 0;
                    return true;
                }
                if (hiddenCmdIdx < hiddenCmd.length - 1) {
                    hiddenCmdIdx++;
                }
            }
            else {
                hiddenCmdIdx = 0;
            }
        }
        return false;
    }
}
