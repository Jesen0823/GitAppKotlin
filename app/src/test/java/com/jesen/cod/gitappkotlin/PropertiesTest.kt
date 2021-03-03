package com.jesen.cod.gitappkotlin

import com.jesen.cod.gitappkotlin.properties.AbsProperties
import com.jesen.cod.gitappkotlin.properties.Propertiesdelegate
import org.junit.Test

class InfoProp: AbsProperties("info.properties") {
    var name: String by prop
    var email:String by prop
    var home:String by prop
    var height:Int by prop
    var happy:Boolean by prop
    var point:Float by prop
}

class TestProperties{

    @Test
    fun testProperties(){
        InfoProp().let {
            println(it.name+"|"+it.email+"|"+it.happy+"|"+it.height+"|"+it.home+"|"+it.point)
            it.happy = false
            it.height = 176
        }
    }
}