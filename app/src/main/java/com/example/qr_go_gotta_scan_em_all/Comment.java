package com.example.qr_go_gotta_scan_em_all;

public class Comment {
    Player player;
    String text;

    /**
     * Constructs a Comment object with the given player and text.
     *
     * @param player The Player object who made the comment.
     * @param text The text of the comment.
     */
    public Comment(Player player, String text) {
        this.player = player;
        this.text = text;
    }

    /**
     * Sets the player who made the comment.
     *
     * @param player The Player object who made the comment.
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Sets the text of the comment.
     *
     * @param text The text of the comment.
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Gets the player who made the comment.
     *
     * @return The Player object who made the comment.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Gets the text of the comment.
     *
     * @return The text of the comment.
     */
    public String getText() {
        return text;
    }
}
