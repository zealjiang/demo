package com.example.kotlinlearn

enum class RGB { RED, GREEN, BLUE }

/*enum class Color(){
    RED,BLACK,BLUE,GREEN,WHITE,YELLOW(100);
    var rgb :Int = 0

    constructor(rgb: Int): this(){
        this.rgb = rgb
    }
}*/

enum class Color(){
    RED,BLACK,BLUE,GREEN,WHITE,YELLOW(100);
    constructor(rgb: Int): this()
}

enum class Shape{
    OVEL{
        override fun chineseName() {
            println("椭圆")
        }
    },
    RECTANGLE{
        override fun chineseName() {
            println("矩形")
        }
    };

    abstract fun chineseName()
}

fun main(args: Array<String>) {
    var color:Color=Color.YELLOW

    println(enumValues<Color>().joinToString { it.name })
    println(enumValueOf<Color>("YELLOW").ordinal)
    println(Color.YELLOW)
    println(color.name)
    println(color.ordinal)

    Shape.OVEL.chineseName()
}

