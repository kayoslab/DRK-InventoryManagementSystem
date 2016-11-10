package presenter;
import model.DatabaseWriteManager;
import model.PasswordManager;
import java.awt.EventQueue;
import javax.swing.JFrame;


public class SettingsPresenter extends Presenter {
	private PasswordManager passwordManager = new PasswordManager();
	private DatabaseWriteManager writeManager = new DatabaseWriteManager();
	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SettingsPresenter window = new SettingsPresenter();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SettingsPresenter() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
