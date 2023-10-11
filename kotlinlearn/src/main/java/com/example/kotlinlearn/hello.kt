package com.example.kotlinlearn

fun main(args: Array<String>){
    println("Hello World!")

}

fun request(){
    fun handleResult(result: Result): String{
        return when(result) {
            is Success -> {
                "success" +result.code
            }
            is Exception -> {
                "exception"+result.message
            }
        }
    }
}