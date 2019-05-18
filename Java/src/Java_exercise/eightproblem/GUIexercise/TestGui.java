package Java_exercise.eightproblem.GUIexercise;

import com.sun.source.tree.NewArrayTree;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;


class DrawShape{
    Shape s;
    Color c;
    int x;
    int y;
}

class DrawShapePen extends DrawShape{
    ArrayList<Shape> s;
}

class DPMouseMotionLisetener implements MouseMotionListener{
    DrawPanel dp;
    DPMouseMotionLisetener(DrawPanel _dp){
        dp=_dp;
    }
    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        DrawShape ds=null;
        if( dp.curds==null) {

            dp.curds = new DrawShape();
        }
        ds=dp.curds;
        switch ( this.dp.currShapeType){
            case 1:{
                Rectangle s=(Rectangle)dp.curds.s;
                s.setSize(e.getX()-dp.curds.x,e.getY()-dp.curds.y);
                dp.curds=null;
                dp.repaint();
            }
        }
    }
}
class DPMouseListener implements MouseListener {
    DrawPanel dp;

    DPMouseListener(DrawPanel _dp){
        dp=_dp;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        DrawShape ds=null;
        if( dp.curds==null) {

            dp.curds = new DrawShape();
        }
        ds=dp.curds;
        switch ( this.dp.currShapeType){
            case 1:{
                Rectangle s=(Rectangle)dp.curds.s;
                s.x=dp.curds.x;
                s.y=dp.curds.y;
                s.setSize(e.getX()-dp.curds.x,e.getY()-dp.curds.y);
                dp.dsl.add(dp.curds);
                dp.curds=null;
                dp.repaint();
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        dp.curds.x=e.getX();
        dp.curds.y=e.getY();
        DrawShape ds=new DrawShape();
        ds.c=this.dp.currColor;
        switch ( this.dp.currShapeType){
            case 1:{
                Shape s=new Rectangle(0,0);
                ds.x=e.getX();
                ds.y=e.getY();
                ds.s=s;
                dp.curds=ds;
            }
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }
}
class DrawPanel extends JPanel{
    Color currColor=Color.black;
    ArrayList<DrawShape> dsl=new ArrayList<>();
    DrawShape curds;
    int currShapeType=1;


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d=(Graphics2D)g;
        g2d.setColor(this.currColor);
//        g2d.draw();
        g2d.drawString("Hello!",20,50);
        for(DrawShape ds:dsl){
            g2d.setColor(ds.c);
            g2d.draw(ds.s);
        }
        if(curds!=null) {
            g2d.setColor(curds.c);
            g2d.draw(curds.s);
        }
    }
}


public class TestGui extends JFrame {
    JButton btnColor=new JButton("Color Chooser");

    DrawPanel dp=new DrawPanel();

    public void addLis(){
        btnColor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JColorChooser jc=new JColorChooser();
                dp.currColor=jc.showDialog(TestGui.this,"choose color",dp.currColor==null?Color.black:dp.currColor);
                dp.setBackground(dp.currColor);
            }
        });
        dp.addMouseListener(new DPMouseListener(dp));
        dp.addMouseMotionListener(new DPMouseMotionLisetener(dp));
    }
    public void init(){
        this.getContentPane().add(dp);
        this.getContentPane().add(btnColor, BorderLayout.NORTH);
        dp.setBackground(Color.red);
        this.setSize(300,400);
//        this.getContentPane().setLayout(new FlowLayout());
        this.setVisible(true);
        this.setDefaultCloseOperation(1);
        addLis();
    }
    public static void main(String[] args){
        TestGui tg=new TestGui();
        tg.init();
    }
}
