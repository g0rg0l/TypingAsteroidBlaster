package self.Gorgol.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SwitchPanel extends JPanel implements ActionListener {
    public Menu menu;
    public Game game;

    public SwitchPanel() {
        super(new CardLayout());

        createGUI();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ("start game command".equals(e.getActionCommand())) {
            ((CardLayout) getLayout()).show(this, "game");
            game.runGameLoop();
        }
        if ("exit game command".equals(e.getActionCommand())) {
            System.exit(0);
        }
        if ("end game command".equals(e.getActionCommand())) {
            ((CardLayout) getLayout()).show(this, "menu");
            this.remove(game);
            createGameComponent();
        }
        if ("open options command".equals(e.getActionCommand())) {
            System.out.println("open options");
        }
    }

    private void createGUI() {
        menu = new Menu(650, 850, this);
        add(menu, "menu");

        createGameComponent();
    }

    private void createGameComponent() {
        game = new Game(650, 850, this);
        game.addKeyListener(new GamePanelKeyListener(game));
        add(game, "game");
    }
}
