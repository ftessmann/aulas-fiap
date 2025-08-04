import java.util.Objects;

public class ProdutoDigital extends Produto {
    private String linkDownload;
    private String formatoArquivo;

    public ProdutoDigital() {
    }

    public ProdutoDigital(String nome, double preco, String descricao, int quantidade, String categoria) {
        super(nome, preco, descricao, quantidade, categoria);
        this.linkDownload = linkDownload;
        this.formatoArquivo = formatoArquivo;
    }

    public String getLinkDownload() {
        return linkDownload;
    }

    public void setLinkDownload(String linkDownload) {
        this.linkDownload = linkDownload;
    }

    public String getFormatoArquivo() {
        return formatoArquivo;
    }

    public void setFormatoArquivo(String formatoArquivo) {
        this.formatoArquivo = formatoArquivo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ProdutoDigital that = (ProdutoDigital) o;
        return Objects.equals(getLinkDownload(), that.getLinkDownload()) && Objects.equals(getFormatoArquivo(), that.getFormatoArquivo());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getLinkDownload(), getFormatoArquivo());
    }

    @Override
    public String toString() {
        return "ProdutoDigital{" +
                "linkDownload='" + linkDownload + '\'' +
                ", formatoArquivo='" + formatoArquivo + '\'' +
                "} " + super.toString();
    }
}
