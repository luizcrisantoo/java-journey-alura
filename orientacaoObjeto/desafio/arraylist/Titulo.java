package orientacaoObjeto.desafio.arraylist;

public class Titulo implements Comparable<Titulo> {
    String nome;

    @Override
    public int compareTo(Titulo outroNome) {
        return this.nome.compareTo(outroNome.nome);
    }   
}
