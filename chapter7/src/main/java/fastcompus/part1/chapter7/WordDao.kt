package fastcompus.part1.chapter7

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface WordDao {
    //단어에 대한 모든 정보 가져오기
    @Query("SELECT * from word ORDER BY id DESC")
    fun getAll(): List<Word>

    //가장 최근(마지막)의 단어에 대한 정보 1개만 가져오기
    @Query("SELECT * from word ORDER BY id DESC LIMIT 1")
    fun getLastWord(word: Word)


    //새로운 단어 추가하기
    @Insert
    fun insert(word: Word)

    //기존의 단어 삭제하기
    @Delete
    fun delete(word: Word)

    //기존의 단어 수정하기
    @Update
    fun update(word: Word)
}