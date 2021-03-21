package com.jesen.cod.layoutlibrary.v2

/**
 * 构造父类
 * */
const val MATCH_PAREN_v2 = -1
const val WRAP_CONTENT_v2 = -2

@DslMarker
@Target(AnnotationTarget.CLASS, AnnotationTarget.TYPE, AnnotationTarget.TYPEALIAS)
annotation class DslViewMarker

@DslViewMarker
interface DSLViewParent_v2