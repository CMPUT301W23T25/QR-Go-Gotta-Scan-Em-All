package com.example.qr_go_gotta_scan_em_all;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CommentUnitTest {
    @Test
    public void testComment() {
        Player player = new Player("John Doe", "123");
        String text = "comment";
        Comment comment = new Comment(
                player,
                text
        );
        assertEquals(player, comment.getPlayer());
        assertEquals(text, comment.getText());
    }
}
