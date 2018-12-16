package GUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import com.sun.javafx.geom.Point2D;

import Coords.Lat_lon_alt;
import algo.Solution;
import convert.Ratio;
import game_elements.Fruit;
import game_elements.Game;
import game_elements.Map;
import game_elements.Packman;



public class MainWindow extends JFrame implements MouseListener, MenuListener 
{
	BufferedImage myImage=null;
	private Map map = null;
	private Game game = null;
	private Solution solution = null;

	public MainWindow() 
	{
		initGUI();
		game = new Game();
		Lat_lon_alt min = new Lat_lon_alt(32.10190,35.20236,0);
		Lat_lon_alt max = new Lat_lon_alt(32.10582,35.21234,0);
		map = new Map(myImage, min, max);
		this.addMouseListener(this); 
	}

	private void initGUI() 
	{
		initMENU();
		try {
			myImage = ImageIO.read(new File("image.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}


	private void initMENU() {
		MenuBar menuBar = new MenuBar();

		Menu csv = new Menu("load csv file");
		Menu manual = new Menu("manual");

		MenuItem load_csv = new MenuItem("load csv to gameboard");
		load_csv(load_csv);
		MenuItem run_csv = new MenuItem("run");
		run_csv(run_csv);

		MenuItem add_p = new MenuItem("add packmans");
		add_p(add_p);
		MenuItem add_f = new MenuItem("add fruits");
		add_f(add_f);
		MenuItem run_manual = new MenuItem("run");
		run_manual(run_manual);

		menuBar.add(csv);
		menuBar.add(manual);

		csv.add(load_csv);
		csv.add(run_csv);

		manual.add(add_p);
		manual.add(add_f);
		manual.add(run_manual);

		this.setMenuBar(menuBar);

	}





	private void load_csv(MenuItem load_csv) {
		load_csv.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//				FileChooser fc = new FileChooser();
			}
		});
	}


	private void run_csv(MenuItem run_csv) {
		run_csv.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
			}
		});
	}



	private void add_p(MenuItem add_p) {
		add_p.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				packman_or_fruit = "packman";
			}
		});
	}



	private void add_f(MenuItem add_f) {
		add_f.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				packman_or_fruit = "fruit";
			}
		});

	}



	private void run_manual(MenuItem run_manual) {
		// TODO Auto-generated method stub

	}


	private String packman_or_fruit = "";
	private int x = -1;
	private int y = -1;

	private ArrayList<Point2D> fruits = new ArrayList<Point2D>();
	private ArrayList<Point2D> packmans = new ArrayList<Point2D>();

	public void paint(Graphics g)
	{
		g.drawImage(myImage, 0, 0,getWidth()-8,getHeight()-8, this);

		if(x!=-1 && y!=-1)
		{
			Iterator<Point2D> it_p = packmans.iterator();
			while(it_p.hasNext()) {
				Point2D current =it_p.next();
				int r = 20;
				Point p = Ratio.ratio2Pixel(getWidth(), getHeight(), current);
				x = p.x-8;
				y = p.y-8;
				g.setColor(Color.YELLOW);
				g.fillOval(x, y, r, r);
			}

			Iterator<Point2D> it_f = fruits.iterator();
			while(it_f.hasNext()) {
				Point2D current =it_f.next();
				int r = 12;
				Point p = Ratio.ratio2Pixel(getWidth(), getHeight(), current);
				x = p.x-4;
				y = p.y-4;
				g.setColor(Color.red);
				g.fillOval(x, y, r, r);
			}
		}
	}

	@Override
	public void menuCanceled(MenuEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void menuDeselected(MenuEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void menuSelected(MenuEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(packman_or_fruit.equals("packman")) {
			System.out.println("packman added!");
			System.out.println("("+ e.getX() + "," + e.getY() +")");
			x = e.getX();
			y = e.getY();
			Point p = new Point(x, y);
			Point2D ratio = Ratio.pixel2Ratio(getWidth(), getHeight(), p);
			packmans.add(ratio);
			Packman packman = new Packman(Ratio.ratio2Lat_lon(map, ratio));
			game.add_packman(packman);
			repaint();
		}
		if(packman_or_fruit.equals("fruit")) {
			System.out.println("fruit added!");
			System.out.println("("+ e.getX() + "," + e.getY() +")");
			x = e.getX();
			y = e.getY();
			Point p = new Point(x, y);
			Point2D ratio = Ratio.pixel2Ratio(getWidth(), getHeight(), p);
			fruits.add(ratio);
			Fruit fruit = new Fruit(Ratio.ratio2Lat_lon(map, ratio));
			game.add_fruit(fruit);
			repaint();
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}


}
