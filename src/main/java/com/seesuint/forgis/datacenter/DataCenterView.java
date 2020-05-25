package com.seesuint.forgis.datacenter;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.geotools.data.mysql.MySQLDataStoreFactory;

public class DataCenterView extends JFrame {

	// private int value=0;
	// static JProgressBar progressBar;
	static JButton btnLoad;
	static JComboBox cb_Lang;
	public JComboBox cb_coord;
	// static Icon imgIcon;
	JProgressBar p;
	Timer t;
	int i = 0;
	private JTable LayerLst;
	private JTable table;
	private JList listview;
	private JTextField txt_dbtype;
	private JTextField txt_ip;
	private JTextField txt_port;
	private JTextField txt_database;
	private JTextField txt_id;
	private JTextField txt_pass;
	private JTextField txt_sheme;
	private JButton btn_con_1;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				DataCenterView form = new DataCenterView();
				form.setVisible(true);

			}
		});
	}

	public DataCenterView() {

		super("ForGIS DataCenter Ver 1.0");
		setResizable(false);
		setBackground(UIManager.getColor("Button.darkShadow"));
		ConfigManager conf = new ConfigManager();
		DbManager db = new DbManager();
		CoordinateManager coordlst = new CoordinateManager();
		setSize(656, 652);
		setLocation(500, 280);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		final JLabel lblResult = new JLabel("Select Path:", JLabel.LEFT);

		final JLabel lblResult_nm = new JLabel("테이블 명:", JLabel.CENTER);

		final JTextField txt_table = new JTextField("", JTextField.CENTER);

		final JCheckBox cSpatial = new JCheckBox("공간인덱스 생성");
		JFrame frame = new JFrame("JOptionPane showMessageDialog example");
		final JLabel lbl_lang = new JLabel("인코딩:", JLabel.CENTER);

		final JLabel lbl_sSrs = new JLabel("좌표계선택:", JLabel.CENTER);
		JTextField txt_sSrs = new JTextField("", JTextField.CENTER);

		final JButton btnButton = new JButton("ShapeFile 선택");
		final JButton btnLoad = new JButton("데이터 베이스 업로드");
		final JButton btnNewLoad = new JButton("목록");
		final JButton btn_con = new JButton("연결테스트");
		final JButton btn_down_shp = new JButton("TO SHP");

		try {
			conf.conf(false, "UTF-8");
			String[] array = conf.SetLangList();

			List<String> columns = new ArrayList<String>();
			List<String[]> values = new ArrayList<String[]>();

			columns.add("테이블 명칭");
			cb_Lang = new JComboBox(array);

			Vector<String> coorSYS = coordlst.getCoordinateList();
			cb_coord = new JComboBox(coorSYS);

		//	String[] Layer = db.LayerList();
			JPanel panel = new JPanel(new BorderLayout());

			listview = new JList();
			JScrollPane listScroller = new JScrollPane();
			listScroller.setViewportView(listview);
			/**
			 * JScrollPane listScroller = new JScrollPane();
			 * listScroller.setViewportView(listview);
			 **/
			listScroller.setSize(100, 100);
			listScroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
			listScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

			listview.setLayoutOrientation(JList.VERTICAL);
			panel.add(listScroller);

			//listview.setVisibleRowCount(100);

			// JScrollPane jsp = new JScrollPane(listview);

			JLabel lblNewLabel = new JLabel("");

			JLabel label = new JLabel("");

			JLabel lblNewLabel_DB = new JLabel("기종:");

			txt_dbtype = new JTextField();
			txt_dbtype.setColumns(10);
			txt_dbtype.setText(conf.DbType);

			JLabel lblNewLabel_1 = new JLabel("IP:");

			txt_ip = new JTextField();
			txt_ip.setColumns(10);
			txt_ip.setText(conf.getHost());

			txt_port = new JTextField();
			txt_port.setColumns(10);

			txt_port.setText(conf.getPort());

			JLabel lblNewLabel_2 = new JLabel("Port:");

			JLabel lbl_database = new JLabel("데이터 베이스:");

			txt_database = new JTextField();
			txt_database.setColumns(10);
			txt_database.setText(conf.getDatabase());
			JLabel lblNewLabel_3 = new JLabel("ID:");

			txt_id = new JTextField();
			txt_id.setColumns(10);

			txt_id.setText(conf.getId());
			JLabel lblPassword = new JLabel("Password:");

			txt_pass = new JTextField();
			txt_pass.setColumns(10);
			txt_pass.setText(conf.getPassword());
			JLabel lbl_scheme = new JLabel("스키마:");

			txt_sheme = new JTextField();
			txt_sheme.setColumns(10);
			txt_sheme.setText(conf.getSchema());

			btn_con_1 = new JButton("연결테스트");

			JLabel lblNewLabel_4 = new JLabel("데이터베이스정보");
			lblNewLabel_4.setFont(new Font("굴림", Font.BOLD, 17));



			GroupLayout groupLayout = new GroupLayout(getContentPane());
			groupLayout.setHorizontalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
					.addGroup(groupLayout.createSequentialGroup()
						.addGap(28)
						.addComponent(lblResult, GroupLayout.PREFERRED_SIZE, 500, GroupLayout.PREFERRED_SIZE)
						.addGap(122)
						.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
							.addGroup(groupLayout.createSequentialGroup()
								.addComponent(label)
								.addGap(135))
							.addGroup(groupLayout.createSequentialGroup()
								.addComponent(lblNewLabel)
								.addGap(175))))
					.addGroup(groupLayout.createSequentialGroup()
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(24)
								.addComponent(lblResult_nm, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(txt_table, GroupLayout.PREFERRED_SIZE, 180, GroupLayout.PREFERRED_SIZE))
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(34)
								.addComponent(cSpatial, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE))
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(19)
								.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
									.addComponent(lbl_sSrs, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
									.addGroup(groupLayout.createSequentialGroup()
										.addComponent(lbl_lang, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)))
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
									.addComponent(cb_Lang, GroupLayout.PREFERRED_SIZE, 180, GroupLayout.PREFERRED_SIZE)
									.addComponent(cb_coord, GroupLayout.PREFERRED_SIZE, 176, GroupLayout.PREFERRED_SIZE))))
						.addPreferredGap(ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
						.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
							.addComponent(btnButton, GroupLayout.PREFERRED_SIZE, 170, GroupLayout.PREFERRED_SIZE)
							.addComponent(btnLoad, GroupLayout.PREFERRED_SIZE, 170, GroupLayout.PREFERRED_SIZE))
						.addGap(355))
					.addGroup(groupLayout.createSequentialGroup()
						.addGap(236)
						.addComponent(lblNewLabel_4)
						.addContainerGap(453, Short.MAX_VALUE))
					.addGroup(groupLayout.createSequentialGroup()
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(31)
									.addComponent(lblNewLabel_3))
								.addGroup(groupLayout.createSequentialGroup()
									.addContainerGap()
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(lbl_database)
										.addComponent(lblPassword)
										.addComponent(lbl_scheme))
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(txt_sheme, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(txt_pass, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(txt_port, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(txt_database, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(txt_ip, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(txt_dbtype, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(txt_id, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(btnNewLoad, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)))
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(34)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(lblNewLabel_2)
										.addComponent(lblNewLabel_1)
										.addComponent(lblNewLabel_DB))))
							.addGroup(groupLayout.createSequentialGroup()
								.addContainerGap()
								.addComponent(btn_con_1)))
						.addPreferredGap(ComponentPlacement.RELATED, 133, Short.MAX_VALUE)
						.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
							.addComponent(panel, GroupLayout.PREFERRED_SIZE, 255, GroupLayout.PREFERRED_SIZE)
							.addComponent(btn_down_shp))
						.addGap(217))
			);
			groupLayout.setVerticalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
					.addGroup(groupLayout.createSequentialGroup()
						.addGap(34)
						.addComponent(lblResult, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
						.addGap(12)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblResult_nm)
								.addComponent(txt_table, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addComponent(btnButton, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addGroup(groupLayout.createSequentialGroup()
								.addComponent(cSpatial, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
									.addGroup(groupLayout.createSequentialGroup()
										.addGap(6)
										.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
											.addComponent(cb_Lang, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
											.addComponent(lbl_lang, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE))
										.addGap(23)
										.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
											.addGroup(groupLayout.createSequentialGroup()
												.addGap(3)
												.addComponent(lbl_sSrs, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE))
											.addComponent(cb_coord, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
									.addGroup(groupLayout.createSequentialGroup()
										.addGap(32)
										.addComponent(lblNewLabel)
										.addGap(18)
										.addComponent(label))))
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(6)
								.addComponent(btnLoad, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)))
						.addGap(74)
						.addComponent(lblNewLabel_4)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(41)
								.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
									.addComponent(lblNewLabel_DB)
									.addComponent(txt_dbtype, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
									.addComponent(lblNewLabel_1)
									.addComponent(txt_ip, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGap(14)
								.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
									.addComponent(lblNewLabel_2)
									.addComponent(txt_port, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGap(14)
								.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
									.addComponent(lbl_database)
									.addComponent(txt_database, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGap(14)
								.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
									.addComponent(lblNewLabel_3)
									.addComponent(txt_id, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGap(14)
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
									.addComponent(lblPassword)
									.addGroup(groupLayout.createSequentialGroup()
										.addComponent(txt_pass, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addGap(14)
										.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
											.addComponent(txt_sheme, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
											.addComponent(lbl_scheme)))))
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(29)
								.addComponent(panel, GroupLayout.PREFERRED_SIZE, 276, GroupLayout.PREFERRED_SIZE)))
						.addGap(5)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
							.addComponent(btn_con_1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(btnNewLoad)
							.addComponent(btn_down_shp))
						.addContainerGap())
			);
			getContentPane().setLayout(groupLayout);

		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		;

		btnButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				JFileChooser fileopen = new JFileChooser();

				FileFilter filter = new FileNameExtensionFilter("ESRI ShapeFile", "shp");

				fileopen.addChoosableFileFilter(filter);

				int ret = fileopen.showDialog(null, "Choose file");
				if (ret == JFileChooser.APPROVE_OPTION) {

					lblResult.setText(fileopen.getSelectedFile().toString());
					// DataCenter dc = new DataCenter();
					// dc.DbProc(fileopen.getSelectedFile().toString());
				}

			}

		});

		btnLoad.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// lblResult.setText(lblResult.getText()+ " 업로드 진행중");
				Cursor TimerCursor = new Cursor(Cursor.WAIT_CURSOR);
				setCursor(TimerCursor);
				String Path = lblResult.getText();
				String TName = txt_table.getText();

				String Lang, CoordSys;
				String type = txt_dbtype.getText();
				String Ip = txt_ip.getText();
				String Port = txt_port.getText();
				int Pt = Integer.parseInt(Port);
				String Schema = txt_sheme.getText();
				String db = txt_database.getText();
				String id = txt_id.getText();
				String pass = txt_pass.getText();
				Map<String, Object> params = new HashMap();
				if(type.trim().equals("postgis")) {
					//Map<String, Object> params = new HashMap();
							params.put("dbtype", type.trim());
							params.put("host", Ip.trim());
							params.put("port", Pt);
							params.put("schema", Schema.trim());
							params.put("database", db.trim());
							params.put("user", id.trim());
							params.put("passwd", pass.trim());
							params.put("validate connections", true);
					}else if(type.trim().equals("mysql")) {

						params.put(MySQLDataStoreFactory.DBTYPE.key, type.trim());
						params.put(MySQLDataStoreFactory.HOST.key, Ip.trim());
						params.put(MySQLDataStoreFactory.PORT.key, Pt);
						//params.put("schema", Schema.trim());
						params.put(MySQLDataStoreFactory.DATABASE.key, db.trim());
						params.put(MySQLDataStoreFactory.USER.key, id.trim());
						params.put(MySQLDataStoreFactory.PASSWD.key, pass.trim());
	                   // params.put(MySQLDataStoreFactory.DATASOURCE.key,"maria");


					}
				Lang = cb_Lang.getSelectedItem().toString();
		    	CoordSys = cb_coord.getSelectedItem().toString();

						// label.setVisible(true);
				btnButton.setEnabled(false);
				btnLoad.setEnabled(false);
				// lblResult.setText(Path + " 진행중 ");

				Boolean sp_gb = cSpatial.isSelected();

				DataCenter dc = new DataCenter();
				MariaDBManager md = new MariaDBManager();
				if(type.trim().equals("postgis")) {
				dc.DbProc(params, Path, TName, sp_gb, Lang, CoordSys);
				}else {
             			md.ShpToMaria(Ip, db, Pt, id, pass, Path,Lang);
				}
				btnLoad.setEnabled(true);
				btnButton.setEnabled(true);

				Cursor EndCursor = new Cursor(Cursor.DEFAULT_CURSOR);
				setCursor(EndCursor);

			}

		});
		btnNewLoad.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				DbManager dbms = new DbManager();
				Vector vc = new Vector();
				List<String> columns = new ArrayList<String>();
				List<String[]> values = new ArrayList<String[]>();

				columns.add("테이블 명칭");

				String type = txt_dbtype.getText();
				String Ip = txt_ip.getText();
				String Port = txt_port.getText();
				int Pt = Integer.parseInt(Port);
				String Schema = txt_sheme.getText();
				String db = txt_database.getText();
				String id = txt_id.getText();
				String pass = txt_pass.getText();

				Map<String, Object> params = new HashMap();
				if(type.trim().equals("postgis")) {
					//Map<String, Object> params = new HashMap();
							params.put("dbtype", type.trim());
							params.put("host", Ip.trim());
							params.put("port", Pt);
							params.put("schema", Schema.trim());
							params.put("database", db.trim());
							params.put("user", id.trim());
							params.put("passwd", pass.trim());
							params.put("validate connections", true);
					}else if(type.trim().equals("mysql")) {

						params.put(MySQLDataStoreFactory.DBTYPE.key, type.trim());
						params.put(MySQLDataStoreFactory.HOST.key, Ip.trim());
						params.put(MySQLDataStoreFactory.PORT.key, Pt);
						//params.put("schema", Schema.trim());
						params.put(MySQLDataStoreFactory.DATABASE.key, db.trim());
						params.put(MySQLDataStoreFactory.USER.key, id.trim());
						params.put(MySQLDataStoreFactory.PASSWD.key, pass.trim());
	                   // params.put(MySQLDataStoreFactory.DATASOURCE.key,"maria");


					}

				String[] layers = dbms.LayerList(params);
				if(layers.length==0) {
				  return;
				}



				for (int i = 0; i < layers.length; i++) {
					vc.addElement(layers[i]);
				}
				listview.setListData(vc);
				listview.setSelectedIndex(layers.length - 1);
				// JScrollPane list1scr = new JScrollPane(listview);
		//		listview.setVisibleRowCount(8);

				// add(new JScrollPane(listview),"Center");
				// listview= new JList(fruits);

			}
		});
		btn_con_1.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// String selected = listview.getSelectedValue().toString();
				// System.out.println("Table:"+ selected);
				// DbManager dbcon = null;
				boolean scr = false;
				String type = txt_dbtype.getText();
				String Ip = txt_ip.getText();
				String Port = txt_port.getText();
				int Pt = Integer.parseInt(Port);
				String Schema = txt_sheme.getText();
				String db = txt_database.getText();
				String id = txt_id.getText();
				String pass = txt_pass.getText();
				Map<String, Object> params = new HashMap();

				System.out.println("기종:"+type);
				if(type.trim().equals("postgis")) {
				//Map<String, Object> params = new HashMap();
						params.put("dbtype", type.trim());
						params.put("host", Ip.trim());
						params.put("port", Pt);
						params.put("schema", Schema.trim());
						params.put("database", db.trim());
						params.put("user", id.trim());
						params.put("passwd", pass.trim());
						params.put("validate connections", true);
				}else if(type.trim().equals("mysql")) {

					params.put(MySQLDataStoreFactory.DBTYPE.key, type.trim());
					params.put(MySQLDataStoreFactory.HOST.key, Ip.trim());
					params.put(MySQLDataStoreFactory.PORT.key, Pt);
					//params.put("schema", Schema.trim());
					params.put(MySQLDataStoreFactory.DATABASE.key, db.trim());
					params.put(MySQLDataStoreFactory.USER.key, id.trim());
					params.put(MySQLDataStoreFactory.PASSWD.key, pass.trim());
                   // params.put(MySQLDataStoreFactory.DATASOURCE.key,"maria");


				}
               /**
			//	params.put(MySQLDataStoreFactory.DBTYPE.key, type.trim());
				params.put(MySQLDataStoreFactory.HOST.key, Ip.trim());
				params.put(MySQLDataStoreFactory.PORT.key, Pt);
				//params.put("schema", Schema.trim());
				params.put(MySQLDataStoreFactory.DATABASE.key, db.trim());
				params.put(MySQLDataStoreFactory.USER.key, id.trim());
				params.put(MySQLDataStoreFactory.PASSWD.key, pass.trim());
					params.put("validate connections", true);
               **/
			//	}
				// System.out.println(""+scr);
				JFrame frame = new JFrame("JOptionPane showMessageDialog example");
				DbManager dbms = new DbManager();
				try {
					scr = dbms.getConnect(params);
					System.out.println("time:"+scr);
					// System.out.println(""+scr);
				//	JFrame frame = new JFrame("JOptionPane showMessageDialog example");
					if (scr == true) {
						JOptionPane.showMessageDialog(frame, "Ok Connect!!", "DataCenter",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					} else {
						JOptionPane.showMessageDialog(frame, "Fail Connect!! Check Information", "DataCenter",
								JOptionPane.INFORMATION_MESSAGE);
					}

				} catch (Exception ex) {
					JOptionPane.showMessageDialog(frame, "Fail Connection!! Check Information", "DataCenter",
							JOptionPane.INFORMATION_MESSAGE);

				} finally {
					if(scr==true) {
				         return;
					} else {
						return;
					}
				}
			}
		});

		btn_down_shp.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// String selected = listview.getSelectedValue().toString();
				// System.out.println("Table:"+ selected);
				// DbManager dbcon = null;
				JFrame frame = new JFrame("");
				 String type=     listview.getSelectedValue().toString();
				    String[] layer = type.split(":");

	                 if(layer[1].equals("no GISData")) {

	                	  JOptionPane.showMessageDialog(frame, type+"GEOMETRY 정보가 없는 테이블 입니다.", "ForGIS DataCenter",
	      						JOptionPane.INFORMATION_MESSAGE);
	                	 return;
	                 }

				Cursor TimerCursor = new Cursor(Cursor.WAIT_CURSOR);
				setCursor(TimerCursor);

				btn_con_1.setEnabled(false);
				btnNewLoad.setEnabled(false);
				btnLoad.setEnabled(false);
				btnButton.setEnabled(false);
				btn_down_shp.setEnabled(false);

			    String layernm = layer[0];
			    File dir;



			    ShpManager shp = new ShpManager();

			    JFileChooser chooser = new JFileChooser();
			//    chooser.setCurrentDirectory(new java.io.File("."));
			    chooser.setDialogTitle("SHP 파일 선택");
			    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			    chooser.setAcceptAllFileFilterUsed(false);


			    JFileChooser fileChooser = new JFileChooser();
			    fileChooser.setDialogTitle("Specify a file to save");


			    if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
			      System.out.println("getCurrentDirectory(): " + chooser.getSelectedFile());
			      dir = chooser.getSelectedFile();
			      try {
					shp.DbToShp(layernm, dir);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			    } else {
			      System.out.println("No Selection ");
			    }


			    JOptionPane.showMessageDialog(frame," 선택 된 경로에 "+layernm+" shp 파일이 생성되었습니다.", "ForGIS DataCenter",
						JOptionPane.INFORMATION_MESSAGE);
			    Cursor EndCursor = new Cursor(Cursor.DEFAULT_CURSOR);
				setCursor(EndCursor);


				btn_con_1.setEnabled(true);
				btnNewLoad.setEnabled(true);
				btnLoad.setEnabled(true);
				btnButton.setEnabled(true);
				btn_down_shp.setEnabled(true);

			}


		});

	}
}
