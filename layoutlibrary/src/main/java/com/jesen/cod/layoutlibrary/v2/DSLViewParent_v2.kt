package com.jesen.cod.layoutlibrary.v2

import kotlin.annotation.AnnotationTarget.*
/**
 * 构造父类
 * */
const val MATCH_PAREN_v2 = -1
const val WRAP_CONTENT_v2 = -2

@DslMarker
@Target(CLASS, TYPE, TYPEALIAS)
annotation class DslViewMarker

@DslViewMarker
interface DSLViewParent_v2