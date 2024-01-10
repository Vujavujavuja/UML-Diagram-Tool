package raf.dsw.classycraft.app.gui.swing.view.tabbedPane;

import raf.dsw.classycraft.app.core.eventHandler.EventBus;
import raf.dsw.classycraft.app.core.eventHandler.EventType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CloseableTabbedPane extends JTabbedPane {
    public CloseableTabbedPane() {
        super();
    }

    public void addTab(String title, Component component) {
        super.addTab(title, component);
        int count = this.getTabCount() - 1;
        setTabComponentAt(count, new CloseButtonTab(component, title, this));
    }

    private class CloseButtonTab extends JPanel {
        private Component tab;

        public CloseButtonTab(final Component tab, String title, final JTabbedPane pane) {
            this.tab = tab;
            setOpaque(false);
            FlowLayout flowLayout = new FlowLayout(FlowLayout.CENTER, 3, 3);
            setLayout(flowLayout);
            JLabel titleLabel = new JLabel(title);
            add(titleLabel);
            JButton closeButton = new JButton("x");
            closeButton.setFont(new Font("Arial", Font.BOLD, 12));
            closeButton.setBorderPainted(false);
            closeButton.setFocusPainted(false);
            closeButton.setContentAreaFilled(false);
            closeButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    int index = pane.indexOfComponent(tab);
                    if (index >= 0) {
                        EventBus.getInstance().notifySubscriber(tab, EventType.DIAGRAM_CLOSE);
                    }
                }
            });
            add(closeButton);
        }
    }

}
