import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.Timer;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Davin Clark
 * P4
 * Feb 29, 2016
 * Time: 4.5
 * 
 * 
 * This lab took a lot more time than I thought it would.I ran into a lot of bugs when 
 * coding this, and they took a long time to figure out. My life code fit pretty well 
 * and I only had to change the arrays from ints to Colors. However, this messed up a lot
 * of things and I spent a long time trying to fix it. As of now , the code runs ok, but 
 * there is still more things I want to finish, but havent had the time yet.
 */

public class P4_Clark_Davin_LifeGUI {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Gui a = new Gui();
		
	}

}

class Gui {
	
	int size = 7;
	
	Timer timer = new Timer(50,new ActionListener(){

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			genPlus();
		}
		
	});
	
	Color c = Color.RED;
	Drawing pad = new Drawing();
	Color[][] pixels = new Color[size][size];
	int start = 0;
	JColorChooser cPick = new JColorChooser();
	
	Lifer life;
	
	JSlider speed = new JSlider();
	JSlider sizeSlide = new JSlider();
	
	JTextArea speedDisplay = new JTextArea();
	
	JRadioButton step = new JRadioButton("Step");
	JRadioButton cont = new JRadioButton("Continuous");
	ButtonGroup mode = new ButtonGroup();
	
	
	JRadioButton mix = new JRadioButton("Mix");
	JRadioButton vs = new JRadioButton("VS");
	JRadioButton choice = new JRadioButton("1 Color");
	
	JButton ss = new JButton("Step");
	JButton stop = new JButton("Stop");
	ButtonGroup type = new ButtonGroup();
	
	
	public void fillWhite(Color[][] pixels){
		for (int i = 0; i < pixels.length; i++) {
			for (int j = 0; j < pixels[0].length; j++) {
				pixels[i][j]=Color.WHITE;
			}
		}
	}
	
	
	@SuppressWarnings("static-access")
	Gui() {
		// Create a basic Java window frame
		JFrame window = new JFrame("Davin Clark");

		// Decide what to do when the user closes the window
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Set the window size (see API)
		window.setBounds(200, 200, 1080, 800);

		// Prevent users from resizing the window
		window.setResizable(true);

		fillWhite(pixels);
		System.out.println(!pixels[0][0].equals(Color.WHITE));
		life = new Lifer(20,pixels,Color.BLACK);
		//Bar
		
		JMenu file = new JMenu("File");
		JMenu edit = new JMenu("Edit");
		
		JMenuBar bar = new JMenuBar();
		JMenuItem open = new JMenuItem("Open");
		JMenuItem save = new JMenuItem("Save");
		JMenuItem clearItem = new JMenuItem("Clear");
		file.add(open);
		file.add(save);
		edit.add(clearItem);
		bar.add(file);
		bar.add(edit);
		window.setJMenuBar(bar);
		clearItem.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				fillWhite(pixels);

				start = 0;
				pad.repaint();
			}
			
		});
		save.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JFileChooser sFile = new JFileChooser();
				sFile.showSaveDialog(window);
				String no = null;
				File f = sFile.getSelectedFile();
				String text = "P3\r\n"+size+"\r\n"+size+"\r\n255"+"\r\n";
				for (int i = 0; i < pixels.length; i++) {
					for (int j = 0; j < pixels.length; j++) {
						Color c = pixels[i][j];
						text+= c.getRed()+"\t"+c.getGreen()+"\t"+c.getBlue()+"\t";
					}
					text+="\r\n";
				}
				System.out.println(text);
				try {
					FileWriter w = new FileWriter(f);
					w.write(text);
					w.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		});
		open.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JFileChooser oFile = new JFileChooser();
				oFile.showOpenDialog(window);
				File f = oFile.getSelectedFile();
				Scanner scan = null;
				int newSize = size;
				try {
					scan = new Scanner(f);
					Color[][] temp = new Color[size][size];

					if (scan.next().equals("P3")) {
						newSize = scan.nextInt();
						System.out.println(newSize+"");
						size = newSize;
						temp = new Color[size][size];
						fillWhite(temp);
						pad.repaint();
						scan.next();
						scan.next();
						int row = 0;
						while (scan.hasNext()) {
							int count = 0;
							while (count < size) {
								int r = scan.nextInt();
								int g = scan.nextInt();
								int b = scan.nextInt();
								temp[row][count] = new Color(r, g, b);
								System.out.println("added" + r + g + b+"at row"+row+"col"+count);
								count++;
							}
							row++;
						}
						pixels = temp;
						pad.repaint();
					}
				
				}
				 catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					// e1.printStackTrace();
					System.out.println("no");
				}

				pad.repaint();
			}

		});
		
		// Create GUI components.
		pad.setPreferredSize(new Dimension(400,400));
		
		JPanel pan = new JPanel(new FlowLayout());
		JPanel space = new JPanel();
		JPanel tools = new JPanel();
		space.setPreferredSize(new Dimension(2000,20));
		tools.setPreferredSize(new Dimension(510, 600));
		pan.add(space);
		
		pad.addMouseListener(new MouseListener(){

			Graphics2D g2 = (Graphics2D)pad.getGraphics();
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
				int x = (int) ((e.getX()-(e.getX()%(400.0/size)))/(400.0/size));
				int y = (int) ((e.getY()-(e.getY()%(400.0/size)))/(400.0/size));
				
				System.out.println(e.getX()+", "+e.getY());
				System.out.println(x+", "+y);
				if(e.getButton() == 1){
					pixels[y][x]=cPick.getColor();
					
				}
				else{
					pixels[y][x]=Color.WHITE;
				}
				pad.repaint();
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		pad.addMouseMotionListener(new MouseMotionListener(){

			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub
				int x = (int) ((e.getX()-(e.getX()%(400.0/size)))/(400.0/size));
				int y = (int) ((e.getY()-(e.getY()%(400.0/size)))/(400.0/size));
				System.out.println(e.getX()+", "+e.getY());
				System.out.println(x+", "+y);
				if(e.getModifiers() == MouseEvent.BUTTON1_MASK){
					pixels[y][x]=cPick.getColor();
					
				}
				else{
					pixels[y][x]=Color.WHITE;
				}
				pad.repaint();
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

		});
		cPick.setPreferredSize(new Dimension(500,300));
		
		tools.add(cPick);
		
		JButton clear = new JButton("Clear");
		clear.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				fillWhite(pixels);

				start = 0;
				pad.repaint();
			}
			
		});
		JPanel contPan = new JPanel(new FlowLayout());
		contPan.add(pad);
		contPan.setPreferredSize(new Dimension(500,700));
		contPan.add(clear);
		clear.setPreferredSize(new Dimension(110,40));
		contPan.add(ss);
		ss.setPreferredSize(new Dimension(110,40));
		
		ss.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(step.isSelected()){
					genPlus();
				}
				else if (timer.isRunning()) {
					timer.stop();
					ss.setText("Start");
				} 
				else {
					timer.start();
					ss.setText("Stop");
				}

			}
			
		});
		
		pan.add(contPan);
		
		JPanel modePan = new JPanel(new FlowLayout());
		modePan.setPreferredSize(new Dimension(200,70));
		step.addActionListener(new modeL());
		cont.addActionListener(new modeL());
		mode.add(step);
		mode.add(cont);
		modePan.add(step);
		modePan.add(cont);
		modePan.setBorder(new TitledBorder("Gen Mode"));
		step.setSelected(true);
		tools.add(modePan);
		
		JPanel typePan = new JPanel(new FlowLayout());
		typePan.setPreferredSize(new Dimension(300,70));
		type.add(choice);
		type.add(mix);
		type.add(vs);
		choice.addActionListener(new typeL());
		mix.addActionListener(new typeL());
		vs.addActionListener(new typeL());
		typePan.add(choice);
		typePan.add(mix);
		typePan.add(vs);
		choice.setSelected(true);
		typePan.setBorder(new TitledBorder("Color Mode"));
		tools.add(typePan);
		
		
		speed.setMinimum(0);
		speed.setMaximum(2000);
		speed.setMajorTickSpacing(200);
		speed.setMinorTickSpacing(50);
		speed.setPaintLabels(true);
		
		speed.setPaintTicks(true);
		speed.setPreferredSize(new Dimension(400,50));
		speedDisplay.setText(speed.getValue()+"");
		speed.addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent e) {
				speedDisplay.setText(speed.getValue()+"");
				timer.setDelay(speed.getValue());
			}
			
		});
		speedDisplay.setPreferredSize(new Dimension(40,20));
		speedDisplay.setEditable(false);
		JPanel speedPan = new JPanel(new FlowLayout());
		speedPan.setBorder(new TitledBorder("Speed"));
		speedPan.add(speed);
		speedPan.add(speedDisplay);
		speedPan.setPreferredSize(new Dimension(500,80));
		tools.add(speedPan);
		pan.add(tools);
		window.add(pan);
		
		
		
		window.setVisible(true);
		
	}
	public void genPlus(){
		life.runLife(1);
		pixels= life.pixels;
		pad.repaint();
	}
	
	private class modeL implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			ss.setText("Step");
			if(e.getSource()==cont){
				if(timer.isRunning())
					timer.stop();
				ss.setText("Start");
				
			}
		}
		
	}
	private class typeL implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
			if(e.getSource()==choice){
				life = new Lifer(size,pixels,cPick.getColor());
				
			}
			else if(e.getSource()==vs){
				life = new Lifer(size,pixels,0);
			}
			else if(e.getSource()==mix){
				life = new Lifer(size,pixels,1);
			}
		}
		
	}
	
	private class Drawing extends JPanel {
		@Override
		public void paintComponent(Graphics g) {

			super.paintComponent(g);
			// Draw stuff
			Graphics2D g2 = (Graphics2D)g;
			
			g2.setColor(Color.WHITE);
			g2.fillRect(0, 0, 400, 400);
			for (int i = 0; i < pixels.length; i++) {
				for (int j = 0; j < pixels[0].length; j++) {
					g2.setColor(pixels[i][j]);
					g2.fillRect(j*400/size, i*400/size, 400/size, 400/size);
				}
			}
			if (size <= 100) {
				g2.setColor(Color.GRAY);
				for (int i = 0; i < size + 1; i++) {
					g2.drawLine(i * 400 / size, 0, i * 400 / size, 400);
					g2.drawLine(0, i * 400 / size, 400, i * 400 / size);
				}
			}

		}
	}
	
}
class Lifer {
	Color[][] pixels;
	boolean mix = false;
	boolean vs = false;
	boolean choice = false;
	Color col = null;
	
