package com.example.kotlinlearn

sealed class Result
class Success(val code: Int) : Result()
class Exception(val code: Int, val message: String) : Result()