package roguelike;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextArea;

public class InfoPanel extends JPanel {
	private static final long serialVersionUID = -5957320065761029043L;
	
	private static InfoPanel Instance = null;
	private String Description = "";
	private JTextArea Desc;
	private JLabel Health;
	private JLabel Offense;
	private JLabel Defense;
	private JLabel Dexterity;
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
	
	public void SetDescription(String Description) {
		this.Description = Description;
	}
	
	public void Update() {
		Player P = Player.GetInstance();
		Stats S = P.GetStats();
		Health.setText("Health: " + S.GetString("health") + "/" + S.GetString("maxHealth"));
		Offense.setText("Offense: " + S.GetString("offense"));
		Defense.setText("Defense: " + S.GetString("defense"));
		Dexterity.setText("Dexterity: " + S.GetString("Dexterity"));
		Weapon.setText("Weapon: " + ((P.GetWeapon() != null) ? P.GetWeapon().GetWeaponType() + " (Off: " + P.GetWeapon().GetWeaponOffense() + ", Dex: " + P.GetWeapon().GetWeaponDexterity() +")" : "None"));
		Shield.setText("Shield: " + ((P.GetShield() != null) ? "Yes. (Def: " + P.GetShield().GetShieldDefense() +")" : "None"));
		Armor.setText("Armor: " + 	((P.GetArmor() != null) ? "Yes. (Def: " +P.GetArmor().GetArmorDefense() +")" : "None"));
		PotionCount.setText("Potions: " + ((P.GetPotionCount() != 0) ? P.GetPotionCount() : "None"));
		HasKey.setText("Has Key: " + ((P.HasKey() == true) ? "Yes." : "No"));
		String D = "Description: " + Description;
		Desc.setText(D);
		repaint();
	}
	public InfoPanel() {
		setLayout(null);
		Health 		= new JLabel();
		Health.setBounds(new java.awt.Rectangle(3, 0, 300, 12));
		Health.setForeground(java.awt.Color.white);
		add(Health);
		Offense 	= new JLabel();
		Offense.setBounds(new java.awt.Rectangle(3, 12, 300, 12));
		Offense.setForeground(java.awt.Color.white);
		add(Offense);
		Defense 	= new JLabel();
		Defense.setBounds(new java.awt.Rectangle(3, 24, 300, 12));
		Defense.setForeground(java.awt.Color.white);
		add(Defense);
		Dexterity   = new JLabel();
		Dexterity.setBounds(new java.awt.Rectangle(3, 36, 300, 12));
		Dexterity.setForeground(java.awt.Color.white);
		add(Dexterity);
		Weapon 		= new JLabel();
		Weapon.setBounds(new java.awt.Rectangle(3, 52, 300, 12));
		Weapon.setForeground(java.awt.Color.white);
		add(Weapon);
		Armor 		= new JLabel();
		Armor.setBounds(new java.awt.Rectangle(3, 64, 300, 12));
		Armor.setForeground(java.awt.Color.white);
		add(Armor);
		Shield 		= new JLabel();
		Shield.setBounds(new java.awt.Rectangle(3, 76, 300, 12));
		Shield.setForeground(java.awt.Color.white);
		add(Shield);
		PotionCount = new JLabel();
		PotionCount.setBounds(new java.awt.Rectangle(3, 90, 300, 12));
		PotionCount.setForeground(java.awt.Color.white);
		add(PotionCount);
		HasKey 		= new JLabel();
		HasKey.setBounds(new java.awt.Rectangle(3, 104, 300, 12));
		HasKey.setForeground(java.awt.Color.white);
		add(HasKey);
		Desc 		= new JTextArea();
		Desc.setBounds(new java.awt.Rectangle(3, 116, 300, 26*TileSet.GetInstance().GetTileHeight()-116));
		Desc.setForeground(java.awt.Color.white);
		Desc.setBackground(java.awt.Color.black);
		Desc.setWrapStyleWord(true);
		Desc.setLineWrap(true);
		add(Desc);
		
	}

}
