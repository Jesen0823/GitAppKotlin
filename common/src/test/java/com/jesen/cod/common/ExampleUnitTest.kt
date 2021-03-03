package com.jesen.cod.common

import org.junit.Assert
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun testBoolean(){
        val resultOther = false.yes {
            1
        }.otherwise{
            2
        }


        assertEquals(resultOther,2)
        val result = true.yes {
            1
        }.otherwise{
            2
        }
        //Assert.assertEquals(result,1)
        assertEquals(result,1)
    }
}
