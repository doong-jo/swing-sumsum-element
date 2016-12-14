import java.awt.*;
import javax.swing.*;
import java.awt.event.*;


//�� ���� �ν��Ͻ��� ���� �ִ� Ŭ����
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
	
	//�̱��� ������ ����ϰ� ���� ��ü�� ����ȴ�.
	private static class Singleton{
		private static final Scene instance = new Scene();	
	}
	
	//�̱����� Scene �ν��Ͻ��� ��ȯ��. public static���� ���������� ����� �����ϴ�.
	public static Scene getInstance() {
		return Singleton.instance;
	}
	
	//������ ���� ȭ�鿡 �����ش�.
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