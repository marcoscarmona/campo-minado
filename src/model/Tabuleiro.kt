package model

import java.util.*

enum class TabuleiroEvento { VITORIA, DERROTA }

class Tabuleiro(val qtdDeLinhas: Int, val qtdeDeColunas: Int, private val qtdDeMinas: Int) {
    private val campos = ArrayList<ArrayList<Campo>>()
    private val callbacks = ArrayList<(TabuleiroEvento) -> Unit>()

    init {
        gerarCampos()
        associarVizinhos()
        sortearMinas()
    }

    private fun gerarCampos() {
        for (linha in 0 until qtdDeLinhas) {
            campos.add(ArrayList())
            for (coluna in 0..<qtdeDeColunas) {
                val novoCampo = Campo(linha, coluna)
                campos[linha].add(novoCampo)
                novoCampo.onEvento(this::verficarDerrotaOuVitoria)
            }
        }
    }

    private fun associarVizinhos() {
        forEachCampos { associarVizinhos(it) }
    }

    private fun associarVizinhos(campo: Campo) {
        val (linha, coluna) = campo
        val linhas = arrayOf(linha - 1, linha, linha + 1)
        val colunas = arrayOf(coluna - 1, coluna, coluna + 1)

        linhas.forEach { l ->
            colunas.forEach { c ->
                val atual = campos.getOrNull(l)?.getOrNull(c)
                atual?.takeIf { campo != it }?.let { campo.addVizinho(it) }
            }
        }
    }

    private fun sortearMinas() {
        val gerador = Random()
        var linhaSorteada = -1
        var colunaSorteada = -1
        var qtdMinasAtual = 0

        while (qtdMinasAtual < this.qtdDeMinas) {
            linhaSorteada = gerador.nextInt(qtdDeLinhas)
            colunaSorteada = gerador.nextInt(qtdeDeColunas)

            val campoSorteado = campos[linhaSorteada][colunaSorteada]
            if (campoSorteado.seguro) {
                campoSorteado.minar()
                qtdMinasAtual++
            }
        }
    }

    fun forEachCampos(callback: (Campo) -> Unit) {
        campos.forEach { linha -> linha.forEach(callback) }
    }

    private fun objetivoAlcancado(): Boolean {
        var jogadorGanhou = true
        forEachCampos { if (!it.objetivoAlcancado) jogadorGanhou = false }
        return jogadorGanhou
    }

    private fun verficarDerrotaOuVitoria(campo: Campo, evento: CampoEvento) {
        if (evento == CampoEvento.EXPLOSAO) {
            callbacks.forEach { it(TabuleiroEvento.DERROTA) }
        } else if (objetivoAlcancado()) {
            callbacks.forEach { it(TabuleiroEvento.VITORIA) }
        }

        fun onEvento(callback: (TabuleiroEvento) -> Unit) {
            callbacks.add(callback)
        }

        fun reiniciar() {
            forEachCampos { it.reiniciar() }
            sortearMinas()
        }

    }
}

