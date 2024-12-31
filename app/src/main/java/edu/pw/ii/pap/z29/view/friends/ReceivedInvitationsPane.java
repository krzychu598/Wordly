package edu.pw.ii.pap.z29.view.friends;

import edu.pw.ii.pap.z29.exception.UserDataException;
import edu.pw.ii.pap.z29.model.primitives.User;
import edu.pw.ii.pap.z29.view.GUI;
import edu.pw.ii.pap.z29.view.GUIHelper;
import edu.pw.ii.pap.z29.view.utility.CardPane;
import edu.pw.ii.pap.z29.view.utility.ListPanel;
import edu.pw.ii.pap.z29.view.utility.ListPanelVertical;

import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class ReceivedInvitationsPane extends CardPane {
    GUI gui;
    private ArrayList<Integer> received = new ArrayList<Integer>();
    
    public ReceivedInvitationsPane(GUI gui) {
        this.gui = gui;
        setName("ReceivedInvitationsPane");
        setOpaque(false);
        setLayout(new GridLayout());
    }

    public void setReceived(List<Integer> received) {
        this.received.clear();
        this.received.addAll(received);
    }

    @Override public void init() {
        addGuiParts();
        revalidate();
        repaint();
    }

    @Override public void cleanup() {
        removeAll();
    }

    private void addGuiParts() {
        var users = new ArrayList<User>();
        try {
            for (var friend_id : received)
                users.add(gui.getMainController().getFriendsController().readUser(friend_id));
        } catch (UserDataException e) {
            // TODO: handle it
            return;
        }
        var centralPanel = new ListPanelVertical();
        add(centralPanel);
        for (var user : users) {
            var usernameLabel = GUIHelper.createDefaultLabel(user.getUsername().getUsername(), 20);
            var buttonPanel = GUIHelper.createContainerPanel();
            buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
            var acceptButton = GUIHelper.createDefaultButton("OK", 20);
            acceptButton.addActionListener(new AcceptListener(user.getUserId()));
            buttonPanel.add(acceptButton);
            buttonPanel.add(Box.createHorizontalStrut(5));
            var removeButton = GUIHelper.createDefaultButton("X", 20);
            removeButton.addActionListener(new RemoveListener(user.getUserId()));
            buttonPanel.add(removeButton);
            var row = new ListPanelVertical.Row();
            row.set(usernameLabel, buttonPanel);
            centralPanel.addRow(row);
        }
    }

    class AcceptListener implements ActionListener {
        int userId;
    
        public AcceptListener(int userId) {
            this.userId = userId;
        }
    
        public void actionPerformed(ActionEvent e) {
            if (gui.getMainController().getFriendsController().acceptFriend(userId)) {
                received.remove(Integer.valueOf(userId));
                cleanup();
                init();
            }
        }
    }
    
    class RemoveListener implements ActionListener {
        int userId;
    
        public RemoveListener(int userId) {
            this.userId = userId;
        }
    
        public void actionPerformed(ActionEvent e) {
            gui.getMainController().getFriendsController().removeFriend(userId);
            received.remove(Integer.valueOf(userId));
            cleanup();
            init();
        }
    }
}
