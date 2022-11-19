//package com.example.refrigerator
//
//import androidx.room.*
//
//@Dao
//interface RecipeDao {
//    @Insert
//    fun insert(recipe: RecipeData)
//
//    @Delete
//    fun delete(recipe: RecipeData)
//
//    @Query("SELECT * FROM Recipe")
//    fun selectAll(): List<RecipeData>
//
////    @Query("SELECT * FROM Recipe WHERE userId = :userId")
////    fun selectedByUserID(userId: Int): User
////
////    @Query("SELECT * FROM User WHERE name = :name")
////    fun selectByUserNAme(name: String) : List<User>
////
////    @Query("UPDATE User SET name = :name WHERE userId = :userId")
////    fun updateNameByUserID(userId: Int, name: String)
////
//}