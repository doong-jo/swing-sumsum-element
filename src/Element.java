import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Element extends JLabel
{	
	private int nFlag;				//���Ҹ� �ĺ��� �� �ִ� ��
	private EMListener EML;			//���콺 ������
	private GameScene gameScene;	//Game Scene�� �ν��Ͻ��� �����ְ� �ν��Ͻ��� �̿��� �Լ��� ȣ���Ѵ�.
	private boolean IsSlot;			//���Կ� ��ġ���θ� ����
	private int nSlotPos;			//������ ��ġ�� ���� (-1:���Կ� ��ġ�� ���� ����,0:���� ����, 1:�����ʽ���)
	private Point nListPos;			//GameScene������ ����Ʈ������ ��ǥ/EndingScene������ ������ ��ǥ
	private int nScale;				//�̹��� ũ�⸦ �����ϴ� �����ϰ�. X,Y �����ϸ� �ʱⰪ�� �̹��� ũ���.
	
	public Element(int flag){
		nSlotPos = -1;
		IsSlot = false;
		nFlag = flag;
		nListPos = new Point(0, 0);
		
		EML = new EMListener();
		addMouseListener(EML);
		
		ImageIcon icon = new ImageIcon("imgs/"+nFlag+".png");
		setIcon(icon);
	}
	
	public EMListener getListener() {
		return EML;
	}
	public void setFlag(int flag){
		nFlag = flag;
	}
	public void setIsSlot(boolean IsSet){
		IsSlot = IsSet;
	}
	public void setSlotPos(int nPos){
		nSlotPos = nPos;	
	}
	public void setScale(int scale){
		nScale = scale;
	}
	public int getScale(){
		return nScale;
	}
	public int getFlag(){
		return nFlag;	
	}
	public boolean getIsSlot(){
		return IsSlot;	
	}
	public int getSlotPos(){
		return nSlotPos;	
	}
	
	public void setgameScene(GameScene scene){
		gameScene = scene;
	}
	
	public void setListPos(int x, int y){
		nListPos.x = x;
		nListPos.y = y;	
	}
	
	public void setListPosX(int x){
		nListPos.x = x;
	}
	
	public void setListPosY(int y){
		nListPos.y = y;
	}
	
	public Point getListPos() {
		return nListPos;
	}
	
	public int getListPosX() {
		return nListPos.x;	
	}
	
	public int getListPosY() {
		return nListPos.y;
	}
	
	public GameScene getgameScene() {
		return gameScene;
	}
	
	private class EMListener implements MouseListener
	{
		public void mouseClicked(MouseEvent event) {}
		public void mousePressed(MouseEvent event) {
			//Pressed�ɶ����� setSlot�Ǵ� dropSlot�� �Ǹ� ���� ������Ų��.
			IsSlot = !IsSlot;
			
			if( IsSlot ) {
				gameScene.setSlot(nFlag);
			}
			if( !IsSlot ) {
				gameScene.dropSlot(nFlag);
			}
		}
		public void mouseReleased(MouseEvent event) {}
		public void mouseEntered(MouseEvent event) {
			System.out.println(nFlag);
		}
		public void mouseExited(MouseEvent event) {}
	}
}