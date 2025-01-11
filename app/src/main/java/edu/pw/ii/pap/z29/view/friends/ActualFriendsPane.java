package edu.pw.ii.pap.z29.view.friends;

import edu.pw.ii.pap.z29.view.utility.CardPane;
import edu.pw.ii.pap.z29.view.utility.ListPanel;
import edu.pw.ii.pap.z29.view.utility.ListPanelVertical;
import edu.pw.ii.pap.z29.view.GUI;
import edu.pw.ii.pap.z29.view.GUIHelper;
import edu.pw.ii.pap.z29.exception.UserDataException;
import edu.pw.ii.pap.z29.model.primitives.User;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;


public class ActualFriendsPane extends CardPane {
    private GUI gui;
    private ArrayList<Integer> friends = new ArrayList<Integer>();
    
    public ActualFriendsPane(GUI gui) {
        this.gui = gui;
        setName("ActualFriendsPane");
        setOpaque(false);
        setLayout(new GridLayout());
    }

    public void setFriends(List<Integer> friends) {
        this.friends.clear();
        this.friends.addAll(friends);
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
            for (var friend_id : friends)
                users.add(gui.getMainController().getFriendsController().readUser(friend_id));
        } catch (UserDataException e) {
            // TODO: handle it
            return;
        }
        var centralPanel = new ListPanelVertical();
        add(centralPanel);
        for (var user : users) {
            var usernameLabel = GUIHelper.createDefaultLabel(user.getUsername().getUsername(), 20);
            var removeButton = GUIHelper.createDefaultButton("X", 20);
            removeButton.addActionListener(new RemoveListener(user.getUserId()));
            var row = new ListPanelVertical.Row();
            row.set(usernameLabel, removeButton);
            centralPanel.addRow(row);
        }
    }

    class RemoveListener implements ActionListener {
        int userId;
    
        public RemoveListener(int userId) {
            this.userId = userId;
        }
    
        public void actionPerformed(ActionEvent e) {
            gui.getMainController().getFriendsController().removeFriend(userId);
            friends.remove(Integer.valueOf(userId));
            cleanup();
            init();
        }
    }
}
