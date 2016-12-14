import java.awt.*;
import javax.swing.*;
import java.awt.event.*;


//각 씬을 인스턴스로 갖고 있는 클래스
public class Scene extends JPanel
{	
	private MenuScene	menuPanel;
	private GameScene	gamePanel;
	private EndingScene	endingPanel;
	private HowtoScene	HowtoPanel;
	
	public Scene() {
		setPreferredSize(new Dimension(800,600));
		setBackground(Color.white);
		setLayout(null);
		
		menuPanel = new MenuScene();
		gamePanel = new GameScene();
		endingPanel = new EndingScene();
		HowtoPanel = new HowtoScene();
		
		add(menuPanel);
		add(gamePanel);
		add(endingPanel);
		add(HowtoPanel);
		
		selectScene("Menu");
	}
	
	public GameScene getgameScene(){
		return gamePanel;
	}
	public MenuScene getMenuScene(){
		return menuPanel;
	}
	public EndingScene getEndingScene(){
		return endingPanel;
	}
	public HowtoScene getHowtoScene(){
		return HowtoPanel;
	}
	
	//싱글톤 패턴을 사용하고 단일 객체로 선언된다.
	private static class Singleton{
		private static final Scene instance = new Scene();	
	}
	
	//싱글톤의 Scene 인스턴스를 반환함. public static으로 전역적으로 사용이 가능하다.
	public static Scene getInstance() {
		return Singleton.instance;
	}
	
	//선택한 씬만 화면에 보여준다.
	public void selectScene(String str){
		switch(str){
			case "Menu":
			menuPanel.setVisible(true);
			gamePanel.setVisible(false);
			endingPanel.setVisible(false);
			HowtoPanel.setVisible(false);
			break;
			
			case "Game":
			menuPanel.setVisible(false);
			gamePanel.Init();
			gamePanel.setVisible(true);
			endingPanel.setVisible(false);
			HowtoPanel.setVisible(false);
			break;
			
			case "Ending":
			menuPanel.setVisible(false);
			gamePanel.setVisible(false);
			endingPanel.setVisible(true);
			endingPanel.Init();
			HowtoPanel.setVisible(false);
			break;
			
			case "Howto":
			menuPanel.setVisible(false);
			gamePanel.setVisible(false);
			endingPanel.setVisible(false);
			HowtoPanel.setVisible(true);
			HowtoPanel.Init();
			break;
		}
	}
} 