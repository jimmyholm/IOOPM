package roguelike;

import javax.swing.JPanel;
import javax.swing.JLabel;

public class InfoPanel extends JPanel {
	private static final long serialVersionUID = -5957320065761029043L;
	
	private static InfoPanel Instance = null;
	private String Description = "";
	private JLabel Desc;
	private JLabel Health;
	private JLabel Offense;
	private JLabel Defense;
	private JLabel Weapon;
	private JLabel Armor;
	private JLabel Shield;
	private JLabel PotionCount;
	private JLabel HasKey;
	public static InfoPanel GetInstance() {
		if(Instance == null)
			Instance = new InfoPanel();
		return Instance;
	}
	
	public void SetDescription(String Desc) {
		Description = Desc;
	}
	
	public void Update() {
		Health.setText("Health: " + Player.GetInstance().GetStats().GetString("health") + "/" + Player.GetInstance().GetStats().GetString("maxhealth"));
		Offense.setText("Offense: " + Player.GetInstance().GetStats().GetString("offense"));
		Defense.setText("Offense: " + Player.GetInstance().GetStats().GetString("offense"));
		Desc.setText(Description);
		//Description = "";
		repaint();
	}
	public InfoPanel() {
		setLayout(null);
		Health 		= new JLabel();
		Health.setBounds(new java.awt.Rectangle(0, 0, 100, 12));
		Health.setForeground(java.awt.Color.white);
		add(Health);
		Offense 	= new JLabel();
		Offense.setBounds(new java.awt.Rectangle(0, 12, 100, 12));
		Offense.setForeground(java.awt.Color.white);
		add(Offense);
		Defense 	= new JLabel();
		Defense.setBounds(new java.awt.Rectangle(0, 24, 100, 12));
		Defense.setForeground(java.awt.Color.white);
		add(Defense);
		Weapon 		= new JLabel();
		Weapon.setBounds(new java.awt.Rectangle(0, 38, 100, 12));
		Weapon.setForeground(java.awt.Color.white);
		add(Weapon);
		Armor 		= new JLabel();
		Armor.setBounds(new java.awt.Rectangle(0, 52, 100, 12));
		Armor.setForeground(java.awt.Color.white);
		add(Armor);
		Shield 		= new JLabel();
		Shield.setBounds(new java.awt.Rectangle(0, 64, 100, 12));
		Shield.setForeground(java.awt.Color.white);
		add(Shield);
		PotionCount = new JLabel();
		PotionCount.setBounds(new java.awt.Rectangle(0, 76, 100, 12));
		PotionCount.setForeground(java.awt.Color.white);
		add(PotionCount);
		HasKey 		= new JLabel();
		HasKey.setBounds(new java.awt.Rectangle(0, 90, 100, 12));
		HasKey.setForeground(java.awt.Color.white);
		add(HasKey);
		Desc 		= new JLabel();
		Desc.setBounds(new java.awt.Rectangle(0, 104, 100, 656));
		Desc.setForeground(java.awt.Color.white);
		add(Desc);
	}

}
