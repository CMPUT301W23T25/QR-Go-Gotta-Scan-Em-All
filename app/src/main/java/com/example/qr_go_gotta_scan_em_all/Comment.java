package com.example.qr_go_gotta_scan_em_all;

public class Comment {
    Player player;
    String text;

    public Comment(Player player, String text) {
        this.player = player;
        this.text = text;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Player getPlayer() {
        return player;
    }

    public String getText() {
        return text;
    }
}