	public void update(Color[][] pixels){
		this.pixels=pixels;
	}
	
	public Lifer(int size,Color[][] pixels,int type){
		
		if(type==1)mix = true;
		else vs = true;
		
		this.pixels=pixels;
	}
	public Lifer(int size,Color[][] pixels,Color col){
		this.col = col;
		choice = true;
		this.pixels = new Color[size][size];
		this.pixels=pixels;
	}
	
	public int rowCount(int row) {
		int count = 0;
		for (int i = 0; i < pixels[row].length; i++) {
			if(!pixels[row][i].equals(Color.WHITE))
				count++;
		}
		
		return count;
	}
	
	public int colCount(int col) {
		int count = 0;
		for (int i = 0; i < pixels.length; i++) {
			if(!pixels[i][col].equals(Color.WHITE))
				count++;
		}
		
		return count;
	}
	
	
	
	public int totalCount() {
		int count = 0;
		for (int i = 0; i < pixels.length; i++) {
			count+= rowCount(i);
		}
		return count;
	}
	
	
	
	public void runLife(int numGenerations){
		int gen = numGenerations;
		while(gen>0){
			Color[][] copy = copy();	
			for (int i = 0; i < copy.length; i++) {
				for (int j = 0; j < copy[0].length; j++) {
					copy[i][j]= test(i,j);
				}
			}
			pixels = copy;
			gen--;
		}
		
		
		
		
		
	}
	
