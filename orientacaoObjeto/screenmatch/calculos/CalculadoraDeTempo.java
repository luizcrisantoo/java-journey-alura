package orientacaoObjeto.screenmatch.calculos;

import orientacaoObjeto.screenmatch.modelos.Titulo;

public class CalculadoraDeTempo {
    private int tempoTotal = 0;

    public int getTempoTotal() {
        return this.tempoTotal;
    }

    public void inclui(Titulo titulo){
        System.out.println("Adicionando duração de minutos de " +titulo);
        this.tempoTotal += titulo.getDuracaoEmMinutos();
    }
    
}
