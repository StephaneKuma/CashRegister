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
public class DashGradientPanel extends JPanel {
    private final Color startColor;
    private final Color endColor;
    
    public DashGradientPanel(Color startColor, Color endColor) {
        super();
        this.startColor = startColor;
        this.endColor = endColor;
    }
    
    @Override
    protected void paintComponent(Graphics graphics) {
        Graphics2D graphics2D = (Graphics2D) graphics;
        int width = getWidth();
        int height = getHeight();
        GradientPaint gradientPaint = new GradientPaint(0, 0, this.startColor, 180, height, this.endColor);
        graphics2D.setPaint(gradientPaint);
        graphics2D.fillRect(0, 0, width, height);
    }
}