	private Color[][] copy() {
		Color[][] fun = new Color[pixels.length][pixels[0].length];
		for (int i = 0; i < pixels.length; i++) {
			for (int j = 0; j < pixels[0].length; j++) {
				fun[i][j]=pixels[i][j];
			}
		}	
		return fun;
	}

	public Color test(int row, int col) {
		switch (friend(row, col)) {

		case 2:
			
			if(!pixels[row][col].equals(Color.WHITE)){
				if(vs){
					return friendVs(row,col);
				}
				if(mix){
					return friendMix(row,col);
				}
				return pixels[row][col];
			};
			return Color.WHITE;
		
		case 3:
			System.out.println(row+"{}"+col);
			if(vs){
				return friendVs(row,col);
			}
			if(mix){
				return friendMix(row,col);
			}
			else{
				return this.col;
			}

		default:
			return Color.WHITE;
		}
	}
	
	
	public int friend(int row, int col) {
		int count = 0;
		int rowM[] = { -1, -1, -1, 0, 1, 1, 1, 0 };
		int colM[] = { -1, 0, 1, 1, 1, 0, -1, -1 };
		for (int i = 0; i < 8; i++) {
			int r = row + rowM[i];
			int c = col + colM[i];
			if (r > -1 && r < pixels.length && c > -1 && c < pixels[1].length) {
				if (!(pixels[r][c].equals(Color.WHITE))){
					count++;
					System.out.println(row+"~"+col);
				}
			}
		}
		return count;
	}

