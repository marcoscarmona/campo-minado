package view

import model.Campo
import model.CampoEvento
import java.awt.Color
import java.awt.Font
import javax.swing.BorderFactory
import javax.swing.JButton
import javax.swing.SwingUtilities

private val COR_BG_NORMAL = Color(184, 184, 184)
private val COR_BG_MARCACAO = Color(8, 179, 247)
private val COR_BG_EXPLOSAO = Color(189, 66, 68)
private val COR_TXT_VERDE = Color(0, 100, 0)


class BotaoCampo(private val campo: Campo) : JButton() {
    init {
        font = font.deriveFont(Font.BOLD)
        background = COR_BG_NORMAL
        isOpaque = true
        border = BorderFactory.createBevelBorder(0)
        addMouseListener(MouseClickListenter(campo, { it.abrir() }, { it.alteraMarcacao() }))
        campo.onEvento(this::aplicaEstilo)
    }

    private fun aplicaEstilo(campo: Campo, evento: CampoEvento) {
        when (evento) {
            CampoEvento.EXPLOSAO -> aplicaEstiloExplodido()
            CampoEvento.ABERTURA -> aplicaEstiloAberto()
            CampoEvento.MARCACAO -> aplicaEstiloMarcado()
            else -> aplicaEstiloPadrao()
        }

        SwingUtilities.invokeLater {
            repaint()
            validate()
        }
    }

    private fun aplicaEstiloPadrao() {
        background = COR_BG_NORMAL
        border = BorderFactory.createBevelBorder(0)
        text = ""
    }

    private fun aplicaEstiloMarcado() {
        background = COR_BG_MARCACAO
        foreground = Color.BLACK
        text = "M"
    }

    private fun aplicaEstiloAberto() {
        background = COR_BG_NORMAL
        border = BorderFactory.createLineBorder(Color.DARK_GRAY)

        foreground = when (campo.qtdeVizinhosMinados) {
            1 -> COR_TXT_VERDE
            2 -> Color.BLUE
            3 -> Color.YELLOW
            4, 5, 6 -> Color.RED
            else -> Color.PINK
        }

        text = if (campo.qtdeVizinhosMinados > 0) campo.qtdeVizinhosMinados.toString() else ""

    }

    private fun aplicaEstiloExplodido() {
        background = COR_BG_EXPLOSAO
        text = "X"
    }
}