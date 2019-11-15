/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dev.stekos.cashregister.views;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

/**
 *
 * @author Stekos
 */
public class GradientPanel extends JPanel {
    @Override
    protected void paintComponent(Graphics graphics) {
        Graphics2D graphics2D = (Graphics2D) graphics;
        int width = getWidth();
        int height = getHeight();
        Color color1 = new Color(71, 120, 197);
        Color color2 = new Color(23, 35, 51);
        GradientPaint gradientPaint = new GradientPaint(0, 0, color1, 180, height, color2);
        graphics2D.setPaint(gradientPaint);
        graphics2D.fillRect(0, 0, width, height);
    }
}