	public Color friendMix(int row, int col) {
		int count = 0;
		int re = 0;
		int g = 0;
		int b = 0;
		int rowM[] = { -1, -1, -1, 0, 1, 1, 1, 0 };
		int colM[] = { -1, 0, 1, 1, 1, 0, -1, -1 };
		for (int i = 0; i < 8; i++) {
			int r = row + rowM[i];
			int c = col + colM[i];
			if (r > -1 && r < pixels.length && c > -1 && c < pixels[1].length) {
				if (!(pixels[r][c].equals(Color.WHITE))){
					
					count++;
					b += pixels[r][c].getBlue();
					g += pixels[r][c].getGreen();
					re += pixels[r][c].getRed();
					System.out.println(re+","+g+","+b
							+"-"+count);
				}
			}
		}
		System.out.println(re/count+","+g/count+","+b/count
				+"-"+count);
		return new Color(re/count,g/count,b/count);
	}
	public Color friendVs(int row, int col) {
		ArrayList<Color>list= new ArrayList<Color>();
		ArrayList<Integer>counts = new ArrayList<Integer>();
		int rowM[] = { -1, -1, -1, 0, 1, 1, 1, 0 };
		int colM[] = { -1, 0, 1, 1, 1, 0, -1, -1 };
		for (int i = 0; i < 8; i++) {
			int r = row + rowM[i];
			int c = col + colM[i];
			if (r > -1 && r < pixels.length && c > -1 && c < pixels[1].length) {
				if (!pixels[r][c].equals(Color.WHITE)){
					if(list.contains(pixels[r][c])){
						int index = list.indexOf(pixels[r][c]);
						counts.set(index,counts.get(index)+1);
					}
					else{
						list.add(pixels[r][c]);
						counts.add(1);
					}
				}
			}
		}
		int large = 0;
		int indexL = 0;
		for(int i = 0; i<counts.size();i++){
			if(counts.get(i)>large){
				large = counts.get(i);
				indexL=i;
			}
		}
		return list.get(indexL);
	}
	public void fillWhite(Color[][] pixels){
		for (int i = 0; i < pixels.length; i++) {
			for (int j = 0; j < pixels[0].length; j++) {
				pixels[i][j]=Color.WHITE;
			}
		}
	}
	
	
	
	
	
	
}

