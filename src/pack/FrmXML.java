package pack;
import javax.swing.JButton;
import javax.swing.JTextPane;

import javax.swing.WindowConstants;
import javax.swing.SwingUtilities;


/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class FrmXML extends javax.swing.JFrame {
	private JButton btnAdd;
	private JButton btnDelete;
	private JTextPane txtNode;
	private JButton btnQuery;
	private JButton btnEdit;

	/**
	* Auto-generated main method to display this JFrame
	*/
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				FrmXML inst = new FrmXML();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});
	}
	
	public FrmXML() {
		super();
		initGUI();
	}
	
	private void initGUI() {
		try {
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			getContentPane().setLayout(null);
			{
				btnAdd = new JButton();
				getContentPane().add(btnAdd);
				btnAdd.setText("\u6dfb\u52a0\u7ed3\u70b9");
				btnAdd.setBounds(142, 27, 62, 24);
			}
			{
				btnEdit = new JButton();
				getContentPane().add(btnEdit);
				btnEdit.setText("\u4fee\u6539\u7ed3\u70b9");
				btnEdit.setBounds(246, 27, 62, 24);
			}
			{
				btnDelete = new JButton();
				getContentPane().add(btnDelete);
				btnDelete.setText("\u5220\u9664\u7ed3\u70b9");
				btnDelete.setBounds(363, 27, 62, 24);
			}
			{
				btnQuery = new JButton();
				getContentPane().add(btnQuery);
				btnQuery.setText("\u67e5\u770b\u7ed3\u70b9");
				btnQuery.setBounds(37, 27, 67, 23);
			}
			{
				txtNode = new JTextPane();
				getContentPane().add(txtNode);
				txtNode.setBounds(39, 89, 386, 169);
			}
			pack();
			this.setSize(504, 336);
		} catch (Exception e) {
		    //add your error handling code here
			e.printStackTrace();
		}
	}

}
