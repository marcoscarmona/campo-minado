package view

import model.Tabuleiro
import java.awt.GridLayout
import javax.swing.JPanel

class PainelTabuleiro(tabuleiro: Tabuleiro) : JPanel() {
    init {
        layout = GridLayout(tabuleiro.qtdDeLinhas, tabuleiro.qtdeDeColunas)
        tabuleiro.forEachCampos { campo ->
            val botao = BotaoCampo(campo)
            add(botao)
        }
    }
}
