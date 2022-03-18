package com.mapr.credibanco.model.db

import androidx.room.*

@Dao
interface DataArtistDao {
    @Query("SELECT * FROM DataArtist")
    fun getAll(): List<DataArtist>

    @Query("SELECT * FROM DataArtist WHERE id LIKE :receiptId")
    fun findById(receiptId: String): DataArtist

    @Insert
    fun insertOne(vararg value: DataArtist)

    @Insert
    fun insertAll(users: List<DataArtist>)

    @Delete
    fun delete(value: DataArtist)

    @Query("DELETE FROM DataArtist")
    fun deleteAll()

    @Update
    fun update(vararg value: DataArtist)
}