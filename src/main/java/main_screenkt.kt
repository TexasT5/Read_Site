import GetLinkPackaces.GetLinks
import java.util.*
import javax.swing.*

class main_screenkt : JFrame(){
    var main_panel: JPanel? = null
    private val enterURLTextField: JTextField? = null
    private val clickButton: JButton? = null
    private val errorMessageLabel: JLabel? = null
    private val list1: JList<String>? = null

    init{
        val checkCertificate = ArrayList(Arrays.asList("https://", "http://"))
        val getLinks = GetLinks()
        add(main_panel)
        setSize(400, 400)
        title = "Read Link In Site"
        isResizable = false
        defaultCloseOperation = EXIT_ON_CLOSE
        errorMessageLabel!!.isVisible = false
        val listModel = DefaultListModel<String>()
        list1!!.model = listModel
        clickButton!!.addActionListener {
            val enterUrlGetText = enterURLTextField!!.text
            if (enterUrlGetText == "") {
                errorMessageLabel.isVisible = true
                errorMessageLabel.text = "Hata link girilmedi"
            } else {
                errorMessageLabel.isVisible = false
                val enteredText = enterUrlGetText.split("https://".toRegex()).toTypedArray()
                if (enteredText[0] == "") {
                    println("boş")
                } else {
                    println("dolu")
                }
            }
        }
    }
}