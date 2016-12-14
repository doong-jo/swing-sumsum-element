import java.awt.*;
import javax.swing.*;

//import MenuScene.GameMListener;

import java.awt.event.*;

public class HowtoScene extends JPanel {
	private final int WIDTH = 800, HEIGHT = 600; 
	private JButton btnStartGame; // to start game
	private JButton btnLeft;	//instance data to move before scene
	private JButton btnRight;	//instace data to move next scene
	private JLabel bkgndimgHow;
	// private GameListener gameL;
	private int imgFlag = 1;//Flag number can declare present page , 
	// 2. declaration of the listener object
	private GameMListener gameML;

	public HowtoScene() {

		setBounds(0, 0, WIDTH, HEIGHT);
		setBackground(Color.white);
		setLayout(null);
		// 2. creating of the listener object
		gameML = new GameMListener();
		// to start game
		btnStartGame = new JButton(new ImageIcon("imgs/start.png")); // button's image
		btnStartGame.setBorderPainted(false);
		btnStartGame.setContentAreaFilled(false);
		btnStartGame.setFocusPainted(false);
		btnStartGame.setBounds(520, 480, 300, 100);
		// 3. add the listener object to the component
		btnStartGame.addMouseListener(gameML);
		add(btnStartGame);

		// move to before scene
		btnLeft = new JButton(new ImageIcon("imgs/btnLeft.png"));
		btnLeft.setVisible(false);
		btnLeft.setBorderPainted(false);
		btnLeft.setContentAreaFilled(false);
		btnLeft.setFocusPainted(false);
		btnLeft.setBounds(10, 300, 50 , 50);
		// 3. add the listener object to the component
		btnLeft.addMouseListener(gameML);
		add(btnLeft);//add left button

		// move to next scene

		btnRight = new JButton(new ImageIcon("imgs/btnRight.png"));
		btnRight.setBorderPainted(false);
		btnRight.setContentAreaFilled(false);
		btnRight.setFocusPainted(false);
		btnRight.setBounds(690, 300, 100, 100);
		// 3. add the listener object to the component
		btnRight.addMouseListener(gameML);
		add(btnRight);//add right button
		
		
	

	ImageIcon img1= new ImageIcon("imgs/howtoBack1.png");	//it is first page
	bkgndimgHow = new JLabel("", img1, SwingConstants.CENTER);
	bkgndimgHow.setVisible(true);//initial state
	bkgndimgHow.setBounds(0,0 ,800,600);
	bkgndimgHow.setHorizontalAlignment(SwingConstants.CENTER);
	bkgndimgHow.setVerticalAlignment(SwingConstants.CENTER);
	add(bkgndimgHow);//add back ground image.
	
	}
	
	public void Init(){
		btnLeft.setVisible(false);
		btnRight.setVisible(true);
		imgFlag = 1;
	}

	public void moveToBefore() {
		if (imgFlag == 1) {//if this is first page, it is not changed
			btnLeft.setVisible(false);//because it is first page
			btnRight.setVisible(true);//to change page
			bkgndimgHow.setIcon(new ImageIcon("imgs/howtoBack1.png"));//set to page 1

			imgFlag = 1;
		} else if (imgFlag == 2) {//codition about present page
			btnLeft.setVisible(false);//because it is first page
			btnRight.setVisible(true);
			bkgndimgHow.setIcon(new ImageIcon("imgs/howtoBack1.png"));//set to page 1

			imgFlag = 1;//change page condition after show present page
		} else if (imgFlag == 3) {//codition about present page
			btnLeft.setVisible(true);
			btnRight.setVisible(true);
			bkgndimgHow.setIcon(new ImageIcon("imgs/howtoBack2.png"));//set to page 2

			imgFlag = 2;//change page condition after show present page
		}
	}

	public void moveToNext() {
		if (imgFlag == 1) {//codition about present page
			btnLeft.setVisible(true);
			btnRight.setVisible(true);
			bkgndimgHow.setIcon(new ImageIcon("imgs/howtoBack2.png")); //set to page 2

			imgFlag = 2;//change page condition after show present page
		} else if (imgFlag == 2) {//codition about present page
			btnLeft.setVisible(true);
			btnRight.setVisible(false);//because it is last page
			bkgndimgHow.setIcon(new ImageIcon("imgs/howtoBack3.png"));//set to page 3

			imgFlag = 3;//change page condition after show present page
		} else if (imgFlag == 3){// if this is last page, it is not changed
			btnLeft.setVisible(true);
			btnRight.setVisible(false);//because it is last page
			bkgndimgHow.setIcon(new ImageIcon("imgs/howtoBack3.png"));//set to page 3

			imgFlag = 3;// it is staying 
		}
	}

	public void moveStartGame() {//to move Game Scene
		Scene.getInstance().selectScene("Game");
	}

	
	private class GameMListener implements MouseListener {//to recognize Mouse function
		public void mouseClicked(MouseEvent event) {
		}

		public void mousePressed(MouseEvent event) {//when users press the mouse 
			Object obj = event.getSource();// to recognize kind of button 

			if (obj == btnStartGame) {
				btnStartGame.setIcon(new ImageIcon("imgs/start_c.png"));//when it is pressed, button is changed to dark color
			} else if (obj == btnLeft) {
				btnLeft.setIcon(new ImageIcon("imgs/btnLeft_down.png"));
			} else if (obj == btnRight) {
				btnRight.setIcon(new ImageIcon("imgs/btnRight_down.png"));
			}
		}

		public void mouseReleased(MouseEvent event) {//when users release the mouse 
			Object obj = event.getSource();// to recognize kind of button 

			if (btnStartGame == obj) { //start game button is clicked
				moveStartGame();// move to game scene
			}
			if (btnLeft == obj) {//left button is clicked
				moveToBefore();//move to before page
			}
			if (btnRight == obj) {//right buttin is clicked
				moveToNext();//move to next page
			}
			btnStartGame.setIcon(new ImageIcon("imgs/start.png"));//when it is released button is changed to original color
			btnLeft.setIcon(new ImageIcon("imgs/btnLeft.png"));
			btnRight.setIcon(new ImageIcon("imgs/btnRight.png"));

		}

		public void mouseEntered(MouseEvent event) {
		}

		public void mouseExited(MouseEvent event) {
		}
	}

}