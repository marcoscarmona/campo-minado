package view

import model.Campo
import java.awt.event.MouseEvent
import java.awt.event.MouseListener

class MouseClickListenter(

    private val campo: Campo,
    private val onBotaoEsquerdo: (Campo) -> Unit,
    private val onBotaoDireito: (Campo) -> Unit
) : MouseListener {
    override fun mouseClicked(e: MouseEvent?) {
        TODO("Not yet implemented")
    }

    override fun mousePressed(e: MouseEvent?) {
        when (e?.button) {
            MouseEvent.BUTTON1 -> onBotaoEsquerdo(campo)
            MouseEvent.BUTTON3 -> onBotaoDireito(campo) //ou 2
        }
    }

    override fun mouseReleased(e: MouseEvent?) {
        TODO("Not yet implemented")
    }

    override fun mouseEntered(e: MouseEvent?) {
        TODO("Not yet implemented")
    }

    override fun mouseExited(e: MouseEvent?) {
        TODO("Not yet implemented")
    }

}