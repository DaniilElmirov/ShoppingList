package com.example.shoppinglist

open class Rectangle(
    private var a: Int,
    private var b: Int
) {
    open fun setWidth(width: Int) {
        this.a = width
    }

    open fun setHeight(height: Int) {
        this.b = height
    }

    fun getWidth() = a

    fun getHeight() = b

    fun area() = a * b
}

class Square(
    private val side: Int
): Rectangle(side, side) {
    override fun setHeight(height: Int) {
        super.setHeight(height)
        super.setWidth(height)
    }

    override fun setWidth(width: Int) {
        super.setWidth(width)
        super.setHeight(width)
    }
}

fun main() {
    test()
}

private fun test() {
    val rectangle: Rectangle = Square(10)

    println(rectangle.area() == 100)
    println(rectangle.getWidth() == 10)
    println(rectangle.getHeight() == 10)

    rectangle.setWidth(5)
    rectangle.setHeight(20)

    println(rectangle.area() == 100)
    println(rectangle.getWidth() == 5)
    println(rectangle.getHeight() == 20)

    //Square не проходит тесты(его поведение отличается от поведения Rectangle) -> Square не является наследником Rectangle
}