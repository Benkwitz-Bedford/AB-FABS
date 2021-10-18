package heatmaps;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JFormattedTextField;

import singleton_holders.SingletonHolder;
import singleton_holders.SingletonSanitisationFields;
import environment_data_holders.Posi;
import environment_data_holders.PositionGrid;

public class HeatMapFrame  extends JFrame implements ActionListener, MouseWheelListener{
	
	int heatX = 1500;
	int heatY = 1500;
	HeatMapPanel heat = new HeatMapPanel(heatX+10,heatY+10);
	Posi posGrid = null;
	JLabel img = new JLabel();
	int scrollIncrement = 10;
	
	NumberFormat doub = new DecimalFormat("#0.00"); 
	JLabel flatLabel = new JLabel("Flat Value:");
	JFormattedTextField flatField = new JFormattedTextField(NumberFormat.INTEGER_FIELD);
	JButton flatButton = new JButton("Flat");
	
	JLabel gammaLabel = new JLabel("Gamma Value:");
	JFormattedTextField gammaField = new JFormattedTextField(doub);
	JButton gammaButton = new JButton("gamma");

	JButton offButton = new JButton("Offload");
	JCheckBox invert = new JCheckBox("Invert");
	private JComboBox<String> c = new JComboBox<String>();
	private JComboBox<String> c2 = new JComboBox<String>();
	Dimension heatSize= new Dimension(800,800);
	JScrollPane scroll = null;
	Posi posi = null;
	int k1 = 0;
	JPanel image = new JPanel();
	
	public HeatMapFrame(int x, int y)
	{
		Dimension fieldSize = new Dimension(100,20);
		Dimension textFieldSize = new Dimension(120,20);
		Dimension screenSize =  Toolkit.getDefaultToolkit().getScreenSize();
		x = screenSize.width/2;
		y = (int) (screenSize.height/1.5);
		if(x > 810)
		{
			x =810;
		}
		if(y > 900)
		{
			y =900;
		}
		c.addItem("rgb = red through yellow");
		c.addItem("rbg = red through pink");
		c.addItem("grb = green through yellow");
		c.addItem("gbr = blue through pink");
		c.addItem("brg = green through blue");
		c.addItem("bgr = blue through teal");
		c.setPreferredSize(textFieldSize);
		c.setActionCommand("selected");
		c.addActionListener(this);
		
		
		c2.addItem("Movement");
		c2.addItem("Directional Turns");
		c2.addItem("Directional Constant");
		c2.addItem("Relative Turns");
		for(int i = 0; i < 16;i++)
		{
			c2.addItem("Angle "+i);
		}
		
		c2.setPreferredSize(textFieldSize);
		c2.setActionCommand("selected2");
		c2.addActionListener(this);
		
		invert.setActionCommand("invert");
		invert.setPreferredSize(new Dimension(150,20));
		invert.addActionListener(this);
		
		flatLabel.setPreferredSize(fieldSize);
		flatField.setPreferredSize(textFieldSize);
		flatButton.setPreferredSize(fieldSize);
		
		gammaLabel.setPreferredSize(fieldSize);
		gammaField.setPreferredSize(textFieldSize);
		gammaButton.setPreferredSize(fieldSize);
		offButton.setPreferredSize(fieldSize);
		
		flatField.setToolTipText("the flat top end value (set to 0 for deafult)");
		flatField.setText("0");
		gammaField.setToolTipText("gamma transformed value (set 1 for deafult)");
		gammaField.setText("1");
		
		flatButton.setActionCommand("flatCommit");
		gammaButton.setActionCommand("gammaCommit");
		offButton.setActionCommand("offLoad");
		
		flatButton.addActionListener(this);
		gammaButton.addActionListener(this);
		offButton.addActionListener(this);
		
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		this.setPreferredSize(new Dimension(x,y));
		//this.setSize(x, y);
		heat = new HeatMapPanel(heatSize.width,heatSize.width);
		img.setSize(heatSize);
		img.addMouseWheelListener(this);
		this.setLayout(new GridBagLayout());
		GridBagConstraints con = new GridBagConstraints();
		
		con = new GridBagConstraints();
		con.gridx = 0;
		con.gridy = 0;
		con.gridwidth = 6;
		con.gridheight = 6;
		con.fill = GridBagConstraints.BOTH;
		image.add(img);
		scroll = new JScrollPane(image);
		scroll.setPreferredSize(new Dimension(x-50,(int) (y-110)));
		scroll.getVerticalScrollBar().setMinimum(scroll.getHeight()/2);
		scroll.getHorizontalScrollBar().setMinimum(scroll.getWidth()/2);
		//scroll.setSize(new Dimension(x+100,y+100));
		//con.weighty = 10;
	    getContentPane().add(scroll, con);
		
		con = new GridBagConstraints();
		con.gridx = 0;
		con.gridy = 7;
		con.gridwidth = 1;
		con.gridheight = 1;
		getContentPane().add(flatLabel, con);
		con = new GridBagConstraints();
		con.gridx = 1;
		con.gridy = 7;
		con.gridwidth = 1;
		con.gridheight = 1;
		getContentPane().add(flatField, con);
		con = new GridBagConstraints();
		con.gridx = 2;
		con.gridy = 7;
		con.gridwidth = 1;
		con.gridheight = 1;
		getContentPane().add(flatButton, con);
		con = new GridBagConstraints();
		con.gridx = 3;
		con.gridy = 7;
		con.gridwidth = 1;
		con.gridheight = 1;
		getContentPane().add(invert, con);
		con = new GridBagConstraints();
		con.gridx = 4;
		con.gridy = 7;
		con.gridwidth = 1;
		con.gridheight = 1;
		getContentPane().add(c, con);

		con = new GridBagConstraints();
		con.gridx = 5;
		con.gridy = 7;
		con.gridwidth = 1;
		con.gridheight = 1;
		getContentPane().add(c2, con);
		
		con = new GridBagConstraints();
		con.gridx = 0;
		con.gridy = 8;
		con.gridwidth = 1;
		con.gridheight = 1;
		getContentPane().add(gammaLabel, con);
		con = new GridBagConstraints();
		con.gridx = 1;
		con.gridy = 8;
		con.gridwidth = 1;
		con.gridheight = 1;
		getContentPane().add(gammaField, con);
		con = new GridBagConstraints();
		con.gridx = 2;
		con.gridy = 8;
		con.gridwidth = 1;
		con.gridheight = 1;
		getContentPane().add(gammaButton, con);
		con = new GridBagConstraints();
		con.gridx = 3;
		con.gridy = 8;
		con.gridwidth = 1;
		con.gridheight = 1;
		getContentPane().add(offButton, con);
		
		scroll.setViewportView(image);
		
		this.pack();
		
		/*this.add(heat);
		this.add(flatLabel);
		this.add(flatField);
		this.add(flatButton);
		this.add(gammaLabel);
		this.add(gammaField);
		this.add(gammaButton);*/
		
	}

