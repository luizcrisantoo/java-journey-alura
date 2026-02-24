package orientacaoObjeto.screenmatch;

public class Filme {
    private String nome;
    private int anoDeLancamento;
    private boolean incluidoNoPlano;
    private double somaDasAvaliacoes;
    private int totalDeAvaliacoes;
    private int duracaoEmMinutos; 

    String getNome() {
    return nome;
    }

    int geAnoDeLancamento() {
    return anoDeLancamento;
    }

    boolean getIncluidoNoPlano() {
    return incluidoNoPlano;
    }

    int getTotalDeAvaliacoes(){
    return totalDeAvaliacoes;
    }

    int getDuracaoEmMinutos() {
    return duracaoEmMinutos;
    }
    
    void setNome(String nome){
    this.nome = nome;
    }

    void setAnoDeLancamento(int anoDeLancamento){
    this.anoDeLancamento = anoDeLancamento;
    }

    void setIncluidoNoPlano(boolean incluidoNoPlano){
    this.incluidoNoPlano = incluidoNoPlano;
    }


    void setDuracaoEmMinutos(int duracaoEmMinutos){
    this.duracaoEmMinutos = duracaoEmMinutos;
    }

    void exibeFichaTecnica() {
        System.out.println("Nome do filme: " +nome);
        System.out.println("Ano de lançamento: " +anoDeLancamento);
    }

    void avalia(double nota) {
        somaDasAvaliacoes += nota;
        totalDeAvaliacoes++;
    }

    double pegaMedia() {
        return somaDasAvaliacoes / totalDeAvaliacoes;
    }
}
