package fastcompus.part1.chapter7

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [VocaBook::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    //Dao 선언
    abstract fun VocaDao(): VocaDao

    //싱글톤 패턴 (단일 인스턴스 사용)
    companion object {
        //단일 인스턴스 저장을 위한 변수 선언
        private var INSTANCE: AppDatabase? = null

        //인스턴스가 null인 경우에만 DB를 초기화
        fun getInstance(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                //단일 접근을 위해 synchronized 사용
                synchronized(AppDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "app-database.db"
                    ).build()
                }
            }
            return INSTANCE
        }
    }
}