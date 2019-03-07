package com.lyle.plugin.flutter.json

import com.intellij.openapi.vfs.VirtualFile
import com.intellij.ui.components.JBScrollPane
import javax.swing.*

class FrameView(private val virtualFile: VirtualFile) {

    var frame: JFrame? = null

    fun build(): JComponent {
        return JPanel().apply {
            placeComponents(this)
        }
    }

    private fun placeComponents(panel: JPanel) = panel.apply {
        layout = null

        val tipLabel = JLabel("input json and click button").apply {
            setBounds(10, 0, 500, 40)
        }

        val jsonText = JTextArea().apply {
            setBounds(0,50,700,400)
        }

        add(JButton("ok").apply {
            setBounds(600, 10, 80, 30)
            isVisible = true
            addActionListener {
//                val classesString = ClassMaker().make(jsonText.text)
                val classesString = ClassGenerator().generate(Utils.toUpperCaseFirstOne(virtualFile.nameWithoutExtension), jsonText.text)
                if (classesString.startsWith("error:")) {
                    tipLabel.text = classesString
                } else {
//                    Util.setSysClipboardText(classesString) // 复制到粘贴板
                    Utils.writeToFile(virtualFile, classesString)
                    frame?.dispose()
                }
            }
        })

        add(JBScrollPane(jsonText).apply {
            verticalScrollBarPolicy = JBScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
            horizontalScrollBarPolicy = JBScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
            setBounds(0, 50, 700, 400)
        })

        add(tipLabel)
    }
}