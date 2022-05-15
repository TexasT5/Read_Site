import BusinessEnum.BusinessEnumClass

fun main(){
    val text :String = "asdamwndöawmdnawd"

    text.split("${BusinessEnumClass.values()}").let {
        print(it)
    }

}