package edu.pw.ii.pap.z29.controller;

import java.sql.SQLException;
import java.util.NoSuchElementException;

import edu.pw.ii.pap.z29.exception.UserDataException;
import edu.pw.ii.pap.z29.model.primitives.Friendship;
import edu.pw.ii.pap.z29.model.primitives.User;
import edu.pw.ii.pap.z29.view.GUI;


public class FriendsController {
    private MainController mainController;

    public FriendsController(MainController mainController) {
        this.mainController = mainController;
    }

    public User readUser(int user_id) throws UserDataException {
        User user;
        try {
            user = mainController.getUsers().read(user_id).get();
        } catch (SQLException e) {
            mainController.getSqlLogger().log(e);
            throw new UserDataException(e);
        } catch (NoSuchElementException e) {
            throw new UserDataException(e);
        }
        return user;
    }

    public void removeFriend(int friend_id) {
        int user_id = mainController.getLoginController().getCurrentUser().getUserId();
        var ending_friendship1 = new Friendship(user_id, friend_id, false);
        var ending_friendship2 = new Friendship(friend_id, user_id, false);
        var friendships_table = mainController.getFriendships();
        try {
            if (!friendships_table.delete(ending_friendship1))
                friendships_table.delete(ending_friendship2);
        } catch (SQLException e) {
            mainController.getSqlLogger().log(e);
        }
    }

    public boolean acceptFriend(int friend_id) {
        int user_id = mainController.getLoginController().getCurrentUser().getUserId();
        var friendship = new Friendship(friend_id, user_id, false);
        var friendships_table = mainController.getFriendships();
        boolean accepted = false;
        try {
            accepted = friendships_table.update_pending(friendship);
        } catch (SQLException e) {
            mainController.getSqlLogger().log(e);
        }
        return accepted;
    }

    public void goBack() {
        mainController.getGui().showPane(GUI.Pane.Profile);
    }
}
