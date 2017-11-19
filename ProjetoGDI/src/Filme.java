import java.awt.image.BufferedImage;

public class Filme {

	private Integer cod;
	private String titulo;
	private String genero;
	private BufferedImage foto;
	
	public Filme(int cod, String titulo, String genero, BufferedImage foto) {
		super();
		this.cod = cod;
		this.titulo = titulo;
		this.genero = genero;
		this.foto = foto;
	}
	
	public Integer getCod() {
		return cod;
	}
	public void setCod(Integer cod) {
		this.cod = cod;
	}
	public void setCod(String cod) {
		this.cod = new Integer(cod);
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getGenero() {
		return genero;
	}
	public void setGenero(String genero) {
		this.genero = genero;
	}
	public BufferedImage getFoto() {
		return foto;
	}
	public void setFoto(BufferedImage foto) {
		this.foto = foto;
	}
	
	
	
}