	public void generate(Posi posi2, int k) {
		this.setVisible(true);
		posGrid = posi2;
		heat.drawPureMap(posi2,k, 1);
		posi = posi2;
		k1 = k;
		img.setIcon(new ImageIcon(heat.getBuffer()));
		
	}

	public void actionPerformed(ActionEvent arg0) {
		sanitiseFields();
		cleanEmpty();
		if(arg0.getActionCommand().equals("flatCommit"))
		{
			drawMap();
			//heat.drawPureMap(posGrid, Integer.parseInt(flatField.getText().replace(",", "")),1);
			//img.setIcon(new ImageIcon(heat.getBuffer()));
		}
		
		if(arg0.getActionCommand().equals("gammaCommit"))
		{
			drawMap();
			//heat.drawPureMap(posGrid, Integer.parseInt(flatField.getText().replace(",", "")), Double.parseDouble(gammaField.getText().replace(",", "")));
			//img.setIcon(new ImageIcon(heat.getBuffer()));
		}
		
		if(arg0.getActionCommand().equals("offLoad"))
		{
			heat.setTarget(c2.getSelectedIndex());
			final JFileChooser fc = new JFileChooser();
			fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			fc.setCurrentDirectory(new File(System.getProperty("user.dir")));
			fc.setDialogTitle("Location");
			fc.showOpenDialog(null);
			File file = fc.getSelectedFile();
			File iterImage = new File(file.getAbsolutePath()+"//"+"heat.png");
			//heat.drawPureMap(posGrid, Integer.parseInt(flatField.getText().replace(",", "")), Double.parseDouble(gammaField.getText().replace(",", "")));
			BufferedImage bi = heat.getHeat().getBuffer();
		    try {
				ImageIO.write(bi, "png", iterImage);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(arg0.getActionCommand().equals("invert"))
		{
			heat.setTarget(c2.getSelectedIndex());
			SingletonHolder.setInvertHeat(invert.isSelected());
			heat.drawPureMap(posGrid, Integer.parseInt(flatField.getText().replace(",", "")), Double.parseDouble(gammaField.getText().replace(",", "")));
			img.setIcon(new ImageIcon(heat.getBuffer()));
		}
		if(arg0.getActionCommand().equals("selected"))
		{
			heat.setTarget(c2.getSelectedIndex());
			SingletonHolder.setColSelector(c.getSelectedIndex());
			System.out.println(c.getSelectedIndex());
			heat.drawPureMap(posGrid, Integer.parseInt(flatField.getText().replace(",", "")), Double.parseDouble(gammaField.getText().replace(",", "")));
			img.setIcon(new ImageIcon(heat.getBuffer()));
		}
		
		if(arg0.getActionCommand().equals("selected2"))
		{
			heat.setTarget(c2.getSelectedIndex());
			SingletonHolder.setColSelector(c.getSelectedIndex());
			System.out.println(c2.getSelectedIndex());
			heat.drawPureMap(posGrid, Integer.parseInt(flatField.getText().replace(",", "")), Double.parseDouble(gammaField.getText().replace(",", "")));
			img.setIcon(new ImageIcon(heat.getBuffer()));
		}
		
	}
	
	private void sanitiseFields() {
		flatField.setText(""+SingletonSanitisationFields.sanitise(SingletonSanitisationFields.getMinHeatDeafult(),SingletonSanitisationFields.getMaxHeatDeafult(), Integer.parseInt(flatField.getText().replace(",", ""))));
		gammaField.setText(""+SingletonSanitisationFields.sanitiseDouble(SingletonSanitisationFields.getMinGammaDeafult(),SingletonSanitisationFields.getMaxGammaDeafult(), Double.parseDouble(gammaField.getText().replace(",", ""))));
		
		
	}

	private void cleanEmpty() {

		if(flatField.getText().replace(",", "").isEmpty()) { flatField.setText("0");}
		if(gammaField.getText().replace(",", "").isEmpty()) { gammaField.setText("0");}
		
	}
	public void drawMap()
	{
		
		heat = new HeatMapPanel(heatSize.width,heatSize.width);

		heat.setTarget(c2.getSelectedIndex());
		//heat.setSize(heatSize);
		//heat.drawPureMap(posi,k1, 1);
		//need to get the top left and bottom right of x y frame with bar  the relation between heat size 
		Rectangle dim = img.getVisibleRect();
		//System.out.println(img.getWidth());
		//System.out.println("dim "+dim);
		//ratio of img width to 
		
		double imgX = img.getWidth();
		double imgY = img.getHeight();
		double size = SingletonHolder.getSize();
		double xRatio = size/imgX;
		double yRatio = size/imgY;
		int x1 = (int) (dim.x*xRatio);
		int y1 = (int) (dim.y*yRatio);
		int x2 = (int)((dim.x+dim.getWidth())*xRatio);
		int y2 = (int)((dim.y+dim.getHeight())*yRatio);
		//get scroll bar size, get ratio of scroll size to visual area, add 
		//System.out.println("drawing "+x1+" "+y1+" "+x2+" "+y2);
		heat.drawPureMap(posGrid, Integer.parseInt(flatField.getText().replace(",", "")), Double.parseDouble(gammaField.getText().replace(",", "")));
		//heat.drawPureSubMap(posi,Integer.parseInt(flatField.getText().replace(",", "")), Double.parseDouble(gammaField.getText().replace(",", "")),x1,y1,x2,y2);
		img.setIcon(new ImageIcon(heat.getBuffer()));
		
		image.removeAll();
		image.add(img);
		image.revalidate();
		image.repaint();
		//System.out.println("wheel up "+heatSize);
	}
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		int scrollIncrement = 10;
		if (e.getScrollType() == MouseWheelEvent.WHEEL_UNIT_SCROLL) {
			if(e.getWheelRotation() < 0)
			{
				heatSize = new Dimension(heatSize.width+(heatSize.width/100*scrollIncrement),heatSize.width+(heatSize.width/100*scrollIncrement));
				if(heatSize.getWidth()>3000)
				{
					heatSize.setSize(3000, heatSize.getHeight());
				}else
				{

					scroll.getHorizontalScrollBar().setValue(scroll.getHorizontalScrollBar().getValue()+(scroll.getHorizontalScrollBar().getValue()/100*scrollIncrement));
				}
				if(heatSize.getHeight()>3000)
				{
					heatSize.setSize(heatSize.getWidth(),3000);
				}else
				{
					scroll.getVerticalScrollBar().setValue(scroll.getVerticalScrollBar().getValue()+(scroll.getVerticalScrollBar().getValue()/100*scrollIncrement));
					
				}
				drawMap();
				//img.setSize(new Dimension(img.getPreferredSize().width-scrollIncrement,img.getPreferredSize().height-scrollIncrement));
			}else
			{
				heatSize = new Dimension(heatSize.width-(heatSize.width/100*scrollIncrement),heatSize.width-(heatSize.width/100*scrollIncrement));
				drawMap();
				scroll.getVerticalScrollBar().setValue(scroll.getVerticalScrollBar().getValue()-scrollIncrement/2);
				scroll.getHorizontalScrollBar().setValue(scroll.getHorizontalScrollBar().getValue()-scrollIncrement/2);
				//System.out.println("wheel down "+heatSize);
				//img.setSize(new Dimension(img.getPreferredSize().width+scrollIncrement,img.getPreferredSize().height+scrollIncrement));
				
			}
		}
		
	}

}
