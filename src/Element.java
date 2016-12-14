import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Element extends JLabel
{	
	private int nFlag;				//원소를 식별할 수 있는 값
	private EMListener EML;			//마우스 리스너
	private GameScene gameScene;	//Game Scene을 인스턴스로 갖고있고 인스턴스를 이용해 함수를 호출한다.
	private boolean IsSlot;			//슬롯에 배치여부를 저장
	private int nSlotPos;			//원소의 위치를 저장 (-1:슬롯에 위치해 있지 않음,0:왼쪽 슬롯, 1:오른쪽슬롯)
	private Point nListPos;			//GameScene에서는 리스트에서의 좌표/EndingScene에서는 원소의 좌표
	private int nScale;				//이미지 크기를 결정하는 스케일값. X,Y 동일하며 초기값은 이미지 크기다.
	
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
			//Pressed될때마다 setSlot또는 dropSlot이 되며 값을 반전시킨다.
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