import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JTable;
import javax.swing.JComboBox;
public class GDI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JFileChooser chooser;
	private File file;
	private JTextField codigoFilme;
	private JTextField tituloFilme;
	private JTextField anoFilme;
	private JTextField generoFilme;
	private JTextField codigoRoteiro;
	private JTextField classificacaoFilme;
	private JTextField notaFilme;
	private JTextField orcamentoFilme;
	private JLabel labelFotoQuery;
	private JComboBox comboBox;
	
	private Connection connection;
	private BufferedImage foto;
	
	FilmeTableModel tabela = new FilmeTableModel();
	private JTable table;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				GDI frame = new GDI();
				frame.setVisible(true);
			}
		});
	}
	
	public void accessDB() {
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		try {
			connection = DriverManager.getConnection(url, "gp05", "gdi123");
			tabela.addListaDeFilme(getFilmes());
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Erro ao conectar com o Banco de Dados.");
		}
	}
	
	public List<Filme> getFilmes() {
		tabela.limpar();
		List<Filme> filmes = new ArrayList<>();
		String sql = "SELECT cod, title, genero FROM FILMES";
		try (PreparedStatement statement = connection.prepareStatement(sql);
				ResultSet resultSet = statement.executeQuery()) {
			while(resultSet.next()) {
				filmes.add(new Filme(resultSet.getInt("cod"), resultSet.getString("title"), 
						resultSet.getString("genero"), null));
				comboBox.addItem(resultSet.getInt("cod"));
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Erro ao selecionar do Banco de Dados.");
		}
		return filmes;
	}

	/**
	 * Create the frame.
	 */
	public GDI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1090, 606);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		codigoFilme = new JTextField();
		codigoFilme.setBounds(635, 121, 168, 20);
		contentPane.add(codigoFilme);
		codigoFilme.setColumns(10);
		
		JLabel lblCodigoDoFilme = new JLabel("Codigo do filme:");
		lblCodigoDoFilme.setBounds(635, 96, 134, 14);
		contentPane.add(lblCodigoDoFilme);
		
		JLabel lblTtuloDoFilme = new JLabel("T\u00EDtulo do filme:");
		lblTtuloDoFilme.setBounds(635, 152, 168, 14);
		contentPane.add(lblTtuloDoFilme);
		
		tituloFilme = new JTextField();
		tituloFilme.setBounds(635, 177, 168, 20);
		tituloFilme.setColumns(10);
		contentPane.add(tituloFilme);
		
		JLabel lblAnoDeProduo = new JLabel("Ano de produ\u00E7\u00E3o:");
		lblAnoDeProduo.setBounds(635, 208, 168, 14);
		contentPane.add(lblAnoDeProduo);
		
		anoFilme = new JTextField();
		anoFilme.setBounds(635, 233, 168, 20);
		anoFilme.setColumns(10);
		contentPane.add(anoFilme);
		
		JLabel lblGnero = new JLabel("G\u00EAnero:");
		lblGnero.setBounds(635, 264, 168, 14);
		contentPane.add(lblGnero);
		
		generoFilme = new JTextField();
		generoFilme.setBounds(635, 289, 168, 20);
		generoFilme.setColumns(10);
		contentPane.add(generoFilme);
		
		JLabel lblCdigoRoteiro = new JLabel("C\u00F3digo Roteiro");
		lblCdigoRoteiro.setBounds(841, 264, 168, 14);
		contentPane.add(lblCdigoRoteiro);
		
		codigoRoteiro = new JTextField();
		codigoRoteiro.setBounds(841, 289, 168, 20);
		codigoRoteiro.setColumns(10);
		contentPane.add(codigoRoteiro);
		
		JLabel lblClassificaoIndicativa = new JLabel("Classifica\u00E7\u00E3o indicativa:");
		lblClassificaoIndicativa.setBounds(841, 96, 168, 14);
		contentPane.add(lblClassificaoIndicativa);
		
		classificacaoFilme = new JTextField();
		classificacaoFilme.setBounds(841, 121, 168, 20);
		classificacaoFilme.setColumns(10);
		contentPane.add(classificacaoFilme);
		
		JLabel lblNotaCrtica = new JLabel("Nota Cr\u00EDtica:");
		lblNotaCrtica.setBounds(841, 152, 157, 14);
		contentPane.add(lblNotaCrtica);
		
		notaFilme = new JTextField();
		notaFilme.setBounds(841, 177, 168, 20);
		notaFilme.setColumns(10);
		contentPane.add(notaFilme);
		
		JLabel lblOramento = new JLabel("Or\u00E7amento:");
		lblOramento.setBounds(841, 208, 168, 14);
		contentPane.add(lblOramento);
		
		JLabel lblFilmeGdi = new JLabel("Filme GDI:");
		lblFilmeGdi.setBounds(20, 11, 78, 14);
		contentPane.add(lblFilmeGdi);
		
		JLabel labelFoto = new JLabel("");
		labelFoto.setBounds(841, 340, 223, 203);
		contentPane.add(labelFoto);
		
		labelFotoQuery = new JLabel("");
		labelFotoQuery.setBounds(46, 340, 345, 216);
		contentPane.add(labelFotoQuery);
		
		orcamentoFilme = new JTextField();
		orcamentoFilme.setBounds(841, 233, 168, 20);
		orcamentoFilme.setColumns(10);
		contentPane.add(orcamentoFilme);
		
		comboBox = new JComboBox();
		comboBox.setBounds(420, 362, 157, 20);
		contentPane.add(comboBox);
		comboBox.addItem("-- Escolha um filme --");
		
		JButton btnInserir = new JButton("Inserir");
		btnInserir.setBounds(650, 320, 140, 23);
		btnInserir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String sql = "INSERT INTO filmes(cod, title, genero, foto) VALUES(?,?,?,?)";
				try (PreparedStatement statement = connection.prepareStatement(sql)) {
					Filme filme = new Filme(new Integer(codigoFilme.getText()), tituloFilme.getText(), 
							generoFilme.getText(), foto);
					statement.setInt(1, filme.getCod());
					statement.setString(2, filme.getTitulo());
					statement.setString(3, filme.getGenero());
					InputStream is = null;
		            try {
		                ByteArrayOutputStream baos = new ByteArrayOutputStream();
		                ImageIO.write(foto, "jpg", baos);
		                is = new ByteArrayInputStream(baos.toByteArray());
		            } catch (IOException ex) {
		                JOptionPane.showMessageDialog(null, "Erro ao converter a imagem.");
		            }
		            statement.setBlob(4, is);
					statement.executeUpdate();
					tabela.addListaDeFilme(getFilmes());
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(null, "Erro ao inserir um filme.");
				}
				tituloFilme.setText("");
				codigoFilme.setText("");
				generoFilme.setText("");
				labelFoto.setIcon(null);
			}
		});
		contentPane.add(btnInserir);
		
		JButton btnUploadImagem = new JButton("Upload Imagem");
		btnUploadImagem.setBounds(650, 428, 142, 23);
		btnUploadImagem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser c = new JFileChooser();
				c.showOpenDialog(btnInserir);
				file = c.getSelectedFile();
				labelFoto.setIcon(new ImageIcon(file.getAbsolutePath()));
				try {
					foto = ImageIO.read(file);
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, "Erro na leitrua da imagem.");
				}
			}
		});
		contentPane.add(btnUploadImagem);
		
		JPanel panel = new JPanel();
		panel.setBounds(20, 47, 557, 269);
		panel.setLayout(new BorderLayout(0, 0));
		contentPane.add(panel);
		
		table = new JTable();
		table.setCellSelectionEnabled(true);
		table.setModel(tabela);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(0, 0, 0, 0);
		panel.add(scrollPane);
		
		JButton btnConsultar = new JButton("Consultar");
		btnConsultar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
	            BufferedImage imagem;
	            String sql = "Select foto from filmes where cod = '" + comboBox.getSelectedItem() + "'";
	            try (PreparedStatement statement = connection.prepareStatement(sql);
	            		ResultSet resultSet = statement.executeQuery()) {
	            	while (resultSet.next()) {
	                    Blob blob = resultSet.getBlob("foto");
	                    ImageIcon i = new ImageIcon(blob.getBytes(1, (int) blob.length()));
	                    //imagem = new BufferedImage(i.getIconWidth(), i.getIconHeight(), BufferedImage.TYPE_INT_RGB);
	                    labelFotoQuery.setIcon(i);
	                }
	            } catch (Exception ex) {
	                JOptionPane.showMessageDialog(null, "Erro ao consultar o Banco de Dados.");
	            }
			}
		});
		btnConsultar.setBounds(420, 490, 157, 23);
		contentPane.add(btnConsultar);
		
		accessDB();

	}
}